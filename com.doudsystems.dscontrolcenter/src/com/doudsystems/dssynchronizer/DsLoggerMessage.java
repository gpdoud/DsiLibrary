// Copyright (c) 2006 by Doud Systems, Inc.  All rights reserved.
package com.doudsystems.dssynchronizer;

import java.util.Date;

public class DsLoggerMessage
{
	private Date dateTimeStamp = null;
	private char severity = 'X';
	private String message = null;
	
	public Date getDateTimeStamp()
	{
		return dateTimeStamp;
	}
	public void setDateTimeStamp(Date dateTimeStamp)
	{
		this.dateTimeStamp = dateTimeStamp;
	}
	public String getMessage()
	{
		return message;
	}
	public void setMessage(String message)
	{
		this.message = message;
	}
	public char getSeverity()
	{
		return severity;
	}
	public void setSeverity(char severity)
	{
		this.severity = severity;
	}
}
