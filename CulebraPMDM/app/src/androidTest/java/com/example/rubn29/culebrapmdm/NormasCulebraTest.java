package com.example.rubn29.culebrapmdm;

import com.example.rubn29.culebrapmdm.Culebra;
import com.example.rubn29.culebrapmdm.Direccion;
import com.example.rubn29.culebrapmdm.Point;

import junit.framework.TestCase;

import org.junit.Test;


import java.util.List;

import static org.junit.Assert.fail;

/**
 * Created by Rubén 29 on 22/03/2016.
 */
public class NormasCulebraTest extends TestCase {

    static final class muerteCulebra extends RuntimeException {
    }

    static final class comerCulebra extends RuntimeException {
    }


    static final class hambrientaCulebra extends RuntimeException {
    }

    static final class llenaCulebra extends RuntimeException {
    }

    /**
     * Regla 1: La serpiente muere al chocar con el muro
     */
    @Test
    public void testMuro() {
        final Culebra definir = new Culebra(new Culebra.Opciones(new DelegateAdapter() {
            public void gameOver(final List<Point> snake) {
                throw new muerteCulebra();
            }
        }).setFilas(16).setColumnas(16));

        definir.start();
        try {
            for (int i = 1; i < 8; i++) {
                definir.tick();
            }
        } catch (muerteCulebra ex) {
            fail("Murió antes de llegar al borde");
        }

        try {
            definir.tick();
            fail("Llegó al borde");
        } catch (muerteCulebra ex) {
        }
    }

    /**
     * Regla 2: la serpiente crece cada vez que come
     */
    @Test
    public void testComida() {
        final Culebra definir = new NuevaComida(new Culebra.Opciones(
                new DelegateAdapter() {
                    int size = 3;

                    public void nextTick(final List<Point> snake) {
                        if (snake.size() != ++size) {
                            throw new comerCulebra();
                        }
                    }
                }).setFilas(16).setColumnas(16).setDirection(Direccion.RIGHT));

        definir.start();
        try {
            for (int i = 1; i < 8; i++) {
                definir.tick();
            }
        } catch (comerCulebra ex) {
            fail("La serpiente no creció");
        }
    }

    /**
     * Regla 3: La serpiente muere si se come a sí misma
     */
    @Test
    public void testHarakiri() {
        final Culebra definir = new FijarComida(new Culebra.Opciones(
                new DelegateAdapter() {
                    public void gameOver(final List<Point> snake) {
                        throw new muerteCulebra();
                    }
                }).setFilas(32).setColumnas(32).setTamanho(4)
                .setDirection(Direccion.RIGHT));

        // Haremos un ciclo
        definir.start();
        try {
            definir.tick();
            definir.mover(Direccion.UP);
            definir.tick();
            definir.mover(Direccion.LEFT);
            definir.tick();
        } catch (muerteCulebra ex) {
            fail("Murió antes de morderse");
        }

        try {
            definir.mover(Direccion.DOWN);
            definir.tick();
            fail("No se mordió");
        } catch (muerteCulebra ex) {
        }
    }


    /**
     * Regla 5: sólo cuando come aparece un nueva comida
     */
    static class CycleFood extends NuevaComida {
        boolean clean;

        CycleFood(final Opciones options) {
            super(options);
            clean = true;
        }

        @Override
        protected Point crearComida() {
            if (!clean) {
                throw new llenaCulebra();
            }

            clean = false;
            return super.crearComida();
        }
    }

    @Test
    public void testFoodCycle() {
        final CycleFood definir = new CycleFood(new Culebra.Opciones(
                new DelegateAdapter()).setFilas(16).setColumnas(16)
                .setDirection(Direccion.RIGHT).setTamanho(3));

        definir.getOpciones().setTouchDelegate(new DelegateAdapter() {
            public void comerComida(final Point comida) {
                definir.clean = true;
            }
        });

        definir.start();
        try {
            for (int i = 1; i < 8; i++) {
                definir.tick();
                if (definir.clean) {
                    throw new hambrientaCulebra();
                }
            }
        } catch (hambrientaCulebra ex) {
            fail("La comida no se repuso");
        } catch (llenaCulebra ex) {
            fail("Hay comida de más");
        }
    }
}