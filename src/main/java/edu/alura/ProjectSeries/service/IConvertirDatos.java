package edu.alura.ProjectSeries.service;

public interface IConvertirDatos {
    <T> T obterDados(String json, Class<T> clase);
}
