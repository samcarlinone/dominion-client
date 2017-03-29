package com.dominion.prog2;

import com.dominion.prog2.input.Keyboard;
import com.dominion.prog2.input.Mouse;
import com.dominion.prog2.game.Window;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Driver extends Canvas implements Runnable
{
	private boolean running = false;
	private Integer frames;
	private Thread thread;
	
	private Window window;
	private Mouse mouse;
	private Keyboard keyboard;
	private BufferedImage img;

	/**
	 *	Constructor of Driver
	 *	Initiates Window, Mouse, Keyboard, handlers
	 */
	public Driver()
	{
		window = new Window("Dominion", this);
		mouse = new Mouse(this);
		this.addMouseListener(mouse);
		keyboard = new Keyboard(this);
		this.addKeyListener(keyboard);

	}

	/**
	 *	updates classes/variables that change per frame
	 */
	public void tick()
	{

	}

	/**
	 *	Draws the game onto the window
	 *	Calls other hanlder render to draw their parts
	 */
	public void render()
    {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null)
		{
			this.createBufferStrategy(2);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		//Start Graphics
		
		g.setColor(Color.RED);
		g.fillRect(0, 0, window.getWidth(), window.getHeight());

		getSpecificImage("copper");
		g.drawImage(img, 0,0, this);
		getSpecificImage("silver");

		g.drawImage(img, 0,200, this);




		//End Graphics
		g.dispose();
		bs.show();
	}

	/**
	 *	Gets an image to draw based off what is being drawn
	 */
	public void getSpecificImage(String imageName)
	{
		try {
			if(imageName == "copper")
				img = ImageIO.read(new File("res/Copper.jpg"));
			if(imageName == "silver")
				img = ImageIO.read(new File("res/Silver.jpg"));
		} catch(IOException e) {
			System.out.println("Image fail!");
			System.exit(1);
		}
		img = resize(img, 125, 200);

	}

	/**
	 *	resize image to wanted size
	 */
	public static BufferedImage resize(BufferedImage img, int newW, int newH) {
		int w = img.getWidth();
		int h = img.getHeight();
		BufferedImage dimg = new BufferedImage(newW, newH, img.getType());
		Graphics2D g = dimg.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g.drawImage(img, 0, 0, newW, newH, 0, 0, w, h, null);
		g.dispose();
		return dimg;
	}

	/**
	 *	Starts thread
	 */
	public synchronized void start() {
		thread = new Thread(this);
		thread.start();
		running = true;
	}

	/**
	 *	Stops thread
	 */
	public synchronized void stop() {
		try {
			thread.join();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 *	Important game function that calls the render and tick methods
	 */
	public void run() {
		this.requestFocus();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		frames = 0;
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				tick();
				delta--;
			}
			if (running)
			    render();

			frames++;

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.println("FPS: " + frames);
				frames = 0;
			}
		}
		stop();
	}

	/**
	 *	Starts up the whole Client side of things
	 */
	public static void main(String[] args)
	{
		new Driver();
	}
}
