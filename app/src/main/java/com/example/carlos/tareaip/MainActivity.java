package com.example.carlos.tareaip;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private int octetos[] = new int[4];
    private int mascara_de_red;
    private TextView netId;
    private TextView cantidadHost;
    private  TextView parteRed;
    private  TextView parteHost;
    private TextView broadCastId;
    private Button boton_calcular;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boton_calcular = findViewById(R.id.boton_calcular);

        boton_calcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String value [] = getMask();

                EditText octeto_1 = findViewById(R.id.octeto_1);
                EditText octeto_2 = findViewById(R.id.octeto_2);
                EditText octeto_3 = findViewById(R.id.octeto_3);
                EditText octeto_4 = findViewById(R.id.octeto_4);

                octetos[0]  = Integer.parseInt(octeto_1.getText().toString());
                octetos[1]  = Integer.parseInt(octeto_2.getText().toString());
                octetos[2]  = Integer.parseInt(octeto_3.getText().toString());
                octetos[3]  = Integer.parseInt(octeto_4.getText().toString());

                calcularNetId(value);
                calcularBroadcastId(value);
                calcularNumHost(value);
            }
        });
    }
    public void calcularNetId(String []value){

        netId = findViewById(R.id.net_id_numero);

        String totalNetId = "";

        String totalNetId2[] = new String[4];

        for (int i = 0; i < 4 ; i++){
            totalNetId2[i] = String.valueOf(toDecimal(value[i]) & octetos[i]);
        }

        //netId.setText(totalNetId.substring(0,8)+"."+totalNetId.substring(8,16)+"."+totalNetId.substring(16,24)
                //+"."+totalNetId.substring(24,31));
        netId.setText(totalNetId2[0]+"."+totalNetId2[1]+"."+totalNetId2[2]+"."+totalNetId2[3]);

    }

    public void calcularBroadcastId(String []value){

        broadCastId = findViewById(R.id.broadcast_numero);
        String totalBroadcast[] = new String[4];

        for (int i = 0; i < 4 ; i++){
            Integer not = ~toDecimal(value[i]); // -256
            totalBroadcast[i] = String.valueOf( octetos[i] | not.byteValue() );
        }

        broadCastId.setText(totalBroadcast[0]+"."+totalBroadcast[1]+"."+totalBroadcast[2]+"."+totalBroadcast[3]);

    }

    public String[] getMask(){
        EditText value = findViewById(R.id.mascara_numero);
        String mask1[] = new String[8];
        String mask2[] = new String[8];
        String mask3[] = new String[8];
        String mask4[] = new String[8];
        String numberMask = value.getText().toString();

        for (int i = 0; i < 32; i++){

            if( i < 8 ){
                mask1[i] = String.valueOf(0);
            }else if( i < 16){
                mask2[i-8] = String.valueOf(0);
            }else if( i < 24 ){
                mask3[i-16] = String.valueOf(0);
            }else if( i < 32 ){
                mask4[i-24] = String.valueOf(0);
            }
        }

        for (int i = 0; i < Integer.parseInt(numberMask); i++){
            if( i < 8 ){
                mask1[i] = String.valueOf(1);
            }else if( i < 16){
                mask2[i-8] = String.valueOf(1);
            }else if( i < 24 ){
                mask3[i-16] = String.valueOf(1);
            }else if( i < 32 ){
                mask4[i-24] = String.valueOf(1);
            }
        }

        String total = "";
        String total1  = "";
        String total2  = "";
        String total3  = "";

        for (int i = 0; i < mask1.length; i++) {
            total +=mask1[i];
            total1 +=mask2[i];
            total2 +=mask3[i];
            total3 +=mask4[i];
            System.out.println(mask1[i]);
        }

        String values[] = new String[4];

        values[0] = total;
        values[1] = total1;
        values[2] = total2;
        values[3] = total3;

        return values;
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


        /*
        * CalcularNumHost() ---> Funciona para calcular el numero de host
        * Lo que hace es considerar el rango en la que se encuentra el primer octeto
        * Los rangos a considerar son:
        *       1-126* CLASE A              *El 127 está reservado para conexiones de prueba local
        *       128-191 CLASE B
        *       192-223 CLASE C
        *   Dependiendo de esto solo va a considerar ciertos octetos:
        *   CLASE A: Los ultimos 3
        *   CLASE B: Los ultimos 2
        *   CLASE C: El último
        *   Luego llama a la funcion hostCounter.
        *
        *   La formula para calcular los host es: (2^(numero_de_ceros)) - 2.
        *   Donde numero_de_ceros es  el valor devuelto por la función hostCounter.
        *   Se le resta 2 porque son direcciones reservadas para el Broadcast y la dirección de red
        *
        * */



        public void calcularNumHost(String []value){
            cantidadHost = findViewById(R.id.cantidad_de_host_numero);
            parteRed = findViewById(R.id.parte_de_red_numero);
            parteHost = findViewById(R.id.parte_de_host_numero);
            int counter;
            if( octetos[0] >= 0 && octetos[0] < 127){
                counter = hostCounter(value,1);
                parteRed.setText(octetos[0]);
                parteHost.setText(octetos[1] + "." + octetos[2] + "." + octetos[3]);
            }else if( octetos[0] >= 128 && octetos[0] < 192){
                counter = hostCounter(value,2);
                parteRed.setText(octetos[0] + "." +octetos[1]);
                parteHost.setText(octetos[2] + "." + octetos[3]);
            }else{
                counter = hostCounter(value,3);
                parteRed.setText(octetos[0] + "." + octetos[1] + "." + octetos[2]);
                parteHost.setText(octetos[3]);
            }
            Double result = Math.pow(2,counter)-2;
            cantidadHost.setText(String.valueOf(result.intValue()));
        }


    /*
    * Esta función recorre cada octeto a partir del tipo de red que sea.
    * Recorre cada octeto en busca de CEROS y los cuenta
    * Estos son devueltos a la funcion del calculo de host.
    * La formula para calcular los host es: (2^(numero_de_ceros)) - 2.
    * Donde numero_de_ceros es  el valor devuelto por esta función.
    *
    * */
        public int hostCounter(String value[], int numOct){
            int sum = 0;
            for(int i = numOct; i < 4 ; i++){
                for( int j = 0; j < 8; j++){
                    if( value[i].charAt(j) == '0'){
                        sum++;
                    }
                }
            }
            return sum;
        }
}
