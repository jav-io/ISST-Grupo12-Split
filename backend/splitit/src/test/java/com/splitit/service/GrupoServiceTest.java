package com.splitit.service;

import com.splitit.dto.GrupoDTO;
import com.splitit.model.Grupo;
import com.splitit.model.Usuario;
import com.splitit.repository.GrupoRepository;
import com.splitit.repository.MiembroRepository;
import com.splitit.repository.UsuarioRepository;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

public class GrupoServiceTest {

    @Mock private GrupoRepository grupoRepository;
    @Mock private MiembroRepository miembroRepository;
    @Mock private UsuarioRepository usuarioRepository;

    @InjectMocks private GrupoService grupoService;

    public GrupoServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCrearGrupoSinNombre_lanzaExcepcion() {
        GrupoDTO grupoDTO = new GrupoDTO();
        grupoDTO.setNombre(null);  // nombre vacío
        grupoDTO.setIdCreador(1L);

        assertThrows(RuntimeException.class, () -> {
            grupoService.crearGrupo(grupoDTO);
        });
    }

    @Test
    public void testCrearGrupoConUsuarioInexistente_lanzaExcepcion() {
        GrupoDTO grupoDTO = new GrupoDTO();
        grupoDTO.setNombre("Viaje a Roma");
        grupoDTO.setIdCreador(99L);

        when(usuarioRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            grupoService.crearGrupo(grupoDTO);
        });
    }

    @Test
    public void testCrearGrupoCorrectamente_funciona() {
        GrupoDTO grupoDTO = new GrupoDTO();
        grupoDTO.setNombre("Viaje a Roma");
        grupoDTO.setDescripcion("Presupuesto y gastos");
        grupoDTO.setIdCreador(1L);

        Grupo grupoMock = new Grupo();
        grupoMock.setNombre(grupoDTO.getNombre());
        grupoMock.setDescripcion(grupoDTO.getDescripcion());
        grupoMock.setIdCreador(grupoDTO.getIdCreador());
        grupoMock.setFechaCreacion(new Date());

        Usuario usuarioMock = new Usuario();
        usuarioMock.setIdUsuario(1L); // 👈 CORREGIDO

        when(grupoRepository.save(org.mockito.ArgumentMatchers.any(Grupo.class))).thenReturn(grupoMock);
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuarioMock));

        Grupo grupoCreado = grupoService.crearGrupo(grupoDTO);

        verify(grupoRepository).save(org.mockito.ArgumentMatchers.any(Grupo.class));
        verify(usuarioRepository).findById(1L);

        System.out.println("✔ Grupo creado correctamente: " + grupoCreado.getNombre());
    }
}
