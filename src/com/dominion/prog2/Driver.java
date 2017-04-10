package com.dominion.prog2;

import com.dominion.prog2.input.Keyboard;
import com.dominion.prog2.input.Mouse;
import com.dominion.prog2.game.Window;
import com.dominion.prog2.modules.*;
import com.dominion.prog2.network.NodeCommunicator;
import com.dominion.prog2.ui.ImageCache;
import com.dominion.prog2.ui.UIElement;
import com.dominion.prog2.ui.UIManager;
import com.sun.org.apache.xpath.internal.operations.Mod;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

public class Driver extends Canvas implements Runnable
{
	private boolean running = false;
	private Integer frames;
	private Thread thread;
	private boolean ready = false;
	
	private Window window;
	private Mouse mouse;
	private Keyboard keyboard;
	private BufferedImage img;
	private Module currentModule;
	private int timeSincePing;
    public NodeCommunicator comm;
	public String name;

	/**
	 *	Constructor of Driver
	 *	Initiates Window, Mouse, Keyboard, handlers
	 */
	public Driver()
	{
		window = new Window("Dominion", this);
		mouse = new Mouse(this);
		keyboard = new Keyboard(this);
        ImageCache.readImages(this);

		comm = new NodeCommunicator();
		timeSincePing = 15;

//        HashMap<String, String> name = new HashMap<>();
//        name.put("type", "connect");
//        name.put("name", "fred");
//
//		String json = comm.mapToJSON(name);
//
//		System.out.println(comm.getMessage(json));

		currentModule = new ChooseName(this);

		ready = true;
	}

	/**
	 *	updates classes/variables that change per frame
	 */
	public void tick() {
	    if(ready) {
            ArrayList<HashMap<String, String>> server_msg = null;

	        if(name != null && !name.equals("") && timeSincePing-- == 0) {
                //Ping server
				HashMap<String, String> msg = new HashMap<>();
				msg.put("type", "ping");
				msg.put("name", name);

				String json = comm.getMessage(comm.mapToJSON(msg));

				if(!json.equals("Error")) {
					server_msg = comm.JSONToMap(json);
				}

				timeSincePing = 15;
            }

	        currentModule = currentModule.tick(server_msg);
	        
	        //Resizes the Window based off of the Game State
			if((currentModule instanceof ChooseName || currentModule instanceof ChooseLobby)&& window.getWidth() != 500)
				window.resizeWindow(500,700);
			else if(currentModule instanceof WaitScreen && window.getWidth() != 505)
				window.resizeWindow(505,700);
			else if(currentModule instanceof Game && window.getWidth() != 1800)
				window.resizeWindow(1800,1100);

            UIManager.get().tick();
        }
	}


	/**
	 *	Draws the game onto the window
	 *	Calls other handler render to draw their parts
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
		
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, window.getWidth(), window.getHeight());

		if(ready) {
            currentModule.render(g);

            UIManager.get().render(g);
        }

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

	/**
	 * getter for window
	 * @return window
	 */
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
