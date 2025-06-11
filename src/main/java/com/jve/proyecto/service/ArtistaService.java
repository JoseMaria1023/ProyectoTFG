package com.jve.proyecto.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jve.proyecto.dto.ArtistaDTO;
import com.jve.proyecto.entity.Artista;
import com.jve.proyecto.exceptions.ArtistaNoEncontradoException;
import com.jve.proyecto.exceptions.SubirImagenException;
import com.jve.proyecto.exceptions.UsuarioNoEncontradoException;
import com.jve.proyecto.repository.ArtistaRepository;
import com.jve.proyecto.converter.ArtistaConverter;

@Service
public class ArtistaService {

    private final ArtistaRepository artistaRepository;
    private final PasswordEncoder passwordEncoder;
    private final ArtistaConverter artistaConverter;
    private static final String UPLOAD_DIR = "uploads/";

    public ArtistaService(ArtistaRepository artistaRepository, PasswordEncoder passwordEncoder, ArtistaConverter artistaConverter) {
        this.artistaRepository = artistaRepository;
        this.passwordEncoder = passwordEncoder;
        this.artistaConverter = artistaConverter;
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

            return artistaConverter.toDto(artistaGuardado);

        } catch (IOException e) {
            throw new SubirImagenException();
        }
    }

    public ArtistaDTO TraerArtistaPorId(Long id) {
        Artista artista = artistaRepository.findById(id).orElseThrow(() -> new ArtistaNoEncontradoException());
        return artistaConverter.toDto(artista);
    }

    public Artista getByUsername(String username) {
        return artistaRepository.findByUsername(username).orElseThrow(() -> new UsuarioNoEncontradoException());
    }


    public List<ArtistaDTO> TraerListaArtistas() {
        return artistaRepository.findAll().stream().map(artista -> artistaConverter.toDto(artista))
        .collect(Collectors.toList());
    }

 public ArtistaDTO actualizarArtista(Long id, ArtistaDTO artistaDTO) {
    Artista artistaExistente = artistaRepository.findById(id).orElseThrow(() -> new ArtistaNoEncontradoException());

    Artista artistaParaActualizar = artistaConverter.toEntity(artistaDTO);
    artistaParaActualizar.setIdArtista(id);

    if (artistaDTO.getPassword() != null && !artistaDTO.getPassword().isEmpty()) {
        artistaParaActualizar.setPassword(passwordEncoder.encode(artistaDTO.getPassword()));
    } else {
        artistaParaActualizar.setPassword(artistaExistente.getPassword());
    }

    if (artistaDTO.getFoto() != null && !artistaDTO.getFoto().isEmpty()) {
        artistaParaActualizar.setFoto(artistaDTO.getFoto());
    } else {
        artistaParaActualizar.setFoto(artistaExistente.getFoto());
    }

    Artista artistaActualizado = artistaRepository.save(artistaParaActualizar);
    return artistaConverter.toDto(artistaActualizado);
}

    public Optional<Long> findArtistaIdByUsername(String username) {
        return artistaRepository.findByUsername(username).map(Artista::getIdArtista);
    }

    public void eliminarArtista(Long id) {
        Artista artista = artistaRepository.findById(id).orElseThrow(() -> new ArtistaNoEncontradoException());
        artistaRepository.delete(artista);
    }
}
