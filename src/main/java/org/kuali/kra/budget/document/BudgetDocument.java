/*
 * Copyright 2006-2008 The Kuali Foundation
 * 
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.osedu.org/licenses/ECL-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.kra.budget.document;

import static org.kuali.kra.infrastructure.KraServiceLocator.getService;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.kuali.core.document.Copyable;
import org.kuali.core.document.SessionDocument;
import org.kuali.core.service.BusinessObjectService;
import org.kuali.core.service.KualiConfigurationService;
import org.kuali.core.util.KualiDecimal;
import org.kuali.core.util.ObjectUtils;
import org.kuali.core.web.format.Formatter;
import org.kuali.core.web.ui.KeyLabelPair;
import org.kuali.kra.bo.DocumentNextvalue;
import org.kuali.kra.bo.InstituteLaRate;
import org.kuali.kra.bo.InstituteRate;
import org.kuali.kra.budget.BudgetDecimal;
import org.kuali.kra.budget.RateDecimal;
import org.kuali.kra.budget.bo.BudgetCostShare;
import org.kuali.kra.budget.bo.BudgetDistributionAndIncomeComponent;
import org.kuali.kra.budget.bo.BudgetLineItem;
import org.kuali.kra.budget.bo.BudgetLineItemCalculatedAmount;
import org.kuali.kra.budget.bo.BudgetModular;
import org.kuali.kra.budget.bo.BudgetModularIdc;
import org.kuali.kra.budget.bo.BudgetPeriod;
import org.kuali.kra.budget.bo.BudgetPerson;
import org.kuali.kra.budget.bo.BudgetPersonnelCalculatedAmount;
import org.kuali.kra.budget.bo.BudgetPersonnelDetails;
import org.kuali.kra.budget.bo.BudgetPersonnelRateAndBase;
import org.kuali.kra.budget.bo.BudgetPrintForm;
import org.kuali.kra.budget.bo.BudgetProjectIncome;
import org.kuali.kra.budget.bo.BudgetProposalLaRate;
import org.kuali.kra.budget.bo.BudgetProposalRate;
import org.kuali.kra.budget.bo.BudgetRateAndBase;
import org.kuali.kra.budget.bo.BudgetSubAwards;
import org.kuali.kra.budget.bo.BudgetUnrecoveredFandA;
import org.kuali.kra.budget.bo.CostElement;
import org.kuali.kra.budget.bo.RateClass;
import org.kuali.kra.budget.bo.RateClassType;
import org.kuali.kra.budget.bo.RateType;
import org.kuali.kra.budget.service.BudgetCalculationService;
import org.kuali.kra.budget.service.BudgetRatesService;
import org.kuali.kra.budget.service.BudgetSummaryService;
import org.kuali.kra.dao.jpa.BudgetDocumentNextValuesDao;
import org.kuali.kra.document.ResearchDocumentBase;
import org.kuali.kra.infrastructure.BudgetDecimalFormatter;
import org.kuali.kra.infrastructure.Constants;
import org.kuali.kra.infrastructure.KraServiceLocator;
import org.kuali.kra.infrastructure.RateDecimalFormatter;
import org.kuali.kra.proposaldevelopment.document.ProposalDevelopmentDocument;
import org.kuali.kra.proposaldevelopment.service.ProposalStatusService;

import edu.iu.uis.eden.exception.WorkflowException;

@IdClass(org.kuali.kra.budget.document.BudgetDocumentId.class)
@Entity
@AttributeOverrides({@AttributeOverride(name="documentNumber", column=@Column(name="DOCUMENT_NUMBER"))})
@EntityListeners(BudgetDocumentNextValuesDao.class)
@Table(name="BUDGET")
public class BudgetDocument extends ResearchDocumentBase implements Copyable, SessionDocument {
    private static final String DETAIL_TYPE_CODE = "D";
    private static final String BUDGET_NAMESPACE_CODE = "KRA-B";
    private static final String FALSE_FLAG = "N";
    private static final String TRUE_FLAG = "Y";

    private static final String DEFAULT_FISCAL_YEAR_START = "01/01/2000";

    private static final Log LOG = LogFactory.getLog(BudgetDocument.class);
    
    @Id
	@Column(name="PROPOSAL_NUMBER")
	private String proposalNumber;
    @Id
	@Column(name="VERSION_NUMBER")
	private Integer budgetVersionNumber;
    @Lob
	@Basic(fetch=FetchType.LAZY)
	@Column(name="COMMENTS")
	private String comments;
    @Column(name="COST_SHARING_AMOUNT")
	private BudgetDecimal costSharingAmount; // = new BudgetDecimal(0);
    String budgetJustification;
    @Column(name="END_DATE")
	private Date endDate;
    @Column(name="FINAL_VERSION_FLAG")
	private boolean finalVersionFlag;
    @Column(name="MODULAR_BUDGET_FLAG")
	private Boolean modularBudgetFlag;
    @Column(name="OH_RATE_CLASS_CODE")
	private String ohRateClassCode;
    @Column(name="OH_RATE_TYPE_CODE")
	private String ohRateTypeCode;
    @Column(name="RESIDUAL_FUNDS")
	private BudgetDecimal residualFunds;
    @Column(name="START_DATE")
	private Date startDate;
    @Column(name="TOTAL_COST")
	private BudgetDecimal totalCost;
    @Column(name="TOTAL_COST_LIMIT")
	private BudgetDecimal totalCostLimit; // = new BudgetDecimal(0);
    @Column(name="TOTAL_DIRECT_COST")
	private BudgetDecimal totalDirectCost;
    @Column(name="TOTAL_INDIRECT_COST")
	private BudgetDecimal totalIndirectCost; // = new BudgetDecimal(0);
    @Column(name="UNDERRECOVERY_AMOUNT")
	private BudgetDecimal underrecoveryAmount; // = new BudgetDecimal(0);
    @Column(name="UR_RATE_CLASS_CODE")
	private String urRateClassCode;
    @ManyToOne(fetch=FetchType.EAGER, cascade={CascadeType.PERSIST})
	@JoinColumn(name="PROPOSAL_NUMBER", insertable=false, updatable=false)
	private ProposalDevelopmentDocument proposal;
    @OneToOne(fetch=FetchType.EAGER, cascade={CascadeType.PERSIST})
	@JoinColumn(name="OH_RATE_CLASS_CODE", insertable=false, updatable=false)
	private RateClass rateClass;
    @OneToMany(cascade={CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE}, 
           targetEntity=org.kuali.kra.budget.bo.BudgetProposalRate.class, mappedBy="budgetDocument")
	private List<BudgetProposalRate> budgetProposalRates;
    @OneToMany(cascade={CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE}, 
           targetEntity=org.kuali.kra.budget.bo.BudgetProposalLaRate.class, mappedBy="budgetDocument")
	private List<BudgetProposalLaRate> budgetProposalLaRates;
    @OneToMany(cascade={CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE}, 
           targetEntity=org.kuali.kra.budget.bo.BudgetPeriod.class, mappedBy="budgetDocument")
	private List<BudgetPeriod> budgetPeriods;
    @OneToMany(cascade={CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE}, 
           targetEntity=org.kuali.kra.budget.bo.BudgetProjectIncome.class, mappedBy="budgetDocument")
	private List<BudgetProjectIncome> budgetProjectIncomes;
    @OneToMany(cascade={CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE}, 
           targetEntity=org.kuali.kra.budget.bo.BudgetCostShare.class, mappedBy="budgetDocument")
	private List<BudgetCostShare> budgetCostShares;
    @OneToMany(cascade={CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE}, 
           targetEntity=org.kuali.kra.budget.bo.BudgetUnrecoveredFandA.class, mappedBy="budgetDocument")
	private List<BudgetUnrecoveredFandA> budgetUnrecoveredFandAs;
    
    @Transient
    private String activityTypeCode="x";
    
    @Transient
    private boolean BudgetLineItemDeleted = false;
    
    @Transient
    private boolean rateClassTypesReloaded = false ;

    @Transient
    private List<BudgetPersonnelDetails> budgetPersonnelDetailsList;
    @OneToMany(cascade={CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE}, 
           targetEntity=org.kuali.kra.budget.bo.BudgetPerson.class, mappedBy="budgetDocument")
	private List<BudgetPerson> budgetPersons;
    
    @Transient
    private Date summaryPeriodStartDate;
    
    @Transient
    private Date summaryPeriodEndDate;    
    
    @Transient
    private List<InstituteRate> instituteRates;
    
    @Transient
    private List<InstituteLaRate> instituteLaRates;
    
    @Transient
    private List<RateClass> rateClasses;
    
    @Transient
    private List<RateClassType> rateClassTypes;
    
    @Transient
    private SortedMap <CostElement, List> objectCodeTotals ;
    
    @Transient
    private SortedMap <RateType, List> calculatedExpenseTotals ;
    
    @Transient
    private List<KeyLabelPair> budgetCategoryTypeCodes;
    
    @Transient
    private String budgetStatus;
    
    @Column(name="ON_OFF_CAMPUS_FLAG")
	private String onOffCampusFlag;
    
    @Transient
    private List<BudgetPrintForm> budgetPrintForms;
    @OneToMany(cascade={CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE}, 
           targetEntity=org.kuali.kra.budget.bo.BudgetSubAwards.class, mappedBy="budgetDocument")
	private List<BudgetSubAwards> budgetSubAwards;
    
    @Transient
    private boolean rateSynced;
    
    public BudgetDocument(){
        super();
        budgetCostShares = new ArrayList<BudgetCostShare>();
        budgetProjectIncomes = new ArrayList<BudgetProjectIncome>();
        budgetProposalRates = new ArrayList<BudgetProposalRate>();
        budgetProposalLaRates = new ArrayList<BudgetProposalLaRate>();
        budgetPeriods = new ArrayList<BudgetPeriod>();
        budgetPersonnelDetailsList = new ArrayList<BudgetPersonnelDetails>();
        budgetUnrecoveredFandAs = new ArrayList<BudgetUnrecoveredFandA>();
        instituteRates = new ArrayList<InstituteRate>();
        instituteLaRates = new ArrayList<InstituteLaRate>();
        rateClasses = new ArrayList<RateClass>();
        rateClassTypes = new ArrayList<RateClassType>();       
        budgetPersons = new ArrayList<BudgetPerson>();
        Formatter.registerFormatter(BudgetDecimal.class, BudgetDecimalFormatter.class);
        Formatter.registerFormatter(RateDecimal.class, RateDecimalFormatter.class);
        budgetCategoryTypeCodes = new ArrayList<KeyLabelPair>();
        budgetPrintForms = new ArrayList<BudgetPrintForm>();
        budgetSubAwards = new ArrayList<BudgetSubAwards>();
        setOnOffCampusFlag("D");
    }

    public void initialize() {
    }
    
    public Integer getHackedDocumentNextValue(String propertyName) {
        Integer propNextValue = 1;
        
        // search for property and get the latest number - increment for next call
        for(Iterator iter = getDocumentNextvalues().iterator(); iter.hasNext();) {
            DocumentNextvalue documentNextvalue = (DocumentNextvalue)iter.next();
            if(documentNextvalue.getPropertyName().equalsIgnoreCase(propertyName)) {
                propNextValue = documentNextvalue.getNextValue();
                documentNextvalue.setNextValue(propNextValue + 1);
            }
        }
        //TODO: need to solve the refresh issue. 
        //workaround till then
        /*****BEGIN BLOCK *****/
        if(propNextValue==1){
            BusinessObjectService bos = KraServiceLocator.getService(BusinessObjectService.class);
            Map<String, String> pkMap = new HashMap<String,String>();
            pkMap.put("documentKey", getProposalNumber());
            pkMap.put("propertyName", propertyName);
            DocumentNextvalue documentNextvalue = (DocumentNextvalue)bos.findByPrimaryKey(DocumentNextvalue.class, pkMap);
            if(documentNextvalue!=null) {
                propNextValue = documentNextvalue.getNextValue();
                documentNextvalue.setNextValue(propNextValue + 1);
                getDocumentNextvalues().add(documentNextvalue);
            }
        }
        /*****END BLOCK********/
        
        // property does not exist - set initial value and increment for next call
        if(propNextValue == 1) {
            DocumentNextvalue documentNextvalue = new DocumentNextvalue();
            documentNextvalue.setNextValue(propNextValue + 1);
            documentNextvalue.setPropertyName(propertyName);
            documentNextvalue.setDocumentKey(getDocumentNumber());
            getDocumentNextvalues().add(documentNextvalue);
        }
        setDocumentNextvalues(getDocumentNextvalues());
        return propNextValue;
    }
    
    
    
    @Override
    public void prepareForSave() {
        super.prepareForSave();
        KraServiceLocator.getService(ProposalStatusService.class).saveBudgetFinalVersionStatus(this);
        if (this.getProposal() != null && this.getProposal().getBudgetVersionOverviews() != null) {
            updateDocumentDescriptions(this.getProposal().getBudgetVersionOverviews());
        }
    }
        
    @Override
    public void toCopy() throws WorkflowException, IllegalStateException {
        super.toCopy();
        setBudgetVersionNumber(proposal.getNextBudgetVersionNumber());
    }
    
    public String getProposalNumber() {
        return proposalNumber;
    }

    public void setProposalNumber(String proposalNumber) {
        this.proposalNumber = proposalNumber;
    }

    public String getComments() {
        return comments;
    }

    /**
     * This method does what its name says
     * @return List of project totals for each budget period, where budget period 1 total is stored in list's 0th element
     */
    public List<KualiDecimal> getProjectIncomePeriodTotalsForEachBudgetPeriod() {
        Map<Integer, KualiDecimal> incomes = mapProjectIncomeTotalsToBudgetPeriodNumbers();                
        return findProjectIncomeTotalsForBudgetPeriods(incomes);
    }
    
    /**
     * This method does what its name says
     * @return
     */
    public List<FiscalYearSummary> getFiscalYearCostShareTotals() {
        Map<Integer, List<BudgetPeriod>> budgetPeriodFiscalYears = mapBudgetPeriodsToFiscalYears();
        return findCostShareTotalsForBudgetPeriods(budgetPeriodFiscalYears);
    }
    
    /**
     * This method does what its name says
     * @return
     */
    public List<FiscalYearSummary> getFiscalYearUnrecoveredFandATotals() {
        Map<Integer, List<BudgetPeriod>> budgetPeriodFiscalYears = mapBudgetPeriodsToFiscalYears();
        return findCostShareTotalsForBudgetPeriods(budgetPeriodFiscalYears); 
    }
    
    /**
     * This method reveals applicability of Cost Sharing to this budget
     * @return
     */
    public Boolean isCostSharingApplicable() {
        return loadCostSharingApplicability();
    }
    
    /**
     * This method reveals availability of Cost Sharing in this budget
     * @return
     */
    public boolean isCostSharingAvailable() {
        boolean costSharingAvailable = false;
        for(BudgetPeriod budgetPeriod: getBudgetPeriods()) {
            costSharingAvailable = budgetPeriod.getCostSharingAmount().isPositive();
            if(costSharingAvailable) { break; }
        }
        return costSharingAvailable;
    }

    public Boolean isUnrecoveredFandAApplicable() {
        return loadUnrecoveredFandAApplicability();
    }
    
    public boolean isUnrecoveredFandAAvailable() {
        boolean unrecoveredFandAAvailable = false;
        for(BudgetPeriod budgetPeriod: getBudgetPeriods()) {
            unrecoveredFandAAvailable = budgetPeriod.getUnderrecoveryAmount().isPositive();
            if(unrecoveredFandAAvailable) { break; }
        }
        return unrecoveredFandAAvailable;
    };
    
    /**
     * This method does what its name says
     * @return
     */
    public BudgetDecimal getAllocatedCostSharing() {
        BudgetDecimal costShareTotal = new BudgetDecimal(0.0);
        for(BudgetCostShare budgetCostShare: getBudgetCostShares()) {
            if(budgetCostShare.getShareAmount() != null) {
                costShareTotal = costShareTotal.add(budgetCostShare.getShareAmount());
            }
        }
        return costShareTotal;
    }
    
    /**
     * This method does what its name says
     * @return
     */
    public BudgetDecimal getAllocatedUnrecoveredFandA() {
        BudgetDecimal allocatedUnrecoveredFandA = BudgetDecimal.ZERO;
        for(BudgetUnrecoveredFandA unrecoveredFandA: getBudgetUnrecoveredFandAs()) {
            if (unrecoveredFandA.getAmount() != null) {
                allocatedUnrecoveredFandA = allocatedUnrecoveredFandA.add(unrecoveredFandA.getAmount());
            } 
        }
        return allocatedUnrecoveredFandA;
    }
    
    /**
     * This method does what its name says
     * @return
     */
    public KualiDecimal getProjectIncomeTotal() {
        KualiDecimal projectIncomeTotal = new KualiDecimal(0.0);
        for(BudgetProjectIncome budgetProjectIncome: budgetProjectIncomes) {
            if(budgetProjectIncome.getProjectIncome() != null) {
                projectIncomeTotal = projectIncomeTotal.add(budgetProjectIncome.getProjectIncome());
            } 
        }
        return projectIncomeTotal;
    }
    
    public void setComments(String comments) {
        this.comments = comments;
    }

    public BudgetDecimal getCostSharingAmount() {
        return costSharingAmount == null ?  new BudgetDecimal(0) : costSharingAmount;
    }

    public BudgetDecimal getUnallocatedCostSharing() {
        return getAvailableCostSharing().subtract(getAllocatedCostSharing());
    }
    
    public BudgetDecimal getUnallocatedUnrecoveredFandA() {
        return getAvailableUnrecoveredFandA().subtract(getAllocatedUnrecoveredFandA());
    }

    public void setCostSharingAmount(BudgetDecimal costSharingAmount) {
        this.costSharingAmount = costSharingAmount;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Boolean getFinalVersionFlag() {
        return finalVersionFlag;
    }

    public void setFinalVersionFlag(Boolean finalVersionFlag) {
        this.finalVersionFlag = finalVersionFlag;
    }

    public Boolean getModularBudgetFlag() {
        return modularBudgetFlag;
    }

    public void setModularBudgetFlag(Boolean modularBudgetFlag) {
        this.modularBudgetFlag = modularBudgetFlag;
    }

    public String getOhRateClassCode() {
        return ohRateClassCode;
    }

    public void setOhRateClassCode(String ohRateClassCode) {
        this.ohRateClassCode = ohRateClassCode;
    }

    public String getOhRateTypeCode() {
        return ohRateTypeCode;
    }

    public void setOhRateTypeCode(String ohRateTypeCode) {
        this.ohRateTypeCode = ohRateTypeCode;
    }

    public BudgetDecimal getResidualFunds() {
        return residualFunds;
    }

    public void setResidualFunds(BudgetDecimal residualFunds) {
        this.residualFunds = residualFunds;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public BudgetDecimal getTotalCost() {
        return totalCost == null ?  new BudgetDecimal(0) : totalCost;
    }

    public void setTotalCost(BudgetDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public BudgetDecimal getTotalCostLimit() {
        return totalCostLimit;
    }

    public void setTotalCostLimit(BudgetDecimal totalCostLimit) {
        this.totalCostLimit = totalCostLimit;
    }

    public BudgetDecimal getTotalDirectCost() {
        return totalDirectCost == null ?  new BudgetDecimal(0) : totalDirectCost;
    }

    public void setTotalDirectCost(BudgetDecimal totalDirectCost) {
        this.totalDirectCost = totalDirectCost;
    }

    public BudgetDecimal getTotalIndirectCost() {
        return totalIndirectCost == null ?  new BudgetDecimal(0) : totalIndirectCost;
    }

    public void setTotalIndirectCost(BudgetDecimal totalIndirectCost) {
        this.totalIndirectCost = totalIndirectCost;
    }

    public BudgetDecimal getUnderrecoveryAmount() {
        return underrecoveryAmount == null ?  new BudgetDecimal(0) : underrecoveryAmount;
    }

    public void setUnderrecoveryAmount(BudgetDecimal underrecoveryAmount) {
        this.underrecoveryAmount = underrecoveryAmount;
    }

    public String getUrRateClassCode() {
        return urRateClassCode;
    }

    public void setUrRateClassCode(String urRateClassCode) {
        this.urRateClassCode = urRateClassCode;
    }

    public Integer getBudgetVersionNumber() {
        return budgetVersionNumber;
    }

    public void setBudgetVersionNumber(Integer budgetVersionNumber) {
        this.budgetVersionNumber = budgetVersionNumber;
    }

    public ProposalDevelopmentDocument getProposal() {
        return proposal;
    }

    public void setProposal(ProposalDevelopmentDocument proposal) {
        this.proposal = proposal;
    }

    public RateClass getRateClass() {
        return rateClass;
    }

    public void setRateClass(RateClass rateClass) {
        this.rateClass = rateClass;
    }

    public List<BudgetPeriod> getBudgetPeriods() {
        /* check for new budget version - if new, generate budget periods */
        if(proposal != null) {
            setStartDate(proposal.getRequestedStartDateInitial());
            setEndDate(proposal.getRequestedEndDateInitial());
        }
        if(budgetPeriods.isEmpty() && getStartDate() != null) {
            getBudgetSummaryService().generateBudgetPeriods(budgetPeriods, getStartDate(), getEndDate());
        }
        return budgetPeriods;
    }
    
    /**
     * Gets the BudgetSummary attribute. 
     * @return Returns the BudgetSummary.
     */
    public BudgetSummaryService getBudgetSummaryService() {
        return getService(BudgetSummaryService.class);
    }
    
    public void setBudgetPeriods(List<BudgetPeriod> budgetPeriods) {
        this.budgetPeriods = budgetPeriods;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List buildListOfDeletionAwareLists() {
        List managedLists = super.buildListOfDeletionAwareLists();
//        managedLists.add(getBudgetPeriods());
        managedLists.add(getRateClassTypes());
        managedLists.add(getBudgetProjectIncomes());
        managedLists.add(getBudgetCostShares());
        managedLists.add(getBudgetUnrecoveredFandAs());
        List<BudgetLineItem> budgetLineItems = new ArrayList<BudgetLineItem>();
        List<BudgetLineItemCalculatedAmount> budgetLineItemCalculatedAmounts = new ArrayList<BudgetLineItemCalculatedAmount>();
        List<BudgetRateAndBase> budgetRateAndBaseList = new ArrayList<BudgetRateAndBase>();
        List<BudgetPersonnelDetails> budgetPersonnelDetailsList = new ArrayList<BudgetPersonnelDetails>();
        List<BudgetPersonnelCalculatedAmount> budgetPersonnelCalculatedAmounts = new ArrayList<BudgetPersonnelCalculatedAmount>();
        List<BudgetPersonnelRateAndBase> budgetPersonnelRateAndBaseList = new ArrayList<BudgetPersonnelRateAndBase>();
        List<BudgetModularIdc> budgetModularIdcs = new ArrayList<BudgetModularIdc>();
        List<BudgetModular> budgetModular = new ArrayList<BudgetModular>();
        
        for (BudgetPeriod budgetPeriod: getBudgetPeriods()) {
            // managedLists.addAll(buildDeletionAwareListsByPeriod(budgetPeriod));
            if (ObjectUtils.isNotNull(budgetPeriod.getBudgetModular())) {
                budgetModularIdcs.addAll(budgetPeriod.getBudgetModular().getBudgetModularIdcs());
                budgetModular.add(budgetPeriod.getBudgetModular());
            }
            List<BudgetLineItem> tempLIs = budgetPeriod.getBudgetLineItems();
            budgetLineItems.addAll(tempLIs);
            for (BudgetLineItem budgetLineItem : tempLIs) {
                budgetLineItemCalculatedAmounts.addAll(budgetLineItem.getBudgetLineItemCalculatedAmounts());
                budgetRateAndBaseList.addAll(budgetLineItem.getBudgetRateAndBaseList());
                List<BudgetPersonnelDetails> tempPerList = budgetLineItem.getBudgetPersonnelDetailsList();
                budgetPersonnelDetailsList.addAll(tempPerList);
                for (BudgetPersonnelDetails budgetPersonnelDetails : tempPerList) {
                    budgetPersonnelCalculatedAmounts.addAll(budgetPersonnelDetails.getBudgetPersonnelCalculatedAmounts());
                    budgetPersonnelRateAndBaseList.addAll(budgetPersonnelDetails.getBudgetPersonnelRateAndBaseList());
                }
            }
        }
        
        managedLists.add(budgetModularIdcs);
        managedLists.add(budgetModular);
        managedLists.add(budgetPersonnelRateAndBaseList);
        managedLists.add(budgetPersonnelCalculatedAmounts);
        managedLists.add(budgetPersonnelDetailsList);
        managedLists.add(budgetRateAndBaseList);
        managedLists.add(budgetLineItemCalculatedAmounts);
        managedLists.add(budgetLineItems);
        managedLists.add(getBudgetPersons());
        managedLists.add(getBudgetPeriods());
        managedLists.add(getBudgetProposalLaRates());
        managedLists.add(getBudgetProposalRates());
        managedLists.add(getBudgetSubAwards());
        return managedLists;
    }

    /**
     * A {@link BudgetPeriod} contains {@link BudgetLineItem} instances and {@link BudgetPersonDetails} instances. Each of 
     * these has a set of calculated amounts. We want to make sure that references to all of these are handled during deletion. 
     *
     * @param period the {@link BudgetPeriod} to apply to
     * @return a List of business objects which are added in {@link #buildListOfDeletionAwareLists()}
     */
    private List buildDeletionAwareListsByPeriod(BudgetPeriod period) {
        List retval = new ArrayList();
        retval.add(buildDeletionAwareListOfBudgetModularIdc(period));
            
        List<BudgetLineItem> budgetLineItemsCopy = new ArrayList<BudgetLineItem>();
        budgetLineItemsCopy.addAll(period.getBudgetLineItems());
        retval.add(budgetLineItemsCopy);

        for (BudgetLineItem budgetLineItem : budgetLineItemsCopy) {
            // retval.add(budgetLineItem.getBudgetLineItemCalculatedAmounts());
            
            List<BudgetPersonnelDetails> personnelDetailsCopy = new ArrayList<BudgetPersonnelDetails>();
            personnelDetailsCopy.addAll(budgetLineItem.getBudgetPersonnelDetailsList());
            retval.add(personnelDetailsCopy);
                       
            for (BudgetPersonnelDetails budgetPersonnelDetails : personnelDetailsCopy) {
                // retval.add(budgetPersonnelDetails.getBudgetPersonnelCalculatedAmounts());
            }
        }
        
        return retval;
    }

    private List<BudgetModularIdc> buildDeletionAwareListOfBudgetModularIdc(BudgetPeriod period) {
        List<BudgetModularIdc> retval = new ArrayList<BudgetModularIdc>();
        if (ObjectUtils.isNotNull(period.getBudgetModular())) {
            retval = new ArrayList<BudgetModularIdc>();
            retval.addAll(period.getBudgetModular().getBudgetModularIdcs());
        }
        return retval;
    }
    
    /**
     * This method checks if any BudgetPeriod LineItem's have Justification
     * @param budgetDocument
     * @return
     */
    public boolean areLineItemJustificationsPresent() {
        boolean justificationFound = false;
OUTER:  for(BudgetPeriod budgetPeriod: getBudgetPeriods()) {
            for(BudgetLineItem lineItem: budgetPeriod.getBudgetLineItems()) {
                justificationFound = !StringUtils.isEmpty(lineItem.getBudgetJustification());
                if(justificationFound) { break OUTER; }
            }
        }
        
        return justificationFound;
    }

    /**
     * Gets index i from the budgetPeriods list.
     * 
     * @param index
     * @return Budget Period at index i
     */
    public BudgetPeriod getBudgetPeriod(int index) {
        while (getBudgetPeriods().size() <= index) {
            getBudgetPeriods().add(new BudgetPeriod());
        }
        return (BudgetPeriod) getBudgetPeriods().get(index);
    }

    public Date getSummaryPeriodStartDate() {
        summaryPeriodStartDate = getBudgetPeriods().get(0).getStartDate();
        if(summaryPeriodStartDate == null) {
            summaryPeriodStartDate = startDate;
        }
        return summaryPeriodStartDate;
    }

    public Date getSummaryPeriodEndDate() {
        summaryPeriodEndDate = getBudgetPeriods().get(budgetPeriods.size()-1).getEndDate();
        if(summaryPeriodEndDate == null) {
            summaryPeriodEndDate = endDate;
        }
        return summaryPeriodEndDate;
    }

    /**
     * Gets the budgetProposalRates attribute. 
     * @return Returns the budgetProposalRates.
     */
    public List<BudgetProposalRate> getBudgetProposalRates() {
        return budgetProposalRates;
    }

    /**
     * Sets the budgetProposalRates attribute value.
     * @param budgetProposalRates The budgetProposalRates to set.
     */
    public void setBudgetProposalRates(List<BudgetProposalRate> budgetProposalRates) {
        this.budgetProposalRates = budgetProposalRates;
    }

    /**
     * Gets the budgetProposalLaRates attribute. 
     * @return Returns the budgetProposalLaRates.
     */
    public List<BudgetProposalLaRate> getBudgetProposalLaRates() {
        return budgetProposalLaRates;
    }

    /**
     * Sets the budgetProposalLaRates attribute value.
     * @param budgetProposalLaRates The budgetProposalLaRates to set.
     */
    public void setBudgetProposalLaRates(List<BudgetProposalLaRate> budgetProposalLaRates) {
        this.budgetProposalLaRates = budgetProposalLaRates;
    }

    /**
     * Gets the activityTypeCode attribute. 
     * @return Returns the activityTypeCode.
     */
    public String getActivityTypeCode() {
        return activityTypeCode;
    }

    /**
     * Sets the activityTypeCode attribute value.
     * @param activityTypeCode The activityTypeCode to set.
     */
    public void setActivityTypeCode(String activityTypeCode) {
        this.activityTypeCode = activityTypeCode;
    }

    public List<InstituteRate> getInstituteRates() {
        return instituteRates;
    }

    public void setInstituteRates(List<InstituteRate> instituteRates) {
        this.instituteRates = instituteRates;
    }

    /**
     * Gets the BudgetRates attribute. 
     * @return Returns the BudgetRates.
     */
    public BudgetRatesService getBudgetRatesService() {
        return getService(BudgetRatesService.class);
    }

    public List<RateClass> getRateClasses() {
        return rateClasses;
    }

    public void setRateClasses(List<RateClass> rateClasses) {
        this.rateClasses = rateClasses;
    }

    public List<RateClassType> getRateClassTypes() {
        /* check budget rates - if empty get all budget rates */
        if(rateClassTypes.isEmpty() || !rateClassTypesReloaded) {  
            if (!rateClassTypes.isEmpty()) {
                rateClassTypes.clear();
            }
            getBudgetRatesService().getBudgetRates(this.rateClassTypes, this);
            Collections.sort(rateClassTypes, new RateClassTypeComparator());
            rateClassTypesReloaded = true;
			}
        return rateClassTypes;
    }

    public void setRateClassTypes(List<RateClassType> rateClassTypes) {
        this.rateClassTypes = rateClassTypes;
    }

    /**
     * This method does what its name says
     * @return
     */
    public int getBudgetProjectIncomeCount() {
        return getCollectionSize(budgetProjectIncomes);
    }
    
    public List<BudgetProjectIncome> getBudgetProjectIncomes() {
        return budgetProjectIncomes;
    }

    /**
    * This method...
    * @param index
    * @return
    */
    public BudgetProjectIncome getBudgetProjectIncome(int index) {
        while (getBudgetProjectIncomes().size() <= index) {
            getBudgetProjectIncomes().add(new BudgetProjectIncome());
        }
        return (BudgetProjectIncome) getBudgetProjectIncomes().get(index);
    }
    
    public void setBudgetProjectIncomes(List<BudgetProjectIncome> budgetProjectIncomes) {
        this.budgetProjectIncomes = budgetProjectIncomes;
    }

    /**
     * This method adds an item to its collection
     * @param budgetCostShare
     */
    public void add(BudgetCostShare budgetCostShare) {
        addBudgetDistributionAndIncomeComponent(getBudgetCostShares(), budgetCostShare);
    }
    
    /**
     * This method adds an item to its collection
     * @param budgetProjectIncome
     */
    public void add(BudgetProjectIncome budgetProjectIncome) {
        budgetProjectIncome.setBudgetPeriodId(getBudgetPeriodId(budgetProjectIncome));
        addBudgetDistributionAndIncomeComponent(getBudgetProjectIncomes(), budgetProjectIncome);        
    }
    
    private Long getBudgetPeriodId(BudgetProjectIncome budgetProjectIncome) {
        //BudgetPeriod budgetPeriod = getBudgetPeriod(budgetProjectIncome.getBudgetPeriodNumber());
        List<BudgetPeriod> budgetPeriods = getBudgetPeriods();
        if(budgetPeriods != null && budgetPeriods.size() > 0) {
            for(BudgetPeriod bPeriod: budgetPeriods) {
                if(bPeriod.getBudgetPeriod() != null && budgetProjectIncome.getBudgetPeriodNumber() != null && bPeriod.getBudgetPeriod().intValue() == budgetProjectIncome.getBudgetPeriodNumber().intValue()) {
                    return bPeriod.getBudgetPeriodId();
                }
            }
        }
        return null;
    } 
    
    /**
     * This method adds an item to its collection
     * @param budgetProposalRate
     */
    public void add(BudgetProposalRate budgetProposalRate) {
        if(budgetProposalRate != null) {
            getBudgetProposalRates().add(budgetProposalRate);
        }
    }
    
    /**
     * This method adds an item to its collection
     * @param budgetProposalLaRate
     */
    public void add(BudgetProposalLaRate budgetProposalLaRate) {
        if(budgetProposalLaRate != null) {
            getBudgetProposalLaRates().add(budgetProposalLaRate);
        }
    }
    
    /**
     * This method adds an item to its collection
     * @return
     */
    public void add(BudgetUnrecoveredFandA budgetUnrecoveredFandA) {
        addBudgetDistributionAndIncomeComponent(getBudgetUnrecoveredFandAs(), budgetUnrecoveredFandA);
    }

    public List<BudgetPerson> getBudgetPersons() {
        return budgetPersons;
    }

    public void setBudgetPersons(List<BudgetPerson> budgetPersons) {
        this.budgetPersons = budgetPersons;
    }
    
    /**
     * Gets index i from the budgetPeriods list.
     * 
     * @param index
     * @return Budget Period at index i
     */
    public BudgetPerson getBudgetPerson(int index) {
        while (getBudgetPersons().size() <= index) {
            getBudgetPersons().add(new BudgetPerson());
        }
        return (BudgetPerson) getBudgetPersons().get(index);
    }
    
    public void addBudgetPerson(BudgetPerson budgetPerson) {
        getBudgetPersons().add(budgetPerson);
    }
    
    @Override
    public void processAfterRetrieve() {
        super.processAfterRetrieve();
        KraServiceLocator.getService(ProposalStatusService.class).loadBudgetStatus(this.getProposal());
    }

    /**
     * This method adds an item to its collection
     * @return
     */
    public void add(BudgetPeriod budgetPeriod) {
        budgetPeriod.setBudgetVersionNumber(getBudgetVersionNumber());
        getBudgetPeriods().add(budgetPeriod);        
    }

    /**
     * This method does what its name says
     * @param index
     * @return Object reference that was deleted
     */
    public BudgetCostShare removeBudgetCostShare(int index) {
        return getBudgetCostShares().remove(index);
    }
    
    /**
     * This method does what its name says
     * @param index
     * @return Object reference that was deleted
     */
    public BudgetProjectIncome removeBudgetProjectIncome(int index) {
        return getBudgetProjectIncomes().remove(index);
    }
    
    /**
     * This method does what its name says
     * @param index
     * @return Object reference that was deleted
     */
    public BudgetUnrecoveredFandA removeBudgetUnrecoveredFandA(int index) {
        return getBudgetUnrecoveredFandAs().remove(index);
    }

    public final List<InstituteLaRate> getInstituteLaRates() {
        return instituteLaRates;
    }

    public final void setInstituteLaRates(List<InstituteLaRate> instituteLaRates) {
        this.instituteLaRates = instituteLaRates;
    }
    
    /**
    * This method...
    * @param index
    * @return
    */
    public BudgetCostShare getBudgetCostShare(int index) {
        while (getBudgetCostShares().size() <= index) {
            getBudgetCostShares().add(new BudgetCostShare());
        }
        return (BudgetCostShare) getBudgetCostShares().get(index);
    }
    
    /**
     * This method does what its name says
     * @return
     */
    public List<BudgetCostShare> getBudgetCostShares() {
        return budgetCostShares;
    }
    
    /**
     * This method does what its name says
     * @return
     */
    public int getBudgetCostShareCount() {
        return getCollectionSize(budgetCostShares);
    }
    
    /**
    * This method...
    * @param index
    * @return
    */
    public BudgetUnrecoveredFandA getBudgetUnrecoveredFandA(int index) {
        while (getBudgetUnrecoveredFandAs().size() <= index) {
            getBudgetUnrecoveredFandAs().add(new BudgetUnrecoveredFandA());
        }
        return (BudgetUnrecoveredFandA) getBudgetUnrecoveredFandAs().get(index);
    }
    
    /**
     * This method does what its name says
     * @return
     */
    public List<BudgetUnrecoveredFandA> getBudgetUnrecoveredFandAs() {
        return budgetUnrecoveredFandAs;
    }
    
    /**
     * This method does what its name says
     * @return
     */
    public int getBudgetUnrecoveredFandACount() {
        return getCollectionSize(budgetUnrecoveredFandAs);
    }

    public void getBudgetTotals() {
        KraServiceLocator.getService(BudgetCalculationService.class).calculateBudgetTotals(this);
    }

    public SortedMap<CostElement, List> getObjectCodeTotals() {
        return objectCodeTotals;
    }

    public void setObjectCodeTotals(SortedMap<CostElement, List> objectCodeTotals) {
        this.objectCodeTotals = objectCodeTotals;
    }

    public SortedMap<RateType, List> getCalculatedExpenseTotals() {
        return calculatedExpenseTotals;
    }

    public void setCalculatedExpenseTotals(SortedMap<RateType, List> calculatedExpenseTotals) {
        this.calculatedExpenseTotals = calculatedExpenseTotals;
    }
    
    /**
     * This method does what its name says
     * @return
     */
    public BudgetDecimal getAvailableCostSharing() {
        BudgetDecimal availableCostShare = BudgetDecimal.ZERO;
        for(BudgetPeriod budgetPeriod: getBudgetPeriods()) {
            if(budgetPeriod.getCostSharingAmount() != null) {
                availableCostShare = availableCostShare.add(budgetPeriod.getCostSharingAmount());
            }
        }
        return availableCostShare;
    }
    
    /**
     * This method does what its name says
     * @return
     */
    public BudgetDecimal getAvailableUnrecoveredFandA() {
        BudgetDecimal availableUnrecoveredFandA = BudgetDecimal.ZERO;
        for(BudgetPeriod budgetPeriod: getBudgetPeriods()) {
            if(budgetPeriod.getUnderrecoveryAmount() != null) {
                availableUnrecoveredFandA = availableUnrecoveredFandA.add(budgetPeriod.getUnderrecoveryAmount());
            }
        }
        return availableUnrecoveredFandA;
    }
    
    /**
     * This method does what its name says
     * @param fiscalYear
     * @return
     */
    public BudgetDecimal findCostSharingForFiscalYear(Integer fiscalYear) {
        BudgetDecimal costSharing = BudgetDecimal.ZERO;
        
        List<FiscalYearSummary> costShareFiscalYears = findCostShareTotalsForBudgetPeriods(mapBudgetPeriodsToFiscalYears());
        for(FiscalYearSummary costShareFiscalYear: costShareFiscalYears) {
            if(costShareFiscalYear.fiscalYear == fiscalYear) {
                costSharing = costShareFiscalYear.costShare;
                break;
            }
        }
        
        return costSharing;
    }
    
    /**
     * This method does what its name says
     * @param fiscalYear
     * @return
     */
    public BudgetDecimal findUnrecoveredFandAForFiscalYear(Integer fiscalYear) {
        BudgetDecimal unrecoveredFandA = BudgetDecimal.ZERO;
        
        List<FiscalYearSummary> fiscalYearSummaries = findCostShareTotalsForBudgetPeriods(mapBudgetPeriodsToFiscalYears());
        for(FiscalYearSummary fiscalYearSummary: fiscalYearSummaries) {
            if(fiscalYearSummary.getFiscalYear() == fiscalYear) {
                unrecoveredFandA = fiscalYearSummary.getUnrecoveredFandA();
                break;
            }
        }
        
        return unrecoveredFandA;
    }
    
    /**
     * This method loads the fiscal year start from the database. Protected to allow mocking out service call
     * @return
     */
    protected Date loadFiscalYearStart() {
        KualiConfigurationService kualiConfigurationService = KraServiceLocator.getService(KualiConfigurationService.class);
        return createDateFromString(kualiConfigurationService.getParameterValue(BUDGET_NAMESPACE_CODE, DETAIL_TYPE_CODE, Constants.BUDGET_CURRENT_FISCAL_YEAR));        
    }
    
    /**
     * This method loads the cost sharing applicability flag from the database. Protected to allow mocking out service call
     * @return
     */
    protected Boolean loadCostSharingApplicability() {
        return getApplicabilityValue(Constants.BUDGET_COST_SHARING_APPLICABILITY_FLAG);        
    }
    
    /**
     * This method loads the unrecovered F&A applicability flag from the database. Protected to allow mocking out service call
     * @return
     */
    protected Boolean loadUnrecoveredFandAApplicability() {
        return getApplicabilityValue(Constants.BUDGET_UNRECOVERED_F_AND_A_APPLICABILITY_FLAG);        
    }

    /**
     * 
     * This method should be in DateUtils, but wasn't found there
     * @param budgetFiscalYearStart
     * @return
     */
    protected Date createDateFromString(String budgetFiscalYearStart) {
        if (budgetFiscalYearStart == null) {
            return null;
        }
        String[] dateParts = budgetFiscalYearStart.split("/"); // mm/dd/yyyy
        
        // using Calendar her because org.kuali.core.util.DateUtils.newdate(...) has hour value in time fields (UT?)
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.set(Integer.valueOf(dateParts[2]), Integer.valueOf(dateParts[0]) - 1, Integer.valueOf(dateParts[1]), 0, 0, 0);
        return new Date(calendar.getTimeInMillis());
    }
    
    /**
     * This method adds a BudgetDistributionAndIncomeComponent to the specified list after setting its key field values
     * @param distributionAndIncomeComponents
     * @param distributionAndIncomeComponent
     */
    @SuppressWarnings("unchecked")
    private void addBudgetDistributionAndIncomeComponent(List distributionAndIncomeComponents, BudgetDistributionAndIncomeComponent distributionAndIncomeComponent) {
        if(distributionAndIncomeComponent != null) {
            distributionAndIncomeComponent.setProposalNumber(getProposalNumber());
            distributionAndIncomeComponent.setBudgetVersionNumber(getBudgetVersionNumber());            
            distributionAndIncomeComponent.setDocumentComponentId(getHackedDocumentNextValue(distributionAndIncomeComponent.getDocumentComponentIdKey()));            
            distributionAndIncomeComponents.add(distributionAndIncomeComponent);
        } else {
            LOG.warn("Attempt to add null distributionAndIncomeComponent was ignored.");
        }
    }
    
    
    /**
     * This method does what its name says
     * @param fiscalYear
     * @return
     */
    private FiscalYearApplicableRate findApplicableRatesForFiscalYear(Integer fiscalYear) {
        String unrecoveredFandARateClassCode = getUrRateClassCode();
        if(unrecoveredFandARateClassCode == null || unrecoveredFandARateClassCode.trim().length() == 0) {
            return new FiscalYearApplicableRate(fiscalYear, RateDecimal.ZERO_RATE, RateDecimal.ZERO_RATE);
        } else {
            RateDecimal offCampusRate = findApplicableRateForRateClassCode(fiscalYear, unrecoveredFandARateClassCode, false);
            RateDecimal onCampusRate = findApplicableRateForRateClassCode(fiscalYear, unrecoveredFandARateClassCode, true);
            return new FiscalYearApplicableRate(fiscalYear, onCampusRate, offCampusRate);
        }
    }

    /**
     * This method does what its name says
     * @param fiscalYear
     * @param unrecoveredFandARateClassCode
     * @param findOnCampusRate
     * @return
     */
    private RateDecimal findApplicableRateForRateClassCode(Integer fiscalYear, String unrecoveredFandARateClassCode, boolean findOnCampusRate) {
        RateDecimal applicableRate = RateDecimal.ZERO_RATE;
        for(BudgetProposalRate budgetProposalRate: getBudgetProposalRates()) {
            if(Integer.valueOf(budgetProposalRate.getFiscalYear()).equals(fiscalYear) && budgetProposalRate.getRateClassCode().equalsIgnoreCase(unrecoveredFandARateClassCode) && findOnCampusRate == budgetProposalRate.getOnOffCampusFlag()) {
                applicableRate = new RateDecimal(budgetProposalRate.getApplicableRate().bigDecimalValue());
                break;
            }
        }
        return applicableRate;
    }

    /**
     * This method does what its name says
     * @param budgetPeriodFiscalYears
     * @return
     */
    private List<FiscalYearSummary> findCostShareTotalsForBudgetPeriods(Map<Integer, List<BudgetPeriod>> budgetPeriodFiscalYears) {
        List<FiscalYearSummary> fiscalYearSummaries = new ArrayList<FiscalYearSummary>();
        for(Integer fiscalYear: budgetPeriodFiscalYears.keySet()) {            
            BudgetDecimal fiscalYearCostShareAmount = BudgetDecimal.ZERO;
            BudgetDecimal fiscalYearUnrecoveredFandA = BudgetDecimal.ZERO;
            List<BudgetPeriod> budgetPeriodsInFiscalYear = budgetPeriodFiscalYears.get(fiscalYear);
            for(BudgetPeriod budgetPeriod: budgetPeriodsInFiscalYear) {
                fiscalYearCostShareAmount = fiscalYearCostShareAmount.add(budgetPeriod.getCostSharingAmount());
                fiscalYearUnrecoveredFandA = fiscalYearUnrecoveredFandA.add(budgetPeriod.getUnderrecoveryAmount());
            }
            fiscalYearSummaries.add(new FiscalYearSummary(budgetPeriodsInFiscalYear.get(0), fiscalYear, fiscalYearCostShareAmount, fiscalYearUnrecoveredFandA, findApplicableRatesForFiscalYear(fiscalYear)));
        }
        return fiscalYearSummaries;
    }
    
    /**
     * This method does what its name says
     * @param incomes
     * @return
     */
    private List<KualiDecimal> findProjectIncomeTotalsForBudgetPeriods(Map<Integer, KualiDecimal> incomes) {
        List<KualiDecimal> periodIncomeTotals = new ArrayList<KualiDecimal>(budgetPeriods.size());
        for(BudgetPeriod budgetPeriod: budgetPeriods) {
            KualiDecimal periodIncomeTotal = incomes.get(budgetPeriod.getBudgetPeriod());
            if(periodIncomeTotal == null) { periodIncomeTotal = KualiDecimal.ZERO; }
            periodIncomeTotals.add(periodIncomeTotal);
        }
        return periodIncomeTotals;
    }
    
    /**
     * This method returns a collection if the collection is not null; otherwise, zero is returned 
     * @param collection
     * @return
     */
    @SuppressWarnings("unchecked")
    private int getCollectionSize(Collection collection) {
        return collection != null ? collection.size() : 0;
    }
    
    /**
     * This method returns the fiscalYearStart, loading it from the database if needed
     *  
     * @return
     */
    private Date getFiscalYearStart() {
        return loadFiscalYearStart();
    }
    
    /**
     * This method looks up the applicability flag
     * @param parmName
     * @return
     */
    protected Boolean getApplicabilityValue(String parmName) {
        String parmValue;
        
        try {
            KualiConfigurationService kualiConfigurationService = KraServiceLocator.getService(KualiConfigurationService.class);
            parmValue = kualiConfigurationService.getParameterValue(BUDGET_NAMESPACE_CODE, DETAIL_TYPE_CODE, parmName);
        } catch(Exception exc) {
            parmValue = FALSE_FLAG;
        }
        
        return parmValue.equalsIgnoreCase(TRUE_FLAG);
    }
    
    /**
     * This method does what its name says
     * @return
     */
    private Map<Integer, KualiDecimal> mapProjectIncomeTotalsToBudgetPeriodNumbers() {
        Map<Integer, KualiDecimal> budgetPeriodProjectIncomeMap = new TreeMap<Integer, KualiDecimal>();
        for(BudgetProjectIncome budgetProjectIncome: budgetProjectIncomes) {
            Integer budgetPeriodNumber = budgetProjectIncome.getBudgetPeriodNumber();
            KualiDecimal amount = budgetPeriodProjectIncomeMap.get(budgetPeriodNumber);
            amount = (amount == null) ? budgetProjectIncome.getProjectIncome() : amount.add(budgetProjectIncome.getProjectIncome());
            
            budgetPeriodProjectIncomeMap.put(budgetPeriodNumber, amount);
        }
        return budgetPeriodProjectIncomeMap;
    }
    
    /**
     * This method does what its name says
     * @return
     */
    private Map<Integer, List<BudgetPeriod>> mapBudgetPeriodsToFiscalYears() {
        Map<Integer, List<BudgetPeriod>> budgetPeriodFiscalYears = new TreeMap<Integer, List<BudgetPeriod>>();
        
        for(BudgetPeriod budgetPeriod: getBudgetPeriods()) {
            Integer fiscalYear = budgetPeriod.calculateFiscalYear(getFiscalYearStart());
            
            List<BudgetPeriod> budgetPeriodsInFiscalYear = budgetPeriodFiscalYears.get(fiscalYear);
            if(budgetPeriodsInFiscalYear == null) {
                budgetPeriodsInFiscalYear = new ArrayList<BudgetPeriod>();
                budgetPeriodFiscalYears.put(fiscalYear, budgetPeriodsInFiscalYear);
            }
            budgetPeriodsInFiscalYear.add(budgetPeriod);
            Collections.sort(budgetPeriodsInFiscalYear, BudgetPeriod.getBudgetPeriodDateComparator());
        }
        return budgetPeriodFiscalYears;
    }
    
    /**
     * This class wraps fiscal year, on and off campus rate
     */
    public class FiscalYearApplicableRate {
        private Integer fiscalYear;
        private RateDecimal onCampusApplicableRate;
        private RateDecimal offCampusApplicableRate;
        
        /**
         * Constructs a BudgetDocument.java.
         * @param fiscalYear
         * @param onCampusApplicableRate
         * @param offCampusApplicableRate
         */
        public FiscalYearApplicableRate(Integer fiscalYear, RateDecimal onCampusApplicableRate, RateDecimal offCampusApplicableRate) {
            this.fiscalYear = fiscalYear;
            this.onCampusApplicableRate = onCampusApplicableRate;
            this.offCampusApplicableRate = offCampusApplicableRate;
        }
        
        /**
         * 
         * This method...
         * @return
         */
        public Integer getFiscalYear() {
            return fiscalYear;
        }
        
        /**
         * 
         * This method...
         * @return
         */
        public RateDecimal getOnCampusApplicableRate() {
            return onCampusApplicableRate;
        }

        /**
         * 
         * This method...
         * @return
         */
        public RateDecimal getOffCampusApplicableRate() {
            return offCampusApplicableRate;
        }
    }
    
    /**
     * This class wraps the fiscal year, assignedBudgetPeriod, fiscl year applicable rate, and the fiscal year totals for cost share and unrecovered
     */
    public class FiscalYearSummary {
        private int fiscalYear;
        private BudgetPeriod assignedBudgetPeriod;
        private BudgetDecimal costShare;
        private BudgetDecimal unrecoveredFandA;
        private FiscalYearApplicableRate fiscalYearRates;
        
        /**
         * 
         * Constructs a BudgetDocument.java.
         * @param assignedBudgetPeriod
         * @param fiscalYear
         * @param costShare
         * @param unrecoveredFandA
         * @param fiscalYearRates
         */
        public FiscalYearSummary(BudgetPeriod assignedBudgetPeriod, int fiscalYear, BudgetDecimal costShare, BudgetDecimal unrecoveredFandA,
                                    FiscalYearApplicableRate fiscalYearRates) {
            super();
            this.assignedBudgetPeriod = assignedBudgetPeriod;
            this.fiscalYear = fiscalYear;
            this.costShare = costShare;
            this.unrecoveredFandA = unrecoveredFandA;
            this.fiscalYearRates = fiscalYearRates;
        }

        /**
         * 
         * This method...
         * @return
         */
        public int getFiscalYear() {
            return fiscalYear;
        }

        /**
         * 
         * This method...
         * @return
         */
        public BudgetPeriod getAssignedBudgetPeriod() {
            return assignedBudgetPeriod;
        }

        /**
         * 
         * This method...
         * @return
         */
        public BudgetDecimal getCostShare() {
            return costShare;
        }        
        
        /**
         * 
         * This method...
         * @return
         */
        public FiscalYearApplicableRate getFiscalYearRates() {
            return fiscalYearRates;
        }
        
        /**
         * 
         * This method...
         * @return
         */
        public BudgetDecimal getUnrecoveredFandA() {
            return unrecoveredFandA;
        }
    }    

    public List<KeyLabelPair> getBudgetCategoryTypeCodes() {
        return budgetCategoryTypeCodes;
    }

    public void setBudgetCategoryTypeCodes(List<KeyLabelPair> budgetCategoryTypeCodes) {
        this.budgetCategoryTypeCodes = budgetCategoryTypeCodes;
    }

    public List<BudgetPersonnelDetails> getBudgetPersonnelDetailsList() {
        return budgetPersonnelDetailsList;
    }

    public void setBudgetPersonnelDetailsList(List<BudgetPersonnelDetails> budgetPersonnelDetailsList) {
        this.budgetPersonnelDetailsList = budgetPersonnelDetailsList;
    }

    public String getBudgetStatus() {
        return budgetStatus;
    }
    
    public void setBudgetStatus(String budgetStatus) {
        this.budgetStatus = budgetStatus;
    }

    public String getBudgetJustification() {
        return budgetJustification;
    }

    public void setBudgetJustification(String budgetJustification) {
        this.budgetJustification = budgetJustification;
    }

    public String getOnOffCampusFlag() {
        return onOffCampusFlag;
    }

    public String getOnOffCampusFlagDescription() {
        return getBudgetSummaryService().getOnOffCampusFlagDescription(getOnOffCampusFlag());
    }

    public void setOnOffCampusFlag(String onOffCampusFlag) {
        this.onOffCampusFlag = onOffCampusFlag;
    }
    
    /**
    * Gets the budgetLineItemDeleted attribute. 
    * @return Returns the budgetLineItemDeleted.
    */
    public boolean isBudgetLineItemDeleted() {
        return BudgetLineItemDeleted;
    }
    
    /**
     * Sets the budgetLineItemDeleted attribute value.
     * @param budgetLineItemDeleted The budgetLineItemDeleted to set.
     */
    public void setBudgetLineItemDeleted(boolean budgetLineItemDeleted) {
        BudgetLineItemDeleted = budgetLineItemDeleted;
    }

    /**
     * Gets the budgetPrintForms attribute. 
     * @return Returns the budgetPrintForms.
     */
    public List<BudgetPrintForm> getBudgetPrintForms() {
        return budgetPrintForms;
    }

    /**
     * Sets the budgetPrintForms attribute value.
     * @param budgetPrintForms The budgetPrintForms to set.
     */
    public void setBudgetPrintForms(List<BudgetPrintForm> budgetPrintForms) {
        this.budgetPrintForms = budgetPrintForms;
    }

    public boolean isRateClassTypesReloaded() {
        return rateClassTypesReloaded;
    }

    public void setRateClassTypesReloaded(boolean rateClassTypesReloaded) {
        this.rateClassTypesReloaded = rateClassTypesReloaded;
    }

    public List<BudgetSubAwards> getBudgetSubAwards() {
        return budgetSubAwards;
    }

    public void setBudgetSubAwards(List<BudgetSubAwards> budgetSubAwards) {
        this.budgetSubAwards = budgetSubAwards;
    }

    public boolean isRateSynced() {
        return rateSynced;
    }

    public void setRateSynced(boolean rateSynced) {
        this.rateSynced = rateSynced;
    }
}

class RateClassTypeComparator implements Comparator<RateClassType> {
    public int compare(RateClassType rateClassType1, RateClassType rateClassType2) {
      RateClassType r1 = (RateClassType)rateClassType1;
      RateClassType r2 = (RateClassType)rateClassType2;
      return r1.getSortId().compareTo(r2.getSortId());
      //return s1.toLowerCase().compareTo(s2.toLowerCase());
    }
  }

