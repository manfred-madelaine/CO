package lieux;

public class APieds extends MoyenTransport {

	// Pas vraiment utile. On pourrait en faire un singleton.
	public APieds() {
	}

	public String toString() {
		return "A Pieds";
	}

	// TODO
	public boolean estPossible(Lieu l1, Lieu l2, Heure dep) {
		/* estVoisin test si 2 lieux sont les memes ou s'ils sont voisins geographiques.*/
		return l1.estVoisin(l2);
	}

	// TODO
	public Heure attente(Lieu l1, Lieu l2, Heure dep) throws ErreurTrajet {
		if (estPossible(l1, l2, dep)) {
			/*
			 * Le temps d'attente avant de pouvoir faire un trajet a  pieds est
			 * toujours nul. On utilise donc le constructeur sans arguments de
			 * Heure, qui cree une heure initialisee a  0h et 0m.
			 */
			return new Heure();
		}
		else throw new ErreurTrajet("Trajet impossible.");
	}

	// duree du voyage de l1 a l2, hors temps d'attente, a l'heure dep selon
	// ce moyen de transport
	// TODO
	public Heure duree(Lieu l1, Lieu l2, Heure dep) throws ErreurTrajet {
		if(estPossible(l1, l2, dep)){
			return l1.distance(l2);
		}
		else throw new ErreurTrajet("Trajet impossible.");
	}

}
