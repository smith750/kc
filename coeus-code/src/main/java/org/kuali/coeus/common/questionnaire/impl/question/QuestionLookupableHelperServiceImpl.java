/*
 * Copyright 2005-2014 The Kuali Foundation
 * 
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.opensource.org/licenses/ecl1.php
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.coeus.common.questionnaire.impl.question;

import org.apache.commons.lang3.StringUtils;
import org.kuali.coeus.common.questionnaire.framework.question.Question;
import org.kuali.kra.infrastructure.PermissionConstants;
import org.kuali.rice.core.api.encryption.EncryptionService;
import org.kuali.rice.kns.lookup.HtmlData;
import org.kuali.rice.kns.lookup.HtmlData.AnchorHtmlData;
import org.kuali.rice.kns.lookup.KualiLookupableHelperServiceImpl;
import org.kuali.rice.kns.lookup.LookupResultsService;
import org.kuali.rice.kns.service.BusinessObjectDictionaryService;
import org.kuali.rice.kns.service.BusinessObjectMetaDataService;
import org.kuali.rice.kns.web.ui.Row;
import org.kuali.rice.krad.bo.BusinessObject;
import org.kuali.rice.krad.service.*;
import org.kuali.rice.krad.util.KRADConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Question specific lookupable helper service methods.
 */
@Component("questionLookupableHelperService")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class QuestionLookupableHelperServiceImpl extends KualiLookupableHelperServiceImpl {

    private static final long serialVersionUID = 7936563894902841571L;

    private static final String MAINTENANCE = "maintenance";
    private static final String NEW_MAINTENANCE = "../maintenanceQ";
    private static final String VIEW = "view";
    private static final String SEQUENCE_STATUS_CURRENT = "C";
    private static final String DOCHANDLER_LINK = "%s/DocHandler.do?command=displayDocSearchView&docId=%s";

    @Autowired
    @Qualifier("questionAuthorizationService")
    private transient QuestionAuthorizationService questionAuthorizationService;

    @Autowired
    @Qualifier("businessObjectDictionaryService")
    @Override
    public void setBusinessObjectDictionaryService(BusinessObjectDictionaryService businessObjectDictionaryService) {
        super.setBusinessObjectDictionaryService(businessObjectDictionaryService);
    }

    @Autowired
    @Qualifier("businessObjectService")
    @Override
    public void setBusinessObjectService(BusinessObjectService businessObjectyService) {
        super.setBusinessObjectService(businessObjectService);
    }

    @Autowired
    @Qualifier("businessObjectMetaDataService")
    @Override
    public void setBusinessObjectMetaDataService(BusinessObjectMetaDataService businessObjectMetaDataService) {
        super.setBusinessObjectMetaDataService(businessObjectMetaDataService);
    }

    @Autowired
    @Qualifier("dataDictionaryService")
    @Override
    public void setDataDictionaryService(DataDictionaryService dataDictionaryService) {
        super.setDataDictionaryService(dataDictionaryService);
    }

    @Autowired
    @Qualifier("encryptionService")
    @Override
    public void setEncryptionService(EncryptionService encryptionService) {
        super.setEncryptionService(encryptionService);
    }

    @Autowired
    @Qualifier("lookupResultsService")
    @Override
    public void setLookupResultsService(LookupResultsService lookupResultsService) {
        super.setLookupResultsService(lookupResultsService);
    }

    @Autowired
    @Qualifier("lookupService")
    @Override
    public void setLookupService(LookupService lookupService) {
        super.setLookupService(lookupService);
    }

    @Autowired
    @Qualifier("persistenceStructureService")
    @Override
    public void setPersistenceStructureService(PersistenceStructureService persistenceStructureService) {
        super.setPersistenceStructureService(persistenceStructureService);
    }

    @Autowired
    @Qualifier("sequenceAccessorService")
    @Override
    public void setSequenceAccessorService(SequenceAccessorService sequenceAccessorService) {
        super.setSequenceAccessorService(sequenceAccessorService);
    }

    /**
     * Don't show the option to select active/inactive questions since Question is being versioned 
     * and we only want active questions in questionnaires.
     * 
     * @see org.kuali.rice.kns.lookup.AbstractLookupableHelperServiceImpl#getRows()
     */
    @Override
    public List<Row> getRows() {
        if ((getParameters().containsKey("multipleValues") && StringUtils.equals(((String[]) this.getParameters().get("multipleValues"))[0], "Yes"))
                || (getParameters().containsKey("multipleValues") && StringUtils.equals(((String[]) this.getParameters().get("multipleValues"))[0], "true"))
                || (getParameters().containsKey("conversionFields") &&  !StringUtils.isEmpty(((String[]) this.getParameters().get("conversionFields"))[0]))) {
            List<Row> retRows = new ArrayList<Row>();
            for (Row row : super.getRows()) {
                if (!"status".equals(row.getFields().get(0).getPropertyName())) {
                    retRows.add(row);
                }
            }
            return retRows;
        } else {
            return super.getRows();
        }
    }

    /**
     * Since Question is being versioned, the lookup should only return active versions of the question
     * (the one with the highest sequenceNumber).
     * 
     * @see org.kuali.rice.kns.lookup.KualiLookupableHelperServiceImpl#getSearchResults(java.util.Map)
     */
    @Override
    public List<? extends BusinessObject> getSearchResults(Map<String, String> fieldValues) {
        fieldValues.put("sequenceStatus", SEQUENCE_STATUS_CURRENT);
        if ((getParameters().containsKey("multipleValues") && StringUtils.equals(((String[]) this.getParameters().get("multipleValues"))[0], "Yes"))
                || (getParameters().containsKey("conversionFields") &&  !StringUtils.isEmpty(((String[]) this.getParameters().get("conversionFields"))[0]))) {
            fieldValues.put("status", "A");
        }        
        return super.getSearchResults(fieldValues);
    }
    
    /**
     * Only display edit, copy and view links for the Questions if proper permission is given.
     * @see org.kuali.rice.kns.lookup.AbstractLookupableHelperServiceImpl#getCustomActionUrls(org.kuali.rice.krad.bo.BusinessObject, java.util.List)
     */
    @Override
    public List<HtmlData> getCustomActionUrls(BusinessObject businessObject, List pkNames) {
        List<HtmlData> htmlDataList = new ArrayList<HtmlData>();
        boolean hasModifyPermission = questionAuthorizationService.hasPermission(PermissionConstants.MODIFY_QUESTION);
        boolean hasViewPermission = hasModifyPermission || questionAuthorizationService.hasPermission(PermissionConstants.VIEW_QUESTION);
        if (hasModifyPermission) {
            AnchorHtmlData editHtmlData = getUrlData(businessObject, KRADConstants.MAINTENANCE_EDIT_METHOD_TO_CALL, pkNames);
            editHtmlData.setHref(editHtmlData.getHref().replace(MAINTENANCE, NEW_MAINTENANCE));
            htmlDataList.add(editHtmlData);

            AnchorHtmlData copyHtmlData = getUrlData(businessObject, KRADConstants.MAINTENANCE_COPY_METHOD_TO_CALL, pkNames);
            copyHtmlData.setHref(copyHtmlData.getHref().replace(MAINTENANCE, NEW_MAINTENANCE));
            htmlDataList.add(copyHtmlData);
            
            AnchorHtmlData deleteHtmlData = getUrlData(businessObject, KRADConstants.MAINTENANCE_DELETE_METHOD_TO_CALL, pkNames);
            deleteHtmlData.setHref(deleteHtmlData.getHref().replace(MAINTENANCE, NEW_MAINTENANCE));
            htmlDataList.add(deleteHtmlData);
        } 
        // if user can view question, then if doc number exists, use doc service to view, otherwise open for editing in read-only mode
        if (hasViewPermission) {
            AnchorHtmlData viewHtmlData = new AnchorHtmlData();
            if (((Question) businessObject).getDocumentNumber() != null) {
                String workflowUrl = getKualiConfigurationService().getPropertyValueAsString(KRADConstants.WORKFLOW_URL_KEY);
                viewHtmlData.setHref(String.format(DOCHANDLER_LINK, workflowUrl, ((Question) businessObject).getDocumentNumber()).replace("&docId", "&readOnly=true&docId"));
            } else {
                viewHtmlData = getUrlData(businessObject, KRADConstants.MAINTENANCE_EDIT_METHOD_TO_CALL, pkNames);
                viewHtmlData.setHref(viewHtmlData.getHref().replace(MAINTENANCE, NEW_MAINTENANCE) + "&readOnly=true");
            }
            viewHtmlData.setDisplayText(VIEW);
            htmlDataList.add(viewHtmlData);
        }
        return htmlDataList;
    }

    public void setQuestionAuthorizationService(QuestionAuthorizationService questionAuthorizationService) {
        this.questionAuthorizationService = questionAuthorizationService;
    }

    public QuestionAuthorizationService getQuestionAuthorizationService() {
        return questionAuthorizationService;
    }

}
