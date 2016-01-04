package org.jenkinsci.plugins.embedgrafanalinks;

import hudson.Extension;
import hudson.model.Action;
import hudson.model.JobProperty;
import hudson.model.JobPropertyDescriptor;
import hudson.model.AbstractProject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import net.sf.json.JSONObject;

import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.StaplerRequest;

/**
 * Set  the link for Project Page
 * @author Mayank Saini
 */
public class ProjectLinksAction extends JobProperty<AbstractProject<?,?>> {
    private List<LinkAction> links = new ArrayList<LinkAction>();
    

    @DataBoundConstructor
    public ProjectLinksAction(List<LinkAction> links) {
        this.links = links;
    }

    public List<LinkAction> getLinks() { return links; }

    @Override
    public Collection<? extends Action> getJobActions(AbstractProject<?,?> job) {
        if(links == null)
            return new ArrayList<LinkAction>();
        
 return Collections.nCopies(links.size(),new ProjectPage(job,links));
       
       
    }
  

    @Extension
    public static class DescriptorImpl extends JobPropertyDescriptor {
        @Override
        public String getDisplayName() {
            return "Sidebar Links";
        }

        @Override
        public ProjectLinksAction newInstance(StaplerRequest req, JSONObject formData) throws FormException {
            return formData.has("grafana-links")
                    ? req.bindJSON(ProjectLinksAction.class, formData.getJSONObject("grafana-links"))
                    : null;
        }
    }
}
