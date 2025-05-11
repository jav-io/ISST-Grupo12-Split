package com.splitit.service;

import com.splitit.dto.GastoDTO;
import com.splitit.model.Gasto;
import com.splitit.repository.DeudaRepository;
import com.splitit.repository.GastoRepository;
import com.splitit.repository.GrupoRepository;
import com.splitit.repository.MiembroRepository;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class GastoServiceTest {

    @Mock
    private GastoRepository gastoRepository;

    @Mock
    private GrupoRepository grupoRepository;

    @Mock
    private MiembroRepository miembroRepository;

    @Mock
    private DeudaRepository deudaRepository;

    @InjectMocks
    private GastoService gastoService;

    public GastoServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCrearGastoSinGrupoId_lanzaExcepcion() {
        GastoDTO gastoDTO = new GastoDTO();
        gastoDTO.setGrupoId(null); // Probamos que no se haya pasado el grupo

        assertThrows(RuntimeException.class, () -> {
            gastoService.crearGasto(gastoDTO);
        });
    }
}
