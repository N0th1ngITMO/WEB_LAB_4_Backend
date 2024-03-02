package ru.kkettch.service;

import jakarta.transaction.Transactional;
import ru.kkettch.dataTransferObject.DotsReqDto;
import ru.kkettch.dataTransferObject.DotsRespDto;
import ru.kkettch.entity.DotsEntity;
import ru.kkettch.entity.HitType;
import ru.kkettch.exception.Exception;
import ru.kkettch.repository.DotsRepository;
import ru.kkettch.service.serviceInterface.DotsService;
import ru.kkettch.service.serviceInterface.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class DotsServiceImpl implements DotsService {
   private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yy HH:mm:ss");
   private static final short COUNT_OF_NUMBERS_AFTER_DECIMAL_POINT = 3;
   private static final double DIVIDER = Math.pow(10, COUNT_OF_NUMBERS_AFTER_DECIMAL_POINT);
   private final DotsRepository dotsRepository;
   private final UserService userService;

   public DotsServiceImpl(DotsRepository dotsRepository, UserService userService) {
       this.dotsRepository = dotsRepository;
       this.userService = userService;
   }


   @Override
   public List<DotsRespDto> getAllUserDots(Authentication authentication) {
       List<DotsEntity> dotsEntityList = dotsRepository.getDotsEntitiesByUserId(userService.findUserByUserName(authentication.getName()).get().getId());
       return dotsEntityList.stream().map(this::mapDotsEntityToResponseDotsDto).toList();
   }

   @Override
   public DotsEntity addDot(DotsReqDto dotsRequestDto, Authentication authentication) {
       long startExec = System.nanoTime();
       DotsEntity dotsEntity = new DotsEntity();
       dotsEntity.setCurRequestTime(dateFormat.format(new Date(System.currentTimeMillis())));
       dotsEntity.setHitType(checkArea(dotsRequestDto));
       dotsEntity.setX(dotsRequestDto.x());
       dotsEntity.setY(dotsRequestDto.y());
       dotsEntity.setR(dotsRequestDto.r());
       dotsEntity.setExecutionTime(System.nanoTime() - startExec);
       dotsEntity.setUser(userService.findUserByUserName(authentication.getName()).get());
       return dotsRepository.save(dotsEntity);
   }

   @Override
   @Transactional
   public int deleteDotsByUserId(Authentication authentication) {
       int dotsDeletedCount = dotsRepository.deleteAllByUserId(userService.findUserByUserName(authentication.getName()).get().getId());
       if (dotsDeletedCount >= 0) {
           return dotsDeletedCount;
       } else {
           throw new Exception("Dot were not deleted", HttpStatus.BAD_REQUEST);
       }
   }

   private String checkArea(DotsReqDto dotsRequestDto) {
        if(dotsRequestDto.r() < 0){
            return HitType.WRONG_DATA.getHitArea();
        }
       if (dotsRequestDto.y() <= dotsRequestDto.x() / 2 + dotsRequestDto.r() / 2 && 0 <= dotsRequestDto.y() &&  dotsRequestDto.y() <= dotsRequestDto.r() && -3 <= dotsRequestDto.x() && dotsRequestDto.x() <= 0)
           return HitType.HIT.getHitArea();
       if (dotsRequestDto.x() * dotsRequestDto.x() + dotsRequestDto.y() * dotsRequestDto.y() <= dotsRequestDto.r() * dotsRequestDto.r() && -5 <= dotsRequestDto.x() && dotsRequestDto.x() <= 0 && -5 <= dotsRequestDto.y() && dotsRequestDto.y() <= 0)
           return HitType.HIT.getHitArea();
       if (0 <= dotsRequestDto.x() && dotsRequestDto.x() <= dotsRequestDto.r() / 2 && 0 <= dotsRequestDto.y() && dotsRequestDto.y() <= dotsRequestDto.r())
           return HitType.HIT.getHitArea();
       return HitType.MISS.getHitArea();
   }

   public DotsRespDto mapDotsEntityToResponseDotsDto(DotsEntity dotsEntity) {
       double x = Math.round(dotsEntity.getX() * DIVIDER) / DIVIDER;
       double y = Math.round(dotsEntity.getY() * DIVIDER) / DIVIDER;
       double r = Math.round(dotsEntity.getR() * DIVIDER) / DIVIDER;
       return new DotsRespDto(x, y, r, dotsEntity.getCurRequestTime(),
               dotsEntity.getExecutionTime(),
               dotsEntity.getHitType()
       );
   }
}
