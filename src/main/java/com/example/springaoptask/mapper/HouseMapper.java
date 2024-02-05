package com.example.springaoptask.mapper;

import com.example.springaoptask.entity.dto.HouseDto;
import com.example.springaoptask.entity.model.House;
import com.example.springaoptask.web.request.HouseRequest;
import com.example.springaoptask.web.response.HouseResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper
public interface HouseMapper {

	@Mapping(ignore = true, target = "uuidHouse")
	@Mapping(ignore = true, target = "createDate")
	HouseDto requestToDto(HouseRequest houseRequest);

	HouseResponse dtoToResponse(HouseDto houseDto);

	@Mapping(ignore = true, target = "id")
	List<HouseDto> entityListToDtoList(List<House> houses);

	HouseDto entityToDto(House house);

	House dtoToEntity(HouseDto houseDto);

	List<HouseResponse> dtoListToResponseList(List<HouseDto> houses);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "createDate", ignore = true)
	void mergeEntity(@MappingTarget House target, HouseRequest source);

}
