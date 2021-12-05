package org.todo_programming.ui.panels;

import org.todo_programming.ArduinoMonitor.Config;
import org.todo_programming.ArduinoMonitor.SensorBean;

import javax.swing.*;

public abstract class SensorView extends JPanel
{
    /** Application configuration */
    protected final Config config = Config.getInstance();

    /**
     * Each child class must use this to update components
     *
     * @param sensorData sensor data to update UI
     */
    public abstract void update(String propertyName, SensorBean sensorData);
}
