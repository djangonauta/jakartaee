package org.home.jakartaee.arquitetura;

import java.io.Serializable;
import java.util.Optional;
import java.util.stream.Stream;

import org.home.jakartaee.administrativo.models.Usuario;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.security.enterprise.SecurityContext;
import jakarta.servlet.http.HttpServletRequest;

@Named
@ApplicationScoped
public class Seguranca implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private SecurityContext securityContext;

    @Inject
    private HttpServletRequest request;

    public Usuario getUsuarioLogado() {
        Optional<Usuario> usuarioOpcional = securityContext.getPrincipalsByType(UsuarioPrincipal.class).stream()
                .map(e -> e.getUsuario()).findAny();

        return usuarioOpcional.orElse(new Usuario()); // TODO: usuário anônimo
    }

    public boolean isUsuarioEstaLogado() {
        return temPermissao("**");
    }

    public boolean temPermissao(String permissao) {
        return request.isUserInRole(permissao);
    }

    public boolean temAlgumaPermissao(String... permissoes) {
        return Stream.of(permissoes).anyMatch(perm -> request.isUserInRole(perm));
    }

    public boolean temTodasPermissoes(String... permissoes) {
        return Stream.of(permissoes).allMatch(perm -> request.isUserInRole(perm));
    }

    public boolean temAcessoRecurso(String recurso) {
        return securityContext.hasAccessToWebResource(recurso, "GET");
    }
}
