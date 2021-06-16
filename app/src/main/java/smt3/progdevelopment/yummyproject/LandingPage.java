package smt3.progdevelopment.yummyproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import smt3.progdevelopment.yummyproject.Admin.loginAdmin;
import smt3.progdevelopment.yummyproject.User.registUser;

public class LandingPage extends AppCompatActivity {

    Button move1, move2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);

        move1 = (Button) findViewById(R.id.button);
        move2 = (Button) findViewById(R.id.button2);

        move1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toUser();
            }
        });
        move2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toAdmin();
            }
        });
    }
    private void toUser(){
        Intent intent = new Intent(LandingPage.this, registUser.class);
        startActivity(intent);
    }
    private void toAdmin(){
        Intent intent = new Intent(LandingPage.this, loginAdmin.class);
        startActivity(intent);
    }
}