import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * JUnit tests for the basic functionality of the SparseGrid class.
 * 
 * @author Nathan Sprague
 * @version V1.0, 1/2019
 *
 */
class SparseGridBasicsTest extends GridBasicsTest {

  @Override
  public <T> Grid<T> makeGrid(int rows, int cols) {
    return new SparseGrid<T>(rows, cols);
  }

  @Test
  void testPutGetandRemoveWorkForHugeGrids() {
    Grid<String> grid = makeGrid(1000000, 1000000);
    grid.put(new Location(999999, 999999), "A");
    assertEquals("A", grid.get(new Location(999999, 999999)));
    assertEquals("A", grid.remove(new Location(999999, 999999)));
    assertEquals(null, grid.remove(new Location(999999, 999999)));
  }
}
