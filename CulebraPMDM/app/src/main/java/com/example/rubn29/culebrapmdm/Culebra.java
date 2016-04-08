package com.example.rubn29.culebrapmdm;


import com.example.rubn29.culebrapmdm.DelegateAdapter;
import com.example.rubn29.culebrapmdm.TouchDelegate;

import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.Random;

/**
 * Created by Rubén 29 on 06/03/2016.
 */

/**
 * Creación de la culebra que es el personaje del juego
 **/
public class Culebra {

    public static class Opciones {
        protected TouchDelegate delegado;
        protected int filas;
        protected int columnas;
        protected int tamanho;
        protected Direccion arriba;

        public Opciones(final TouchDelegate delegado) {
            this.filas = 16;
            this.columnas = 16;
            this.tamanho = 3;
            this.arriba = Direccion.RIGHT;
            this.delegado = delegado;
        }


        public Opciones setFilas(final int filas) {
            this.filas = filas;
            return this;
        }

        public Opciones setColumnas(final int columnas) {
            this.columnas = columnas;
            return this;
        }

        public Opciones setTamanho(final int tamanho) {
            this.tamanho = tamanho;
            return this;
        }

        public Opciones setDirection(final Direccion arriba) {
            this.arriba = arriba;
            return this;
        }

        public Opciones setTouchDelegate(final TouchDelegate delegado) {
            this.delegado = delegado;
            return this;
        }


    }

    private LinkedList<Point> culebra;
    private Point direccion;
    private Point comida;

    private final Opciones parametros;
    private final Random generador;


    public Culebra(final Opciones parametros) {
        this.parametros = parametros;
        this.generador = new Random(new Date().getTime());
        this.culebra = new LinkedList<Point>();
    }

    public Culebra() {
        this(new Opciones(new DelegateAdapter()));
    }

    private Point generador() {
        return new Point(generador.nextInt(parametros.filas), generador.nextInt(parametros.columnas));
    }


    /**
     * Método para la colocación inicial de la culebra
     **/
    public final void start() {
        final int iniciaX = (int) Math.floor(parametros.columnas / 2.0);
        final int iniciaY = (int) Math.floor(parametros.filas / 2.0);

        culebra.clear();

        /**For que se recorre para la colocación inicial**/
        for (int i = 0; i < parametros.tamanho; i++) {
            culebra.add(new Point(iniciaX - i, iniciaY));
        }

        direccion = parametros.arriba.point();
        comida = crearComida();

        parametros.delegado.dibujarComida(comida);
        parametros.delegado.dibujarCulebra(culebra);
    }


    /**
     * Método que indica que si la culebra esta en la misma posicion del objeto, lo cogera
     **/
    private boolean laCulebra(final Point posicion) {
        boolean cogerla = false;
        for (Point s : culebra) {
            if (s.equals(posicion)) {
                cogerla = true;
                break;
            }
        }

        return cogerla;
    }

    /**
     * Metodo que indica que al clicar la culebra se mueva a la direccion indicada
     **/
    private Point movimiento() {
        final Point prin = culebra.get(0);
        return new Point(prin.x + direccion.x, prin.y + direccion.y);
    }

    /**
     * Metodo que indica que la culebra no puede salir del los limites
     **/
    private boolean fueraLimites(final Point posicion) {
        return posicion.x < 0 || posicion.x > parametros.columnas - 1 || posicion.y < 0 || posicion.y > parametros.filas - 1;
    }


    /**
     * Metodo que recoge los bolleans anteriores y a traves de "if" realiza las posibilidades indicadas
     **/
    public void tick() {
        if (culebra.isEmpty()) {
            return;
        }

        final Point nuevaPosi = movimiento();

        if (fueraLimites(nuevaPosi) || laCulebra(nuevaPosi)) {
            parametros.delegado.gameOver(Collections.unmodifiableList(culebra));
        } else {
            culebra.addFirst(nuevaPosi);

            if (comida.equals(culebra.get(0))) {
                parametros.delegado.comerComida(comida);
                comida = crearComida();
            } else {
                culebra.removeLast();
            }
            parametros.delegado.nextTick(Collections.unmodifiableList(culebra));
        }

        parametros.delegado.dibujarCulebra(Collections.unmodifiableList(culebra));
        parametros.delegado.dibujarComida(comida);

    }

    public void mover(final Point posi) {
        if (direccion != null && posi.x != -direccion.x && posi.y != -direccion.y) {
            direccion = posi;
        }
    }

    public void mover(final Direccion dir) {
        mover(dir.point());
    }

    protected Point crearComida() {
        Point comidaPosi;
        do {
            comidaPosi = generador();
        } while (laCulebra(comidaPosi));

        return comidaPosi;
    }

    public Opciones getOpciones() {
        return parametros;
    }

}
