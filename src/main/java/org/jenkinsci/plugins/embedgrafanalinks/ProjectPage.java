package org.jenkinsci.plugins.embedgrafanalinks;

import hudson.model.Action;
import hudson.model.AbstractProject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ProjectPage implements Action {
private AbstractProject<?,?> job;
private List<LinkAction> links = new ArrayList<LinkAction>();
private String urlname;
private String displayname;
	private int i=0;
 public List<LinkAction> getLinks() { return links; }

 	public ProjectPage(AbstractProject<?,?> job,List<LinkAction> links) {
 		Map<String,String> valueMap=new HashMap<String, String>();
	this.job=job;
	this.links=links;
	
 	}
	public AbstractProject getBuild() {
		
		return this.job;
	}
	
	@Override
	public String getIconFileName() {
		
		return this.links.get(i).getIconFileName();
	}

	@Override
	public String getDisplayName() {
		
		displayname=links.get(i).getDisplayName();
		urlname=links.get(i).getUrlName();
		

		i++;
		if(i==links.size()){
			i=0;
		}
		System.out.println("GetDisplayName Executed");
	
				
		return this.displayname;
	}

	@Override
	public String getUrlName() {
		// TODO Auto-generated method stub
		return "grafana";
	}

	public String getIframeURL() 
	{
		
	
	return this.urlname;
	}
	

}
