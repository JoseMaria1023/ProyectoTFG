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
    // Directorio de subida (asegúrate de que coincida con el usado en el servicio)
    private static final String UPLOAD_DIR = "uploads/";

    public ArtistaController(ArtistaService artistaService) {
        this.artistaService = artistaService;
    }

    @GetMapping("/ObtenerArtistas")
    public List<ArtistaDTO> obtenerArtistas() {
        return artistaService.obtenerTodosLosArtistas();
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
        return artistaService.obtenerListaArtistas();
    }
    
    // Endpoint para actualizar artista
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
                // Asigna la ruta de la imagen al DTO
                artistaDTO.setFoto("/" + UPLOAD_DIR + fileName);
            } catch (IOException e) {
                throw new RuntimeException("Error al subir la imagen: " + e.getMessage());
            }
        }
        // Llama al servicio para actualizar el artista
        return artistaService.actualizarArtista(id, artistaDTO);
    }
     @GetMapping("/{id}")
    public ArtistaDTO getArtistaPorId(@PathVariable Long id) {
        return artistaService.obtenerArtistaPorId(id);
    }
    // Endpoint para eliminar artista
    @DeleteMapping("/eliminar/{id}")
    public void eliminarArtista(@PathVariable Long id) {
        artistaService.eliminarArtista(id);
    }
}
