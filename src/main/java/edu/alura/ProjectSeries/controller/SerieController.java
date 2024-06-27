package edu.alura.ProjectSeries.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.alura.ProjectSeries.dto.SerieDTO;
import edu.alura.ProjectSeries.service.SerieService;

@RestController
@RequestMapping("/series")
public class SerieController {

    @Autowired
    private SerieService serieService;

    @GetMapping()
    public List<SerieDTO> mostrarSeries() {

        return serieService.mostrarSeries();
    }

    @GetMapping("/top5")
    public List<SerieDTO> mostrarSeriesTop5() {
        return serieService.mostrarTop5();
    }

    @GetMapping("/lanzamientos")
    public List<SerieDTO> mostrarSeriesRecientes() {
        return serieService.lanzamientoRecente();
    }

    @GetMapping("/{id}")
    public SerieDTO mostrarSeriePorId(@PathVariable int id) {
        return serieService.mostrarSeriePorId(id);
    }

}
