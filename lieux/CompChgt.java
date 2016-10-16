package lieux;

public class CompChgt implements Comparateur {
    
	public int compare(Trajet t1, Trajet t2) {
    	int chgtT1 = 0;
    	int chgtT2 = 0;
    	int res = 0;
    	
    	try {
        	chgtT1 = t1.nbChgt();
			chgtT2 = t2.nbChgt();
		} catch (ErreurTrajet e) {
			return -2;
		}
    	
    	if (chgtT1 > chgtT2){
    		res = 1;
    	}
    	
    	return res;
    }
}

