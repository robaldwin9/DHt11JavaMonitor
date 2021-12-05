package org.todo_programming.ui.barvalue;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Timer;
import java.util.TimerTask;

public class BarValue extends Component implements PropertyChangeListener
{
    /** Model for UI element */
    private final BarValueModel model;

    /** Descriptive text for value being displayed */
    String barDescription = "";

    /** Units if applicable */
    String unitsText = "";

    /**
     * Constructor
     */
    public BarValue()
    {
        model = new BarValueModel();
        model.addPropertyChangeListener(this);
    }

    /**
     *
     * @param valueLabel provide label text for UI element
     */
    public BarValue(String valueLabel)
    {
        model = new BarValueModel();
        model.addPropertyChangeListener(this);
        this.barDescription = valueLabel;
    }

    /**
     *
     * @param valueLabel value description
     * @param unitsText Units to display if any
     */
    public BarValue(String valueLabel, String unitsText)
    {
        model = new BarValueModel();
        model.addPropertyChangeListener(this);
        this.barDescription = valueLabel;
        this.unitsText = unitsText;
    }

    /**
     *
     * @param valueLabel value description
     * @param unitsText Units to display if any
     */
    public BarValue(String valueLabel, String unitsText, float min, float  max)
    {
        model = new BarValueModel(min, max);
        model.addPropertyChangeListener(this);
        this.barDescription = valueLabel;
        this.unitsText = unitsText;
    }

    /**
     *
     * @param valueLabel value description
     * @param
     */
    public BarValue(String valueLabel, float min, float  max)
    {
        model = new BarValueModel(min, max);
        model.addPropertyChangeListener(this);
        this.barDescription = valueLabel;
    }

    @Override
    public void paint(Graphics g)
    {
        super.paint(g);
        Graphics2D g2d = (Graphics2D)(g);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        int drawWidth = (int)(getWidth() * 0.90);
        int drawHeight = (int)(getHeight() * 0.90);

        /* Draw background */
        GradientPaint gp = new GradientPaint(0, 0, Color.BLACK, drawWidth, drawHeight, Color.gray, true);
        Paint oldPaint = g2d.getPaint();
        g2d.setPaint(gp);
        g2d.fillRect((int)(getWidth() * 0.05f),drawHeight/4, drawWidth, drawHeight/2);

        /* Fill with color based on current value */
        g2d.setPaint(oldPaint);
        g2d.setColor(model.getBarColor());
        int newWidth = (int)(drawWidth / 100f * model.getFillPercent());
        g2d.fillRect((int)(getWidth() * 0.05f),drawHeight/4, newWidth, drawHeight/2);

        /* Draw current value */
        g2d.setColor(model.getTextColor());
        String text = model.getCurrentValue() + " " + unitsText;
        int fontSize = (int)(0.20 * drawHeight);
        g2d.setFont(new Font("TimesRoman", Font.PLAIN, fontSize));
        FontMetrics metrics = g2d.getFontMetrics();
        int x = ((int)(((getWidth() * 0.05f) + drawWidth)) - metrics.stringWidth(text))/2;
        int y = drawHeight/4 + (drawHeight/4) + metrics.getAscent() - fontSize/2 ;
        g2d.setColor(Color.WHITE);
        g2d.drawString(text,x,y);

        /* Draw bar title */
        x = ((int)(((getWidth() * 0.05f) + drawWidth)) - metrics.stringWidth(barDescription))/2;
        y = drawHeight/6 ;
        g2d.drawString(barDescription, x, y);

        /* Draw min value text */
        fontSize = (int)(0.10 * drawHeight);
        g2d.setFont(new Font("TimesRoman", Font.PLAIN, fontSize));
        metrics = g2d.getFontMetrics();
        text = String.valueOf(model.getMin());
        x = (int)(getWidth() * 0.06f) + metrics.stringWidth(text)/2;
        y = drawHeight/4 +((drawHeight/2 - metrics.getHeight())/2) + metrics.getAscent() + metrics.stringWidth(text)/2;
        drawRotatedText(g2d, x, y, -90, text);

        /* Draw max value text */
        text = String.valueOf(model.getMax());
        x = (int) (drawWidth + (getWidth() * 0.045f));
        y = drawHeight/4 +((drawHeight/2 - metrics.getHeight())/2) + metrics.getAscent()+ metrics.stringWidth(text)/2;
        drawRotatedText(g2d, x, y, -90, text);
    }

    /**
     *
     * @param g2d 2d graphics context
     * @param x x-coordinate
     * @param y y-coordinate
     * @param angle text rotation angle
     * @param text text to draw
     */
    private void drawRotatedText(Graphics2D g2d, double x, double y, int angle, String text)
    {
        g2d.translate((float)x,(float)y);
        g2d.rotate(Math.toRadians(angle));
        g2d.drawString(text,0,0);
        g2d.rotate(-Math.toRadians(angle));
        g2d.translate(-(float)x,-(float)y);
    }

    @Override
    public void propertyChange(PropertyChangeEvent propertyChangeEvent)
    {
        switch(propertyChangeEvent.getPropertyName())
        {
            case BarValueModel.BAR_COLOR_CHANGE_EVENT:
            case BarValueModel.CURRENT_VALUE_EVENT:
            case BarValueModel.MIN_CHANGE_EVENT:
            case BarValueModel.MAX_CHANGE_EVENT:
            case BarValueModel.TEXT_COLOR_CHANGE_EVENT:
                repaint();
                break;
            default:
                break;
        }
    }

    /**
     *
     * @param currentValue current value to depict
     */
    public void setCurrentValue(float currentValue)
    {
        model.setCurrentValue(currentValue);
    }

    /**
     *
     * @param color fill color
     */
    public void setBarColor(Color color)
    {
        model.setBarColor(color);
    }

    /**
     *
     * @param minValue minimum value possible
     */
    public void setMinValue(float minValue)
    {
        model.setMin(minValue);
    }

    /**
     *
     * @param maxValue max value possible
     */
    public void setMaxValue(float maxValue)
    {
        model.setMax(maxValue);
    }

    /**
     *
     * @param units Units text if any
     */
    public void setUnitsText(String units)
    {
        this.unitsText = units;
    }

    /**
     *
     * @param text label for UI element
     */
    public void setValueDescriptionText(String text)
    {
        barDescription = text;
    }

    /**
     * Test main
     *
     * @param args CLI arguments
     */
    public static void main(String[] args)
    {
        BarValue bar = new BarValue("Temperature",0, 500);
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
                int max = 500;
                int status = (int)(Math.random() * (max - min) + min);
               bar.setCurrentValue(status);
           }
       }; uiTimer.scheduleAtFixedRate(task, 2000, 2000);
    }
}
