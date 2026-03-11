package com.project.park_api.service;

import com.project.park_api.entity.Usuario;
import com.project.park_api.exception.EntityNotFoundException;
import com.project.park_api.exception.PasswordInvalidException;
import com.project.park_api.exception.UserNameUniqueViolationException;
import com.project.park_api.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Transactional
    public Usuario salvar(Usuario usuario) {
        try {
            return usuarioRepository.save(usuario);
        } catch (DataIntegrityViolationException ex) {
            throw new UserNameUniqueViolationException(String.format("Username %s já cadastrado.", usuario.getUsername()));
        }
    }

    @Transactional(readOnly = true)
    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("Usuário id = %s não encontrado", id)));
    }

    @Transactional
    public Usuario editarSenha(Long id, String senhaAtual, String novaSenha, String confirmaSenha) throws PasswordInvalidException {
        Usuario user = buscarPorId(id);
        if(!novaSenha.equals(confirmaSenha)) {
            throw new PasswordInvalidException("As senhas não conferem.");
        }

        if(user.getPassword().equals(novaSenha)){
            throw new PasswordInvalidException("A nova senha deve ser diferente da atual.");
        }

        user.setPassword(novaSenha);
        return user;
    }

    @Transactional(readOnly = true)
    public List<Usuario> buscarTodos() {
        return usuarioRepository.findAll();
    }
}
