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
package org.kuali.coeus.common.committee.impl.keyvalue;

import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;

import java.util.ArrayList;
import java.util.List;

public class MonthsWeek extends UifKeyValuesFinderBase {

    public static final String FIRST =  "first";
    
    public static final String SECOND = "second";
    
    public static final String THIRD = "third";
    
    public static final String FOURTH = "fourth";
    
    public static final String LAST = "last";
    
    public MonthsWeek() {
    }

    /**
     * Creates and return List of week of month.
     */
    @Override
    public List<KeyValue> getKeyValues() {
        List<KeyValue> keyValues = new ArrayList<KeyValue>();
        keyValues.add(new ConcreteKeyValue(FIRST, FIRST));
        keyValues.add(new ConcreteKeyValue(SECOND, SECOND));
        keyValues.add(new ConcreteKeyValue(THIRD, THIRD));
        keyValues.add(new ConcreteKeyValue(FOURTH, FOURTH));
        keyValues.add(new ConcreteKeyValue(LAST, LAST));
        return keyValues;
    }

}