package org.home.jakartaee.administrativo.services;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import java.util.Optional;
import org.apache.commons.codec.digest.DigestUtils;
import org.home.jakartaee.administrativo.models.Usuario;

@Stateless
public class UsuarioService {

    @PersistenceContext
    private EntityManager em;

    public void cadastrarUsuario(Usuario usuario) {
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
        jpql.append("where u.login = :login and u.senha = :senha ");

        TypedQuery<Usuario> query = em.createQuery(jpql.toString(), Usuario.class);
        query.setParameter("login", login);
        query.setParameter("senha", DigestUtils.sha256Hex(senha));

        Usuario usuario = null;
        try {
            usuario = query.getSingleResult();

        } catch (NoResultException exception) {
            System.err.println(exception);
        }

        return Optional.ofNullable(usuario);
    }
}
