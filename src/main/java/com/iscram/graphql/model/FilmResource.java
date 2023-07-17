package com.iscram.graphql.model;

import com.iscram.graphql.service.GalaxyService;
import jakarta.inject.Inject;
import org.eclipse.microprofile.graphql.*;

import java.util.List;

@GraphQLApi
public class FilmResource {

    @Inject
    GalaxyService service;

    @Query("allFilms")
    @Description("Get all Films from a galaxy far far away")
    public List<Film> getAllFilms() {
        return service.getAllFilms();
    }

    @Query
    @Description("Get a Films from a galaxy far far away")
    public Film getFilm(@Name("filmId") int id) {
        return service.getFilm(id);
    }

    public List<Hero> heroes(@Source Film film) {
        return service.getHeroesByFilm(film);
    }
    @Query
    @Description("Get all characters from a galaxy far far away")
    public List<Character> characters() {
        return service.getAllCharacters();
    }


}