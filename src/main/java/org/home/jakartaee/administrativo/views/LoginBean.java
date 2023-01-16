package org.home.jakartaee.administrativo.views;

import jakarta.enterprise.inject.Model;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.security.enterprise.AuthenticationStatus;
import jakarta.security.enterprise.SecurityContext;
import jakarta.security.enterprise.authentication.mechanism.http.AuthenticationParameters;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import org.home.jakartaee.administrativo.models.Usuario;

@Model
public class LoginBean implements Serializable {

    @Inject
    private HttpServletRequest request;

    @Inject
    private SecurityContext securityContext;

    @Inject
    private ExternalContext externalContext;

    @Inject
    private FacesContext facesContext;

    private String redirect;

    private Usuario usuario;

    public LoginBean() {
        iniciar();
    }

    private void iniciar() {
        usuario = new Usuario();
    }

    public void login() throws IOException {
        switch (obterStatusLogin()) {
            case SEND_CONTINUE ->
                facesContext.responseComplete();

            case SUCCESS -> {
                externalContext.redirect(redirect);
            }

            case SEND_FAILURE ->
                facesContext.addMessage(null, new FacesMessage("Login ou Senha inválidos"));

            case NOT_DONE -> {
            }
        }
    }

    private AuthenticationStatus obterStatusLogin() {
        UsernamePasswordCredential credenciais = new UsernamePasswordCredential(
                usuario.getLogin(),
                usuario.getSenha()
        );
        AuthenticationParameters parametros = AuthenticationParameters.withParams()
                .credential(credenciais);

        return securityContext.authenticate(getRequest(), getResponse(), parametros);
    }

    public String logout() throws ServletException {
        request.getSession().invalidate();
        request.logout();
        return "/index.faces?faces-redirect=true";
    }

    public HttpServletResponse getResponse() {
        return (HttpServletResponse) getExternalContext().getResponse();
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public SecurityContext getSecurityContext() {
        return securityContext;
    }

    public void setSecurityContext(SecurityContext securityContext) {
        this.securityContext = securityContext;
    }

    public ExternalContext getExternalContext() {
        return externalContext;
    }

    public void setExternalContext(ExternalContext externalContext) {
        this.externalContext = externalContext;
    }

    public FacesContext getFacesContext() {
        return facesContext;
    }

    public void setFacesContext(FacesContext facesContext) {
        this.facesContext = facesContext;
    }

    public String getRedirect() {
        if (redirect == null) {
            redirect = request.getRequestURI();
        }

        return redirect;
    }

    public void setRedirect(String redirect) {
        this.redirect = redirect;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

}
