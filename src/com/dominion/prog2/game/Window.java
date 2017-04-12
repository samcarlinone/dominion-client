package com.dominion.prog2.game;

import com.dominion.prog2.Driver;

import java.awt.*;

import javax.swing.JFrame;


public class Window extends Canvas
{
	private int width;
	private int height;

	private Driver d;
	private String title;
	private JFrame frame;

	/**
	 *	Constructor for Window
	 *	adds title and changes the size of the window
	 *	set window into middle of screen
	 *	starts the Driver
	 * @param title for the window
	 * @param gameDriver
	 */
	public Window(String title, Driver gameDriver)
	{
		this.title = title;
		this.d = gameDriver;

		
		frame = new JFrame(this.title);
		frame.pack();

		width = 500;
		height = 700 + frame.getInsets().top;

		frame.setSize(width,height);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		
		frame.setLocationRelativeTo(null);
		frame.add(d);
		frame.setVisible(true);
		
		d.start();
	}

	/**
	 * Finds location of the window in relation to screen
	 * @return the location of the window
	 */
	public Point getLocation()
	{
		return frame.getLocationOnScreen();
	}

	/**
	 *	Resizes window
	 * @param w width of window
	 * @param h height of window
	 */
	public void resizeWindow(int w, int h)
	{
		System.out.println("Resizing");
		width = w;
		height = h+frame.getInsets().top;

		frame.resize(width, height);
		frame.setLocationRelativeTo(null);
	}


	/**
	 * Access the Height of the window
	 * @return int of height
	 */
	public int getHeight()
	{
		return height;
	}

	/**
	 * Access the Width of the window
	 * @return int of width
	 */
	public int getWidth()
	{
		return width;
	}	
}