package apifestivos.apifestivos.presentacion;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import apifestivos.apifestivos.core.interfaces.servicios.IFestivoServicio;

@RestController
@RequestMapping("/api/festivos")
public class FestivoControlador {
    private IFestivoServicio servicio;

    public FestivoControlador(IFestivoServicio servicio) {
        this.servicio = servicio;
    }

    @RequestMapping(value = "/listar/{year}", method = RequestMethod.GET)
    public List<String> listar(@PathVariable int year) {
        return servicio.listar(year);
    }

    @RequestMapping(value = "/verificar/{year}/{month}/{day}", method = RequestMethod.GET)
    public String verificar(@PathVariable String year, @PathVariable String month, @PathVariable String day) {
        try { // intenta convertir los datos a enteros y verifica que la fecha sea válida
            int intYear = Integer.parseInt(year);
            int intMonth = Integer.parseInt(month);
            int intDay = Integer.parseInt(day);
            LocalDate.of(intYear, intMonth, intDay);
            return servicio.verificar(intYear, intMonth, intDay);
        } catch (NumberFormatException e) { // si hay error al tratar de convertir una cadena no numérica a numero
            return "Error: Los valores de año, mes y día deben ser números enteros.";
        } catch (DateTimeException e) { // si la fecha no es válida
            return "Fecha Invalida";
        }
    }
}
