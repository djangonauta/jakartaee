package org.home.jakartaee.arquitetura;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.faces.annotation.FacesConfig;
import jakarta.security.enterprise.authentication.mechanism.http.CustomFormAuthenticationMechanismDefinition;
import jakarta.security.enterprise.authentication.mechanism.http.LoginToContinue;
import java.io.Serializable;

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
    
}
