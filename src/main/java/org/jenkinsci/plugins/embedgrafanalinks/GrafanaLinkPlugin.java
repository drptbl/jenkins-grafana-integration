package org.jenkinsci.plugins.embedgrafanalinks;

import hudson.FilePath;
import hudson.Plugin;
import hudson.model.Descriptor.FormException;
import hudson.model.Hudson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;

import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;
import org.jenkinsci.plugins.embedgrafanalinks.Messages;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;

/**
 * Add side link in dashboard panel
 * @author Mayank Saini
 */
public class GrafanaLinkPlugin extends Plugin {
    private List<LinkAction> links = new ArrayList<LinkAction>();

    // From older versions
     private transient String url, text, icon;

    @Override public void start() throws Exception {
	load();
	Hudson.getInstance().getActions().addAll(links);
    }

    public List<LinkAction> getLinks() { return links; }

    @Override public void configure(StaplerRequest req, JSONObject formData)
	    throws IOException, ServletException, FormException {
	Hudson.getInstance().getActions().removeAll(links);
	links.clear();
	links.addAll(req.bindJSONToList(LinkAction.class, formData.get("links")));
	save();
	Hudson.getInstance().getActions().addAll(links);
    }

    private Object readResolve() {
	// Upgrade config from older version
	if (url != null && url.length() > 0) {
	    links.add(new LinkAction(url, text, icon));
	}
	return this;
    }

    /**
     * Receive file upload from startUpload.jelly.
     * File is placed in $JENKINS_HOME/userContent directory.
     */
    public void doUpload(StaplerRequest req, StaplerResponse rsp)
            throws IOException, ServletException, InterruptedException {
        Hudson hudson = Hudson.getInstance();
        hudson.checkPermission(Hudson.ADMINISTER);
        FileItem file = req.getFileItem("linkimage.file");
        String error = null, filename = null;
        if (file == null || file.getName().isEmpty())
            error = Messages.NoFile();
        else {
            filename = "userContent/"
                    // Sanitize given filename:
                    + file.getName().replaceFirst(".*/", "").replaceAll("[^\\w.,;:()#@!=+-]", "_");
            FilePath imageFile = hudson.getRootPath().child(filename);
            if (imageFile.exists())
                error = Messages.DupName();
            else {
                imageFile.copyFrom(file.getInputStream());
                imageFile.chmod(0644);
            }
        }
        rsp.setContentType("text/html");
        rsp.getWriter().println(
                (error != null ? error : Messages.Uploaded("<tt>/" + filename + "</tt>"))
                + " <a href=\"javascript:history.back()\">" + Messages.Back() + "</a>");
    }
}
