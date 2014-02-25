/*
 * Copyright 2005-2013 The Kuali Foundation
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
package org.kuali.kra.institutionalproposal.rules;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.kuali.coeus.sys.framework.rule.KcDocumentEventBase;
import org.kuali.kra.institutionalproposal.document.InstitutionalProposalDocument;
import org.kuali.kra.institutionalproposal.home.InstitutionalProposalCostShare;
import org.kuali.rice.krad.rules.rule.BusinessRule;

/**
 * This class...
 */
public class InstitutionalProposalAddCostShareRuleEvent extends KcDocumentEventBase {

    private static final Log LOG = LogFactory.getLog(InstitutionalProposalAddCostShareRuleEvent.class);
    
    private InstitutionalProposalCostShare institutionalProposalCostShare;

    public InstitutionalProposalAddCostShareRuleEvent(String errorPathPrefix, 
            InstitutionalProposalDocument institutionalProposalDocument,
            InstitutionalProposalCostShare institutionalProposalCostShare) {
        super("Cost Share", errorPathPrefix, institutionalProposalDocument);
        this.institutionalProposalCostShare = institutionalProposalCostShare;
    }
    
    /**
     * Convenience method to return an InstitutionalProposalDocument
     * @return
     */
    public InstitutionalProposalDocument getInstitutionalProposalDocument() {
        return (InstitutionalProposalDocument) getDocument();
    }
    
    /**
     * This method returns the equipment item for validation
     * @return
     */
    public InstitutionalProposalCostShare getCostShareForValidation() {
        return institutionalProposalCostShare;
    }
    
    
    /**
     * @see org.kuali.coeus.sys.framework.rule.KcDocumentEventBase#logEvent()
     */
    @Override
    protected void logEvent() {
        LOG.info("Logging InstitutionalProposalCostShareRuleEvent");
    }

    /**
     * @see org.kuali.core.rule.event.KualiDocumentEvent#getRuleInterfaceClass()
     */
    @SuppressWarnings("unchecked")
    public Class getRuleInterfaceClass() {
        return InstitutionalProposalAddCostShareRule.class;
    }

    /**
     * @see org.kuali.core.rule.event.KualiDocumentEvent#invokeRuleMethod(org.kuali.core.rule.BusinessRule)
     */
    public boolean invokeRuleMethod(BusinessRule rule) {
        return ((InstitutionalProposalAddCostShareRule)rule).processAddInstitutionalProposalCostShareBusinessRules(this);
    }

}