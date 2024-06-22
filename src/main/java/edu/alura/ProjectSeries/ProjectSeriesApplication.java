package edu.alura.ProjectSeries;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import edu.alura.ProjectSeries.principal.Principal;

@SpringBootApplication
public class ProjectSeriesApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ProjectSeriesApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal();
		principal.menu();

	}

}
