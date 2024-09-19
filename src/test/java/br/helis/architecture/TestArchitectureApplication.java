package br.helis.architecture;

import org.springframework.boot.SpringApplication;

public class TestArchitectureApplication {

	public static void main(String[] args) {
		SpringApplication.from(ArchitectureApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
