package com.example.alpha;

import static com.example.alpha.FBref.currentUser;
import static com.example.alpha.FBref.mAuth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class MainActivity extends AppCompatActivity {
    EditText mail, password;
    String mail1, password1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mail= (EditText) findViewById(R.id.mail);
        password= (EditText) findViewById(R.id.password);
    }
    public void signIn(View view) {
        mail1= mail.getText().toString();
        password1= password.getText().toString();
        if (mail1.isEmpty()){
            Toast.makeText(this, "Enter your mail", Toast.LENGTH_SHORT).show();
        }
        else if (password1.isEmpty()) {
            Toast.makeText(this, "Enter your password", Toast.LENGTH_SHORT).show();
        }
        else{
            mAuth.createUserWithEmailAndPassword(mail1, password1)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("TAG", "createUserWithEmail:success");
                                currentUser = mAuth.getCurrentUser();
                                updateUI(currentUser);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("TAG", "createUserWithEmail:failure", task.getException());
                                Toast.makeText(MainActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                updateUI(null);
                            }
                        }
                    });

        }
    }

    private void updateUI(FirebaseUser user) {
        if (user==null)
            Toast.makeText(this, "We have a problem", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show();
    }

    public void logIn(View view) {
        mail1= mail.getText().toString();
        password1= password.getText().toString();
        if (mail1.isEmpty()){
            Toast.makeText(this, "Enter your mail", Toast.LENGTH_SHORT).show();
        }
        else if (password1.isEmpty()) {
            Toast.makeText(this, "Enter your password", Toast.LENGTH_SHORT).show();
        }
        else {
            mAuth.signInWithEmailAndPassword(mail1,password1)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                update2(true);
                            } else {
                                update2(false);
                            }
                        }
                    });
        }
    }
    public void update2(boolean b1){
        if (b1)
            Toast.makeText(this, "Welcome back", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "We have a problem", Toast.LENGTH_SHORT).show();
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        int id= item.getItemId();
        if(id==R.id.gallery){
            Intent a = new Intent(this, Gallery.class);
            startActivity(a);
        }
        if (id==R.id.newPicture){
            Intent a = new Intent(this, NewPicture.class);
            startActivity(a);
        }
        return true;
    }
}