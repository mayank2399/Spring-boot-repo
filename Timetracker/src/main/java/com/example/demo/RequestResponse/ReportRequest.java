package com.Timetracker.RequestResponse;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportRequest {

	public Integer startMonth;
	public Integer startYear;
	public Integer endMonth;
	public Integer endYear;
	private boolean show;

}
