package com.doudsystems.dssynchronizer;

import java.text.DateFormat;
import java.util.Date;
// Copyright (c) 2006 by Doud Systems, Inc.  All rights reserved.

import javax.swing.event.EventListenerList;

public class DsLogger
{
	public enum messageSeverity { Informational, Warning, Error, Fatal };

	protected static EventListenerList list = new EventListenerList();
	
	public static void addMessageListener(DsLoggerMessageListener listener)
	{
		list.add(DsLoggerMessageListener.class, listener);
	}
	
	public static void removeMessageListener(DsLoggerMessageListener listener)
	{
		list.remove(DsLoggerMessageListener.class, listener);
	}
	
	private static void fireMessageEvent(DsLoggerMessageEvent evt)
	{
		Object[] listeners = list.getListenerList();
		for(int idx = 0; idx < listeners.length; idx += 2)
			if(listeners[idx] == DsLoggerMessageListener.class)
				((DsLoggerMessageListener) listeners[idx+1]).messageEvent(evt);
	}
	
	public static void message(Object message)
	{
		message(message, messageSeverity.Informational);
	}
	
	public static void message(Object message, messageSeverity severity)
	{
		DsLoggerMessage m = new DsLoggerMessage();
		m.setDateTimeStamp(new Date());
		m.setMessage(message.toString());
		m.setSeverity(messageSeverityToChar(messageSeverity.Informational));
		DsLoggerMessageEvent evt = new DsLoggerMessageEvent(m);
		fireMessageEvent(evt);
		//DateFormat df = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.LONG);
		//String sNow = df.format(now);
	}
	
	private static char messageSeverityToChar(messageSeverity severity)
	{
		switch(severity)
		{
		case Warning:
			return 'W';
		case Error:
			return 'E';
		case Fatal:
			return 'F';
		default:
			return 'I';
		}
	}
}
