// Copyright (c) 2006 by Doud Systems, Inc.  All rights reserved.
package com.doudsystems.dscontrolcenter.plugins;

import java.awt.*;
import java.awt.event.*;
import java.util.TimerTask;

import javax.swing.*;

import com.doudsystems.dscontrolcenter.IDsControlCenterPlugin;

public class HelloWorldPlugin implements IDsControlCenterPlugin
{
	boolean keepRunning = true;

	public void run()
	{
		Thread thread = Thread.currentThread();
		while(keepRunning)
		{
			label.setText("Thread running ");
			for(int idx = 0; idx < 10; idx++)
			{
				label.setText(label.getText() + ".");
				thread.yield();
				try
				{ thread.sleep(1000); }
				catch (InterruptedException ex) {}
			}
			label.setText("Thread running ");
		}
		label.setText("Thread stopped. ");
	}
	
	public void setMenu(JMenu menu)
	{
		JMenuItem pluginMenu = new JMenuItem("Hello world plugin");
		menu.add(pluginMenu);
	}

	JLabel label = null;
	public void setContentPane(JPanel panel)
	{
		panel.removeAll();
		panel.setLayout(new FlowLayout());
		
		JButton button = new JButton("Shutdown");
		panel.add(button);
		button.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent evt)
			{
				buttonClicked();
			}
		});
		
		label = new JLabel("Label");
		panel.add(label);
		
	}
	
	void buttonClicked()
	{
		keepRunning = false;
		label.setText("Thread stopped scheduled. ");
	}

}
