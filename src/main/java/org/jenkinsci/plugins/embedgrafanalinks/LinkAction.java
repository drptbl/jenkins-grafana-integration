package org.jenkinsci.plugins.embedgrafanalinks;

import hudson.Extension;
import hudson.model.AbstractDescribableImpl;
import hudson.model.Action;
import hudson.model.Descriptor;

import org.kohsuke.stapler.DataBoundConstructor;

public final class LinkAction extends AbstractDescribableImpl<LinkAction> implements Action {
    private String url, text, icon;

    @DataBoundConstructor
    public LinkAction(String urlName, String displayName, String iconFileName) {
	this.url = urlName;
	this.text = displayName;
	this.icon = iconFileName;
    }

    public String getUrlName() { return url; }
    public String getDisplayName() { return text; }
    public String getIconFileName() { return icon; }
   
    @Extension
    public static class DescriptorImpl extends Descriptor<LinkAction> {
        @Override
        public String getDisplayName() {
            return "";
        }
    }
}
