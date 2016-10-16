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
	 * Un trajet est cherent si chacune des etapes est coherente et s'enchainent correctement:
	 * le lieu de depart de l'etape i = le lieu d'arrivee de l'etape i-1
	 * l'heure de depart de l'etape i >= l'heure d'arrivee de l'etape i-1
	 * 
	 * @return true si toutes les etapes du trajet sont coherentes, false sinon
	 */
	public boolean estCoherent() {		
		for (int i = 0; i < sesEtapes.size(); i++){
			if (sesEtapes.get(i).estPossible()){
				// si le depart correspond a l'arrivee de l'etape precedente 
				// et l'heure de depart >= heure d'arrivee de la precedente
				try {
					if(i>0 && sesEtapes.get(i).depart().equals( sesEtapes.get(i-1).arrivee() )
							&& sesEtapes.get(i).hDepart().compareTo( sesEtapes.get(i-1).hArrivee() ) > 0 ){
						System.out.println("OK");
						continue;
					}
					else if (i!= 0){
						return false;
					}
				} catch (ErreurTrajet e) {
					
				}
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
		Trajet meilleur = null;
		Trajet temp = null;
		int resComp = 0;

		Iterator<Trajet> itr = col.iterator();
		meilleur = itr.next();
		if (!meilleur.estCoherent())
			throw new ErreurTrajet();

		while (itr.hasNext()) {
			temp = itr.next();
			
			if (!temp.estCoherent())
				throw new ErreurTrajet();
			
			resComp = comp.compare(meilleur, temp);
			if (resComp == 1)
				meilleur = temp;
			else if(resComp == -2)
				throw new ErreurTrajet();
		}

		return meilleur;
	}

}
