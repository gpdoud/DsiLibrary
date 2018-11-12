// Copyright (c) 2006 by Doud Systems, Inc.  All rights reserved.
package com.doudsystems.dscontrolcenter;

public interface IDsControlCenterPlugin extends Runnable
{
	public void setContentPane(javax.swing.JPanel panel);
	public void setMenu(javax.swing.JMenu menu);
}
