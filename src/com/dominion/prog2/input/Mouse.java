package com.dominion.prog2.input;

import com.dominion.prog2.Driver;

import java.awt.event.*;

public class Mouse implements MouseListener, MouseMotionListener, MouseWheelListener{
	private Driver d;
	
	public Mouse(Driver driver)
	{
		this.d = driver;
	}

	/**
	 * Invoked when the mouse button has been clicked (pressed
	 * and released) on a component.
	 */
	@Override
	public void mouseClicked(MouseEvent e) {

	}

	/**
	 * Invoked when a mouse button has been pressed on a component.
	 */
	@Override
	public void mousePressed(MouseEvent e) {

	}

	/**
	 * Invoked when a mouse button has been released on a component.
	 */
	@Override
	public void mouseReleased(MouseEvent e) {

	}

	/**
	 * Invoked when the mouse enters a component.
	 */
	@Override
	public void mouseEntered(MouseEvent e) {

	}

	/**
	 * Invoked when the mouse exits a component.
	 */
	@Override
	public void mouseExited(MouseEvent e) {

	}

	/**
	 * Invoked when the mouse wheel is rotated.
	 */
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {

	}

	/**
	 * Mouse is moving while pressed.
	 */
	@Override
	public void mouseDragged(MouseEvent e) {

	}

	/**
	 * Mouse is moving while released.
	 */
	@Override
	public void mouseMoved(MouseEvent e) {

	}
}
