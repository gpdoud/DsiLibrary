// Copyright (c) 2006 by Doud Systems, Inc.  All rights reserved.
package com.doudsystems.dssynchronizer;

public class DsException extends Exception
{

	public DsException()
	{
	}

	public DsException(String message)
	{
		super(message);
	}

	public DsException(Throwable cause)
	{
		super(cause);
	}

	public DsException(String message, Throwable cause)
	{
		super(message, cause);
	}

}
