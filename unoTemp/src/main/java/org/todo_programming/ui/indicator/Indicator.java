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

    private String descriptiveText = "";

    /**
     * Constructor
     */
    public Indicator()
    {
        model = new IndicatorModel();
        model.addPropertyChangeListener(this);
    }

    /**
     * Constructor
     */
    public Indicator(String descriptiveText)
    {
        this.descriptiveText = descriptiveText;
        model = new IndicatorModel();
        model.addPropertyChangeListener(this);
    }

    @Override
    public void paint(Graphics g)
    {
        super.paint(g);
        Graphics2D g2d = (Graphics2D)(g);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        int centerY = getHeight()/2;
        int drawHeight;
        int drawWidth;
        int stroke;
        int fontSize;
        int padding = (int)(getHeight() * 0.02);
        if(getHeight() > getWidth())
        {
            fontSize = (int)(getWidth() * 0.35);
            stroke = (int)(getWidth() * 0.08);
            drawHeight = drawWidth = (int)(getWidth() * 0.40);
        }

        else
        {
            fontSize = (int)(getHeight()* 0.35);
            stroke = (int)(getHeight() * 0.08);
            drawHeight = drawWidth = (int)(getHeight() * 0.40);
        }

        /* Draw Indicator description text */
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Montserrat", Font.PLAIN, fontSize));
        FontMetrics metrics = g2d.getFontMetrics();
        int x = 0;
        int y = centerY - drawHeight/2 +((drawHeight - metrics.getHeight())/2) + metrics.getAscent();
        g2d.drawString(descriptiveText,x,y);

        /* Outer border */
        GradientPaint gp = new GradientPaint(0, 0, Color.BLACK, drawWidth, drawHeight, Color.gray, true);
        g2d.setPaint(gp);

        /* Draw indicator */
        int startX = x + metrics.stringWidth(descriptiveText) + stroke + padding;
        g2d.setStroke(new BasicStroke(stroke));
        g2d.drawRect(startX,centerY - drawHeight/2, drawWidth, drawHeight);
       g2d.setColor(Color.GRAY);

        switch (model.getIndicatorStatus())
        {
            case DISABLED:
                g2d.setColor(Color.GRAY);
                break;
            case WARN:
                g2d.setColor(Color.YELLOW);
                break;
            case HEALTHY:
                g2d.setColor(Color.GREEN);
                break;
            case ERROR:
                g2d.setColor(Color.GREEN);
                break;
            default:
                g2d.setColor(Color.GRAY);
                break;
        }

       
        g2d.fillRect(startX,centerY - drawHeight/2, drawWidth, drawHeight);
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
        Indicator indicator = new Indicator("Connected");
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
                indicator.setStatus(IndicatorStatus.getEnumFromValue(status));
            }
        };
        uiTimer.scheduleAtFixedRate(task, 2000, 2000);
        frame.setVisible(true);
    }
}
