package org.todo_programming.ui.panels;

import org.todo_programming.ArduinoMonitor.SensorBean;
import org.todo_programming.ui.barvalue.BarValue;
import org.todo_programming.ui.indicator.Indicator;
import org.todo_programming.ui.indicator.IndicatorStatus;

import java.awt.*;

public class BarValuesPanel extends SensorView
{
    /** Temperature UI element displayed as a bar */
    private final BarValue tempBar;

    /** Humidity UI element displayed as a bar */
    private final BarValue humidBar;

    /** Air quality UI element displayed as  a bar */
    private final BarValue airQualityBar;

    /** Control connected UI indicator */
    private final Indicator controllerIndicator = new Indicator("Controller Connected");

    /**
     * Constructor
     */
    public BarValuesPanel()
    {
        /* Bar value initialization*/
        if(config.getUnits() ==1)
        {
            tempBar = new BarValue("Temperature", "F", 0,122);
        }

        else
        {
            tempBar = new BarValue("Temperature", "C", 0, 50);
        }

        humidBar = new BarValue("Humidity", "%", 0, 100);
        airQualityBar = new BarValue("Air Quality", 0, 500);

        /* layout Init */
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints constraints = new GridBagConstraints();
        this.setLayout(layout);

        /* Common constraints */
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;

        /* Add Elements */
        constraints.gridx = 0;
        constraints.gridy = 0;
        add(tempBar,constraints);

        constraints.gridy +=1;
        humidBar.setBarColor(Color.BLUE);
        add(humidBar, constraints);

        if(config.isAirQualitySensorEnabled())
        {
            constraints.gridy += 1;
            add(airQualityBar, constraints);
        }

        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridy +=1;
        constraints.weightx = 0.30;
        constraints.weighty = 0.30;
        constraints.insets = new Insets(1,10,1,1);
        add(controllerIndicator,constraints);
        setBackground(Color.BLACK);
    }

    @Override
    public void update(String propertyName, SensorBean sensorData)
    {
        // Temperature data updated
        if(SensorBean.UPDATED_TEMPERATURE.equals(propertyName))
        {
            javax.swing.SwingUtilities.invokeLater(() ->
            {
                tempBar.setCurrentValue(sensorData.getTempInteger());
                if(sensorData.getTempInteger() < config.getThreshold1())
                {
                    tempBar.setBarColor(config.getStatusColor1());
                }

                else if(sensorData.getTempInteger() >= config.getThreshold1() && sensorData.getTempInteger() < config.getThreshold2())
                {
                    tempBar.setBarColor(config.getStatusColor2());
                }

                else if(sensorData.getTempInteger() > config.getThreshold2())
                {
                    tempBar.setBarColor(config.getStatusColor3());
                }
            });
        }

        /* Humidity data updated */
        else if(SensorBean.UPDATED_HUMIDITY.equals(propertyName))
        {
            javax.swing.SwingUtilities.invokeLater(() -> humidBar.setCurrentValue(sensorData.getHumidityInteger()));
        }

        else if(SensorBean.UPDATED_AIR_QUALITY.equals(propertyName))
        {
            int quality = sensorData.getAirQualityInt();
            airQualityBar.setCurrentValue(quality);
            if(quality < 200)
            {
                airQualityBar.setBarColor(config.getStatusColor1());
            }

            else if(quality > 200 && quality < 300)
            {
                airQualityBar.setBarColor(config.getStatusColor2());
            }

            else if(quality > 300)
            {
               airQualityBar.setBarColor(config.getStatusColor3());
            }
        }

        else if(SensorBean.UPDATE_CONTROLLER_CONNECTION.equals(propertyName))
        {
            if(sensorData.isControllerConnected())
            {
                controllerIndicator.setStatus(IndicatorStatus.HEALTHY);
            }

            else
            {
                controllerIndicator.setStatus(IndicatorStatus.ERROR);
            }
        }
    }
}
