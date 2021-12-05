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
    /** Model of UI element */
    private final ArcValueModel model;

    /** Descriptive text for what is being displayed */
    private String valueDescription = "";

    /** Units associated with displayed value */
    private String valueUnits = "";

    /**
     * Constructor
     */
    public ArcValue()
    {
        model = new ArcValueModel();
        model.addPropertyChangeListener(this);
    }


    public ArcValue(String valueDescription, String valueUnits, int min, int max)
    {
        model = new ArcValueModel(min, max);
        model.addPropertyChangeListener(this);
        this.valueDescription = valueDescription;
        this.valueUnits = valueUnits;
    }

    public ArcValue(String valueDescription, int min, int max)
    {
        model = new ArcValueModel(min,max);
        model.addPropertyChangeListener(this);
        this.valueDescription = valueDescription;
        this.valueUnits = valueUnits;
    }


    /**
     * Constructor
     *
     * @param valueDescription text for UI element label
     */
    public ArcValue(String valueDescription)
    {
        model = new ArcValueModel();
        model.addPropertyChangeListener(this);
        this.valueDescription = valueDescription;
    }

    /**
     * Constructor
     *
     * @param valueDescription Text for UI element label
     * @param valueUnits Units of value
     */
    public ArcValue(String valueDescription, String valueUnits)
    {
        model = new ArcValueModel();
        model.addPropertyChangeListener(this);
        this.valueUnits = valueUnits;
        this.valueDescription = valueDescription;
    }

    @Override
    public void paint(Graphics g)
    {
        super.paint(g);
        Graphics2D g2d = (Graphics2D)(g);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        int drawHeight;
        int drawWidth;
        int stroke;
        if(getHeight() > getWidth())
        {
            stroke = (int)(getWidth() * 0.075);
            drawHeight = drawWidth = (int)(getWidth() * 0.80);
        }

        else
        {
            stroke = (int)(getHeight() * 0.075);
            drawHeight = drawWidth = (int)(getHeight() * 0.80);
        }

        int centerX = getWidth()/2 - drawWidth/2;
        int centerY = getHeight()/2 - drawHeight /2 + (int)(getHeight() * 0.05);
        g2d.setStroke(new BasicStroke(stroke));
        g2d.setColor(model.getFillColor());
        g2d.drawArc(centerX, centerY, drawWidth, drawHeight, 0, (int) (model.getFillPercent()/10 * -36f));

        /* Draw Value, and units in center of arc */
        g2d.setColor(model.getTextColor());
        String text = model.getCurrentValue() + " " + valueUnits;
        int fontSize = (int)(0.18 * drawHeight);
        g2d.setFont(new Font("Montserrat", Font.PLAIN, fontSize));
        FontMetrics metrics = g2d.getFontMetrics();
        int x = centerX + (drawWidth - metrics.stringWidth(text))/2;
        int y = centerY + ((drawHeight - metrics.getHeight())/2) + metrics.getAscent();
        g2d.drawString(text,x,y);

        /* Draw value description text above value */
        g2d.setColor(model.getTextColor());
        g2d.setFont(new Font("Montserrat", Font.PLAIN, fontSize));
        x = centerX + (drawWidth - metrics.stringWidth(valueDescription))/2;
        y = 1 + (((int)(getHeight() * 0.10f) - metrics.getHeight())/2) + metrics.getAscent();
        g2d.drawString(valueDescription,x,y);
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
     * @param currentValue currently displayed value
     */
    public void setCurrentValue(float currentValue)
    {
        model.setCurrentValue(currentValue);
    }

    /**
     *
     * @param minValue minimum value possible for currentValue
     */
    public void setMinValue(float minValue)
    {
        model.setMinValue(minValue);
    }

    /**
     *
     * @param maxValue maximum value possible for current value
     */
    public void setMaxValue(float maxValue)
    {
        model.setMaxValue(maxValue);
    }

    /**
     *
     * @param color Fill color for arc
     */
    public void setFillColor(Color color)
    {
        model.setFillColor(color);
    }

    /**
     *
     * @param color Text color
     */
    public void setTextColor(Color color)
    {
        model.setTextColor(color);
    }

    /**
     * test main
     *
     * @param args CLI arguments
     */
    public static void main(String[] args)
    {
        ArcValue indicator = new ArcValue("test", "%");
        indicator.setMinValue(0);
        indicator.setMaxValue(10);
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
                int max =11;
                int status = (int)(Math.random() * (max - min) + min);
                indicator.setCurrentValue(status);
            }
        };
        uiTimer.scheduleAtFixedRate(task, 2000, 2000);
        frame.setVisible(true);
    }
}
