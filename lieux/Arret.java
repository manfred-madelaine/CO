package lieux;

import java.util.ArrayList;

public class Arret extends Lieu {
    protected ArrayList<LigneBus> sesLignes;

    public Arret(String nom) {
	super(nom);
	sesLignes = new ArrayList<LigneBus>();
    }

    public void ajoutLigne(LigneBus l) {
	sesLignes.add(l);
    }
}
