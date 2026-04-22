package com.suporte.chamados.enums;

public enum StatusChamado {

    ABERTO,
    EM_ATENDIMENTO,
    RESOLVIDO,
    CANCELADO;

    public boolean isFinalizado() {
        return this == RESOLVIDO || this == CANCELADO;
    }
}