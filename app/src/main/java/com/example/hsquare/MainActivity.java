package com.example.hsquare;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.hsquare.Fragments.HomeFragment;
import com.example.hsquare.Model.GoogleUsers;
import com.example.hsquare.Model.Users;
import com.example.hsquare.Prevalent.Prevalent;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {
    private static final int RC_SIGN_IN = 123;
    Button btn_join, btn_already, btnQuickShop;
    ImageView ivGoogleSignin, ivFbSignin;
    ProgressDialog progressDialog;
    GoogleSignInOptions gso;
    GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;
    private String gName, gEmail, gId;
    private String googleUser = "googleUser";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_join = findViewById(R.id.btn_main_join);
        btn_already = findViewById(R.id.btn_main_already);
        btnQuickShop = findViewById(R.id.btn_main_quickshop);
        ivGoogleSignin = findViewById(R.id.iv_googlesignin);
        ivFbSignin = findViewById(R.id.iv_fbsignin);


        mAuth = FirebaseAuth.getInstance();

        //Google Sign in
        createRequest();


        progressDialog = new ProgressDialog(this);

        Paper.init(this);

        //checkBox Implementation
        String userPhoneKey = Paper.book().read(Prevalent.userPhoneKey);
        String userPasswordKey = Paper.book().read(Prevalent.userPasswordKey);

        if (userPhoneKey != "" && userPasswordKey != "") {
            if (!TextUtils.isEmpty(userPhoneKey) && !TextUtils.isEmpty(userPasswordKey)) {
                progressDialog.setTitle("Already Logged in");
                progressDialog.setMessage("Please wait...");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();
                allowAccess(userPhoneKey, userPasswordKey);

            }

        }
        //-------------------google Sign in---------------------

        ivGoogleSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                googleSignIn();


            }
        });


        //------------------quickshop-----------------

        btnQuickShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Singleton.obj.guestid = UUID.randomUUID().toString();

                Intent intent = new Intent(MainActivity.this, HomeGuestAcitvity.class);

                startActivity(intent);
                overridePendingTransition(R.anim.push_down_in, R.anim.push_down_out);
            }
        });


        //-------------------------Already--------------------------------------

        btn_already.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LoginAcitvity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);

            }
        });

        //-----------------------join now register----------------------
        btn_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }
        });

    }

    private void createRequest() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();


        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null) {

            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
            GoogleSignInAccount sign = GoogleSignIn.getLastSignedInAccount(MainActivity.this);

            Singleton.obj.googleId = sign.getId();


            startActivity(intent);
        }
    }

    private void googleSignIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately

                // ...
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            final FirebaseUser user = mAuth.getCurrentUser();

                            GoogleSignInAccount signIn = GoogleSignIn.getLastSignedInAccount(MainActivity.this);
                            if (signIn != null) {
                                gName = signIn.getDisplayName();
                                gEmail = signIn.getEmail();
                                gId = signIn.getId();


                                final DatabaseReference rootRef;
                                rootRef = FirebaseDatabase.getInstance().getReference();

                                rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull final DataSnapshot snapshot) {
                                        if (!snapshot.child("Users").child(gId).exists()) {


                                            HashMap<String, Object> userDataMap = new HashMap<>();
                                            userDataMap.put("email", gEmail);
                                            userDataMap.put("name", gName);
                                            userDataMap.put("id", gId);

                                            rootRef.child("Users").child(gId).updateChildren(userDataMap)

                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {
                                                                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
//                                                                final GoogleUsers users = snapshot.child("Users").child(gId).getValue(GoogleUsers.class);
                                                                Singleton.obj.googleId = gId;
                                                                intent.putExtra("googleUser", "googleUser");


                                                                startActivity(intent);
                                                            }
                                                        }
                                                    });
                                        } else {
                                            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
//                                          final GoogleUsers users = snapshot.child("Users").child(gId).getValue(GoogleUsers.class);
                                            Singleton.obj.googleId = gId;

                                            startActivity(intent);

                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });

                            }


                        } else {
                            // If sign in fails, display a message to the user.
                            Log.e("googleException", "Sign-in Failed: " + task.getException().getMessage());
                            Toast.makeText(MainActivity.this, "Sorry! Authentication failed.", Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }


    private void allowAccess(final String number, final String password) {

        final DatabaseReference databaseReference;
        databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.child("Users").child(number).exists()) {
                    Users usersData = snapshot.child("Users").child(number).getValue(Users.class);

                    if (usersData.getPhone().equals(number)) {

                        if (usersData.getPassword().equals(password)) {
                            Toast.makeText(MainActivity.this, "Logged in Successfully...", Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();

                            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                            Prevalent.currentOnlineUser = usersData;
                            startActivity(intent);


                        } else {
                            Toast.makeText(MainActivity.this, "Password is incorrect!", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();

                        }
                    }

                } else {
                    Toast.makeText(MainActivity.this, "Account don't exists! Create a new Account!", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            finishAffinity();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
}
