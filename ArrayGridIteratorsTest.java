import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * JUnit tests for the iterator functionality of the ArrayGrid class.
 * 
 * @author Nathan Sprague
 * @version V1.0, 1/2019
 *
 */
class ArrayGridIteratorsTest extends GridIteratorsTest {

  @Override
  public <T> Grid<T> makeGrid(int rows, int cols) {
    return new ArrayGrid<T>(rows, cols);
  }

}
