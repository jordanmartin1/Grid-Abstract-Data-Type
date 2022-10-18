import java.util.Iterator;
import java.util.NoSuchElementException;


/**
 * Creation of ArrayGrid class and individual
 * iterators for the methods.
 * I abide by the JMU Honor Code.
 * 
 * @extends AbstractGrid class
 * @author Jordan Martin
 */

@SuppressWarnings("rawtypes")
public class ArrayGrid<E> extends AbstractGrid<E> {
  
  private E[][] data;
  private int numItems;
  
  
  /**
   * Constructor for ArrayGrid class.
   * @param rows - number of rows
   * @param cols - number of columns
   */
  @SuppressWarnings("unchecked")
  public ArrayGrid(int rows, int cols) {
    if (rows <= 0 || cols <= 0) {
      throw new IllegalArgumentException();
    }
    data = (E[][]) new Object[rows][cols];
    numItems = 0;
    
    
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

      
      /**
       * Check if a grid has a next spot.
       * @return - boolean depending on result
       */
      @Override
      public boolean hasNext() {
        for (int row = currPosY; row < numRows(); row++) {
          for (int col = currPosX; col < numCols(); col++) {
            if (data[row][col] != null && last != data[row][col]) {
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
          last = data[currPosY][currPosX];
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
        
        if (data[currPosY][currPosX] != null) {
          data[currPosY][currPosX] = null;
          numItems--;
        }
        
      }
    
    
    };
  }
  
  /**
   * Put the parameter item into the designated spot.
   * @param loc -  location for object to be placed
   * @param item - item to be placed
   */
  @SuppressWarnings("unchecked")
  public void put(Location loc, Object item) {
    if (loc == null || item == null) {
      throw new NullPointerException();
    }
    
    if (loc.getRow() == 0 && loc.getCol() == 0) {
      if (data[0][0] == null) {
        data[0][0] = (E) item;
        numItems++;
      }

    }
    
    if (loc.getRow() < data.length && loc.getCol() < data[0].length) {
      if (data[loc.getRow()][loc.getCol()] == null) {
        data[loc.getRow()][loc.getCol()] = (E) item;
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
    if (loc.getRow() >= numRows() || loc.getCol() >= numCols()) {
      throw new IllegalArgumentException();
    } else if (loc == null || data[loc.getRow()][loc.getCol()] == null) {
      return null;
    }
    
    
    return data[loc.getRow()][loc.getCol()];
    
    
     
  }

  /**
   * Remove object at respective location.
   * @param loc - location to remove
   * @return - removed value
   */
  public E remove(Location loc) {
    if (loc == null) {
      throw new IllegalArgumentException();
    }
    
    if (loc.getRow() < data.length && loc.getCol() < data[0].length) {
      if (data[loc.getRow()][loc.getCol()] != null) {
        E element = data[loc.getRow()][loc.getCol()];
        data[loc.getRow()][loc.getCol()] = null;
        numItems--;
        return element;
      } else {
        return null;
      }

    }
    
    throw new IllegalArgumentException();
    
    
  }

  /**
   * Iterator for all item locations.
   * @return - Iterable object for all items
   */
  public Iterable<Location> itemLocations() {
    return new ItemIterator();
  }
  
  /**
   * Creation of ItemIterator class and individual
   * overridden iterator methods.
   * @implements iterable class
   */
  private class ItemIterator implements Iterable<Location> {

    /**
     * Iterator constructor.
     * @return - Iterator able to parse item locations
     */
    @Override
    public Iterator<Location> iterator() {
      return new Iterator<Location>() {
        
        private Location currLoc = new Location(0, 0);
        private int currRow = currLoc.getRow();
        private int currCol = currLoc.getCol();
        private boolean currEnd = false;

        /**
         * Check if there is a next spot in the grid with an item.
         * @return - boolean depending on result
         */
        @Override
        public boolean hasNext() {
            for (int row = currRow; row < numRows(); row++) {
              for (int col = currCol; col < numCols(); col++) {
                if (data[row][col] != null) {
                  currCol = col;
                  currRow = row;
                  return true;
                }
              }
            }
            
            return false;
          }

        /**
         * Move to the next spot in the grid with an item.
         * @return - previous value
         */
        @Override
        public Location next() {
          if (hasNext() && !currEnd) {
            currLoc = new Location(currRow, currCol);
            currCol++;
            return currLoc;
          } else if (hasNext() && currEnd) {
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
   * Get the number of rows.
   * @return - number of rows
   */
  public int numRows() {
    return data.length;
  }

  /**
   * Get the number of columns.
   * @return - number of columns
   */
  public int numCols() {
    return data[0].length;
  }

  /**
   * Iterator to iterate through all locations.
   * @return - Iterable object
   */
  @SuppressWarnings("unchecked")
  public Iterable allLocations() {

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


}
