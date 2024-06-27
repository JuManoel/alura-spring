package edu.alura.ProjectSeries.dto;

import edu.alura.ProjectSeries.models.Categorias;

public record SerieDTO(
        int id,
        String titulo,
        int totalTemporadas,
        double evaluacion,
        Categorias genero,
        String sinopsis,
        String poster,
        String premios) {

}
