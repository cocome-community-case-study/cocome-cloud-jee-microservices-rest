package org.cocome.frontendservice.logindata;

public interface ICredentialFactory {
	ICredential createPlainPassword(String password);
}
