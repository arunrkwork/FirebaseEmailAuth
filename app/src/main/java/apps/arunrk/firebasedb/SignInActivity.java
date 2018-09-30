package apps.arunrk.firebasedb;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseAuth;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class SignInActivity extends AppCompatActivity {

    private static final String TAG = SignInActivity.class.getSimpleName();

    private FirebaseAuth mAuth;
    private EditText edUname, edPass;
    private Button btnLogin, btnReg;


    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        mAuth = FirebaseAuth.getInstance();

        btnLogin = findViewById(R.id.btnSignUp);
        btnReg = findViewById(R.id.btnReg);
        edUname = findViewById(R.id.edUname);
        edPass = findViewById(R.id.edPass);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAccount();
            }
        });

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new android.content.Intent(SignInActivity.this, SignUpActivity.class));
            }
        });

    }

    private void createAccount() {

        String email = edUname.getText().toString().trim();
        String pass = edPass.getText().toString().trim();


        if (!validate()) return;

        mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    android.util.Log.d(TAG, "signInWithEmail:success");
                    FirebaseUser user = mAuth.getCurrentUser();
                    updateUI(user);
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                    android.widget.Toast.makeText(SignInActivity.this, "Authentication failed.", android.widget.Toast.LENGTH_SHORT).show();
                    updateUI(null);
                }


                if (!task.isSuccessful()) {
                    Toast.makeText(SignInActivity.this, "Unsuccess", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void updateUI(FirebaseUser currentUser) {

        if (currentUser != null) {
            final FirebaseUser user = mAuth.getCurrentUser();
            user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "Email sent.");
                    }
                }
            });
            finish();
            startActivity(new android.content.Intent(SignInActivity.this, MainActivity.class));
        } else {
            Toast.makeText(SignInActivity.this, "Sign In Not Allowed", Toast.LENGTH_SHORT).show();
        }

    }

    private boolean validate() {
        boolean validation = true;

        String email = edUname.getText().toString().trim();
        String pass = edPass.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            edUname.setError("Required");
            validation = false;
        } else {
            edUname.setError(null);
        }

        if (TextUtils.isEmpty(pass)) {
            edPass.setError("Required");
            validation = false;
        } else {
            edPass.setError(null);
        }

        return validation;
    }


}
