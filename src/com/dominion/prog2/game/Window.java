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

	/**
	 *	Constructor for Window
	 *	adds title and changes the size of the window
	 *	set window into middle of screen
	 *	starts the Driver
	 */
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

	/**
	 *
	 */
	public Point getLocation()
	{
		return frame.getLocationOnScreen();
	}

	/**
	 * Access the Height and Width of the window
	 */

	public int getHeight()
	{
		return height;
	}
	public int getWidth()
	{
		return width;
	}	
}