package edu.alura.ProjectSeries.models;

public enum Categorias {
    ACCION("Action","Acción"),
    ROMANCE("Romance","Romance"),
    COMEDIA("Comedy","Comedia"),
    CRIMEN("Crime","Crime"),
    DRAMA("Drama","Drama"),
    TERROR("Thriller","Terror"),
    CIENCIA_FICCION("Sci-Fi","Ciancia ficcion"),
    ANIMACION("Animation","Animacion"),
    AVENTURA("Adventure","Aventura"),
    FANTASIA("Fantasy","Fantasia");

    private String categoria;
    private String categoriaEsp;

    Categorias(String categoria, String categoriaEsp) {
        this.categoria = categoria;
        this.categoriaEsp = categoriaEsp;
    }
    public static Categorias fromString(String s){
        for(Categorias c: Categorias.values()){
            if(c.categoria.equalsIgnoreCase(s) || c.categoriaEsp.equalsIgnoreCase(s)){
                return c;   
            }
        }
        throw new IllegalArgumentException(s+" no es una categoria");
    }
}
