package com.suporte.chamados.dto;

import com.suporte.chamados.entity.Chamado;
import com.suporte.chamados.enums.PrioridadeChamado;
import com.suporte.chamados.enums.SetorChamado;
import com.suporte.chamados.enums.StatusChamado;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ChamadoResponseDTO {

    private Long id;
    private String titulo;
    private String descricao;
    private SetorChamado setor;
    private String solicitante;
    private StatusChamado status;
    private PrioridadeChamado prioridade;
    private LocalDateTime dataAbertura;
    private LocalDateTime dataFechamento;

    public static ChamadoResponseDTO fromEntity(Chamado chamado) {
        return ChamadoResponseDTO.builder()
                .id(chamado.getId())
                .titulo(chamado.getTitulo())
                .descricao(chamado.getDescricao())
                .setor(chamado.getSetor())
                .solicitante(chamado.getSolicitante())
                .status(chamado.getStatus())
                .prioridade(chamado.getPrioridade())
                .dataAbertura(chamado.getDataAbertura())
                .dataFechamento(chamado.getDataFechamento())
                .build();
    }
}