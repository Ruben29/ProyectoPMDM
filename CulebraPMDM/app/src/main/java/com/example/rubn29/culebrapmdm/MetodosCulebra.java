package com.example.rubn29.culebrapmdm;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;

//import com.example.rubn29.culebrapmdm.definir.Culebra;
//import com.example.rubn29.culebrapmdm.definir.Direccion;
//import com.example.rubn29.culebrapmdm.definir.Point;

import java.util.List;

import com.example.rubn29.culebrapmdm.*;

/**
 * Created by Rubén 29 on 09/03/2016.
 */

/**
 * Clase para la definicion de los metodos de la culebra
 **/
public class MetodosCulebra extends View implements TouchDelegate {

    private final Culebra definir;
    private final transient RefreshHandler refrescarHandler = new RefreshHandler();
    private List<Point> culebra;

    private int topX;
    private int topY;
    private int blockArea;

    private int bottomX;
    private int bottomY;
    private int blockdir;

    private Point comida;
    private boolean dead;

    /**
     * Metodo para la "actualizacion" de la culebra. Cuando muera será colocada en la posicion inicial
     **/
    class RefreshHandler extends Handler {
        @Override
        public void handleMessage(final Message msg) {
            MetodosCulebra.this.invalidate();
            if (dead) {
                postDelayed(new Runnable() {
                    public void run() {
                        definir.start();
                        dead = false;
                        MetodosCulebra.this.update();
                    }
                }, 500);
            } else {
                definir.tick();
                MetodosCulebra.this.update();
            }

        }

        /**
         * Metodo para la proxima definicion de algunos movimientos de la culebra
         **/
        public void sleep(final long distancia) {
            this.removeMessages(0);
            sendMessageDelayed(obtainMessage(0), distancia);
        }

    }

    public MetodosCulebra(final Context context) {
        super(context);
        setFocusable(true);

        this.dead = false;
        this.definir = new Culebra(new Culebra.Opciones(this));
        this.definir.start();
        this.definir.tick();
    }

    /**
     * Definición del tamaño del tablero de juego
     **/
    @Override
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);

        topX = (getWidth() - 512) / 2;
        topY = topX;
        blockArea = (getWidth() - topX - topY) / 16;

        final Paint pintar = new Paint();
        pintar.setStyle(Paint.Style.FILL);

        drawBgDroid(canvas, pintar);
        drawCulebraDroid(canvas, pintar);
        drawComidaDroid(canvas, pintar);

    }

    /**
     * Definicion del rango de coordenadas en la pantalla para cada movimiento
     **/
    public boolean onTouchEvent(MotionEvent event) {

        // int fil = (int) (event.getY() / (getMeasuredHeight()));
        //int col = (int) (event.getX() / (getMeasuredWidth()));

        int fil = (int) (event.getY());
        int col = (int) (event.getX());

        int resultado = fil + col;

        System.out.println(resultado);

        if (resultado > 810 && resultado < 1010) {
            System.out.println("Izq");
            definir.mover(Direccion.LEFT);
        } else if (resultado > 1000 && resultado < 1130) {
            System.out.println("arriba");
            definir.mover(Direccion.UP);
        } else if (resultado > 1300 && resultado < 1500) {
            System.out.println("der");
            definir.mover(Direccion.RIGHT);
        } else if (resultado > 1200 && resultado < 1350) {
            System.out.println("abajo");
            definir.mover(Direccion.DOWN);
        }

        this.invalidate();

        return true;

    }

    /**
     * Definición de los cuadros de movimiento
     **/
    private void drawBgDroid(final Canvas canvas, final Paint pintar) {
        pintar.setColor(Color.BLACK);
        canvas.drawPaint(pintar);
        pintar.setColor(Color.WHITE);
        canvas.drawRect(topX, topY, topX + blockArea * 16, topY + blockArea
                * 16, pintar);


        //izq,arri,der,aba
        /**cuadrado arriba**/
        bottomX = 200;
        bottomY = 200;
        blockdir = 5;
        pintar.setColor(Color.BLUE);
        canvas.drawRect(260, 650, 460, 750, pintar);

        /**cuadrado abajo**/
        bottomX = 200;
        bottomY = 200;
        blockdir = 5;
        pintar.setColor(Color.RED);
        canvas.drawRect(260, 850, 460, 950, pintar);

        /**cuadrado izquierda**/
        bottomX = 200;
        bottomY = 200;
        blockdir = 5;
        pintar.setColor(Color.YELLOW);
        canvas.drawRect(40, 750, 240, 850, pintar);

        /**cuadrado derecha**/
        bottomX = 200;
        bottomY = 200;
        blockdir = 5;
        pintar.setColor(Color.GREEN);
        canvas.drawRect(480, 750, 680, 850, pintar);


    }

    /**
     * Definición de la culebra y de sus colores, negro en inicial y rojo al morir
     **/
    private void drawCulebraDroid(final Canvas canvas, final Paint pintar) {
        if (culebra == null) {
            return;
        }

        pintar.setColor(dead ? Color.RED : Color.BLACK);

        for (Point p : culebra) {
            canvas.drawRect(topX + p.getX() * blockArea, topY + p.getY()
                            * blockArea, topX + (p.getX() + 1) * blockArea,
                    topY + (p.getY() + 1) * blockArea, pintar);
        }
    }

    /**
     * Definición de la comida y de sus colores, verde en inicial y rojo al morir
     **/
    private void drawComidaDroid(final Canvas canvas, final Paint pintar) {
        if (comida == null) {

            return;
        }

        pintar.setColor(dead ? Color.RED : Color.GREEN);
        canvas.drawCircle(topX + comida.getX() * blockArea + blockArea / 2, topY
                + comida.getY() * blockArea + blockArea / 2, blockArea / 2, pintar);
    }

    /**
     * Velocidad definida de la culebra
     **/
    public void update() {
        refrescarHandler.sleep(350 - culebra.size() * 5);
    }

    /**
     * Metodo que dibuja sin necesidad de imagenes la culebra
     **/
    public void dibujarCulebra(final List<Point> culebra) {
    }

    /**
     * Metodo que dibuja sin necesidad de imagenes la comida
     **/
    public void dibujarComida(final Point comida) {
        this.comida = comida;
    }

    /**
     * Método que detiene la culebra al morir
     **/
    public void gameOver(final List<Point> culebra) {
        this.culebra = culebra;
        dead = true;
        refrescarHandler.sleep(0);
    }

    public void comerComida(final Point comida) {
    }

    /**
     * Método que recoloca la culebra
     **/
    public void nextTick(final List<Point> culebra) {
        this.culebra = culebra;
        update();
    }


}