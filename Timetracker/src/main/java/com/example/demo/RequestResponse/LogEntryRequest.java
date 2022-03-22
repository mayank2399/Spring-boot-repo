package com.Timetracker.RequestResponse;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LogEntryRequest {

	public LogEntryRequest(Integer date, Integer month, Integer year, Integer startHour, Integer startMinute,
			Integer endHour, Integer endMinute, String description, String userId, boolean show,String taskName,String taskNo) {
		this.date = date;
		this.month = month;
		this.year = year;
		this.startHour = startHour;
		this.startMinute = startMinute;
		this.endHour = endHour;
		this.endMinute = endMinute;
		this.description = description;
		this.userId = userId;
		this.show = show;
		this.taskName=taskName;
		this.taskNo=taskNo;
	}
	private Integer date;
	private Integer month;
	private Integer year;
	private Integer startHour;
	private Integer startMinute;
	private Integer endHour;
	private Integer endMinute;
	private String description;
	private String userId;
	private String taskName;
	private String taskNo;
	private boolean show;

}
