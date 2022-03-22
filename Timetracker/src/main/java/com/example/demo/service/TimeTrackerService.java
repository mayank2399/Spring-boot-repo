package com.Timetracker.service;

import java.util.ArrayList;

import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import com.Timetracker.RequestResponse.FirebaseEntry;
import com.Timetracker.RequestResponse.FirebaseEntryJson;
import com.Timetracker.RequestResponse.LogEntryRequest;
import com.Timetracker.RequestResponse.UserProfile;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.database.FirebaseDatabase;

@Service
public class TimeTrackerService {

	public FirebaseEntryJson addNewEntry(LogEntryRequest logEntryRequest) {
		FirebaseEntryJson firebaseEntryJson = new FirebaseEntryJson();
		try {

			// year current year
			// month range 1-12
			// day range 1-31
			// hour range 0-23
			// minute range 0-59
			DateTime startTime = DateTime.now().withYear(logEntryRequest.getYear())
					.withMonthOfYear(logEntryRequest.getMonth()).withDayOfMonth(logEntryRequest.getDate())
					.withHourOfDay(logEntryRequest.getStartHour()).withMinuteOfHour(logEntryRequest.getStartMinute());
			DateTime endTime = DateTime.now().withYear(logEntryRequest.getYear())
					.withMonthOfYear(logEntryRequest.getMonth()).withDayOfMonth(logEntryRequest.getDate())
					.withHourOfDay(logEntryRequest.getEndHour()).withMinuteOfHour(logEntryRequest.getEndMinute());
			if (startTime.isAfter(endTime)) {
				System.out.println(startTime.getHourOfDay());
				System.out.println(endTime.getHourOfDay());
				firebaseEntryJson.setException("Start time is after end time");
				return firebaseEntryJson;
			}

			/*
			 * for actual output DateTimeFormatter dtfOutput =
			 * DateTimeFormat.forPattern("E dd MMM yyyy HH:mm:ss");
			 * System.out.println(dtfOutput.print(dt));
			 */

			FirebaseDatabase.getInstance().setPersistenceEnabled(true);
			Firestore dbStore = FirestoreClient.getFirestore();
			
			DocumentReference refernce = dbStore.collection("timetracker").document(logEntryRequest.getUserId())
					.collection("Year").document(logEntryRequest.getYear().toString()).collection("Month")
					.document(logEntryRequest.getMonth().toString()).collection("Date")
					.document(logEntryRequest.getDate().toString());

			DocumentSnapshot documentSnapshot = refernce.get().get();
			firebaseEntryJson = documentSnapshot.toObject(FirebaseEntryJson.class);
			if (firebaseEntryJson == null) {
				firebaseEntryJson = new FirebaseEntryJson();
				firebaseEntryJson.setEntryList(new ArrayList<>());
			}

			FirebaseEntry entry = new FirebaseEntry();
			entry.setDescription(logEntryRequest.getDescription());
			entry.setEndTime(endTime.getMillis());
			entry.setStartTime(startTime.getMillis());
			entry.setShow(logEntryRequest.isShow());
			firebaseEntryJson.getEntryList().add(entry);
			refernce.set(firebaseEntryJson);
			return firebaseEntryJson;

		} catch (Exception e) {
			firebaseEntryJson.setException(e.getMessage());
		}
		return firebaseEntryJson;
	}

	public UserProfile viewProfile(String userId) {
		UserProfile user = new UserProfile();
		try {
			FirebaseDatabase.getInstance().setPersistenceEnabled(true);
			Firestore dbStore = FirestoreClient.getFirestore();
			DocumentReference refernce = dbStore.collection("timetracker").document(userId);
			DocumentSnapshot documentSnapshot = refernce.get().get();
			user = documentSnapshot.toObject(UserProfile.class);
			return user;
		} catch (Exception e) {
			user.setException(e.getMessage());
			e.printStackTrace();
		}
		return user;
	}
}
