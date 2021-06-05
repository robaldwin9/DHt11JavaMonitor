package org.todo_programming.Serial;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.todo_programming.unoTemp.Config;
import org.todo_programming.unoTemp.TempBean;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;

/**
 * Monitors temperature data on Communication Port
 * @author robal
 *
 */
public class SerialTemperatureComms
{
	/** Port data is coming from */
	private SerialPort commPort;

	/** String buffer for serial data */
	private final StringBuilder data;

	/** model of the view */
	private final TempBean bean;

	/* log4j instance */
	static final Logger log = LogManager.getLogger(SerialTemperatureComms.class.getName());

	/**
	 * Start looking for serial data
	 *
	 * @param tempBean - Model of the view
	 */
	public SerialTemperatureComms(TempBean tempBean)
	{
		/* Initialize attributes */
		bean = tempBean;
		data = new StringBuilder();

		/* Connect to port using communication port identifier */
		Config config = Config.getInstance();

		new Thread(() ->
		{
			findCorrectSerialPort(config.getSerialPort());
			openSerialPort();

			/* Add listener to port */
			commPort.addDataListener(new SerialPortDataListener()
			{
				public int getListeningEvents() { return SerialPort.LISTENING_EVENT_DATA_AVAILABLE; }

				public void serialEvent(SerialPortEvent event)
				{
			  	 	/* Data not available return */
			   		if (event.getEventType() != SerialPort.LISTENING_EVENT_DATA_AVAILABLE)
			   		{
			      		return;
			  	 	}

			   		/* Read serial bytes from communication port */
			   		byte[] newData = new byte[8];
			   		commPort.readBytes(newData, newData.length);

			   		/* iterate over data for data collection/conversion */
					for (byte newDatum : newData)
					{
						/* convert byte to char */
						char serialInput = (char) newDatum;

						/* Character is a number or comma */
						if ((int) serialInput >= 48 && (int) serialInput < 58 || (int) serialInput == 44)
						{
							data.append(serialInput);
						}

						/* character is E -> end */
						else if ((int) serialInput == 69)
						{
							String[] splitData = data.toString().split(",");

							/* Only update if there is Temperature and Humidity data present */
							if (splitData.length >= 2)
							{
								bean.setTemp(splitData[0]);
								bean.setHumidity(splitData[1]);
							}

							/* clear buffer */
							data.setLength(0);
							break;
					}
				}

			}

		});

		}).start();
	}


	public void findCorrectSerialPort(String portDescription)
	{
		boolean found = false;
		long failures = 0;

		while(!found)
		{
			SerialPort[] ports = SerialPort.getCommPorts();
			for (SerialPort port : ports)
			{
				if (port.getSystemPortName().equals(portDescription))
				{
					commPort = port;
					found = true;
				}
			}

			if(!found)
			{
				failures += 1;

				if(failures % 100 == 0)
				{
					log.warn("search attempt for  \"" + portDescription + "\"" + " has failed " + failures + " times");
				}
			}
		}

	}

	/**
	 * Will continue to try and open serial port until it is open
	 * If any other device is talking to the device listed then this function will not exit
	 */
	public void openSerialPort()
	{
		long failures = 0;
		while(!commPort.openPort())
		{
			failures +=1;

			if(failures % 100 == 0)
			{
				log.warn("Open port failed " + failures + " times on " + commPort.getSystemPortName());
			}
		}

		commPort.setBaudRate(9600);
	}


	public void autoConnect()
	{

	}

}
