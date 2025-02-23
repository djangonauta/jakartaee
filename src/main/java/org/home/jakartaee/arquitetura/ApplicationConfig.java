package org.home.jakartaee.arquitetura;

import java.io.Serializable;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.faces.annotation.FacesConfig;
import jakarta.security.enterprise.authentication.mechanism.http.CustomFormAuthenticationMechanismDefinition;
import jakarta.security.enterprise.authentication.mechanism.http.LoginToContinue;

@CustomFormAuthenticationMechanismDefinition(
	loginToContinue = @LoginToContinue(
		loginPage = "/usuarios/login.faces",
		errorPage = "",
		useForwardToLogin = false
	)
)
@FacesConfig
@ApplicationScoped
public class ApplicationConfig implements Serializable {

	private static final long serialVersionUID = 1L;
    
}
