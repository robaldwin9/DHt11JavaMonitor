package org.todo_programming.unoTemp;

import org.todo_programming.Serial.SerialTemperatureComms;

/**
 * Java swing, arduino 
 *  temperature gauge
 */
public class App 
{
	/**
	 * 
	 * @param args command line arguments
	 */
    public static void main( String[] args )
    {
    	TempBean tempDataBean = new TempBean();
    	new SerialTemperatureComms("COM8",tempDataBean);
    	new MainFrame("COM8",tempDataBean);    
    }
}
