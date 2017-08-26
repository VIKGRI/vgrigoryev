package ru.job4j.loop;

/**
*Class for rendering virtual board.
*@author vgrigoryev
*@since 26.08.2017
*@version 1
*/
public class Board {
   /**
     *This method is used for building virtual chess board.
	 *@param width This is the first parameter which represents width of the chess board
	 *@param height This is the first parameter which represents height of the chess board
	 *@return board in String representation
	 */
   public String paint(int width, int height) {
	  final String line = System.getProperty("line.separator");
	  StringBuilder builder = new StringBuilder();
	  for (int i = 0; i < height; i++) {
	      for (int j = 0; j < width; j++) {
		     if ((i * width + j) % 2 == 0) {
			     builder.append('x');
			 } else {
			     builder.append(' ');
			 }
		  }
		  builder.append(line);
	  }
	  return builder.toString();
   }
}