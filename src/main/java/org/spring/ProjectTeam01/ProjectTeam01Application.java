package org.spring.ProjectTeam01;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ProjectTeam01Application {

	public static void main(String[] args) {
		SpringApplication.run(ProjectTeam01Application.class, args);
	}

}
