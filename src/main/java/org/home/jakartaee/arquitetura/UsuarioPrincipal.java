package org.home.jakartaee.arquitetura;

import org.home.jakartaee.administrativo.models.Usuario;

import jakarta.security.enterprise.CallerPrincipal;

public class UsuarioPrincipal extends CallerPrincipal {

    private static final long serialVersionUID = 1L;
    private final Usuario usuario;

    public UsuarioPrincipal(Usuario usuario) {
        super(usuario.getLogin());
        this.usuario = usuario;
    }

    public Usuario getUsuario() {
        return usuario;
    }

}
