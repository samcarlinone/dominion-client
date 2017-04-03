package com.dominion.prog2;

import com.dominion.prog2.input.Keyboard;
import com.dominion.prog2.input.Mouse;
import com.dominion.prog2.game.Window;
import com.dominion.prog2.game.Game;
import com.dominion.prog2.ui.ImageCache;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Driver extends Canvas implements Runnable
{
	private boolean running = false;
	private Integer frames;
	private Thread thread;
	
	private Window window;
	private Mouse mouse;
	private Keyboard keyboard;
	private BufferedImage img;
	private Game game;


	/**
	 *	Constructor of Driver
	 *	Initiates Window, Mouse, Keyboard, handlers
	 */
	public Driver()
	{
		window = new Window("Dominion", this);
		mouse = new Mouse(this);
		keyboard = new Keyboard(this);
		this.addKeyListener(keyboard);
		game = new Game(this);
		ImageCache.readImages(this);
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

		if(game != null)
			game.render(g);





		//End Graphics
		g.dispose();
		bs.show();
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

	public Window getWindow()
	{
		return window;
	}

	/**
	 *	Starts up the whole Client side of things
	 */
	public static void main(String[] args)
	{
		new Driver();
	}
}
