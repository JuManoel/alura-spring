package edu.alura.ProjectSeries.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosTemporadas(
                @JsonAlias("Season") Integer num,
                @JsonAlias("Episodes") List<DatosEpisode> episodes) {

}
