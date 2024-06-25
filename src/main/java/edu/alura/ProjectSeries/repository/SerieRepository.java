package edu.alura.ProjectSeries.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.alura.ProjectSeries.models.Categorias;
import edu.alura.ProjectSeries.models.Serie;

public interface SerieRepository extends JpaRepository<Serie,Integer>{
    Optional<Serie> findByTituloContainsIgnoreCase(String nombre);
    List<Serie> findTop5ByOrderByEvaluacionDesc();
    List<Serie> findByGenero(Categorias categorias);
    Optional<List<Serie>> findByTotalTemporadasGreaterThanEqualAndEvaluacionGreaterThanEqual(int totalTemp, double eval);
}
