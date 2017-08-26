package ru.job4j.loop;

/**
*Class for rendering pyramid.
*@author vgrigoryev
*@since 26.08.2017
*@version 1
*/
public class Paint {
   /**
     *This method is used for building pyramid.
	 *@param h This parameter represents the number of levels in the pyramid
	 *@return pyramid in String representation
	 */
    public String piramid(int h) {
	    final String line = System.getProperty("line.separator");
	    StringBuilder builder = new StringBuilder();
	    int caret = 2 * (h - 1) + 1; //количество галочек на самом нижнем уровне для значения h
	    int numOfWhitespaces = 0; //количество пробелов на уровне на одной стороне
	    for (int i = 0; i < h; i++) {
		    numOfWhitespaces =  (caret - (2 * i + 1)) / 2;
	        for (int j = 0; j < caret; j++) {
		        if (j < numOfWhitespaces) {
			        builder.append(' ');
			    } else if (j >= numOfWhitespaces + 2 * i + 1) {
			        builder.append(' ');
			    } else {
				    builder.append('^');
			    }
		    }
		    if (i != h - 1) {
				builder.append(line);
			}
	    }
	    return builder.toString();
    }
}