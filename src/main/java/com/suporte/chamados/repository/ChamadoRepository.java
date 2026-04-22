package com.suporte.chamados.repository;

import com.suporte.chamados.entity.Chamado;
import com.suporte.chamados.enums.SetorChamado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChamadoRepository extends JpaRepository<Chamado, Long> {

    List<Chamado> findBySetor(SetorChamado setor);
}