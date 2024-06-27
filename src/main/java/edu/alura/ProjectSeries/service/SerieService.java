package edu.alura.ProjectSeries.service;

import java.util.List;
import java.util.stream.Collectors;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.alura.ProjectSeries.dto.SerieDTO;
import edu.alura.ProjectSeries.models.Serie;
import edu.alura.ProjectSeries.repository.SerieRepository;

@Service
public class SerieService {
    @Autowired
    private SerieRepository serieRepository;

    public List<SerieDTO> mostrarSeries() {

        return converterSerie(serieRepository.findAll());
    }

    public List<SerieDTO> mostrarTop5() {
        return converterSerie(serieRepository.findTop5ByOrderByEvaluacionDesc());
    }

    public List<SerieDTO> lanzamientoRecente() {
        return converterSerie(serieRepository.lanzamentoRecente());
    }

    private List<SerieDTO> converterSerie(List<Serie> serie) {
        return serie.stream()
                .map(s -> new SerieDTO(s.getId(), s.getTitulo(), s.getTotalTemporadas(), s.getEvaluacion(),
                        s.getGenero(),
                        s.getSinopsis(), s.getPoster(), s.getPremios()))
                .collect(Collectors.toList());
    }

    public SerieDTO mostrarSeriePorId(int id) {
        Optional<Serie> serie = serieRepository.findById(id);
        if (serie.isPresent()) {
            Serie s = serie.get();
            return new SerieDTO(s.getId(), s.getTitulo(), s.getTotalTemporadas(), s.getEvaluacion(), s.getGenero(),
                    s.getSinopsis(), s.getPoster(), s.getPremios());

        } else {
            return null;
        }
    }
}
