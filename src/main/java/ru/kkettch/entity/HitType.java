package ru.kkettch.entity;

public enum HitType {
   HIT("Hit"),
   MISS("Miss"),
   WRONG_DATA("Not valid data received");
   private final String hitArea;

   HitType(String hitArea) {
       this.hitArea = hitArea;
   }

   public String getHitArea() {
       return hitArea;
   }
}
