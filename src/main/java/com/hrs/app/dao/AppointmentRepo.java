package com.hrs.app.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hrs.app.model.Appointment;




@Repository
public interface AppointmentRepo extends JpaRepository<Appointment, Long> {

}
