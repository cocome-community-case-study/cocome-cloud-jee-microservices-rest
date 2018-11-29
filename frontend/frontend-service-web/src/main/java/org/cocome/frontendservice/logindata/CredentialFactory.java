package org.cocome.frontendservice.logindata;

import javax.enterprise.context.RequestScoped;

/**
 * Create new Passwort
 * @author Niko Benkler
 * @author Robert Heinrich
 *
 */
@RequestScoped
public class CredentialFactory implements ICredentialFactory {
	
	@Override
	public ICredential createPlainPassword(String password) {
		return new PlainCredential(password);
	}

}
