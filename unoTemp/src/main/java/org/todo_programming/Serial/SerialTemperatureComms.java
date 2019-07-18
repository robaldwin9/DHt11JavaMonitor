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
		
		//Initialize attributes
		bean = tempBean;						
		data = "";								
		ports = SerialPort.getCommPorts();		
		
		//Connect to port using communication port identifier
		commPort = SerialPort.getCommPort(commPortDescription);
		commPort = ports[1];
		System.out.println(commPort.openPort());
		commPort.setBaudRate(4500);
		
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
}
