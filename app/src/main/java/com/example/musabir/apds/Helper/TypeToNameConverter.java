package com.example.musabir.apds.Helper;

/**
 * Created by Musabir on 4/12/2018.
 */

public class TypeToNameConverter {

    public static String typeToNameConvert(int id){
        switch (id) {
            case 1:
                return "Temperature";
            case 2:
                return "Humidity";
            case 3:
                return "Methane";
            case 4:
                return "Carbon Monoxide";
            case 5:
                return "Carbon Dioxide";
            case 6:
                return "Smoke";
            case 7:
                return "LPG";
            case 8:
                return "Hydrogen";

        }


        return "";
    }
}
