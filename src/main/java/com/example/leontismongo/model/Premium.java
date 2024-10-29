package com.example.leontismongo.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Premium {
    @Schema(description = "Possível usuário Premium",example = "true")
    private Boolean possivelPremium;
}
