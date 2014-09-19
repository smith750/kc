package org.kuali.coeus.propdev.impl.auth;

import org.kuali.coeus.common.framework.auth.KcKradTransactionalDocumentViewAuthorizerBase;
import org.kuali.coeus.propdev.impl.core.ProposalDevelopmentConstants;
import org.kuali.coeus.propdev.impl.core.ProposalDevelopmentDocument;
import org.kuali.coeus.propdev.impl.core.ProposalDevelopmentDocumentForm;
import org.kuali.coeus.propdev.impl.person.ProposalPerson;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.krad.document.Document;
import org.kuali.rice.krad.uif.UifConstants;
import org.kuali.rice.krad.uif.container.Group;
import org.kuali.rice.krad.uif.view.View;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.rice.krad.web.form.DocumentFormBase;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service("proposalDevelopmentDocumentViewAuthorizer")
@Scope("prototype")
public class ProposalDevelopmentDocumentViewAuthorizer extends KcKradTransactionalDocumentViewAuthorizerBase {

    @Override
    public Set<String> getEditModes(View view, ViewModel model, Person user, Set<String> editModes) {
        Document document = ((DocumentFormBase) model).getDocument();
        Set currentEditModes = ((ProposalDevelopmentDocumentAuthorizer)getDocumentAuthorizer()).getEditModes(document,user,new HashSet<String>());
        return super.getEditModes(view, model, user, currentEditModes);
    }

    @Override
    public boolean canViewGroup(View view, ViewModel model, Group group, String groupId, Person user) {
        boolean success = super.canViewGroup(view, model, group, groupId, user);
        if (group.getId().contains(ProposalDevelopmentConstants.KradConstants.PERSONNEL_QUESTIONNAIRE)) {
            ProposalDevelopmentDocumentForm form = (ProposalDevelopmentDocumentForm) model;
            ProposalDevelopmentDocument document = form.getProposalDevelopmentDocument();
            int index = getIndexFromCollectionGroupId(ProposalDevelopmentConstants.KradConstants.PERSONNEL_QUESTIONNAIRE, groupId);
            ProposalPerson proposalPerson = document.getDevelopmentProposal().getProposalPersons().get(index);

            success &= ((ProposalDevelopmentDocumentAuthorizer)getDocumentAuthorizer()).hasCertificationPermissions(document, proposalPerson, user);
        }

        return success;
    }

    /**
     * To be used with group collection items.
     * Gets the index of the group that is passed in.
     *
     * @param prefix the predefined id property for the group.  Defined in the view p:id="someGroupIdentifier"
     * @param groupId is in format someGroupIdentifier_line***, where *** is an int 0..n
     * @return parsed int value
     */
    public int getIndexFromCollectionGroupId(String prefix, String groupId){
        String indexStr = groupId.substring(prefix.length() + UifConstants.IdSuffixes.LINE.length());
        return Integer.parseInt(indexStr);
    }
}