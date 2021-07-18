#include <SimpleDHT.h>

/* for DHT11, 
 *      VCC: 5V or 3V
 *      GND: GND
 *      DATA: 2
*/      
typedef struct 
{
  int pin;
  int treshold;
  int value;
  String sensor_type;
  
}gas_sensor;

 gas_sensor create_gas_sensor(String sensor_type, int pin)
{
  gas_sensor sensor;
  sensor.pin = pin;
  sensor.sensor_type = sensor_type;
  return sensor;
}
int pinDHT11 = 2;
SimpleDHT11 dht11;
gas_sensor mq2 = create_gas_sensor("MQ-airQuality",  A1);


void setup()
{
  Serial.begin(9600);
  pinMode(mq2.pin, INPUT);
}

void loop() 
{
  temperature_read();
  gas_sensor_read(mq2);
}


void temperature_read()
{
  byte temperature = 0;
  byte humidity = 0;
  
  if (SimpleDHTErrSuccess != dht11.read(pinDHT11, &temperature, &humidity, NULL)) 
  {
      return;
  }

  Serial.println("Temp:" + String((int)temperature) + String("E"));
  Serial.println("Humid:" + String((int)humidity) + String("E"));

}


void gas_sensor_read(gas_sensor sensor)
{
  mq2.value = analogRead(mq2.pin);
  Serial.println(mq2.sensor_type + ':' + String(mq2.value) + String("E"));
}
