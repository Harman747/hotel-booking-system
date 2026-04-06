package com.grandreserve.repository;

import com.grandreserve.entity.TableBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TableBookingRepository extends JpaRepository<TableBooking, Long> {
    List<TableBooking> findByUserId(Long userId);
    List<TableBooking> findAllByOrderByCreatedAtDesc();
    List<TableBooking> findByFloorAndStatus(Integer floor, TableBooking.BookingStatus status);
}
