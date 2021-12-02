package org.todo_programming.ui.ArcValue;

import javax.swing.event.SwingPropertyChangeSupport;
import java.awt.*;
import java.beans.PropertyChangeListener;

public class ArcValueModel
{

    /** */
    private final SwingPropertyChangeSupport changeSupport;

    /** */
    public static final String TEXT_COLOR_CHANGE_EVENT = "textColor";

    /** */
    public static final String FILL_COLOR_CHANGE_EVENT = "fillColor";

    /** */
    public static final String CURRENT_VALUE_CHANGE_EVENT = "currentValue";

    /** */
    public static final String MIN_VALUE_CHANGE_EVENT = "minValue";

    /** */
    public static final String MAX_VALUE_CHANGE_EVENT = "maxValue";

    /** */
    private float minValue = 0;

    /** */
    private float maxValue = 0;

    /** */
    private float currentValue = 0;

    /** */
    private Color fillColor;

    /** */
    private Color textColor;

    /**
     * Constructor
     */
    public ArcValueModel()
    {
        changeSupport = new SwingPropertyChangeSupport(this);
    }

    /**
     *
     * @return
     */
    public int getEndAngle()
    {
        return (int)(currentValue - minValue / (maxValue) * 100f);
    }

    /**
     *
     * @return
     */
    public float getMinValue()
    {
        return minValue;
    }

    /**
     *
     * @param minValue
     */
    public void setMinValue(float minValue)
    {
        this.minValue = minValue;
    }

    /**
     *
     * @return
     */
    public float getMaxValue()
    {
        return maxValue;
    }

    /**
     *
     * @param maxValue
     */
    public void setMaxValue(float maxValue)
    {
        this.maxValue = maxValue;
    }

    /**
     *
     * @return
     */
    public float getCurrentValue()
    {
        return currentValue;
    }

    /**
     *
     * @param currentValue
     */
    public void setCurrentValue(float currentValue)
    {
        this.currentValue = currentValue;
    }

    /**
     *
     * @return
     */
    public Color getFillColor()
    {
        return fillColor;
    }

    /**
     *
     * @param fillColor
     */
    public void setFillColor(Color fillColor)
    {
        this.fillColor = fillColor;
    }

    /**
     *
     * @return
     */
    public Color getTextColor()
    {
        return textColor;
    }

    /**
     *
     * @param textColor
     */
    public void setTextColor(Color textColor)
    {
        this.textColor = textColor;
    }

    /**
     *
     * @param observer object that implements PropertyChangeListener
     */
    public void addPropertyChangeListener(PropertyChangeListener observer)
    {
        changeSupport.addPropertyChangeListener(observer);
    }

    /**
     *
     * @param observer object that implements PropertyChangeListener
     */
    public void removePropertyChangeListener(PropertyChangeListener observer)
    {
        changeSupport.removePropertyChangeListener(observer);
    }
}
