package org.todo_programming.ui.barvalue;

import javax.swing.event.SwingPropertyChangeSupport;
import java.awt.*;
import java.beans.PropertyChangeListener;

public class BarValueModel
{
    /** property change support */
    private final SwingPropertyChangeSupport changeSupport;

    /** Event fired when color changes */
    public static final String BAR_COLOR_CHANGE_EVENT = "barColor";

    /** Text color */
    public static final String TEXT_COLOR_CHANGE_EVENT = "textColor";

    /** Event fired when min value changes */
    public static final String MIN_CHANGE_EVENT = "minChange";

    /** Event fired when max value changes */
    public static final String MAX_CHANGE_EVENT = "maxChange";

    /** Event fired when current depicted value changes */
    public static final String CURRENT_VALUE_EVENT = "currentValue";

    /** value where area starts to fill */
    private float min = 0f;

    /** Value where draw area is completely filled */
    private float max = 100;

    /** Current value depicted */
    private float currentValue = 0f;

    /** Color used to draw */
    private Color barColor = Color.GREEN;

    /** Color for text on the UI element */
    private Color textColor = Color.WHITE;

    /**
     * Constructor
     */
    public BarValueModel()
    {
        changeSupport = new SwingPropertyChangeSupport(this);
    }

    /**
     * Constructor
     */
    public BarValueModel(float min, float max)
    {
        this.min = min;
        this.max = max;
        changeSupport = new SwingPropertyChangeSupport(this);
    }

    /**
     *
     * @return current minimum value
     */
    public float getMin()
    {
        return min;
    }

    /**
     *
     * @return current maximum value
     */
    public float getMax()
    {
        return max;
    }

    /**
     *
     * @return color of drawing
     */
    public Color getBarColor()
    {
        return barColor;
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
     * @param min minimum value
     */
    public void setMin(float min)
    {
        changeSupport.firePropertyChange(MIN_CHANGE_EVENT, this.min, this.min = min);
    }

    /**
     *
     * @param max maximum value
     */
    public void setMax(float max)
    {
        changeSupport.firePropertyChange(MAX_CHANGE_EVENT, this.max, this.max = max);
    }

    public float getFillPercent()
    {
        return ((currentValue - min) / (max) * 100f);
    }

    /**
     *
     * @param currentValue current value
     */
    public void setCurrentValue(float currentValue)
    {
        changeSupport.firePropertyChange(CURRENT_VALUE_EVENT, this.currentValue, this.currentValue = currentValue);
    }

    /**
     *
     * @param color barcolor
     */
    public void setBarColor(Color color)
    {
        changeSupport.firePropertyChange(BAR_COLOR_CHANGE_EVENT, this.barColor, this.barColor = color);
    }

    /**
     *
     * @return color of text
     */
    public Color getTextColor()
    {
        return textColor;
    }

    /**
     *
     * @param textColor text color to use
     */
    public void setTextColor(Color textColor)
    {
        changeSupport.firePropertyChange(BAR_COLOR_CHANGE_EVENT, this.textColor, this.textColor = textColor);
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
