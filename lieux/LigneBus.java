package lieux;

import java.util.ArrayList;

public class LigneBus {
	protected String nomLigne;
	protected ArrayList<Arret> sesArrets;
	protected Heure[] sesDeparts;
	protected Heure[] sesTemps;

	public LigneBus(String nom) {
		nomLigne = nom;
	}

	public void addArrets(Arret[] lesArrets) {
		sesArrets = new ArrayList<Arret>();
		for (Arret a : lesArrets) {
			a.ajoutLigne(this);
			sesArrets.add(a);
		}
	}

	public String nom() {
		return nomLigne;
	}

	/*
	 * pour simplifier on suppose que les durees de deplacement sont
	 * independantes de l'heure. Pour definir les horaires il suffit de donner
	 * les heures de depart ainsi que les durees entre 2 arrets successifs. La
	 * dimension du tableau des horaires doit etre egale au nombre d'arrets de
	 * la ligne moins 1.
	 * 
	 * Attention: une ligne de bus est orientee. Les horaires sont donnes dans
	 * le sens de parcours. Si on veut aussi pouvoir parcourir une ligne dans
	 * l'autre sens, il faut creer une deuxieme ligne ! On suppose enfin que les
	 * lignes ne sont pas circulaires,
	 */
	public void ajoutHoraires(Heure[] horaire, Heure[] hdepart) throws ErreurTrajet {
		if (horaire.length != sesArrets.size() - 1) {
			throw new ErreurTrajet("Horaire mal formatte");
		}
		sesDeparts = hdepart;
		sesTemps = horaire;
	}

	// TODO
	/**
	 * on peut voyager sur une ligne uniquement si l'arret de depart est situe
	 * avant l'arret d'arrivee dans la liste (ordonnee) des arrets car la ligne est orientee
	 * ET
	 * si l'heure actuelle n'est pas superieure a l'heure de fin d
	 * @param a1 : arret de depart
	 * @param a2 : arret d'arrivee
	 * @param dep : heure de depart
	 * @return true si on peut voyager sur cette ligne, false sinon
	 */
	public boolean estPossible(Arret a1, Arret a2, Heure dep) {
		return (sesArrets.indexOf(a1) < sesArrets.indexOf(a2)) 
				&& (dep.compareTo(sesDeparts[sesDeparts.length-1]) < 0);
	}

	/**
	 * temps d'attente = delai d'arrivee du prochain bus
	 * 
	 * @param a
	 *            : arret ou on prendre le bus
	 * @param h
	 *            : heure actuelle
	 * @return temps d'attente
	 * @throws ErreurTrajet
	 */
	public Heure attente(Arret a, Heure h) throws ErreurTrajet {
		Heure t;
		// on trouve le prochain bus
		int i = 0;
		
		// tant que l'horaire de depart du bus est anterieur à l'heure actuelle (le bus est deja passe) on passe à l'horaire suivante
		while (h.compareTo(sesDeparts[i]) > 0) {
			i++;
		}
		
		//delai d'attente jusqu'au prochain passage
		try {
			t = h.delaiAvant(sesDeparts[i]);
		} catch (ErreurHeure e) {
			throw new ErreurTrajet("Trajet impossible, le trajet doit se faire dans la journee");
		}
		
		return t;
	}

	// On suppose que la duree de transport entre deux arrets ne depend pas
	// de l'heure et qu'on n'arrive jamais le lendemain du jour de depart.
	/**
	 * La duree entre 2 arrets bien orientes = somme des temps de trajet de 
	 * toutes les paires d'arrets consecutifs compris entre l'arret 1 et 2
	 * 
	 * Si ces deux derniers se suivent directement sur la ligne alors
	 * la duree = temps de trajet entre ces 2 arrets
	 * 
	 * @param a1 : arret de depart
	 * @param a2 : arret d'arrivee
	 * @return duree de l'etape
	 * @throws ErreurTrajet
	 */
	public Heure dureeEnBus(Arret a1, Arret a2) throws ErreurTrajet {
		Heure h = new Heure();
		int i = sesArrets.indexOf(a1);
		//tant qu'on est entre les arrets a1 et a2 
		while(i < sesArrets.indexOf(a2)){
			try {
				h = h.add(sesTemps[i]);
			} catch (ErreurHeure e) {
				throw new ErreurTrajet("Trajet impossible, le trajet doit se faire dans la journee");
			}
			i++;
		}
		return h;
	}
}
