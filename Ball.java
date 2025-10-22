import java.awt.*;

import utils.Signal;
import utils.Vector2;

class Ball extends GameObject {
  public Signal<double[]> wallBounce = new Signal<double[]>();
  private int radius;
  private Color color;
  private double posx, posy;

  private Vector2 direction = new Vector2(Math.cos(Math.random() * 2 * Math.PI), Math.cos(Math.random() * 2 * Math.PI))
      .normalize();
  private int speed = 250;

  Ball(double posx, double posy, int radius, Color color) {
    this.posx = posx;
    this.posy = posy;
    this.radius = radius;
    this.color = color;
  }

  public void draw(Graphics g) {
    int x = (int) this.posx;
    int y = (int) this.posy;
    g.setColor(color);
    g.fillOval(x, y, this.radius * 2, this.radius * 2);
  }

  public void update(double dt) {
    this.bounceOffWalls();
    this.direction = this.direction.normalize();
    double dx = this.direction.x * dt * this.speed;
    double dy = this.direction.y * dt * this.speed;
    this.posx += dx;
    this.posy += dy;
  }

  private void bounceOffWalls() {
    int canvasWidth = 699;
    int canvasHeight = 699;

    // Left wall
    if ((this.posx) < 0) {
      this.posx = 1;
      this.wallBounce.emit(new double[] { posx, posy });
      this.direction = this.direction.reflect(Vector2.RIGHT);
    }
    // Right wall
    if ((this.posx + (radius * 2)) > canvasWidth) {
      this.posx = canvasWidth - radius * 2;
      this.wallBounce.emit(new double[] { posx, posy });
      this.direction = this.direction.reflect(Vector2.LEFT);
    }
    // Top wall
    if ((this.posy) < 0) {
      this.posy = 1;
      this.wallBounce.emit(new double[] { posx, posy });
      this.direction = this.direction.reflect(Vector2.DOWN);
    }
    // Bottom wall
    if ((this.posy + (radius * 2)) > canvasHeight) {
      this.posy = canvasHeight - radius * 2;
      this.wallBounce.emit(new double[] { posx, posy });
      this.direction = this.direction.reflect(Vector2.UP);
    }
  }
}
