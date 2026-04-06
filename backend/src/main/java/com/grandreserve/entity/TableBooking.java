package com.grandreserve.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "table_bookings")
public class TableBooking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String  name;
    private Integer age;
    private String  contact;
    private Integer floor;
    private Integer seats;
    private Integer price;

    @Enumerated(EnumType.STRING)
    private BookingStatus status = BookingStatus.PENDING;

    private LocalDateTime createdAt = LocalDateTime.now();

    public enum BookingStatus { PENDING, ACCEPTED, CANCELLED }

    public TableBooking() {}

    private TableBooking(Builder b) {
        this.user      = b.user;
        this.name      = b.name;
        this.age       = b.age;
        this.contact   = b.contact;
        this.floor     = b.floor;
        this.seats     = b.seats;
        this.price     = b.price;
        this.status    = BookingStatus.PENDING;
        this.createdAt = LocalDateTime.now();
    }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private User user;
        private String name, contact;
        private Integer age, floor, seats, price;

        public Builder user(User v)      { this.user = v;     return this; }
        public Builder name(String v)    { this.name = v;     return this; }
        public Builder age(Integer v)    { this.age = v;      return this; }
        public Builder contact(String v) { this.contact = v;  return this; }
        public Builder floor(Integer v)  { this.floor = v;    return this; }
        public Builder seats(Integer v)  { this.seats = v;    return this; }
        public Builder price(Integer v)  { this.price = v;    return this; }
        public TableBooking build()      { return new TableBooking(this); }
    }

    // Getters
    public Long          getId()        { return id; }
    public User          getUser()      { return user; }
    public String        getName()      { return name; }
    public Integer       getAge()       { return age; }
    public String        getContact()   { return contact; }
    public Integer       getFloor()     { return floor; }
    public Integer       getSeats()     { return seats; }
    public Integer       getPrice()     { return price; }
    public BookingStatus getStatus()    { return status; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    // Setters
    public void setId(Long v)               { this.id = v; }
    public void setUser(User v)             { this.user = v; }
    public void setName(String v)           { this.name = v; }
    public void setAge(Integer v)           { this.age = v; }
    public void setContact(String v)        { this.contact = v; }
    public void setFloor(Integer v)         { this.floor = v; }
    public void setSeats(Integer v)         { this.seats = v; }
    public void setPrice(Integer v)         { this.price = v; }
    public void setStatus(BookingStatus v)  { this.status = v; }
    public void setCreatedAt(LocalDateTime v){ this.createdAt = v; }
}
