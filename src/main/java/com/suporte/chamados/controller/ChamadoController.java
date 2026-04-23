package com.suporte.chamados.controller;

import com.suporte.chamados.dto.AnaliseIAResponseDTO;
import com.suporte.chamados.dto.AtualizarChamadoDTO;
import com.suporte.chamados.dto.ChamadoRequestDTO;
import com.suporte.chamados.dto.ChamadoResponseDTO;
import com.suporte.chamados.dto.MensagemResponseDTO;
import com.suporte.chamados.enums.SetorChamado;
import com.suporte.chamados.service.AnalisadorIAService;
import com.suporte.chamados.service.ChamadoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chamados")
@RequiredArgsConstructor
public class ChamadoController {

    private final ChamadoService chamadoService;
    private final AnalisadorIAService analisadorIAService;

    @PostMapping
    public ResponseEntity<ChamadoResponseDTO> criar(@RequestBody @Valid ChamadoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(chamadoService.criar(dto));
    }

    @GetMapping
    public ResponseEntity<List<ChamadoResponseDTO>> listarTodos() {
        return ResponseEntity.ok(chamadoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChamadoResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(chamadoService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ChamadoResponseDTO> atualizar(
            @PathVariable Long id,
            @RequestBody @Valid AtualizarChamadoDTO dto) {
        return ResponseEntity.ok(chamadoService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MensagemResponseDTO> cancelar(@PathVariable Long id) {
        return ResponseEntity.ok(chamadoService.cancelar(id));
    }

    @GetMapping("/setor/{setor}")
    public ResponseEntity<List<ChamadoResponseDTO>> filtrarPorSetor(@PathVariable SetorChamado setor) {
        return ResponseEntity.ok(chamadoService.filtrarPorSetor(setor));
    }

    @PostMapping("/{id}/analisar")
    public ResponseEntity<AnaliseIAResponseDTO> analisar(@PathVariable Long id) {
        return ResponseEntity.ok(analisadorIAService.analisar(id));
    }
}