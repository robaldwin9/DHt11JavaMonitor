package org.todo_programming.ui.ArcValue;

import javax.swing.event.SwingPropertyChangeSupport;
import java.awt.*;
import java.beans.PropertyChangeListener;

public class ArcValueModel
{

    /** property change support(observer pattern) */
    private final SwingPropertyChangeSupport changeSupport;

    /** Event string for  text color change */
    public static final String TEXT_COLOR_CHANGE_EVENT = "textColor";

    /** Event string for fill color change */
    public static final String FILL_COLOR_CHANGE_EVENT = "fillColor";

    /** Event string for value change  */
    public static final String CURRENT_VALUE_CHANGE_EVENT = "currentValue";

    /** Event string for min value change */
    public static final String MIN_VALUE_CHANGE_EVENT = "minValue";

    /** Event string for max value change */
    public static final String MAX_VALUE_CHANGE_EVENT = "maxValue";

    /** minimum value possible */
    private float minValue = 100;

    /** maximum value possible */
    private float maxValue = 0;

    /** Current value displayed by UI */
    private float currentValue = 0;

    /** current arc color */
    private Color fillColor = Color.GREEN;

    /** current text color */
    private Color textColor = Color.WHITE;

    /**
     * Constructor
     */
    public ArcValueModel()
    {
        changeSupport = new SwingPropertyChangeSupport(this);
    }

    public ArcValueModel(int min, int max)
    {
        this.minValue = min;
        this.maxValue = max;
        changeSupport = new SwingPropertyChangeSupport(this);
    }


    /**
     *
     * @return fill percent of arc, which is useful for drawing
     */
    public float getFillPercent()
    {
        return ((currentValue - minValue) / (maxValue) * 100f);
    }

    /**
     * get minimum value
     *
     * @return min value possible
     */
    public float getMinValue()
    {
        return minValue;
    }

    /**
     * set minimum
     *
     * @param minValue min value possible
     */
    public void setMinValue(float minValue)
    {

        changeSupport.firePropertyChange(MIN_VALUE_CHANGE_EVENT, this.minValue, this.minValue = minValue);
    }

    /**
     *
     * @return maximum value
     */
    public float getMaxValue()
    {
        return maxValue;
    }

    /**
     *
     * @param maxValue maximum value
     */
    public void setMaxValue(float maxValue)
    {
        changeSupport.firePropertyChange(MAX_VALUE_CHANGE_EVENT, this.maxValue, this.maxValue = maxValue);
    }

    /**
     *
     * @return current value
     */
    public float getCurrentValue()
    {
        return currentValue;
    }

    /**
     *
     * @param currentValue value to display
     */
    public void setCurrentValue(float currentValue)
    {
        changeSupport.firePropertyChange(CURRENT_VALUE_CHANGE_EVENT, this.currentValue, this.currentValue = currentValue);
    }

    /**
     *
     * @return arc color
     */
    public Color getFillColor()
    {
        return fillColor;
    }

    /**
     *
     * @param fillColor arc color
     */
    public void setFillColor(Color fillColor)
    {
        changeSupport.firePropertyChange(FILL_COLOR_CHANGE_EVENT, this.fillColor, this.fillColor = fillColor);
    }

    /**
     *
     * @return text color
     */
    public Color getTextColor()
    {
        return textColor;
    }

    /**
     *
     * @param textColor color of text in UI
     */
    public void setTextColor(Color textColor)
    {
        changeSupport.firePropertyChange(TEXT_COLOR_CHANGE_EVENT, this.textColor, this.textColor = textColor);
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
