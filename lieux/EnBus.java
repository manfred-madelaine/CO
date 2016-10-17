package lieux;

public class EnBus extends MoyenTransport {
	private LigneBus saLigne;

	public EnBus(LigneBus l) {
		saLigne = l;
	}

	public String toString() {
		return "Ligne Bus [" + saLigne.nom() + "]";
	}

	public boolean estPossible(Lieu l1, Lieu l2, Heure dep) {
		// si les lieux sont compris dans la liste d'arrets de la ligne de bus
		if (saLigne.sesArrets.contains(l1) && saLigne.sesArrets.contains(l2)) {
			// on recupere les indices de chacun des arrets
			int i1 = saLigne.sesArrets.indexOf(l1);
			int i2 = saLigne.sesArrets.indexOf(l2);

			return saLigne.estPossible(saLigne.sesArrets.get(i1), saLigne.sesArrets.get(i2), dep);
		}
		return false;
	}

	public Heure attente(Lieu l1, Lieu l2, Heure dep) throws ErreurTrajet {
		if (estPossible(l1, l2, dep)) {
			int i1 = saLigne.sesArrets.indexOf(l1);
			return saLigne.attente(saLigne.sesArrets.get(i1), dep);
		} else
			throw new ErreurTrajet("Trajet impossible.");
	}

	public Heure duree(Lieu l1, Lieu l2, Heure dep) throws ErreurTrajet {
		if (estPossible(l1, l2, dep)) {
			int i1 = saLigne.sesArrets.indexOf(l1);
			int i2 = saLigne.sesArrets.indexOf(l2);

			return saLigne.dureeEnBus(saLigne.sesArrets.get(i1), saLigne.sesArrets.get(i2));
		} else
			throw new ErreurTrajet("Trajet impossible.");
	}

	/*
	 * Si cette methode est appelee c'est qu'on change de ligne de bus, cela
	 * compte donc comme un changement.
	 */
	public boolean estChangement(MoyenTransport m) {
		return true;
	}

	/*
	 * Cette methode sera appelee si on passe du moyen de transport APieds au
	 * moyen EnBus (true = changement).
	 */
	public boolean estAPieds() {
		return true;
	}
}
