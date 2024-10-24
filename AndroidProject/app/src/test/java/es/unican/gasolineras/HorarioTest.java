package es.unican.gasolineras;

import static org.junit.Assert.assertTrue;

import org.junit.After;

import org.junit.Test;

import org.mockito.MockedStatic;

import es.unican.gasolineras.common.Horario;
import es.unican.gasolineras.common.Tiempo;

import static org.junit.Assert.*;


import org.junit.Before;
import org.mockito.Mockito;


public class HorarioTest {

    private MockedStatic<Tiempo> tiempoMock;

    @Before
    public void setup() {
        // Initialize the static mock for Tiempo before each test
        tiempoMock = Mockito.mockStatic(Tiempo.class);
    }
    @After
    public void tearDown() {
        // Deregister the static mock to avoid conflicts in other tests
        if (tiempoMock != null) {
            tiempoMock.close();
        }
    }



    @Test
    public void testUD1a() {
        // Simular los valores actuales del tiempo
        tiempoMock.when(Tiempo::horaActual).thenReturn(8);
        tiempoMock.when(Tiempo::minutoActual).thenReturn(0);
        tiempoMock.when(Tiempo::letraDiaActual).thenReturn("L");

        // Llamar al método con el horario dado
        boolean resultado = Horario.compruebaHorario("L-S: 07:00-22:30 ; D: 08:00-14:00");

        // Comprobar el resultado esperado
        assertTrue(resultado);
    }

    @Test
    public void testUD1b() {
        tiempoMock.when(Tiempo::horaActual).thenReturn(7);
        tiempoMock.when(Tiempo::minutoActual).thenReturn(1);
        tiempoMock.when(Tiempo::letraDiaActual).thenReturn("L");

        boolean resultado = Horario.compruebaHorario("L-S: 07:00-22:30 ; D: 08:00-14:00");

        assertTrue(resultado);
    }

    @Test
    public void testUD1c() {
        tiempoMock.when(Tiempo::horaActual).thenReturn(22);
        tiempoMock.when(Tiempo::minutoActual).thenReturn(10);
        tiempoMock.when(Tiempo::letraDiaActual).thenReturn("L");

        boolean resultado = Horario.compruebaHorario("L-S: 07:00-22:30 ; D: 08:00-14:00");

        assertTrue(resultado);
    }

    @Test
    public void testUD1d() {
        tiempoMock.when(Tiempo::horaActual).thenReturn(8);
        tiempoMock.when(Tiempo::minutoActual).thenReturn(0);
        tiempoMock.when(Tiempo::letraDiaActual).thenReturn("L");

        boolean resultado = Horario.compruebaHorario("L-S: 07:00-14:30 y 16:00-22:00");

        assertTrue(resultado);
    }

    @Test
    public void testUD1e() {
        tiempoMock.when(Tiempo::horaActual).thenReturn(7);
        tiempoMock.when(Tiempo::minutoActual).thenReturn(1);
        tiempoMock.when(Tiempo::letraDiaActual).thenReturn("L");

        boolean resultado = Horario.compruebaHorario("L-S: 07:00-14:30 y 16:00-22:00");

        assertTrue(resultado);
    }

    @Test
    public void testUD1f() {
        tiempoMock.when(Tiempo::horaActual).thenReturn(14);
        tiempoMock.when(Tiempo::minutoActual).thenReturn(10);
        tiempoMock.when(Tiempo::letraDiaActual).thenReturn("L");

        boolean resultado = Horario.compruebaHorario("L-S: 07:00-14:30 y 16:00-22:00");

        assertTrue(resultado);
    }

    @Test
    public void testUD1g() {
        tiempoMock.when(Tiempo::horaActual).thenReturn(8);
        tiempoMock.when(Tiempo::minutoActual).thenReturn(0);
        tiempoMock.when(Tiempo::letraDiaActual).thenReturn("L");

        boolean resultado = Horario.compruebaHorario("L-D: 24H");

        assertTrue(resultado);
    }

    @Test
    public void testUD1h() {
        tiempoMock.when(Tiempo::horaActual).thenReturn(6);
        tiempoMock.when(Tiempo::minutoActual).thenReturn(0);
        tiempoMock.when(Tiempo::letraDiaActual).thenReturn("L");

        boolean resultado = Horario.compruebaHorario("L-S: 07:00-22:00 ; D: 08:00-14:00");

        assertFalse(resultado);
    }



    @Test
    public void testUD1i() {
        tiempoMock.when(Tiempo::horaActual).thenReturn(6);
        tiempoMock.when(Tiempo::minutoActual).thenReturn(0);
        tiempoMock.when(Tiempo::letraDiaActual).thenReturn("L");

        boolean resultado = Horario.compruebaHorario("");

        assertFalse(resultado);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUD1j() {
        Horario.compruebaHorario(null);
    }
}
