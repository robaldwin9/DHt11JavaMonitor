package org.todo_programming.Serial;

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
	private String data;

	/** model of the view */
	private final TempBean bean;

	/**
	 * Start looking for serial data
	 *
	 * @param tempBean			  - Model of the view
	 */
	public SerialTemperatureComms(TempBean tempBean)
	{

		//Initialize attributes
		bean = tempBean;
		data = "";


		//Connect to port using communication port identifier
		Config config = Config.getInstance();
		findCorrectSerialPort(config.getSerialPort());
		openSerialPort();


		//Add listener to port
		commPort.addDataListener(new SerialPortDataListener()
		{
			public int getListeningEvents() { return SerialPort.LISTENING_EVENT_DATA_AVAILABLE; }

			public void serialEvent(SerialPortEvent event)
			{
			   //Data not available return
			   if (event.getEventType() != SerialPort.LISTENING_EVENT_DATA_AVAILABLE)
			   {
			      return;
			   }

			   //Read serial bytes from communication port
			   byte[] newData = new byte[8];
			   commPort.readBytes(newData, newData.length);

			   //iterate over data for data collection/conversion
				for (byte newDatum : newData)
				{
					//convert byte to char
					char serialInput = (char) newDatum;

					//Character is a number or comma
					if ((int) serialInput >= 48 && (int) serialInput < 58 || (int) serialInput == 44) {
						//Add string to buffer data
						data += serialInput;
					}

					// character is E -> end
					else if ((int) serialInput == 69) {
						String[] splitData = data.split(",");

						//Only update if there is Temperature and Humidity data present
						if (splitData.length == 2) {
							bean.setTemp(splitData[0]);
							bean.setHumidity(splitData[1]);
						}

						//clear buffer
						data = "";
						break;

					}
				}

			   }

			});
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

				try
				{
					Thread.sleep(250);
				}

				catch(Exception e)
				{
					System.out.println("Thread interupt Exception");
				}

				System.out.println("search attempt for  \"" + portDescription + "\"" + " has happened " + failures + " times");
			}
		}

	}

	/**
	 * Will continue to try and open serial port untill it is open
	 * If any other device is talking to the device liste then this function will not exit
	 */
	public void openSerialPort()
	{
		long failures = 0;
		while(!commPort.openPort())
		{
			try
			{
				Thread.sleep(250);
			}

			catch(Exception e)
			{
				System.out.println("Thread interrupt Exception");
			}

			failures +=1;
			System.out.println("Open port failed "  + failures + " times on " + commPort.getSystemPortName());
		}

		commPort.setBaudRate(9600);
	}
}
