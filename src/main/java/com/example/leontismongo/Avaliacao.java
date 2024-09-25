package com.example.leontismongo;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class Avaliacao {

    private String obraId;
    private double nota;
    private LocalDateTime dataAvaliacao;

}