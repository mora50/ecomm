package com.stoom.ecomm.mappers;

import com.stoom.ecomm.dto.request.CreateProductRequest;
import com.stoom.ecomm.dto.request.UpdateProductRequest;
import com.stoom.ecomm.entities.Category;
import com.stoom.ecomm.entities.Product;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);
    
    @Mapping(target = "id", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updatePartial(@MappingTarget Product product, UpdateProductRequest dto);


    @Mapping(source = "brandId", target = "brand.id")
    @Mapping(source = "categoriesId", target = "categories", qualifiedByName = "mapToCategories")
    Product mapToEntity(CreateProductRequest dto);

    @Named("mapToCategories")
    default Set<Category> mapToCategories(Set<Long> categoryIds) {
        return categoryIds.stream().map(id -> {
            Category category = new Category();
            category.setId(id);
            return category;
        }).collect(Collectors.toSet());
    }
}