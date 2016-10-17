package lieux2;

public abstract class MoyenTransport {

	public abstract String toString();

	// temps d'attente a l'heure dep, avant de pouvoir aller de l1 a l2 avec
	// ce moyen de transort
	public abstract Heure attente(Lieu l1, Lieu l2, Heure dep) throws ErreurTrajet;

	// duree du voyage de l1 a l2, hors temps d'attente, a l'heure dep selon
	// ce moyen de transport
	public abstract Heure duree(Lieu l1, Lieu l2, Heure dep) throws ErreurTrajet;

	public abstract boolean estPossible(Lieu l1, Lieu l2, Heure dep);

	public Heure hArrivee(Lieu l1, Lieu l2, Heure dep) throws ErreurTrajet {
		try {
			Heure res = dep;
			// attente le cas echeant avant de pouvoir partir avec ce moyen
			res.add(this.attente(l1, l2, dep));
			// plus duree du trajet avec ce moyen.
			res.add(this.duree(l1, l2, dep));
			return res;
		} catch (ErreurHeure e) {
			throw new ErreurTrajet("Probleme avec les horaires");
		}
	}
}
