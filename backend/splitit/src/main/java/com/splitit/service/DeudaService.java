package com.splitit.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.splitit.dto.TransferenciaDTO;
import com.splitit.model.Deuda;
import com.splitit.model.Grupo;
import com.splitit.model.Miembro;
import com.splitit.repository.DeudaRepository;
import com.splitit.repository.GrupoRepository;

@Service
public class DeudaService {

    @Autowired
    private DeudaRepository deudaRepository;

    public Deuda crearDeuda(Deuda deuda) {
        return deudaRepository.save(deuda);
    }

    public List<Deuda> obtenerTodas() {
        return deudaRepository.findAll();
    }

    public Deuda buscarPorId(Long id) {
        return deudaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Deuda no encontrada"));
    }

    public void eliminarDeuda(Long id) {
        deudaRepository.deleteById(id);
    }
    @Autowired
private GrupoRepository grupoRepository;

public List<TransferenciaDTO> calcularTransferenciasParaSaldarGrupo(Long idGrupo) {
    Grupo grupo = grupoRepository.findById(idGrupo)
            .orElseThrow(() -> new RuntimeException("Grupo no encontrado"));

    List<Miembro> miembros = grupo.getMiembros();
    if (miembros == null || miembros.isEmpty()) {
        return Collections.emptyList();
    }

    List<TransferenciaDTO> transferencias = new ArrayList<>();
    List<Miembro> deudores = new ArrayList<>();
    List<Miembro> acreedores = new ArrayList<>();

    for (Miembro miembro : miembros) {
        BigDecimal saldo = miembro.getSaldo();
        if (saldo.compareTo(BigDecimal.ZERO) < 0) {
            deudores.add(miembro);
        } else if (saldo.compareTo(BigDecimal.ZERO) > 0) {
            acreedores.add(miembro);
        }
    }

    int i = 0, j = 0;
    while (i < deudores.size() && j < acreedores.size()) {
        Miembro deudor = deudores.get(i);
        Miembro acreedor = acreedores.get(j);

        BigDecimal deuda = deudor.getSaldo().negate();
        BigDecimal credito = acreedor.getSaldo();
        BigDecimal monto = deuda.min(credito);

        transferencias.add(new TransferenciaDTO(
                deudor.getUsuario().getNombre(),
                acreedor.getUsuario().getNombre(),
                monto,
                grupo.getNombre()
        ));

        deudor.setSaldo(deudor.getSaldo().add(monto));
        acreedor.setSaldo(acreedor.getSaldo().subtract(monto));

        if (deudor.getSaldo().compareTo(BigDecimal.ZERO) == 0) i++;
        if (acreedor.getSaldo().compareTo(BigDecimal.ZERO) == 0) j++;
    }

    return transferencias;
}

}
