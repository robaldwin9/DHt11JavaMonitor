package org.todo_programming.ArduinoMonitor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.crypto.Data;
import java.util.TimerTask;

public class DataLogger extends TimerTask {
    SensorBean data;

    Config config;

    /* log4j instance */
    static final Logger log = LogManager.getLogger(DataLogger.class.getName());

    public DataLogger(SensorBean data)
    {
        this.data = data;
        config = Config.getInstance();
    }

    @Override
    public void run()
    {
        if(config.getUnits() == 1)
        {
            log.info("Temp: {} {}", data.getTemp(), "F");
        }

        else
        {
            log.info("Temp: {} {}", data.getTemp(), "C");
        }

        log.info("Humidity: {} {}", data.getHumidity(), "%");
        log.info("Air: {}", data.getAirQualityInt());
    }
}
