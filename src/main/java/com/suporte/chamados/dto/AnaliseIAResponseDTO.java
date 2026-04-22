package com.suporte.chamados.dto;

import com.suporte.chamados.enums.PrioridadeChamado;
import com.suporte.chamados.enums.SetorChamado;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class AnaliseIAResponseDTO {

    private Long chamadoId;
    private PrioridadeChamado prioridadeSugerida;
    private SetorChamado setorSugerido;
    private String resumo;
    private LocalDateTime analisadoEm;
}