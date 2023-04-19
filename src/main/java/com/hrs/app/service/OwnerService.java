package com.hrs.app.service;

import java.util.List;

import com.hrs.app.model.Appointment;
import com.hrs.app.model.House;
import com.hrs.app.model.MessageModel;
import com.hrs.app.model.ReportOwnerModel;
import com.hrs.app.model.ReportUserModel;
import com.hrs.app.model.User;

public interface OwnerService {
	
	void saveHouse(House house);
	
	House getHouse();
	
	List<House> getAllHousesByEmail(String emailId);
	
	void deleteHouse(Long id);
	
	House getHouseById(Long id);
	
	List<House> getAllHousesDetailsByEmail(String email);
	
	House getHouseDetailsById(Long id);
	
	void updateHouse(House house);
	
	List<Appointment> getAllAppointmentsByUserId(String email);

	List<User> getAllOwners();

	MessageModel getMsgById(Long id);

	List<MessageModel> findAllMessages(String email);

	void saveReport(ReportOwnerModel report);

	void saveUserReport(ReportUserModel report);

	List<User> getAllUsers();

	void approve(Long id);

	void reject(Long id);

}
