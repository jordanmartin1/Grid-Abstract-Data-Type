import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * Creation of SparseGrid class and individual
 * iterators for the methods.
 * I abide by the JMU Honor Code.
 * 
 * @extends AbstractGrid class
 * @author Jordan Martin
 */
public class SparseGrid<E> extends AbstractGrid<E> {
  
  private HashMap<Location, E> data;
  private int numRows;
  private int numCols;
  private int numItems;
  
  /**
   * Constructor for SparseGrid class.
   * @param rows - number of rows
   * @param cols - number of columns
   */
  public SparseGrid(int rows, int cols) {
    if (rows <= 0 || cols <= 0) {
      throw new IllegalArgumentException();
    }
    numRows = rows;
    numCols = cols;
    numItems = 0;
    data = new HashMap<Location, E>();
  }
  
  /**
   * Constructor for default iterator.
   * @return - a new Iterator
   */
  public Iterator<E> iterator() {
    return new Iterator<E>() {
    
      private int currPosX = 0;
      private int currPosY = 0;
      private E last = null;
      private Location loc = new Location(0, 0);

      /**
       * Check if a grid has a next spot.
       * @return - boolean depending on result
       */
      @Override
      public boolean hasNext() {
        for (int row = currPosY; row < numRows(); row++) {
          for (int col = currPosX; col < numCols(); col++) {
            loc = new Location(row, col);
            if (data.get(loc) != null && last != data.get(loc)) {
              currPosX = col;
              currPosY = row;
              return true;
            }
          }
        }
        
        return false;
      }
      
      /**
       * Move to the next spot in the grid.
       * @return - previous value
       */
      @Override
      public E next() {
        if (!hasNext()) {
          throw new NoSuchElementException();
        } else {
          last = data.get(loc);
          return last;
        }
      }
      
      /**
       * Remove the object at the current position.
       */
      @Override
      public void remove() {
        if (numItems <= 0) {
          throw new IllegalStateException();
        }
        
        data.remove(loc);
        numItems--;
      }
    };
  }

  /**
   * Put the parameter item into the designated spot.
   * @param loc -  location for object to be placed
   * @param item - item to be placed
   */
  public void put(Location loc, E item) {
    if (loc == null || item == null) {
      throw new NullPointerException();
    }
    
    if (loc.getRow() < numRows && loc.getCol() < numCols) {
      if (data.get(loc) != null) {
        data.replace(loc, item);
      } else {
        data.put(loc, item);
        numItems++;
      }
      
    } else {
      throw new IllegalArgumentException();
    }
  }

  /**
   * Get object at respective position.
   * @param loc - location to be grabbed
   * @return - grabbed value
   */
  public E get(Location loc) {
    if (loc.getRow() >= numRows() || loc.getCol() >= numCols) {
      throw new IllegalArgumentException();
    } else if (loc == null || data.get(loc) == null) {
      return null;
    }
    
    return data.get(loc);
    
  }


  /**
   * Remove object at respective location.
   * @param loc - location to remove
   * @return - removed value
   */
  public E remove(Location loc) {
    if (loc == null || loc.getCol() >= numCols || loc.getRow() >= numRows) {
      throw new IllegalArgumentException();
      
    }   
    
    if (data.get(loc) != null) {
      numItems--;
      return data.remove(loc);
    }
    
    return null;

  }

  /**
   * Get the number of rows.
   * @return - number of rows
   */
  public int numRows() {
    return numRows;
  }

  /**
   * Get the number of columns.
   * @return - number of columns
   */
  public int numCols() {
    return numCols;
  }

  /**
   * Iterator to iterate through all locations.
   * @return - Iterable object
   */
  public Iterable<Location> allLocations() {
    return new LocationIterator();
   
  }
  
  /**
   * Creation of LocationIterator class and individual
   * overridden iterator methods.
   * @implements iterable class
   */
  private class LocationIterator implements Iterable<Location> {

    /**
     * Constructor for new location iterator.
     * @return - Object that iterates through locations
     */
    @Override
    public Iterator<Location> iterator() {
      return new Iterator<Location>() {
        
        private Location currLoc = new Location(0, 0);
        private int currRow = currLoc.getRow();
        private int currCol = currLoc.getCol();
        private boolean currEnd = false;

        /**
         * Check if there is a next location.
         * @return - boolean depending on the result
         */
        @Override
        public boolean hasNext() {
          if (currCol + 1 >= numCols() && currRow + 1 < numRows()) {
            currEnd = true;
            return true;
          } else if (currRow >= numRows() || currCol >= numCols()) {
            return false;
          } else {
            return true;
          }
          
        
        }

        /**
         * Move to the next location.
         * @return - previous location
         */
        @Override
        public Location next() {
          if (hasNext() && !currEnd) {
            currLoc = new Location(currRow, currCol);
            currCol++;
            return currLoc;
          } else if (currEnd) {
            currLoc = new Location(currRow, currCol);
            currRow = currLoc.getRow() + 1;
            currCol = 0;
            currEnd = false;
            return currLoc;
          } 
          
          throw new NoSuchElementException();
          
        }
        
        /**
         * Remove method; only implemented in default iterator.
         */
        public void remove() {
          throw new UnsupportedOperationException();
        }
        
      };
    
    }
    
  }

 
  /**
   * Get number of items in the grid.
   * @return - number of items
   */
  public Integer numItems() {
    return numItems;
  }


  @Override
  public Iterable<Location> itemLocations() {
    return new Iterable<Location>() {

      @Override
        public Iterator<Location> iterator() {
          Set<Location> loc = Collections.unmodifiableSet(data.keySet());
          return loc.iterator();
        }
    
    };
  }
}

