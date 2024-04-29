package apifestivos.apifestivos.aplicacion;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import apifestivos.apifestivos.core.entidades.Festivo;
import apifestivos.apifestivos.core.interfaces.repositorios.IFestivoRepositorio;
import apifestivos.apifestivos.core.interfaces.servicios.IFestivoServicio;

@Service
public class FestivoServicio implements IFestivoServicio{
    private IFestivoRepositorio repositorio;

    public FestivoServicio(IFestivoRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    // Funciones auxiliares

    public static Date agregarDias(Date date, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, days);
        return calendar.getTime();
    }

    public static Date siguienteLunes(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        if (calendar.get(Calendar.DAY_OF_WEEK) > Calendar.MONDAY) {
            date = agregarDias(date, 9 - calendar.get(Calendar.DAY_OF_WEEK));
        } else {
            date = agregarDias(date, 1);
        }
        return date;
    }

    public static int dayofWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }
    
    private Date calcularFechaBasadaEnPascua(int year, int diasPascua) {
        Calendar calendar = Calendar.getInstance();
        // Domingo de pascua
        int a = year % 19;
        int b = year % 4;
        int c = year % 7;
        int d = (19 * a + 24) % 30;
        int days = d + (2 * b + 4 * c + 6 * d + 5) % 7;

        int day = 22 + days;
        int month = 3;

        day = day + diasPascua; // Jueves, viernes santo, ascensión del Señor, Corpus Christ, Sagrado Corazón de Jesús
        calendar.set(year, month - 1, day); // para asegurar que Calendar.DAY_OF_MONTH corresponda al mes actual
        while (day > calendar.getActualMaximum(Calendar.DAY_OF_MONTH)) {
            day = day - calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
            month = month + 1;
            calendar.set(year, month - 1, day); // actualizar el objeto Calendar con el nuevo mes
        }

        return calendar.getTime();
    }

    public static Date festivosFijos(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, day);
        return calendar.getTime();
    }

    @Override
    public List<String> listar(int year) {
        List<Festivo> festivos = repositorio.findAll();
        List<String> festividades = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        for (Festivo festivo : festivos) {
            int tipo = festivo.getIdTipo();

            switch (tipo) {
                case 1: // Fijo
                    date = festivosFijos(year, festivo.getMes(), festivo.getDia());
                    break;

                case 2: // Ley Puente Festivo
                    date = festivosFijos(year, festivo.getMes(), festivo.getDia());
                    if (dayofWeek(date) != 2) { // si la fecha no cae lunes
                        date = siguienteLunes(date);
                    }
                    break;

                case 3: // Basado en Pascua
                    date = calcularFechaBasadaEnPascua(year, festivo.getDiasPascua());
                    break;

                case 4: // Basado en Pascua y Ley Puente Festivo
                    date = calcularFechaBasadaEnPascua(year, festivo.getDiasPascua());

                    if (dayofWeek(date) != 2) { // si la fecha no cae lunes
                        date = siguienteLunes(date);
                    }
                    break;

                default:
                    break;
            }

            festividades.add(sdf.format(date) + " - " + festivo.getNombre());
        }
        return festividades;
    }

    @Override
    public String verificar(int year, int month, int day) {
        List<String> festivos = listar(year);
        String fechaABuscar = String.format("%02d/%02d/%04d", day, month, year);

        for (String festivo : festivos) {
            System.out.println(festivo);
            if (festivo.startsWith(fechaABuscar)) {
                return "Es festivo";
            }
        }

        return "No es festivo";
    }
}
