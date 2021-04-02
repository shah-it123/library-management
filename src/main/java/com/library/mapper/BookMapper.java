package com.library.mapper;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.library.dto.BookDTO;
import com.library.model.Book;

@Mapper(componentModel = "spring")
public interface BookMapper {
	
	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	void updateBookFromDto(BookDTO dto, @MappingTarget Book entity);
}
