package edu.alura.ProjectSeries.principal;

import java.util.ArrayList;
import java.util.Comparator;

import java.util.List;

import java.util.Scanner;

import java.util.stream.Collectors;

import edu.alura.ProjectSeries.models.*;
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

        private List<DatosSeries> datosSeries = new ArrayList<>();

        public void menu() {
                int opcion;
                do {
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
                                case 3:
                                        mostrarSeries();
                                        break;
                                case 0:
                                        System.out.println("Cerrando la aplicación...");
                                        break;
                                default:
                                        System.out.println("Opción inválida");
                        }
                } while (opcion != 0);

        }

        private DatosSeries getDatosSerie() {
                System.out.println("Escribe el nombre de la serie que deseas buscar");
                var nombreSerie = scn.nextLine();
                this.json = consumoApi.obtenerDatos(URL + nombreSerie.replace(" ", "+") + API);
                DatosSeries datos = convierteDatos.obterDados(this.json, DatosSeries.class);
                return datos;
        }

        private void buscarEpisodioPorSerie() {
                DatosSeries datosSerie = getDatosSerie();
                List<DatosTemporadas> temporadas = new ArrayList<>();

                for (int i = 1; i <= datosSerie.totalTemporadas(); i++) {
                        this.json = consumoApi.obtenerDatos(
                                        URL + datosSerie.titulo().replace(" ", "+") + "&season=" + i + API);
                        DatosTemporadas datosTemporada = convierteDatos.obterDados(this.json, DatosTemporadas.class);
                        temporadas.add(datosTemporada);
                }
                temporadas.forEach(System.out::println);
        }

        private void buscarSerieWeb() {
                DatosSeries datos = getDatosSerie();
                datosSeries.add(datos);
                System.out.println(datos.titulo() + " adicionada con sucesso!");
        }

        private void mostrarSeries() {
                List<Serie> series = new ArrayList<>();
                series = datosSeries.stream()
                                .map(s -> new Serie(s))
                                .collect(Collectors.toList());
                series.stream()
                                .sorted(Comparator.comparing(Serie::getGenero))
                                .forEach(System.out::println);
        }
}
