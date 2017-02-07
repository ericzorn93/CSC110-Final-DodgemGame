/**************
 * Dodgem
 * Author: ....
 * 
 * Move a round sprite around the screen
 * trying to avoid a pesky triangle
 * This is just a starting code for a more
 * sophisticated Dodgem game.
 * ************/

// import fang and java libraries
import fang2.sprites._
import fang2.core.Game
import fang2.core.Sound
import java.awt.Color

// Set up the Dodgem class to be an extension of a Game class
//   This allows it to gain all the methods available for that
//   class.
class Dodgem extends Game(750,750) {
  // Create the sprites
  var curPlayer: OvalSprite = new OvalSprite(1,1)
  var tri: PolygonSprite = new PolygonSprite(3)
  var scoreSprite: StringSprite = new StringSprite("Score: 0")

  // Sound effects (collision) - can only handle wav files
  var collisionSound: Sound = new Sound("hit-pipe.wav")

  var score: Int = 0
  var triDX: Double = 0.05           // Speed/direction of triangle movement
  val playerStartPos = (0.25, 0.25)  // Start position of player
  val playerSpeed = 0.05             // "Speed" of player

  /*****
   * setup
   *    Override the default setup method so we can control
   *    exactly what is setup at the start of the game.
   *****/
  override def setup {
    // Set the triangle location and scale and color
    tri.setLocation(0.5,0.5)
    tri.setScale(0.05)
    tri.setColor(Color.red)

    // Add the Triangle sprite to the game
    addSprite(tri)

    // Set the player's location and scale
    curPlayer.setLocation(playerStartPos._1, playerStartPos._2)
    curPlayer.setScale(0.05)

    // Add the Player sprite to the game
    addSprite(curPlayer)

    // Create a score sprite
    scoreSprite.setHeight(0.05)  
    scoreSprite.topJustify()     // Location specifies top edge of string spr.
    scoreSprite.leftJustify()    // Location specifies left edge of sprite
    scoreSprite.setLocation(0,0) // Set location (upper left corner)
    addSprite(scoreSprite)

    // Set up the help text
    setHelpText("You wish there was help with this game.")

    // Turn on sound and start game immediately
    playSoundImmediately()  // Otherwise, muted initially
    startGameImmediately()  // Otherwise, paused initially
    Sound.turnAllSoundOn()
  }

  /*****
   * advance
   *    Override the default advance method so we can control
   *    what happens each intermediate frame.  FANG calls this
   *    method every "step" allowing us to animate the game
   *    or do user interaction (like check keyboard/mouse state).
   *****/
  override def advance {
    // Print a message if the sprites intersect
    //    And move the player back to the "start"
    if (curPlayer.intersects(tri)) {
      handleCollision
    }

    // Move the triangle
    moveTriangle

    // Move player (based on key pressed)
    movePlayer

    // Check for key pressed
    if (keyPressed)
      handleKey(getKeyPressed)
  }

  def handleKey(ch: Char) = {
    ch match {
      case 's' => {
        println("Save State")
        println(curPlayer.getX)
        println(curPlayer.getY)
        println(tri.getX)
        println(tri.getY)
        println(triDX)
        println(score)
      // Open file
      // Write to file
      // Close file
      }
      case 'l' => println("Load State")
      case 'r' => {
        println("Load State")
        curPlayer.setLocation(playerStartPos._1, playerStartPos._2)
        tri.setLocation(0.5,0.5)
        triDX = 0.05
        score = 0
        scoreSprite.setText("Score: " + score)
      }
      case _ =>
    }
  }

  /*****
   * handleCollision
   *    Handle the collision of player with triangle!
   *    Currently prints a debugging message
   *    Resets player's location (to initial state)
   *    Decreases the score
   *    Plays a sample sound.
   *****/
  def handleCollision {
    println("INTERSECTING!")
    curPlayer.setLocation(playerStartPos._1, playerStartPos._2)
    updateScore(-5)   // Lose points for being hit

     // NOte sound it not quite working at the moment (on my system anyway)
    collisionSound.play(1.0)
  }

  /*****
   * moveTriangle
   *    Move the triangle one "unit" in current direction
   *    Check if it hits a "wall" and bounce the opposite
   *    direction if so.
   *****/
  def moveTriangle {
    tri.translateX(triDX)
    if (tri.getX < 0.0) {
      // It hit the left wall - go other direction
      tri.setX(0.0)    // Place it on left wall
      triDX = -triDX   // Move in opposite direction
    } else if (tri.getX > 1) {
      // It hit the right wall - go other direction
      tri.setX(1.0)    // Place it on right wall
      triDX = -triDX   // Move in opposite directin
    }
  }

  /*****
   * movePlayer
   *    Move the player IF a specific key is pressed
   *    If it hits an edge, wrap around to other side
   *****/
  def movePlayer {
    // Check the arrow keys for up movement
    if (upPressed) {
      // Move the sprite (up)
      curPlayer.translateY(-playerSpeed)

      // Block it from going above (back to the other side)
      if (curPlayer.getY < 0.0) {
        curPlayer.setY(0.0)
      }
    }
    // Check the arrow keys for down movement
    else if (downPressed) {
      // Move the sprite (down)
      curPlayer.translateY(playerSpeed)

      // Wrap the sprite if necessary
      if (curPlayer.getY > 1.0) {
        curPlayer.setY(0.0)
        updateScore(1)  // A point for making it across
      }
    }
    // Check the arrow keys for up movement
    else if (leftPressed) {
      // Move the sprite (left)
      curPlayer.translateX(-playerSpeed)

      // Wrap the sprite if necessary
      if (curPlayer.getX < 0.0)
        curPlayer.setX(1.0)
    }
    // Check the arrow keys for up movement
    else if (rightPressed) {
      // Move the sprite (right)
      curPlayer.translateX(playerSpeed)

      // Wrap the sprite if necessary
      if (curPlayer.getX > 1.0)
        curPlayer.setX(0.0)
    }
  }

  /*****
   * updateScore
   *    Update the actual score AND the displayed score (text)
   *    using the amount provided
   *****/
  def updateScore(amount: Int) {
    score += amount   
    scoreSprite.setText("Score: " + score)  // 
  }
}
