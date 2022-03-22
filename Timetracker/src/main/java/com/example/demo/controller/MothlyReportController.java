package com.example.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.Timetracker.RequestResponse.MonthlyReportResponse;
import com.Timetracker.RequestResponse.ReportRequest;
import com.Timetracker.service.MothlyReportService;

@RestController
@CrossOrigin
public class MothlyReportController {

	@Autowired
	private MothlyReportService mothlyReportService;

	@PostMapping("/monthly-report-withoutshow/{userId}")
	public MonthlyReportResponse monthlyReport(@PathVariable String userId, @RequestBody ReportRequest reportRequest) {
		return mothlyReportService.getMonhlyReport(reportRequest, userId);
	}
}
