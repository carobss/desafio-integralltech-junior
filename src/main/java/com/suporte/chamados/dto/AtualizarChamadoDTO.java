package com.suporte.chamados.dto;

import com.suporte.chamados.enums.PrioridadeChamado;
import com.suporte.chamados.enums.SetorChamado;
import com.suporte.chamados.enums.StatusChamado;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AtualizarChamadoDTO {

    @NotBlank(message = "O título é obrigatório")
    @Size(min = 5, max = 100, message = "O título deve ter entre 5 e 100 caracteres")
    private String titulo;

    @NotBlank(message = "A descrição é obrigatória")
    private String descricao;

    @NotNull(message = "O setor é obrigatório")
    private SetorChamado setor;

    @NotNull(message = "A prioridade é obrigatória")
    private PrioridadeChamado prioridade;

    @NotNull(message = "O status é obrigatório")
    private StatusChamado status;
}