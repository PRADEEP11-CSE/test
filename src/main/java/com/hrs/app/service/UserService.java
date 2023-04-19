package com.hrs.app.service;

import java.util.List;

import com.hrs.app.model.Announcement;
import com.hrs.app.model.Appointment;
import com.hrs.app.model.Book;
import com.hrs.app.model.Complaint;
import com.hrs.app.model.Favourite;
import com.hrs.app.model.House;
import com.hrs.app.model.Lease;
import com.hrs.app.model.Maintenance;
import com.hrs.app.model.MessageModel;
import com.hrs.app.model.ReviewPropertyModel;
import com.hrs.app.model.User;

public interface UserService {
	
	int saveUser(User user);
	
	User findUser(String email);
	
	User authenticateUser(User user);
	
	User findUserByUsername(String username);
	
	int validatePassword(User usermodel, String securityQuestion, String securityAnswer);
	
	void saveNewPassword(User usermodel);
	
	void deleteUser(Long id);
	
	List<House> getAllHouseDetails();
	
	void reserveHouse(Long houseId, Long userId);
	
	void saveAppointment(Appointment appointment);
	
	List<House> searchHouses(String searchKey);
	
	List<House> filterHouses(String city, String moveInDate);

	void savefavourites(Favourite favourite);

	List<House> findAllFavs(Long id);

	void saveMsg(MessageModel msg);

	List<MessageModel> findAllMessages(String email);

	List<User> getAllOwners();

	List<House> getAllBookedHouseDetails(Long id);

	Book saveBookHouse(Book book);

	void saveMaintenance(Maintenance maintenance);

	void saveReviewProperty(ReviewPropertyModel property);
	
	List<House> getAllHouses();

	void saveComplaint(Complaint complaint);

	List<Announcement> getAllAnnouncements();

	void saveLease(Lease lease);

	List<User> getAllUsers();

}
