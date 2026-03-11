package com.project.park_api.web.controller;

import com.project.park_api.entity.Usuario;
import com.project.park_api.exception.PasswordInvalidException;
import com.project.park_api.service.UsuarioService;
import com.project.park_api.web.records.UsuarioCreate;
import com.project.park_api.web.records.UsuarioResponse;
import com.project.park_api.web.records.UsuarioSenha;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<UsuarioResponse> create(@Valid @RequestBody UsuarioCreate usuarioCreate){
        Usuario user = usuarioCreate.toEntity();
        Usuario userSaved = usuarioService.salvar(user);
        UsuarioResponse userResponse = UsuarioResponse.fromEntity(userSaved);

        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponse> getById(@PathVariable Long id){
        Usuario user = usuarioService.buscarPorId(id);
        UsuarioResponse userResponse = UsuarioResponse.fromEntity(user);

        return ResponseEntity.ok(userResponse);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UsuarioResponse> updatePassword(@Valid @PathVariable Long id, @RequestBody UsuarioSenha usuarioSenha) throws PasswordInvalidException {
        Usuario senhaAtualizada = usuarioService.editarSenha(id, usuarioSenha.senha(), usuarioSenha.novaSenha(), usuarioSenha.confirmaSenha());

        return ResponseEntity.noContent().build();
    }

    @GetMapping()
    public ResponseEntity<List<UsuarioResponse>> getAll(){
        List<Usuario> users = usuarioService.buscarTodos();
        List<UsuarioResponse> responseList = users.stream().map(UsuarioResponse::fromEntity).toList();

        return ResponseEntity.ok(responseList);
    }
}
