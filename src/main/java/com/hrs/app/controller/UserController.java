package com.hrs.app.controller;

import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hrs.app.model.Appointment;
import com.hrs.app.model.House;
import com.hrs.app.model.User;
import com.hrs.app.service.OwnerService;
import com.hrs.app.service.UserService;

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private OwnerService ownerService;
	
	@GetMapping("/user")
	public String getUserWelcomePage(@ModelAttribute("user") User user, Model model, HttpSession session)
	{
		@SuppressWarnings("unchecked")
        List<String> messages = (List<String>) session.getAttribute("MY_SESSION_MESSAGES");

		if(messages == null) {
			model.addAttribute("errormsg", "Session Expired. Please Login Again");
			return "home/error";
		}
        model.addAttribute("sessionMessages", messages);
        User userdata = userService.findUser(messages.get(0));
        model.addAttribute("role", userdata.getUsertype());
//        String base64EncodedImage = Base64.getEncoder().encodeToString(houseOwnerService.getHouse().getHousePhoto());
//        model.addAttribute("image", base64EncodedImage);
//        System.out.println(base64EncodedImage);
        List<House> houses = userService.getAllHouseDetails();
        model.addAttribute("houses", houses);
		return "user/welcomeuser";
	}
	
	@GetMapping("/viewHouse/{id}")
	public String viewHouse(Model model, HttpSession session, @PathVariable(name="id") Long id) {
		
		
		@SuppressWarnings("unchecked")
        List<String> messages = (List<String>) session.getAttribute("MY_SESSION_MESSAGES");
		if(messages == null) {
			model.addAttribute("errormsg", "Session Expired. Please Login Again");
			return "home/error";
		}
		User userdata = userService.findUser(messages.get(0));
		House house = ownerService.getHouseById(id);
		
		
		model.addAttribute("house", house);
		
		model.addAttribute("role", userdata.getUsertype());
		return "user/viewsinglehouse";
	}
	
//	@GetMapping("/bookHouse/{id}")
//	public String bookHouse(@PathVariable(name="id") Long id, Model model, HttpSession session)
//	{
//		@SuppressWarnings("unchecked")
//        List<String> messages = (List<String>) session.getAttribute("MY_SESSION_MESSAGES");
//
//		if(messages == null) {
//			model.addAttribute("errormsg", "Session Expired. Please Login Again");
//			return "home/error";
//		}
//        model.addAttribute("sessionMessages", messages);
//        User userdata = userService.findUser(messages.get(0));
//        model.addAttribute("role", userdata.getUsertype());
//        House houseDetails = ownerService.getHouseDetailsById(id);
//        model.addAttribute("houseid", id);
//        model.addAttribute("houseRent", houseDetails.getHouseRent());
//        Book book = new Book();
//        model.addAttribute("book", book);
//		return "user/bookhouse";
//	}
	
	@GetMapping("/reserveHouse/{id}")
	public String reserveHouse(@PathVariable(name="id") Long id, Model model, HttpSession session)
	{
		@SuppressWarnings("unchecked")
        List<String> messages = (List<String>) session.getAttribute("MY_SESSION_MESSAGES");

		if(messages == null) {
			model.addAttribute("errormsg", "Session Expired. Please Login Again");
			return "home/error";
		}
        model.addAttribute("sessionMessages", messages);
        
        User userdata = userService.findUser(messages.get(0));
        System.out.println(id);
        userService.reserveHouse(id, userdata.getId());
		return "redirect:/user";
	}
	
	@GetMapping("/appointment/{id}")
	public String appointment(@PathVariable(name="id") Long id, Model model, HttpSession session)
	{
		@SuppressWarnings("unchecked")
        List<String> messages = (List<String>) session.getAttribute("MY_SESSION_MESSAGES");

		if(messages == null) {
			model.addAttribute("errormsg", "Session Expired. Please Login Again");
			return "home/error";
		}
        model.addAttribute("sessionMessages", messages);
        User userdata = userService.findUser(messages.get(0));
        model.addAttribute("role", userdata.getUsertype());
        model.addAttribute("houseid", id);
        Appointment appointment = new Appointment();
        model.addAttribute("appointment", appointment);
		return "user/appointment";
	}
	
	@GetMapping("/filter")
	public String viewFiltersPage(Model model, HttpSession session) {
		@SuppressWarnings("unchecked")
        List<String> messages = (List<String>) session.getAttribute("MY_SESSION_MESSAGES");
		if(messages == null) {
			model.addAttribute("errormsg", "Session Expired. Please Login Again");
			return "home/error";
		}
		User userdata = userService.findUser(messages.get(0));
		model.addAttribute("role", userdata.getUsertype());
		List<House> houses = userService.getAllHouseDetails();
	    model.addAttribute("houses", houses);
		return "user/filter";
		
	}
	
	@PostMapping("/bookAppointment")
	public String bookAppointment(@Param("houseid") Long houseid,@ModelAttribute("appointment") Appointment appointment, Model model, HttpSession session) {
		
		@SuppressWarnings("unchecked")
        List<String> messages = (List<String>) session.getAttribute("MY_SESSION_MESSAGES");

		if(messages == null) {
			model.addAttribute("errormsg", "Session Expired. Please Login Again");
			return "home/error";
		}
        model.addAttribute("sessionMessages", messages);
        User userdata = userService.findUser(messages.get(0));
        
        House house = ownerService.getHouseById(houseid);
        
        appointment.setHouseId(houseid.toString());
//        appointment.setHouseDetails(house.getHouseDetails());
        
        appointment.setUserId(userdata.getId().toString());
        
        userService.saveAppointment(appointment);
        return "redirect:/user";
        
	}
	
	@PostMapping("/searchHouse")
	public String searchHouse(Model model, HttpSession session, @RequestParam("searchKey") String searchKey ) {
		@SuppressWarnings("unchecked")
        List<String> messages = (List<String>) session.getAttribute("MY_SESSION_MESSAGES");

		if(messages == null) {
			model.addAttribute("errormsg", "Session Expired. Please Login Again");
			return "home/error";
		}
		User userdata = userService.findUser(messages.get(0));
        model.addAttribute("sessionMessages", messages);
        List<House> houses = userService.searchHouses(searchKey);
        model.addAttribute("houses", houses);
        model.addAttribute("role", userdata.getUsertype());
		return "user/welcomeuser";
	}
	
	@PostMapping("/applyFilters")
	public String applyFilters(Model model, HttpSession session, @RequestParam("city") String city,
			 @RequestParam("moveInDate") String moveInDate) {
		@SuppressWarnings("unchecked")
        List<String> messages = (List<String>) session.getAttribute("MY_SESSION_MESSAGES");

		if(messages == null) {
			model.addAttribute("errormsg", "Session Expired. Please Login Again");
			return "home/error";
		}
		User userdata = userService.findUser(messages.get(0));
        model.addAttribute("sessionMessages", messages);
        List<House> houses = userService.filterHouses(city,moveInDate);
        model.addAttribute("houses", houses);
        model.addAttribute("role", userdata.getUsertype());
		return "user/filter";
	}
	
	
	
//	@GetMapping("/previousBookings")
//	public String previousBookings(Model model, HttpSession session)
//	{
//		@SuppressWarnings("unchecked")
//        List<String> messages = (List<String>) session.getAttribute("MY_SESSION_MESSAGES");
//
//		if(messages == null) {
//			model.addAttribute("errormsg", "Session Expired. Please Login Again");
//			return "home/error";
//		}
//        model.addAttribute("sessionMessages", messages);
//        User userdata = userService.findUser(messages.get(0));
//        model.addAttribute("role", userdata.getUsertype());
//        
//        List<Book> bookings = userService.getAllBookingsByUserId(userdata.getId());
//        
//        model.addAttribute("bookings", bookings);
//		return "user/previousbookings";
//	}

}
