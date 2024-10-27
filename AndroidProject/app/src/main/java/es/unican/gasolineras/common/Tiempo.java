package es.unican.gasolineras.common;


import java.util.Calendar;


public class Tiempo {

    /*
    * Obtiene la hora actual
    * @return la hora actual
    * @throws DataAccessException si la hora actual no es válida
    */
    public static int horaActual()   {
        // Obtener la hora actual
        Calendar calendar = Calendar.getInstance();
        int horaActual = calendar.get(Calendar.HOUR_OF_DAY);

        return horaActual;
    }

    /*
     * Obtiene el minuto actual
     * @return el minuto actual
     * @throws DataAccessException si el minuto actual no es válido
     */
    public static int minutoActual()   {
        // Obtener el minuto actual
        Calendar calendar = Calendar.getInstance();
        int minutoActual = calendar.get(Calendar.MINUTE);

        return minutoActual;
    }


    /*
     * Obtiene la letra del día actual
     * @return la letra del día actual
     * @throws DataAccessException si el día actual no es válido
     */
    public static String letraDiaActual ()  {
        // Obtener el día actual
        Calendar calendar = Calendar.getInstance();
        int letraDiaActual = calendar.get(Calendar.DAY_OF_WEEK);

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
}

