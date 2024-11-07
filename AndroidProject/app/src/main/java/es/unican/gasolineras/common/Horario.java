package es.unican.gasolineras.common;

import java.util.Calendar;

/**
 * Clase que contiene métodos para comprobar si una gasolinera está abierta
 */
public class Horario {

    /*
    * Obtiene los horarios de una gasolinera
    * @param horario el horario en formato String
    *               (ejemplo: "L-D: 06:00-22:00")
    *              (ejemplo: "L-S: 07:00-22:00; D: 08:00-14:00")
    *             (ejemplo: "L-V: 07:00-22:00; S: 08:00-14:00"; D: 08:00-14:00")
    * @return un array con los horarios
    * @throws IllegalArgumentException si el horario es nulo o vacío
    */
    public static String[] obtenerHorario (String horario) throws IllegalArgumentException {
        if (horario == null ) {
            throw new IllegalArgumentException("El horario no puede ser nulo o vacío");
        }
        String[] horarios = horario.split(";");
        return horarios;
    }

    /*
        * Comprueba si el horario de la gasolinera está abierto
        * @param horario el horario en formato String
        *               (ejemplo: "L-D: 06:00-22:00")
        *              (ejemplo: "L-S: 07:00-22:00; D: 08:00-14:00")
        *             (ejemplo: "L-V: 07:00-22:00; S: 08:00-14:00"; D: 08:00-14:00")
        * @return "Abierto" si el horario de la gasolinera está abierto
        * @return "Cerrado" si el horario de la gasolinera está cerrado
        * @throws DataAccessException si el horario no es válido
     */
    public static String estaAbierto(String horario) throws IllegalArgumentException {

        // Caso 24h
        if (horario.contains("L-D: 24H")) {
            return "Abierto 24h";
        }
        if (horario.contains("L-D: 00:00-23:59"))
        {
            return "Abierto 24h";
        }
        if (horario.contains("L: 24H")) {
            if (Tiempo.letraDiaActual().equals("L")) {
                return "Abierto";
            }
            return "Cerrado";
        }
        if (horario.contains("M: 24H")) {
            if (Tiempo.letraDiaActual().equals("M")) {
                return "Abierto";
            }
            return "Cerrado";
        }
        if (horario.contains("X: 24H")) {
            if (Tiempo.letraDiaActual().equals("X")) {
                return "Abierto";
            }
            return "Cerrado";
        }
        if (horario.contains("J: 24H")) {
            if (Tiempo.letraDiaActual().equals("J")) {
                return "Abierto";
            }
            return "Cerrado";
        }
        if (horario.contains("V: 24H")) {
            if (Tiempo.letraDiaActual().equals("V")) {
                return "Abierto";
            }
            return "Cerrado";
        }
        if (horario.contains("S: 24H")) {
            if (Tiempo.letraDiaActual().equals("S")) {
                return "Abierto";
            }
            return "Cerrado";
        }
        if (horario.contains("D: 24H")) {
            if (Tiempo.letraDiaActual().equals("D")) {
                return "Abierto";
            }
            return "Cerrado";
        }
        if (horario.contains("S-D: 24H")) {
            if (Tiempo.letraDiaActual().equals("D") || Tiempo.letraDiaActual().equals("S")) {
                return "Abierto";
            }
            return "Cerrado";
        }

        // Obtener la hora actual
        if (compruebaHorario(horario)) {
            return "Abierto";
        } else {
            return "Cerrado";
        }
    }

    /*
        * Comprueba si el horario de la gasolinera está abierto
        * @param horario el horario en formato String
        * (ejemplo: "L-D: 06:00-22:00")
        * (ejemplo: "L-S: 07:00-22:00; D: 08:00-14:00")
        * (ejemplo: "L-V: 07:00-22:00; S: 08:00-14:00"; D: 08:00-14:00")
        * (ejemplo: "L-D: 24H")
        *
        * @return true si el horario de la gasolinera está abierto
        * @return false si el horario de la gasolinera está cerrado
     */
    public static boolean compruebaHorario (String horario) throws IllegalArgumentException {
        // Separar los horarios por días
        String[] horarios = obtenerHorario(horario);
        if(horario.isEmpty()){
            return false;
        }
        // Si el horario contiene el día actual
        int horaActual = Tiempo.horaActual();
        for (String horario2 : horarios) {
            // Elimino los espacios en blanco
            if (horario2.startsWith(" ")) {
                horario2 = horario2.substring(1);
            }

            if (estaEnFranjaDia(horario2)) {
                // Parseo el horario para obtener la hora de apertura y cierre
                if (horario2.contains("24H")) {
                    return true;
                }
                // Caso de un horario partido en dos partes
                // Ejemplo: "L-S: 07:00-14:00 y 16:00-22:00"
                // Se subdivide en dos partes: "07:00-14:00" y "16:00-22:00"
                // Hay que eliminar espacios en blanco para poder parsear correctamente
                if (horario2.contains("y")) {
                    String [] horariosPartidos = horario2.split("y");
                    // Renombro el hroario 0 como split a partir del espacio
                    horariosPartidos[0] = horariosPartidos[0].split("\\s+")[1];
                    for (String horarioPartido : horariosPartidos) {
                        String [] horaCierreApertura = horarioPartido.split("-");

                        // Elimino el espacio si lo hay del string en horaCierreApertura[0], sino quedaría " 15:00"
                        if (horaCierreApertura[0].startsWith(" ")) {
                            horaCierreApertura[0] = horaCierreApertura[0].substring(1);
                        }
                        int horaApertura = Integer.parseInt(horaCierreApertura[0].split(":")[0]);
                        int horaCierre = Integer.parseInt(horaCierreApertura[1].split(":")[0]);
                        int minutoApertura = Integer.parseInt(horaCierreApertura[0].split(":")[1]);
                        int minutoCierre = Integer.parseInt(horaCierreApertura[1].split(":")[1]);
                        // Compruebo si la hora actual está entre la hora de apertura y cierre
                        if (horaActual > horaApertura && horaActual < horaCierre) {
                            return true;
                        } else if (horaActual == horaApertura && Tiempo.minutoActual() >= minutoApertura) {
                            return true;
                        } else if (horaActual == horaCierre && Tiempo.minutoActual() <= minutoCierre) {
                            return true;
                        }
                    }
                }
                // Caso de un horario normal, separación por espacio
                String horario3 = horario2.split("\\s+")[1];
                String [] horaCierreApertura = horario3.split("-");
                int horaApertura = Integer.parseInt(horaCierreApertura[0].split(":")[0]);
                int horaCierre = Integer.parseInt(horaCierreApertura[1].split(":")[0]);
                int minutoApertura = Integer.parseInt(horaCierreApertura[0].split(":")[1]);
                int minutoCierre = Integer.parseInt(horaCierreApertura[1].split(":")[1]);
                // Compruebo si la hora actual está entre la hora de apertura y cierre
                if (horaActual > horaApertura && horaActual < horaCierre) {
                    return true;
                } else if (horaActual == horaApertura && Tiempo.minutoActual() >= minutoApertura) {
                    return true;
                } else if (horaActual == horaCierre && Tiempo.minutoActual() <= minutoCierre) {
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
    public static boolean estaEnFranjaDia (String horario) throws IllegalArgumentException {
        String letraletraDiaActual = Tiempo.letraDiaActual();
        if (horario == null || horario.isEmpty() ) {
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

                // Si el caso es un valor no valido, no pasa nada
            default:
                break;
        }
        // Compruebo si el dia actual esta en la franja horaria
        Calendar calendar = Calendar.getInstance();
        int letraDiaActual = calendar.get(Calendar.DAY_OF_WEEK);
        return dias[letraDiaActual] == 1;
    }
}
