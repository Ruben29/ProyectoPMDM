package com.example.rubn29.culebrapmdm;

import com.example.rubn29.culebrapmdm.Point;

import java.util.List;

/**
 * Created by Rubén 29 on 07/03/2016.
 */

/**
 * Interfaz relacionada con la clase DelegateAdapter
 **/
public interface TouchDelegate {

    void dibujarCulebra(final List<Point> culebra);

    void dibujarComida(final Point comida);

    void gameOver(final List<Point> culebra);

    void comerComida(final Point comida);

    void nextTick(final List<Point> culebra);
}
