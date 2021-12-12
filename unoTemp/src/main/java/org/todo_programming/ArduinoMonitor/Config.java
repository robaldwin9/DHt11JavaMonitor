package org.todo_programming.ArduinoMonitor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.todo_programming.Serial.SerialTemperatureComms;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Properties;

public class Config
{
    /** Class instance */
    private transient static Config instance = null;

    /** Serial port of the arduino */
    private String serialPort = "COM6";

    /** Border color for HIGH range temp */
    private transient Color statusColor1 = new Color(0,255,0);

    /** Border color for MID range temp */
    private transient Color statusColor2 = new Color(255,69,0);

    /** Border color for LOW range temp */
    private transient Color statusColor3 = new Color(255,0,0);

    /** Units imperial, or metric */
    private int units = 0;

    /** Threshold for 1st color */
    private transient int threshold1 = 65;

    /** Threshold for 2nd color */
    private transient int threshold2 = 80;

    /** 0 not enabled and 1 is enabled */
    private int airQualityEnable = 0;

    /** Web socket server enabled */
    private transient boolean isServer = true;

    /** No GUI mode */
    private transient boolean isHeadless = false;

    /** Data logging enabled */
    private transient boolean dataLogging = true;

    /** IPV 4 address for websocket */
    private String ipv4ServerAddress = "localhost";

    /** web socket server port */
    private transient int serverPort = 9123;

    /* log4j instance */
    static transient final Logger log = LogManager.getLogger(SerialTemperatureComms.class.getName());


    private int localUIType = 0;

    /**
     * Load application config
     */
    private Config()
    {
        readProperties();
    }

    /**
     * get application configuration instance
     * @return application config
     */
    public static Config getInstance()
    {
        if(instance == null)
        {
            instance = new Config();
        }
        return instance;
    }

    /**
     * Reads application config from properties file
     */
    private void readProperties()
    {
        Properties properties = new Properties();

        try
        {
            String dir = new File(App.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParentFile().getPath();
            properties.load(new FileInputStream(dir +"/config.properties"));
            serialPort = properties.getProperty("serialPort");
            statusColor1 = createColor(properties.getProperty("statusColor1").split(","), statusColor1);
            statusColor2 = createColor(properties.getProperty("statusColor2").split(","), statusColor2);
            statusColor3 = createColor(properties.getProperty("statusColor3").split(","), statusColor3);
            units = Integer.parseInt(properties.getProperty("units"));
            threshold1 = Integer.parseInt(properties.getProperty("threshold1"));
            threshold2 = Integer.parseInt(properties.getProperty("threshold2"));
            airQualityEnable = Integer.parseInt(properties.getProperty("MQ135Enabled"));
            isServer = Integer.parseInt(properties.getProperty("websocket")) == 1;
            isHeadless = Integer.parseInt(properties.getProperty("headless")) == 1;
            dataLogging = Integer.parseInt(properties.getProperty("datalogging")) == 1;
            serverPort = Integer.parseInt(properties.getProperty("websocketport"));
            localUIType = Integer.parseInt(properties.getProperty("localUIType"));
            ipv4ServerAddress = properties.getProperty("ipv4ServerAddress");
        }

        catch(IOException | URISyntaxException e)
        {
            log.error("Error occurred while trying to load config.properties \n{}", e.getMessage());
            log.warn("Default values will be used instead of user defined values: Default serial port is\n{}", serialPort);
        }
    }

    /**
     *
     * @param rgbColor rgb color value as string
     * @param originalColor orig. color
     * @return Color
     */
    private Color createColor(String[] rgbColor, Color originalColor)
    {
        Color color = originalColor;
        boolean valid = true;
        int [] rgbValues = new int[3];

        if(rgbColor.length == 3)
        {
            for (int i =0; i < rgbColor.length; i++)
            {
                try
                {
                    rgbValues[i] = Integer.parseInt(rgbColor[i]);
                }

                catch (NumberFormatException e)
                {
                    valid = false;

                    log.error("Exception occurred while parsing a color from config.properties: \n{}", e.getMessage());
                    e.printStackTrace();
                }
            }
        }

        if(valid)
        {
            color = new Color(rgbValues[0], rgbValues[1], rgbValues[2]);
        }

        return color;
    }

    /**
     *
     * @return serial port of arduino
     */
    public String getSerialPort()
    {
        return serialPort;
    }

    /**
     *
     * @return border color for HIGH range temp
     */
    public Color getStatusColor1()
    {
        return statusColor1;
    }

    /**
     *
     * @return border color for MID range temp
     */
    public Color getStatusColor2()
    {
        return statusColor2;
    }

    /**
     *
     * @return border color for LOW range temp
     */
    public Color getStatusColor3() {
        return statusColor3;
    }

    /**
     *
     * @return units to be used
     */
    public int getUnits()
    {
        return units;
    }

    /**
     *
     * @return threshold for first color
     */
    public int getThreshold1()
    {
        return threshold1;
    }

    /**
     *
     * @return threshold for second color
     */
    public int getThreshold2()
    {
        return threshold2;
    }

    /**
     *
     * @return true if air quality sensor is wired into controller
     */
    public boolean isAirQualitySensorEnabled()
    {
        return airQualityEnable == 1;
    }

    /**
     *
     * @return is websocket server enabled
     */
    public boolean isServer()
    {
        return isServer;
    }

    /**
     *
     * @return is GUI shown in application
     */
    public boolean isHeadless()
    {
        return isHeadless;
    }

    /**
     *
     * @return is data logging turned  on
     */
    public boolean isDataLogging()
    {
        return dataLogging;
    }

    /**
     *
     * @return - ipv4 address
     */
    public String getIpv4ServerAddress()
    {
        return ipv4ServerAddress;
    }

    /**
     *
     * @return - web socket server port
     */
    public int getServerPort() {
        return serverPort;
    }

    /**
     *
     * @return what UI style to use
     */
    public int getLocalUIType() {
        return localUIType;
    }
}
