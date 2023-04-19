package com.hrs.app.service;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hrs.app.dao.AppointmentRepo;
import com.hrs.app.dao.HouseRepo;
import com.hrs.app.dao.MessageRepo;
import com.hrs.app.dao.ReportOwnerRepo;
import com.hrs.app.dao.ReportUserRepo;
import com.hrs.app.dao.UserRepo;
import com.hrs.app.model.Appointment;
import com.hrs.app.model.House;
import com.hrs.app.model.MessageModel;
import com.hrs.app.model.ReportOwnerModel;
import com.hrs.app.model.ReportUserModel;
import com.hrs.app.model.User;

@Service
public class OwnerServiceImpl implements OwnerService{
	
	@Autowired
	private HouseRepo houseRepo;
	
	@Autowired
	private AppointmentRepo appointmentRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private MessageRepo messageRepo;
	
	@Autowired
	private ReportOwnerRepo reportOwnerRepo;
	
	@Autowired
	private ReportUserRepo reportUserRepo;
	
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
		
		appointmentRepo.findAll().stream().filter(app -> app.getStatus().equals("0")).forEach(apt -> {
			
			houseRepo.findAll().forEach(house -> {
				if(apt.getHouseId().equals(house.getId().toString()) && house.getHouseOwnerMail().equals(email)) {
					appointments.add(apt);
				}
			});

		});
		
		return appointments;
	}
	

	public List<User> getAllOwners() {
		// TODO Auto-generated method stub
		return userRepo.findAllOwners();
	}
	
	public List<MessageModel> findAllMessages(String email) {
		// TODO Auto-generated method stub
		List<MessageModel> msgs = messageRepo.findAll();
		List<MessageModel> studentMsgs = msgs.stream().filter(msg -> msg.getOwnerMail().equals(email) && msg.getAnswer().equals("")).collect(Collectors.toList());
		return studentMsgs;
	}

	@Override
	public MessageModel getMsgById(Long id) {
		// TODO Auto-generated method stub
		return messageRepo.findMessageById(id);
		
	}
	
	public void saveReport(ReportOwnerModel report) {
		// TODO Auto-generated method stub
		reportOwnerRepo.save(report);
	}
	
	public void saveUserReport(ReportUserModel report) {
		// TODO Auto-generated method stub
		reportUserRepo.save(report);
	}

	@Override
	public List<User> getAllUsers() {
		// TODO Auto-generated method stub
		return userRepo.getAllUsers();
	}

	@Override
	public void approve(Long id) {
		// TODO Auto-generated method stub
		Appointment app = appointmentRepo.findAppointmentById(id);
		app.setStatus("1");
		appointmentRepo.save(app);
		
		
	}

	@Override
	public void reject(Long id) {
		// TODO Auto-generated method stub
		Appointment app = appointmentRepo.findAppointmentById(id);
		app.setStatus("2");
		appointmentRepo.save(app);
		
	}

}
