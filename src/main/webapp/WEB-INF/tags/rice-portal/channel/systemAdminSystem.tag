<%--
 Copyright 2006-2009 The Kuali Foundation
 
 Licensed under the Educational Community License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at
 
 http://www.osedu.org/licenses/ECL-2.0
 
 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
--%>
<%@ include file="/WEB-INF/jsp/kraTldHeader.jsp"%>

<channel:portalChannelTop channelTitle="System" />
<div class="body">
  <ul class="chan">
    <li>Employee Status</li>
    <li>Employee Type</li>
    <li>Message of the Day</li>
    <li><portal:portalLink displayTitle="true" title="Person Extended Attributes" url="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.kra.bo.KcPersonExtendedAttributes&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true&docFormKey=88888888" /></li>
    <li>System Options</li>
  </ul>
</div>
<channel:portalChannelBottom />
