package com.gabriel.spring_boot_project.business;

import com.gabriel.spring_boot_project.infrastructure.entity.Usuario;
import com.gabriel.spring_boot_project.infrastructure.exceptions.ConflictException;
import com.gabriel.spring_boot_project.infrastructure.exceptions.ResourceNotFoundException;
import com.gabriel.spring_boot_project.infrastructure.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class UsuarioService {

  private final UsuarioRepository usuarioRepository;
  private final PasswordEncoder passwordEncoder;

  public Usuario salvarUsuario(Usuario usuario) {
    try {
      emailExistente(usuario.getEmail());
      usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
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

  public Usuario buscaUsuarioPorEmail(String email) {
    return usuarioRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Email não encontrado " + email));
  }

  public void deletaUsuarioPorEmail(String email) {
    usuarioRepository.deleteByEmail(email);
  }

}
