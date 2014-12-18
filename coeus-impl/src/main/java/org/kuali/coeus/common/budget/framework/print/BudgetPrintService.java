/*
 * Copyright 2005-2014 The Kuali Foundation
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
package org.kuali.coeus.common.budget.framework.print;

import java.util.List;

import org.kuali.coeus.common.budget.framework.core.Budget;
import org.kuali.coeus.common.budget.impl.print.BudgetPrintForm;
import org.kuali.coeus.common.framework.print.AttachmentDataSource;

public interface BudgetPrintService {
	
	/**
	 * Populates the various forms that are part of Budget on UI
	 * @param budget
	 */
    public void populateBudgetPrintForms(Budget budget);
    
    /**
     * Generates the report specified and returns the bytes
     * 
     * @param budget {@link Budget}
     * @param selectedBudgetPrintFormId form to print
     * @return {@link AttachmentDataSource} bytes of the generated form
     */
    public AttachmentDataSource readBudgetPrintStream(Budget budget, String selectedBudgetPrintFormId);
    
    /**
     * Generates the selected reports and returns the bytes
     * 
     * @param budget {@link Budget}
     * @param budgetPrintForms {@link BudgetPrintForm} selected forms to print
     * @param reportName report to be generated
     * @return {@link AttachmentDataSource} bytes of the generated form
     */
    public AttachmentDataSource readBudgetSelectedPrintStreams(Budget budget, List<BudgetPrintForm> budgetPrintForms, String reportName);
}
