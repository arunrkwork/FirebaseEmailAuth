package apps.arunrk.firebasedb;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;

public class MainActivity extends AppCompatActivity {

    EditText edUname, edPass;
    Button btnAdd, btnDelete;
    String uname, pass;
    FirebaseDatabase mDataBase;
    DatabaseReference mDataBaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Write a message to the database
        mDataBase = FirebaseDatabase.getInstance();
        mDataBaseReference = mDataBase.getReference();

        // Simple
        /*FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");

        myRef.setValue("Hello, World!");*/

        edUname = findViewById(R.id.edUname);
        edPass = findViewById(R.id.edPass);

        btnAdd = findViewById(R.id.btnAdd);
        btnDelete = findViewById(R.id.btnDelete);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uname = edUname.getText().toString().trim();
                pass = edPass.getText().toString().trim();
                writeNewUser(uname,pass);
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDataBaseReference.removeValue();
            }
        });
    }

    private void writeNewUser(String name, String pass) {
        User user = new User(name, pass);
        mDataBaseReference.child("user").child(name).setValue(user);
    }

    @IgnoreExtraProperties
    class User {

        // objects must be public
        public String uname, pass;

        public User() {

        }

        public User(String uname, String pass) {
            this.uname = uname;
            this.pass = pass;
        }



    }

}
