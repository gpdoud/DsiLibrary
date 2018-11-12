// Copyright (c) 2006 by Doud Systems, Inc.  All rights reserved.
package com.doudsystems.dssynchronizer;

import java.util.EventListener;

public interface DsLoggerMessageListener extends EventListener
{
	public void messageEvent(DsLoggerMessageEvent evt);
}
