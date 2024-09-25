package br.helis.architecture;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ArchitectureApplication {

	public static void main(String[] args) {
		SpringApplication.run(ArchitectureApplication.class, args);
	}

}
