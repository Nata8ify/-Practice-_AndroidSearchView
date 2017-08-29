# -Practice-_AndroidSearchView
1. Get the Color's List from URL
2. Parse to ArrayList<Color> 
  ** toString of Color Object returns color (red, green, blue, ..)
3. Initial all view objects. (ListView is included)
4. Create ArrayAdapter<String> and set ArrayList<Color>  for ListView 
5. Adapt the ListView with ArrayAdapter<String>
6. Set SearchView Listener (textChange, submit)

7. If textChange or submit then perform filterColorList(String nameLike)
8. Notify ListView for data changed by its adapter object

9.If restore button is clicked then restore all color data and notify ListView for data changed by its adapter object 
