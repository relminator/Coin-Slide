package com.example.coin;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

// simpleng class para i-wrap ang keyboard functionalities
public class Keyboard implements KeyListener
{
	// singleton 'to papz
	private static Keyboard instance = new Keyboard();

	// poll the keys
	private boolean[] keysPressed = new boolean[256];
	private boolean[] keysReleased = new boolean[256];

	private boolean[] buttonKP= new boolean[256];
	private boolean[] buttonKC= new boolean[256];
	
	// check para sa kahit anong key
	private boolean anyKeyPressed = false;

	private Keyboard()
	{

	}

	public static Keyboard getInstance()
	{
		return instance;
	}

	// kailangan dahil sa keylistener interface
	@Override
	public void keyPressed(KeyEvent e)
	{
		// & 255 limits the input to 0 - 255
		// para walang array out of bounds
		int keyId = (e.getKeyCode() & 255);
		keysPressed[keyId] = true;
		keysReleased[keyId] = false;
		anyKeyPressed = true;
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		int keyId = (e.getKeyCode() & 255);
		keysPressed[keyId] = false;
		keysReleased[keyId] = true;
		anyKeyPressed = false;
	}

	@Override
	public void keyTyped(KeyEvent e)
	{
		
	}

	public boolean isKeyPressed(int key)
	{
		return keysPressed[key];
	}

	public boolean isKeyReleased(int key)
	{
		return keysReleased[key];
	}

	public boolean isKeyDown( int key )
	{
		key = key & 255;
		buttonKP[key] = buttonKC[key];
		buttonKC[key] = keysPressed[key];
		return ( buttonKC[key] != false ) && ( buttonKP[key] == false ) ? true : false;
	}
	
	public boolean isAnyKeyPressed()
	{
		return anyKeyPressed;
	}

	public void resetKeys()
	{
		for (int i = 0; i < 256; i++)
		{
			keysReleased[i] = false;
		}
	}

}
