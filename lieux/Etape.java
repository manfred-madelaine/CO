package lieux;

public class Etape {
	private Lieu dep, arr;
	private MoyenTransport moyen;
	private Heure hdep;

	/*
	 * Pour l'instant on autorise la creation d'une etape meme si elle n'utilise
	 * pas un moyen de transport possible entre ces lieux a cette heure.
	 */
	/**
	 * 
	 * @param dep
	 *            : lieu de depart
	 * @param arr
	 *            : lieu d'arrivee
	 * @param m
	 *            : moyen de transport
	 * @param h
	 *            : heure de depart
	 */
	public Etape(Lieu d, Lieu a, MoyenTransport m, Heure h) {
		dep = d;
		arr = a;
		moyen = m;
		hdep = h;
	}

	public void liste() {
		/* manque encore l'horaire de depart + le delai d'attente */
		System.out.println("De " + dep.nom() + " a " + arr.nom() + ": " + moyen.toString() + " [depart: " + hdep + "]");
	}

	public MoyenTransport moyen() {
		return moyen;
	}

	public Lieu depart() {
		return dep;
	}

	public Lieu arrivee() {
		return arr;
	}

	public Heure hDepart() {
		return hdep;
	}

	// TODO
	/**
	 * heure d'arrivee = heure de depart + temps d'attente selon le M de T + duree de trajet du M de T
	 * 
	 * @return heure d'arrivee
	 * @throws ErreurTrajet
	 */
	public Heure hArrivee() throws ErreurTrajet {
		if (estPossible()) {
			Heure h = hdep;
			System.out.println("au depart: "+h.toString()+", tps d'attente :" + moyen.attente(dep, arr, h) + ", duree etape :" + moyen.duree(dep, arr, h));
			
			try {
				h = h.add( moyen.attente(dep, arr, h) );
				h = h.add( moyen.duree(dep, arr, h) );
			} catch (ErreurHeure e) {
				throw new ErreurTrajet("Etape impossible, l'etape doit se faire dans la journee");
			}
			return h;
		}
		else throw new ErreurTrajet("Etape impossible.");
	}

	// TODO
	/**
	 * On ne peut se rendre de depart a arrivee, que si le moyen de transport l'autorise
	 * 
	 * @return true si cette etape est autorisee par moyen
	 * 			false sinon
	 */
	public boolean estPossible() {
		return moyen.estPossible(dep, arr, hdep);
	}

	/**
	 * La duree de l'etape correspond a la duree de transport du M de T
	 * 
	 * @return heure d'attente due au moyen de transport
	 * @throws ErreurTrajet
	 */
	public Heure duree() throws ErreurTrajet {
		if (estPossible()) {
			return moyen.duree(dep, arr, hdep);
		}
		else throw new ErreurTrajet("Etape impossible.");
	}

	/**
	 * On attend le temps d'attente lié au moyen de transport
	 * 
	 * @return heure d'attente due au moyen de transport
	 * @throws ErreurTrajet
	 */
	public Heure attente() throws ErreurTrajet {
		if (estPossible()) {
			return moyen.attente(dep, arr, hdep);
		}
		else throw new ErreurTrajet("Etape impossible.");
	}
}
