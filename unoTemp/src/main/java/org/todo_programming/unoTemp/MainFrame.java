package org.todo_programming.unoTemp;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

import org.todo_programming.scaleable.ScalableLabel;

/**
 * GUI monitor that displays temperature data collected
 * @author robal
 *
 */
public class MainFrame extends JFrame implements PropertyChangeListener, KeyListener
{

	/** ID for class*/
	private static final long serialVersionUID = 6244897348903266447L;
	
	private TempBean tempBean;					//Data associated with the view
	private ScalableLabel lblTemp;				//Label for temperature
	private ScalableLabel lblHumidity;			//Label for humidity
	
	/**
	 * 
	 * @param commPort - Serial port where the DHt11 data is coming from
	 * @param data - contains updated Temperature data after it is collected from the Serial Port
	 */
	public MainFrame(String commPort,TempBean data)
	{
		tempBean = data;
		data.addPropertyChangeListener(this);
		
		
		
		/** Label initialization*/
		lblTemp = new ScalableLabel("0F",0.20f);
		lblHumidity = new ScalableLabel("0%",0.20f);
		
		/** layout Init */
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints constraints = new GridBagConstraints();
		setLayout(layout);
		
		/** Window Dimensions*/
		Dimension dim = new Dimension(300,300);
		setPreferredSize(dim);
		setSize(300, 300);
		
		/** Common constraints */
		constraints.fill = GridBagConstraints.RELATIVE;
		constraints.weightx = 1.0;
		constraints.weighty = 1.0;

		/** Add Elements */
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.insets = new Insets(1,2,1,1);
		add(lblTemp,constraints);
		
		constraints.gridx = 1;
		constraints.gridy = 0;
		constraints.insets = new Insets(1,1,1,1);
		add(lblHumidity,constraints);
		
		getRootPane().setBorder(BorderFactory.createMatteBorder(7, 7, 7, 7, Color.BLUE));
		getContentPane().setBackground(new Color(86,250, 187));
		setIconImage(new ImageIcon(getClass().getClassLoader().getResource("img/therm.png")).getImage());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		addKeyListener(this);
		setTitle("Temperature " + commPort);
		setVisible(true);
	}
	
	/**
	 * Fired when temperature or humidity updated
	 */
	public void propertyChange(PropertyChangeEvent e) 
	{
		//Temperature data updated
		if(TempBean.UPDATED_TEMPERATURE.equals(e.getPropertyName()))
		{
			javax.swing.SwingUtilities.invokeLater(new Runnable()
					{

						public void run() 
						{
							lblTemp.setText(tempBean.getTemp());
							if(tempBean.getTempInteger() >= 80)
							{
								getRootPane().setBorder(BorderFactory.createMatteBorder(7, 7, 7, 7, Color.RED));
								getContentPane().setBackground(new Color(250,111, 86));
							}
							
							else if(tempBean.getTempInteger() >=65)
							{
								getRootPane().setBorder(BorderFactory.createMatteBorder(7, 7, 7, 7, new Color(255,99,8)));
								getContentPane().setBackground(new Color(250,193, 86));
							}
							
							else if(tempBean.getTempInteger() < 65)
							{
								getRootPane().setBorder(BorderFactory.createMatteBorder(7, 7, 7, 7, Color.BLUE));
								getContentPane().setBackground(new Color(86,250, 187));
							}
							
						}
				
					});
			
		}
		
		//Humidity data updated
		else if(TempBean.UPDATED_HUMIDITY.equals(e.getPropertyName()))
		{
			javax.swing.SwingUtilities.invokeLater(new Runnable()
			{

				public void run() 
				{
					lblHumidity.setText(tempBean.getHumidity());
				}
		
			});
		}
		
	}

	@Override
	public void keyPressed(KeyEvent e) 
	{
		if(e.getKeyCode() == KeyEvent.VK_F12 )
		{
			if(isAlwaysOnTop())
			{
				setAlwaysOnTop(false);
			}
			
			else
			{
				setAlwaysOnTop(true);
				System.out.println("Always on top set: true");
			}
		}
		
		else if(e.getKeyCode() == KeyEvent.VK_F11)
		{
			if(getExtendedState() == JFrame.MAXIMIZED_BOTH)
			{
				setExtendedState(JFrame.NORMAL);
			}
			
			else
			{
				setExtendedState(JFrame.MAXIMIZED_BOTH);
			}
		}
	}
	

	@Override
	public void keyTyped(KeyEvent e) 
	{	
	}

	@Override
	public void keyReleased(KeyEvent e) 
	{
	}

}
