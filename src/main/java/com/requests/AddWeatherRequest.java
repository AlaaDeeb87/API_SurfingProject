package com.requests;

import javax.validation.constraints.NotBlank;

public class AddWeatherRequest {
    
    @NotBlank(message = "Name is mandatory")
    private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public AddWeatherRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

}
