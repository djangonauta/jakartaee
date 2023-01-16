package org.home.jakartaee.arquitetura;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.security.enterprise.SecurityContext;
import jakarta.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Optional;
import org.home.jakartaee.administrativo.models.Usuario;

@Named
@ApplicationScoped
public class Seguranca implements Serializable {

    @Inject
    private SecurityContext securityContext;

    @Inject
    private HttpServletRequest request;

    public Usuario getUsuarioLogado() {
        Optional<Usuario> usuarioOpcional = securityContext.getPrincipalsByType(UsuarioPrincipal.class)
                .stream().map(e -> e.getUsuario()).findAny();

        return usuarioOpcional.orElse(new Usuario()); // TODO: usuário anônimo
    }

    public boolean isUsuarioEstaLogado() {
        return temPermissao("**");
    }

    public boolean temPermissao(String permissao) {
        return request.isUserInRole(permissao);
    }

    public boolean temAlgumaPermissao(String... permissoes) {
        return Arrays.asList(permissoes).stream().anyMatch(perm -> request.isUserInRole(perm));
    }

    public boolean temTodasPermissoes(String... permissoes) {
        return Arrays.asList(permissoes).stream().allMatch(perm -> request.isUserInRole(perm));
    }

    public boolean temAcessoRecurso(String recurso) {
        return securityContext.hasAccessToWebResource(recurso, "GET");
    }
}
