import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Mouse implements MouseListener
{
	private Driver d;
	
	public Mouse(Driver driver)
	{
		this.d = driver;
	}
	
	public void mouseClicked(MouseEvent arg0)
	{
		
	}

	
	//All below not needed
	public void mouseEntered(MouseEvent arg0) {
		
	}
	
	public void mouseExited(MouseEvent arg0) {
		
	}


	public void mousePressed(MouseEvent arg0) {
		//used for dragging
	}


	public void mouseReleased(MouseEvent arg0) {
		//used for dragging
	}

}
