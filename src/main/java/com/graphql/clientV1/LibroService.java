package com.graphql.clientV1;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class LibroService {
    AtomicInteger id=new AtomicInteger(1);

    List<Libro> libros(){
        return List.of(
                new Libro("Poemas",new Artista("Levstein",1991)),
                new Libro("Dibujos",new Artista("Di Pascuale",1967)),
                new Libro("Trampa de vocales",new Artista("Casile",1992))
        );
    }

    Optional<List<Libro>> encontrarLibrosDelArtista (String apellido){
        return Optional.of(libros().parallelStream()
                .collect(Collectors
                        .filtering(libro->libro.artista().apellido().equalsIgnoreCase(apellido),
                                Collectors.toUnmodifiableList())));
    }
}
