package com.jve.proyecto.converter;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;


public class ModelMapperUtils {

    private static final ModelMapper modelMapper = new ModelMapper();

    static {
        // Configuración global del ModelMapper
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    private ModelMapperUtils() {
        // Constructor privado para evitar instanciación
    }

    public static ModelMapper getMapper() {
        return modelMapper;
    }

    public static <D, T> D map(final T entity, Class<D> outClass) {
        return modelMapper.map(entity, outClass);
    }

    public static <S, D> void map(final S source, D destination) {
        modelMapper.map(source, destination);
    }
}
