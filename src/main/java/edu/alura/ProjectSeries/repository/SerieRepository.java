package edu.alura.ProjectSeries.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import edu.alura.ProjectSeries.models.Categorias;
import edu.alura.ProjectSeries.models.Episode;
import edu.alura.ProjectSeries.models.Serie;

public interface SerieRepository extends JpaRepository<Serie, Integer> {
    Optional<Serie> findByTituloContainsIgnoreCase(String nombre);

    List<Serie> findTop5ByOrderByEvaluacionDesc();

    List<Serie> findByGenero(Categorias categorias);

    @Query("SELECT s FROM Serie s WHERE s.totalTemporadas <= :totalTemp AND  s.evaluacion >= :eval")
    Optional<List<Serie>> buscarSerieTempEval(int totalTemp, double eval);

    @Query("SELECT e FROM Serie s JOIN s.episodes e WHERE e.titulo ILIKE %:nombre%")
    Optional<List<Episode>> buscarEpPorNombre(String nombre);

    @Query("SELECT e FROM Serie s JOIN s.episodes e WHERE s=:serie ORDER BY e.evaluacion DESC LIMIT 5")
    List<Episode> top5Episodes(Serie serie);
}
