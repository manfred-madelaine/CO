package lieux;

import java.util.ArrayList;

public class TestLieux {
	static Batiment B620, B640, Supelec, LeGuichet, OrsayVille;
	static Arret AOrsay, AGuichet, APlateau, AChateau, ASupelec, AAlgorithmes;
	static LigneBus L1, L2;

	public static void creerLieux() {
		B620 = new Batiment("MdI");
		B640 = new Batiment("PUIO");
		Supelec = new Batiment("Supelec");
		LeGuichet = new Batiment("Gare Guichet");
		OrsayVille = new Batiment("Gare Orsay");

		AOrsay = new Arret("Arret Gare Orsay");
		AGuichet = new Arret("Arret Gare Guichet");
		APlateau = new Arret("Arret MdI-PUIO");
		AChateau = new Arret("Chateau");
		ASupelec = new Arret("Arret CNEF");
		AAlgorithmes = new Arret("Arret Les Algorithmes");

		Arret[] tabArret1 = { AOrsay, AChateau, APlateau, ASupelec, AAlgorithmes };

		Arret[] tabArret2 = { AGuichet, APlateau, ASupelec };

		L1 = new LigneBus("Ligne 1");
		L1.addArrets(tabArret1);
		L2 = new LigneBus("Ligne 2");
		L2.addArrets(tabArret2);
	}

	static void setHoraires() throws ErreurTrajet, ErreurHeure {
		/*
		 * duree entre les arrets respectifs sur chaque ligne et heures de
		 * depart de chaque tete de ligne. Il doit y avoir un horaire de moins
		 * que d'arrets ???
		 */
		Heure[] hA1 = { new Heure(0, 3), new Heure(0, 5), new Heure(0, 7), new Heure(0, 9) };
		Heure[] dA1 = { new Heure(9, 0), new Heure(9, 30), new Heure(10, 30), new Heure(14, 0) };

		Heure[] hA2 = { new Heure(0, 8), new Heure(0, 3) };
		Heure[] dA2 = { new Heure(9, 0), new Heure(10, 0), new Heure(11, 0) };
		L1.ajoutHoraires(hA1, dA1);
		L2.ajoutHoraires(hA2, dA2);
	}

	/* Ajoute des distances a pieds */
	static void setDistances() throws ErreurTrajet, ErreurHeure {
		B620.addVoisin(APlateau, new Heure(0, 2));
		B640.addVoisin(APlateau, new Heure(0, 5));
		AOrsay.addVoisin(OrsayVille, new Heure(0, 1));
		/* on peut rejoindre l'arret Chateau a pieds mais c'est long */
		AOrsay.addVoisin(AChateau, new Heure(0, 15));
		AChateau.addVoisin(APlateau, new Heure(0, 30));
		AGuichet.addVoisin(LeGuichet, new Heure(0, 1));
		APlateau.addVoisin(Supelec, new Heure(0, 20));
		APlateau.addVoisin(ASupelec, new Heure(0, 10));
		/*
		 * La, le chemin a pied fait des detours bizarres et on n'a pas le droit
		 * de longer a pied la ligne de bus !
		 */
		ASupelec.addVoisin(Supelec, new Heure(0, 7));

	}

	static void addRaccourci() throws ErreurTrajet, ErreurHeure {
		B620.addVoisin(B640, new Heure(0, 4));
	}

	public static void testeTrajet(Trajet t) throws ErreurTrajet, ErreurHeure {
		if (t.estCoherent()) {
			t.liste();
			System.out.println("Heure d'arrivee pour " + t.nom() + ": " + t.hArrivee().toString());
			System.out.println();
		} else {
			System.out.println("Trajet " + t.nom() + " est incoherent !");
		}
	}

	public static Trajet mkTrajet0a() throws ErreurTrajet, ErreurHeure {
		/* un trajet elementaire a pieds */
		ArrayList<Etape> res = new ArrayList<Etape>();
		Heure dep = new Heure(8, 50);
		res.add(new Etape(OrsayVille, AOrsay, new APieds(), dep));
		return new Trajet("t0a", OrsayVille, AOrsay, dep, res);
	}

	public static Trajet mkTrajet0b() throws ErreurTrajet, ErreurHeure {
		/* un trajet en Bus */
		ArrayList<Etape> res = new ArrayList<Etape>();
		Heure dep = new Heure(8, 50);
		res.add(new Etape(AGuichet, ASupelec, new EnBus(L2), dep));
		return new Trajet("t0b", AGuichet, ASupelec, dep, res);
	}

	public static Trajet mkTrajet1() throws ErreurTrajet, ErreurHeure {
		ArrayList<Etape> res = new ArrayList<Etape>();
		Heure dep = new Heure(8, 50);
		res.add(new Etape(OrsayVille, AOrsay, new APieds(), dep));
		res.add(new Etape(AOrsay, APlateau, new EnBus(L1), new Heure(9, 0)));
		res.add(new Etape(APlateau, B640, new APieds(), new Heure(9, 8)));
		res.add(new Etape(B640, B620, new APieds(), new Heure(9, 13)));
		return new Trajet("t1", OrsayVille, B620, dep, res);
	}

	// Le meme mais en flanant a differents arrets, ce qui fait qu'on n'arrive
	// pas
	// le plus tot possible. On n'est pas, oblige de n'avoir que des egalites
	// horaires entre deux etapes.
	public static Trajet mkTrajet1a() throws ErreurTrajet, ErreurHeure {
		ArrayList<Etape> res = new ArrayList<Etape>();
		Heure dep = new Heure(8, 50);
		res.add(new Etape(OrsayVille, AOrsay, new APieds(), dep));
		res.add(new Etape(AOrsay, APlateau, new EnBus(L1), new Heure(8, 53)));
		res.add(new Etape(APlateau, B640, new APieds(), new Heure(9, 12)));
		res.add(new Etape(B640, B620, new APieds(), new Heure(9, 17)));
		return new Trajet("t1a", OrsayVille, B620, dep, res);
	}

	public static Trajet mkTrajet1b() throws ErreurTrajet, ErreurHeure {
		ArrayList<Etape> res = new ArrayList<Etape>();
		Heure dep = new Heure(8, 50);
		res.add(new Etape(OrsayVille, AOrsay, new APieds(), dep));
		res.add(new Etape(AOrsay, APlateau, new EnBus(L1), new Heure(9, 0)));
		res.add(new Etape(APlateau, B620, new APieds(), new Heure(9, 8)));
		return new Trajet("t1b", OrsayVille, B620, dep, res);
	}

	public static Trajet mkTrajet2() throws ErreurTrajet, ErreurHeure {
		ArrayList<Etape> res = new ArrayList<Etape>();
		Lieu l1 = OrsayVille;
		Lieu l2 = OrsayVille;
		Heure dep = new Heure(8, 50);
		return new Trajet("t2", l1, l2, dep, res);
	}

	public static Trajet mkTrajet3() throws ErreurTrajet, ErreurHeure {
		ArrayList<Etape> res = new ArrayList<Etape>();
		Lieu l1 = LeGuichet;
		Lieu l2 = AAlgorithmes;
		Heure dep = new Heure(9, 0);
		Heure dep2 = new Heure(9, 11);
		res.add(new Etape(LeGuichet, AGuichet, new APieds(), dep));
		res.add(new Etape(AGuichet, ASupelec, new EnBus(L2), dep2));
		res.add(new Etape(ASupelec, l2, new EnBus(L1), new Heure(10, 11)));
		return new Trajet("t3", l1, l2, dep, res);
	}

	public static Trajet mkTrajet4() throws ErreurTrajet, ErreurHeure {
		Heure dep = new Heure(8, 40);
		ArrayList<Etape> res = new ArrayList<Etape>();
		res.add(new Etape(OrsayVille, AOrsay, new APieds(), dep));
		res.add(new Etape(AOrsay, ASupelec, new EnBus(L1), new Heure(8, 45)));
		return new Trajet("t4", OrsayVille, ASupelec, dep, res);
	}

	public static Trajet mkTrajet4b() throws ErreurTrajet, ErreurHeure {
		Heure dep = new Heure(8, 40);
		ArrayList<Etape> res = new ArrayList<Etape>();
		res.add(new Etape(OrsayVille, AOrsay, new APieds(), dep));
		res.add(new Etape(AOrsay, APlateau, new EnBus(L1), new Heure(8, 45)));
		res.add(new Etape(APlateau, ASupelec, new EnBus(L2), new Heure(9, 8)));
		return new Trajet("t4b", OrsayVille, ASupelec, dep, res);
	}

	// Il y a bcp d'etapes mais un seul mode de transport.
	public static Trajet mkTrajet4c() throws ErreurTrajet, ErreurHeure {
		Heure dep = new Heure(8, 40);
		ArrayList<Etape> res = new ArrayList<Etape>();
		res.add(new Etape(OrsayVille, AOrsay, new APieds(), dep));
		res.add(new Etape(AOrsay, AChateau, new APieds(), new Heure(8, 41)));
		res.add(new Etape(AChateau, APlateau, new APieds(), new Heure(8, 56)));
		res.add(new Etape(APlateau, ASupelec, new APieds(), new Heure(9, 26)));
		return new Trajet("t4c", OrsayVille, ASupelec, dep, res);
	}

	public static void main(String[] args) throws ErreurTrajet, ErreurHeure {
		creerLieux();
		setDistances();
		addRaccourci();
		setHoraires();
		Trajet t0a = mkTrajet0a();
		System.out.println("TEST 0a\n");
		testeTrajet(t0a);
		
		Trajet t0b = mkTrajet0b();
		System.out.println("\n TEST 0b\n");
		testeTrajet(t0b);
		Trajet t1 = mkTrajet1();
		Trajet t1a = mkTrajet1a();
		Trajet t1b = mkTrajet1b();
		Trajet t2 = mkTrajet2();
		Trajet t3 = mkTrajet3();
		System.out.println("\n TEST 1\n");
		testeTrajet(t1);
		System.out.println("\n TEST 1a\n");
		testeTrajet(t1a);
		System.out.println("\n TEST 1b\n");
		testeTrajet(t1b);
		System.out.println("\n TEST 2\n");
		testeTrajet(t2);
		System.out.println("\n TEST 3\n");
		testeTrajet(t3);
	}
}
