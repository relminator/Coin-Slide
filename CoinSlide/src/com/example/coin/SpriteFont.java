package com.example.coin;

import java.awt.Graphics2D;


// Wrapper class for font rendering
public class SpriteFont
{

	private ImageAtlas fontAtlas;

	int charWidth = 16;
	int charHeight = 16;

	public SpriteFont(ImageAtlas imageAtlas)
	{
		fontAtlas = imageAtlas;
		charWidth = fontAtlas.getSprite(0).getWidth();
		charHeight = fontAtlas.getSprite(0).getHeight();
	}

	// Prints a string on the screen
	public void print(int x, int y, String text, Screen screen, Graphics2D g)
	{
		int len = text.length();
		for (int i = 0; i < len; i++)
		{
			int index = text.charAt(i) - 32; // offset the index by spacebar(32)
			g.drawImage(fontAtlas.getSprite(index), x, y, screen); // draw the
																	// sprite
			x += charWidth; // next location
		}

	}

	// Centers a string on the screen
	public void printCenter(int x, int y, int screenWidth, String text,
			Screen screen, Graphics2D g)
	{
		int len = text.length();
		x = (screenWidth - (len * charWidth)) / 2;
		for (int i = 0; i < len; i++)
		{
			int index = text.charAt(i) - 32;
			g.drawImage(fontAtlas.getSprite(index), x, y, screen);
			x += charWidth;
		}

	}

	public void printSine(int x, int y, String text, int height, int cycles, int startAngle, Screen screen, Graphics2D g)
	{
		int len = text.length();
		int angleInc = (360/len)*cycles;
		
		for (int i = 0; i < len; i++)
		{
			int index = text.charAt(i) - 32; // offset the index by spacebar(32)
			float ySine = (float)Math.sin(Utils.DEG_TO_RAD * startAngle) * height;  
			g.drawImage(fontAtlas.getSprite(index), x, (int)ySine + y, screen); // draw the
																	// sprite
			x += charWidth; // next location
			startAngle += angleInc;
		}

	}

	public void printCenterSine(int x, int y, int screenWidth, 
			int height, int cycles, int startAngle,
			String text, Screen screen, Graphics2D g)
	{
		int len = text.length();
		int angleInc = (360/len)*cycles;
		x = (screenWidth - (len * charWidth)) / 2;
		for (int i = 0; i < len; i++)
		{
			int index = text.charAt(i) - 32;
			float ySine = (float)Math.sin(Utils.DEG_TO_RAD * startAngle) * height;  
			g.drawImage(fontAtlas.getSprite(index), x, y + (int)ySine, screen);
			x += charWidth;
			startAngle += angleInc;
		}
	}
	
	public void setCharWidth(int charWidth)
	{
		this.charWidth = charWidth;
	}

	public void setCharHeight(int charHeight)
	{
		this.charHeight = charHeight;
	}

	public int getCharWidth()
	{
		return charWidth;
	}

	public int getCharHeight()
	{
		return charHeight;
	}

}
