package com.example.coin;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Mouse implements MouseListener, MouseMotionListener {
	private static Mouse instance = new Mouse();
	
	private boolean leftPressed = false;
	private boolean rightPressed = false;
	
	private boolean leftClicked = false;
	private boolean rightClicked = false;
	
	
	private int mouseX = 0;
	private int mouseY = 0;
	
	private Mouse() {
		
	}
	
	public static Mouse getInstance() {
		return instance;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1) {
            leftClicked = true;
        }
		
        if(e.getButton() == MouseEvent.BUTTON3) {
            rightClicked = true;
        }
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1) {
            leftPressed = true;
        }
		
        if(e.getButton() == MouseEvent.BUTTON3) {
            rightPressed = true;
        }
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1) {
            leftPressed = false;
        }
		
        if(e.getButton() == MouseEvent.BUTTON3) {
            rightPressed = false;
        }
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
	}

	public void resetButtons() {
		//leftPressed = false;
		//rightPressed = false;
		
		leftClicked = false;
		rightClicked = false;
	}
	
	public boolean isLeftPressed() {
		return leftPressed;
	}

	public boolean isRightPressed() {
		return rightPressed;
	}

	public boolean isLeftClicked() {
		return leftClicked;
	}

	public boolean isRightClicked() {
		return rightClicked;
	}

	public int getMouseX() {
		return mouseX;
	}

	public int getMouseY() {
		return mouseY;
	}
}