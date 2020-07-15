import 'package:flutter/material.dart';
import 'package:hsquare/Components/porduct.dart';

class Cart_products extends StatefulWidget {
  @override
  _Cart_productsState createState() => _Cart_productsState();
}

class _Cart_productsState extends State<Cart_products> {
  var Products_on_the_cart = [
    {
      "name": "Blazer",
      "picture": "images/products/blazer1.jpeg",
      "price": 85,
      "size": "M",
      "color": "Black",
      "quantity": 1,
    },
    {
      "name": "Shoes",
      "picture": "images/products/hills1.jpeg",
      "price": 85,
      "size": "M",
      "color": "Red",
      "quantity": 1,
    },
  ];
  @override
  Widget build(BuildContext context) {
    return ListView.builder(
        itemCount: Products_on_the_cart.length,
        itemBuilder: (context, index) {
          return Single_cart_product(
            cart_prod_name: Products_on_the_cart[index]["name"],
            cart_prod_color: Products_on_the_cart[index]["color"],
            cart_prod_qty: Products_on_the_cart[index]["quantity"],
            cart_prod_size: Products_on_the_cart[index]["size"],
            cart_prod_price: Products_on_the_cart[index]["price"],
            cart_prod_picture: Products_on_the_cart[index]["picture"],
          );
        });
  }
}

class Single_cart_product extends StatelessWidget {
  final cart_prod_name;
  final cart_prod_price;
  final cart_prod_color;
  final cart_prod_qty;
  final cart_prod_size;
  final cart_prod_picture;

  Single_cart_product({
    this.cart_prod_name,
    this.cart_prod_price,
    this.cart_prod_color,
    this.cart_prod_qty,
    this.cart_prod_size,
    this.cart_prod_picture,
  });
  @override
  Widget build(BuildContext context) {
    return Card(
      child: ListTile(
        //Leading Section***
        leading: Container(
          height: 100.0,
          width: 100.0,
          child: Image.asset(
            cart_prod_picture,
            width: 70.0,
            height: 70.0,
          ),
        ),

        //title Section
        title: Text(cart_prod_name),
        //subtitle Section
        subtitle: Column(
          //row inside the column
          children: <Widget>[
            Row(
              children: <Widget>[
                //section for the size of the products
                Padding(
                    padding: const EdgeInsets.all(8.0), child: Text("Size:")),
                Padding(
                    padding: const EdgeInsets.all(4.0),
                    child: Text(
                      cart_prod_size,
                      style: TextStyle(color: Colors.red),
                    )),

                //***Product color section****

                Padding(
                  padding: const EdgeInsets.fromLTRB(20.0, 8.0, 8.0, 8.0),
                  child: Text("Color"),
                ),
                Padding(
                    padding: const EdgeInsets.all(4.0),
                    child: Text(
                      cart_prod_color,
                      style: TextStyle(color: Colors.red),
                    ))
              ],
            ),

            //***Product price Section***

            Container(
              alignment: Alignment.topLeft,
              child: Text(
                "\$${cart_prod_price}",
                style: TextStyle(
                    fontSize: 17.0,
                    fontWeight: FontWeight.bold,
                    color: Colors.red),
              ),
            )
          ],
        ),
        trailing: FittedBox(
          fit: BoxFit.fill,
          child: Column(
            children: <Widget>[
              IconButton(
                icon: Icon(
                  Icons.arrow_drop_up,
                  color: Colors.red,
                ),
                onPressed: () {},
                iconSize: 38.0,
              ),
              Text(
                "$cart_prod_qty",
                style: TextStyle(fontSize: 25.0, fontWeight: FontWeight.bold),
              ),
              IconButton(
                icon: Icon(
                  Icons.arrow_drop_down,
                  color: Colors.red,
                ),
                onPressed: () {},
                iconSize: 38.0,
              ),
            ],
          ),
        ),
      ),
    );
  }

  void addQuantity() {}
}
