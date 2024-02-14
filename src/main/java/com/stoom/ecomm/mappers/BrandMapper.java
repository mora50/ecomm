package com.stoom.ecomm.mappers;

import com.stoom.ecomm.dto.request.CreateBrandRequest;
import com.stoom.ecomm.dto.request.PatchBrandRequest;
import com.stoom.ecomm.entities.Brand;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring")
public interface BrandMapper {

    BrandMapper INSTANCE = Mappers.getMapper(BrandMapper.class);

    Brand mapToEntity(CreateBrandRequest dto);

    @Mapping(target = "id", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateParcial(
            @MappingTarget Brand brand,
            PatchBrandRequest dto
    );

    CreateBrandRequest mapToDTO(Brand entity);
}