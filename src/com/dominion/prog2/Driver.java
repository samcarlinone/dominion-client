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
		g.drawImage(img, window.getWidth()/2 - 164/2 - 164 -10,0, this);
		getSpecificImage("silver");
		g.drawImage(img, window.getWidth()/2 - 164/2,0, this);
		getSpecificImage("gold");
		g.drawImage(img, window.getWidth()/2 - 164/2 + 164 + 10,0, this);
		getSpecificImage("estate");
		g.drawImage(img, window.getWidth()/2 - 164/2 - 164 - 10,243 +10, this);
		getSpecificImage("duchy");
		g.drawImage(img, window.getWidth()/2 - 164/2,243 + 10, this);
		getSpecificImage("province");
		g.drawImage(img, window.getWidth()/2 - 164/2 + 164 + 10,243 +10, this);

		getSpecificImage("trash");
		g.drawImage(img, window.getWidth()/2 - 164/2 + 164*5 + 10*5,243/2 +10, this);
		getSpecificImage("back");
		g.drawImage(img, window.getWidth()/2 - 164/2 - 164*5 - 10*5,243/2 +10, this);






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
				img = ImageIO.read(new File("res/cards/Copper.jpg"));
			else if(imageName == "silver")
				img = ImageIO.read(new File("res/cards/Silver.jpg"));
			else if(imageName == "gold")
				img = ImageIO.read(new File("res/cards/Gold.jpg"));
			else if(imageName == "estate")
				img = ImageIO.read(new File("res/cards/Estate.jpg"));
			else if(imageName == "duchy")
				img = ImageIO.read(new File("res/cards/Duchy.jpg"));
			else if(imageName == "province")
				img = ImageIO.read(new File("res/cards/Province.jpg"));
			else if(imageName == "trash")
				img = ImageIO.read(new File("res/cards/Trash.jpg"));
			else if(imageName == "back")
				img = ImageIO.read(new File("res/cards/Card_back.jpg"));


		} catch(IOException e) {
			System.out.println("Image fail!");
			System.exit(1);
		}
		int height = (window.getHeight()/4);
		int width = ((height*125)/200);
		img = resize(img, width, height-20);

		//card ratio: 125:200

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
