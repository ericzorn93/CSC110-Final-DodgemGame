/**************
 * Dodgem
 * Author: Andrew Marrese
 * 
 * Move a round sprite around the screen
 * trying to avoid a pesky triangle and collect the nice blue ovals
 * ************/

// import fang and java libraries
import fang2.sprites._
import fang2.core.Game
import fang2.core.Sound
import java.awt.Color

// Set up the Dodgem class to be an extension of a Game class
//   This allows it to gain all the methods available for that
//   class.
class Dodgem1 extends Game(750,750) {
  // Create the sprites
var curPlayer: OvalSprite = new OvalSprite(1,1)
var tri: PolygonSprite = new PolygonSprite(3)
var tri2: PolygonSprite = new PolygonSprite(3)
var tri3: PolygonSprite = new PolygonSprite(3)
var tri4: PolygonSprite = new PolygonSprite(3)
var tri5: PolygonSprite = new PolygonSprite(3)
  var ova1: OvalSprite = new OvalSprite (1,2)
var ova2: OvalSprite = new OvalSprite (1,2)
var ova3: OvalSprite = new OvalSprite (1,2)
var ova4: OvalSprite = new OvalSprite (1,2)
var ova5: OvalSprite = new OvalSprite (1,2)
// Sound effects (collision) - can only handle wav files
 var collisionSound: Sound = new Sound("hit-pipe.wav")	
  var scoreSprite: StringSprite = new StringSprite("Score: 0")
  var score: Int = 0
  var triDX: Double = 0.05 // Speed/direction of triangle movement
var tri2DX: Double = 0.05
var tri3DX: Double = 0.01
var tri4DX: Double = 0.02
var tri5DX: Double = 0.06  
//------------------------------------------------------
var tri2DY: Double = 0.05
var tri3DY: Double = 0.08
var tri4DY: Double = 0.05
var tri5DY: Double = 0.05  

val playerStartPos = (0.25, 0.25)  // Start position of player
  val playerSpeed = 0.05             // "Speed" of player

  /*****
   * setup
   *    Override the default setup method so we can control
   *    exactly what is setup at the start of the game.
   *****/
  override def setup {
    // Set the triangle location and scale and color for each triangle
    tri.setLocation(0.5,0.7)
    tri.setScale(0.08)
    tri.setColor(Color.red)
//---------------------------------------------------------------------
    tri2.setLocation(0.6,0.9)
    tri2.setScale(0.05)
    tri2.setColor(Color.red)
//---------------------------------------------------------------------
    tri3.setLocation(0.2,0.4)
    tri3.setScale(0.05)
    tri3.setColor(Color.red)
//---------------------------------------------------------------------
    tri4.setLocation(0.5,0.35)
    tri4.setScale(0.07)
    tri4.setColor(Color.red)
//-------------------------------------------------------------------
    tri5.setLocation(0.7,0.9)
    tri5.setScale(0.02)
    tri5.setColor(Color.red)
 // Set the oval location and scale and color for each oval
   ova1.setLocation(0.1,0.7)
    ova1.setScale(0.05)
    ova1.setColor(Color.blue)
//---------------------------------------------------------------------
    ova2.setLocation(0.7,0.4)
    ova2.setScale(0.05)
    ova2.setColor(Color.blue)
//---------------------------------------------------------------------
    ova3.setLocation(0.6,0.8)
    ova3.setScale(0.05)
    ova3.setColor(Color.blue)
//---------------------------------------------------------------------
    ova4.setLocation(0.5,0.5)
    ova4.setScale(0.08)
    ova4.setColor(Color.blue)
//-------------------------------------------------------------------
    ova5.setLocation(0.9,0.9)
    ova5.setScale(0.05)
    ova5.setColor(Color.blue)
    // Add the Triangle sprites to the game
    addSprite(tri)
addSprite(tri2)
addSprite(tri3)
addSprite(tri4)
addSprite(tri5)
// Add the oval sprites to the game
addSprite(ova1)
addSprite(ova2)
addSprite(ova3)
addSprite(ova4)
addSprite(ova5)
// Add the Player sprite to the game
    addSprite(curPlayer)
    // Set the player's location and scale
    curPlayer.setLocation(playerStartPos._1, playerStartPos._2)
    curPlayer.setScale(0.05)

     // Create a score sprite
    scoreSprite.setHeight(0.05)  
    scoreSprite.topJustify()     // Location specifies top edge of string spr.
    scoreSprite.leftJustify()    // Location specifies left edge of sprite
    scoreSprite.setLocation(0,0) // Set location (upper left corner)
    addSprite(scoreSprite)

    // Set up the help text
    setHelpText("Use the arrow keys to dodge the tringlas and collect the ovals.")

    // Turn on sound and start game immediately
  //  playSoundImmediately()  // Otherwise, muted initially
    startGameImmediately()  // Otherwise, paused initially
  //  Sound.turnAllSoundOn()
  }

  /*****
   * advance
   *    Override the default advance method so we can control
   *    what happens each intermediate frame.  
   *****/
  override def advance {
    // Print a message if the sprites intersect
    //    And move the player back to the "start"
    if (curPlayer.intersects(tri)||curPlayer.intersects(tri2)||curPlayer.intersects(tri3)||curPlayer.intersects(tri4)||curPlayer.intersects(tri5)) {
handleCollision
    }
//=============================================================================
// Deleat oval and add to score if player hits an oval
if (curPlayer.intersects(ova1)&& ova1.isVisible) {
updateScore(+5)       
ova1.hide
    }
if (curPlayer.intersects(ova2)&& ova2.isVisible) {
       updateScore(+5)       
ova2.hide
    }
if (curPlayer.intersects(ova3)&& ova3.isVisible) {
       updateScore(+5)       
ova3.hide
    }
if (curPlayer.intersects(ova4)&& ova4.isVisible) {
       updateScore(+5)       
ova4.hide
    }
if (curPlayer.intersects(ova5)&& ova5.isVisible) {
       updateScore(+5)       
ova5.hide
    }
//============================================================================
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
   *    Player is moved to start position and points are deducted.*/

  def handleCollision {
    println("INTERSECTING!")
    curPlayer.setLocation(playerStartPos._1, playerStartPos._2)
    updateScore(-5)   
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
//-----------------------------------------------------------------
	tri2.translateX(tri2DX)
    if (tri2.getX < 0.0) {
      // It hit the left wall - go other direction
      tri2.setX(0.0)    // Place it on left wall
      tri2DX = -tri2DX   // Move in opposite direction
    } else if (tri2.getX > 1) {
      // It hit the right wall - go other direction
      tri2.setX(1.0)    // Place it on right wall
      tri2DX = -tri2DX   // Move in opposite directin
    }
//-----------------------------------------------------------------
tri3.translateY(tri3DX)
    if (tri3.getY < 0.0) {
      // It hit the top wall - go other direction
      tri3.setY(0.0)    // Place it on top wall
      tri3DX = -tri3DX   // Move in opposite direction
    } else if (tri3.getY > 1) {
      // It hit the bottom wall - go other direction
      tri3.setY(1.0)    // Place it on bottom wall
      tri3DX = -tri3DX   // Move in opposite directin
  }
//-------------------------------------------------------------------
tri4.translateX(tri4DX)
    if (tri4.getX < 0.0) {
      // It hit the left wall - go other direction
      tri4.setX(0.0)    // Place it on left wall
      tri4DX = -tri4DX   // Move in opposite direction
    } else if (tri4.getX > 1) {
      // It hit the right wall - go other direction
      tri4.setX(1.0)    // Place it on right wall
      tri4DX = -tri4DX   // Move in opposite directin
  	}
 //-------------------------------------------------------------
tri5.translateY(tri5DX)
    if (tri5.getY < 0.0) {
      // It hit the top wall - go other direction
      tri5.setY(0.0)    // Place it on top wall
      tri5DX = -tri5DX   // Move in opposite direction
    } else if (tri5.getY > 1) {
      // It hit the bottom wall - go other direction
      tri5.setY(1.0)    // Place it on bottom wall
      tri5DX = -tri5DX   // Move in opposite directin
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
