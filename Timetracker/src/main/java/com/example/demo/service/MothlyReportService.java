package com.example.demo.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.RequestResponse.FirebaseEntry;
import com.example.demo.RequestResponse.FirebaseEntryJson;
import com.example.demo.RequestResponse.LogEntryRequest;
import com.example.demo.RequestResponse.MonthlyReponseEntity;
import com.example.demo.RequestResponse.MonthlyReportResponse;
import com.example.demo.RequestResponse.ReportRequest;
import com.example.demo.RequestResponse.UserProfile;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.database.FirebaseDatabase;

@Service
public class MothlyReportService {

	@Autowired
	TimeTrackerService timeTrackerService;

	public MonthlyReportResponse getMonhlyReport(ReportRequest reportRequst, String userId) {

		MonthlyReportResponse monthlyReportResponse = new MonthlyReportResponse();
		List<MonthlyReponseEntity> mothlyReportList = new ArrayList<>();
		try {

			FirebaseDatabase.getInstance().setPersistenceEnabled(true);
			Firestore dbStore = FirestoreClient.getFirestore();

			for (Integer year = reportRequst.getStartYear(); year <= reportRequst.getEndYear(); year++) {

				for (Integer month = reportRequst.getStartMonth(); month <= reportRequst.getEndMonth(); month++) {
					Iterator<DocumentReference> refernce = dbStore.collection("timetracker").document(userId)
							.collection("Year").document(Integer.toString(year)).collection("Month")
							.document(Integer.toString(month)).collection("Date").listDocuments().iterator();
					System.out.println(refernce.hasNext());
					while (refernce.hasNext()) {
						DocumentReference documentReference = refernce.next();
						DocumentSnapshot a = documentReference.get().get();
						FirebaseEntryJson firebaseEntryJson = a.toObject(FirebaseEntryJson.class);

						MonthlyReponseEntity monthlyReponseEntity = new MonthlyReponseEntity();
						monthlyReponseEntity.setYear(year);
						monthlyReponseEntity.setMonth(month);
						long workedHours = 0;
						long extraHours = 0;
						long lowHours = 0;
						for (FirebaseEntry entry : firebaseEntryJson.getEntryList()) {
//					if(show!=entry.isShow())
//						continue;
							System.out.println(entry.getStartTime() + "          -           " + entry.getEndTime());
							DateTime startTime = new DateTime(entry.getStartTime());
							DateTime endTime = new DateTime(entry.getEndTime());
							monthlyReponseEntity.setDate(startTime.getDayOfMonth());
							Duration duration = new Duration(startTime, endTime);
							System.out.println(duration.toString());
							System.out.println(duration.getStandardHours());
							workedHours += duration.getStandardHours();
							LogEntryRequest logEntryRequest = new LogEntryRequest(startTime.getDayOfMonth(),
									startTime.getMonthOfYear(), startTime.getYear(), startTime.getHourOfDay(),
									startTime.getMinuteOfHour(), endTime.getHourOfDay(), endTime.getMinuteOfHour(),
									entry.getDescription(), "", false, entry.getTaskName(), entry.getTaskNo());
							monthlyReponseEntity.getMoreInfo().add(logEntryRequest);

						}
						UserProfile viewString = timeTrackerService.viewProfile(userId);
						long workingHours = viewString.getWorkingHours();
						if (workedHours > workingHours) {
							extraHours = workedHours - workingHours;
						} else {
							lowHours = workingHours - workedHours;
						}
						monthlyReponseEntity.setWorkedHours(workedHours);
						monthlyReponseEntity.setLowHours(lowHours);
						monthlyReponseEntity.setExtraHours(extraHours);
						mothlyReportList.add(monthlyReponseEntity);
					}
				}
			}
		} catch (Exception e) {
			monthlyReportResponse.setException(e.getMessage());
			e.printStackTrace();
		}
		monthlyReportResponse.setMothlyReportList(mothlyReportList);
		return monthlyReportResponse;
	}

}
