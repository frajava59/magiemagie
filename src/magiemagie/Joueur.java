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
public class Joueur {

    protected String nom;
    protected ArrayList<Carte> cartes = new ArrayList<>();

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public ArrayList<Carte> getCartes() {
        return cartes;
    }

    public void setCartes(ArrayList<Carte> cartes) {
        this.cartes = cartes;
    }

    @Override
    public String toString() {
        String txt="";
        txt+="Nom : " + nom+"\n";
        txt+="Cartes : " + cartes+"\n";
        
        return txt;

//        System.out.println("Cartes : ");
//        if (cartes != null && cartes.size() != 0) {
//            for (Carte c : cartes) {
//                System.out.println(c.getType());
//            }
//        } else {
//            System.out.println("Pas de carte");
//        }
    }

    public void afficheJoueur() {
        System.out.println("Joueur");
        System.out.println("------");
        System.out.println("Nom : " + nom);
        System.out.println("Cartes : ");
        if (cartes != null && cartes.size() != 0) {
            for (Carte c : cartes) {
                System.out.println(c.getType().toString() );
            }
        } else {
            System.out.println("Pas de carte");
        }

    }

}
