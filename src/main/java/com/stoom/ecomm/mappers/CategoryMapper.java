package com.stoom.ecomm.mappers;


import com.stoom.ecomm.dto.request.CreateCategoryRequest;
import com.stoom.ecomm.dto.request.PatchCategoryRequest;
import com.stoom.ecomm.dto.response.CategoryResponse;
import com.stoom.ecomm.entities.Category;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    Category mapToEntity(CreateCategoryRequest dto);

    @Mapping(target = "id", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updatePartial(
            @MappingTarget Category category,
            PatchCategoryRequest dto
    );

    CategoryResponse mapToResponse(Category entity);
}