import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Creation of AbstractGrid class and individual
 * iterators for the methods.
 * I abide by the JMU Honor Code.
 * 
 * @extends AbstractGrid class
 * @author Jordan Martin
 */

public abstract class AbstractGrid<E> implements Grid<E> {

  /**
   * Check the surrounding eight neighbors of a location.
   * @return - Iterator able to parse eight neighbors
   */
  public Iterable<Location> eightNeighbors(Location loc) {
    int currRow = loc.getRow();
    int currCol = loc.getCol();
    Collection<Location> collection = new ArrayList<Location>();
    
    if (currRow >= numRows() || currCol >= numCols()) {
      throw new IllegalArgumentException();
    }
    
    
    
    if (currRow + 1 < numRows()) {
      //south
      collection.add(new Location(currRow + 1, currCol));
    }
    
    if (currRow - 1 >= 0) {
      //north
      collection.add(new Location(currRow - 1, currCol));
    }
    
    if (currCol + 1 < numCols()) {
      //east
      collection.add(new Location(currRow, currCol + 1));
    }
    
    if (currCol - 1 >= 0) {
      //west
      collection.add(new Location(currRow, currCol - 1));
    }
    
    if (currCol - 1 >= 0 && currRow - 1 >= 0) {
      //northwest
      collection.add(new Location(currRow - 1, currCol - 1));
    }
    
    if (currCol + 1 < numCols() && currRow - 1 >= 0) {
      //northeast
      collection.add(new Location(currRow - 1, currCol + 1));
    }
    
    if (currCol + 1 < numCols() && currRow + 1 < numRows()) {
      //southeast
      collection.add(new Location(currRow + 1, currCol + 1));
    }
    
    if (currCol - 1 >= 0 && currRow + 1 < numRows()) {
      //southwest
      collection.add(new Location(currRow + 1, currCol - 1));
    }
    
    
    return Collections.unmodifiableCollection(collection);
      
  }

  /**
   * Check the surrounding four neighbors.
   * @return - Iterator able to parse four neighbors
   */
  @Override
  public Iterable<Location> fourNeighbors(Location loc) {
    Collection<Location> collection;
    int currRow = loc.getRow();
    int currCol = loc.getCol();
    
    if (currRow >= numRows() || currCol >= numCols()) {
      throw new IllegalArgumentException();
    }
    
    collection = new ArrayList<Location>();
    
    
    
    if (currRow + 1 < numRows()) {
      //south
      collection.add(new Location(currRow + 1, currCol));
    }
    
    if (currRow - 1 >= 0) {
      //north
      collection.add(new Location(currRow - 1, currCol));
    }
    
    if (currCol + 1 < numCols()) {
      //east
      collection.add(new Location(currRow, currCol + 1));
    }
    
    if (currCol - 1 >= 0) {
      //west
      collection.add(new Location(currRow, currCol - 1));
    }
    
    return Collections.unmodifiableCollection(collection);
    
  }

}
