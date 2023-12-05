package com.example.coin;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Richard Eric M. Lope
 *
 */

public class Game
{

	private double accumulator = 0;

	@SuppressWarnings("unused")
	private int fps = 0;
	private int framesPerSecond = 0;
	private double previousTime = 0;
	private double oldTime = 0;
	
	@SuppressWarnings("unused")
	private int ticks = 0;
	
	private SpriteFont fontKrom = new SpriteFont(new ImageAtlas(new ImageTextureDataDefault("gfx/font.png"), 16, 16));
	private ImageAtlas bgImage = new ImageAtlas(new ImageTextureDataDefault("gfx/bg.png"), 800, 480);
	private ImageAtlas coinImage = new ImageAtlas(new ImageTextureDataDefault("gfx/coin.png"), 100, 100);
	
	List<Coin> coins = new ArrayList<Coin>();
	
	public Game()
	{
	
	}

	public void init( BufferStrategy strategy, Screen screen )
	{
		 
		Graphics2D g2D = (Graphics2D) strategy.getDrawGraphics();
		g2D.setColor(Color.BLACK);
		g2D.fillRect(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);

		Toolkit.getDefaultToolkit().sync();

		g2D.dispose();
		strategy.show();
		
		reset();
	}
	
	private void reset()
	{	
		ticks = 0;
		coins.clear();
		
		
		for( int i = 0; i < 9; i++ )
		{
			Coin c = new Coin();
			c.setActive(false);
			c.setPosition( (float)Math.random()*Constants.SCREEN_WIDTH, 
						   (float)Math.random()*Constants.SCREEN_HEIGHT );
			coins.add(c);
		}
		
		
		
	}
	
		
	private void update() 
	{
		ticks++;
		
		for( int i = 0; i < coins.size(); i++)
		{
			Coin c = coins.get(i);
			c.update();
		}
		/*
		if( Mouse.getInstance().isLeftClicked() )
		{
			coords.add(new Vector2D( Mouse.getInstance().getMouseX(),
									 Mouse.getInstance().getMouseY()) );
		}
		if( Mouse.getInstance().isRightClicked() )
		{
			if( coords.size() > 0 ) coords.remove(coords.size()-1);
		}
		*/
		
		if(Keyboard.getInstance().isKeyDown(KeyEvent.VK_ESCAPE))
		{
			System.exit(0);
		}
		
		Keyboard.getInstance().resetKeys();
		Mouse.getInstance().resetButtons();
	}
	
	public void run( BufferStrategy strategy, Screen screen ) 
	{

		double dt = getDeltaTime(getSystemTime());
		
		init( strategy, screen  );
		
		while( true ) 
		{

			dt = getDeltaTime(getSystemTime());
			if( dt > Constants.FIXED_TIME_STEP ) dt = Constants.FIXED_TIME_STEP;
			
			accumulator += dt;
			
			while( accumulator >= Constants.FIXED_TIME_STEP ) 
			{

				accumulator -= Constants.FIXED_TIME_STEP;
				update();
								
			}

			
			Graphics2D g2D = (Graphics2D) strategy.getDrawGraphics();
			g2D.setColor(Color.BLACK);
			g2D.fillRect(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);

			render( g2D, screen );
			g2D.dispose();
			strategy.show();
			Toolkit.getDefaultToolkit().sync();
			

			try 
			{
				Thread.sleep(14);
			} 
			catch (InterruptedException e)
			{
				System.out.println("interrupted");
			}
			
		}

	}

	
	
	public Keyboard getKeyHandler()
	{
		return Keyboard.getInstance();
	}
	
	public Mouse getMouseHandler() 
	{
		return Mouse.getInstance();
	}
	
	public double getSystemTime() 
	{
		return System.nanoTime() / 1000000000.0;
	}

	public double getDeltaTime( double timerInSeconds ) 
	{

		double currentTime = timerInSeconds;
		double elapsedTime = currentTime - oldTime;
		oldTime = currentTime;

		framesPerSecond++;

		if ((currentTime - previousTime) > 1.0) 
		{
			previousTime = currentTime;
			fps = framesPerSecond;
			framesPerSecond = 0;
		}

		return elapsedTime;
	}
	
	private void render( Graphics2D g2D, Screen screen ) 
	{
		
		g2D.drawImage( bgImage.getSprite(0), 0, 0, screen );
		
		
		
		for( int i = 0; i < coins.size(); i++)
		{
			Coin c = coins.get(i);
			int x = (int) c.getX();
			int y = (int) c.getY();
			
			BufferedImage sprite = coinImage.getSprite(0);
			int w2 = sprite.getWidth() / 2;
			int h2 = sprite.getHeight() / 2;
			float scale = 1f;
			AffineTransform playerTransform = new AffineTransform();
			playerTransform.setToIdentity();
			playerTransform.translate(x - w2*scale, 
									  y - h2*scale);
			playerTransform.scale(scale, scale);
			playerTransform.rotate(c.getRotation(), w2*scale, h2*scale);
			g2D.drawImage(sprite, playerTransform, screen);
			
			
		}
		
		fontKrom.printCenter(0, 10, Constants.SCREEN_WIDTH,
				"USE LEFT MOUSE BUTTON TO DRAG", screen, g2D);
		
		/*
		if( Mouse.getInstance().isLeftPressed() )
		{
			fontKrom.print( 150, 130,
					  "LEFT PRESSED",
					  screen, g2D );
		}
		*/
		
		dragActive();
		checkMouseCollision( g2D, screen );
		
	}

	private void checkMouseCollision( Graphics2D g2D, Screen screen )
	{
		
		for( int i = 0; i < coins.size(); i++)
		{
			int x = Mouse.getInstance().getMouseX();
			int y = Mouse.getInstance().getMouseY();
			Coin c = coins.get(i);
			
		
			if( c.isPointInside(x, y) )
			{
				
				if( !alreadyPicked() ) 
				{
					c.setActive(true);
				}
				
			}
			else
			{
				boolean pwede = true;
				for( int j = 0; j < coins.size(); j++)
				{
					if(j == i) continue;
					Coin c2 = coins.get(j);
					if( c.collidesWith(c2) )
					{
						//get angle
						double angle = Math.atan2(c.getY() - c2.getY(), c.getX() - c2.getX());
						double sx = Math.cos(angle);
						double sy = Math.sin(angle);
						c.setPosition(c2.getX() + (float)sx * 100, c2.getY() +(float)sy*100);
						pwede = true;
					}
				}
				if( pwede ) c.setActive(false);
			}
			
			
		}
	}
	
	private boolean alreadyPicked()
	{
		for( int i = 0; i < coins.size(); i++)
		{
			if( coins.get(i).isActive() ) return true;
		}
		return false;
	}

	private void dragActive()
	{
		for( int i = 0; i < coins.size(); i++)
		{
			Coin c = coins.get(i);
			if( c.isActive() )
			{
				int x = Mouse.getInstance().getMouseX();
				int y = Mouse.getInstance().getMouseY();
				
				if( Mouse.getInstance().isLeftPressed() )
				{
					c.setPosition(x, y);
				}
			}
		}
		
	}
}
