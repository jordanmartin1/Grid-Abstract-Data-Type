/**
 * Creation of Grid interface and individual
 * method declarations.
 * I abide by the JMU Honor Code.
 * 
 * @extends Iterable class
 * @author Jordan Martin
 */
public interface Grid<E> extends Iterable<E> {

  public void put(Location loc, E item);
  
  public E get(Location loc);
  
  public E remove(Location loc);
  
  public int numRows();
  
  public int numCols();
  
  public Iterable<Location> allLocations();
  
  public Iterable<Location> itemLocations();
  
  public Iterable<Location> eightNeighbors(Location loc);
  
  public Iterable<Location> fourNeighbors(Location loc);

  public Integer numItems();
  
  
}



