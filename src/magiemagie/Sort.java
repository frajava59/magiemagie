/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magiemagie;

import java.util.ArrayList;

/**
 *
 * @author Formation
 */
public class Sort {

    protected String nom;
    protected ArrayList<Carte.TypeCarte> ingredients;
    protected String description;

    public Sort(String nom, ArrayList<Carte.TypeCarte> ingredients, String description) {
        this.nom = nom;
        this.ingredients = ingredients;
        this.description = description;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public ArrayList<Carte.TypeCarte> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<Carte.TypeCarte> ingredients) {
        this.ingredients = ingredients;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return nom + " demande " + ingredients;
    }
}
