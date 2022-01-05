package com.example.carros.domain;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@Data
@Entity
public class Carro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //A JPA vai fazer o auto incremento desse id
    private Long id;
    private String nome;
    private String tipo;
}
