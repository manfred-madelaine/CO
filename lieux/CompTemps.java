package lieux;

public class CompTemps implements Comparateur {
    public int compare(Trajet t1, Trajet t2) {
    	Heure dureeT1 = new Heure();
    	Heure dureeT2 = new Heure();
    	
    	try{
    		dureeT1 = t1.duree();
    	} catch(ErreurTrajet e) {
    		return -2;
    	}
    	
    	try{
    		dureeT2 = t2.duree();
    	} catch(ErreurTrajet e) {
    		return -2;
    	}
    	
    	return dureeT1.compareTo(dureeT2);
    }
}
