package org.todo_programming.ui.panels;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.todo_programming.ArduinoMonitor.Config;
import org.todo_programming.ArduinoMonitor.SensorBean;
import org.todo_programming.ui.MainFrame;
import org.todo_programming.ui.ScalableLabel;

import javax.swing.*;
import java.awt.*;

public class TextValuesPanel extends SensorView
{
    /** Label for temperature */
    private final ScalableLabel lblTempValue;

    /** Label for humidity */
    private final ScalableLabel lblHumidityValue;

    /** Air Quality */
    private final ScalableLabel lblAirQualityValue;

    /** Application configuration */
    private final Config config = Config.getInstance();

    /** Shows progress of controller connection */
    JProgressBar connectionProgress;

    /** Label in progress bar */
    JLabel progressLabel;

    /* log4j instance */
    static final Logger log = LogManager.getLogger(MainFrame.class.getName());

    /** Used to track data rate */
    long startTime;

    public TextValuesPanel(SensorBean sensorData)
    {
        super();
        startTime = System.currentTimeMillis();

        /* Label initialization*/
        lblTempValue = new ScalableLabel("NA", 0.20f);
        lblTempValue.setForeground(Color.WHITE);

        /* Temp sensor labels */;
        lblHumidityValue = new ScalableLabel("NA",0.20f);
        lblHumidityValue.setForeground(Color.BLUE);

        /* Air quality labels */
        lblAirQualityValue = new ScalableLabel("Air: NA", 0.15f);
        lblAirQualityValue.setForeground(Color.WHITE);
        lblAirQualityValue.setHorizontalAlignment(JLabel.CENTER);

        /* layout Init */
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints constraints = new GridBagConstraints();
        setLayout(layout);

        /* Common constraints */
        constraints.fill = GridBagConstraints.RELATIVE;
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;

        /* Add Elements */
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(1,2,1,1);
        add(lblTempValue,constraints);

        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.insets = new Insets(1,1,1,1);
        add(lblHumidityValue,constraints);

        if(config.isAirQualitySensorEnabled())
        {
            constraints.gridx = 0;
            constraints.gridy = 1;
            constraints.gridwidth = 2;
            add(lblAirQualityValue, constraints);
        }

        /* Add progress bar */
        constraints.gridy = 2;
        constraints.gridx = 0;
        constraints.gridwidth = 2;
        constraints.gridheight = 2;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        connectionProgress = new JProgressBar();
        connectionProgress.setIndeterminate(true);
        connectionProgress.setFont(lblTempValue.getFont());
        connectionProgress.setLayout(new BorderLayout());
        JLabel progressLabel = new JLabel("connecting...");
        progressLabel.setHorizontalAlignment(JLabel.CENTER);
        progressLabel.setVerticalAlignment(JLabel.CENTER);
        connectionProgress.add(progressLabel,BorderLayout.CENTER);
        add(connectionProgress, constraints);

        setBorder(BorderFactory.createMatteBorder(7, 7, 7, 7, Color.BLUE));
        setBackground(Color.BLACK);
        setVisible(true);
    }

    @Override
   public void update(String propertyName, SensorBean sensorData)
    {
        //Temperature data updated
        if(SensorBean.UPDATED_TEMPERATURE.equals(propertyName))
        {
            javax.swing.SwingUtilities.invokeLater(() -> {
                lblTempValue.setText(sensorData.getTemp());
                if(sensorData.getTempInteger() < config.getThreshold1())
                {
                    setBorder(BorderFactory.createMatteBorder(7, 7, 7, 7, config.getStatusColor1()));
                    lblTempValue.setForeground(config.getStatusColor1());
                }

                else if(sensorData.getTempInteger() >= config.getThreshold1() && sensorData.getTempInteger() < config.getThreshold2())
                {
                    setBorder(BorderFactory.createMatteBorder(7, 7, 7, 7, config.getStatusColor2()));
                    lblTempValue.setForeground(config.getStatusColor2());
                }

                else if(sensorData.getTempInteger() > config.getThreshold2())
                {
                    setBorder(BorderFactory.createMatteBorder(7, 7, 7, 7, config.getStatusColor3()));
                    lblTempValue.setForeground(config.getStatusColor3());
                }
            });
        }

        /* Humidity data updated */
        else if(SensorBean.UPDATED_HUMIDITY.equals(propertyName))
        {
            javax.swing.SwingUtilities.invokeLater(() -> lblHumidityValue.setText(sensorData.getHumidity()));
        }

        else if(SensorBean.UPDATED_AIR_QUALITY.equals(propertyName))
        {
            int quality = sensorData.getAirQualityInt();
            if(quality < 200)
            {
                lblAirQualityValue.setText("Air: Good");
                lblAirQualityValue.setForeground(config.getStatusColor1());
            }

            else if(quality > 200 && quality < 300)
            {
                lblAirQualityValue.setText("Air: Moderate");
                lblAirQualityValue.setForeground(config.getStatusColor2());
            }

            else if(quality > 300)
            {
                lblAirQualityValue.setText("Air: Bad");
                lblAirQualityValue.setForeground(config.getStatusColor3());
            }
        }

        else if(SensorBean.UPDATE_CONTROLLER_CONNECTION.equals(propertyName))
        {
            if(sensorData.isControllerConnected())
            {
                javax.swing.SwingUtilities.invokeLater(() ->connectionProgress.setVisible(false));
                javax.swing.SwingUtilities.invokeLater(() ->connectionProgress.setEnabled(false));
                log.info("set visible false");
            }

            else
            {
                javax.swing.SwingUtilities.invokeLater(() ->connectionProgress.setVisible(true));
                javax.swing.SwingUtilities.invokeLater(() ->connectionProgress.setEnabled(true));
                javax.swing.SwingUtilities.invokeLater(() ->progressLabel.setText("connecting..."));
            }
        }
    }
}
