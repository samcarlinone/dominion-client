import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Point;

import javax.swing.JFrame;


public class Window extends Canvas
{
	private int width = 600;
	private int height = 400;

	private static final long serialVersionUID = 4157042182766335151L;

	private Driver d;
	private String title;
	private JFrame frame;

	double scale = 1.0;
	
	public Window(String title, Driver game)
	{
		this.title = title;
		this.d = game;
		
		
		frame = new JFrame(this.title);
		
		Dimension dim = new Dimension((int)(width*scale), (int)(height*scale));
		
		frame.setPreferredSize(dim);
		frame.setMaximumSize(dim);
		frame.setMinimumSize(dim);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		
		frame.setLocationRelativeTo(null);
		//frame.move(0,0);
		frame.add(d);
		frame.setVisible(true);
		
		d.start();
	}
	
	public void tick(double s)
	{
		scale = s;
		
		frame.setSize((int)(width*scale), (int)(height*scale));
		//frame.setLocationRelativeTo(null);
	}
	public Point getLocation()
	{
		return frame.getLocationOnScreen();
	}
	
	public double getScale()
	{
		return scale;
	}
	public void addScale(double s)
	{
		scale += s;
		
		if(scale < 1.5)
			scale = 1.5;
		if(scale > 3)
			scale = 3;
	}
	public JFrame getFrame()
	{
		return frame;
	}
	public int getHeight()
	{
		return (int) (height * scale);
	}
	public int getWidth()
	{
		return (int) (width* scale);
	}	
}