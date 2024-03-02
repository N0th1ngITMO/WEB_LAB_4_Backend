package ru.kkettch.dataTransferObject;

public record DotsRespDto(
       double x,
       double y,
       double r,
       String curRequestTime,
       long executionTime,
       String hitType
) {
}
