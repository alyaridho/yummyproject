package smt3.progdevelopment.yummyproject.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import smt3.progdevelopment.yummyproject.MainActivity;
import smt3.progdevelopment.yummyproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.HashMap;

public class registAdmin extends AppCompatActivity {

    private Button RegistAdminButton;
    private EditText InputAdminName, InputAdminEmail, InputAdminPassword;
    private ProgressDialog AdminloadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist_admin);

        RegistAdminButton = (Button) findViewById(R.id.Admin_register_btn);
        InputAdminName = (EditText) findViewById(R.id.admin_username);
        InputAdminEmail = (EditText) findViewById(R.id.admin_regist_email);
        InputAdminPassword = (EditText) findViewById(R.id.admin_regist_password);
        AdminloadingBar = new ProgressDialog(this);

        RegistAdminButton.setOnClickListener(new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                CreateAccount();
            }
        });
    }

    private void CreateAccount() {
        String adminemail = InputAdminEmail.getText().toString();
        String adminname = InputAdminName.getText().toString();
        String adminpassword = InputAdminEmail.getText().toString();

        if(TextUtils.isEmpty(adminname)){
            Toast.makeText(this,"Please write your name...",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(adminemail)){
            Toast.makeText(this,"Please insert your email...",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(adminpassword)){
            Toast.makeText(this,"Please insert your password...",Toast.LENGTH_SHORT).show();
        }
        else {
            AdminloadingBar.setTitle("Create Account");
            AdminloadingBar.setMessage("Please wait, wait we are checking the credentials");
            AdminloadingBar.setCanceledOnTouchOutside(false);
            AdminloadingBar.show();

            ValidateEmail(adminname, adminpassword, adminemail);
        }

    }

    private void ValidateEmail(final String adminname, final String adminemail, final String adminpassword) {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!(dataSnapshot.child("Admins").child(adminemail).exists())){
                    HashMap<String, Object> userdataMap = new HashMap<>();
                    userdataMap.put("AdminEmail",adminemail);
                    userdataMap.put("AdminName",adminname);
                    userdataMap.put("AdminPassword",adminpassword);


                    RootRef.child("Admins").child(adminemail).updateChildren(userdataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(registAdmin.this, "Congratulations, your account has been created", Toast.LENGTH_SHORT).show();
                                AdminloadingBar.dismiss();

                                Intent intent = new Intent(registAdmin.this, MainActivity.class);
                                startActivity(intent);
                            }else {
                                AdminloadingBar.dismiss();
                                Toast.makeText(registAdmin.this, "Network Error: Please try again after some time...", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }else {
                    Toast.makeText(registAdmin.this, "This "+ adminemail +" already exists.", Toast.LENGTH_SHORT).show();
                    AdminloadingBar.dismiss();
                    Toast.makeText(registAdmin.this, "Please try again using another phone number.", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(registAdmin.this, MainActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}