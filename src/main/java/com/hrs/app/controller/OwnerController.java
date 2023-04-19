package com.hrs.app.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.hrs.app.model.Appointment;
import com.hrs.app.model.House;
import com.hrs.app.model.User;
import com.hrs.app.service.OwnerService;
import com.hrs.app.service.UserService;


@Controller
public class OwnerController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private OwnerService ownerService;
	
	@GetMapping("/owner")
	public String getHouseOwnerWelcomePage(@ModelAttribute("user") User user, Model model, HttpSession session)
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
		return "owner/welcomehouseowner";
	}
	
	@GetMapping("/createHouse")
	public String createHouse(Model model, HttpSession session) {
		
		House house = new House();
		model.addAttribute("house", house);
		@SuppressWarnings("unchecked")
        List<String> messages = (List<String>) session.getAttribute("MY_SESSION_MESSAGES");

        if (messages == null) {
            messages = new ArrayList<>();
        }
        model.addAttribute("sessionMessages", messages);
        User userdata = userService.findUser(messages.get(0));
        model.addAttribute("role", userdata.getUsertype());
		return "owner/createhouse";
	}
	
	@PostMapping("/saveHouse")
	public String saveHouse(@ModelAttribute("house") House house, Model model, HttpSession session)
	{
		System.out.println("house created");
		@SuppressWarnings("unchecked")
        List<String> messages = (List<String>) session.getAttribute("MY_SESSION_MESSAGES");

		if(messages == null) {
			model.addAttribute("errormsg", "Session Expired. Please Login Again");
			return "home/error";
		}
        model.addAttribute("sessionMessages", messages);
        User userdata = userService.findUser(messages.get(0));
        
		house.setIsBooked("0");
		house.setIsHide("0");
		house.setIsVerified("1");
		house.setHouseOwnerMail(userdata.getEmail());
		
		ownerService.saveHouse(house);
		
		return "redirect:/owner";
	}
	
	@GetMapping("/viewHouses")
	public String viewHouses(Model model, HttpSession session) {
		
		
		@SuppressWarnings("unchecked")
        List<String> messages = (List<String>) session.getAttribute("MY_SESSION_MESSAGES");
		if(messages == null) {
			model.addAttribute("errormsg", "Session Expired. Please Login Again");
			return "home/error";
		}
		User userdata = userService.findUser(messages.get(0));
		List<House> houses = ownerService.getAllHousesDetailsByEmail(userdata.getEmail());
		model.addAttribute("houses", houses);
		model.addAttribute("houseOwnerMail", userdata.getEmail());
		model.addAttribute("role", userdata.getUsertype());
		return "owner/displayhouses";
	}
	
	@GetMapping("/viewSingleHouse/{id}")
	public String viewSingleHouse(Model model, HttpSession session, @PathVariable(name="id") Long id) {
		
		
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
		return "owner/displaysinglehouse";
	}
	
	@GetMapping("/editHouse/{id}")
	public String editHouse(Model model, HttpSession session, @PathVariable(name="id") Long id) {
		
		
		@SuppressWarnings("unchecked")
        List<String> messages = (List<String>) session.getAttribute("MY_SESSION_MESSAGES");
		if(messages == null) {
			model.addAttribute("errormsg", "Session Expired. Please Login Again");
			return "home/error";
		}
		User userdata = userService.findUser(messages.get(0));
		House house = ownerService.getHouseById(id);
		
		House houseDetails = ownerService.getHouseDetailsById(id);
		
		model.addAttribute("house", house);
		
		model.addAttribute("role", userdata.getUsertype());

		return "owner/updatehouse";
	}
	
	@PostMapping("/updateHouse")
	public String updateHouse(@ModelAttribute("house") House house, Model model, HttpSession session)
	{
		System.out.println("house updated");
		@SuppressWarnings("unchecked")
        List<String> messages = (List<String>) session.getAttribute("MY_SESSION_MESSAGES");
		if(messages == null) {
			model.addAttribute("errormsg", "Session Expired. Please Login Again");
			return "home/error";
		}
		User userdata = userService.findUser(messages.get(0));
		
		
		
		ownerService.updateHouse(house);
		
		
		return "redirect:/owner";
	}
	
	@PostMapping("/deleteHouse/{id}")
	public String deleteHouse(@PathVariable(name="id") Long id)
	{
		ownerService.deleteHouse(id);
		
		return "redirect:/owner";
	}
	

	@GetMapping("/viewAppointments")
	public String viewAppointments(Model model, HttpSession session)
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
        
        List<Appointment> appointments = ownerService.getAllAppointmentsByUserId(userdata.getEmail());
        
        model.addAttribute("appointments", appointments);
		return "owner/viewappointments";
	}

}
