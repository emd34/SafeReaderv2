package edu.fsu.cs.mobile.safereader;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;

public class MainActivity extends AppCompatActivity {
    private SignInButton googleButton;
    private static final int RC_SIGN_IN = 777;
    private static final String TAG = "SafeReader";

    private GoogleApiClient mGoogleApiClient;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private Firebase mRef;

    private Button login;
    private Button register;
    private EditText username;
    private EditText password;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Firebase.setAndroidContext(this);

        mRef = new Firebase("https://safereader-6e505.firebaseio.com/");
        mAuth = FirebaseAuth.getInstance();

        googleButton = (SignInButton) findViewById(R.id.googleButton);
        login = (Button) findViewById(R.id.login_button_main);
        register = (Button) findViewById(R.id.register_button_main);
        username = (EditText) findViewById(R.id.usernameEdit);
        password = (EditText) findViewById(R.id.passwordEdit);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                //If we're already logged in...
                if(firebaseAuth.getCurrentUser() != null){
                    startActivity(new Intent(MainActivity.this, HomeActivity.class));
                }
            }
        };

        login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String getName = username.getText().toString();
                String getPass = password.getText().toString();

                if(TextUtils.isEmpty(getName) || TextUtils.isEmpty(getPass)){
                    Toast.makeText(getApplicationContext(), "Please enter the information required", Toast.LENGTH_SHORT).show();
                }
                else{
                    startSignIn();
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getName = username.getText().toString();
                String getPass = password.getText().toString();

                if(TextUtils.isEmpty(getName) || TextUtils.isEmpty(getPass)){
                    Toast.makeText(getApplicationContext(), "Please enter the information required", Toast.LENGTH_SHORT).show();
                }
                else{

                }
            }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Toast.makeText(MainActivity.this, "Connection Failed!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        googleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {

            }
        }
    }

    public void firebaseAuthWithGoogle(GoogleSignInAccount account){
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            startActivity(new Intent(MainActivity.this, HomeActivity.class));
                        } else {
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                        }
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    private void startSignIn(){
        String getName = username.getText().toString();
        String getPass = password.getText().toString();

        mAuth.signInWithEmailAndPassword(getName, getPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(!task.isSuccessful())
                    Toast.makeText(MainActivity.this, "Sign in failed!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void startRegister(){
        final String getName = username.getText().toString();
        final String getPass = password.getText().toString();

        mAuth.createUserWithEmailAndPassword(getName, getPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(!task.isSuccessful()) {
                    String user = mAuth.getCurrentUser().getUid();

                    DatabaseReference currentDatabase = mDatabase.child(user);

                    currentDatabase.child("email").setValue(getName);
                    currentDatabase.child("password").setValue(getPass);

                    Toast.makeText(getApplicationContext(), "Registered!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
