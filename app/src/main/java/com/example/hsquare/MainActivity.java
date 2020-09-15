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

import com.example.hsquare.Model.Users;
import com.example.hsquare.Prevalent.Prevalent;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {
    private static final int RC_SIGN_IN = 123;
    Button btn_join, btn_already, btnQuickShop;
    ImageView ivGoogleSignin, ivFbSignin;
    ProgressDialog progressDialog;
    GoogleSignInOptions gso;
    GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;
    private String gName, gEmail, gId, fId;
    LoginButton loginButton;
    private static final String EMAIL = "email";
    CallbackManager callbackManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_join = findViewById(R.id.btn_main_join);
        btn_already = findViewById(R.id.btn_main_already);
        btnQuickShop = findViewById(R.id.btn_main_quickshop);
        ivGoogleSignin = findViewById(R.id.iv_googlesignin);
        ivFbSignin = findViewById(R.id.iv_fbsignin);
//        loginButton = findViewById(R.id.btn_fblogin);

        mAuth = FirebaseAuth.getInstance();

        //Facebook login
//        createFbRequest();
        callbackManager = CallbackManager.Factory.create();

        ivFbSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logInWithReadPermissions(MainActivity.this, Arrays.asList("email", "public_profile"));
                LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        handleFacebookAccessToken(loginResult.getAccessToken());
                        GraphRequest request = GraphRequest.newMeRequest(
                                loginResult.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(JSONObject object, GraphResponse response) {
                                        Log.v("LoginActivity", response.toString());

                                        // Application code
                                        try {
                                            String email = object.getString("email");
                                            String birthday = object.getString("birthday");
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        // 01/31/1980 format
                                    }
                                });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,name,email,gender,birthday");
                        request.setParameters(parameters);
                        request.executeAsync();


                    }


                    @Override
                    public void onCancel() {

                    }

                    @Override
                    public void onError(FacebookException error) {

                    }
                });
            }
        });
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

    private void handleFacebookAccessToken(final AccessToken token) {
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();

                        } else {
                            // If sign in fails, display a message to the user.

                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
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
        if (Singleton.obj.googleId != null) {
            if (user != null) {

                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                GoogleSignInAccount sign = GoogleSignIn.getLastSignedInAccount(MainActivity.this);

                Singleton.obj.googleId = sign.getId();


                startActivity(intent);
            }
        } else if (Singleton.obj.fbId != null) {
                if (user != null) {

                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);


                    startActivity(intent);
                }
            }
        }


    private void googleSignIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        callbackManager.onActivityResult(requestCode, resultCode, data);

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

    AccessTokenTracker tokenTracker = new AccessTokenTracker() {
        @Override
        protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {

            if (currentAccessToken == null) {
                Toast.makeText(MainActivity.this, "User Logged out", Toast.LENGTH_LONG).show();
            } else {
                loadUserProfile(currentAccessToken);
            }
        }
    };

    private void loadUserProfile(AccessToken token) {

        GraphRequest request = GraphRequest.newMeRequest(token, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {

                try {
                    final String first_name = object.getString("first_name");
                    final String last_name = object.getString("last_name");
                    final String email = object.getString("email");
                    fId = object.getString("id");
                    final String img_url = "https://graph.facebook.com/" + fId + "/picture?type=normal";


                    final DatabaseReference rootRef;
                    rootRef = FirebaseDatabase.getInstance().getReference();

                    rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull final DataSnapshot snapshot) {
                            if (!snapshot.child("Users").child(fId).exists()) {


                                HashMap<String, Object> userDataMap = new HashMap<>();
                                userDataMap.put("email", email);
                                userDataMap.put("name", first_name + last_name);
                                userDataMap.put("id", fId);
                                userDataMap.put("image", img_url);

                                rootRef.child("Users").child(fId).updateChildren(userDataMap)

                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
//                                                                final GoogleUsers users = snapshot.child("Users").child(gId).getValue(GoogleUsers.class);
                                                    Singleton.obj.fbId = fId;

                                                    startActivity(intent);
                                                }
                                            }
                                        });
                            } else {
                                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
//                                          final GoogleUsers users = snapshot.child("Users").child(gId).getValue(GoogleUsers.class);
                                Singleton.obj.fbId = fId;

                                startActivity(intent);

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "first_name,last_name,email,id");
        request.setParameters(parameters);
        request.executeAsync();

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
