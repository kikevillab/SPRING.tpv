package batch;

public class CronConstans {
    /*
     * La expresión de Cron está representada por seis campos:
     * 
     * Segundo, Minuto, Hora, Día del Mes, Mes, Día (s) de la semana (*) Significa que coincide con cualquier valor / X significa "cada X" ?
     * ("Sin valor específico")
     */

    public static final String EJECUCION_CADA_MINUTO = "0 * * * * *";

    public static final String EJECUCION_A_MEDIA_NOCHE = "0 0 0 * * *";

}
