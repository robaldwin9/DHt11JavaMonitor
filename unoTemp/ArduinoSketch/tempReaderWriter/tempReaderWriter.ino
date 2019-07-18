#include <SimpleDHT.h>

// for DHT11, 
//      VCC: 5V or 3V
//      GND: GND
//      DATA: 2

int pinDHT11 = 6 ;
SimpleDHT11 dht11;

void setup()
{
  Serial.begin(4500);
}

void loop() 
{

  byte temperature = 0;
  byte humidity = 0;
  
  if (dht11.read(pinDHT11, &temperature, &humidity, NULL)) 
  {
    return;
  }
  
  Serial.print((int)temperature * 9/5 + 32); 
  Serial.print(',');
  Serial.print((int)humidity);
  Serial.print('E');
  
  delay(1000);
}
