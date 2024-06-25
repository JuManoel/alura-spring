package edu.alura.ProjectSeries.models;

import java.util.List;
import java.util.OptionalDouble;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "series")
public class Serie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true)
    private String titulo;

    private Integer totalTemporadas;

    private double evaluacion;

    @Enumerated(EnumType.STRING)
    private Categorias genero;

    private String premios;

    private String sinopsis;

    private String poster;

    @Transient
    private List<Episode> episodes;

    public Serie(){
        
    }

    public Serie(DatosSeries series) {
        this.titulo = series.titulo();
        this.totalTemporadas = series.totalTemporadas();
        this.evaluacion = OptionalDouble.of(Double.valueOf(series.evaluacion())).orElse(0.0f);
        this.genero = Categorias.fromString(series.genero().split(",")[0]);
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
                + "\ngenero: " + genero + "\npremios: " + premios + "\nsinopsis: " + sinopsis + "\nposter: " + poster;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Episode> getEpisodes() {
        return episodes;
    }

    public void setEpisodes(List<Episode> episodes) {
        this.episodes = episodes;
    }

}
