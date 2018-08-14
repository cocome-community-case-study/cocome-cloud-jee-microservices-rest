package org.cocome.frontendservice.logindata;

public interface ICredential {
	public boolean isMatching(ICredential credentials);
	
	public String getCredentialString();
}
