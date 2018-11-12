// Copyright (c) 2006 by Doud Systems, Inc.  All rights reserved.
package com.doudsystems.dscontrolcenter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.lang.reflect.Method;

public class DsControlCenter
{
	JFrame frame = null;
	JComponent panel = null;
	JTabbedPane tpane = null;
	JMenuBar mnuBar = null;
	JMenu mneApplications = null;
	
	private void InitAndRun()
	{
		String lookAndFeel = UIManager.getCrossPlatformLookAndFeelClassName();
		try
		{ UIManager.setLookAndFeel(lookAndFeel); }
		catch (Exception e) {};
		//JFrame.setDefaultLookAndFeelDecorated(false);

		frame = createFrame("DS Control Center");
		
		createMenu(frame);
		
		tpane = createTabbedPane(frame);
		
		//frame.pack();
		frame.setVisible(true);
	}
	
	private JFrame createFrame(String title)
	{
		
		JFrame frame = new JFrame(title);
		frame.setSize(800, 600);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		return frame;
	}
	
	private JTabbedPane createTabbedPane(JFrame frame)
	{
		tpane = new JTabbedPane();
		//ImageIcon icon = new ImageIcon("image.gif");
		frame.getContentPane().add(tpane);
		
		panel = createPanel("Control Center");
        tpane.addTab("Control", panel);

        addApplicationPlugins(tpane);

        return tpane;
	}
	
	private void addApplicationPlugins(JTabbedPane tpane)
	{
		DsControlCenterConfiguration config = new DsControlCenterConfiguration();
		for(DsApplicationPlugin plugin : config.getPlugins())
		{	
			JComponent panel = createPanel(plugin.getClassName());
			tpane.addTab(plugin.getName(), panel);
			
			JMenuItem menuItem = createMenuItem(mneApplications, plugin.getName(), ' ');
		}
	}
	
	private void createMenu(JFrame frame)
	{
		mnuBar = new JMenuBar();
		frame.setJMenuBar(mnuBar);
		
		// FILE
		
		JMenu mnuFile = createMenu(mnuBar, "File", 'f');
		JMenuItem mnuFileLoad = createMenuItem(mnuFile, "Load", 'l');
		mnuFileLoad.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e)
			{
				mnuFileLoadClick(e);
			}
		});
		JMenuItem mnuFileExit = createMenuItem(mnuFile, "Exit", 'x');
		mnuFileExit.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e)
			{
				mnuFileExitClick(e);
			}
		});
		
		// EDIT
		
		JMenu mnuEdit = createMenu(mnuBar, "Edit", 'e');
		JMenuItem mnuEditCut = createMenuItem(mnuEdit, "Cut", 'x');
		JMenuItem mnuEditCopy = createMenuItem(mnuEdit, "Copy", 'c');
		JMenuItem mnuEditPaste = createMenuItem(mnuEdit, "Paste", 'v');
		
		// APPLICATIONS
		
		mneApplications = createMenu(mnuBar, "Applications", 'A');
		
		// HELP
		
		JMenu mnuHelp = createMenu(mnuBar, "Help", 'h');
		JMenuItem mnuHelpAbout = createMenuItem(mnuHelp, "About", '?');
		mnuHelpAbout.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e)
			{
				mnuHelpAboutClick(e);
			}
		});
	}
	
	private void mnuFileLoadClick(ActionEvent e)
	{
		int lastPane = tpane.getComponentCount() - 1;
		JPanel panel = (JPanel) tpane.getComponent(lastPane);
		JMenu menu = mnuBar.getMenu(2);
		Class cls = null;
		try
		{
			cls = Class.forName("com.doudsystems.dscontrolcenter.plugins.HelloWorldPlugin");
		} catch (ClassNotFoundException ex) { ex.printStackTrace(); }

		IDsControlCenterPlugin plugin = null;
		try
		{
			plugin = (IDsControlCenterPlugin) cls.newInstance();
		} 
		catch (IllegalAccessException ex) { ex.printStackTrace(); }
		catch (InstantiationException ex) { ex.printStackTrace(); }
		
		plugin.setContentPane(panel);
		plugin.setMenu(menu);
		
		Thread thread = new Thread(plugin);
		thread.start();
	}
	
	private void mnuFileExitClick(ActionEvent e)
	{
		System.exit(0);
	}
	
	private void mnuHelpAboutClick(ActionEvent e)
	{
		JOptionPane.showMessageDialog(frame, "Help goes here...", "DS Control Center", JOptionPane.INFORMATION_MESSAGE);
	}

	private JMenu createMenu(JMenuBar mnuBar, String text)
	{
		return createMenu(mnuBar, text, ' ');
	}
	
	private JMenu createMenu(JMenuBar mnuBar, String text, char mnemonic)
	{
		JMenu mnu = new JMenu(text);
		if(mnemonic != ' ')
			mnu.setMnemonic(mnemonic);
		mnuBar.add(mnu);
		return mnu;
	}
	
	private JMenuItem createMenuItem(JMenu mnu, String text)
	{
		return createMenuItem(mnu, text, ' ');
	}
	
	private JMenuItem createMenuItem(JMenu mnu, String text, char mnemonic)
	{
		JMenuItem mnuItem = new JMenuItem(text);
		mnu.add(mnuItem);
		if(mnemonic != ' ')
			mnuItem.setMnemonic(mnemonic);
		return mnuItem;
	}
	
	private JPanel createPanel(String tabTitle)
	{
        JPanel panel = new JPanel(false);
        JLabel filler = new JLabel(tabTitle);
        filler.setHorizontalAlignment(JLabel.CENTER);
        panel.setLayout(new GridLayout(1, 1));
        panel.add(filler);
        return panel;
	}
	
	public void Run()
	{
		javax.swing.SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				InitAndRun();
			}
		});
	}

}
