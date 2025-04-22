package com.jve.proyecto.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jve.proyecto.dto.ArtistaDTO;
import com.jve.proyecto.entity.Artista;
import com.jve.proyecto.repository.ArtistaRepository;

@Service
public class ArtistaService {

    private final ArtistaRepository artistaRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private static final String UPLOAD_DIR = "uploads/";

    public ArtistaService(ArtistaRepository artistaRepository, PasswordEncoder passwordEncoder, ModelMapper modelMapper) {
        this.artistaRepository = artistaRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }

    public ArtistaDTO crearArtistaConFoto(String nombre, String apellidos, String username, String password,
                                          String descripcion, String generoMusical, MultipartFile foto) {
        try {
            String fileName = System.currentTimeMillis() + "_" + foto.getOriginalFilename();
            Path path = Paths.get(UPLOAD_DIR + fileName);
            Files.createDirectories(path.getParent());
            Files.write(path, foto.getBytes());

            String passwordEncriptada = passwordEncoder.encode(password);

            Artista artista = new Artista();
            artista.setNombre(nombre);
            artista.setApellidos(apellidos);
            artista.setUsername(username);
            artista.setPassword(passwordEncriptada);
            artista.setDescripcion(descripcion);
            artista.setGeneroMusical(generoMusical);
            artista.setFoto("/" + UPLOAD_DIR + fileName);

            Artista artistaGuardado = artistaRepository.save(artista);

            return modelMapper.map(artistaGuardado, ArtistaDTO.class);

        } catch (IOException e) {
            throw new RuntimeException("Error al subir la imagen: " + e.getMessage());
        }
    }

    public ArtistaDTO obtenerArtistaPorId(Long id) {
        Artista artista = artistaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Artista no encontrado con ID: " + id));
        return modelMapper.map(artista, ArtistaDTO.class);
    }

    public Artista getByUsername(String username) {
        return artistaRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Artista no encontrado"));
    }

    public List<ArtistaDTO> obtenerTodosLosArtistas() {
        return artistaRepository.findAll().stream()
                .map(artista -> modelMapper.map(artista, ArtistaDTO.class))
                .collect(Collectors.toList());
    }

    public List<ArtistaDTO> obtenerListaArtistas() {
        return artistaRepository.findAll().stream()
                .map(artista -> {
                    ArtistaDTO dto = new ArtistaDTO();
                    dto.setIdArtista(artista.getIdArtista());
                    dto.setNombre(artista.getNombre());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public ArtistaDTO actualizarArtista(Long id, ArtistaDTO artistaDTO) {
        Artista artista = artistaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Artista no encontrado con ID: " + id));
        modelMapper.map(artistaDTO, artista);
        Artista artistaActualizado = artistaRepository.save(artista);
        return modelMapper.map(artistaActualizado, ArtistaDTO.class);
    }

    public void eliminarArtista(Long id) {
        Artista artista = artistaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Artista no encontrado con ID: " + id));
        artistaRepository.delete(artista);
    }
}
