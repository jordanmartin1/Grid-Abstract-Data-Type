import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * JUnit tests for the basic functionality of any class implementing the Grid
 * interface. (Iterators tests are not included.)
 * 
 * @author Nathan Sprague
 * @version V1.0, 1/2019
 *
 */
abstract class GridBasicsTest {

  public abstract <T> Grid<T> makeGrid(int rows, int cols);

  protected Grid<Integer> big;
  protected Grid<Integer> small;
  protected Location l00;
  protected Location l01;
  protected Location l02;
  protected Location l10;
  protected Location l11;
  protected Location l12;
  protected Location l20;
  protected Location l21;
  protected Location l22;

  @BeforeEach
  public void setup() {
    big = makeGrid(3, 5);
    small = makeGrid(3, 2);
    l00 = new Location(0, 0);
    l01 = new Location(0, 1);
    l02 = new Location(0, 2);
    l10 = new Location(1, 0);
    l11 = new Location(1, 1);
    l12 = new Location(1, 2);
    l20 = new Location(2, 0);
    l21 = new Location(2, 1);
    l22 = new Location(2, 2);
  }

  // ---------------------------------------------------------------
  // CONSTRUCTOR TESTS
  // ---------------------------------------------------------------

  @Test
  void testInitialNumItemsIsZero() {
    assertEquals(0, big.numItems());
  }


  @Test
  void testConstructorNegativeDimensions() {
    assertThrows(IllegalArgumentException.class, () -> {
      Grid<Integer> g = makeGrid(0, 1);
    });
    assertThrows(IllegalArgumentException.class, () -> {
      Grid<Integer> g = makeGrid(1, 0);
    });
    assertThrows(IllegalArgumentException.class, () -> {
      Grid<Integer> g = makeGrid(-1, 1);
    });
    assertThrows(IllegalArgumentException.class, () -> {
      Grid<Integer> g = makeGrid(1, -1);
    });
    assertThrows(IllegalArgumentException.class, () -> {
      Grid<Integer> g = makeGrid(1, -1);
    });
  }

  @Test
  void testNumRowsNumCols() {
    assertEquals(3, big.numRows());
    assertEquals(5, big.numCols());
    assertEquals(3, small.numRows());
    assertEquals(2, small.numCols());
  }

  // ---------------------------------------------------------------
  // PUT/GET TESTS
  // ---------------------------------------------------------------
  @Test
  void testPutThrowsExceptionForBadLocation() {
    assertThrows(IllegalArgumentException.class, () -> small.put(new Location(3, 0), 7));
    assertThrows(IllegalArgumentException.class, () -> small.put(new Location(0, 5), 7));
    assertThrows(IllegalArgumentException.class, () -> small.put(new Location(3, 5), 7));
  }

  @Test
  void testGetThrowsExceptionForBadLocation() {
    assertThrows(IllegalArgumentException.class, () -> small.get(new Location(3, 0)));
    assertThrows(IllegalArgumentException.class, () -> small.get(new Location(0, 5)));
    assertThrows(IllegalArgumentException.class, () -> small.get(new Location(3, 5)));
  }

  @Test
  void testPutThrowsExceptionForNullElement() {
    assertThrows(NullPointerException.class, () -> small.put(l00, null));
  }

  @Test
  void testPutTracksSizeChangesCorrectly() {
    assertEquals(0, big.numItems());

    big.put(l00, 20);
    assertEquals(1, big.numItems());

    big.put(l00, 30);
    assertEquals(1, big.numItems());

    big.put(l01, 20);
    assertEquals(2, big.numItems());
  }


  @Test
  void testPutThenGetReturnsCorrectItem() {

    int val = 17;
    for (int row = 0; row < 3; row++) {
      for (int col = 0; col < 5; col++) {
        big.put(new Location(row, col), val);
        val += 1;
      }
    }

    val = 17;
    for (int row = 0; row < 3; row++) {
      for (int col = 0; col < 5; col++) {
        assertEquals(Integer.valueOf(val), big.get(new Location(row, col)));
        assertEquals(Integer.valueOf(val), big.get(new Location(row, col)));
        val += 1;
      }
    }
  }

  @Test
  void testGetReturnsNullForEmptyCell() {
    for (int row = 0; row < 3; row++) {
      for (int col = 0; col < 5; col++) {
        assertEquals(null, big.get(new Location(row, col)));
      }
    }

  }

  // ---------------------------------------------------------------
  // REMOVE TESTS
  // ---------------------------------------------------------------
  @Test
  void testRemoveThrowsExceptionForBadLocation() {
    assertThrows(IllegalArgumentException.class, () -> small.remove(new Location(3, 0)));
    assertThrows(IllegalArgumentException.class, () -> small.remove(new Location(0, 5)));
    assertThrows(IllegalArgumentException.class, () -> small.remove(new Location(3, 5)));
  }

  @Test
  void testRemoveTracksSizeChangesCorrectly() {
    assertEquals(0, big.numItems());

    big.put(l00, 20);
    assertEquals(1, big.numItems());

    big.put(l01, 20);
    assertEquals(2, big.numItems());

    big.remove(l00);
    assertEquals(1, big.numItems());
    big.remove(l00);
    assertEquals(1, big.numItems());
    big.remove(l01);
    assertEquals(0, big.numItems());
  }

  @Test
  void testRemoveReturnsNullForEmptyCell() {
    assertEquals(null, small.remove(l11));
  }

  @Test
  void testRemoveReturnsNullAfterItemRemoved() {
    assertEquals(0, big.numItems());

    big.put(l00, 20);
    assertEquals(1, big.numItems());

    big.put(l01, 20);
    assertEquals(2, big.numItems());

    big.remove(l00);
    assertEquals(null, big.remove(new Location(0, 0)));

    big.remove(l01);
    assertEquals(null, big.remove(new Location(0, 1)));

  }

}
