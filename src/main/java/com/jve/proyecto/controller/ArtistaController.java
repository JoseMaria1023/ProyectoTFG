package com.jve.proyecto.controller;

import com.jve.proyecto.dto.ArtistaDTO;
import com.jve.proyecto.service.ArtistaService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/artistas")
public class ArtistaController {

    private final ArtistaService artistaService;
    private static final String UPLOAD_DIR = "uploads/";

    public ArtistaController(ArtistaService artistaService) {
        this.artistaService = artistaService;
    }

    @PostMapping("/crear")
    public ArtistaDTO crearArtista(@RequestParam("nombre") String nombre,
                                   @RequestParam("apellidos") String apellidos,
                                   @RequestParam("username") String username,
                                   @RequestParam("password") String password,
                                   @RequestParam(value = "descripcion", required = false) String descripcion,
                                   @RequestParam(value = "generoMusical", required = false) String generoMusical,
                                   @RequestParam("foto") MultipartFile foto) {
        return artistaService.crearArtistaConFoto(nombre, apellidos, username, password, descripcion, generoMusical, foto);
    }

    @GetMapping("/listar")
    public List<ArtistaDTO> listarArtistas() {
        return artistaService.TraerListaArtistas();
    }
    
    @PutMapping("/actualizar/{id}")
    public ArtistaDTO actualizarArtista(@PathVariable Long id,
                                        @RequestParam("nombre") String nombre,
                                        @RequestParam("apellidos") String apellidos,
                                        @RequestParam("username") String username,
                                        @RequestParam("password") String password,
                                        @RequestParam(value = "descripcion", required = false) String descripcion,
                                        @RequestParam(value = "generoMusical", required = false) String generoMusical,
                                        @RequestParam(value = "foto", required = false) MultipartFile foto) {
        ArtistaDTO artistaDTO = ArtistaDTO.builder()
                .nombre(nombre)
                .apellidos(apellidos)
                .username(username)
                .password(password)
                .descripcion(descripcion)
                .generoMusical(generoMusical)
                .build();

        if (foto != null && !foto.isEmpty()) {
            try {
                String fileName = System.currentTimeMillis() + "_" + foto.getOriginalFilename();
                Path path = Paths.get(UPLOAD_DIR + fileName);
                Files.createDirectories(path.getParent());
                Files.write(path, foto.getBytes());
                artistaDTO.setFoto("/" + UPLOAD_DIR + fileName);
            } catch (IOException e) {
                throw new RuntimeException("Error al subir la imagen: " + e.getMessage());
            }
        }
        return artistaService.actualizarArtista(id, artistaDTO);
    }
     @GetMapping("/{id}")
    public ArtistaDTO getArtistaPorId(@PathVariable Long id) {
        return artistaService.TraerArtistaPorId(id);
    }
    @DeleteMapping("/eliminar/{id}")
    public void eliminarArtista(@PathVariable Long id) {
        artistaService.eliminarArtista(id);
    }
}
