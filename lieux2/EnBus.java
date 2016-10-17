package lieux2;

public class EnBus extends MoyenTransport {
	private LigneBus saLigne;

	// permet de savoir si une instance de la classe a deja ete cree
	private static volatile EnBus instance = null;
	 

	/**
     * Méthode permettant de renvoyer une instance de la classe EnBus
     * @return Retourne l'instance du singleton.
     */
	public final static EnBus getInstance(LigneBus l) {

    	if (EnBus.instance == null) {
           synchronized(EnBus.class) {
             if (EnBus.instance == null) {
               EnBus.instance = new EnBus(l);
             }
           }
        }
        return EnBus.instance;
    }
		
	private EnBus(LigneBus l) {
		saLigne = l;
	}

	public String toString() {
		return "Ligne Bus [" + saLigne.nom() + "]";
	}

	// TODO
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
}
