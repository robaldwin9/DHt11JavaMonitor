package org.todo_programming.unoTemp;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Properties;

public class Config {

    /** Class instance */
    private static Config instance = null;

    /** Serial port of the arduino */
    private String serialPort = "COM6";

    /** Background color used for HIGH range of temperature */
    private Color backgroundColor1 = new Color(250,111,86);

    /** Background color used for CENTER range of temperature */
    private Color backgroundColor2 = new Color(250,193,86);

     /** Background color used for LOW range of temperature */
    private Color  backgroundColor3 = new Color(86,250,187);

    /** Border color for HIGH range temp */
    private Color borderColor1 = new Color(255,0,0);

    /** Border color for MID range temp */
    private Color borderColor2 = new Color(255,99,8);

    /** Border color for LOW range temp */
    private Color borderColor3 = new Color(0,0,255);

    /** Color of Labels */
    private Color labelColor = Color.BLACK;

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
            backgroundColor1 = createColor(properties.getProperty("backgroundColor1").split(","),backgroundColor1);
            borderColor1 = createColor(properties.getProperty("borderColor1").split(","), borderColor1);
            backgroundColor2 = createColor(properties.getProperty("backgroundColor2").split(","),backgroundColor2);
            borderColor2 = createColor(properties.getProperty("borderColor2").split(","), borderColor2);
            backgroundColor3 = createColor(properties.getProperty("backgroundColor3").split(","),backgroundColor3);
            borderColor3 = createColor(properties.getProperty("borderColor3").split(","), borderColor3);
            labelColor = createColor(properties.getProperty("labelColor").split(","), labelColor);
        }

        catch(IOException | URISyntaxException e)
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
     * @return color used for HIGH range of temperatures
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
     * @return color used for LOW range of temperatures
     */
    public Color getBackgroundColor3()
    {
        return backgroundColor3;
    }

    /**
     *
     * @return color of labels defined by user
     */
    public Color getLabelColor() {
        return labelColor;
    }

    /**
     *
     * @return border color for HIGH range temp
     */
    public Color getBorderColor1() {
        return borderColor1;
    }

    /**
     *
     * @return border color for MID range temp
     */
    public Color getBorderColor2() {
        return borderColor2;
    }

    /**
     *
     * @return border color for LOW range temp
     */
    public Color getBorderColor3() {
        return borderColor3;
    }

}
