package org.todo_programming.ArduinoMonitor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.TimerTask;

public class DataLogger extends TimerTask {

    /** Data from sensors */
    SensorBean data;

    /** Application configuration */
    Config config;

    /* log4j instance */
    static final Logger log = LogManager.getLogger(DataLogger.class.getName());

    /**
     *
     * @param data current sensor values
     */
    public DataLogger(SensorBean data)
    {
        this.data = data;
        config = Config.getInstance();
    }

    @Override
    public void run()
    {
        if(data.isControllerConnected())
        {
            log.info("Temp: {}", data.getTemp());
            log.info("Humidity: {}", data.getHumidity());

            if (config.isAirQualitySensorEnabled())
            {
                log.info("Air: {}", data.getAirQualityInt());
            }
        }
    }
}
