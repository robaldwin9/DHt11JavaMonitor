package org.todo_programming.ui.indicator;

import javax.swing.event.SwingPropertyChangeSupport;
import java.beans.PropertyChangeListener;

public class IndicatorModel
{
    /** Swing property change support */
    private final SwingPropertyChangeSupport changeSupport;

    /** Event name */
    public static final String INDICATOR_STATUS_CHANGE = "statusChange";

    /** Indicator status determines color of element */
    private IndicatorStatus status = IndicatorStatus.DISABLED;

    /**
     * Constructor
     */
    public IndicatorModel()
    {
        changeSupport = new SwingPropertyChangeSupport(this);
    }

    /**
     *
     * @param observer object that implements PropertyChangeListener
     */
    public void addPropertyChangeListener(PropertyChangeListener observer)
    {
        changeSupport.addPropertyChangeListener(observer);
    }

    public void setIndicatorStatusChange(IndicatorStatus status)
    {
        changeSupport.firePropertyChange(INDICATOR_STATUS_CHANGE, this.status, this.status = status);
    }

    public IndicatorStatus getIndicatorStatus()
    {
        return status;
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
