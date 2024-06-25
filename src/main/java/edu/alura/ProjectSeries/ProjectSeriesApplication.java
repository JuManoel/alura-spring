package edu.alura.ProjectSeries;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import edu.alura.ProjectSeries.principal.Principal;
import edu.alura.ProjectSeries.repository.SerieRepository;

@SpringBootApplication
public class ProjectSeriesApplication implements CommandLineRunner {

	@Autowired
	private SerieRepository serieRepository;

	public static void main(String[] args) {
		SpringApplication.run(ProjectSeriesApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(serieRepository);
		principal.menu();

	}
	

}
