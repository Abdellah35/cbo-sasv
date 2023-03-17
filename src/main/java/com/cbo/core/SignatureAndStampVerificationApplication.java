package com.cbo.core;


import com.cbo.core.model.ERole;
import com.cbo.core.model.Role;
import com.cbo.core.model.User;
import com.cbo.core.repo.UserRepository;
import com.cbo.core.service.RoleService;
import com.cbo.core.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.ResponseEntity;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


@SpringBootApplication
@EnableSwagger2
@EnableJpaRepositories(basePackageClasses = UserRepository.class)
public class SignatureAndStampVerificationApplication {

	private final UserService userService;
	private final UserRepository userRepository;
	private final RoleService roleService;

	public SignatureAndStampVerificationApplication(UserService userService, UserRepository userRepository, RoleService roleService) {
		this.userService = userService;
		this.userRepository = userRepository;
		this.roleService = roleService;
	}

	public static void main(String[] args) {
		SpringApplication.run(SignatureAndStampVerificationApplication.class, args);
	}

	@Bean
	public CommandLineRunner CommandLineRunnerBean() {
		return (args) -> {

			List<User> adminUser = userRepository.findAll();
			if(adminUser.isEmpty()){
				System.out.println("Creating ROLE");
				// creating admin role
				Role admin = new Role();
				admin.setName(ERole.ROLE_ADMIN);
				System.out.println(admin.getName());
				roleService.addRole(admin);

				//creating user role
				Role userR = new Role();
				userR.setName(ERole.ROLE_USER);
				roleService.addRole(userR);

				//creating director role
				Role director = new Role();
				director.setName(ERole.ROLE_DIRECTOR);
				roleService.addRole(director);

				System.out.println("Creating Admin If not exist.");

				userService.addAdmin();
			}
		};
	}
	@Bean
	public Docket petApi() {

		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any())
				.build()
				.apiInfo(apiDetails())
				.pathMapping("/")
				.directModelSubstitute(LocalDate.class, String.class)
				.genericModelSubstitutes(ResponseEntity.class);
	}

	private ApiInfo apiDetails(){
		return new ApiInfo(
				"Stamp & Signature Verification API",
				"API for Authorities Identity verification, Build for CBO Employees",
				"1.0",
				"Secure",
				new springfox.documentation.service.Contact("Core System", "https://cbo.com.et","info@cbo.com.et"),
				"API License",
				"https://cbo.com.et",
				Collections.emptyList()
		);
	}
	@Bean
	public CorsFilter corsFilter(){
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.setAllowCredentials(true);
		corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
		corsConfiguration.setAllowedHeaders(Arrays.asList("Origin","Access-Control-Allow-Origin","Content-Type",
				"Accept","Authorization","Origin, Accept", "X-Requested-With",
				"Access-Control-Request-Method","Access-Control-Request-Headers"));
		corsConfiguration.setExposedHeaders(Arrays.asList("Origin","Content-Type",
				"Accept","Authorization","Access-Control-Allow-Origin", "Access-Control-Allow-Credentials"));
		corsConfiguration.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE"));
		UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
		urlBasedCorsConfigurationSource.registerCorsConfiguration("http://localhost:4200/**", corsConfiguration);
		return new CorsFilter(urlBasedCorsConfigurationSource);
	}
}