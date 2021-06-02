package org.todo_programming.Serial;

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
	SerialPort[] ports;			//All available ports
	SerialPort commPort;		//Port data is coming from
	String data;				//String buffer for serial data
	TempBean bean;				//model of the view
	
	/**
	 * Start looking for serial data
	 * 
	 * @param commPortDescription - String that identifies proper port for connection
	 * @param tempBean			  - Model of the view
	 */
	public SerialTemperatureComms(String commPortDescription,TempBean tempBean)
	{
		
		// Initialize attributes
		bean = tempBean;						
		data = "";									
		
		// Search until port is available with matching name
		findCorrectSerialPort(commPortDescription);

		// Attempt to open port till connection is established
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
			   for (int i = 0; i < newData.length; ++i)
			   {
				   //convert byte to char
				   char serialInput = (char)newData[i];
				   
				   //Character is a number or comma
				   if((int)serialInput >= 48 && (int)serialInput <58 || (int)serialInput == 44)
				   {
					   //Add string to buffer data
					   data += serialInput;
				   }
				
				   // character is E -> end
				   else if ((int)serialInput == 69)
				   {
					   String[] splitData = data.split(",");
					   
					   //Only update if there is Temperature and Humidity data present
					   if(splitData.length == 2)
					   {
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

	/**
	 * Will keep search serial ports till the correct one is found
	 * The correct comm port will have a matching system port name
	 * ex) COM8 
	 */
	public void findCorrectSerialPort(String portDescription)
	{
		boolean found = false;
		long failures = 0;

		while(!found)
		{
			ports = SerialPort.getCommPorts();
			for(int i = 0; i < ports.length; i++)
			{
				if(ports[i].getSystemPortName().equals(portDescription))
				{
					commPort = ports[i];
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
				System.out.println("Thread interupt Exception");
			}

			failures +=1;
			System.out.println("Open port failed "  + failures + " times on " + commPort.getSystemPortName());
		}

		commPort.setBaudRate(9600);
	}
}
