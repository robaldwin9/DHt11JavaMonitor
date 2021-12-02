package org.todo_programming.ui.ArcValue;

import org.todo_programming.ui.indicator.Indicator;
import org.todo_programming.ui.indicator.IndicatorStatus;

import javax.swing.*;
import javax.swing.event.SwingPropertyChangeSupport;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Timer;
import java.util.TimerTask;

public class ArcValue extends Component implements PropertyChangeListener
{
    /** */
    private final ArcValueModel model;

    /**
     *
     */
    public ArcValue()
    {
        model = new ArcValueModel();
        model.addPropertyChangeListener(this);
    }

    @Override
    public void paint(Graphics g)
    {
        super.paint(g);
        Graphics2D g2d = (Graphics2D)(g);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        int drawHeight;
        int drawWidth;
        int stroke;
        if(getHeight() > getWidth())
        {
            stroke = (int)(getWidth() * 0.10);
            drawHeight = drawWidth = (int)(getWidth() * 0.90);
        }

        else
        {
            stroke = (int)(getHeight() * 0.10);
            drawHeight = drawWidth = (int)(getHeight() * 0.90);
        }

        int centerX = getWidth()/2 - drawWidth/2;
        int centerY = getHeight()/2 - drawHeight /2;

        g2d.setStroke(new BasicStroke(stroke));
        g2d.drawArc(centerX, centerY, drawWidth, drawHeight, 0 , 360);
    }

    @Override
    public void propertyChange(PropertyChangeEvent propertyChangeEvent)
    {
        switch(propertyChangeEvent.getPropertyName())
        {
            case ArcValueModel.TEXT_COLOR_CHANGE_EVENT:
            case ArcValueModel.FILL_COLOR_CHANGE_EVENT:
            case ArcValueModel.CURRENT_VALUE_CHANGE_EVENT:
            case ArcValueModel.MIN_VALUE_CHANGE_EVENT:
            case ArcValueModel.MAX_VALUE_CHANGE_EVENT:
                repaint();
        }
    }

    /**
     *
     * @param currentValue
     */
    public void setCurrentValue(float currentValue)
    {
        model.setCurrentValue(currentValue);
    }

    /**
     *
     * @param minValue
     */
    public void setMinValue(float minValue)
    {
        model.setMinValue(minValue);
    }

    /**
     *
     * @param maxValue
     */
    public void setMaxValue(float maxValue)
    {
        model.setMaxValue(maxValue);
    }

    /**
     *
     * @param color
     */
    public void setFillColor(Color color)
    {
        model.setFillColor(color);
    }

    /**
     *
     * @param color
     */
    public void setTextColor(Color color)
    {
        model.setTextColor(color);
    }

    /**
     *
     * @param args
     */
    public static void main(String[] args)
    {
        ArcValue indicator = new ArcValue();
        JFrame frame = new JFrame();
        frame.getContentPane().setBackground(Color.LIGHT_GRAY);
        frame.setPreferredSize(new Dimension(500,500));
        frame.setSize(new Dimension(300,300));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(indicator,BorderLayout.CENTER);

        java.util.Timer uiTimer = new Timer("UITimer");
        TimerTask task = new TimerTask()
        {
            @Override
            public void run() {
                int min = 0;
                int max = 3;
                int status = (int)(Math.random() * (max - min) + min);
                System.out.println("Status: " + status);
                //indicator.setStatus(IndicatorStatus.getEnumFromValue(status));
            }
        };
        uiTimer.scheduleAtFixedRate(task, 2000, 2000);
        frame.setVisible(true);
    }
}
