package com.gabriel.spring_boot_project.infrastructure.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "endereco")
public class Endereco {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(name = "logradouro")
  private String logradouro;
  @Column(name = "numero")
  private String numero;
  @Column(name = "complemento",  length = 10)
  private String complemento;
  @Column(name = "cidade",   length = 150)
  private String cidade;
  @Column(name = "esatdo", length = 2)
  private String estado;
  @Column(name = "cep", length = 9)
  private String cep;
}
