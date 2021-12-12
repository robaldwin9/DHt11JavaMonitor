# DHt11JavaMonitor 
Application reads serial data from Arduino using jSerialComm Java library. This read data is then displayed back to the user in a graphical user interface(GUI).

## Translations

* None Available

## Third party libraries

* [jSerialComm](https://fazecast.github.io/jSerialComm/) 2.5.1
* [JUnit 3.8.1](https://junit.org/junit5/)
* [Maven](https://maven.apache.org/)
* [Log4j2](https://logging.apache.org/log4j/2.x/)

## License 

* Do what you want commercial use, and private use are welcome

- [License file](LICENSE)

## :label: Versions 

1.  **Version 1.0** 

   :heavy_check_mark: Initial Release 

    :bug:Incorrectly selecting port

    :bug: ​Only attempting connection to port 1x

2.  **Version 1.1**

    :heavy_check_mark:Fixed Incorrect selection of serial port

    :heavy_check_mark:Retry logic on Serial Port

3. **Version 1.2**

    :heavy_check_mark:Add application configuration

    :heavy_check_mark:No longer requires compile to change port

4. **Version 1.3**

     :heavy_check_mark:Add additional style configuration

     :heavy_check_mark:Added Logj4 logging for better debugging

     :heavy_check_mark:Added threading

     :heavy_check_mark: Added configurable temp thresholds, and units​

5. **Version 1.4**

   :heavy_check_mark:Added  ability to reconnect when controller is removed, and then plugged in again. This will decrease the number of times you have to relaunch the java application

   :heavy_check_mark:Added Optional Air quality sensor MQ-135

   :heavy_check_mark: Added logging of values on 1 minute intervals so the data can be collected over time

   :heavy_check_mark: Improved parsing of serial data 

   :heavy_check_mark: Added progress bar that will show whenever the controller is disconnected​
   
6.  **Version 1.5**

     :heavy_check_mark: Added Web socket server

     :heavy_check_mark: Add example client webpage
    
7.  **Version 1.6**

     :heavy_check_mark: Added Configurable UI, Text data view, Data as bars, data as arcs(like web client)

     :heavy_check_mark: Much improved local UI graphics options

     :warning: **BE SURE TO UPDATE CONFIG TO INCLUDE NEW CONFIGURATION OPTIONS**

     :bug: Fixed some reconnect issues

     :bug: Made Control connected status more accurate

     

## Feature Documents

[Websocket server](webSocketServer.md)

[UI Configurations]: UIConfiguration.md	"local UI configurations"





##  :desktop_computer: Build/Load Arduino Sketch

1. Open [Arduino IDE](https://www.arduino.cc/en/software)
2. Go to tools tab, and set your board, processor, and  port
3. Go to file tab, and select open
4. Use the file explorer to select the [tempReaderWriter.ino](https://github.com/robaldwin9/DHt11JavaMonitor/blob/master/unoTemp/ArduinoSketch/tempReaderWriter/tempReaderWriter.ino) file
5. Hit the build/upload button in the Arduino IDE
6. Ensure your pins for you sensor match what is commented in the Arduino sketch
   1. GND
   2. PWR
   3. pin 2 is the data pin

7. Open Arduino serial monitor, and temperature, and humidity are being published
8. Ensure you close serial monitor, and any other applications that use serial communication with the Arduino before launching the GUI.

### :computer: Build Java Code

1. [Install maven](https://maven.apache.org/install.html) if you have not already

2. Ensure **JAVA_HOME** environment variable is set correctly.

   - **windows:** `echo %JAVA_HOME%`

   - **Linux:** `echo $JAVA_HOME`

3. Ensure that the maven bin directory is in your system path

4.  :open_file_folder: Navigate to the [unoTemp](https://github.com/robaldwin9/DHt11JavaMonitor/tree/master/unoTemp) directory of the project in you terminal

5. run `mvn clean install`

6. If you get a build error complaining about the java version being 10 you can always adjust the target version in the [pom.xml](https://github.com/robaldwin9/DHt11JavaMonitor/blob/master/unoTemp/pom.xml) file.

   ```xml
   <plugin>
   	<groupId>org.apache.maven.plugins</groupId>
   	<artifactId>maven-compiler-plugin</artifactId>
   	<version>3.0</version>
   	<configuration>
   		<source>10</source>
   		<target>10</target>
   	</configuration>
   </plugin>
   ```

### :computer: ​Run/Setup Java Application

 :open_file_folder:**Navigate to target directory:**
- target/unoTemp.jar is your application
- target/config.properties is your application config

:floppy_disk: ​ **Edit / save your configuration:**

- Ensure you have selected  your Arduinos port correctly, or the application will fail to display data.

- If you change the default colors make sure that they are provided as rgb values

  `[0-255][0-255][0-255]`

```properties
# Serial Port controller is on
serialPort=COM6
```

**Run Application:**

- Launch by double clicking in your explorer

- Alternatively you can launch from cmd/terminal

  `java -jar unoTemp.jar`

![http://todo-programming.com/img/unoTemp1.PNG](http://todo-programming.com/img/unoTemp1.PNG)