package edu.alura.ProjectSeries.principal;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.swing.text.DateFormatter;

import edu.alura.ProjectSeries.models.DatosEpisode;
import edu.alura.ProjectSeries.models.DatosSeries;
import edu.alura.ProjectSeries.models.DatosTemporadas;
import edu.alura.ProjectSeries.models.Episode;
import edu.alura.ProjectSeries.service.ConsumoAPI;
import edu.alura.ProjectSeries.service.ConvierteDatos;

public class Principal {
        // Classes auxiliares para el codigo.
        // el Scanner, la clase que va leer los datos del usuario
        // el ConsumoAPI, la clase que va realizar la conecion con el servidor
        // el ConvierteDatos, la clase que va convertir el json en un objeto de
        // cualquier tipo
        private Scanner scn = new Scanner(System.in);
        private ConsumoAPI comsumoAPI = new ConsumoAPI();
        private ConvierteDatos convierteDatos = new ConvierteDatos();

        // nuestra variable json. va recibir la string de Consumo API
        private String json;

        // Constante para pedir los dados del consumo API
        private final String API = "&apikey=53a8e07f";
        private final String URL = "https://www.omdbapi.com/?t=";

        public void menu() {
                // pedimos los datos de la serie que queremos
                System.out.println("Ingrese el nombre de la Serie que quieres ver");
                String serie = scn.nextLine();
                // la API no acepta " ", en lugar de eso utilizamos el +
                serie = serie.toLowerCase();
                serie = serie.replace(" ", "+");
                // conecion con la API, utilizo String.format() por ser mas sencillo y menos
                // prompicio a errores
                this.json = comsumoAPI.obtenerDatos(String.format("%s%s%s", URL, serie, API));
                // guardo y muestro los datos
                var datosSerie = convierteDatos.obterDados(json, DatosSeries.class);
                System.out.println(datosSerie);
                // muestro todos los datos de las temporadas
                List<DatosTemporadas> temporadas = new ArrayList<>();
                for (int index = 1; index <= datosSerie.totalTemporadas(); index++) {
                        json = comsumoAPI.obtenerDatos(String.format("%s%s&season=%d&%s",
                                        URL, serie, index, API));
                        var temporada = convierteDatos.obterDados(json, DatosTemporadas.class);
                        temporadas.add(temporada);
                }
                // temporadas.forEach(t -> {
                // t.episodes().forEach(e -> {
                // System.out.println(e.titulo());
                // });
                // System.out.println("================================================================");
                // });

                // List<DatosEpisode> datosEpisodes = temporadas.stream()
                // .flatMap(t -> t.episodes().stream())
                // .collect(Collectors.toList());

                // System.out.println("Top 5 episodeos");
                // datosEpisodes.stream()
                // .filter(e->!e.evaluacion().equals("N/A"))
                // .sorted(Comparator.comparing(DatosEpisode::evaluacion).reversed())
                // .limit(5)
                // .forEach(e->System.out.println(e));

                List<Episode> episodes = temporadas.stream()
                                .flatMap(t -> t.episodes().stream()
                                                .map(e -> new Episode(t.num(), e)))
                                .collect(Collectors.toList());

                // System.out.println("Top 5 episodes");
                // episodes.stream().sorted(Comparator.comparing(Episode::getEvaluacion).reversed())
                // .limit(5)
                // .forEach(System.out::println);

                // episodes.forEach(e -> System.out.println(e.toString()));
                // DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                // System.out.println("Ingrese el año del epsodio");
                // int year = Integer.parseInt(scn.nextLine());
                // LocalDate fechaBusqueda = LocalDate.of(year, 1, 1);
                // episodes.stream()
                // .filter(e -> e.getFechaLanz() != null &&
                // e.getFechaLanz().isAfter(fechaBusqueda))
                // .forEach(e -> System.out.println(
                // "Temporada: " + e.getTemporada() +
                // " Episodio: " + e.getTitulo() +
                // " Fecha: " + e.getFechaLanz().format(formato)));

                // System.out.println("Ingrese el nombre o um pedaço del nombre del episodio que
                // buscas");
                // String titulo = scn.nextLine();
                // Optional<Episode> epBuscado = episodes.stream()
                // .filter(e->e.getTitulo().toLowerCase().contains(titulo.toLowerCase()))
                // .findFirst();
                // if(epBuscado.isPresent())
                // System.out.println(epBuscado.get());
                // else
                // System.out.println("Ep no encontrado");

                Map<Integer, Double> evalPorTemp = episodes.parallelStream()
                .filter(e->e.getEvaluacion()>0)
                .collect(Collectors.groupingBy(Episode::getTemporada,
                Collectors.averagingDouble(Episode::getEvaluacion)));

                System.out.println(evalPorTemp);

                System.out.println("Deseas buscar otra serie?");
                if (scn.nextLine().toLowerCase().toCharArray()[0] == 's') {
                        menu();
                }
                return;

        }
}
