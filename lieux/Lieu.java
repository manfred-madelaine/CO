package lieux;

import java.util.HashMap;

public abstract class Lieu {
	protected String nom;
	private HashMap<Lieu, Heure> lesDistances;

	protected Lieu(String nom) {
		this.nom = nom;
		lesDistances = new HashMap<Lieu, Heure>();
	}

	public String nom() {
		return nom;
	}

	/*
	 * pour simplifier on considere que les distances ne dependent pas de la
	 * direction.
	 */
	public void addVoisin(Lieu lieu, Heure dist) throws ErreurTrajet {
		if (this.equals(lieu)) {
			throw new ErreurTrajet("Distance d'un Lieu a lui-meme");
		}
		lesDistances.put(lieu, dist);
		lieu.lesDistances.put(this, dist);
	}

	/*
	 * Distance a pieds entre les deux lieux qui doivent etre voisins. Un long
	 * trajet a pieds devra etre decoupe en une succession de trajets
	 * elementaires entre lieux voisins
	 */
	public Heure distance(Lieu l) throws ErreurTrajet {
		if (this.equals(l)) {
			return new Heure();
		} else {
			Heure i = lesDistances.get(l);
			if (i == null) {
				throw new ErreurTrajet("distance indefinie");
			} else {
				return i;
			}
		}
	}

	public boolean estVoisin(Lieu l) {
		return this.equals(l) || lesDistances.get(l) != null;
	}
}
