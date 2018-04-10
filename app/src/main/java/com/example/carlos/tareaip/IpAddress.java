package com.example.carlos.tareaip;

import android.util.Log;

/**
 * Created by Rodrigo Corvera on 8/4/2018.
 */

public class IpAddress {

    private int octante[];
    private int mascara;
    private String mascaraArreglo[];
    private char classType;

    public IpAddress(String octante1, String octante2, String octante3, String octante4, String mascara){
        octante = new int[4];
        octante[0] = Integer.parseInt(octante1);
        octante[1] = Integer.parseInt(octante2);
        octante[2] = Integer.parseInt(octante3);
        octante[3] = Integer.parseInt(octante4);
        this.mascara = Integer.parseInt(mascara);
        getMask();
    }

    public void getMask(){
        String cadenaMascara = "";
        for (int i = 0; i < 32; i++){
            if( i < mascara ){
                cadenaMascara += String.valueOf(1);
            }else{
                cadenaMascara += String.valueOf(0);
            }
        }

        mascaraArreglo = new String[4];

        for (int i = 0; i < 4; i++){
            mascaraArreglo[i] = cadenaMascara.substring(i*8,(i+1)*8);
        }
    }


    public int toDecimal(String value){
        int expo = value.length() - 1;
        int decimal = 0;
        for (int i = 0; i < value.length(); i++) {
            decimal += Integer.parseInt(value.charAt(i) + "")*Math.pow(2, expo) ;
            expo--;
        }
        return decimal;
    }

    public String[] calcularNetId(){

        String totalNetId = "";

        String totalNetId2[] = new String[4];

        for (int i = 0; i < 4 ; i++){
            totalNetId2[i] = String.valueOf(toDecimal(mascaraArreglo[i]) & octante[i]);
        }

        return totalNetId2;

    }

    public String[] calcularBroadcastId(){
        String totalBroadcast[] = new String[4];

        for (int i = 0; i < 4 ; i++){

            String cadenaBinaria = ("0000000" + Integer.toBinaryString(0xFF & ~toDecimal(mascaraArreglo[i]))).replaceAll(".*(.{8})$", "$1");
            Integer not = toDecimal(cadenaBinaria);
            totalBroadcast[i] = String.valueOf( octante[i] | not );

        }

        return totalBroadcast;

    }

    public int calcularNumHost(){
        int counter;
        if( octante[0] >= 0 && octante[0] < 127){
            counter = hostCounter(1);
            classType = 'A';

        }else if( octante[0] >= 128 && octante[0] < 192){
            counter = hostCounter(2);
            classType = 'B';

        }else{
            counter = hostCounter(3);
            classType = 'C';

        }
        Double result = Math.pow(2,counter)-2;
        return result.intValue();

    }


    public char getClassType(){
        return classType;
    }

    public int hostCounter(int numOct){
        int sum = 0;
        for(int i = numOct; i < 4 ; i++){
            for( int j = 0; j < 8; j++){
                if( mascaraArreglo[i].charAt(j) == '0'){
                    sum++;
                }
            }
        }
        return sum;
    }

    public String[] getNetId() {
        return calcularNetId();
    }

    public String[] getBrodcastId(){
        return calcularBroadcastId();
    }


}
