package com.splitit.service;

import com.splitit.model.Deuda;
import com.splitit.repository.DeudaRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class DeudaServiceTest {

    @Mock
    private DeudaRepository deudaRepository;

    @InjectMocks
    private DeudaService deudaService;

    public DeudaServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testBuscarPorId_DeudaNoExiste_LanzaExcepcion() {
        Long idInexistente = 99L;
        when(deudaRepository.findById(idInexistente)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            deudaService.buscarPorId(idInexistente);
        });
    }
}
