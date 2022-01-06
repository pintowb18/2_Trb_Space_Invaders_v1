import pt.isel.canvas.*
import java.io.File

// extension function of canvas that erases the canvas and draws again with a new Game
fun Canvas.drawGame(game: Game) {
    erase()
    drawSpaceShip(game)
    drawAliens(game)
    drawAlienShots(game)
    if (game.shipShot != null) drawShipShot(game)
    if (game.over) {
        drawText(GAME_OVER_X, GAME_OVER_Y, "Game Over", RED, GAME_OVER_FONT)
    }
}

fun Game.changes(): Game = removeAlienShot().moveAlienShot().shotHit().gameOver().alienHit().moveShot()

fun buildAlien(tokens: List<String>):Alien =
    Alien(
        tokens[0].toInt() * CELL_WIDTH,
        tokens[1].toInt() * CELL_HEIGHT,
        when (tokens[1].toInt()) {
            1 -> AlienType.Octopus
            2 -> AlienType.Octopus
            3 -> AlienType.Crab
            4 -> AlienType.Crab
            else  -> AlienType.Squid
        }

    )


// function that receives a file with each element of each Alien and saves it on a list of Aliens
fun alienList(): List<Alien> {
    var line: String?
    var alienLst = emptyList<Alien>()
    do {
        line = readLine()
        if (line != null) {
            val tokens = line.split(',')
            val alien = buildAlien(tokens)
            alienLst += alien
        }
    }
        while (line != null)

    return alienLst
}


// extension function that draws the aliens with the right image(sprite) from the file given, depending on the type it recieved from the list of aliens
fun Canvas.drawAliens(game: Game) {
    game.alienList.forEach {
        val spriteX = if (game.animationStep) SPRITE_WIDTH else 0
        val spriteY = when (it.type) {
            AlienType.Octopus -> 0 * SPRITE_HEIGHT
            AlienType.Crab -> 1 * SPRITE_HEIGHT
            AlienType.Squid -> 2 * SPRITE_HEIGHT
        }
        val coords = "$spriteX,$spriteY,$SPRITE_WIDTH,$SPRITE_HEIGHT"
        when (it.type) {
            AlienType.Octopus -> drawImage("invaders.png|$coords", it.x, it.y, CELL_WIDTH, CELL_HEIGHT)
            AlienType.Crab -> drawImage("invaders.png|$coords", it.x, it.y, CELL_WIDTH, CELL_HEIGHT)
            AlienType.Squid -> drawImage("invaders.png|$coords", it.x, it.y, CELL_WIDTH, CELL_HEIGHT)
        }
    }
}

// extension function of canvas that draws the spaceship
fun Canvas.drawSpaceShip(game: Game) {
    drawImage("spaceship.png", game.ship.x, game.ship.y, SPACESHIP_WIDTH, SPACESHIP_HEIGHT)
}

// extension function of canvas that draws the Alien shots from their list
fun Canvas.drawAlienShots(game: Game) {
    game.alienShots.forEach {
        drawRect(it.x, it.y, GUN_SHOT_WIDHT, GUN_SHOT_HEIGHT, ALIEN_SHOT_COLOR)
    }
}

// extension function of canvas that draws a shot from the spaceship
fun Canvas.drawShipShot(game: Game) {
    drawRect(
        game.shipShot!!.x,
        game.shipShot!!.y,
        GUN_SHOT_WIDHT,
        GUN_SHOT_HEIGHT,
        GUN_SHOT_COLOR
    )
}


// extension function that filters the alien shots list and returns Game with a new one with only the shots that were not hit
fun Game.shotHit(): Game {
    return if (shipShot != null)
        Game(
            area,
            alienShots.filter { !shipShot.getBoundingBox().intersectsWith(it.getBoundingBox()) },
            shipShot,
            ship,
            alienList,
            over,
            animationStep,
            step
        )
    else copy()
}

fun Game.alienHit(): Game =
    if (shipShot != null)
        Game(
            area,
            alienShots,
            if (shipShot.y < 0 || alienList.any {
                    shipShot.getBoundingBox().intersectsWith(it.getBoundingBox())
                }) null else shipShot,
            ship,
            alienList.filter { !shipShot.getBoundingBox().intersectsWith(it.getBoundingBox()) },
            over,
            animationStep,
            step
        )
    else copy()

// extension function that returns a new game with a new spaceship shot, only if there isn't one already
fun Game.shot(mouseX: Int): Game = copy(
    shipShot = Shot(
        mouseX - GUN_SHOT_WIDHT / 2,
        ship.y,
        SHIP_SHOT_SPEED
    )
)

// extension function that determinate if the spaceship has been hit by any of the alien shots and if so returns Game with the value of over with true
fun Game.gameOver() = Game(
    area,
    alienShots,
    shipShot,
    ship,
    alienList,
    over = alienShots.any { it.getBoundingBox().intersectsWith(ship.getBoundingBox()) },
    animationStep,
    step
)

// extension function that receives 2 areas (boundingboxes) of different objects, and checks if they intersect with one another, returning true if they did
fun BoundingBox.intersectsWith(box: BoundingBox) =
    corner.x in box.corner.x..box.corner.x + box.width && corner.y in box.corner.y..box.corner.y + box.height ||
            box.corner.x in corner.x..corner.x + width && box.corner.y in corner.y..corner.y + height

// extension function that returns the area (boundingboxes) of the spaceship
fun Spaceship.getBoundingBox() = BoundingBox(Position(x, y), width, height)

// extension function that returns the area (boundingboxes) of the Alien
fun Alien.getBoundingBox() = BoundingBox(Position(x, y), CELL_WIDTH, CELL_HEIGHT)

// extension function that returns true when the bounding box touches the sides of the canvas
fun BoundingBox.touchSides(): Boolean = this.corner.x < 0 || this.corner.x + width > CANVAS_WIDTH

// extension function that returns the areas (boundingboxes) of the shot
fun Shot.getBoundingBox() = BoundingBox(Position(x, y), GUN_SHOT_WIDHT, GUN_SHOT_HEIGHT)

// extension function that return a new Game with new coordinates to the spaceship based on the x received
fun Game.moveSpaceship(mouseX: Int) = copy(ship = ship.copy(x = mouseX - ship.width / 2))

// extension function that returns a new game with each of the alien shots moved based on their speed
fun Game.moveAlienShot() =
    Game(
        area,
        alienShots.map { Shot(x = it.x, y = it.y + it.speed, speed = it.speed) },
        shipShot,
        ship,
        alienList,
        over,
        animationStep,
        step
    )

fun Game.alienMove() =
    Game(
        area,
        alienShots,
        shipShot,
        ship,
        alienList.map { Alien(it.x + step, it.y, it.type) },
        over,
        !animationStep,
        step
    )

// extension function that returns a new game with the spaceship shot moved based on its speed
fun Game.moveShot(): Game =
    Game(
        area,
        alienShots,
        shipShot?.let { Shot(x = shipShot.x, y = shipShot.y - shipShot.speed, speed = shipShot.speed) },
        ship,
        alienList,
        over,
        animationStep,
        step
    )

// extension function that returns a new game with one additional shot on a 50% chance
fun Game.addAlienShot(): Game {
    val x = alienList.random().x + CELL_WIDTH / 2
    val y = alienList.random().y + CELL_HEIGHT / 2
    return if (lst.random() == 0) copy(alienShots = (alienShots + Shot(x, y))) else copy()
}

// extension function that return a new game with the shots that passed beyond the canvas limit removed from the alien shots list
fun Game.removeAlienShot() =
    Game(area, alienShots.filter { it.y <= CANVAS_HEIGHT }, shipShot, ship, alienList, over, animationStep, step)


// function that determinate if a given mouseEvent is within canvas limit
fun isOnLimit(mouseEvent: MouseEvent): Boolean =
    mouseEvent.x < CANVAS_WIDTH - SPACESHIP_WIDTH / 2 && mouseEvent.x > SPACESHIP_WIDTH / 2

// function that returns a game where the aliens descends and changes direction if any of the aliens touches the sides of the canvas
fun Game.alienIsOnLimit(): Game =
    if (alienList.any { it.getBoundingBox().touchSides() })
        Game(
            area,
            alienShots,
            shipShot,
            ship,
            alienList.map { Alien(it.x, it.y + DOWN, it.type) },
            over,
            animationStep,
            step = step * -1
        )
    else copy()