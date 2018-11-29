package org.cocome.frontendservice.logindata;

/**
 * Normal Credential and compare method
 * @author Niko Benkler
 * @author Robert Heinrich
 *
 */
public class PlainCredential implements ICredential {
	
	private String password;
	
	public PlainCredential(String password) {
		this.password = password;
	}

	@Override
	public boolean isMatching(ICredential credentials) {
		return password.equals(credentials.getCredentialString());
	}

	@Override
	public String getCredentialString() {
		return password;
	}

}
