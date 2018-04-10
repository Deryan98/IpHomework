package com.example.carlos.tareaip;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
    private EditText value;
    private Button boton_calcular;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boton_calcular = findViewById(R.id.boton_calcular);

        boton_calcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                EditText valorMascara = findViewById(R.id.numeroMascara);

                EditText octeto_1 = findViewById(R.id.octeto1);
                EditText octeto_2 = findViewById(R.id.octeto2);
                EditText octeto_3 = findViewById(R.id.octeto3);
                EditText octeto_4 = findViewById(R.id.octeto4);

                Intent ventanaCalcular = new Intent(getApplicationContext(), Main2Activity.class);
                ventanaCalcular.setAction(Intent.ACTION_SEND);
                ventanaCalcular.setType("text/plain");

                String value = octeto_1.getText().toString() + " " + octeto_2.getText().toString() + " " + octeto_3.getText().toString() + " " + octeto_4.getText().toString() + " " + valorMascara.getText().toString();
                ventanaCalcular.putExtra(Intent.EXTRA_TEXT, value);

                startActivity(ventanaCalcular);



            }
        });
    }
}
