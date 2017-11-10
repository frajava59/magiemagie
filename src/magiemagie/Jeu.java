/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magiemagie;

import java.util.ArrayList;
import static java.util.Collections.list;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;
import magiemagie.Carte.TypeCarte;

/**
 *
 * @author Formation
 */
public class Jeu {

    protected ArrayList<Joueur> joueurs = new ArrayList<>();
    protected Joueur joueurEnCours;
    private static ArrayList<String> ingredients = new ArrayList<>();
    private static ArrayList<Sort> sorts = new ArrayList<>();
    TypeCarte[] tabTypCart = Carte.TypeCarte.values();

    public Jeu() {
        initJeu();
    }

    public ArrayList<Joueur> getJoueurs() {
        return joueurs;
    }

    public void setJoueurs(ArrayList<Joueur> joueurs) {
        this.joueurs = joueurs;
    }

    public void afficherMenuPricipal() {

        boolean encore = true;
        String saisieClav;

        while (encore) {
            System.out.println("----------------");
            System.out.println("     Menu");
            System.out.println("----------------");
            System.out.println("(1) Nouveau Joueur");
            System.out.println("(2) Démarrer partie");
            System.out.println("(8) Afficher liste des joueurs");
            System.out.println("(L) Liste joueur");
            System.out.println("(9) Quitter");

            System.out.println("----------------");

            System.out.print("Votre choix : ");
            saisieClav = new Scanner(System.in).next();

            switch (saisieClav) {
                case "1":
                    // Nouveau joueur
                    afficherSaisieNouveauJoueur();
                    break;
                case "2":
                    if (joueurs.size() < 2) {
                        System.err.println(">>>  Pas assez de joueur !");
                    } else {
                        gererPartie();
                    }
                    break;
                case "8":
                    if (joueurs.size() == 0) {
                        System.out.println(">>>  Aucun joueur !");
                    } else {
                        afficherListeJoueur();
                    }
                    break;
                case "L":
                    System.out.println(joueurs.toString());
                    break;
                case "9":
                    encore = false;
                    break;

            }
            System.out.println("");

        }
    }

    public void afficherSaisieNouveauJoueur() {
        String saisieClav;

        System.out.println("----------------");
        System.out.print("Nom du joueur : ");
        saisieClav = (new Scanner(System.in).nextLine()).trim();

        if (!saisieClav.equals("")) {
            Joueur j1 = new Joueur();
            j1.setNom(saisieClav);
            joueurs.add(j1);
        }
        System.out.println("");
    }

    public void gererPartie() {

        int joueurCourant;
        boolean encore = true;

        int ind;
        String saisieClav;
        int choixSort;

        // Distribuer 7 cartes à chaque joueur
        ajoutCarteAleatoire(7);

        // Le 1er joueur a la main
        joueurCourant = 0;

        // Déroulement de la partie
        while (encore) {
            joueurEnCours = joueurs.get(joueurCourant);
            // affichage des infos du joueur courant
            System.out.println(">>>> Joueur en cours : " + joueurEnCours.getNom() + " <<<<<<");
            System.out.println("     Cartes : : " + joueurEnCours.getCartes());

            // affichage des sorts du joueur courant
            ind = 1;
            // Liste des sorts lancables pour le joueur courant
            ArrayList<String> sortPossible = new ArrayList<>();

            // Listes temporaire (pour realiser comparaison containsAll)
            ArrayList<String> tmpStr1 = new ArrayList<>();
            ArrayList<String> tmpStr2 = new ArrayList<>();
            for (Carte xx : joueurEnCours.getCartes()) {
                tmpStr1.add(xx.toString());
            }
            for (Sort s : sorts) {
                tmpStr2.clear();
                for (TypeCarte ii : s.getIngredients()) {
                    tmpStr2.add(ii.toString());
                }
                if (tmpStr1.containsAll(tmpStr2)) {
                    sortPossible.add(s.nom);
                }
            }

            // afichage du Menu joueur
            // Sorts lancables
            for (String s : sortPossible) {
                System.out.println(ind + ") " + s);
                ind++;
            }
            // fin du menu
            System.out.println("p) Passer son tour");
            System.out.println("q) quitter la partie");
            System.out.print("------>  votre choix : ");
            saisieClav = (new Scanner(System.in).next()).trim();
            if (saisieClav.equals("q")) {
                encore = false;
            } else {
                if (saisieClav.equals("p")) {
                    // Passer son trour

                    // Ajout d'une carte
                    ajoutUneCarteAleatoire();
                } else {
                    choixSort = Integer.parseInt(saisieClav);
                    // test si hors limite
                    if (choixSort > 0 && choixSort <= sortPossible.size()) {
                        choixSort--;
                        System.out.println("Excellent choix");
                        //System.out.println(sortPossible.get(choixSort));

                        //System.out.println(joueurs.get(joueurCourant).getCartes());
                        // enlever les 2 cartes du sort
                        enleverCartes(sortPossible.get(choixSort));

                        //System.out.println(joueurs.get(joueurCourant).getCartes());
                        // Traitements des sorts
                        this.gererSort(sortPossible.get(choixSort));

                    }

                }
            }

            // application du sort choisi
            // passage au suivant
            if (joueurs.size() > 1) {
                joueurCourant++;
                if (joueurCourant >= joueurs.size()) {
                    System.out.println("Tour suivant");
                    joueurCourant = 0;
                }

            }

            if (joueurs.size() == 1) {
                encore = false;
                System.out.println("Bravo, " + joueurs.get(joueurCourant).getNom() + " ! Vous avez gagné");
            }
        }

    }

    public void afficherListeJoueur() {
        for (Joueur j : joueurs) {
            j.afficheJoueur();
        }

    }

    public void initJeu() {

        sorts.add(new Sort("INVISIBILITE", new ArrayList() {
            {
                add(Carte.TypeCarte.CORNE_DE_LICORNE);
                add(Carte.TypeCarte.BAVE_DE_CRAPAUD);
            }
        },
                "le joueur prend 1 carte(au hasard) chez tous ses adversaires"));
        sorts.add(new Sort("FILTRE D’AMOUR", new ArrayList() {
            {
                add(Carte.TypeCarte.CORNE_DE_LICORNE);
                add(Carte.TypeCarte.MANDRAGORE);
            }
        },
                "le joueur de votre choix vous donne la moitié de ses cartes(au hasard). S’il ne possède qu’une carte il a perdu"));
        sorts.add(new Sort("HYPNOSE", new ArrayList() {
            {
                add(Carte.TypeCarte.LAPIS_LAZULI);
                add(Carte.TypeCarte.BAVE_DE_CRAPAUD);
            }
        },
                "le joueur échange une carte de son choix contre trois cartes(au hasard) de son adversaire"));
        sorts.add(new Sort("DIVINATION", new ArrayList() {
            {
                add(Carte.TypeCarte.LAPIS_LAZULI);
                add(Carte.TypeCarte.AILE_DE_CHAUVE_SOURIS);
            }
        },
                "le joueur peut voir les cartes de tous les autres joueurs"));
        sorts.add(new Sort("SOMMEIL-PROFOND", new ArrayList() {
            {
                add(Carte.TypeCarte.MANDRAGORE);
                add(Carte.TypeCarte.BAVE_DE_CRAPAUD);
            }
        },
                "le joueur choisit une victime qui ne pourra pas lancer de sorts pendant 2 tours"));

    }

    public void ajoutCarteAleatoire(int nbCarte) {
        Carte tempocarte;
        Random rand = new Random();
        int nombreAleatoire;
        // Distribuer nbCarte cartes à chaque joueur
        for (Joueur j : joueurs) {
            // réinitialise le tableau de carte
            j.getCartes().clear();
            // traitement des cartes
            for (int i = 0; i < nbCarte; i++) {
                tempocarte = new Carte();
                nombreAleatoire = rand.nextInt(5); // nbre aleat entre 0 et 4
                tempocarte.setType(tabTypCart[nombreAleatoire]);
                j.getCartes().add(tempocarte);
            }
        }
    }

    public void ajoutUneCarteAleatoire() {
        // ajout d'un carte aléatoire au joueur courant quand il passe son tour
        Carte tempocarte;
        Random rand = new Random();
        int nombreAleatoire;
        // Distribuer nbCarte cartes à chaque joueur
        tempocarte = new Carte();
        nombreAleatoire = rand.nextInt(5); // nbre aleat entre 0 et 4
        tempocarte.setType(tabTypCart[nombreAleatoire]);
        joueurEnCours.getCartes().add(tempocarte);
    }

    public void gererSort(String sortChoisi) {
        Random rand = new Random();
        int nombreAleatoire;

        // Selon les cas 
        switch (sortChoisi) {
            case "INVISIBILITE":
                // prend une carte sur chacun des joueurs

                // balayage de tous les joueurs
                for (Joueur joueur : joueurs) {
                    // ne pas traiter le joueur courant
                    if (joueur != joueurEnCours) {
                        // pour chaque adversaire

                        // vérification si au moins une carte
                        if (joueur.cartes.size() >= 1) {
                            // nombreAleatoire en fonction du nombre de cartes
                            nombreAleatoire = rand.nextInt(joueur.cartes.size());
                            // ajout d'une carte provenant d'un adversaire
                            joueurEnCours.cartes.add(joueur.cartes.get(nombreAleatoire));
                            // suppression de la carte de l'adversaire
                            joueur.cartes.remove(nombreAleatoire);
                        }
                    }
                }

                break;
            case "FILTRE D’AMOUR":
                
                System.out.println( joueurEnCours.nom );
                
                Joueur[] tabJoueur = new Joueur[joueurs.size()];
                
                
                

                break;
            case "HYPNOSE":

                break;
            case "DIVINATION":
                // afficher les cartes des joueurs

                for (Joueur joueur : joueurs) {
                    if (joueur != joueurEnCours) {
                        System.out.println(joueur);
                    }
                }

                break;
            case "SOMMEIL-PROFOND":

                break;

            default:

        }

    }
    

    public void enleverCartes(String sortChoisi) {
        Carte uneCarte = new Carte();

        for (Sort s : sorts) {
            if (s.nom.equals(sortChoisi)) {
                for (TypeCarte ii : s.getIngredients()) {
                    Iterator<Carte> it = joueurEnCours.getCartes().iterator();
                    while (it.hasNext()) {
                        if (it.next().getType().equals(ii)) {
                            it.remove();
                            break;
                        }
                    }
                }
            }
        }
    }
}

// old version
//                    for (Carte carte : joueurs.get(jcourant).getCartes()) {
//                        //System.out.println(carte.toString());
//                        if (ii.toString().equals(carte.toString())) {
//                            uneCarte.setType(Carte.TypeCarte.valueOf(carte.toString()));
//
//                            joueurs.get(jcourant).getCartes().remove(uneCarte);
//                            //joueurs.get(jcourant).getCartes().remove(carte);
//                        }
//                    }
//String strTypeCarte = Carte.TypeCarte.BAVE_DE_CRAPAUD.toString(); 
//Carte.TypeCarte uu = Carte.TypeCarte.valueOf(strTypeCarte);
//        Iterator<Friend> it = list.iterator();
//        while (it.hasNext()) {
//            if (it.next().getFriendCaption().equals(targetCaption)) {
//                it.remove();
//                // If you know it's unique, you could `break;` here
//            }
//        }
