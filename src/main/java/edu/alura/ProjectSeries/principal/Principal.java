package edu.alura.ProjectSeries.principal;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
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
        private ConsumoAPI consumoApi = new ConsumoAPI();
        private ConvierteDatos convierteDatos = new ConvierteDatos();

        // nuestra variable json. va recibir la string de Consumo API
        private String json;

        // Constante para pedir los dados del consumo API
        private final String API = "&apikey=53a8e07f";
        private final String URL = "https://www.omdbapi.com/?t=";

        public void menu() {
                var opcion = -1;
                while (opcion != 0) {
                        var menu = """
                                        1 - Buscar series
                                        2 - Buscar episodios
                                        3 - Mostrar series buscadas

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

                                case 0:
                                        System.out.println("Cerrando la aplicación...");
                                        break;
                                default:
                                        System.out.println("Opción inválida");
                        }
                }

        }

        private DatosSeries getDatosSerie() {
                System.out.println("Escribe el nombre de la serie que deseas buscar");
                var nombreSerie = scn.nextLine();
                var json = consumoApi.obtenerDatos(URL + nombreSerie.replace(" ", "+") + API);
                System.out.println(json);
                DatosSeries datos = convierteDatos.obterDados(json, DatosSeries.class);
                return datos;
        }

        private void buscarEpisodioPorSerie() {
                DatosSeries datosSerie = getDatosSerie();
                List<DatosTemporadas> temporadas = new ArrayList<>();

                for (int i = 1; i <= datosSerie.totalTemporadas(); i++) {
                        var json = consumoApi.obtenerDatos(
                                        URL + datosSerie.titulo().replace(" ", "+") + "&season=" + i + API);
                        DatosTemporadas datosTemporada = convierteDatos.obterDados(json, DatosTemporadas.class);
                        temporadas.add(datosTemporada);
                }
                temporadas.forEach(System.out::println);
        }

        private void buscarSerieWeb() {
                DatosSeries datos = getDatosSerie();
                System.out.println(datos);
        }
}
