package org.cocome.frontendservice.resolver;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.cocome.frontendservice.login.Login;





@ManagedBean
@ViewScoped
public class MicroserviceRedirecter implements Serializable {

     /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String currentDestination;
    
	@Inject
	Login login;
     
     
	@PostConstruct
    public void init() {
       //setPage("http://localhost:8580/frontendservice/faces/ajax/content/01name.xhtml?param1=" + login.getUserName()); //  Default include.
    	this.currentDestination = ("http://localhost:8580/frontendservice/faces/ajax/content/01name.xhtml");
    	System.out.println(currentDestination);
	}
    
    public void store(){
     System.out.println(login.getUserName());
   	 this.currentDestination = "http://localhost:8880/storesmicroservice/faces/main.xhtml?param1=" + login.getUserName();
   	 
    }
    
    public String enterprise(){
    	//this.page = "/ajax/content/01name.xhtml";
    	//this.page = "http://localhost:8580/frontendservice/faces/ajax/content/01name.xhtml?param1=" + login.getUserName();
    	this.currentDestination = "http://localhost:8580/frontendservice/faces/enterprise/show_enterprises.xhtml";
    	System.out.println(currentDestination);
    	return this.currentDestination;
    }
    

	public String getDestination() {
		return currentDestination;
	}

	public void setDestination(String page) {
		this.currentDestination = page;
	}
    
 }