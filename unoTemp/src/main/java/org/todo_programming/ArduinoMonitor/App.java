package org.todo_programming.ArduinoMonitor;

import org.glassfish.tyrus.server.Server;
import org.todo_programming.Serial.SerialTemperatureComms;

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
    	SensorBean sensorBean = SensorBean.getInstance();
		new MainFrame(sensorBean);
     	new SerialTemperatureComms(sensorBean);
    	Timer dataLogTimer = new Timer("sensorData");
    	TimerTask task = new  DataLogger(sensorBean);
        dataLogTimer.scheduleAtFixedRate(task, 12000, 60000);
        websocketStart();
    }

    private static void websocketStart()
	{
		Server server = new Server("localhost", 9123, "/climate", WebsocketServer.class);

		try
		{
			server.start();
		}

		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}
}

