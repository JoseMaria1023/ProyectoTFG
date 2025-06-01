package com.jve.proyecto.converter;

import com.jve.proyecto.dto.ConciertoDTO;
import com.jve.proyecto.entity.Concierto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConciertoConverter {

    private final ModelMapper modelMapper;

    @Autowired
    public ConciertoConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public ConciertoDTO toDto(Concierto concierto) {
        ConciertoDTO dto = modelMapper.map(concierto, ConciertoDTO.class);

        if (concierto.getGira() != null && concierto.getGira().getArtista() != null) {
            dto.setNombreArtista(concierto.getGira().getArtista().getNombre());
            dto.setApellidosArtista(concierto.getGira().getArtista().getApellidos());
        }

        if (concierto.getZona() != null) {
            dto.setNombreZona(concierto.getZona().getNombre());
            if (concierto.getZona().getRecinto() != null) {
                dto.setNombreRecinto(concierto.getZona().getRecinto().getNombre());
            }
        }

        return dto;
    }

    public Concierto toEntity(ConciertoDTO conciertoDTO) {
        return modelMapper.map(conciertoDTO, Concierto.class);
    }
}
