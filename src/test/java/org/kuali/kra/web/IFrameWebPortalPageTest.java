/*
 * Copyright 2005-2010 The Kuali Foundation
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
package org.kuali.kra.web;

import org.junit.Before;
import org.junit.Test;
import org.kuali.kra.KraWebTestBase;

import com.gargoylesoftware.htmlunit.html.FrameWindow;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * Tests the KRA IFrame within Portal Links
 *
 * @author Kuali Research Administration Team (kualidev@oncourse.iu.edu)
 */
public class IFrameWebPortalPageTest extends KraWebTestBase {
    
    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        setPortalPage(clickOn(getPortalPage(), "Maintenance"));
    }
    
    /**
     * Verify that the when clicked on Sponsor link on portal; it opens the Sponsor Lookup page within an iFrame
     * @throws Exception
     */
    @Test
    public void testIFrameOnPortalPage() throws Exception {
        HtmlPage portalPage = getPortalPage();
        HtmlPage actionListPage = clickOn(portalPage, "Sponsor");
        
        //gets the outer page since thats what this test expects
        actionListPage = (HtmlPage) actionListPage.getEnclosingWindow().getParentWindow().getEnclosedPage();
        FrameWindow frame = actionListPage.getFrameByName("iframeportlet");
        assertContains((HtmlPage)frame.getEnclosedPage(), "Sponsor Lookup");
        assertTrue(checkPageHasIFrame(actionListPage));   
    }
    
    /**
     * returns true if the page contains an iFrame; otherwise returns false
     * @param page the HTML web page.
     */
    public boolean checkPageHasIFrame(HtmlPage page) {
        return getElement(page,"iframeportlet","","E-Doc") != null;
    }

}
