package org.todo_programming.ui.panels;

import org.todo_programming.ArduinoMonitor.SensorBean;
import org.todo_programming.ui.ArcValue.ArcValue;
import org.todo_programming.ui.indicator.Indicator;
import org.todo_programming.ui.indicator.IndicatorStatus;

import java.awt.*;

public class ArcValuesPanel extends SensorView
{
    /** Temperature data UI arc element */
    private final ArcValue tempArc;

    /** Humidity data UI arc element */
    private final ArcValue humidArc;

    /** air quality data UI arc element */
    private final ArcValue airQualityArc;

    /** Indicator for control connection */
    private final Indicator controllerIndicator = new Indicator("Controller Connected");

    /**
     * Constructor
     */
    public ArcValuesPanel()
    {
        /* Bar value initialization*/
        if(config.getUnits() ==1)
        {
            tempArc = new ArcValue("Temperature", "F", 0,122);
        }

        else
        {
            tempArc = new ArcValue("Temperature", "C", 0, 50);
        }

        humidArc = new ArcValue("Humidity", "%", 0, 100);
        airQualityArc = new ArcValue("Air Quality", 0, 500);

        /* layout Init */
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints constraints = new GridBagConstraints();
        this.setLayout(layout);

        /* Common constraints */
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        constraints.insets = new Insets(20,0,0,0);

        /* Add Elements */
        constraints.gridx = 0;
        constraints.gridy = 0;
        add(tempArc,constraints);

        constraints.gridx +=1;
        humidArc.setFillColor(Color.BLUE);
        add(humidArc, constraints);

        constraints.gridwidth = 2;
        if(config.isAirQualitySensorEnabled())
        {
            constraints.gridy += 1;
            constraints.gridx = 0;
            add(airQualityArc, constraints);
        }

        constraints.gridwidth = 1;
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
                tempArc.setCurrentValue(sensorData.getTempInteger());
                if(sensorData.getTempInteger() < config.getThreshold1())
                {
                    tempArc.setFillColor(config.getStatusColor1());
                }

                else if(sensorData.getTempInteger() >= config.getThreshold1() && sensorData.getTempInteger() < config.getThreshold2())
                {
                    tempArc.setFillColor(config.getStatusColor2());
                }

                else if(sensorData.getTempInteger() > config.getThreshold2())
                {
                    tempArc.setFillColor(config.getStatusColor3());
                }
            });
        }

        /* Humidity data updated */
        else if(SensorBean.UPDATED_HUMIDITY.equals(propertyName))
        {
            javax.swing.SwingUtilities.invokeLater(() -> humidArc.setCurrentValue(sensorData.getHumidityInteger()));
        }

        else if(SensorBean.UPDATED_AIR_QUALITY.equals(propertyName))
        {
            int quality = sensorData.getAirQualityInt();
            airQualityArc.setCurrentValue(quality);

            if(quality < 200)
            {
                airQualityArc.setFillColor(config.getStatusColor1());
            }

            else if(quality > 200 && quality < 300)
            {
                airQualityArc.setFillColor(config.getStatusColor2());
            }

            else if(quality > 300)
            {
                airQualityArc.setFillColor(config.getStatusColor3());
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
