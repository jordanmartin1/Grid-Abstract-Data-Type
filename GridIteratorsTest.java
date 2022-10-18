import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * JUnit tests for the iterators of any class implementing the Grid interface.
 * 
 * @author Nathan Sprague
 * @version V1.0, 1/2019
 *
 */
abstract class GridIteratorsTest {

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

  /**
   * Returns true if the two lists contain exactly the same elements. The elements
   * need not be in the same order.
   * 
   * @param listA The first list
   * @param listB The second list
   * @return True if listA and listB contain the same elements.
   */
  protected static <T extends Comparable<? super T>> void sameItems(List<T> listA, List<T> listB) {
    Collections.sort(listA);
    Collections.sort(listB);
    assertEquals(listA, listB);

  }
  
  

  // ---------------------------------------------------------------
  // DEFAULT ITERATOR TESTS
  // ---------------------------------------------------------------
  @Test
  void testDefaultIteratorForLoopAllCellsFull() {
    int val = 17;
    ArrayList<Integer> correct = new ArrayList<>();

    for (int row = 0; row < 3; row++) {
      for (int col = 0; col < 5; col++) {
        big.put(new Location(row, col), val);
        correct.add(val);
        val += 1;
      }
    }

    ArrayList<Integer> iterated = new ArrayList<>();

    for (Integer cur : big) {
      iterated.add(cur);
    }

    sameItems(correct, iterated);
  }

  @Test
  void testDefaultIteratorForLoopSomeCellsFull() {
    int val = 17;
    ArrayList<Integer> correct = new ArrayList<>();

    // Skip some entries...
    for (int row = 2; row < 3; row++) {
      for (int col = 2; col < 5; col++) {
        big.put(new Location(row, col), val);
        correct.add(val);
        val += 1;
      }
    }

    ArrayList<Integer> iterated = new ArrayList<>();

    for (Integer cur : big) {
      iterated.add(cur);
    }

    sameItems(correct, iterated);
  }

  @Test
  void testDefaultIteratorThrowsNoSuchElementException() {
    big.put(l00, 15);
    Iterator<Integer> it = big.iterator();
    it.next();
    assertThrows(NoSuchElementException.class, () -> it.next());
  }

  
  @Test
  void testDefaultIteratorHasNextAndNext() {
    big.put(l00, 15);
    big.put(l10, 16);

    Iterator<Integer> it = big.iterator();
    assertTrue(it.hasNext());
    assertEquals(Integer.valueOf(15), it.next());
    assertTrue(it.hasNext());
    assertEquals(Integer.valueOf(16), it.next());
  }
  
  

  @Test
  void testDefaultIteratorRemove() {
    big.put(new Location(0, 0), 15);
    big.put(new Location(1, 0), 16);
    big.put(new Location(1, 1), 17);
    big.put(new Location(1, 2), 18);
    big.put(new Location(1, 3), 19);

    Iterator<Integer> it = big.iterator();
    while (it.hasNext()) {
      int val = it.next();
      if (val > 15 && val < 18) {
        it.remove();
      }
    }

    ArrayList<Integer> correct = new ArrayList<>();
    correct.add(15);
    correct.add(18);
    correct.add(19);

    ArrayList<Integer> iterated = new ArrayList<>();
    for (Integer cur : big) {
      iterated.add(cur);
    }
    
    sameItems(correct, iterated);
    assertEquals(3, big.numItems());
  }

  
  @Test
  void testDefaultIteratorRemoveIllegalStateException() {
    big.put(l00, 15);
    Iterator<Integer> it = big.iterator();
    it.next();
    it.remove();
    assertThrows(IllegalStateException.class, () -> it.remove());
  }
  
  
  

  // ---------------------------------------------------------------
  // ALL LOCATIONS TESTS
  // ---------------------------------------------------------------
  @Test
  void testAllLocationIteratorThrowsNoSuchElementException() {
    Grid<String> grid = makeGrid(1, 1);
    Iterator<Location> it = grid.allLocations().iterator();
    it.next();
    assertThrows(NoSuchElementException.class, () -> it.next());
  }
  

  @Test
  void testAllLocationIteratorDoesNotReturnCollection() {
    assertFalse(big.allLocations() instanceof Collection);
  }

  @Test
  void testAllLocationIteratorRemoveThrowsException() {
    Iterator<Location> it = big.allLocations().iterator();
    it.next();
    assertThrows(UnsupportedOperationException.class, () -> it.remove());
  }
  
  

  @Test
  void testAllLocationsEmptyOneByOneGrid() {
    Grid<String> grid = makeGrid(1, 1);

    List<Location> correct;
    ArrayList<Location> iterated = new ArrayList<>();

    for (Location loc : grid.allLocations()) {
      iterated.add(loc);
    }
    correct = new ArrayList<Location>(List.of(l00));
    sameItems(correct, iterated);
  }
  
  

  @Test
  void testAllLocationsFullOneByOneGrid() {
    Grid<String> grid = makeGrid(1, 1);
    grid.put(l00, "A");

    List<Location> correct;
    ArrayList<Location> iterated = new ArrayList<>();

    for (Location loc : grid.allLocations()) {
      iterated.add(loc);
    }
    correct = new ArrayList<Location>(List.of(l00));
    sameItems(correct, iterated);
  }
  

  @Test
  void testAllLocationsTwoByThreeGrid() {
    Grid<String> grid = makeGrid(2, 3);
    grid.put(l00, "A");

    List<Location> correct;
    ArrayList<Location> iterated = new ArrayList<>();

    for (Location loc : grid.allLocations()) {
      iterated.add(loc);
    }
    correct = new ArrayList<Location>(List.of(l00, l01, l02, l10, l11, l12));
    assertEquals(correct, iterated);
  }
  
  

  // ---------------------------------------------------------------
  // ITEM LOCATIONS TESTS
  // ---------------------------------------------------------------
  @Test
  void testItemLocationIteratorThrowsNoSuchElementException() {
    big.put(l11, 7);
    Iterator<Location> it = big.itemLocations().iterator();
    it.next();
    assertThrows(NoSuchElementException.class, () -> it.next());
  }

  @Test
  void testItemLocationIteratorThrowsNoSuchElementExceptionEmptyGrid() {
    Iterator<Location> it = big.itemLocations().iterator();
    assertThrows(NoSuchElementException.class, () -> it.next());
  }

  @Test
  void testItemLocationIteratorDoesNotReturnCollection() {
    big.put(new Location(1, 1), 7);
    assertFalse(big.itemLocations() instanceof Collection);
  }

  @Test
  void testItemLocationIteratorRemoveThrowsException() {
    big.put(l11, 7);
    Iterator<Location> it = big.itemLocations().iterator();
    it.next();
    assertThrows(UnsupportedOperationException.class, () -> it.remove());
  }

  @Test
  void testItemLocationIteratorFullOneByOneGrid() {
    Grid<String> grid = makeGrid(1, 1);
    grid.put(l00, "B");
    Iterator<Location> it = grid.itemLocations().iterator();
    assertEquals(l00, it.next());
  }
  
  

  @Test
  void testItemLocationIteratorFullGrid() {
    Grid<String> grid = makeGrid(2, 3);
    for (int row = 0; row < 2; row++) {
      for (int col = 0; col < 3; col++) {
        grid.put(new Location(row, col), "B");
      }
    }

    List<Location> correct;
    ArrayList<Location> iterated = new ArrayList<>();
    for (Location loc : grid.itemLocations()) {
      iterated.add(loc);
    }
    correct = new ArrayList<Location>(List.of(l00, l01, l02, l10, l11, l12));
    sameItems(correct, iterated);
  }
  
  

  @Test
  void testItemLocationIteratorPartiallyFullGrid() {
    Grid<String> grid = makeGrid(3, 3);
    grid.put(l00, "A");
    grid.put(l22, "A");
    grid.put(l02, "A");
    grid.put(l20, "A");
    grid.put(l11, "A");

    List<Location> correct;
    ArrayList<Location> iterated = new ArrayList<>();
    for (Location loc : grid.itemLocations()) {
      iterated.add(loc);
    }
    correct = new ArrayList<Location>(List.of(l00, l22, l02, l20, l11));
    sameItems(correct, iterated);
  }
  
  

  // ---------------------------------------------------------------
  // EIGHT NEIGHBOR TESTS
  // ---------------------------------------------------------------
  @Test
  void testEightNeighborBadLocationThrowsException() {
    Grid<String> grid = makeGrid(1, 1);
    assertThrows(IllegalArgumentException.class, () -> grid.eightNeighbors(l01));
    assertThrows(IllegalArgumentException.class, () -> grid.eightNeighbors(l10));
    assertThrows(IllegalArgumentException.class, () -> grid.eightNeighbors(l11));
  }

  @Test
  void testEightNeighborOneByOneGrid() {
    Grid<String> grid = makeGrid(1, 1);
    for (Location loc : grid.eightNeighbors(l00)) {
      fail();
    }
  }

  @Test
  void testEightNeighborIteratorRemoveThrowsException() {
    Iterator<Location> it = big.eightNeighbors(l00).iterator();
    it.next();
    assertThrows(UnsupportedOperationException.class, () -> it.remove());
  }

  @Test
  void testEightNeighborTwoByTwoGrid() {
    Grid<String> grid = makeGrid(2, 2);

    List<Location> correct;
    ArrayList<Location> iterated = new ArrayList<>();

    for (Location loc : grid.eightNeighbors(l00)) {
      iterated.add(loc);
    }
    correct = new ArrayList<Location>(List.of(l01, l10, l11));
    sameItems(correct, iterated);

    iterated.clear();
    for (Location loc : grid.eightNeighbors(l11)) {
      iterated.add(loc);
    }
    correct = new ArrayList<Location>(List.of(l01, l10, l00));
    sameItems(correct, iterated);

    iterated.clear();
    for (Location loc : grid.eightNeighbors(l01)) {
      iterated.add(loc);
    }
    correct = new ArrayList<Location>(List.of(l00, l11, l10));
    sameItems(correct, iterated);

    iterated.clear();
    for (Location loc : grid.eightNeighbors(l10)) {
      iterated.add(loc);
    }
    correct = new ArrayList<Location>(List.of(l00, l11, l01));
    sameItems(correct, iterated);
  }


  @Test
  void testEightNeighborOneByThreeGrid() {
    Grid<String> grid = makeGrid(1, 3);

    List<Location> correct;
    ArrayList<Location> iterated = new ArrayList<>();

    for (Location loc : grid.eightNeighbors(l00)) {
      iterated.add(loc);
    }
    correct = new ArrayList<Location>(List.of(l01));
    sameItems(correct, iterated);

    iterated.clear();
    for (Location loc : grid.eightNeighbors(l01)) {
      iterated.add(loc);
    }
    correct = new ArrayList<Location>(List.of(l00, l02));
    sameItems(correct, iterated);

    iterated.clear();
    for (Location loc : grid.eightNeighbors(l02)) {
      iterated.add(loc);
    }
    correct = new ArrayList<Location>(List.of(l01));
    sameItems(correct, iterated);
  }

  @Test
  void testEightNeighborThreeByOneGrid() {
    Grid<String> grid = makeGrid(3, 1);

    List<Location> correct;
    ArrayList<Location> iterated = new ArrayList<>();

    for (Location loc : grid.eightNeighbors(l00)) {
      iterated.add(loc);
    }
    correct = new ArrayList<Location>(List.of(l10));
    sameItems(correct, iterated);

    iterated.clear();
    for (Location loc : grid.eightNeighbors(l10)) {
      iterated.add(loc);
    }
    correct = new ArrayList<Location>(List.of(l00, l20));
    sameItems(correct, iterated);


    iterated.clear();
    for (Location loc : grid.eightNeighbors(l20)) {
      iterated.add(loc);
    }
    correct = new ArrayList<Location>(List.of(l10));
    sameItems(correct, iterated);
  }

  @Test
  void testEightNeighborThreeBy5Grid() {
    Grid<String> grid = makeGrid(3, 5);

    List<Location> correct;
    ArrayList<Location> iterated = new ArrayList<>();

    for (Location loc : grid.eightNeighbors(new Location(0, 1))) {
      iterated.add(loc);
    }
    correct = new ArrayList<Location>(List.of(new Location(1, 2), new Location(1, 0),
        new Location(0, 0), new Location(1, 1), new Location(0, 2)));
    sameItems(correct, iterated);

    iterated.clear();
    for (Location loc : grid.eightNeighbors(new Location(1, 3))) {
      iterated.add(loc);
    }
    correct = new ArrayList<Location>(
        List.of(new Location(1, 2), new Location(0, 3), new Location(1, 4), new Location(2, 3),
            new Location(0, 2), new Location(0, 4), new Location(2, 2), new Location(2, 4)));
    sameItems(correct, iterated);
  }

  
  // ---------------------------------------------------------------
  // FOUR NEIGHBOR TESTS
  // ---------------------------------------------------------------
  @Test
  void testFourNeighborOneByOneGrid() {
    Grid<String> grid = makeGrid(1, 1);
    for (Location loc : grid.fourNeighbors(l00)) {
      fail();
    }
  }

  @Test
  void testFourNeighborIteratorRemoveThrowsException() {
    Iterator<Location> it = big.fourNeighbors(l00).iterator();
    it.next();
    assertThrows(UnsupportedOperationException.class, () -> it.remove());
  }

  @Test
  void testFourNeighborBadLocationThrowsException() {
    Grid<String> grid = makeGrid(1, 1);
    assertThrows(IllegalArgumentException.class, () -> grid.fourNeighbors(l01));
    assertThrows(IllegalArgumentException.class, () -> grid.fourNeighbors(l10));
    assertThrows(IllegalArgumentException.class, () -> grid.fourNeighbors(l11));
  }

  @Test
  void testFourNeighborTwoByTwoGrid() {
    Grid<String> grid = makeGrid(2, 2);

    List<Location> correct;
    ArrayList<Location> iterated = new ArrayList<>();

    for (Location loc : grid.fourNeighbors(l00)) {
      iterated.add(loc);
    }
    correct = new ArrayList<Location>(List.of(l01, l10));
    sameItems(correct, iterated);

    iterated.clear();
    for (Location loc : grid.fourNeighbors(l11)) {
      iterated.add(loc);
    }
    sameItems(correct, iterated);

    iterated.clear();
    for (Location loc : grid.fourNeighbors(l01)) {
      iterated.add(loc);
    }
    correct = new ArrayList<Location>(List.of(l00, l11));
    sameItems(correct, iterated);

    iterated.clear();
    for (Location loc : grid.fourNeighbors(l10)) {
      iterated.add(loc);
    }
    sameItems(correct, iterated);
  }


  @Test
  void testFourNeighborOneByThreeGrid() {
    Grid<String> grid = makeGrid(1, 3);

    List<Location> correct;
    ArrayList<Location> iterated = new ArrayList<>();

    for (Location loc : grid.fourNeighbors(l00)) {
      iterated.add(loc);
    }
    correct = new ArrayList<Location>(List.of(l01));
    sameItems(correct, iterated);

    iterated.clear();
    for (Location loc : grid.fourNeighbors(l01)) {
      iterated.add(loc);
    }
    correct = new ArrayList<Location>(List.of(l00, l02));
    sameItems(correct, iterated);

    iterated.clear();
    for (Location loc : grid.fourNeighbors(l02)) {
      iterated.add(loc);
    }
    correct = new ArrayList<Location>(List.of(l01));
    sameItems(correct, iterated);
  }

  @Test
  void testFourNeighborThreeByOneGrid() {
    Grid<String> grid = makeGrid(3, 1);

    List<Location> correct;
    ArrayList<Location> iterated = new ArrayList<>();

    for (Location loc : grid.fourNeighbors(l00)) {
      iterated.add(loc);
    }
    correct = new ArrayList<Location>(List.of(l10));
    sameItems(correct, iterated);

    iterated.clear();
    for (Location loc : grid.fourNeighbors(l10)) {
      iterated.add(loc);
    }
    correct = new ArrayList<Location>(List.of(l00, l20));
    sameItems(correct, iterated);

    iterated.clear();
    for (Location loc : grid.fourNeighbors(l20)) {
      iterated.add(loc);
    }
    correct = new ArrayList<Location>(List.of(l10));
    sameItems(correct, iterated);
  }

  @Test
  void testFourNeighborThreeBy5Grid() {
    Grid<String> grid = makeGrid(3, 5);

    List<Location> correct;
    ArrayList<Location> iterated = new ArrayList<>();

    for (Location loc : grid.fourNeighbors(new Location(0, 1))) {
      iterated.add(loc);
    }
    correct = new ArrayList<Location>(
        List.of(new Location(0, 0), new Location(1, 1), new Location(0, 2)));
    sameItems(correct, iterated);

    iterated.clear();
    for (Location loc : grid.fourNeighbors(new Location(1, 3))) {
      iterated.add(loc);
    }
    correct = new ArrayList<Location>(
        List.of(new Location(1, 2), new Location(0, 3), new Location(1, 4), new Location(2, 3)));
    sameItems(correct, iterated);

  }
  
  
  
  
}
