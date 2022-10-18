import static org.junit.jupiter.api.Assertions.*;

import java.util.Iterator;
import org.junit.jupiter.api.Test;

/**
 * JUnit tests for the iterator functionality of the SparseGrid class.
 * 
 * @author Nathan Sprague
 * @version V1.0, 1/2019
 *
 */
class SparseGridIteratorsTest extends GridIteratorsTest {

  @Override
  public <T> Grid<T> makeGrid(int rows, int cols) {
    return new SparseGrid<T>(rows, cols);
  }

  @Test
  void testItemLocationIteratorHugeSparseGrid() {
    Grid<String> huge = makeGrid(1000000, 1000000);
    huge.put(new Location(999999, 999999), "A");
    Iterator<Location> it = huge.itemLocations().iterator();
    assertEquals(new Location(999999, 999999), it.next());
  }
}
