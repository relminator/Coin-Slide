package com.example.coin;

public class Coin 
{
	private float x;
	private float y;
	private float radius;
	private boolean active = true;
	private float rotation;
	public Coin()
	{
		radius = 50;    //100 diameter 
		rotation = (float) (Math.random() * (Math.PI * 2));
	}
	
	public void update()
	{
		if(active) rotation += 0.03f;
	}
	
	public void setPosition(float x, float y)
	{
		this.x = x;
		this.y = y;
	}
	
	public boolean collidesWith( Coin c )
	{
		float dx = c.getX() - x;
		float dy = c.getY() - y;
		
		return (dx * dx + dy * dy) < ((radius*2)*(radius*2));
	}

	public boolean isPointInside( float mx, float my )
	{
		float dx = mx - x;
		float dy = my - y;
		
		return (dx * dx + dy * dy) < ( radius * radius );
	}

	public boolean isActive() 
	{
		return active;
	}

	public void setActive(boolean active) 
	{
		this.active = active;
	}

	public float getX() 
	{
		return x;
	}

	public float getY() 
	{
		return y;
	}

	public float getRotation() 
	{
		return rotation;
	}
	
	
	
}
