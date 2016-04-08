package com.example.rubn29.culebrapmdm;

import com.example.rubn29.culebrapmdm.Culebra;
import com.example.rubn29.culebrapmdm.Point;

/**
 * Created by Rubén 29 on 11/03/2016.
 */

/**
 * Clase para la fijacion de coordenadas remotas de la comida
 **/
class FijarComida extends Culebra {
    FijarComida(final Opciones parametros) {
        super(parametros);
    }

    @Override
    protected Point crearComida() {
        return new Point(1, 1);
    }
}
