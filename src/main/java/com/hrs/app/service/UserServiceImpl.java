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

import com.hrs.app.dao.AnnouncementRepo;
import com.hrs.app.dao.AppointmentRepo;
import com.hrs.app.dao.BookRepo;
import com.hrs.app.dao.ComplaintRepo;
import com.hrs.app.dao.CouponRepo;
import com.hrs.app.dao.FavouritesRepo;
import com.hrs.app.dao.HouseRepo;
import com.hrs.app.dao.LeaseRepo;
import com.hrs.app.dao.MaintenanceRepo;
import com.hrs.app.dao.MessageRepo;
import com.hrs.app.dao.ReserveRepo;
import com.hrs.app.dao.ReviewPropertyRepo;
import com.hrs.app.dao.UserRepo;
import com.hrs.app.model.Announcement;
import com.hrs.app.model.Appointment;
import com.hrs.app.model.Book;
import com.hrs.app.model.Complaint;
import com.hrs.app.model.Coupon;
import com.hrs.app.model.Favourite;
import com.hrs.app.model.House;
import com.hrs.app.model.Lease;
import com.hrs.app.model.Maintenance;
import com.hrs.app.model.MessageModel;
import com.hrs.app.model.Reserve;
import com.hrs.app.model.ReviewPropertyModel;
import com.hrs.app.model.User;


@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private HouseRepo houseRepo;
	
	@Autowired
	private ReserveRepo reserveRepo;
	
	@Autowired
	private AppointmentRepo appointmentRepo;
	
	@Autowired
	private FavouritesRepo favouritesRepo;
	
	@Autowired
	private MessageRepo messageRepo;
	
	@Autowired
	private BookRepo bookRepo;
	
	@Autowired
	private MaintenanceRepo maintenanceRepo;
	
	@Autowired
	private ReviewPropertyRepo reviewPropertyRepo;
	
	@Autowired
	private ComplaintRepo complaintRepo;
	
	@Autowired
	private CouponRepo couponRepo;
	
	@Autowired
	private AnnouncementRepo announcementRepo;
	
	@Autowired
	private LeaseRepo leaseRepo;
	
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
		return houseDetails.stream().filter(house -> house.getIsHide().equals("0") && house.getIsBooked().equals("0")).collect(Collectors.toList());
	}
	
	public List<House> getAllHouses(){
		
		List<House> houseDetails = houseRepo.findAll();
		return houseDetails.stream().collect(Collectors.toList());
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
	
	public void savefavourites(Favourite favourite) {
		// TODO Auto-generated method stub
		favouritesRepo.save(favourite);
	}
	

	public List<House> findAllFavs(Long id) {
		// TODO Auto-generated method stub
		
		List<Favourite> favs = favouritesRepo.findUserFavs(id);
		List<House> houseDetails = new ArrayList<House>();
		
		favs.forEach(fav -> {
			houseDetails.add(houseRepo.findHouseById(Long.parseLong(fav.getHouseId())));
		});
		
		return houseDetails;
	}

	@Override
	public void saveMsg(MessageModel msg) {
		messageRepo.save(msg);
		
	}


	public List<MessageModel> findAllMessages(String email) {
		// TODO Auto-generated method stub
		List<MessageModel> msgs = messageRepo.findAll();
		List<MessageModel> ownerMsgs = msgs.stream().filter(msg -> msg.getUserMail().equals(email) && !msg.getAnswer().equals("")).collect(Collectors.toList());
		return ownerMsgs;
	}
	
	public List<User> getAllOwners() {
		// TODO Auto-generated method stub
		return userRepo.findAllOwners();
	}

	@Override
	public List<House> getAllBookedHouseDetails(Long id) {
		// TODO Auto-generated method stub
		
		List<Book> bookings = bookRepo.getAllBookingsOfUser(id);
		List<House> houses = new ArrayList<House>();
		
		bookings.forEach(book -> {
			String houseId = book.getHouseId();
			houses.add(houseRepo.findHouseById(Long.parseLong(houseId)));			
		});
		
		return houses;
	}

	@Override
	public Book saveBookHouse(Book book) {
		
			
		if(book.getCoupon().equals("")) {
			
			House house = houseRepo.findHouseById(Long.parseLong(book.getHouseId()));
			
			house.setIsBooked("1");
			houseRepo.save(house);
			
			
			return bookRepo.save(book);
		}
		
		List<Coupon> coupons = couponRepo.findAll();
		
		List<Coupon> coupon = coupons.stream().filter(co -> co.getCouponCode().equals(book.getCoupon())).collect(Collectors.toList());
		
		if(coupon.size() != 0) {
			
			Integer houseRent = Integer.parseInt(book.getHouseRent()) - Integer.parseInt(coupon.get(0).getDiscountAmount());
			
			book.setHouseRent(houseRent.toString());
			
			
			
			couponRepo.deleteById(coupon.get(0).getId());
			House house = houseRepo.findHouseById(Long.parseLong(book.getHouseId()));
			
			house.setIsBooked("1");
			houseRepo.save(house);
			
			return bookRepo.save(book);
		}
		else {
			
			return null;
		}
		

		
	}

	@Override
	public void saveMaintenance(Maintenance maintenance) {
		maintenanceRepo.save(maintenance);
		
	}
	
	public void saveReviewProperty(ReviewPropertyModel property) {
		// TODO Auto-generated method stub
		
		reviewPropertyRepo.save(property);
		
	}

	@Override
	public void saveComplaint(Complaint complaint) {
		// TODO Auto-generated method stub
		complaintRepo.save(complaint);
		
	}

	@Override
	public List<Announcement> getAllAnnouncements() {
		// TODO Auto-generated method stub
		return announcementRepo.findAll();
	}

	@Override
	public void saveLease(Lease lease) {
		// TODO Auto-generated method stub
		leaseRepo.save(lease);
		
	}

	@Override
	public List<User> getAllUsers() {
		// TODO Auto-generated method stub
		return userRepo.getAllUsers();
	}

}
