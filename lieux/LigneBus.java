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
	 * avant l'arret d'arrivee dans la liste (ordonnee) des arrets car la ligne
	 * est orientee ET si l'heure actuelle n'est pas superieure a l'heure de fin
	 * d
	 * 
	 * @param a1
	 *            : arret de depart
	 * @param a2
	 *            : arret d'arrivee
	 * @param dep
	 *            : heure de depart
	 * @return true si on peut voyager sur cette ligne, false sinon
	 */
	public boolean estPossible(Arret a1, Arret a2, Heure dep) {
		int indexA1 = sesArrets.indexOf(a1);
		int indexA2 = sesArrets.indexOf(a2);

		/*
		 * On verifie que les 2 arrets appartiennent a  la ligne et que a1 est
		 * place avant a2.
		 */
		if (indexA1 != -1 && indexA2 != -1 && indexA1 < indexA2) {
			/*
			 * On verifie qu'il est possible d'aller de a1 a  a2 le meme jour en
			 * partant a  l'heure dep on doit avoir dep < heure du dernier
			 * depart de la ligne + temps de trajet du debut de la ligne
			 * jusqu'a  a1
			 */

			Heure hTest = sesDeparts[sesDeparts.length - 1];
			try {
				try {
					hTest = hTest.add(dureeEnBus(a1, a2));
				} catch (ErreurTrajet e) {
					return false;
				}
			} catch (ErreurHeure e) {
				return false;
			}

			if (dep.compareTo(hTest) <= 0)
				return true;
		}

		return false;
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
		/*
		 * On commence par calculer l'heure a laquelle passe le dernier bus de
		 * la journee a cet arret.
		 */
		Heure dernier = sesDeparts[sesDeparts.length - 1];
		try {
			dernier = dernier.add(dureeEnBus(sesArrets.get(0), a));
		} catch (ErreurHeure e) {
			throw new ErreurTrajet("Probleme avec les horaires");
		}

		/*
		 * Si l'heure h est superieure a l'heure de passage du dernier bus, il
		 * est impossible de faire le trajet.
		 */
		if (h.compareTo(dernier) == 1) {
			throw new ErreurTrajet("Trajet impossible.");
		} else {
			/* Sinon on determine l'heure du depart du bus le plus proche. */
			int i = 0;
			while (sesDeparts[i].compareTo(h) == -1)
				i++;

			System.out.println("i = " + i);
			Heure arrive = sesDeparts[i];
			try {
				arrive = arrive.add(dureeEnBus(sesArrets.get(0), a));
			} catch (ErreurHeure e) {
				throw new ErreurTrajet("Probleme avec les horaires");
			}

			/*
			 * Puis on fait la difference entre l'heure a laquelle le bus arrive
			 * et l'heure h.
			 */
			try {
				return h.delaiAvant(arrive);
			} catch (ErreurHeure e) {
				throw new ErreurTrajet("Probleme avec les horaires.");
			}
		}
	}

	// On suppose que la duree de transport entre deux arrets ne depend pas
	// de l'heure et qu'on n'arrive jamais le lendemain du jour de depart.
	/**
	 * La duree entre 2 arrets bien orientes = somme des temps de trajet de
	 * toutes les paires d'arrets consecutifs compris entre l'arret 1 et 2
	 * 
	 * Si ces deux derniers se suivent directement sur la ligne alors la duree =
	 * temps de trajet entre ces 2 arrets
	 * 
	 * @param a1
	 *            : arret de depart
	 * @param a2
	 *            : arret d'arrivee
	 * @return duree de l'etape
	 * @throws ErreurTrajet
	 */
	public Heure dureeEnBus(Arret a1, Arret a2) throws ErreurTrajet {
		Heure res = new Heure();

		int indexA1 = sesArrets.indexOf(a1);
		int indexA2 = sesArrets.indexOf(a2);

		try {
			for (int i = indexA1; i < indexA2; i++)
				res = res.add(sesTemps[i]);
		} catch (ErreurHeure e) {
			throw new ErreurTrajet("Probleme avec les horaires");
		}

		return res;
	}
}
