// Copyright (c) 2006 by Doud Systems, Inc.  All rights reserved.
package com.doudsystems.dscontrolcenter;

import javax.swing.JOptionPane;

public class Main
{
	void run()
	{
		DsControlCenter cc = new DsControlCenter();
		cc.Run();
	}

	public static void main(String[] args)
	{
		Main pgm = new Main();
		com.doudsystems.dsutility.DsConsole.printDateTimeStamp = true;
		com.doudsystems.dsutility.DsConsole.WriteLine("Start");
		pgm.run();
		com.doudsystems.dsutility.DsConsole.WriteLine("End");
	}
	
	public void help(String message)
	{
		javax.swing.JOptionPane.showMessageDialog(null,	message, "Help Control Center",
				JOptionPane.INFORMATION_MESSAGE);
	}

}
