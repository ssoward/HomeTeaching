package com.soward.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import com.soward.object.Comps;

public class HomeTeachingUtils {

    /**
     * @param args
     */
    public static void main( String[] args ) {
        // TODO Auto-generated method stub

    }
    public static ArrayList<Family> alphabetizeFamList( ArrayList<Family> famList ) {
        ArrayList<String> famAL = new ArrayList<String>();
        HashMap<String, Family>famHash = new HashMap<String, Family>();
        ArrayList<Family> famListAlphabetized = new ArrayList<Family>();
        for(Family tempFam: famList){
            famHash.put( tempFam.getName()+tempFam.getFamFirstName(), tempFam );
            famAL.add(tempFam.getName()+tempFam.getFamFirstName() );
        }
        Collections.sort( famAL );
        for(String familyName: famAL){
            famListAlphabetized.add( famHash.get( familyName ) );
        }
        return famListAlphabetized;
    }
    public static ArrayList<Comps> alphabetizeCompList( ArrayList<Comps> compList ) {
        ArrayList<String> famAL = new ArrayList<String>();
        HashMap<String, Comps>famHash = new HashMap<String, Comps>();
        ArrayList<Comps> famListAlphabetized = new ArrayList<Comps>();
        for(Comps tempFam: compList){
            famHash.put( tempFam.getComp1().getLastName(), tempFam );
            famAL.add(tempFam.getComp1().getLastName());
        }
        Collections.sort( famAL );
        for(String familyName: famAL){
            famListAlphabetized.add( famHash.get( familyName ) );
        }
        return famListAlphabetized;
    }
}
