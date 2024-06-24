package edu.alura.ProjectSeries.models;

import java.util.OptionalDouble;

public class Serie {
    private String titulo;
    private Integer totalTemporadas;
    private double evaluacion;
    private Categorias genero;
    private String director;
    private String escritor;
    private String actores;
    private String premios;
    private String sinopsis;
    private String poster;

    
    public Serie(DatosSeries series) {
        this.titulo = series.titulo();
        this.totalTemporadas = series.totalTemporadas();
        this.evaluacion = OptionalDouble.of(Double.valueOf(series.evaluacion())).orElse(0.0f);
        this.genero = Categorias.fromString(series.genero().split(",")[0]);
        this.director = series.director();
        this.escritor = series.escritor();
        this.actores = series.actores();
        this.premios = series.premios();
        this.sinopsis = series.sinopsis();
        this.poster = series.poster();
    }
    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public Integer getTotalTemporadas() {
        return totalTemporadas;
    }
    public void setTotalTemporadas(Integer totalTemporadas) {
        this.totalTemporadas = totalTemporadas;
    }
    public double getEvaluacion() {
        return evaluacion;
    }
    public void setEvaluacion(double evaluacion) {
        this.evaluacion = evaluacion;
    }
    public Categorias getGenero() {
        return genero;
    }
    public void setGenero(Categorias genero) {
        this.genero = genero;
    }
    public String getDirector() {
        return director;
    }
    public void setDirector(String director) {
        this.director = director;
    }
    public String getEscritor() {
        return escritor;
    }
    public void setEscritor(String escritor) {
        this.escritor = escritor;
    }
    public String getActores() {
        return actores;
    }
    public void setActores(String actores) {
        this.actores = actores;
    }
    public String getPremios() {
        return premios;
    }
    public void setPremios(String premios) {
        this.premios = premios;
    }
    public String getSinopsis() {
        return sinopsis;
    }
    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }
    public String getPoster() {
        return poster;
    }
    public void setPoster(String poster) {
        this.poster = poster;
    }
    @Override
    public String toString() {
        return "titulo: " + titulo + "\ntotalTemporadas: " + totalTemporadas + "\nevaluacion: " + evaluacion
                + "\ngenero: " + genero + "\ndirector: " + director + "\nescritor: " + escritor + "\nactores: " + actores
                + "\npremios: " + premios + "\nsinopsis: " + sinopsis + "\nposter: " + poster;
    }
    
}
