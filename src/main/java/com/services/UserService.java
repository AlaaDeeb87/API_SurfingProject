package com.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import com.entities.Beach;
import com.entities.Weather;
import com.entities.User;
import com.repositories.BeachSqlRepository;
import com.repositories.WeatherSqlRepository;
import com.repositories.UserSqlRepository;
import com.requests.AddWeatherRequest;
import com.requests.AddUserRequest;
import com.requests.UpdateUserRequest;

@Service
@RequiredArgsConstructor

public class UserService {
	@Getter
	@Setter
	

	Logger logger = LoggerFactory.getLogger(UserService.class);
	@Autowired
	
	private  UserSqlRepository UserRepo;
	private  BeachSqlRepository BeachRepo;
	private  WeatherSqlRepository WeatherRepo;

	public Collection<User> getAll() {
		Sort sort = Sort.by(Sort.Direction.ASC, "firstName");
		return UserRepo.findAll(sort);
	}

	public User getById(Long id) {
		return UserRepo.findById(id).get();
	}

	public Collection<User> getByFirstName(String firstName){
		return UserRepo.findByFirstName(firstName);
	}

	public Collection<User> getByLocation(String location){
		return UserRepo.findByBeachLocation(location);
	}

	public User addUser(AddUserRequest request) {	

		var beach = new Beach();
		beach.setLocation(request.getLocation());
		beach.setBeachName(request.getBeachName());
		beach = BeachRepo.save(beach);

		var user = new User();
		user.setFirstName(request.getFirstName());
		user.setLastName(request.getLastName());
		user.setCreatedAt(new Date());
		user.setBeach(beach);
		user = UserRepo.save(user);

		if (request.getWeathers().size() > 0) {
			var weathers = new ArrayList<Weather>();
			for (AddWeatherRequest weather : request.getWeathers()) {
				var newWeather = new Weather();
				newWeather.setName(weather.getName() != null ? weather.getName() : "NoName!");
				newWeather.setUser(user);
				weathers.add(newWeather);
			}
			WeatherRepo.saveAll(weathers);
			user.setWeathers(weathers);
		}		

		logger.info("Successfully created new User");
		return user;
	}

	public User updateUser(Long id, UpdateUserRequest request) {
		var user = UserRepo.findById(id).get();
		user.setLastName(request.getLastName());
		user.setUpdatedAt(new Date());
		user = UserRepo.save(user);
		
		logger.info("Updated User");
		return user;
	}

	public void deleteUser(Long id) {
		try {
			UserRepo.deleteById(id);
			logger.info("Successfully deleted user with id: " + id);
		}
		catch(Exception ex) {
			logger.error("Failed to delete user with id: " + id, ex);
		}
		
	}

}