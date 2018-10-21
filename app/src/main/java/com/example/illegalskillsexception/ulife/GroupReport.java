package com.example.illegalskillsexception.ulife;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.glassfish.jersey.jaxb.internal.XmlJaxbElementProvider;

/**
 * Created by healthappy on 10/21/18.
 */

public class GroupReport extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_groupmode);


        final Button DoneButton = findViewById(R.id.button2);
        DoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GroupReport.this, MainActivity.class);
                (GroupReport.this).startActivity(intent);
            }
        });

        final Button AgainButton = findViewById(R.id.button1);
        AgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GroupReport.this, GroupMode.class);
                (GroupReport.this).startActivity(intent);
            }
        });
    }


}

