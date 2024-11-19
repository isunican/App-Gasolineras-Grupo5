package es.unican.gasolineras.common;

import static es.unican.gasolineras.common.Horario.estaAbierto;

import java.util.ArrayList;
import java.util.List;

import es.unican.gasolineras.model.Gasolinera;

public class Generador {


    private static final String PENACASTILLO = "Peñacastillo";
    private static final String CANTABRIA = "Cantabria";
    private static final String HORARIO24H = "L-D: 24H";
    private static final String REPSOL = "Repsol";
    private static final String CARREFOUR = "Carrefour";
    private static final String SHELL = "Shell";
    private static final String PETRONOR = "Petronor";

    /**
     * Genera una lista de gasolineras.
     */
    public static List<Gasolinera> generarGasolineras() {
        List<Gasolinera> gasolineras = new ArrayList<>();

        int horaActual = Tiempo.horaActual();
        int minutoActual = Tiempo.minutoActual();


        String horarioCerrada1 = generarHorarioCerrado(horaActual, minutoActual);
        String horarioCerrada2 = generarHorarioCerrado(horaActual, minutoActual);

        gasolineras.add(crearGasolinera("1", REPSOL, "28001", "Calle A", PENACASTILLO, CANTABRIA, HORARIO24H, 1.35, 1.40));
        gasolineras.add(crearGasolinera("2", CARREFOUR, "28002", "Calle B", PENACASTILLO, CANTABRIA, HORARIO24H, 1.30, 1.38));
        gasolineras.add(crearGasolinera("3", SHELL, "28003", "Calle C", PENACASTILLO, CANTABRIA, horarioCerrada1, 1.32, 1.37));
        gasolineras.add(crearGasolinera("4", PETRONOR, "28004", "Calle D", PENACASTILLO, CANTABRIA, horarioCerrada2, 1.28, 1.36));

        return gasolineras;
    }

    /**
     * Genera una lista de gasolineras con companhias
     */
    public static List<Gasolinera> generarGasolinerasCompanhia(){
        List<Gasolinera> gasolineras = new ArrayList<>();
        gasolineras.add(crearGasolinera("Repsol1", "Repsol1", "29001", "Calle D", "Santander", CANTABRIA, HORARIO24H, 1.35, 1.40));
        gasolineras.add(crearGasolinera("Repsol2", "Repsol2", "29002", "Calle E", "Santander", CANTABRIA, HORARIO24H, 1.30, 1.38));
        gasolineras.add(crearGasolinera("Cepsa", "Cepsa", "29004", "Calle F", "Las Rozas", "Madrid", HORARIO24H, 1.28, 1.36));
        gasolineras.add(crearGasolinera(CARREFOUR, CARREFOUR, "29003", "Calle G", "Sestao", "Bilbao", HORARIO24H, 1.32, 1.37));
        gasolineras.add(crearGasolinera("Gasofa", "Otros", "29003", "Calle H", "Maliaño", CANTABRIA, HORARIO24H, 1.32, 1.37));
        gasolineras.add(crearGasolinera("SinRotulo", "", "29004", "Calle I", "Oviedo", "Asturias", HORARIO24H, 1.28, 1.36));
        return gasolineras;
    }

    /**
     * Genera una lista de gasolineras , algunas de ellas con datos vacios.
     */
    public static List<Gasolinera> generarGasolinerasDatosVacios() {
        List<Gasolinera> gasolineras = new ArrayList<>();


        int horaActual = Tiempo.horaActual();
        int minutoActual = Tiempo.minutoActual();


        String horarioCerrada1 = generarHorarioCerrado(horaActual, minutoActual);
        String horarioCerrada2 = generarHorarioCerrado(horaActual, minutoActual);


        gasolineras.add(crearGasolinera("1", REPSOL, "30001", "Calle J", PENACASTILLO, CANTABRIA, HORARIO24H, 1.35, 1.40));
        gasolineras.add(crearGasolinera("2", CARREFOUR, "30002", "Calle K", PENACASTILLO, CANTABRIA, HORARIO24H, 1.30, 1.38));
        gasolineras.add(crearGasolinera("3", SHELL, "30003", "Calle L", PENACASTILLO, "", horarioCerrada1, 1.32, 1.37));
        gasolineras.add(crearGasolinera("4", PETRONOR, "30004", "Calle M", PENACASTILLO, CANTABRIA, horarioCerrada2, 1.28, 1.36));
        gasolineras.add(crearGasolinera("5", "BP", "30004", "Calle N", PENACASTILLO, CANTABRIA, "", 1.28, 1.36));

        return gasolineras;
    }

    /**
     * Genera una lista de gasolineras , con los horarios cerrados.
     */
    public static List<Gasolinera> generarGasolinerasCerradas() {
        List<Gasolinera> gasolineras = new ArrayList<>();


        int horaActual = Tiempo.horaActual();
        int minutoActual = Tiempo.minutoActual();

        String horarioCerrada1 = generarHorarioCerrado(horaActual, minutoActual);
        String horarioCerrada2 = generarHorarioCerrado(horaActual, minutoActual);
        String horarioCerrada3 = generarHorarioCerrado(horaActual, minutoActual);
        String horarioCerrada4 = generarHorarioCerrado(horaActual, minutoActual);


        gasolineras.add(crearGasolinera("1", REPSOL, "31001", "Calle O", PENACASTILLO, CANTABRIA, horarioCerrada3, 1.35, 1.40));
        gasolineras.add(crearGasolinera("2", CARREFOUR, "31002", "Calle P", PENACASTILLO, CANTABRIA, horarioCerrada4, 1.30, 1.38));
        gasolineras.add(crearGasolinera("3", SHELL, "31003", "Calle Q", PENACASTILLO, CANTABRIA, horarioCerrada1, 1.32, 1.37));
        gasolineras.add(crearGasolinera("4", PETRONOR, "31004", "Calle R", PENACASTILLO, CANTABRIA, horarioCerrada2, 1.28, 1.36));

        return gasolineras;
    }

    /**
     * Genera un horario cerrado a partir de la hora y minuto actuales
     *
     * @param horaActual la hora actual del dispositivo.
     * @param minutoActual el minuto actual del dispositivo.
     */
    private static String generarHorarioCerrado(int horaActual, int minutoActual) {

        int horaCierre = (horaActual - 1 + 24) % 24; // Hora de cierre es una hora antes de la actual
        int horaApertura = (horaActual + 2) % 24;    // Hora de apertura es dos horas después de la actual

        String horario = "L-D: " + formatoHora(horaApertura, minutoActual) + "-" + formatoHora(horaCierre, minutoActual);

        return horario;
    }

    /**
     * Pone en el formato correcto la hora y minutos pasados.
     *
     * @param hora la hora actual del dispositivo.
     * @param minuto el minuto actual del dispositivo.
     */
    private static String formatoHora(int hora, int minuto) {
        return String.format("%02d:%02d", hora % 24, minuto);  // Ajusta para mantener la hora en el rango de 0-23
    }

    /**
     * Crea una gasolinera.
     *
     * @param id id de la gasolinera.
     * @param rotulo rótulo de la gasolinera.
     * @param cp  código postal de la gasolinera
     * @param direccion dirección de la gasolinera.
     * @param municipio municipio de la gasolinera.
     * @param localidad localidad de la gasolinera.
     * @param horario horario de la gasolinera.
     * @param gasoleoA precio del gasoleo en la gasolinera.
     * @param gasolina95E5 precio del 95 en la gasolinera.
     */

    private static Gasolinera crearGasolinera(String id, String rotulo, String cp, String direccion, String municipio, String localidad, String horario, double gasoleoA, double gasolina95E5) {
        Gasolinera gasolinera = new Gasolinera();
        gasolinera.setId(id);
        gasolinera.setRotulo(rotulo);
        gasolinera.setCp(cp);
        gasolinera.setDireccion(direccion);
        gasolinera.setMunicipio(municipio);
        gasolinera.setHorario(horario);
        gasolinera.setGasoleoA(gasoleoA);
        gasolinera.setGasolina95E5(gasolina95E5);
        return gasolinera;
    }
}
