package org.home.jakartaee.administrativo.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.codec.digest.DigestUtils;

@Entity
@Table(schema = "administrativo", name = "usuario")
public class Usuario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuario_sequence")
    @SequenceGenerator(name = "usuario_sequence", schema = "administrativo", sequenceName = "usuario_sequence")
    @Column(name = "id_usuario")
    private Long id;

    @ManyToMany
    @JoinTable(schema = "administrativo", name = "usuario_grupo",
            joinColumns = @JoinColumn(name = "id_usuario"),
            inverseJoinColumns = @JoinColumn(name = "nome_grupo")
    )
    Set<Grupo> grupos;

    @ManyToMany
    @JoinTable(schema = "administrativo", name = "usuario_permissao",
            joinColumns = @JoinColumn(name = "id_usuario"),
            inverseJoinColumns = @JoinColumn(name = "codigo_permissao")
    )
    Set<Permissao> permissoes;

    @NotBlank
    @Size(min = 3)
    private String login;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Size(min = 8)
    private String senha;

    public Usuario() {
        iniciar();
    }

    private void iniciar() {
        permissoes = new HashSet<>();
        grupos = new HashSet<>();
    }

    public void configurarSenha(String senha) {
        this.senha = DigestUtils.sha256Hex(senha);
    }

    @Transient
    public Set<String> getPermissoesTexto() {
        for (Grupo grupo : grupos) {
            permissoes.addAll(grupo.getPermissoes());
        }

        return permissoes.stream().map(p -> p.getCodigo().toString()).collect(Collectors.toSet());
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 83 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Usuario other = (Usuario) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "Usuario[login=" + login + "][email=" + email + "]";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Permissao> getPermissoes() {
        return permissoes;
    }

    public void setPermissoes(Set<Permissao> permissoes) {
        this.permissoes = permissoes;
    }

    public Set<Grupo> getGrupos() {
        return grupos;
    }

    public void setGrupos(Set<Grupo> grupos) {
        this.grupos = grupos;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

}
