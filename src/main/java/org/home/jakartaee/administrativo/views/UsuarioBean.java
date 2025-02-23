package org.home.jakartaee.administrativo.views;

import java.io.Serializable;

import org.apache.commons.codec.binary.StringUtils;
import org.home.jakartaee.administrativo.models.Usuario;
import org.home.jakartaee.administrativo.services.UsuarioService;

import jakarta.enterprise.inject.Model;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Model
public class UsuarioBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private UsuarioService usuarioService;

    @Inject
    private FacesContext facesContext;

    private Usuario usuario;

    @NotBlank
    @Size(
        min = 8,
        max = 32
    )
    private String senha;

    @NotBlank
    @Size(
        min = 8,
        max = 32
    )
    private String confirmarSenha;

    private String mensagem;

    public UsuarioBean() {
        iniciar();
    }

    private void iniciar() {
        usuario = new Usuario();
    }

    public void cadastrarUsuario() {
        if (isDadosEstaoCorretos()) {
            usuarioService.cadastrarUsuario(usuario);
            mensagem = "Usuário cadastrado com sucesso";
            iniciar();
        }
    }

    private boolean isDadosEstaoCorretos() {
        boolean loginExiste = usuarioService.isLoginExiste(usuario);
        boolean loginNaoExiste = !loginExiste;

        boolean emailExiste = usuarioService.isEmailExiste(usuario);
        boolean emailNaoExiste = !emailExiste;

        boolean senhasCombinam = StringUtils.equals(senha, confirmarSenha);
        boolean senhasNaoCombinam = !senhasCombinam;

        if (loginExiste) {
            facesContext.addMessage("form:login", new FacesMessage("Já existe um usuário com esse login"));
        }

        if (emailExiste) {
            facesContext.addMessage("form:email", new FacesMessage("Já existe um usuário com esse email"));
        }

        if (senhasNaoCombinam) {
            facesContext.addMessage("form:confirmarSenha", new FacesMessage("Senhas não combinam"));
        }

        return loginNaoExiste && emailNaoExiste && senhasCombinam;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getConfirmarSenha() {
        return confirmarSenha;
    }

    public void setConfirmarSenha(String confirmarSenha) {
        this.confirmarSenha = confirmarSenha;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

}
