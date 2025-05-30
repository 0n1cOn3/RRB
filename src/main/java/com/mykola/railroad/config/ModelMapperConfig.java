package com.mykola.railroad.config;

import com.mykola.railroad.db.public_.enums.TypeAcl;
import com.mykola.railroad.dto.TypeACL;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.NameTokenizers;
import org.modelmapper.jooq.RecordValueReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setSourceNameTokenizer(NameTokenizers.UNDERSCORE)
                .setFieldMatchingEnabled(true)
                .addValueReader(new RecordValueReader());
        configureEnumMappings(modelMapper);
        return modelMapper;
    }

    private void configureEnumMappings(ModelMapper modelMapper) {
        modelMapper.typeMap(TypeAcl.class, TypeACL.class)
                .setConverter(ctx -> ctx.getSource() == null ? null : TypeACL.valueOf(ctx.getSource().getLiteral().toUpperCase()));
    }
}
