package edu.alura.ProjectSeries.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosEpisode(
                @JsonAlias("Title") String titulo,
                @JsonAlias("Episode") Integer numEp,
                @JsonAlias("imdbRating") String evaluacion,
                @JsonAlias("Released") String fechaLanz) {

}
