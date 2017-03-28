package com.dominion.prog2.game;

import com.dominion.prog2.Driver;

import java.awt.*;

import javax.swing.JFrame;


public class Window extends Canvas
{
	private final int width;
	private final int height;

	private Driver d;
	private String title;
	private JFrame frame;
	
	public Window(String title, Driver game)
	{
		this.title = title;
		this.d = game;

		
		frame = new JFrame(this.title);
		frame.pack();

		width = 1000;
		height = 1000 + frame.getInsets().top;

		Dimension dim = new Dimension(width, height);
		frame.setSize(dim);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		
		frame.setLocationRelativeTo(null);
		frame.add(d);
		frame.setVisible(true);
		
		d.start();
	}
	public Point getLocation()
	{
		return frame.getLocationOnScreen();
	}
	

	public JFrame getFrame()
	{
		return frame;
	}
	public int getHeight()
	{
		return height;
	}
	public int getWidth()
	{
		return width;
	}	
}