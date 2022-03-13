package com.ems;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ems.entity.User;
import com.ems.entity.UserRoles;
import com.ems.response.RestResponse;
import com.ems.service.RolesService;
import com.ems.service.UserService;
import com.ems.util.JWTTokenUtil;

@RestController
public class RequestController {


	
	@Autowired
	UserService userService;
	
	@Autowired
	RolesService rolesService;
	
	@Autowired
	JWTTokenUtil jwtTokenUtil;
	
	@Autowired
	AuthenticationManager authenticationManager;

	
	@PostMapping(path = "/user")
	public RestResponse<User> addUser(@RequestBody User user)
	{
		RestResponse<User> response = new RestResponse<User>();
		try
		{
			user  = userService.saveEntity(user);
			response.setData(user);
			response.setStatusCode(HttpStatus.OK);
			response.setResponseMessage("Success");
		}catch(Exception e)
		{
			response.setResponseMessage(e.getMessage());
			response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}
	
	@PostMapping(path = "/role")
	public RestResponse<UserRoles> addRole(@RequestBody UserRoles role)
	{
		RestResponse<UserRoles> response = new RestResponse<UserRoles>();
		try
		{
			UserRoles userRole  = rolesService.saveEntity(role);
			response.setData(userRole);
			response.setStatusCode(HttpStatus.OK);
			response.setResponseMessage("Success");
		}catch(Exception e)
		{
			response.setResponseMessage(e.getMessage());
			response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return response;
	}
	
	@GetMapping(path="/getuser/{name}")
	public RestResponse<Iterable<User>> getUserDetailsbyUserId(@PathVariable(name = "name") String name)
	{
		RestResponse<Iterable<User>> response = new RestResponse<Iterable<User>>();
		try {
			Iterable<User> userDetails = userService.getUserByName(name);
			response.setData(userDetails);
			response.setStatusCode(HttpStatus.OK);
			response.setResponseMessage("Success");
			
		}catch(Exception e)
		{
			response.setResponseMessage(e.getMessage());
			response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
		
	}
	
	@PostMapping(path = "/authenticate")
	public RestResponse<String> getAccessToken(@RequestBody User user) throws Exception
	{
		System.out.println(user);
	
		RestResponse<String> response = new RestResponse<String>();
		try {	
			authenticateUser(user);
			Iterable<User> users = userService.getUserByName(user.getUserId());
			String token = jwtTokenUtil.generateToken(users.iterator().next().getUserId());
			response.setData(token);
			response.setStatusCode(HttpStatus.OK);
			response.setResponseMessage("Success");
		}catch(Exception e)
		{
			response.setResponseMessage(e.getMessage());
			response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}

	private void authenticateUser(User user) throws Exception {
		try
		{
			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user.getUserId(), user.getPassword());
			authenticationManager.authenticate(authentication);
		}
		catch(DisabledException e)
		{
			throw new Exception("User Disabled",e);
		}
		catch(BadCredentialsException e)
		{
			throw new Exception("Bad Credentials",e);
		}
	}
	
	@GetMapping(path="/users")
	public RestResponse<Iterable<User>> getActiveUsers()
	{
		RestResponse<Iterable<User>> response = new RestResponse<Iterable<User>>();
		try {
			List<User> userDetails = ((List)userService.getAllEntities());
			userDetails = userDetails.stream().filter(user->user.getIsActive().equalsIgnoreCase("Y")).collect(Collectors.toList());
			
			response.setData(userDetails);
			response.setStatusCode(HttpStatus.OK);
			response.setResponseMessage("Success");
			
		}catch(Exception e)
		{
			response.setResponseMessage(e.getMessage());
			response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
		
	}
	 
	
}
