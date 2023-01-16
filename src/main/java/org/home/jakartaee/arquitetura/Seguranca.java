package org.home.jakartaee.arquitetura;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.security.enterprise.SecurityContext;
import java.io.Serializable;
import java.util.Optional;
import org.home.jakartaee.administrativo.models.Usuario;

@Named
@ApplicationScoped
public class Seguranca implements Serializable {

    @Inject
    private SecurityContext securityContext;

    public Usuario getUsuarioLogado() {
        Optional<Usuario> usuarioOpcional = securityContext.getPrincipalsByType(UsuarioPrincipal.class)
                .stream().map(e -> e.getUsuario()).findAny();

        return usuarioOpcional.orElse(new Usuario());
    }

    public boolean isUsuarioEstaLogado() {
        return getUsuarioLogado().getId() != null;
    }
}
