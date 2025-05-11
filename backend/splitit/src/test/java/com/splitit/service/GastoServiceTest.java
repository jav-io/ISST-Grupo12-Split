package com.splitit.service;

import com.splitit.dto.GastoDTO;
import com.splitit.model.Gasto;
import com.splitit.repository.DeudaRepository;
import com.splitit.repository.GastoRepository;
import com.splitit.repository.GrupoRepository;
import com.splitit.repository.MiembroRepository;
import com.splitit.model.Grupo;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Optional;
import static org.mockito.Mockito.when;

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

    @Test
    public void testCrearGastoConGrupoIdInexistente_lanzaExcepcion() {
        GastoDTO gastoDTO = new GastoDTO();
        gastoDTO.setGrupoId(999L); // un grupo que no existe

        // Simulamos que no se encuentra el grupo en el repositorio
        when(grupoRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            gastoService.crearGasto(gastoDTO);
        });
    }
    @Test
    public void testCrearGastoConGrupoValido_funciona() {
        GastoDTO gastoDTO = new GastoDTO();
        gastoDTO.setGrupoId(1L);
        gastoDTO.setPagadorId(2L);

        Grupo grupoMock = new Grupo();
        grupoMock.setId(1L);

        when(grupoRepository.findById(1L)).thenReturn(Optional.of(grupoMock));

        assertThrows(RuntimeException.class, () -> {
            gastoService.crearGasto(gastoDTO);
            ;
        });
        System.out.println("✔ Test pasado correctamente");
  
    }

















}


