package org.home.jakartaee.arquitetura;

import jakarta.security.enterprise.CallerPrincipal;
import org.home.jakartaee.administrativo.models.Usuario;

public class UsuarioPrincipal extends CallerPrincipal {

    private final Usuario usuario;

    public UsuarioPrincipal(Usuario usuario) {
        super(usuario.getLogin());
        this.usuario = usuario;
    }

    public Usuario getUsuario() {
        return usuario;
    }

}
