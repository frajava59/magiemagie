/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magiemagie;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import magiemagie.Carte.TypeCarte;

/**
 *
 * @author Formation
 */
public class Jeu {

    private ArrayList<Joueur> joueurs = new ArrayList<>();
    private Joueur joueurEnCours;
    private static ArrayList<String> ingredients = new ArrayList<>();
    private static ArrayList<Sort> sorts = new ArrayList<>();
    TypeCarte[] tabTypCart = Carte.TypeCarte.values();

    public static final boolean POURTEST = true;

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
                    if (POURTEST) {
                        Joueur j1 = new Joueur();
                        j1.setNom("primus");
                        Joueur j2 = new Joueur();
                        j2.setNom("deuzio");
                        joueurs.add(j1);
                        joueurs.add(j2);
                    }
                    if (joueurs.size() < 2) {
                        System.out.println(">>>  Pas assez de joueur !");
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

        // Distribuer 7 cartes à chaque joueur et RAZ sommeil
        ajoutCarteAleatoire(7);

        // Le 1er joueur a la main
        joueurCourant = 0;

        // Déroulement de la partie
        while (encore) {
            joueurEnCours = joueurs.get(joueurCourant);
            // affichage des infos du joueur courant
            this.afficheUnJoueur(joueurEnCours);

            if (joueurEnCours.getSommeil() > 0) {
                this.gererJoueurEnSommeil();
            } else {
                if (joueurEnCours.getCartes().isEmpty()) {
                    // Cas du joueur spectateur
                    System.out.println("     est spectateur");

                } else {
                    encore = this.afficherMenuJoueur();
                }
            }

            // Il y a t il un gagnant ?
            if (this.gagnantTrouve()) {
                encore = false;
            } else {
                // passage au suivant
                joueurCourant++;
                if (joueurCourant >= joueurs.size()) {
                    System.out.println("____________");
                    System.out.println("Tour suivant");
                    System.out.println("____________");
                    joueurCourant = 0;
                }
            }
        }
    }

    private boolean afficherMenuJoueur() {
        // afichage du Menu joueur
        int ind;
        String saisieClav;
        int choixSort;
        ArrayList<String> sortPossible;
        boolean encore = true;

        // Liste des sorts lancables pour le joueur courant
        sortPossible = new ArrayList<>();
        this.creationListSortPossible(sortPossible);

        while (encore) {
            System.out.print("");
            // affichage des sorts du joueur courant

            // Sorts lancables
            ind = 1;
            for (String s : sortPossible) {
                System.out.println(ind + ") " + s);
                ind++;
            }
            // fin du menu
            System.out.println("p) Passer son tour");
            System.out.println("q) quitter la partie");
            System.out.print("------>  votre choix : ");

            saisieClav = (new Scanner(System.in).next()).trim();
            System.out.println("");

            if (saisieClav.equals("q")) {
                return false;
            } else {
                if (saisieClav.equals("p")) {
                    // Passer son tour
                    // Ajout d'une carte
                    ajoutUneCarteAleatoire();
                    encore = false;
                } else {
                    try {
                        choixSort = Integer.parseInt(saisieClav);
                    } catch (NumberFormatException e) {
                        choixSort = -1;
                    }
                    // test si hors limite
                    if (choixSort > 0 && choixSort <= sortPossible.size()) {
                        choixSort--;
                        System.out.println("Excellent choix");
                        // enlever les 2 cartes du sort
                        enleverCartes(sortPossible.get(choixSort));
                        // Traitements des sorts
                        this.gererSort(sortPossible.get(choixSort));
                        encore = false;
                    } else {
                        System.out.println(" choix incorrect !!!");
                        System.out.println("");
                    }
                }
            }

        }
        return true;
    }

    public void gererSort(String sortChoisi) {
        Random rand = new Random();
        int nombreAleatoire;
        int nbCarteCible;
        Joueur cible;

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
                        if (joueur.getCartes().size() >= 1) {
                            // nombreAleatoire en fonction du nombre de cartes
                            nombreAleatoire = rand.nextInt(joueur.getCartes().size());
                            // ajout d'une carte provenant d'un adversaire
                            joueurEnCours.getCartes().add(joueur.getCartes().get(nombreAleatoire));
                            // suppression de la carte de l'adversaire
                            joueur.getCartes().remove(nombreAleatoire);
                        }
                    }
                }

                // pour tests
                if (POURTEST) {
                    System.out.println("");
                    afficherListeJoueur();
                    System.out.println("");
                }

                break;
            case "PHILTRE D’AMOUR":
                // Choix d'un joueur cible
                cible = this.selectionnerJoueur();
                // Récupérer la moitié des carte de la cible
                nbCarteCible = cible.getCartes().size();
                System.out.println("Récupération de " + Math.abs(nbCarteCible / 2) + " cartes");

                for (int i = 0; i < Math.abs(nbCarteCible / 2); i++) {
                    if (cible.getCartes().size() > 0) {
                        nombreAleatoire = rand.nextInt(cible.getCartes().size());
                        //System.out.println(i +" , "+nombreAleatoire);
                        joueurEnCours.getCartes().add(cible.getCartes().get(nombreAleatoire));
                        cible.getCartes().remove(nombreAleatoire);
                    }
                }

                // pour tests
                if (POURTEST) {
                    System.out.println("");
                    afficheUnJoueur(joueurEnCours);
                    afficheUnJoueur(cible);
                    System.out.println("");
                }

                break;
            case "HYPNOSE":
                // Choix d'un joueur cible
                cible = this.selectionnerJoueur();
                // sélection d'une carte
                Carte uneCarte = this.selectionnerCarte(joueurEnCours.getCartes());
                // Retirer cette carte au joueur en cours et la mémoriser
                joueurEnCours.getCartes().remove(uneCarte);
                // Retirer 3 cartes du joueur cible que l'on donne au joueur en cours
                for (int i = 0; i < 3; i++) {
                    if (cible.getCartes().size() > 0) {
                        nombreAleatoire = rand.nextInt(cible.getCartes().size());
                        joueurEnCours.getCartes().add(cible.getCartes().get(nombreAleatoire));
                        cible.getCartes().remove(nombreAleatoire);
                    }
                }
                // Ajouter la carte mémorisé au joueur cible
                cible.getCartes().add(uneCarte);

                // pour tests
                if (POURTEST) {
                    System.out.println("");
                    afficheUnJoueur(joueurEnCours);
                    afficheUnJoueur(cible);
                    System.out.println("");
                }

                break;

            case "DIVINATION":
                // afficher les cartes des joueurs

                for (Joueur joueur : joueurs) {
                    if (joueur != joueurEnCours) {
                        System.out.println(joueur);
                    }
                }

                // pour tests
                if (POURTEST) {
                    System.out.println("");
                    afficheUnJoueur(joueurEnCours);
                    System.out.println("");
                }

                break;
            case "SOMMEIL-PROFOND":

                // Choix d'un joueur cible
                cible = this.selectionnerJoueur();
                cible.setSommeil(2);
                System.out.println("Le joueur " + cible.getNom() + " est endormi pendant 2 tours");

                // pour tests
                if (POURTEST) {
                    System.out.println("");
                    afficheUnJoueur(joueurEnCours);
                    afficheUnJoueur(cible);
                    System.out.println("");
                }

                break;

            default:
                break;
        }

    }

    public void afficherListeJoueur() {
        for (Joueur j : joueurs) {
            j.afficheJoueur();
        }

    }

    public void initJeu() {

        Carte corne = new Carte();
        corne.setType(TypeCarte.CORNE_DE_LICORNE);
        Carte bave = new Carte();
        bave.setType(TypeCarte.BAVE_DE_CRAPAUD);
        Carte mandragore = new Carte();
        mandragore.setType(TypeCarte.MANDRAGORE);
        Carte lapis = new Carte();
        lapis.setType(TypeCarte.LAPIS_LAZULI);
        Carte aile = new Carte();
        aile.setType(TypeCarte.AILE_DE_CHAUVE_SOURIS);

        sorts.add(new Sort("INVISIBILITE", new ArrayList() {
            {
                add(corne);
                add(bave);
            }
        },
                "le joueur prend 1 carte(au hasard) chez tous ses adversaires"));
        sorts.add(new Sort("PHILTRE D’AMOUR", new ArrayList() {
            {
                add(corne);
                add(mandragore);
            }
        },
                "le joueur de votre choix vous donne la moitié de ses cartes(au hasard). S’il ne possède qu’une carte il a perdu"));
        sorts.add(new Sort("HYPNOSE", new ArrayList() {
            {
                add(lapis);
                add(bave);
            }
        },
                "le joueur échange une carte de son choix contre trois cartes(au hasard) de son adversaire"));
        sorts.add(new Sort("DIVINATION", new ArrayList() {
            {
                add(lapis);
                add(aile);
            }
        },
                "le joueur peut voir les cartes de tous les autres joueurs"));
        sorts.add(new Sort("SOMMEIL-PROFOND", new ArrayList() {
            {

                add(mandragore);
                add(aile);
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
            // Raz Sommeil
            j.setSommeil(0);
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

    public Joueur selectionnerJoueur() {
        boolean encore = true;
        String saisieClav;
        Joueur[] tabJoueur = new Joueur[joueurs.size() - 1];
        int cpt = 0, choixJoueur;

        // au cas ou 
        if (joueurs.size() <= 1) {
            return null;
        }

        for (Joueur jj : joueurs) {
            if (jj != joueurEnCours) {
                tabJoueur[cpt] = jj;
            }
        }
        // Afficher les joueurs
        while (encore) {

            for (int i = 0; i < tabJoueur.length; i++) {
                System.out.println(i + 1 + ") " + tabJoueur[i].getNom());
            }
            System.out.print("Votre choix : ");
            saisieClav = new Scanner(System.in).next();
            if (!saisieClav.isEmpty()) {
                try {
                    choixJoueur = Integer.parseInt(saisieClav) - 1;
                } catch (NumberFormatException e) {
                    choixJoueur = -1;
                }
                if (choixJoueur >= 0 && choixJoueur < tabJoueur.length) {
                    return tabJoueur[choixJoueur];
                } else {
                    System.out.println("choix incorrect");
                }
            }
        }

        return null;

    }

    public Carte selectionnerCarte(ArrayList<Carte> cartes) {
        boolean encore = true;
        String saisieClav;
        Carte[] tabCarte = new Carte[cartes.size()];
        int cpt = 0, choixCarte;

        // au cas ou 
        if (cartes.size() < 1) {
            return null;
        }

        tabCarte = cartes.toArray(tabCarte);

        // Afficher les cartes
        while (encore) {

            for (int i = 0; i < tabCarte.length; i++) {
                System.out.println(i + 1 + ") " + tabCarte[i].getType());
            }
            System.out.print("Votre choix : ");
            saisieClav = new Scanner(System.in).next();
            if (!saisieClav.isEmpty()) {
                try {
                    choixCarte = Integer.parseInt(saisieClav) - 1;
                } catch (NumberFormatException e) {
                    choixCarte = -1;
                }
                if (choixCarte >= 0 && choixCarte < tabCarte.length) {
                    return tabCarte[choixCarte];
                }
            }
        }

        return null;

    }

    public void enleverCartes(String sortChoisi) {

        for (Sort s : sorts) {
            if (s.getNom().equals(sortChoisi)) {
                for (Carte ingr : s.getIngredients()) {
                    joueurEnCours.getCartes().remove(ingr);
                }
                break;
            }
        }
    }

    private void creationListSortPossible(ArrayList<String> sortPossible) {
        // constitution de la liste sortPossible du joueur en cour

        for (Sort s : sorts) {
            if (joueurEnCours.getCartes().containsAll(s.getIngredients())) {
                if (s.getNom().equals("HYPNOSE")) {
                    if (joueurEnCours.getCartes().size() > 2) {
                        sortPossible.add(s.getNom());
                    }
                } else {
                    sortPossible.add(s.getNom());
                }
            }
        }
    }

    private void gererJoueurEnSommeil() {
        // le joueur est endormi
        // passage d'un tour
        joueurEnCours.setSommeil(joueurEnCours.getSommeil() - 1);
    }

    private void afficheUnJoueur(Joueur j) {
        System.out.println("");
        System.out.println("------------------------");
        System.out.println(">>>> Joueur : " + j.getNom() + " <<<<<<");
        System.out.println(">  " + j.getCartes().size() + " Carte(s) :");
        System.out.println(">     " + j.getCartes());
        if (j.getSommeil() > 0) {
            // le joueur est endormi
            System.out.println(">    dort pendant encore " + (j.getSommeil()) + " tour(s) ");
        }

        // humain, IA
    }

    private boolean gagnantTrouve() {
        int nbJoueurActif = 0;
        Joueur gagnant = new Joueur();
        for (Joueur joueur : joueurs) {
            if (!joueur.getCartes().isEmpty()) {
                nbJoueurActif++;
                gagnant = joueur;
            }

        }
        if (nbJoueurActif == 1) {
            System.out.println("");
            System.out.println("....................vvvvvvvvvv...............");
            System.out.println("Bravo, " + gagnant.getNom() + " ! Vous avez gagné");
            System.out.println("....................^^^^^^^^^^...............");
            System.out.println("");
            joueurs.clear();
            return true;
        }
        return false;
    }
}

//____________________________________________
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
//                for (Carte ii : s.getIngredients()) {
//                    Iterator<Carte> it = joueurEnCours.getCartes().iterator();
//                    while (it.hasNext()) {
//                        if (it.next().getType().equals(ii)) {
//                            it.remove();
//                            break;
//                        }
//                    }
//                }
//    public void enleverCartes(String sortChoisi) {
//        Carte uneCarte = new Carte();
//
//        for (Sort s : sorts) {
//            if (s.nom.equals(sortChoisi)) {
//                for (TypeCarte ii : s.getIngredients()) {
//                    Iterator<Carte> it = joueurEnCours.getCartes().iterator();
//                    while (it.hasNext()) {
//                        if (it.next().getType().equals(ii)) {
//                            it.remove();
//                            break;
//                        }
//                    }
//                }
//            }
//        }
//    }
//    private void creationListSortPossibleOld(ArrayList<String> sortPossible) {
//        // Listes temporaire (pour realiser comparaison containsAll)
//        ArrayList<String> tmpStr1 = new ArrayList<>();
//        ArrayList<String> tmpStr2 = new ArrayList<>();
//        for (Carte xx : joueurEnCours.getCartes()) {
//            tmpStr1.add(xx.toString());
//        }
//        for (Sort s : sorts) {
//            tmpStr2.clear();
//            for (TypeCarte ii : s.getIngredients()) {
//                tmpStr2.add(ii.toString());
//            }
//            if (tmpStr1.containsAll(tmpStr2)) {
//                sortPossible.add(s.nom);
//            }
//        }
//    }
