package com.crusos.IAScursos;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(
    scanBasePackages = "com.crusos",
    exclude = {UserDetailsServiceAutoConfiguration.class}
)
@EnableJpaRepositories(basePackages = "com.crusos")
@EntityScan(basePackages = "com.crusos")
@SecurityScheme(
    name = "bearerAuth",
    type = SecuritySchemeType.HTTP,
    bearerFormat = "JWT",
    scheme = "bearer"
)
public class IaScursosApplication {

	public static void main(String[] args) {
		SpringApplication.run(IaScursosApplication.class, args);
	}

}
