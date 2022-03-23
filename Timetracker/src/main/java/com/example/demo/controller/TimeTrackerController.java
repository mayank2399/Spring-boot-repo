 package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.RequestResponse.FirebaseEntryJson;
import com.example.demo.RequestResponse.LogEntryRequest;
import com.example.demo.RequestResponse.UserProfile;
import com.example.demo.service.TimeTrackerService;

@RestController
@CrossOrigin
public class TimeTrackerController {

	@Autowired
	private TimeTrackerService timeTrackerService;

	@PostMapping("/add-entry")
	public FirebaseEntryJson addEntry(@RequestBody LogEntryRequest logEntryRequest) {
		System.out.println("yessss");

		return timeTrackerService.addNewEntry(logEntryRequest);

	}

	@GetMapping("/view-profile/{userId}")
	public UserProfile viewProfile(@PathVariable String userId) {
		return timeTrackerService.viewProfile(userId);
	}
}
