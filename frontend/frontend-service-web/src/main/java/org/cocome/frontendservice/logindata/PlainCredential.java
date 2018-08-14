package org.cocome.frontendservice.logindata;

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
