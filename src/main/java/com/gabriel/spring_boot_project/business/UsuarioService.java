package com.gabriel.spring_boot_project.business;

import com.gabriel.spring_boot_project.infrastructure.entity.Usuario;
import com.gabriel.spring_boot_project.infrastructure.exceptions.ConflictException;
import com.gabriel.spring_boot_project.infrastructure.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class UsuarioService {

  private final UsuarioRepository usuarioRepository;

  public Usuario salvarUsuario(Usuario usuario) {
    try {
      emailExistente(usuario.getEmail());
      return usuarioRepository.save(usuario);
    } catch (ConflictException e) {
      throw new ConflictException("Email já cadastrado", e.getCause());
    }
  }

  public void emailExistente(String email) {
    try {
      boolean existente =  verificaEmailExistente(email);
      if (existente) {
        throw new ConflictException("Email já cadastrado" + email);
      }
    } catch (ConflictException e) {
        throw new ConflictException("Email já cadastrado" + e.getCause());
    }
  }

  public boolean verificaEmailExistente(String email) {
    return usuarioRepository.existsByEmail(email);
  }


}
