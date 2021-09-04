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
    	/* Sensor data object initialization */
    	SensorBean sensorBean = SensorBean.getInstance();

    	/* Run application GUI if not in headless mode */
    	if(!Config.getInstance().isHeadless())
    	{
			new MainFrame(sensorBean);
		}

    	/* Serial Data monitor that updates model */
     	new SerialTemperatureComms(sensorBean);

		/* Run data logging task if enabled */
		if(Config.getInstance().isDataLogging())
    	{
			Timer dataLogTimer = new Timer("sensorData");
			TimerTask task = new DataLogger(sensorBean);
			dataLogTimer.scheduleAtFixedRate(task, 12000, 60000);
		}

		/* Start websocket sever if enabled in config */
        if(Config.getInstance().isServer())
        {
			websocketStart();
		}
    }

	/**
	 * Starts websocket thread
	 */
	private static void websocketStart()
	{
		Server server = new Server(Config.getInstance().getIpv4ServerAddress(), Config.getInstance().getServerPort(), "/climate", WebsocketServer.class);

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

