package com.mdrahorat4563.myfirebasechat;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    EditText email;
    EditText password;
    Button signIn;
    Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.txt_username);
        password = findViewById(R.id.txt_password);
        signIn = (Button) findViewById(R.id.btn_login);
        register = (Button) findViewById(R.id.btn_signup);
        mAuth = FirebaseAuth.getInstance();

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate()){
                    signIn(email.getText().toString(), password.getText().toString());
                }
                else{
                    Toast.makeText(LoginActivity.this, "Username or Password is incorrect",
                    Toast.LENGTH_SHORT).show();
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate()){
                    createAccount(email.getText().toString(), password.getText().toString());
                }
                else{
                    Toast.makeText(LoginActivity.this, "Validation failed.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public boolean validate(){
        boolean isValid = true;

        String email = this.email.getText().toString();
        String pass = password.getText().toString();

        if(email.isEmpty() || pass.isEmpty()){
            isValid = false;
        }

        return isValid;

    }


    public void createAccount(String email, String password){
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete (@NonNull Task < AuthResult > task) {
                            if (task.isSuccessful()) {
                                FirebaseUser newUser = mAuth.getCurrentUser();
                                updateUI(newUser);
                            }
                            else {
                                try {
                                    throw task.getException();
                                }catch(FirebaseAuthWeakPasswordException e){
                                    Toast.makeText(LoginActivity.this, e.getReason(),
                                            Toast.LENGTH_SHORT).show();
                                }
                                catch (FirebaseAuthInvalidCredentialsException e) {
                                    Toast.makeText(LoginActivity.this, e.getMessage(),
                                            Toast.LENGTH_SHORT).show();
                                }
                                catch (FirebaseAuthUserCollisionException e) {
                                    Toast.makeText(LoginActivity.this, e.getMessage(),
                                            Toast.LENGTH_SHORT).show();
                                }
                                catch (Exception e) {
                                    Toast.makeText(LoginActivity.this, e.getMessage(),
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });
        }

    public void signIn(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        }
                        else{
                            try{
                                throw task.getException();
                            }
                            catch (FirebaseAuthInvalidUserException e){
                                Toast.makeText(LoginActivity.this, e.getMessage()
                                        , Toast.LENGTH_SHORT).show();
                            }
                            catch (FirebaseAuthInvalidCredentialsException e){
                                Toast.makeText(LoginActivity.this, e.getMessage()
                                        , Toast.LENGTH_SHORT).show();
                            }
                            catch (Exception e){
                                Toast.makeText(LoginActivity.this, e.getMessage()
                                        , Toast.LENGTH_SHORT).show();
                            }

                        }
                    }
                });
    }

    public void updateUI(FirebaseUser user){
        Intent i = new Intent(LoginActivity.this, ChatActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }
}
