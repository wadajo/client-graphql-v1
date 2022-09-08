package com.graphql.clientV1;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.graphql.client.HttpGraphQlClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@SpringBootApplication
public class ClientV1Application {

	public static void main(String[] args) {
		SpringApplication.run(ClientV1Application.class, args);
	}

	@Bean
	HttpGraphQlClient graphQlClient(){
		return HttpGraphQlClient.builder()
				.url("http://localhost:8998/graphql")
				.build();
	}

	@Bean
	CommandLineRunner inicio(HttpGraphQlClient graphQlClient){
		return (args)->
			graphQlClient
				.document("""
							query{
								 artistas{
									 apellido,
									 nacimiento
								 }
							 }
						""")
				.retrieve("artistas")
				.toEntityList(Artista.class)
				.map(artistas -> artistas.stream()
						.map(artista->new Libro("Libro de "+artista.apellido(),artista))
						.toList())
				.subscribe(System.out::println);
	}
}
