/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magiemagie.test;

import java.util.ArrayList;
import magiemagie.Carte;
import org.junit.Test;

/**
 *
 * @author Formation
 */
public class RemoveTest {
    
    @Test
    public void removeOK() {

        ArrayList<Carte> cartes = new ArrayList<>();

        Carte c1 = new Carte();
        c1.setType(Carte.TypeCarte.MANDRAGORE);
        cartes.add(c1);

        Carte c2 = new Carte();
        c2.setType(Carte.TypeCarte.MANDRAGORE);
        cartes.add(c2);

        Carte c3 = new Carte();
        c3.setType(Carte.TypeCarte.MANDRAGORE);
        cartes.add(c3);
        
        
        ArrayList<Carte.TypeCarte> titi = new ArrayList<>();
                        
        titi.add(Carte.TypeCarte.CORNE_DE_LICORNE);
        titi.add(c1.getType());
        
        System.out.println("titi : "+titi);
        System.out.println("cartes : "+cartes);


        Carte carteSortA = new Carte();
        carteSortA.setType(Carte.TypeCarte.MANDRAGORE);

        System.out.println(cartes.contains(carteSortA));
        System.out.println(cartes.remove(carteSortA));
        System.out.println(cartes);

    }
}
