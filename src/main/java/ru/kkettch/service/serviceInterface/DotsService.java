package ru.kkettch.service.serviceInterface;

import org.springframework.security.core.Authentication;

import ru.kkettch.dataTransferObject.DotsReqDto;
import ru.kkettch.dataTransferObject.DotsRespDto;
import ru.kkettch.entity.DotsEntity;

import java.util.List;

public interface DotsService {
   DotsEntity addDot(DotsReqDto dotsRequestDto, Authentication authentication);

   List<DotsRespDto> getAllUserDots(Authentication authentication);

   DotsRespDto mapDotsEntityToResponseDotsDto(DotsEntity dotsEntity);

   int deleteDotsByUserId(Authentication authentication);
}
