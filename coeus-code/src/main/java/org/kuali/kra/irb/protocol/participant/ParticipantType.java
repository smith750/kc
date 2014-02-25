/*
 * Copyright 2005-2013 The Kuali Foundation
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
package org.kuali.kra.irb.protocol.participant;

import org.kuali.coeus.sys.framework.model.KcPersistableBusinessObjectBase;

/**
 * 
 * This class implements the participant type object.
 * 
 * @author Kuali Research Administration Team (kc.dev@kuali.org)
 */
public class ParticipantType extends KcPersistableBusinessObjectBase {

    private String participantTypeCode;

    private String description;

    public ParticipantType() {
    }

    public String getParticipantTypeCode() {
        return participantTypeCode;
    }

    public void setParticipantTypeCode(String participantTypeCode) {
        this.participantTypeCode = participantTypeCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}