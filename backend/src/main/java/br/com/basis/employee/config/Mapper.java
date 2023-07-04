package br.com.basis.employee.config;

import org.mapstruct.factory.Mappers;

public class Mapper {
    public static <T> T factory(Class<T> clazz) {
        return Mappers.getMapper(clazz);
    }
}
