package com.example.gamechanger.model;

import java.util.LinkedList;
import java.util.List;

public class Model {
    public final static Model instance = new Model();

    private Model(){
        Game game1 = new Game();
        game1.id = "1234";
        game1.name = "Fifa21";
        game1.price = "199.00";
        data.add(game1);

        Game game2 = new Game();
        game2.id = "1234";
        game2.name = "Fifa22";
        game2.price = "199.00";
        data.add(game2);

        Game game3 = new Game();
        game3.id = "1234";
        game3.name = "Fifa23";
        game3.price = "199.00";
        data.add(game3);

        Game game4 = new Game();
        game4.id = "1234";
        game4.name = "Fifa24";
        game4.price = "199.00";
        data.add(game4);
    }

    List<Game> data = new LinkedList<Game>();

    public List<Game> getAllGames() {
        return data;
    }
}
