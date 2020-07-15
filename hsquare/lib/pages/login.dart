import 'package:flutter/material.dart';
import 'package:hsquare/pages/home.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:fluttertoast/fluttertoast.dart';
import 'package:firebase_auth/firebase_auth.dart';
import 'package:google_sign_in/google_sign_in.dart';
import 'package:cloud_firestore/cloud_firestore.dart';


class Login extends StatefulWidget {
  @override
  _LoginState createState() => _LoginState();
}

class _LoginState extends State<Login> {
  final GoogleSignIn google_signin = new GoogleSignIn();
  final FirebaseAuth firebaseAuth = FirebaseAuth.instance;
  SharedPreferences preferences;
  bool loading = false;
  bool isLoggedin = false;

  @override
  void initState() {
    // TODO: implement initState
    super.initState();
    isSignedIn();
  }

//asyns=>>>is going to wait for something that will comes in future while we r waiting u can do anything else
  void isSignedIn() async {
    setState(() {
      loading = true;
    });
    //await=>hold on until u get the value
    preferences = await SharedPreferences.getInstance();
    isLoggedin = await google_signin.isSignedIn();
    //if its true
    if (isLoggedin) {
      //pushreplacement=>agr user aik bar kisi page pe chala jae to use wapis previous page pe nahi ja sakta
      Navigator.pushReplacement(
          context, MaterialPageRoute(builder: (context) => HomePage()));
    }
    setState(() {
      loading = false;
    });
  }

//future=> it is also for waiting something ,async
  Future handleSignIn() async {
    preferences = await SharedPreferences.getInstance();
    setState(() {
      loading = true;
    });
    GoogleSignInAccount googleUser = await google_signin.signIn();
    GoogleSignInAuthentication googleSignInAuthentication =
        await googleUser.authentication;
    FirebaseUser firebaseUser = await firebaseAuth.signInWithGoogle(
        idToken: googleSignInAuthentication.idToken,
        accessToken: googleSignInAuthentication.accessToken);
    if (firebaseUser != null) {
      final QuerySnapshot result = await Firestore.instance
          .collection("users")
          .where("id", isEqualTo: firebaseUser.uid)
          .getDocuments();
      final List<DocumentSnapshot> documents = result.documents;

      if (documents.length == 0) {
        // insert the user to our collection
        Firestore.instance
            .collection("users")
            .document(firebaseUser.uid)
            .setData({
          "id": firebaseUser.uid,
          "username": firebaseUser.displayName,
          "profilePicture": firebaseUser.photoUrl
        });
        await preferences.setString("id", firebaseUser.uid);
        await preferences.setString("username", firebaseUser.displayName);

        //agr koi ghalti hui to idr dekhna he***********************************

        await preferences.setString("photoUrl", firebaseUser.photoUrl);
      } else {
        await preferences.setString("id", documents[0]['id']);
        await preferences.setString("username", documents[0]['username']);
        await preferences.setString("photoUrl", documents[0]['photoUrl']);
      }
      Fluttertoast.showToast(msg: "Logged in Successful");
      setState(() {
        loading = false;
      });
      Navigator.pushReplacement(
          context, MaterialPageRoute(builder: (context) => HomePage()));
    } else {
      Fluttertoast.showToast(msg: "Login Failed!");
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
          backgroundColor: Colors.white,
          centerTitle: true,
          title: Text(
            "Login",
            style: TextStyle(color: Colors.red.shade900),
          ),
          elevation: 0.1),
      body: Stack(
        children: <Widget>[
          Center(
            child: FlatButton(
                onPressed: () {
                  handleSignIn();
                },
                child: Text(
                  "Sign in or Sign up With Google ",
                  style: TextStyle(color: Colors.white),
                )),
          ),
          Visibility(visible: loading ?? true, child: Container(
            color: Colors.white.withOpacity(0.7),
            child: CircularProgressIndicator(
              valueColor:AlwaysStoppedAnimation<Color>(Colors.red),
            ),
          ))
        ],
      ),
    );
  }
}
