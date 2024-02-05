package com.example.springaoptask.mapper;

import com.example.springaoptask.entity.dto.HouseHistoryDto;
import com.example.springaoptask.entity.model.HouseHistory;
import org.mapstruct.Mapper;

@Mapper
public interface HouseHistoryMapper {

	HouseHistory dtoToEntity(HouseHistoryDto houseHistoryDto);

}
