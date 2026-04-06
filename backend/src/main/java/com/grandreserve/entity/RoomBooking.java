package com.grandreserve.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "room_bookings")
public class RoomBooking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String  name;
    private Integer age;
    private String  contact;
    private String  address;
    private String  idProof;
    private String  roomType;
    private String  bedSize;
    private Integer floor;
    private boolean balcony;
    private boolean pool;
    private Integer price;

    @Enumerated(EnumType.STRING)
    private BookingStatus status = BookingStatus.PENDING;

    private LocalDateTime createdAt = LocalDateTime.now();

    public enum BookingStatus { PENDING, ACCEPTED, CANCELLED }

    public RoomBooking() {}

    private RoomBooking(Builder b) {
        this.user     = b.user;
        this.name     = b.name;
        this.age      = b.age;
        this.contact  = b.contact;
        this.address  = b.address;
        this.idProof  = b.idProof;
        this.roomType = b.roomType;
        this.bedSize  = b.bedSize;
        this.floor    = b.floor;
        this.balcony  = b.balcony;
        this.pool     = b.pool;
        this.price    = b.price;
        this.status   = BookingStatus.PENDING;
        this.createdAt = LocalDateTime.now();
    }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private User user;
        private String name, contact, address, idProof, roomType, bedSize;
        private Integer age, floor, price;
        private boolean balcony, pool;

        public Builder user(User v)       { this.user = v;      return this; }
        public Builder name(String v)     { this.name = v;      return this; }
        public Builder age(Integer v)     { this.age = v;       return this; }
        public Builder contact(String v)  { this.contact = v;   return this; }
        public Builder address(String v)  { this.address = v;   return this; }
        public Builder idProof(String v)  { this.idProof = v;   return this; }
        public Builder roomType(String v) { this.roomType = v;  return this; }
        public Builder bedSize(String v)  { this.bedSize = v;   return this; }
        public Builder floor(Integer v)   { this.floor = v;     return this; }
        public Builder balcony(boolean v) { this.balcony = v;   return this; }
        public Builder pool(boolean v)    { this.pool = v;      return this; }
        public Builder price(Integer v)   { this.price = v;     return this; }
        public RoomBooking build()        { return new RoomBooking(this); }
    }

    // Getters
    public Long          getId()        { return id; }
    public User          getUser()      { return user; }
    public String        getName()      { return name; }
    public Integer       getAge()       { return age; }
    public String        getContact()   { return contact; }
    public String        getAddress()   { return address; }
    public String        getIdProof()   { return idProof; }
    public String        getRoomType()  { return roomType; }
    public String        getBedSize()   { return bedSize; }
    public Integer       getFloor()     { return floor; }
    public boolean       isBalcony()    { return balcony; }
    public boolean       isPool()       { return pool; }
    public Integer       getPrice()     { return price; }
    public BookingStatus getStatus()    { return status; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    // Setters
    public void setId(Long v)               { this.id = v; }
    public void setUser(User v)             { this.user = v; }
    public void setName(String v)           { this.name = v; }
    public void setAge(Integer v)           { this.age = v; }
    public void setContact(String v)        { this.contact = v; }
    public void setAddress(String v)        { this.address = v; }
    public void setIdProof(String v)        { this.idProof = v; }
    public void setRoomType(String v)       { this.roomType = v; }
    public void setBedSize(String v)        { this.bedSize = v; }
    public void setFloor(Integer v)         { this.floor = v; }
    public void setBalcony(boolean v)       { this.balcony = v; }
    public void setPool(boolean v)          { this.pool = v; }
    public void setPrice(Integer v)         { this.price = v; }
    public void setStatus(BookingStatus v)  { this.status = v; }
    public void setCreatedAt(LocalDateTime v){ this.createdAt = v; }
}
