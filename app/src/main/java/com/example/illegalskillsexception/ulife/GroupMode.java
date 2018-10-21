package com.example.illegalskillsexception.ulife;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.Random;
import android.text.InputType;


/**
 * Created by healthappy on 10/20/18.
 */

public class GroupMode extends AppCompatActivity{

    public static boolean groupDetectionService;
    public static boolean isGroupMode;
    Random random = new Random();
    public static String GroupID = "";
    private String inputCode = "";
    final String alphabet = "abcdefghijklmnop";
    final int N = alphabet.length();
    public static String userID = "";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_mode);

        final Button startGroupButton = findViewById(R.id.start_group_button);
        startGroupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GroupID = String.format("%04d", random.nextInt(10000));
                String msg;
                msg = "Your Group ID is: " + GroupID + ". Share this code with your friends!";
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(GroupMode.this,  AlertDialog.THEME_HOLO_LIGHT);
                // android.R.style
                // .Theme_Material_Light_Dialog_Alert);
                alertDialogBuilder.setTitle("Your Group Code!");
                alertDialogBuilder.setMessage(Html.fromHtml(msg))
                        .setCancelable(true)
                        .setPositiveButton("OK, Join",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                Random r = new Random();
                                for (int i = 0; i < 4; i++) {
                                    userID = userID + (alphabet.charAt(r.nextInt(N)));
                                }
                                Toast.makeText(GroupMode.this, "You Have Joined The Group!", Toast.LENGTH_SHORT).show();
                                isGroupMode = true;
                                startBackgroundService();
                                dialog.cancel(); // close current activity
                            }
                        });
                alertDialogBuilder.create(); // create alert dialog
                alertDialogBuilder.show();
                //should give pop up dialog with new group code randomally generated locally
                //other users need to put group code in
                //GroupRESTService.pushKeyValue(GroupID, );

            }
        });

        final Button joinGroupButton = findViewById(R.id.join_group_button);
        joinGroupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(GroupMode.this);
                builder.setTitle("Enter Your Existing Group Code: ");

                final EditText input = new EditText(GroupMode.this);

                input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                builder.setView(input);

                builder.setPositiveButton("OK, Join", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        inputCode = input.getText().toString();
                        GroupID = inputCode;
                        Toast.makeText(GroupMode.this, "You Have Joined The Group!", Toast.LENGTH_SHORT).show();
                        isGroupMode = true;
                        Random r = new Random();
                        for (int i = 0; i < 4; i++) {
                           userID = userID + (alphabet.charAt(r.nextInt(N)));
                        }
                        startBackgroundService();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();


                //dialog saying enter group ID!
                //String GroupID = String.format("%04d", random.nextInt(10000));
                //should give pop up dialog with new group code randomally generated locally
                //other users need to put group code in
                //GroupRESTService.pushKeyValue(GroupID, )

            }
        });

    }

    public void startBackgroundService() {
        Intent intent = new Intent(GroupMode.this, DetectionService.class);
        ((GroupMode) GroupMode.this).startService(intent);
        groupDetectionService = true;
    }

    public void stopBackgroundService() {
        groupDetectionService = false;
    }

    public static String getGroupID(){
        return GroupID;
    }

    public static String getUserID(){
        return userID;
    }

}
