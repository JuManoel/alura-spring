package edu.alura.ProjectSeries.models;

public enum Categorias {
    ACCION("Action"),
    ROMANCE("Romance"),
    COMEDIA("Comedy"),
    CRIMEN("Crime"),
    DRAMA("Drama"),
    TERROR("Thriller"),
    CIENCIA_FICCION("Sci-Fi"),
    ANIMACION("Animation"),
    AVENTURA("Adventure"),
    FANTASIA("Fantasy");

    private String categoria;

    Categorias(String categoria) {
        this.categoria = categoria;
    }
    public static Categorias fromString(String s){
        for(Categorias c: Categorias.values()){
            if(c.categoria.equalsIgnoreCase(s)){
                return c;   
            }
        }
        throw new IllegalArgumentException(s+" no es una categoria");
    }
}
