package com.wipro.bt.beanpackage;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class InputDO {
	
	private String timeStmp;
	private String userName;
	private String activity;
	private LocalTime timeVal;
	private Boolean flag;

	public String getTimeStmp() {
		return timeStmp;
	}

	public void setTimeStmp(String timeStmp) {
		this.timeStmp = timeStmp;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getActivity() {
		return activity;
	}

	public void setActivity(String activity) {
		this.activity = activity;
	}

	public LocalTime getTimeVal() {
		return timeVal;
	}

	public void setTimeVal(LocalTime timeVal) {
		this.timeVal = timeVal;
	}
	
	public InputDO(String timeStmp, String userName, String activity) {
		this.timeStmp = timeStmp;
		this.userName = userName;
		this.activity = activity;
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
		this.timeVal = LocalTime.parse(timeStmp, dtf);
		this.flag = Boolean.TRUE;
	}

	public Boolean getFlag() {
		return flag;
	}

	public void setFlag(Boolean flag) {
		this.flag = flag;
	}

}
