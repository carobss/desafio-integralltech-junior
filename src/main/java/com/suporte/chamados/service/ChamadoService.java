package com.suporte.chamados.service;

import com.suporte.chamados.dto.AtualizarChamadoDTO;
import com.suporte.chamados.dto.ChamadoRequestDTO;
import com.suporte.chamados.dto.ChamadoResponseDTO;
import com.suporte.chamados.entity.Chamado;
import com.suporte.chamados.enums.SetorChamado;
import com.suporte.chamados.enums.StatusChamado;
import com.suporte.chamados.repository.ChamadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChamadoService {

    private final ChamadoRepository chamadoRepository;

    // Cria um novo chamado
    public ChamadoResponseDTO criar(ChamadoRequestDTO dto) {
        Chamado chamado = Chamado.builder()
                .titulo(dto.getTitulo())
                .descricao(dto.getDescricao())
                .setor(dto.getSetor())
                .solicitante(dto.getSolicitante())
                .prioridade(dto.getPrioridade())
                .build();

        return ChamadoResponseDTO.fromEntity(chamadoRepository.save(chamado));
    }

    // Lista os chamados existentes
    public List<ChamadoResponseDTO> listarTodos() {
        return chamadoRepository.findAll()
                .stream()
                .map(ChamadoResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    // Busca chamado pelo ID
    public ChamadoResponseDTO buscarPorId(Long id) {
        Chamado chamado = buscarOuLancarErro(id);
        return ChamadoResponseDTO.fromEntity(chamado);
    }

    // Busca chamado pelo SETOR
    public List<ChamadoResponseDTO> filtrarPorSetor(SetorChamado setor) {
        return chamadoRepository.findBySetor(setor)
                .stream()
                .map(ChamadoResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    // Atualiza chamado por completo
    public ChamadoResponseDTO atualizar(Long id, AtualizarChamadoDTO dto) {
        Chamado chamado = buscarOuLancarErro(id);

        if (chamado.getStatus().isFinalizado()) {
            throw new RuntimeException(
                "Não é possível alterar um chamado com status " + chamado.getStatus() +
                ". Chamados CANCELADOS ou RESOLVIDOS não podem ser reabertos."
            );
        }

        chamado.setTitulo(dto.getTitulo());
        chamado.setDescricao(dto.getDescricao());
        chamado.setSetor(dto.getSetor());
        chamado.setPrioridade(dto.getPrioridade());
        chamado.setStatus(dto.getStatus());

        if (dto.getStatus().isFinalizado()) {
            chamado.setDataFechamento(LocalDateTime.now());
        }

        return ChamadoResponseDTO.fromEntity(chamadoRepository.save(chamado));
    }

    // Deleta chamado
    public void cancelar(Long id) {
        Chamado chamado = buscarOuLancarErro(id);

        if (chamado.getStatus().isFinalizado()) {
            throw new RuntimeException(
                "O chamado já está " + chamado.getStatus() + " e não pode ser cancelado novamente."
            );
        }

        chamado.setStatus(StatusChamado.CANCELADO);
        chamado.setDataFechamento(LocalDateTime.now());

        chamadoRepository.save(chamado);
    }

    // Método auxiliar
    private Chamado buscarOuLancarErro(Long id) {
        return chamadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Chamado não encontrado com ID: " + id));
    }
}