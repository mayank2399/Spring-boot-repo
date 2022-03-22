package com.Timetracker.RequestResponse;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserProfile extends BaseResponse {

	private String name;
	private long workingHours;
	private long workingWeeks;
//	private 
}
