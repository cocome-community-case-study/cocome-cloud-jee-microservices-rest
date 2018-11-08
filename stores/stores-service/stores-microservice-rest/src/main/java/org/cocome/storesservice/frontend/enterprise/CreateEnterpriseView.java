package org.cocome.storesservice.frontend.enterprise;

import java.io.Serializable;


import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.cocome.storesservice.navigation.NavigationElementLabelResolver;

/**
 * Class to temporarily save new EnterprieInformation <br>
 * It is used while reating a new Enterprise
 * @author Niko Benkler
 * @author Robert Heinrich
 *
 */

@Named
@SessionScoped
public class CreateEnterpriseView implements Serializable{
	
	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 3639618966317691562L;
	private static final Logger LOG = Logger.getLogger(CreateEnterpriseView.class);
	private String newEnterpriseName;

	public String getNewEnterpriseName() {
		return newEnterpriseName;
	}

	public void setNewEnterpriseName(String newEnterpriseName) {
		this.newEnterpriseName = newEnterpriseName;
	}

}
