package org.home.jakartaee.administrativo.views;

import jakarta.inject.Inject;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.home.jakartaee.arquitetura.Seguranca;

@WebFilter(urlPatterns = {"/usuarios/login.faces"})
public class LoginVerificacaoFilter implements Filter {

    @Inject
    private Seguranca seguranca;
    
    @Inject
    private ServletContext servletContext;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        if (seguranca.isUsuarioEstaLogado()) {
            ((HttpServletResponse) response).sendRedirect(servletContext.getInitParameter("application.HOME_PAGE"));

        } else {
            chain.doFilter(request, response);
        }
    }

}
