# Websocket Server Functionality

This features adds the ability to host a websocket server that will publish sensor/config info over the network. This allows users to create custom clients to interface with the application, and the data that it publishes.

## Enable/Disable

1. Ensure you have atleast version 1.5 software
2. Edit **config.properties** file
   1. To enable set `websocket = 1`
   2. To disable set `websocket = 0`
   3. Set `ipv4ServerAddress` value to the address  you desire default is localhost
   4. If you want the server functionality, but do not want the local GUI set `headless = 1`
3. Run application with desired settings

## JSON Requests

### Configuration data

Configuration data sent over JSON contains most of the fields in the config.properties file so that a client can know what sensors are enabled, and the preferred units for values.

1. send 'units' to get a one time reply integer value of 0 or 1
2. send "config" to get all the available config information

### Sensor Data

1. send "temp" to get a one time reply integer value for temp
2. send "humid" to get a one time reply humidity value 0-100
3. send "airQuality" to get a one time reply integer value 
4. send "subscribe" to get a response every 100ms with all sensor data



## Example Client

For more information on usage you can view the client page that exists within this project. There are examples in JavaScript on how to connect to the server, and parse the varies JSON messages. The client page is located [here](https://github.com/robaldwin9/DHt11JavaMonitor/tree/websocket_server/unoTemp/clientwebpage). Additionally you can view the example client actively running [here](http://www.todo-programming.org/clientwebpage/).

