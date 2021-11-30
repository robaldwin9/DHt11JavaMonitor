package org.todo_programming.ui.barvalue;

import org.todo_programming.ui.indicator.Indicator;
import org.todo_programming.ui.indicator.IndicatorModel;
import org.todo_programming.ui.indicator.IndicatorStatus;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Timer;
import java.util.TimerTask;

public class BarValue extends Component implements PropertyChangeListener
{
    private BarValueModel model;

    public BarValue()
    {
        model = new BarValueModel();
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

        drawWidth = (int)(getWidth() * 0.90);
        drawHeight = (int)(getHeight() * 0.90);
//        if(getHeight() > getWidth())
//        {
//            stroke = (int)(getWidth() * 0.15);
//
//        }
//
//        else
//        {
//            stroke = (int)(getHeight() * 0.15);
//            drawHeight = (int)(getHeight() * 0.90);
//        }

        GradientPaint gp = new GradientPaint(0, 0, Color.BLACK, drawWidth, drawHeight, Color.gray, true);
        Paint oldPaint = g2d.getPaint();
        g2d.setPaint(gp);
        g2d.fillRect((int)(getWidth() * 0.05f),drawHeight/4, drawWidth, drawHeight/2);

        g2d.setPaint(oldPaint);
        g2d.setColor(model.getBarColor());
        System.out.println("repaint");
        float fillPercent =  10 - 10/(100f - 10) * 100f;
        System.out.println("fill percent: " + model.getFillPercent());
        int newWidth = (int)(drawWidth / 100f * model.getFillPercent());
        g2d.fillRect((int)(getWidth() * 0.05f),drawHeight/4, newWidth, drawHeight/2);
    }

    public void setCurrentValue(float currentValue)
    {
        model.setCurrentValue(currentValue);
    }

    public static void main(String[] args)
    {
        BarValue bar = new BarValue();
        JFrame frame = new JFrame();
        frame.getContentPane().setBackground(Color.LIGHT_GRAY);
        frame.setPreferredSize(new Dimension(500,500));
        frame.setSize(new Dimension(300,300));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(bar,BorderLayout.CENTER);
        frame.setVisible(true);

        java.util.Timer uiTimer = new Timer("UITimer");
        TimerTask task = new TimerTask()
        {
            @Override
            public void run() {
                int min = 0;
                int max = 100;
                int status = (int)(Math.random() * (max - min) + min);
                System.out.println("Status: " + status);
                bar.setCurrentValue(status);
            }
        };
        uiTimer.scheduleAtFixedRate(task, 2000, 2000);
    }

    @Override
    public void propertyChange(PropertyChangeEvent propertyChangeEvent)
    {
        switch(propertyChangeEvent.getPropertyName())
        {
            case BarValueModel.COLOR_CHANGE_EVENT:
            case BarValueModel.CURRENT_VALUE_EVENT:
            case BarValueModel.MIN_CHANGE_EVENT:
            case BarValueModel.MAX_CHANGE_EVENT:
                repaint();
                break;
            default:
                break;
        }
    }
}
