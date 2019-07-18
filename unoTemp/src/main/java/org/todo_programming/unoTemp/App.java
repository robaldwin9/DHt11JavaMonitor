package org.todo_programming.unoTemp;

import org.todo_programming.Serial.SerialTemperatureComms;

/**
 * Java swing, arduino 
 *  temperature gauge
 */
public class App 
{
    public static void main( String[] args )
    {
    	TempBean tempDataBean = new TempBean();
    	new SerialTemperatureComms("(COM3)",tempDataBean);
    	new MainFrame("(COM3)",tempDataBean);    
    }
}
