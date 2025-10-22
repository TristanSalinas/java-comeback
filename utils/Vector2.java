package utils;

public class Vector2 {
  public double x;
  public double y;

  public static Vector2 DOWN = new Vector2(0, 1);
  public static Vector2 UP = new Vector2(0, -1);
  public static Vector2 RIGHT = new Vector2(1, 0);
  public static Vector2 LEFT = new Vector2(-1, 0);

  // Constructors
  public Vector2() {
    this.x = 0;
    this.y = 0;
  }

  public Vector2(double x, double y) {
    this.x = x;
    this.y = y;
  }

  public double dot(Vector2 v) {
    return this.x * v.x + this.y * v.y;
  }

  /**
   * With Magical linear algebra and given the normal of a surface,
   * return a bounced vector off that surface. Or reflect a vector relative of the
   * normal
   */
  public Vector2 reflect(Vector2 normal) {
    normal.normalize();
    double dot = this.dot(normal);
    double rx = this.x - 2 * dot * normal.x;
    double ry = this.y - 2 * dot * normal.y;
    return new Vector2(rx, ry);
  }

  public Vector2 add(Vector2 other) {
    return new Vector2(this.x + other.x, this.y + other.y);
  }

  public Vector2 subtract(Vector2 other) {
    return new Vector2(this.x - other.x, this.y - other.y);
  }

  public Vector2 scale(double factor) {
    return new Vector2(this.x * factor, this.y * factor);
  }

  public double magnitude() {
    return Math.sqrt(x * x + y * y);
  }

  public Vector2 normalize() {
    double mag = magnitude();
    if (mag == 0)
      return new Vector2(0, 0);
    return new Vector2(x / mag, y / mag);
  }

  @Override
  public String toString() {
    return "(" + x + ", " + y + ")";
  }
}
