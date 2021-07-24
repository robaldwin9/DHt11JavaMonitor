package org.todo_programming.ArduinoMonitor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Objects;
import java.util.Optional;

import javax.swing.*;

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

	/** Data associated with the view */
	private final SensorBean sensorBean;

	/** Label for temperature */
	private final ScalableLabel lblTempValue;

	/** Label for humidity */
	private final ScalableLabel lblHumidityValue;

	/** Air Quality */
	private final ScalableLabel lblAirQualityValue;

	/** Application configuration */
	private final Config config = Config.getInstance();

	/**
	 *
	 * @param data - contains updated Temperature data after it is collected from the Serial Port
	 */
	public MainFrame(SensorBean data)
	{
		sensorBean = data;
		data.addPropertyChangeListener(this);

		/* Label initialization*/
		if(config.getUnits() ==1)
		{
			lblTempValue = new ScalableLabel("0F", 0.20f);
		}

		else
		{
			lblTempValue = new ScalableLabel("0C", 0.20f);
		}


		/* Temp sensor labels */
		lblTempValue.setForeground(config.getLabelColor());
		lblHumidityValue = new ScalableLabel("0%",0.20f);
		lblHumidityValue.setForeground(config.getLabelColor());

		/* Air quality labels */
		ScalableLabel lblAirQualityDescription = new ScalableLabel("Air: ", 0.20f);
		lblAirQualityValue = new ScalableLabel("0", 0.20f);
		lblAirQualityValue.setForeground(config.getLabelColor());
		lblAirQualityDescription.setForeground(config.getLabelColor());
		lblAirQualityValue.setHorizontalAlignment(JLabel.CENTER);

		/* layout Init */
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints constraints = new GridBagConstraints();
		setLayout(layout);
		
		/* Window Dimensions*/
		Dimension dim = new Dimension(300,300);
		setPreferredSize(dim);
		setSize(300, 300);
		
		/* Common constraints */
		constraints.fill = GridBagConstraints.RELATIVE;
		constraints.weightx = 1.0;
		constraints.weighty = 1.0;

		/* Add Elements */
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.insets = new Insets(1,2,1,1);
		add(lblTempValue,constraints);
		
		constraints.gridx = 1;
		constraints.gridy = 0;
		constraints.insets = new Insets(1,1,1,1);
		add(lblHumidityValue,constraints);


		if(config.isAirQualitySensorEnabled())
		{
			constraints.gridx = 0;
			constraints.gridy = 1;
			add(lblAirQualityDescription, constraints);

			constraints.gridx = 1;
			constraints.gridy = 1;
			add(lblAirQualityValue, constraints);
		}

		getRootPane().setBorder(BorderFactory.createMatteBorder(7, 7, 7, 7, Color.BLUE));
		getContentPane().setBackground(new Color(86,250, 187));
		setIconImage(new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("img/therm.png"))).getImage());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		addKeyListener(this);
		setTitle("Monitor " + config.getSerialPort());
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setVisible(true);
	}
	
	/**
	 * Fired when temperature or humidity updated
	 */
	public void propertyChange(PropertyChangeEvent e) 
	{
		//Temperature data updated
		if(SensorBean.UPDATED_TEMPERATURE.equals(e.getPropertyName()))
		{
			javax.swing.SwingUtilities.invokeLater(() -> {
				lblTempValue.setText(sensorBean.getTemp());
				if(sensorBean.getTempInteger() >= config.getThreshold1())
				{
					getRootPane().setBorder(BorderFactory.createMatteBorder(7, 7, 7, 7, config.getBorderColor1()));
					getContentPane().setBackground(config.getBackgroundColor1());
				}

				else if(sensorBean.getTempInteger() >= config.getThreshold2())
				{
					getRootPane().setBorder(BorderFactory.createMatteBorder(7, 7, 7, 7, config.getBorderColor2()));
					getContentPane().setBackground(config.getBackgroundColor2());
				}

				else if(sensorBean.getTempInteger() < config.getThreshold3())
				{
					getRootPane().setBorder(BorderFactory.createMatteBorder(7, 7, 7, 7, config.getBorderColor3()));
					getContentPane().setBackground(config.getBackgroundColor3());
				}
			});
		}
		
		//Humidity data updated
		else if(SensorBean.UPDATED_HUMIDITY.equals(e.getPropertyName()))
		{
			javax.swing.SwingUtilities.invokeLater(() -> lblHumidityValue.setText(sensorBean.getHumidity()));
		}

		else if(SensorBean.UPDATED_AIR_QUALITY.equals(e.getPropertyName()))
		{
			int quality = sensorBean.getAirQualityInt();
			if(quality < 175)
			{
				lblAirQualityValue.setText("Good");
			}

			else if(quality > 200 && quality < 300)
			{
				lblAirQualityValue.setText("Moderate");
			}

			else if(quality > 300)
			{
				lblAirQualityValue.setText("Bad");
			}

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
