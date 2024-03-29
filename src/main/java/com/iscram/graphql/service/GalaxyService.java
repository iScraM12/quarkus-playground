package com.iscram.graphql.service;

import com.iscram.graphql.model.Ally;
import com.iscram.graphql.model.Film;
import com.iscram.graphql.model.Hero;
import com.iscram.graphql.model.LightSaber;
import com.iscram.graphql.model.Character;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class GalaxyService {
    private List<Hero> heroes = new ArrayList<>();
    private List<Film> films = new ArrayList<>();
    private List<Ally> allies = new ArrayList();


    public GalaxyService() {

        Film aNewHope = new Film();
        aNewHope.title = "A New Hope";
        aNewHope.releaseDate = LocalDate.of(1977, Month.MAY, 25);
        aNewHope.episodeID = 4;
        aNewHope.director = "George Lucas";

        Film theEmpireStrikesBack = new Film();
        theEmpireStrikesBack.title = "The Empire Strikes Back";
        theEmpireStrikesBack.releaseDate = LocalDate.of(1980, Month.MAY, 21);
        theEmpireStrikesBack.episodeID = 5;
        theEmpireStrikesBack.director = "George Lucas";

        Film returnOfTheJedi = new Film();
        returnOfTheJedi.title = "Return Of The Jedi";
        returnOfTheJedi.releaseDate = LocalDate.of(1983, Month.MAY, 25);
        returnOfTheJedi.episodeID = 6;
        returnOfTheJedi.director = "George Lucas";

        films.add(aNewHope);
        films.add(theEmpireStrikesBack);
        films.add(returnOfTheJedi);

        Hero luke = new Hero();
        luke.setName("Luke");
        luke.setSurname("Skywalker");
        luke.setHeight(1.7);
        luke.setMass(73);
        luke.setLightSaber(LightSaber.GREEN);
        luke.setDarkSide(false);
        luke.getEpisodeIds().addAll(Arrays.asList(4, 5, 6));

        Hero leia = new Hero();
        leia.setName("Leia");
        leia.setSurname("Organa");
        leia.setHeight(1.5);
        leia.setMass(51);
        leia.setDarkSide(false);
        leia.getEpisodeIds().addAll(Arrays.asList(4, 5, 6));


        Hero vader = new Hero();
        vader.setName("Darth");
        vader.setSurname("Vader");
        vader.setHeight(1.9);
        vader.setMass(89);
        vader.setDarkSide(true);
        vader.setLightSaber(LightSaber.RED);
        vader.getEpisodeIds().addAll(Arrays.asList(4, 5, 6));

        Ally jarjar = new Ally();
        jarjar.setName("Jar Jar");
        jarjar.setSurname("Binks");
        allies.add(jarjar);

        heroes.add(luke);
        heroes.add(leia);
        heroes.add(vader);

    }

    public List<Film> getAllFilms() {
        return films;
    }

    public Film getFilm(int id) {
        return films.get(id);
    }

    public List<Hero> getHeroesByFilm(Film film) {
        return heroes.stream()
                .filter(hero -> hero.getEpisodeIds().contains(film.episodeID))
                .collect(Collectors.toList());
    }

    public void addHero(Hero hero) {
        heroes.add(hero);
    }

    public Hero deleteHero(int id) {
        return heroes.remove(id);
    }

    public List<Hero> getHeroesBySurname(String surname) {
        return heroes.stream()
                .filter(hero -> hero.getSurname().equals(surname))
                .collect(Collectors.toList());
    }

    public List<Character> getAllCharacters() {
        List<Character> characters = new ArrayList<>();
        characters.addAll(heroes);
        characters.addAll(allies);
        return characters;
    }
}
