package com.example.rubn29.culebrapmdm;

/**
 * Created by Rubén 29 on 06/03/2016.
 */
public final class Point {

    final int x;
    final int y;

    public Point(final int x, final int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Método que permite comer el objeto y hacerlo desaparecer
     **/
    @Override
    public boolean equals(final Object puntinho) {
        boolean cogerla;

        if (this == puntinho) {
            cogerla = true;
        } else if (puntinho instanceof Point) {
            final Point that = (Point) puntinho;

            cogerla = this.x == that.x && this.y == that.y;
        } else {
            cogerla = false;
        }

        return cogerla;
    }


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
