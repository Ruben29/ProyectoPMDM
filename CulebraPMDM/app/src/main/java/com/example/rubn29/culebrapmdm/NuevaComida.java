package com.example.rubn29.culebrapmdm;

import com.example.rubn29.culebrapmdm.Culebra;
import com.example.rubn29.culebrapmdm.Point;

/**
 * Created by Rubén 29 on 11/03/2016.
 */
class NuevaComida extends Culebra {

    protected int nuevaX;
    final protected int nuevaY;

    /**
     * Método para colocar el primer alimento suponiendo 16 filas y columnas
     **/
    protected NuevaComida(final Opciones opciones) {
        super(opciones);
        this.nuevaX = 9;
        this.nuevaY = 8;
    }

    @Override
    protected Point crearComida() {
        /**Siempre hacia la derecha**/
        return new Point(nuevaX++, nuevaY);
    }
}
