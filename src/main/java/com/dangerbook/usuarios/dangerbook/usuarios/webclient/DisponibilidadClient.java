package com.dangerbook.usuarios.dangerbook.usuarios.webclient;



import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Component
public class DisponibilidadClient {

    private final WebClient webClient;

    public DisponibilidadClient(@Value("${disponibilidad-service.url}") String disponibilidadServiceURL){
        this.webClient = WebClient.builder()
                .baseUrl(disponibilidadServiceURL)
                .build();
    }

    public Map<String, Object> getDisponibilidadById(Integer id){
        return this.webClient.get()
                .uri("/{id}",id)
                .retrieve()
                .onStatus(
                        status ->status.is4xxClientError(),
                        response ->response.bodyToMono(String.class)
                                .map(body -> new RuntimeException("Disponibilidad no encontrada"))
                )
                .bodyToMono(Map.class)
                .block();
    }
}
