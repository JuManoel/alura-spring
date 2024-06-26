package edu.alura.ProjectSeries.principal;

import java.util.ArrayList;
import java.util.Comparator;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import java.util.stream.Collectors;

import edu.alura.ProjectSeries.models.*;
import edu.alura.ProjectSeries.repository.SerieRepository;
import edu.alura.ProjectSeries.service.ConsumoAPI;
import edu.alura.ProjectSeries.service.ConvierteDatos;

public class Principal {
        // Classes auxiliares para el codigo.
        // el Scanner, la clase que va leer los datos del usuario
        // el ConsumoAPI, la clase que va realizar la conecion con el servidor
        // el ConvierteDatos, la clase que va convertir el json en un objeto de
        // cualquier tipo
        private Scanner scn = new Scanner(System.in);
        private ConsumoAPI consumoApi = new ConsumoAPI();
        private ConvierteDatos convierteDatos = new ConvierteDatos();

        // nuestra variable json. va recibir la string de Consumo API
        private String json;

        // Constante para pedir los dados del consumo API
        private final String API = "&apikey=53a8e07f";
        private final String URL = "https://www.omdbapi.com/?t=";

        private SerieRepository repository;

        private List<Serie> series;

        public Principal(SerieRepository serieRepository) {
                this.repository = serieRepository;
        }

        public void menu() {
                int opcion;
                do {
                        var menu = """
                                        1 - Buscar series           4. Buscar serie por Titulo
                                        2 - Buscar episodios        5. Top 5 Series
                                        3 - Mostrar series buscadas 6. Buscar serie por Genero
                                        7. Buscar por temporadas y  evaluacion
                                        8. Buscar episodio por nombre
                                        9. TOP 5 episodes por Serie
                                        0 - Salir
                                        """;
                        System.out.println(menu);
                        opcion = scn.nextInt();
                        scn.nextLine();

                        switch (opcion) {
                                case 1:
                                        buscarSerieWeb();
                                        break;
                                case 2:
                                        buscarEpisodioPorSerie();
                                        break;
                                case 3:
                                        mostrarSeries();
                                        break;
                                case 4:
                                        buscarSerieTitulo();
                                        break;
                                case 5:
                                        buscarTop5();
                                        break;
                                case 6:
                                        buscarPorGenero();
                                        break;
                                case 7:
                                        buscarPorTempeEEval();
                                        break;
                                case 8:
                                        buscarEpisodeNombre();
                                        break;
                                case 9:
                                        buscarTop5Eps();
                                        break;
                                case 0:
                                        System.out.println("Cerrando la aplicación...");
                                        break;
                                default:
                                        System.out.println("Opción inválida");
                        }
                } while (opcion != 0);

        }

        private void buscarTop5Eps() {
                System.out.println("Ingres el nombre de la Serie que buscas");
                String nombre = this.scn.nextLine();
                Optional<Serie> serie = this.repository.findByTituloContainsIgnoreCase(nombre);
                if (serie.isPresent()) {
                        List<Episode> episodes = this.repository.top5Episodes(serie.get());
                        episodes.forEach(System.out::println);
                } else
                        System.out.println("No existe esa serie");
        }

        private void buscarEpisodeNombre() {
                System.out.println("Ingrese el nombre del episodio");
                String nombre = this.scn.nextLine();
                Optional<List<Episode>> serie = this.repository.buscarEpPorNombre(nombre);
                if (serie.isPresent()) {
                        serie.get().stream()
                                        .forEach(System.out::println);
                } else
                        System.out.println("Nombre no existe");
        }

        private void buscarPorTempeEEval() {
                System.out.println("Ingrese la cantidad de temporadas");
                int temporadas = this.scn.nextInt();
                this.scn.nextLine();
                System.out.println("Ingrese la evaluacion que buscas");
                double evaluacion = this.scn.nextDouble();
                this.scn.nextLine();
                Optional<List<Serie>> serie = this.repository
                                .buscarSerieTempEval(temporadas,
                                                evaluacion);
                if (serie.isPresent())
                        serie.get().forEach(System.out::println);
                else
                        System.out.println("Serie no encuentrada :()");
        }

        private void buscarPorGenero() {
                System.out.println("Ingrese o genero que buscas");
                String genero = this.scn.nextLine();
                List<Serie> series = repository.findByGenero(Categorias.fromString(genero));
                series.forEach(System.out::println);
        }

        private void buscarTop5() {
                List<Serie> top5 = repository.findTop5ByOrderByEvaluacionDesc();
                top5.forEach(System.out::println);
        }

        private void buscarSerieTitulo() {
                System.out.println("Ingrese o titulo que desejas buscar");
                String nombreSerie = this.scn.nextLine();
                Optional<Serie> serie = repository.findByTituloContainsIgnoreCase(nombreSerie);
                if (serie.isPresent())
                        System.out.println(serie.get());
                else
                        System.out.println("Serie no encuentrada");
        }

        private DatosSeries getDatosSerie() {
                System.out.println("Escribe el nombre de la serie que deseas buscar");
                var nombreSerie = scn.nextLine();
                this.json = consumoApi.obtenerDatos(URL + nombreSerie.replace(" ", "+") + API);
                DatosSeries datos = convierteDatos.obterDados(this.json, DatosSeries.class);
                return datos;
        }

        private void buscarEpisodioPorSerie() {
                mostrarSeries();
                System.out.println("Escriba el nombre de la serie que quiere ver");
                String nombreSerie = this.scn.nextLine();
                Optional<Serie> serie = series.stream()
                                .filter(s -> s.getTitulo().toLowerCase().contains(nombreSerie.toLowerCase()))
                                .findFirst();
                if (serie.isPresent()) {
                        var savedSerie = serie.get();
                        List<DatosTemporadas> temporadas = new ArrayList<>();

                        for (int i = 1; i <= savedSerie.getTotalTemporadas(); i++) {
                                this.json = consumoApi.obtenerDatos(
                                                URL + savedSerie.getTitulo().replace(" ", "+") + "&season=" + i + API);
                                DatosTemporadas datosTemporada = convierteDatos.obterDados(this.json,
                                                DatosTemporadas.class);
                                temporadas.add(datosTemporada);
                        }
                        List<Episode> episodes = temporadas.stream()
                                        .flatMap(t -> t.episodes().stream()
                                                        .map(e -> new Episode(t.num(), e)))
                                        .collect(Collectors.toList());
                        savedSerie.setEpisodes(episodes);

                        episodes.stream()
                                        .forEach(System.out::println);

                        repository.save(savedSerie);
                } else
                        System.out.println("Serie no encontrada");

        }

        private void buscarSerieWeb() {
                DatosSeries datos = getDatosSerie();
                Serie serie = new Serie(datos);
                repository.save(serie);
                // datosSeries.add(datos);
                System.out.println(datos.titulo() + " adicionada con sucesso!");
        }

        private void mostrarSeries() {
                series = repository.findAll();
                series.stream()
                                .sorted(Comparator.comparing(Serie::getGenero))
                                .forEach(System.out::println);
        }
}
