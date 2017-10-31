package com.vogella.tycho.plugin1;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectNatureDescriptor;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;

import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.contentassist.CompletionProposal;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.jface.text.contentassist.IContextInformationValidator;

public class ProjectContentAssistProcessor implements IContentAssistProcessor {

    @Override
    public ICompletionProposal[] computeCompletionProposals(ITextViewer viewer, int offset) {
    	// TODO this is logic for .project file to complete on nature and project references. Replace with your language logic!
        String text = viewer.getDocument().get();
        String natureTag= "<nature>";
        String projectReferenceTag="<project>";
        IWorkspace workspace = ResourcesPlugin.getWorkspace();
        if (text.length() >= natureTag.length() && text.substring(offset - natureTag.length(), offset).equals(natureTag)) {
            IProjectNatureDescriptor[] natureDescriptors= workspace.getNatureDescriptors();
            ICompletionProposal[] proposals = new ICompletionProposal[natureDescriptors.length];
            for (int i= 0; i < natureDescriptors.length; i++) {
                IProjectNatureDescriptor descriptor= natureDescriptors[i];
                proposals[i] = new CompletionProposal(descriptor.getNatureId(), offset, 0, descriptor.getNatureId().length());
            }
            return proposals;
        }
        if (text.length() >= projectReferenceTag.length() && text.substring(offset - projectReferenceTag.length(), offset).equals(projectReferenceTag)) {
            IProject[] projects= workspace.getRoot().getProjects();
            ICompletionProposal[] proposals = new ICompletionProposal[projects.length];
            for (int i= 0; i < projects.length; i++) {
                proposals[i]=new CompletionProposal(projects[i].getName(), offset, 0, projects[i].getName().length());
            }
            return proposals;
        }
        return new ICompletionProposal[0];
    }

    @Override
    public IContextInformation[] computeContextInformation(ITextViewer viewer, int offset) {
        return null;
    }

    @Override
    public char[] getCompletionProposalAutoActivationCharacters() {
        return new char[] { '"' }; //NON-NLS-1
    }

    @Override
    public char[] getContextInformationAutoActivationCharacters() {
        return null;
    }

    @Override
    public String getErrorMessage() {
        return null;
    }

    @Override
    public IContextInformationValidator getContextInformationValidator() {
        return null;
    }

}