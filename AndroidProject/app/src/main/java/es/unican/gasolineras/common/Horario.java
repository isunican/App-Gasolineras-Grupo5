package es.unican.gasolineras.common;

import java.util.Calendar;
import es.unican.gasolineras.common.DataAccessException;
public class Horario {
    /**
     * Parsea el horario obtenido de la API en formato String
     * @param horario el horario en formato String
     *                (ejemplo: "L-D: 06:00-22:00")
     *                (ejemplo: "L-S: 07:00-22:00; D: 08:00-14:00")
     *                (ejemplo: "L-V: 07:00-22:00; S: 08:00-14:00"; D: 08:00-14:00")
     *
     */

    public static String[] obtenerHorario (String horario) throws IllegalArgumentException {
        if (horario == null || horario.isEmpty()) {
            throw new IllegalArgumentException("El horario no puede ser nulo o vacío");
        }
        String[] horarios = horario.split(";");
        return horarios;
    }
    public static String letraDiaActual () throws DataAccessException {
        // Obtener el día actual
        Calendar calendar = Calendar.getInstance();
        int letraDiaActual = calendar.get(Calendar.DAY_OF_WEEK);
        if (letraDiaActual < 1 || letraDiaActual > 7) {
            throw new DataAccessException("El día actual no es válido");
        }

        String letraletraDiaActual = "";
        switch (letraDiaActual) {
            case Calendar.MONDAY:
                letraletraDiaActual = "L";
                break;
            case Calendar.TUESDAY:
                letraletraDiaActual = "M";
                break;
            case Calendar.WEDNESDAY:
                letraletraDiaActual = "X";
                break;
            case Calendar.THURSDAY:
                letraletraDiaActual = "J";
                break;
            case Calendar.FRIDAY:
                letraletraDiaActual = "V";
                break;
            case Calendar.SATURDAY:
                letraletraDiaActual = "S";
                break;
            case Calendar.SUNDAY:
                letraletraDiaActual = "D";
                break;
        }
        return letraletraDiaActual;
    }

    public static int horaActual() throws DataAccessException {
        // Obtener la hora actual
        Calendar calendar = Calendar.getInstance();
        int horaActual = calendar.get(Calendar.HOUR_OF_DAY);
        if (horaActual < 0 || horaActual > 23) {
            throw new DataAccessException("La hora actual no es válida");
        }
        return horaActual;
    }
    public static int minutoActual() throws DataAccessException {
        // Obtener el minuto actual
        Calendar calendar = Calendar.getInstance();
        int minutoActual = calendar.get(Calendar.MINUTE);
        if (minutoActual < 0 || minutoActual > 59) {
            throw new DataAccessException("El minuto actual no es válido");
        }
        return minutoActual;
    }

    public static String estaAbierto(String horario) throws DataAccessException,IllegalArgumentException {

        // Caso 24h
        if (horario.contains("L-D: 24H")) {
            return "Abierto 24h";
        }
        // Obtener la letra del día actual
        String letraletraDiaActual = letraDiaActual();
        // Obtener la hora actual
        int horaActual = horaActual();
        if (compruebaHorario(horario, letraletraDiaActual, horaActual)) {
            return "Abierto";
        } else {
            return "Cerrado";
        }
    }

    /*
        * Comprueba si el horario de la gasolinera está abierto
        * @param horario el horario en formato String
        *              (ejemplo: "L-D: 06:00-22:00")
        *            (ejemplo: "L-S: 07:00-22:00; D: 08:00-14:00")
        *          (ejemplo: "L-V: 07:00-22:00; S: 08:00-14:00"; D: 08:00-14:00")
        * @return true si el horario de la gasolinera está abierto
        * @return false si el horario de la gasolinera está cerrado
     */
    public static boolean compruebaHorario (String horario, String letraletraDiaActual, int horaActual) throws DataAccessException,IllegalArgumentException {
        // Separar los horarios por días
        String[] horarios = obtenerHorario(horario);
        // Si el horario contiene el día actual
        for (String horario2 : horarios) {
            if (estaEnFranjaDia(letraletraDiaActual, horario2)) {
                // Parseo el horario para obtener la hora de apertura y cierre
                String horario3 = horario2.split("\\s+")[1];
                String [] horaCierreApertura = horario3.split("-");
                int horaApertura = Integer.parseInt(horaCierreApertura[0].split(":")[0]);
                int horaCierre = Integer.parseInt(horaCierreApertura[1].split(":")[0]);
                int minutoApertura = Integer.parseInt(horaCierreApertura[0].split(":")[1]);
                int minutoCierre = Integer.parseInt(horaCierreApertura[1].split(":")[1]);
                // Compruebo si la hora actual está entre la hora de apertura y cierre
                if (horaActual > horaApertura && horaActual < horaCierre) {
                    return true;
                } else if (horaActual == horaApertura && minutoActual() >= minutoApertura) {
                    return true;
                } else if (horaActual == horaCierre && minutoActual() <= minutoCierre) {
                    return true;
                }
            }
        }
        return false;
    }

    /*
        * Comprueba si el dia actual esta en la franja horaria
        * @param letraletraDiaActual la letra del dia actual
        * @param horario el horario en formato String
        *               (ejemplo: "L-D: 06:00-22:00")
        *              (ejemplo: "L-S: 07:00-22:00; D: 08:00-14:00")
        *             (ejemplo: "L-V: 07:00-22:00; S: 08:00-14:00"; D: 08:00-14:00")
        * @return true si el dia actual esta en la franja horaria
        * @throws DataAccessException si el dia actual no es válido
     */
    public static boolean estaEnFranjaDia (String letraletraDiaActual, String horario) throws IllegalArgumentException {
        if (horario == null || horario.isEmpty() || letraletraDiaActual == null || letraletraDiaActual.isEmpty()) {
            throw new IllegalArgumentException("El horario o la letra del día actual no puede ser nulo o vacío");
        }
        // array de 7 posiciones, una por cada dia de la semana a cero inicialmente
        int [] dias = new int[7];
        // Del string de horario existen dos casos, que sea un solo dia, es decir, una sola letra
        // o que sea un rango de dias, es decir, una letra seguida de un guion y otra letra
        // primero el caso de una sola letra
        if (horario.contains(letraletraDiaActual)) {
            return true;
        }
        // caso de rango de dias
        // debo separar los dias por el guion
        String[] diasIniFin = horario.split("-");
        // Elimino los espacios en blanco
        diasIniFin[0] = diasIniFin[0].trim();
        diasIniFin[1] = diasIniFin[1].split(":")[0].trim();
        // Cojo el primer valor de dias [0] y lo comparo con los dias de la semana
        // si coincide con alguno, guardo su posicion en el array dias estableciendo su valor a 1
        switch (diasIniFin[0]) {
            case "L":
                dias[0] = 1;
                break;
            case "M":
                dias[1] = 1;
                break;
            case "X":
                dias[2] = 1;
                break;
            case "J":
                dias[3] = 1;
                break;
            case "V":
                dias[4] = 1;
                break;
            case "S":
                dias[5] = 1;
                break;
            case "D":
                dias[6] = 1;
                break;
        }
        // Cojo el segundo valor de dias [1] y lo comparo con los dias de la semana, marco a 1 ese dia y todos los anteriories
        switch (diasIniFin[1]) {
            case "L":
                for (int i = 0; i < 1; i++) {
                    dias[i] = 1;
                }
                break;
            case "M":
                for (int i = 0; i < 2; i++) {
                    dias[i] = 1;
                }
                break;
            case "X":
                for (int i = 0; i < 3; i++) {
                    dias[i] = 1;
                }
                break;
            case "J":
                for (int i = 0; i < 4; i++) {
                    dias[i] = 1;
                }
                break;
            case "V":
                for (int i = 0; i < 5; i++) {
                    dias[i] = 1;
                }
                break;
            case "S":
                for (int i = 0; i < 6; i++) {
                    dias[i] = 1;
                }
                break;
            case "D":
                for (int i = 0; i < 7; i++) {
                    dias[i] = 1;
                }
                break;
        }
        // Compruebo si el dia actual esta en la franja horaria
        Calendar calendar = Calendar.getInstance();
        int letraDiaActual = calendar.get(Calendar.DAY_OF_WEEK);
        return dias[letraDiaActual] == 1;
    }

}
