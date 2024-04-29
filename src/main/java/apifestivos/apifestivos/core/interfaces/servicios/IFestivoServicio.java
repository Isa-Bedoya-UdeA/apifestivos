package apifestivos.apifestivos.core.interfaces.servicios;

import java.util.List;

public interface IFestivoServicio {
    public List<String> listar(int year);

    public String verificar(int year, int month, int day);
}
