import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';

class HorizontalList extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Container(
      height: 40.0,
      child: ListView(
        scrollDirection: Axis.horizontal,
        children: <Widget>[
          Categories(
            image_location: "images/categories/tshirt.png",
            image_caption: "Shirt",
          ),
          Categories(
            image_location: "images/categories/dress.png",
            image_caption: "Dress",
          ),
          Categories(
            image_location: "images/categories/formal.png",
            image_caption: "Formal",
          ),
          Categories(
            image_location: "images/categories/informal.png",
            image_caption: "Informal",
          ),
          Categories(
            image_location: "images/categories/jeans.png",
            image_caption: "Pants",
          ),
          Categories(
            image_location: "images/categories/shoe.png",
            image_caption: "Shoes",
          ),
          Categories(
            image_location: "images/categories/accessories.png",
            image_caption: "Accessories",
          )
        ],
      ),
    );
  }
}

class Categories extends StatelessWidget {
  String image_location;
  String image_caption;

  Categories({this.image_location, this.image_caption});
  @override
  Widget build(BuildContext context) {
    return Padding(
        padding: const EdgeInsets.all(0.0),
        child: InkWell(
          onTap: () {},
          child: Container(
            width: 80.0,
            child: ListTile(
              title: Image.asset(image_location, width: 40.0, height: 40.0),
              subtitle: Container(
                alignment: Alignment.topCenter,
                child: Text(
                  image_caption,
                  style: TextStyle(color: Colors.grey[800]),
                ),
              ),
            ),
          ),
        ));
  }
}
