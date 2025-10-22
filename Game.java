import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JFrame;

public class Game implements KeyListener {

  private Canvas canvas = new Canvas();
  private final Set<Integer> pressedKeys = new HashSet<>();
  private ArrayList<GameObject> gameObjects = new ArrayList<>();
  private ArrayList<GameObject> gameObjectsAddQueue = new ArrayList<>();

  Game() {
    JFrame frame = new JFrame("Bouncing ball");
    canvas.setPreferredSize(new Dimension(700, 700));
    frame.pack();
    frame.setVisible(true);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.add(this.canvas);
    canvas.addKeyListener(this);
    canvas.setFocusable(true);
    canvas.requestFocusInWindow();

    frame.pack();

    this.spawnBall(new double[] { 350.0, 350.0 });

  }

  /**
   * Copied on
   * https://www.reddit.com/r/learnprogramming/comments/o2aet5/i_cant_understand_the_notch_game_loop_java/
   * lastime: Time since the last iteration of the loop. Helps compute delta.
   * 
   * AmountOfTicks: The max FPS for the game.
   * 
   * ns: The number of nanoseconds per frame.
   * 
   * delta: The 'progress' that must be elapsed until the next frame.
   * 
   * frames: The number of frames elapsed since the last time we displayed the
   * FPS.
   * 
   * time: The current time. Used to know when to display next the FPS.
   */
  public void run() {
    long lastime = System.nanoTime();
    double AmountOfTicks = 60;
    double ns = 1000000000 / AmountOfTicks;
    double delta = 0;
    int frames = 0;
    double time = System.currentTimeMillis();
    double lastUpdateTime = System.currentTimeMillis();
    boolean isRunning = true;

    while (isRunning == true) {
      long now = System.nanoTime();
      delta += (now - lastime) / ns;
      lastime = now;

      if (delta >= 1) {
        double currentUpdateTime = System.currentTimeMillis();
        double deltaTime = (currentUpdateTime - lastUpdateTime) / 1000;
        lastUpdateTime = currentUpdateTime;
        this.processFrame(deltaTime);

        frames++;
        delta--;
        if (System.currentTimeMillis() - time >= 1000) {
          System.out.println("fps: " + frames + " | ball count : " + gameObjects.size());
          time += 1000;
          frames = 0;
        }
      }
    }
  }

  public void spawnBall(double[] position) {
    Ball ball = new Ball(350, 350, 10, Color.WHITE);
    gameObjectsAddQueue.add(ball);
    ball.wallBounce.connect(pos -> this.spawnBall(pos));
  }

  public void update(double dt) {
    if (this.isKeyPressed(KeyEvent.VK_SPACE)) {
      return;
    }
    for (GameObject objects : gameObjects) {
      objects.update(dt);
    }
  }

  public void draw() {
    this.canvas.repaint();
  }

  private void processFrame(double deltaTime) {
    this.gameObjects.addAll(gameObjectsAddQueue);
    this.canvas.add(gameObjectsAddQueue);
    gameObjectsAddQueue.clear();

    this.update(deltaTime);
    this.draw();
  }

  @Override
  public void keyPressed(KeyEvent e) {
    int key = e.getKeyCode();
    if (!pressedKeys.contains(key)) {
      pressedKeys.add(key);
    } else {
      pressedKeys.remove(key);
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {
  }

  @Override
  public void keyTyped(KeyEvent e) {

  }

  public boolean isKeyPressed(int keyCode) {
    return pressedKeys.contains(keyCode);
  }
}
