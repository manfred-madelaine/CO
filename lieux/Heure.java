package lieux;

/**
 * Heure: une version tres simplifie d'une classe qui gere des heures et des
 * minutes. Sert a la fois pour indiquer des heures et des durees !
 */

public class Heure implements Comparable<Heure> {
	private int heure;
	private int minutes;

	public Heure(int h, int m) throws ErreurHeure {
		if (h < 0 || h > 23 || m < 0 || m > 59) {
			throw new ErreurHeure("Heure mal formee");
		} else {
			heure = h;
			minutes = m;
		}
	}

	// L'avantage de ce constructeur est qu'il ne leve pas d'exception.
	public Heure() {
		heure = 0;
		minutes = 0;
	}

	public String toString() {
		return "[" + heure + ":" + minutes + "]";
	}


	public Heure add(Heure h) throws ErreurHeure {
		int h2 = heure, m2 = minutes;
		h2 += h.heure;
		m2 += h.minutes;
		if (m2 > 59) {
			h2++;
			m2 -= 60;
		}
		return new Heure(h2, m2);
	}

	public int compareTo(Heure h) {
		if (heure < h.heure)
			return -1;
		else if (heure > h.heure)
			return +1;
		else if (minutes < h.minutes)
			return -1;
		else if (minutes > h.minutes)
			return 1;
		else
			return 0;
	}

	public boolean equals(Object arg) {
		if (arg == null) {
			return false;
		}
		if (arg == this) {
			return true;
		}
		if (arg instanceof Heure) {
			Heure h = (Heure) arg;
			return h.heure == heure && h.minutes == minutes;
		} else {
			return false;
		}
	}

	public int hashCode() {
		return 37 * heure + 53 * minutes;
	}

	/* retourne le delai avant l'heure h (supposee posterieure ! ) */
	public Heure delaiAvant(Heure h) throws ErreurHeure {
		// si l'heure actuelle est plus grande que h
		if (compareTo(h) > 0) {
			throw new ErreurHeure("Appel incorrect a delaiAvant");
		}
		if (heure == h.heure) {
			return new Heure(0, h.minutes - minutes);
		} else {
			if (minutes <= h.minutes) {
				return new Heure(h.heure - heure, h.minutes - minutes);
			} else {
				return new Heure(h.heure - heure - 1, h.minutes + (60 - minutes));
			}
		}
	}
}
