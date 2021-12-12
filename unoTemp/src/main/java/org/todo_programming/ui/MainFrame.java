package org.todo_programming.ui;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Objects;

import javax.swing.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.todo_programming.ArduinoMonitor.Config;
import org.todo_programming.ArduinoMonitor.SensorBean;
import org.todo_programming.ui.panels.ArcValuesPanel;
import org.todo_programming.ui.panels.BarValuesPanel;
import org.todo_programming.ui.panels.SensorView;
import org.todo_programming.ui.panels.TextValuesPanel;

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

	/** Sub-panel that displays sensor data */
	private final SensorView panelView;

	/* log4j instance */
	static final Logger log = LogManager.getLogger(MainFrame.class.getName());

	/**
	 *
	 * @param data - contains updated Temperature data after it is collected from the Serial Port
	 */
	public MainFrame(SensorBean data)
	{
		sensorBean = data;
		data.addPropertyChangeListener(this);
		/* Set panel based on configuration */
		Config config = Config.getInstance();
		switch(config.getLocalUIType())
		{
			case 0:
				panelView = new TextValuesPanel(data);
				break;
			case 1:
				panelView = new BarValuesPanel();
				break;
			case 2:
				panelView = new ArcValuesPanel();
				break;
			default:
				panelView = new TextValuesPanel(data);
				break;
		}


		/* Window setup */
		setTitle("Monitor " + config.getSerialPort());
		setIconImage(new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("img/therm.png"))).getImage());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		addKeyListener(this);
		Dimension dim = new Dimension(500,500);
		setPreferredSize(dim);
		setSize(300, 300);
		
		/* Add Panel */
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints constraints = new GridBagConstraints();
		setLayout(layout);
		constraints.fill = GridBagConstraints.BOTH;
		constraints.weightx = 1.0;
		constraints.weighty = 1.0;
		constraints.gridx = 0;
		constraints.gridy = 0;
		add(panelView, constraints);
		setVisible(true);




	}
	
	/**
	 * Fired when temperature or humidity updated
	 */
	public void propertyChange(PropertyChangeEvent e) 
	{
		panelView.update(e.getPropertyName(), sensorBean);
	}

	@Override
	public void keyPressed(KeyEvent e) 
	{
		if(e.getKeyCode() == KeyEvent.VK_F12 )
		{
			setAlwaysOnTop(!isAlwaysOnTop());
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
