package com.gabriel.spring_boot_project.infrastructure.repository;

import com.gabriel.spring_boot_project.infrastructure.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

  boolean existsByEmail(String email);
}
