package org.todo_programming.unoTemp;

import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Properties;

public class Config {

    /** Class instance */
    private static Config instance = null;

    /** Serial port of the arduino */
    private String serialPort = "COM6";

    /** Background color used for lowest range of temperature */
    private Color backgroundColor1 = new Color(250,111,86);

    /** Background color used for center range of temperature */
    private Color backgroundColor2 = new Color(250,193,86);

     /** Background color used for High range of temperature */
    private Color  backgroundColor3 = new Color(86,250,187);

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

            String dir = System.getProperty("user.dir");
            properties.load(new FileInputStream(dir +"/config.properties"));
            serialPort = properties.getProperty("serialPort");
            backgroundColor1 = createColor(properties.getProperty("backgroundColor1").split(","),backgroundColor1);
            backgroundColor2 = createColor(properties.getProperty("backgroundColor2").split(","),backgroundColor2);
            backgroundColor3 = createColor(properties.getProperty("backgroundColor3").split(","),backgroundColor3);
        }

        catch(IOException e)
        {
            e.printStackTrace();
        }
    }


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
     * @return color used for bottom range of temperatures
     */
    public Color getBackgroundColor1()
    {
        return backgroundColor1;
    }

    /**
     *
     * @return color used for mid range of temperatures
     */
    public Color getBackgroundColor2()
    {
        return backgroundColor2;
    }

    /**
     *
     * @return color used for high range of temperatures
     */
    public Color getBackgroundColor3()
    {
        return backgroundColor3;
    }

}
