package edu.fsu.cs.mobile.safereader;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static android.content.ContentValues.TAG;

public class RegisterFragment extends Fragment {
    private EditText name;
    private EditText password;
    private Button register;

    private FirebaseAuth mAuth;
    private String getName;
    private String getPass;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        mAuth = FirebaseAuth.getInstance();
        name = (EditText) view.findViewById(R.id.register_username);
        password = (EditText) view.findViewById(R.id.register_password);
        register = (Button) view.findViewById(R.id.register_button);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                getName = name.getText().toString();
                getPass = password.getText().toString();

                createAccount(getName, getPass);
            }
        });

        return view;
    }

    private void createAccount(String username, String password){

        if(!validateForm())
            return;

        mAuth.createUserWithEmailAndPassword(username, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithUsername:success");
                            Toast.makeText(getContext(), "Registered!", Toast.LENGTH_LONG).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithUsername:failure", task.getException());
                            Toast.makeText(getContext(), "Uh oh!", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private boolean validateForm() {
        boolean valid = true;

        String username = name.getText().toString();
        if (TextUtils.isEmpty(username)) {
            name.setError("Please enter a username");
            valid = false;
        } else {
            name.setError(null);
        }

        String pass = password.getText().toString();
        if (TextUtils.isEmpty(pass)) {
            password.setError("Please enter a password");
            valid = false;
        } else {
            password.setError(null);
        }

        return valid;
    }
}
