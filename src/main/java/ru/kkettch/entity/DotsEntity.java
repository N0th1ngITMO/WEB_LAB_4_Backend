package ru.kkettch.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "result_dots")
public class DotsEntity {
   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private long id;
   @Column(name = "x", nullable = false)
   private double x;
   @Column(name = "y", nullable = false)
   private double y;
   @Column(name = "r", nullable = false)
   private double r;
   @Column(name = "cur_req_time", nullable = false)
   private String curRequestTime;
   @Column(name = "exec_time", nullable = false)
   private long executionTime;
   @Column(name = "hit", nullable = false)
   private String hitType;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;

   public long getId() {
       return id;
   }

   public void setId(long id) {
       this.id = id;
   }

   public double getX() {
       return x;
   }

   public void setX(double x) {
       this.x = x;
   }

   public double getY() {
       return y;
   }

   public void setY(double y) {
       this.y = y;
   }

   public double getR() {
       return r;
   }

   public void setR(double r) {
       this.r = r;
   }

   public String getCurRequestTime() {
       return curRequestTime;
   }

   public void setCurRequestTime(String curRequestTime) {
       this.curRequestTime = curRequestTime;
   }

   public long getExecutionTime() {
       return executionTime;
   }

   public void setExecutionTime(long executionTime) {
       this.executionTime = executionTime;
   }

   public String getHitType() {
       return hitType;
   }

   public void setHitType(String hitType) {
       this.hitType = hitType;
   }

   public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
}
