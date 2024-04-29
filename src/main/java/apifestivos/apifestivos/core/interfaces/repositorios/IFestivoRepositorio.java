package apifestivos.apifestivos.core.interfaces.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import apifestivos.apifestivos.core.entidades.Festivo;

@Repository
public interface IFestivoRepositorio extends JpaRepository<Festivo, Integer>{
    @Query("SELECT f FROM Festivo f")
    public List<String> listar(int year);
}
