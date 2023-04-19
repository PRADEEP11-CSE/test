package com.hrs.app.service;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hrs.app.dao.AppointmentRepo;
import com.hrs.app.dao.HouseRepo;
import com.hrs.app.model.Appointment;
import com.hrs.app.model.House;

@Service
public class OwnerService {
	
	@Autowired
	private HouseRepo houseRepo;
	
	@Autowired
	private AppointmentRepo appointmentRepo;
	
	public void saveHouse(House house) {
		
	
		
		House savedHouse = houseRepo.save(house);
		
		
//		
		
		
	}

	public House getHouse() {
		// TODO Auto-generated method stub
		return houseRepo.findAll().get(0);
	}

	public List<House> getAllHousesByEmail(String emailId) {
		// TODO Auto-generated method stub
		List<House> houses = houseRepo.findAllByEmailId(emailId);
		return houses;
	}

	public void deleteHouse(Long id) {
		
		houseRepo.deleteById(id);
		
		
		
	}

	public House getHouseById(Long id) {
		// TODO Auto-generated method stub
		
		House house = houseRepo.findHouseById(id);
//		System.out.println(house.getHouseAddress());
		return house;
	}
	
	public List<House> getAllHousesDetailsByEmail(String email) {
		// TODO Auto-generated method stub
		
		List<House> houses = houseRepo.findAllByEmailId(email);
		
		
		return houses;
	}
	
	public House getHouseDetailsById(Long id) {
		// TODO Auto-generated method stub
		return houseRepo.findHouseById(id);
	}
	
	public void updateHouse(House house) {

		
		House savedHouse = houseRepo.save(house);
		
		
		
		
	}

	

	public List<Appointment> getAllAppointmentsByUserId(String email) {
		
		List<Appointment> appointments = new ArrayList<Appointment>();
		
		appointmentRepo.findAll().forEach(apt -> {
			
			houseRepo.findAll().forEach(house -> {
				if(apt.getHouseId().equals(house.getId().toString()) && house.getHouseOwnerMail().equals(email)) {
					appointments.add(apt);
				}
			});

		});
		
		return appointments;
	}
}
