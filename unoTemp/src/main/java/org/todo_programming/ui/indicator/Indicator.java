package org.todo_programming.ui.indicator;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Indicator extends Component implements PropertyChangeListener
{

    /** Model for UI element */
    private final IndicatorModel model;

    /**
     * Constructor
     */
    public Indicator()
    {
        model = new IndicatorModel();
        model.addPropertyChangeListener(this);
    }

    @Override
    public void paint(Graphics g)
    {
        super.paint(g);
        Graphics2D g2d = (Graphics2D)(g);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        int centerX = getWidth()/2;
        int centerY = getHeight()/2;
        int drawHeight;
        int drawWidth;
        int stroke;
        if(getHeight() > getWidth())
        {
            stroke = (int)(getWidth() * 0.15);
            drawHeight = drawWidth = (int)(getWidth() * 0.75);
        }

        else
        {
            stroke = (int)(getHeight() * 0.15);
            drawHeight = drawWidth = (int)(getHeight() * 0.75);
        }

        /* Outer border */
        GradientPaint gp = new GradientPaint(0, 0, Color.BLACK, drawWidth, drawHeight, Color.gray, true);
        g2d.setPaint(gp);

        switch (model.getIndicatorStatus())
        {
            case DISABLED:
                gp = new GradientPaint(0, 0, Color.GRAY, drawWidth, drawHeight, Color.BLACK, true);
                break;
            case WARN:
                gp = new GradientPaint(0, 0, Color.BLACK, drawWidth, drawHeight, Color.YELLOW, true);
                break;
            case HEALTHY:
                gp = new GradientPaint(0, 0, Color.BLACK, drawWidth, drawHeight, Color.GREEN, true);
                break;
            default:
                gp = new GradientPaint(0, 0, Color.GRAY, drawWidth, drawHeight, Color.BLACK, true);
                break;
        }

        g2d.setStroke(new BasicStroke(stroke));
        g2d.drawRect(centerX - drawWidth/2,centerY - drawHeight/2, drawWidth, drawHeight);
        g2d.setPaint(gp);
        g2d.fillRect(centerX - drawWidth/2,centerY - drawHeight/2, drawWidth, drawHeight);

    }

    /**
     *
     * @param status indicator status affects repaint method
     */
    public void setStatus(IndicatorStatus status)
    {
        model.setIndicatorStatusChange(status);
    }

    @Override
    public void propertyChange(PropertyChangeEvent propertyChangeEvent)
    {
        if(propertyChangeEvent.getPropertyName().equals(IndicatorModel.INDICATOR_STATUS_CHANGE))
        {
            repaint();
        }
    }

    /**
     * Quick test code
     *
     * @param args cmd line arguments
     */
    public static void main(String[] args)
    {
        Indicator indicator = new Indicator();
        JFrame frame = new JFrame();
        frame.getContentPane().setBackground(Color.LIGHT_GRAY);
        frame.setPreferredSize(new Dimension(500,500));
        frame.setSize(new Dimension(300,300));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(indicator,BorderLayout.CENTER);

        Timer uiTimer = new Timer("UITimer");
        TimerTask task = new TimerTask()
        {
            @Override
            public void run() {
                int min = 0;
                int max = 3;
                int status = (int)(Math.random() * (max - min) + min);
                System.out.println("Status: " + status);
                indicator.setStatus(IndicatorStatus.getEnumFromValue(status));
            }
        };
        uiTimer.scheduleAtFixedRate(task, 2000, 2000);
        frame.setVisible(true);
    }
}
