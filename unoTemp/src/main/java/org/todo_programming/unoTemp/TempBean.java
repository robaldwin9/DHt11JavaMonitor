package org.todo_programming.unoTemp;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;

public class TempBean implements Serializable
{
	/** UID of class */
	private static final long serialVersionUID = -5067790505985875022L;

	/** Temperature Value */
	private String temperature;

	/** Humidity Value*/
	private String humidity;

	/** Observer pattern object */
	private final PropertyChangeSupport observer;

	/** Temperature changed event */
	public static final String UPDATED_TEMPERATURE = "tempUpdate";

	/** Humidity changed event */
	public static final String UPDATED_HUMIDITY = "humidUpdate";
	
	/**
	 *  Model for temperature values
	 */
	TempBean()
	{
		temperature = "0";
		humidity = "0";
		observer = new PropertyChangeSupport(this);
	}

	/**
	 * 
	 * @return temperature string
	 */
	public String getTemp() 
	{

		if(Config.getInstance().getUnits() == 1)
		{
			return temperature + "F";
		}

		else
		{
			return temperature + "C";
		}

	}
	
	public int getTempInteger()
	{

		if(Config.getInstance().getUnits() == 1)
		{
			return ((Integer.parseInt((temperature ) )* 9/5) + 32);
		}

		else
			{
			return Integer.parseInt(temperature);
		}

	}
	
	/**
	 * 
	 * @param temp temperature String
	 */
	public void setTemp(String temp) 
	{
		if(Config.getInstance().getUnits() == 1)
		{
			temp = String.valueOf((Integer.parseInt(temp) * 9/5) +32);
		}
		String oldValue = temperature;
		temperature = temp;
		this.observer.firePropertyChange(UPDATED_TEMPERATURE, oldValue, temp);
	}
	
	/**
	 * 
	 * @return Humidity String
	 */
	public String getHumidity() 
	{
		return humidity + "%";
	}
	
	/**
	 * 
	 * @param humidity - String value
	 */
	public void setHumidity(String humidity) 
	{
		String oldValue = this.humidity;
		this.humidity = humidity;
		this.observer.firePropertyChange(UPDATED_HUMIDITY, oldValue, humidity);
	}

	/**
	 *
	 * @param listener property change listener
	 */
	public void addPropertyChangeListener(PropertyChangeListener listener)
	{
		observer.addPropertyChangeListener(listener);
	}

}
