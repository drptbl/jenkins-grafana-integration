package org.jenkinsci.plugins.embedgrafanalinks;

import hudson.Extension;
import hudson.Launcher;
import hudson.model.BuildListener;
import hudson.model.AbstractBuild;
import hudson.model.AbstractProject;
import hudson.tasks.BuildWrapper;
import hudson.tasks.BuildWrapperDescriptor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.StaplerRequest;

/**
 * Set the link for build page
 * @author Mayank Saini
 */
public class BuildLinkActionWrapper extends BuildWrapper {
	

	 private List<LinkAction> links = new ArrayList<LinkAction>();
	   @DataBoundConstructor
	    public BuildLinkActionWrapper(List<LinkAction> links) {
	        this.links = links;
	    }

	    public List<LinkAction> getLinks() { return links; }


	@Override
	public Environment setUp(AbstractBuild build, Launcher launcher,BuildListener listener) throws IOException, InterruptedException {

for(int i=0;i<links.size();i++)
{
		final BuildLinkAction action = new BuildLinkAction(build,  this.links,this.links.get(i).getUrlName(),this.links.get(i).getDisplayName(),this.links.get(i).getIconFileName());
		build.addAction(action);
}		

	
		return new Environment() {
			@Override
			public boolean tearDown(AbstractBuild build, BuildListener listener)
					throws IOException, InterruptedException {
				return true;
			}
		};
	}

	@Extension
	public static final class DescriptorImpl extends BuildWrapperDescriptor  {
		@Override
		public String getDisplayName() {
			return "Show Additional Links during build";
		}

		@Override
		public boolean isApplicable(AbstractProject<?, ?> item) {
			return true;
		}
		  @Override
	        public BuildLinkActionWrapper newInstance(StaplerRequest req, JSONObject formData) throws FormException {
	            return formData.has("grafana-links")? req.bindJSON(BuildLinkActionWrapper.class, formData.getJSONObject("grafana-links")): null;
	        }
		
	}
}
