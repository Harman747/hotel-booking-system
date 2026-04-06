package com.grandreserve.repository;

import com.grandreserve.entity.RoomBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RoomBookingRepository extends JpaRepository<RoomBooking, Long> {
    List<RoomBooking> findByUserId(Long userId);
    List<RoomBooking> findAllByOrderByCreatedAtDesc();
    List<RoomBooking> findByFloorAndStatus(Integer floor, RoomBooking.BookingStatus status);
}
