package com.Timetracker.RequestResponse;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MonthlyReponseEntity {

	private Integer date;
	private Integer month;
	private Integer year;
	private long workedHours;
	private long extraHours;
	private long lowHours;

	private List<LogEntryRequest> moreInfo = new ArrayList<>();
}
