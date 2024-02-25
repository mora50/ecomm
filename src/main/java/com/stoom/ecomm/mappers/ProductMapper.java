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


    @Mapping(source = "brandId", target = "brand.id")
    @Mapping(source = "categoriesId", target = "categories", qualifiedByName = "mapToCategories")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Product updatePartial(UpdateProductRequest dto, @MappingTarget Product product);


    @Mapping(source = "brandId", target = "brand.id")
    @Mapping(source = "categoriesId", target = "categories", qualifiedByName = "mapToCategories")
    Product mapToEntity(CreateProductRequest dto);

    @Named("mapToCategories")
    default Set<Category> mapToCategories(Set<Long> categoryIds) {

        if (categoryIds == null) {
            return null;
        }

        return categoryIds.stream().map(id -> {
            Category category = new Category();
            category.setId(id);
            return category;
        }).collect(Collectors.toSet());
    }
}