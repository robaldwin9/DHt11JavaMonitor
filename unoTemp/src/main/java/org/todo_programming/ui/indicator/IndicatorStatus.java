package org.todo_programming.ui.indicator;

public enum IndicatorStatus
{
    DISABLED(0),
    WARN(1),
    HEALTHY(2),
    ERROR(3);

    private final int value;

    private IndicatorStatus(int value)
    {
        this.value = value;
    }

    public int getValue()
    {
        return value;
    }

    public static IndicatorStatus getEnumFromValue(int value)
    {
        IndicatorStatus status = DISABLED;
        switch (value)
        {
            case 0:
                status = DISABLED;
                break;
            case 1:
                status = WARN;
                break;
            case 2:
                status = HEALTHY;
                break;
            case 3:
                status = ERROR;
                break;
            default:
                status = DISABLED;
                break;
        }

        return status;
    }
}
