package org.todo_programming.ArduinoMonitor;

import org.todo_programming.Serial.SerialTemperatureComms;

import javax.xml.crypto.Data;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Java swing, arduino 
 *  temperature gauge
 */
public class App 
{
    public static void main( String[] args )
    {
    	SensorBean sensorBean = new SensorBean();
		new MainFrame(sensorBean);
     	new SerialTemperatureComms(sensorBean);
    	Timer dataLogTimer = new Timer("sensorData");
    	TimerTask task = new  DataLogger(sensorBean);
        dataLogTimer.scheduleAtFixedRate(task, 12000, 60000);
    }
}
