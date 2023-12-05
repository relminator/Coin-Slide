package com.example.coin;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;
import javax.swing.JPanel;


/**
 * @author Anya
 *
 */

public class Screen extends Canvas 
{

	private static final long serialVersionUID = 1L;

	private BufferStrategy strategy;
	
	Game engine = new Game();
	
	public Screen() 
	{

		JFrame container = new JFrame("Follow The Path Coordinate Editor");

		JPanel panel = (JPanel) container.getContentPane();
		panel.setPreferredSize(new Dimension(Constants.SCREEN_WIDTH,
				Constants.SCREEN_HEIGHT));
		panel.setLayout(null);

		setBounds(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);

		panel.add(this);

		setIgnoreRepaint(true);
		container.setResizable(false);
		container.setVisible(true);
		container.pack();
		container.setLocationRelativeTo(null);
		
		container.addWindowListener(new WindowAdapter() 
		{
			public void windowClosing(WindowEvent e) 
			{
				System.exit(0);
			}
		});

		addKeyListener( engine.getKeyHandler() );
		addMouseListener(engine.getMouseHandler());
		addMouseMotionListener(engine.getMouseHandler());
		
		requestFocus();
		setFocusable(true);
		
		createBufferStrategy(2);
		strategy = getBufferStrategy();
		
		
	}

	public void playGame() 
	{

		engine.run( strategy, this );
		
	}

}
