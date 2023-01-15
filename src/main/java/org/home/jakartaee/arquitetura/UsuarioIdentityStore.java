package org.home.jakartaee.arquitetura;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.security.enterprise.credential.Credential;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.security.enterprise.identitystore.IdentityStore;
import java.util.Optional;
import org.home.jakartaee.administrativo.models.Usuario;
import org.home.jakartaee.administrativo.services.UsuarioService;

@ApplicationScoped
public class UsuarioIdentityStore implements IdentityStore {

    @Inject
    private UsuarioService usuarioService;

    @Override
    public CredentialValidationResult validate(Credential credential) {
        UsernamePasswordCredential credenciais = (UsernamePasswordCredential) credential;
        String login = credenciais.getCaller();
        String senha = credenciais.getPasswordAsString();

        Optional<Usuario> resultado = usuarioService.buscarPorLoginSenha(login, senha);
        if (resultado.isPresent()) {
            Usuario usuario = resultado.get();
            return new CredentialValidationResult(
                    usuario.getLogin(),
                    usuario.getPermissoesTexto()
            );
        }

        return CredentialValidationResult.INVALID_RESULT;
    }
}
