package com.gabriel.spring_boot_project.infrastructure.repository;

import com.gabriel.spring_boot_project.infrastructure.entity.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
}
