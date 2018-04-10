package com.example.carlos.tareaip;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import org.w3c.dom.Text;

public class Main2Activity extends AppCompatActivity {

    private TextView myIp;
    private TextView net;
    private TextView broadcast;
    private TextView netPart;
    private TextView broadcastPart;
    private TextView numberOfHost;
    private String values[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        IpAddress ip;
        String netId[];
        String broadcastId[];
        myIp = findViewById(R.id.myIp);
        net = findViewById(R.id.netId);
        broadcast = findViewById(R.id.broadcastId);
        netPart = findViewById(R.id.netNumber);
        broadcastPart = findViewById(R.id.broadcastNumber);
        numberOfHost = findViewById(R.id.numberOfHost);

        final Button back = findViewById(R.id.back);
        char classType;
        Intent getIntent  = getIntent();
        String actionIntent = getIntent.getAction();
        String typeIntent = getIntent.getType();

        if( Intent.ACTION_SEND.equals(actionIntent) && typeIntent != null ){
            if( typeIntent.equals("text/plain") ){
                values = gettingValues(getIntent);

                ip = new IpAddress(values[0], values[1], values[2], values[3], values[4]);
                netId = ip.getNetId();
                broadcastId = ip.getBrodcastId();
                numberOfHost.setText(String.valueOf(ip.calcularNumHost()));
                myIp.setText(values[0] + "." + values[1] + "." + values[2] + "." + values[3] + "/" + values[4]);
                net.setText(netId[0] + "." + netId[1] + "." + netId[2] + "." + netId[3]);
                broadcast.setText(broadcastId[0] + "." + broadcastId[1] + "." + broadcastId[2] + "." + broadcastId[3]);
                classType = ip.getClassType();
                setNetBroad(classType);
            }
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backToHome = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(backToHome);
            }
        });


    }

    public void setNetBroad(char classType){
        switch (classType){
            case 'A':
                netPart.setText(values[0]);
                broadcastPart.setText(values[1] + "." + values[2] + "." + values[3]);
                break;
            case 'B':
                netPart.setText(values[0] + "." + values[1]);
                broadcastPart.setText(values[2] + "." + values[3]);
                break;

            case 'C':
                netPart.setText(values[0] + "." + values[1] + "." + values[2]);
                broadcastPart.setText(values[3]);
                break;
        }
    }

    public String[] gettingValues(Intent intent){

        String values[] = intent.getStringExtra(intent.EXTRA_TEXT).split(" ");
        return values;
    }
}
