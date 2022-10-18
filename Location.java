/**
 * Location objects represent a row-column coordinate in a two dimensional grid.
 * 
 * @author Nathan Sprague
 * @version V1.0, 1/2019
 *
 */
public class Location implements Comparable<Location> {
  private final int row;
  private final int col;

  /**
   * Create a location at the given coordinates.
   * 
   * @param row Row number
   * @param col Column Number
   * @throws IllegalArgumentException for negative row or column
   */
  public Location(int row, int col) {
    if (row < 0 || col < 0) {
      throw new IllegalArgumentException();
    }
    this.row = row;
    this.col = col;
  }

  /**
   * Returns the row number.
   */
  public int getRow() {
    return row;
  }

  /**
   * Returns the column number.
   */
  public int getCol() {
    return col;
  }

  @Override
  public String toString() {
    return "Location [row=" + row + ", col=" + col + "]";
  }

  @Override
  public int hashCode() {
    // Default Eclipse implementation...
    final int prime = 31;
    int result = 1;
    result = prime * result + col;
    result = prime * result + row;
    return result;
  }

  /**
   * Two locations are considered equal if they have the same row and column
   * values.
   * 
   * @return True if the provided location is equal to this location
   * 
   */
  @Override
  public boolean equals(Object obj) {
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }

    Location other = (Location) obj;
    if (col != other.col || row != other.row) {
      return false;
    }

    return true;
  }

  /**
   * Compare locations based on position in row-major order.
   * 
   * @returns A negative integer, zero, or a positive integer as this location is
   *          less than, equal to, or greater than the other location in a
   *          row-major ordering.
   */
  @Override
  public int compareTo(Location other) {
    int result = getRow() - other.getRow();

    if (result == 0) {
      result = getCol() - other.getCol();
    }
    return result;
  }

}
