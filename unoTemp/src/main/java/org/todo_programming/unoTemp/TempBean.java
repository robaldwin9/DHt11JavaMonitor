package org.todo_programming.unoTemp;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;

public class TempBean implements Serializable
{
	/** UID of class */
	private static final long serialVersionUID = -5067790505985875022L;
	private String temperature;
	private String humidity;
	private PropertyChangeSupport observer;
	
	public static final String UPDATED_TEMPERATURE = "tempUpdate";
	public static final String UPDATED_HUMIDITY = "humidUpdate";
	
	/**
	 * 
	 */
	TempBean()
	{
		temperature = "0";
		humidity = "0";
		observer = new PropertyChangeSupport(this);
	}
	
	/**
	 * 
	 */
	TempBean(String temperature, String humidity)
	{
		
	}
	/**
	 * 
	 * @return
	 */
	public String getTemp() 
	{
		return temperature + "F";
	}
	
	public int getTempInteger()
	{
		return Integer.parseInt(temperature);
	}
	
	/**
	 * 
	 * @param temp
	 */
	public void setTemp(String temp) 
	{
		String oldValue = temperature;
		temperature = temp;
		this.observer.firePropertyChange(UPDATED_TEMPERATURE, oldValue, temp);
	}
	
	/**
	 * 
	 * @return
	 */
	public String getHumidity() 
	{
		return humidity + "%";
	}
	
	/**
	 * 
	 * @return
	 */
	public int getHumidityInteger() 
	{
		return Integer.parseInt(temperature);
	}
	
	/**
	 * 
	 * @param humidity
	 */
	public void setHumidity(String humidity) 
	{
		String oldValue = this.humidity;
		this.humidity = humidity;
		this.observer.firePropertyChange(UPDATED_HUMIDITY, oldValue, humidity);
	}
	
	public void addPropertyChangeListener(PropertyChangeListener listener)
	{
		observer.addPropertyChangeListener(listener);
	}

}
