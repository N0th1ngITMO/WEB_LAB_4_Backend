package ru.kkettch.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import ru.kkettch.dataTransferObject.DotsReqDto;
import ru.kkettch.dataTransferObject.DotsRespDto;
import ru.kkettch.service.serviceInterface.DotsService;

import java.util.List;

@RestController
@RequestMapping("/api/dots")
public class DotController {
   private final DotsService dotsService;

   public DotController(DotsService dotsService) {
       this.dotsService = dotsService;
   }

   @PostMapping("/add")
   public ResponseEntity<DotsRespDto> addDots(@RequestBody DotsReqDto dotsRequestDto, Authentication user) {
       DotsRespDto responseDto = dotsService.mapDotsEntityToResponseDotsDto(dotsService.addDot(dotsRequestDto, user));
       return new ResponseEntity<>(responseDto, HttpStatus.OK);
   }

   @GetMapping("/getDots")
   public ResponseEntity<List<DotsRespDto>> getAllDotsByUser(Authentication authentication) {
       return new ResponseEntity<>(dotsService.getAllUserDots(authentication), HttpStatus.OK);
   }

   @DeleteMapping("/deleteDots")
   public ResponseEntity<String> deleteDots(Authentication authentication) {
       dotsService.deleteDotsByUserId(authentication);
       return new ResponseEntity<>("Dots were removed successfully", HttpStatus.OK);
   }
}
