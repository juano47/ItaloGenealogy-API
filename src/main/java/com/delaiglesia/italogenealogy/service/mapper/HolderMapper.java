package com.delaiglesia.italogenealogy.service.mapper;

import com.delaiglesia.italogenealogy.domain.Holder;
import com.delaiglesia.italogenealogy.service.dto.HolderDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Holder} and its DTO {@link HolderDTO}.
 */
@Mapper(componentModel = "spring")
public interface HolderMapper extends EntityMapper<HolderDTO, Holder> {}
