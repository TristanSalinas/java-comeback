import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

import interfaces.Drawable;

class Canvas extends JPanel {
  ArrayList<Drawable> drawables = new ArrayList<>();

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    g.setColor(Color.BLACK);
    g.fillRect(0, 0, getWidth(), getHeight());

    for (Drawable drawable : new ArrayList<>(drawables)) {
      drawable.draw(g);
    }
  }

  public void add(ArrayList<? extends Drawable> drawables) {
    this.drawables.addAll(drawables);
  }

  public void add(Drawable drawable) {
    drawables.add(drawable);
  }
}