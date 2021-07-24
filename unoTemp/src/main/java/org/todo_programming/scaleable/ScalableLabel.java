package org.todo_programming.scaleable;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JLabel;

/**
 * Wraps Jlabel for proper scaling 
 * 
 * @author robal
 *
 */
public class ScalableLabel extends JLabel implements ComponentListener
{
	float scaleFactor;														        //Scale label by float percentage 0.50f -> 50 Percent
	private static final String DEFAULT_FONT_NAME = "TimesRoman";					//Default font name
	private static final int DEFAULT_FONT_STYLE = Font.BOLD;						//Default font style
	private static final int DEFAULT_FONT_SIZE = 16;                                //Default font size
	private static final long serialVersionUID = -6063895322407126611L;		        //Class identifier

	/**
	 * Default constructor
	 */
	public ScalableLabel()
	{
		super();
		addComponentListener(this);
		setFont(new Font(DEFAULT_FONT_NAME,DEFAULT_FONT_STYLE, DEFAULT_FONT_SIZE));	
		scaleFactor = 1.00f;
	}
	
	/**
	 * 
	 * @param string to initialize label with
	 */
	public ScalableLabel(String string) 
	{
		super(string);
		addComponentListener(this);
		setFont(new Font(DEFAULT_FONT_NAME, DEFAULT_FONT_STYLE, DEFAULT_FONT_SIZE));	
		scaleFactor = 1.00f;
	}
	
	/**
	 * Default scales to 100% parents size
	 * 
	 * @param scalePercent - scalePercents
	 */
	public ScalableLabel(float scalePercent) 
	{
		super();
		addComponentListener(this);
		setFont(new Font(DEFAULT_FONT_NAME, DEFAULT_FONT_STYLE, DEFAULT_FONT_SIZE));	
		scaleFactor = scalePercent;
	}
	
	
	/**
	 * User defined scaling of label
	 * @param string Text displayed
	 * @param scalePercent s to parent
	 */
	public ScalableLabel(String string, float scalePercent) 
	{
		super(string);
		addComponentListener(this);
		setFont(new Font(DEFAULT_FONT_NAME,DEFAULT_FONT_STYLE, DEFAULT_FONT_SIZE));	
		scaleFactor = scalePercent;
	}
	
	/**
	 * 
	 * @param string default label text
	 * @param fontName as string
	 */
	public ScalableLabel(String string, String fontName) 
	{
		super(string);
		addComponentListener(this);
		setFont(new Font(fontName, DEFAULT_FONT_STYLE, DEFAULT_FONT_SIZE));	
		scaleFactor = 1.00f;
	}
	
	public ScalableLabel(String string, float scalePercent, String fontName, int fontStyle, int fontSize) 
	{
		super(string);
		addComponentListener(this);
		setFont(new Font(fontName, fontStyle, fontSize));
		scaleFactor = scalePercent;
	}
		
	/**
	 * Logic to scale this label
	 */
	@Override
	public void componentResized(ComponentEvent e) 
	{
		//Parent Dimensions
		float parentWidth = this.getParent().getWidth();
		float parentHeight = this.getParent().getHeight();
		
		//Maintain font attributes that are not size
		Font previouseFont = getFont();
		setFont(new Font(previouseFont.getFontName(),previouseFont.getStyle(), (int)(parentWidth * scaleFactor)));
		
		//Update size
		Dimension lblDim = new Dimension();
		lblDim.setSize(parentWidth, parentHeight);
		setPreferredSize(lblDim);
		revalidate();
		
	}
	
	/**
	 * Unimplemented method
	 */
	@Override
	public void componentMoved(ComponentEvent e) 
	{	
	}
	
	/**
	 * Unimplemented method
	 */
	@Override
	public void componentShown(ComponentEvent e) 
	{
	}
	
	/**
	 * Unimplemented method
	 */
	@Override
	public void componentHidden(ComponentEvent e) 
	{
	}

}
