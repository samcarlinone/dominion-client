import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Driver extends Canvas implements Runnable
{
	private boolean running = false;
	private Integer frames;
	private Thread thread;
	public JFrame frame;
	
	private Window window;
	private Mouse mouse;
	
	
	public Driver()
	{
		window = new Window("Dominion", this);
		mouse = new Mouse(this);
		this.addMouseListener(mouse);
		
	}
	
	public void tick()
	{
		
	}
	
	public void render() throws IOException
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
		
		BufferedImage specific = ImageIO.read(Driver.class.getResourceAsStream("Test.jpg"));
		g.drawImage(specific, 0, 0, this);
		
		//End Graphics
		g.dispose();
		bs.show();
	}
	
	public synchronized void start() {
		thread = new Thread(this);
		thread.start();
		running = true;
	}
	public synchronized void stop() {
		try {
			thread.join();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
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
				try {
					render();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			frames++;

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.println("FPS: " + frames);
				frames = 0;
			}
		}
		stop();
	}
	public static void main(String[] args)
	{
		new Driver();
	}
	
	
}
