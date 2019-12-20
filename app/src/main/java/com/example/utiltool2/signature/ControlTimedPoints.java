package com.example.utiltool2.signature;

/**
 * author:lgh on 2019-12-19 11:45
 */
public class ControlTimedPoints {

    public TimedPoint c1;
    public TimedPoint c2;

    public ControlTimedPoints set(TimedPoint c1, TimedPoint c2) {
        this.c1 = c1;
        this.c2 = c2;
        return this;
    }

}
