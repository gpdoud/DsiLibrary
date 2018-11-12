// Copyright (c) 2006 by Doud Systems, Inc.  All rights reserved.
package com.doudsystems.dsutility;

import java.io.PrintWriter;
import java.util.Calendar;

public class DsConsole
{
	private static PrintWriter out = null;
	public static boolean printDateTimeStamp = false; 
	
	public static void WriteLine(Object object)
	{
		if(printDateTimeStamp)
		{
			Calendar now = Calendar.getInstance();
			out.printf("%4d-%02d-%02d %02d:%02d:%02d %s\n", 
				now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DATE),
				now.get(Calendar.HOUR), now.get(Calendar.MINUTE), now.get(Calendar.SECOND),
				object.toString());
		}
		else
			out.printf("%s\n", object.toString());
	}
	
	static
	{
		out = new PrintWriter(System.out, true);
	}
}
