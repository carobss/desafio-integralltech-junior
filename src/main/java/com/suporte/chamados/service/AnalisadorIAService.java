package com.suporte.chamados.service;

import com.suporte.chamados.dto.AnaliseIAResponseDTO;
import com.suporte.chamados.dto.ChamadoResponseDTO;
import com.suporte.chamados.enums.PrioridadeChamado;
import com.suporte.chamados.enums.SetorChamado;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AnalisadorIAService {

    private final ChamadoService chamadoService;

    public AnaliseIAResponseDTO analisar(Long id) {
        ChamadoResponseDTO chamado = chamadoService.buscarPorId(id);

        String prompt = montarPrompt(chamado.getTitulo(), chamado.getDescricao());

        return chamarMock(id, chamado.getTitulo(), prompt);
    }

    private String montarPrompt(String titulo, String descricao) {
        return """
                Você é um assistente especializado em suporte técnico empresarial.
                Analise o chamado abaixo e retorne APENAS um JSON válido, sem texto adicional.
                
                Chamado:
                Título: %s
                Descrição: %s
                
                Retorne exatamente neste formato JSON:
                {
                  "prioridadeSugerida": "BAIXA | MEDIA | ALTA | CRITICA",
                  "setorSugerido": "TI | MANUTENCAO | RH | FINANCEIRO",
                  "resumo": "resumo do problema em até 2 frases"
                }
                """.formatted(titulo, descricao);
    }

    private AnaliseIAResponseDTO chamarMock(Long chamadoId, String titulo, String prompt) { 
        String texto = prompt.toLowerCase();

        PrioridadeChamado prioridade = inferirPrioridade(texto);
        SetorChamado setor = inferirSetor(texto);
        String resumo = gerarResumo(titulo, prioridade); 
        return AnaliseIAResponseDTO.builder()
                .chamadoId(chamadoId)
                .prioridadeSugerida(prioridade)
                .setorSugerido(setor)
                .resumo(resumo)
                .analisadoEm(LocalDateTime.now())
                .build();
    }

    private PrioridadeChamado inferirPrioridade(String texto) {
        if (texto.contains("parado") || texto.contains("urgente") ||
            texto.contains("sistema fora") || texto.contains("não funciona") ||
            texto.contains("critico") || texto.contains("crítico")) {
            return PrioridadeChamado.CRITICA;
        }
        if (texto.contains("lento") || texto.contains("travando") ||
            texto.contains("intermitente") || texto.contains("erro")) {
            return PrioridadeChamado.ALTA;
        }
        if (texto.contains("dúvida") || texto.contains("duvida") ||
            texto.contains("como") || texto.contains("atualizar")) {
            return PrioridadeChamado.BAIXA;
        }
        return PrioridadeChamado.MEDIA;
    }

    private SetorChamado inferirSetor(String texto) {
        if (texto.contains("computador") || texto.contains("sistema") ||
            texto.contains("internet") || texto.contains("rede") ||
            texto.contains("senha") || texto.contains("software") ||
            texto.contains("impressora") || texto.contains("acesso")) {
            return SetorChamado.TI;
        }
        if (texto.contains("salario") || texto.contains("salário") ||
            texto.contains("pagamento") || texto.contains("beneficio") ||
            texto.contains("férias") || texto.contains("ferias")) {
            return SetorChamado.RH;
        }
        if (texto.contains("nota fiscal") || texto.contains("reembolso") ||
            texto.contains("financeiro") || texto.contains("pagamento")) {
            return SetorChamado.FINANCEIRO;
        }
        if (texto.contains("ar condicionado") || texto.contains("lâmpada") ||
            texto.contains("lampada") || texto.contains("torneira") ||
            texto.contains("cadeira") || texto.contains("mesa")) {
            return SetorChamado.MANUTENCAO;
        }
        return SetorChamado.TI;
    }

    private String gerarResumo(String titulo, PrioridadeChamado prioridade) {
        return switch (prioridade) {
            case CRITICA -> "Situação crítica identificada: " + titulo.toLowerCase() +
                            ". Recomenda-se atendimento imediato para evitar impacto operacional.";
            case ALTA    -> "Problema de alta prioridade relacionado a: " + titulo.toLowerCase() +
                            ". Atendimento urgente recomendado.";
            case MEDIA   -> "Chamado de prioridade média: " + titulo.toLowerCase() +
                            ". Deve ser atendido dentro do prazo padrão.";
            case BAIXA   -> "Solicitação de baixa prioridade: " + titulo.toLowerCase() +
                            ". Pode ser atendida conforme disponibilidade da equipe.";
        };
    }
}