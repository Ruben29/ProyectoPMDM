package com.example.rubn29.culebrapmdm;

/**
 * Created by Rubén 29 on 06/03/2016.
 */

/**
 * Definición de la dirección correspondiente a los 4 posibles movimientos
 **/
public enum Direccion {
    RIGHT(1, 0),
    LEFT(-1, 0),
    DOWN(0, 1),
    UP(0, -1);

    final Point direccion;

    Direccion(final int x, final int y) {
        this.direccion = new Point(x, y);
    }

    Point point() {
        return direccion;
    }
}
