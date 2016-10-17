package lieux2;

public class CompTemps implements Comparateur {
    public int compare(Trajet t1, Trajet t2) {
    	Heure dureeT1 = new Heure();
    	Heure dureeT2 = new Heure();
    	
    	try{
    		dureeT1 = dureeT1.add(t1.sesEtapes().get(0).hDepart().delaiAvant(t1.sesEtapes().get(t1.sesEtapes().size() - 1).hArrivee()));
    	} catch(ErreurHeure e) {
    		return -2;
    	} catch(ErreurTrajet e) {
    		return -2;
    	}
    	
    	try{
    		dureeT2 = dureeT2.add(t2.sesEtapes().get(0).hDepart().delaiAvant(t2.sesEtapes().get(t2.sesEtapes().size() - 1).hArrivee()));
    	} catch(ErreurHeure e) {
    		return -2;
    	} catch(ErreurTrajet e) {
    		return -2;
    	}
    	System.out.println("Duree t1 : "+ dureeT1 + "\nDuree t2 : " + dureeT2 + "\n");
    	
    	return dureeT1.compareTo(dureeT2);
    }
}
