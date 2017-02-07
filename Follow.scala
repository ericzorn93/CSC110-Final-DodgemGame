// import fang libraries
import fang2.sprites._
import fang2.core.Game

// Set up the game class
class Follow extends Game {

  // Create the two sprites
  var curPlayer: OvalSprite = new OvalSprite(1,1)
  var tri: PolygonSprite = new PolygonSprite(3)

  // Override the default setup method
  override def setup {
    // Set the triangle location and scale
    tri.setLocation(0.5,0.5)
    tri.setScale(0.05)

    // Add the sprite to the game
    addSprite(tri)

    // Set the player location and scale
    curPlayer.setLocation(0.25,0.25)
    curPlayer.setScale(0.05)

    // Set the player location and scale
    addSprite(curPlayer)
  }

  // Override the default advance method
  override def advance {
    // Print a message if the sprites intersect
    if (curPlayer.intersects(tri)) {
      println("INTERSECTING!")
    }

    // Check the arrow keys for up movement
    if (upPressed) {
      // Move the sprite
      curPlayer.translateY(-0.05)

      // Wrap the sprite if necessary
      if (curPlayer.getY < 0.0)
        curPlayer.setY(1.0)
    }
    // Check the arrow keys for up movement
    else if (downPressed) {
      // Move the sprite
      curPlayer.translateY(0.05)

      // Wrap the sprite if necessary
      if (curPlayer.getY > 1.0)
        curPlayer.setY(0.0)
    }
    // Check the arrow keys for up movement
    else if (leftPressed) {
      // Move the sprite
      curPlayer.translateX(-0.05)

      // Wrap the sprite if necessary
      if (curPlayer.getX < 0.0)
        curPlayer.setX(1.0)
    }
    // Check the arrow keys for up movement
    else if (rightPressed) {
      // Move the sprite
      curPlayer.translateX(0.05)

      // Wrap the sprite if necessary
      if (curPlayer.getX > 1.0)
        curPlayer.setX(0.0)
    }
  }

}
