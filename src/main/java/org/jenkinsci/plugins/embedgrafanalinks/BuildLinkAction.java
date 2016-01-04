package org.jenkinsci.plugins.embedgrafanalinks;

import hudson.model.Action;
import hudson.model.AbstractBuild;

import java.util.ArrayList;
import java.util.List;

/**
 * Set the action for build 
 * @author Mayank Saini
 */
public class BuildLinkAction implements Action {
	private AbstractBuild build;
	private String urlname;
	private String displayname;
	private String icon;
	
	 private List<LinkAction> links = new ArrayList<LinkAction>();
	 
    public List<LinkAction> getLinks() { return links; }
	
	public BuildLinkAction(AbstractBuild build, List<LinkAction> links,String urlname,String displayname,String icon) {
		this.build = build;
		this.links=links;
		this.urlname=urlname;
		this.displayname=displayname;
		this.icon=icon;
	}

	public AbstractBuild getBuild() {
	
		return this.build;
	}
	
	public String getDisplayName() {
		
		return displayname;
	}

	public String getIconFileName() {
		return icon;
	}

	public String getUrlName() 
	{
			return "grafana";
	}
	
	
	public String getIframeURL() 
	{
		String displayBuildName=this.build.getDisplayName();
	
	return this.urlname+"/#/dashboard/db/"+displayBuildName;
	}
	
}
