package com.Timetracker.RequestResponse;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FirebaseEntryJson extends BaseResponse {

	List<FirebaseEntry> entryList = new ArrayList<>();

}
