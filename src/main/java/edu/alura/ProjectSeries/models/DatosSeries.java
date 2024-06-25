package edu.alura.ProjectSeries.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosSeries(
        @JsonAlias("Title")String titulo,
        @JsonAlias("totalSeasons")Integer totalTemporadas,
        @JsonAlias("imdbRating")String evaluacion,
        @JsonAlias("Genre")String genero,
        @JsonAlias("Awards")String premios,
        @JsonAlias("Plot")String sinopsis,
        @JsonAlias("Poster") String poster) {

}
