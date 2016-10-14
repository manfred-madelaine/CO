package lieux;

import java.util.ArrayList;
import java.util.Collection;

public class Trajet {
	private String nom;
	private Lieu depart;
	private Lieu arrivee;
	private Heure dateDepart;
	private ArrayList<Etape> sesEtapes;

	/**
	 * 
	 * @param n
	 *            : nom du trajet
	 * @param dep
	 *            : lieu de depart
	 * @param arr
	 *            : lieu d'arrivee
	 * @param d
	 *            : heure de depart
	 * @param etapes
	 *            : liste des etapes
	 */
	public Trajet(String n, Lieu dep, Lieu arr, Heure d, ArrayList<Etape> etapes) {
		nom = n;
		depart = dep;
		arrivee = arr;
		dateDepart = d;
		sesEtapes = etapes;
	}

	public String nom() {
		return nom;
	}

	public Lieu depart() {
		return depart;
	}

	public Lieu arrivee() {
		return arrivee;
	}

	public void liste() {
		System.out.println(
				"Trajet " + nom + " de " + depart.nom() + " a " + arrivee.nom() + ", depart a " + dateDepart + " :");
		for (Etape e : sesEtapes)
			e.liste();
	}

	// TODO modifié
	/**
	 * 
	 * @return true si toutes les etapes du trajet sont coherentes, false sinon
	 */
	public boolean estCoherent() {
		for (Etape e : sesEtapes) {
			if (!(e.estPossible())) {
				return false;
			}
		}
		return true;
	}

	// TODO
	/**
	 * heure d'arrivee = heure de depart + delai de chaque etape
	 * 
	 * @return l'heure d'arrivee de tout le trajet
	 * @throws ErreurTrajet
	 */
	public Heure hArrivee() throws ErreurTrajet {
		Heure h = dateDepart;
		int i=0;
		for(Etape e: sesEtapes){
			try {

				System.out.println("\n\tEtape "+i+ ": h = "+h);
				h = h.add( h.delaiAvant(e.hArrivee()) );
			} catch (ErreurHeure e1) {
				throw new ErreurTrajet("Trajet impossible, le trajet doit se faire dans la journee");
			}
			i++;
		}		
		return h;
	}

	public Heure duree() throws ErreurTrajet {
		Heure h = new Heure();
		for(Etape e: sesEtapes){
			try {
				h = h.add( e.duree() );
			} catch (ErreurHeure e1) {
				throw new ErreurTrajet("Trajet impossible, le trajet doit se faire dans la journee");
			}
		}		
		return h;
	}

	public Heure attente() throws ErreurTrajet {
		Heure h = new Heure();
		for(Etape e: sesEtapes){
			try {
				h = h.add( e.attente() );
			} catch (ErreurHeure e1) {
				throw new ErreurTrajet("Trajet impossible, le trajet doit se faire dans la journee");
			}
		}		
		return h;
	}

	public int nbChgt() throws ErreurTrajet {
		return sesEtapes.size();
	}

	public static Trajet meilleur(Collection<Trajet> col, Comparateur comp) throws ErreurTrajet {
		return null;
	}

}
