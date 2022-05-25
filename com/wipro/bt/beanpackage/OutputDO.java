package com.wipro.bt.beanpackage;

import java.util.Objects;

public class OutputDO {
	private String userName;
	private int sessionCount;
	private long sessionTime;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getSessionCount() {
		return sessionCount;
	}

	public void setSessionCount(int sessionCount) {
		this.sessionCount = sessionCount;
	}

	public long getSessionTime() {
		return sessionTime;
	}

	public void setSessionTime(long sessionTime) {
		this.sessionTime = sessionTime;
	}
	
	public OutputDO(String userName, int sessionCount, long sessionTime) {
		this.userName = userName;
		this.sessionCount = sessionCount;
		this.sessionTime = sessionTime;
	}

	@Override
	public int hashCode() {
		return Objects.hash(sessionCount, sessionTime, userName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OutputDO other = (OutputDO) obj;
		return sessionCount == other.sessionCount && sessionTime == other.sessionTime
				&& Objects.equals(userName, other.userName);
	}	

}
