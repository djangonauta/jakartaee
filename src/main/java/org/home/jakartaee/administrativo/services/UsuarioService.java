package org.home.jakartaee.administrativo.services;

import java.util.Optional;

import org.home.jakartaee.administrativo.models.Usuario;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;

@Stateless
public class UsuarioService {

    @PersistenceContext
    private EntityManager em;

    @Inject
    private Pbkdf2PasswordHash passwordHash;

    public void cadastrarUsuario(Usuario usuario) {
        usuario.setSenha(passwordHash.generate(usuario.getSenha().toCharArray()));
        em.persist(usuario);
    }

    public boolean isLoginExiste(Usuario usuario) {
        Query query = em.createQuery("select count(u) from Usuario u where u.login = :login");
        query.setParameter("login", usuario.getLogin());

        return (Long) query.getSingleResult() > 0;
    }

    public boolean isEmailExiste(Usuario usuario) {
        Query query = em.createQuery("select count(u) from Usuario u where u.email = :email");
        query.setParameter("email", usuario.getEmail());

        return (Long) query.getSingleResult() > 0;
    }

    public Optional<Usuario> buscarPorLoginSenha(String login, String senha) {
        StringBuilder jpql = new StringBuilder("select u from Usuario u ");
        jpql.append("left join fetch u.permissoes perms ");
        jpql.append("left join fetch u.grupos grps ");
        jpql.append("left join fetch grps.permissoes grperms ");
        jpql.append("where u.login = :login");

        TypedQuery<Usuario> query = em.createQuery(jpql.toString(), Usuario.class);
        query.setParameter("login", login);

        Usuario usuario;
        try {
            usuario = query.getSingleResult();
            boolean senhaNaoCorresponde = !passwordHash.verify(senha.toCharArray(), usuario.getSenha());
            if (senhaNaoCorresponde) {
                usuario = null;
            }

        } catch (NoResultException exception) {
            usuario = null;
        }

        return Optional.ofNullable(usuario);
    }
}
