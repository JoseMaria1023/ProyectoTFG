package com.jve.proyecto.controller;

import com.jve.proyecto.dto.ArtistaDTO;
import com.jve.proyecto.service.ArtistaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ArtistaDTO> crearArtista(@RequestParam("nombre") String nombre,
                                                   @RequestParam("apellidos") String apellidos,
                                                   @RequestParam("username") String username,
                                                   @RequestParam("password") String password,
                                                   @RequestParam(value = "descripcion", required = false) String descripcion,
                                                   @RequestParam(value = "generoMusical", required = false) String generoMusical,
                                                   @RequestParam("foto") MultipartFile foto) {
        ArtistaDTO nuevoArtista = artistaService.crearArtistaConFoto(nombre, apellidos, username, password, descripcion, generoMusical, foto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoArtista);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<ArtistaDTO>> listarArtistas() {
        List<ArtistaDTO> artistas = artistaService.TraerListaArtistas();
        return ResponseEntity.ok(artistas);
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<ArtistaDTO> actualizarArtista(@PathVariable Long id,
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
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(null); 
            }
        }
        ArtistaDTO actualizado = artistaService.actualizarArtista(id, artistaDTO);
        return ResponseEntity.ok(actualizado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArtistaDTO> TraerArtistaPorId(@PathVariable Long id) {
        ArtistaDTO artista = artistaService.TraerArtistaPorId(id);
        return ResponseEntity.ok(artista);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarArtista(@PathVariable Long id) {
        artistaService.eliminarArtista(id);
        return ResponseEntity.noContent().build();
    }
}
