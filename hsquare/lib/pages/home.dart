import 'package:carousel_pro/carousel_pro.dart';
import 'package:flutter/material.dart';
import 'package:hsquare/Components/horizontal_listview.dart';
import 'package:hsquare/Components/porduct.dart';

import 'cart.dart';

class HomePage extends StatefulWidget {
  @override
  _HomePageState createState() => _HomePageState();
}

class _HomePageState extends State<HomePage> {
  @override
  //image carousel
  Widget build(BuildContext context) {
    Widget carosel = Container(
      height: 250,
      child: Carousel(
        //is ku analys krna
        boxFit: BoxFit.fitWidth,
        images: [
          AssetImage('images/products/c1.jpg'),
          AssetImage('images/products/m2.jpg'),
          AssetImage('images/products/m1.jpeg'),
          //  AssetImage('images/products/blazer1.jpeg'),
        ],
        autoplay: true,
        animationCurve: Curves.fastOutSlowIn,
        animationDuration: Duration(milliseconds: 1000),
        dotSize: 5.0,
        indicatorBgPadding: 3.0,
        dotBgColor: Colors.transparent,
      ),
    );

//Structure starts from here

    return Scaffold(
      appBar: AppBar(
        elevation: 0.0,
        centerTitle: true,
        backgroundColor: Colors.red,
        title: Text(
          "Hsquare",
          style: TextStyle(color: Colors.white),
        ),
        actions: <Widget>[
          new IconButton(
              icon: Icon(Icons.search, color: Colors.white), onPressed: () {}),
          new IconButton(
              icon: Icon(Icons.shopping_cart, color: Colors.white),
              onPressed: () {
                Navigator.push(
                    context, MaterialPageRoute(builder: (context) => Cart()));
              }),
        ],
      ),
      drawer: new Drawer(
        child: ListView(
          children: <Widget>[
            //header
            new UserAccountsDrawerHeader(
              accountName: Text("User"),
              accountEmail: Text("uzahid231@gmail.com"),
              currentAccountPicture: GestureDetector(
                  child: CircleAvatar(
                backgroundColor: Colors.grey,
                child: Icon(Icons.person, color: Colors.white),
              )),
              decoration: BoxDecoration(color: Colors.red),
            ),
//body
            InkWell(
              onTap: () {},
              child: ListTile(
                title: Text('Home'),
                leading: Icon(
                  Icons.home,
                  color: Colors.red,
                ),
              ),
            ),
            InkWell(
              onTap: () {},
              child: ListTile(
                title: Text('My Account'),
                leading: Icon(
                  Icons.person,
                  color: Colors.red,
                ),
              ),
            ),
            InkWell(
              onTap: () {},
              child: ListTile(
                title: Text('My Orders'),
                leading: Icon(
                  Icons.shopping_basket,
                  color: Colors.red,
                ),
              ),
            ),
            InkWell(
              onTap: () {
                Navigator.push(
                    context, MaterialPageRoute(builder: (context) => Cart()));
              },
              child: ListTile(
                title: Text('Shopping Cart'),
                leading: Icon(
                  Icons.shopping_cart,
                  color: Colors.red,
                ),
              ),
            ),
            InkWell(
              onTap: () {},
              child: ListTile(
                title: Text('Favorite'),
                leading: Icon(
                  Icons.favorite,
                  color: Colors.red,
                ),
              ),
            ),
            Divider(color: Colors.grey[600]),
            InkWell(
              onTap: () {},
              child: ListTile(
                title: Text('Settings'),
                leading: Icon(Icons.settings, color: Colors.redAccent[700]),
              ),
            ),
            InkWell(
              onTap: () {},
              child: ListTile(
                title: Text('About'),
                leading: Icon(Icons.info, color: Colors.redAccent[700]),
              ),
            ),
          ],
        ),
      ),
      body: Column(
        children: <Widget>[
          //image carousel
          // carosel,
          //padding widget
          Padding(
            padding: EdgeInsets.all(4.0),
            child: Container(
              alignment: Alignment.centerLeft,
              child: Text(
                "Categories",
                style: TextStyle(
                  color: Colors.black,
                  fontSize: 15,
                ),
              ),
            ),
          ),

          //Horizontal Listview
          HorizontalList(),

          //padding widget
          Padding(
            padding: EdgeInsets.all(4.0),
            child: Container(
              alignment: Alignment.centerLeft,
              child: Text(
                "Recent Products",
                style: TextStyle(
                  color: Colors.black,
                  fontSize: 15,
                ),
              ),
            ),
          ),

          //grid view
          Flexible(
            child: Products(),
          ),
        ],
      ),
    );
  }
}
