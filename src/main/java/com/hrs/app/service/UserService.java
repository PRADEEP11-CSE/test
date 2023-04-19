package com.hrs.app.service;
import static java.util.Comparator.comparingLong;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hrs.app.dao.AppointmentRepo;
import com.hrs.app.dao.HouseRepo;
import com.hrs.app.dao.ReserveRepo;
import com.hrs.app.dao.UserRepo;
import com.hrs.app.model.Appointment;
import com.hrs.app.model.House;
import com.hrs.app.model.Reserve;
import com.hrs.app.model.User;


@Service
public class UserService {
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private HouseRepo houseRepo;
	
	@Autowired
	private ReserveRepo reserveRepo;
	
	@Autowired
	private AppointmentRepo appointmentRepo;
	
	public int saveUser(User user) {
		userRepo.save(user);
		if(userRepo.save(user)!=null) {
			return 1;
		}
		else {
			return 0;
		}
	}
	
	public User findUser(String email) {
		List<User> user = userRepo.findAll();
		System.out.println("----"+user.size());
		if(user.size() == 0) {
			return null;
		}
		List<User> veifiedUser = user.stream().filter(n -> n.getEmail().equals(email)).collect(Collectors.toList());
		if(veifiedUser.size() > 0) {
			return veifiedUser.get(0);
		}
		else {
			return null;
		}
		
	}
	
	public User authenticateUser(User user) {
		
		if(user.getEmail().equals("admin@gmail.com") && user.getPassword().equals("admin")) {
			
			user.setUsertype("admin");
			
			return user;
		}
		
		List<User> users = userRepo.findAll();
		List<User> veifiedUser = users.stream().filter(n -> (n.getEmail().equals(user.getEmail()) || n.getUsername().equals(user.getEmail())) && n.getPassword().equals(user.getPassword())).collect(Collectors.toList());
		
		if(veifiedUser.size() ==1) {
			return veifiedUser.get(0);
		}
		else {
			return null;
		}
			
	}
	
	public User findUserByUsername(String username) {
		
		List<User> users = userRepo.findAll();
		List<User> veifiedUser = users.stream().filter(n -> n.getUsername().equals(username)).collect(Collectors.toList());
		if(veifiedUser.size() > 0) {
			return veifiedUser.get(0);
		}
		else {
			return null;
		}
		
	}
	
	public int validatePassword(User usermodel, String securityQuestion, String securityAnswer) {
		List<User> users = userRepo.findAll();
		List<User> verifiedUser = users.stream().filter(n -> n.getEmail().equals(usermodel.getEmail())).collect(Collectors.toList());
		if(verifiedUser.size() ==1) {
			List<User> userSecurities = userRepo.findAll();
			
			List<User> securedUser = userSecurities.stream().filter(security -> security.getSecurityQuestion().equals(securityQuestion) && security.getSecurityAnswer().equals(securityAnswer)
					
					).collect(Collectors.toList());
			if(securedUser.size() == 1) {
				return 1;
			}
			else {
				return 2;
			}
		}
		else {
			return 0;
		}
	}
	
	public void saveNewPassword(User usermodel) {
			
			User user = userRepo.findbyEmail(usermodel.getEmail());
			System.out.println("user#########"+user.toString());
			user.setPassword(usermodel.getPassword());
			userRepo.save(user);
	}
	
	public void deleteUser(Long id) {
			
			userRepo.deleteById(id);
			
	}
	
	public List<House> getAllHouseDetails(){
		
		List<House> houseDetails = houseRepo.findAll();
		return houseDetails.stream().filter(house -> house.getIsHide() == "0" && house.getIsBooked() == "0" && house.getIsVerified() != "0").collect(Collectors.toList());
	}
	
	public void reserveHouse(Long houseId, Long userId) {
		// TODO Auto-generated method stub
		
		Reserve reserve = new Reserve();
		
		reserve.setHouseId(houseId);
		reserve.setUserId(userId);
		
		reserveRepo.save(reserve);
		
		House house = houseRepo.findHouseById(houseId);
		
		house.setIsHide("1");
		houseRepo.save(house);
		
		
	}
	
	public void saveAppointment(Appointment appointment) {
		
		appointmentRepo.save(appointment);
		
		
	}
	
	public List<House> searchHouses(String searchKey) {
		// TODO Auto-generated method stub
		List<House> houses = houseRepo.findAll();
		List<House> searchedHouses = houses.stream().filter(house -> house.getHouseName().contains(searchKey) ||
				house.getHouseRent().equals(searchKey) || house.getAvailableFrom().contains(searchKey)
				|| house.getNoOfBedrooms().equals(searchKey)|| house.getNoOfBathrooms().equals(searchKey)).collect(Collectors.toList());
		return searchedHouses;
		
	}
	
	public List<House> filterHouses(String city, String moveInDate) {
		// TODO Auto-generated method stub
		List<House> houses = houseRepo.findAll();
		
		List<House> filteredHouses = houses.stream().filter(house -> house.getAvailableFrom().equals(moveInDate) || house.getCity().equals(city)).collect(Collectors.toList());	
		
		
		
		return filteredHouses.stream().collect(collectingAndThen(toCollection(() -> new TreeSet<>(comparingLong(House::getId))),
                ArrayList::new));
		
	}
	

}
