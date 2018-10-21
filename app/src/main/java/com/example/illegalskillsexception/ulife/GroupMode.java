package com.example.illegalskillsexception.ulife;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by healthappy on 10/20/18.
 */

public class GroupMode extends AppCompatActivity{

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_mode);

        final Button startButton = findViewById(R.id.start_group_button);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //should give pop up dialog with new group code randomally generated locally
                //other users need to put group code in
                //
            }
        });

    }

}
