package lieux;

public class CompAttente implements Comparateur {
	public int compare(Trajet t1, Trajet t2) {
		Heure attenteT1 = new Heure();
		Heure attenteT2 = new Heure();

		for (Etape e : t1.sesEtapes()) {
			try {
				attenteT1 = attenteT1.add(e.attente());
			} catch (ErreurHeure e1) {
				return -2;
			} catch (ErreurTrajet e1) {
				return -2;
			}
		}

		for (Etape e : t2.sesEtapes()) {
			try {
				attenteT2 = attenteT2.add(e.attente());
			} catch (ErreurHeure e1) {
				return -2;
			} catch (ErreurTrajet e1) {
				return -2;
			}
		}

		return attenteT1.compareTo(attenteT2);
	}
}
