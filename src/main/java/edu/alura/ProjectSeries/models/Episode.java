package edu.alura.ProjectSeries.models;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Episode {
    private int temporada;
    private String titulo;
    private int numEp;
    private double evaluacion;
    private LocalDate fechaLanz;
    

    public Episode(int temporada, DatosEpisode datosEpisode) {
        this.temporada = temporada;
        this.titulo = datosEpisode.titulo();
        this.numEp = datosEpisode.numEp();
        try {
            this.evaluacion = Double.parseDouble(datosEpisode.evaluacion());
        } catch (NumberFormatException e) {
            this.evaluacion = 0;
        }
        try {
            this.fechaLanz = LocalDate.parse(datosEpisode.fechaLanz());
        } catch (DateTimeParseException e) {
            this.fechaLanz = null;
        }
        

    }
    public int getTemporada() {
        return temporada;
    }
    public void setTemporada(int temporada) {
        this.temporada = temporada;
    }
    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public int getNumEp() {
        return numEp;
    }
    public void setNumEp(int numEp) {
        this.numEp = numEp;
    }
    public double getEvaluacion() {
        return evaluacion;
    }
    public void setEvaluacion(double evaluacion) {
        this.evaluacion = evaluacion;
    }
    public LocalDate getFechaLanz() {
        return fechaLanz;
    }
    public void setFechaLanz(LocalDate fechaLanz) {
        this.fechaLanz = fechaLanz;
    }
    @Override
    public String toString() {
        String str ="""
                Temporada: %d
                Titulo: %s
                Numero de episodio: %d
                Evaluacion: %.2f
                Fecha de lanzamiento: %s
                """;
        str = String.format(str, temporada,titulo,numEp,evaluacion,fechaLanz);
        return str;
    }
    
}
