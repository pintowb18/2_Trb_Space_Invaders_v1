import pt.isel.canvas.*

// extension function of canvas that erases the canvas and draws again with a new Game
fun Canvas.drawGame(game: Game) {
    erase()
    drawSpaceShip(game)
    drawAliens(game)
    if (game.shipShot != null) drawShot(game)
    drawAlienShots(game)

    if (game.over) {
        drawText(GAME_OVER_X, GAME_OVER_Y, "Game Over", RED, GAME_OVER_FONT)
    }
}

fun Game.changes(): Game = removeAlienShot().moveAlienShot().shotHit().gameOver().alienHit().moveShot()


fun alienList(): List<Alien> = listOf(
    Alien(0 * CELL_WIDTH, 1 * CELL_HEIGHT, AlienType.Octopus),
    Alien(1 * CELL_WIDTH, 1 * CELL_HEIGHT, AlienType.Octopus),
    Alien(2 * CELL_WIDTH, 1 * CELL_HEIGHT, AlienType.Octopus),
    Alien(3 * CELL_WIDTH, 1 * CELL_HEIGHT, AlienType.Octopus),
    Alien(4 * CELL_WIDTH, 1 * CELL_HEIGHT, AlienType.Octopus),
    Alien(5 * CELL_WIDTH, 1 * CELL_HEIGHT, AlienType.Octopus),
    Alien(6 * CELL_WIDTH, 1 * CELL_HEIGHT, AlienType.Octopus),
    Alien(7 * CELL_WIDTH, 1 * CELL_HEIGHT, AlienType.Octopus),
    Alien(8 * CELL_WIDTH, 1 * CELL_HEIGHT, AlienType.Octopus),
    Alien(9 * CELL_WIDTH, 1 * CELL_HEIGHT, AlienType.Octopus),
    Alien(10 * CELL_WIDTH, 1 * CELL_HEIGHT, AlienType.Octopus),
    Alien(11 * CELL_WIDTH, 1 * CELL_HEIGHT, AlienType.Octopus),
    Alien(0 * CELL_WIDTH, 2 * CELL_HEIGHT, AlienType.Octopus),
    Alien(1 * CELL_WIDTH, 2 * CELL_HEIGHT, AlienType.Octopus),
    Alien(2 * CELL_WIDTH, 2 * CELL_HEIGHT, AlienType.Octopus),
    Alien(3 * CELL_WIDTH, 2 * CELL_HEIGHT, AlienType.Octopus),
    Alien(4 * CELL_WIDTH, 2 * CELL_HEIGHT, AlienType.Octopus),
    Alien(5 * CELL_WIDTH, 2 * CELL_HEIGHT, AlienType.Octopus),
    Alien(6 * CELL_WIDTH, 2 * CELL_HEIGHT, AlienType.Octopus),
    Alien(7 * CELL_WIDTH, 2 * CELL_HEIGHT, AlienType.Octopus),
    Alien(8 * CELL_WIDTH, 2 * CELL_HEIGHT, AlienType.Octopus),
    Alien(9 * CELL_WIDTH, 2 * CELL_HEIGHT, AlienType.Octopus),
    Alien(10 * CELL_WIDTH, 2 * CELL_HEIGHT, AlienType.Octopus),
    Alien(11 * CELL_WIDTH, 2 * CELL_HEIGHT, AlienType.Octopus),
    Alien(0 * CELL_WIDTH, 3 * CELL_HEIGHT, AlienType.Crab),
    Alien(1 * CELL_WIDTH, 3 * CELL_HEIGHT, AlienType.Crab),
    Alien(2 * CELL_WIDTH, 3 * CELL_HEIGHT, AlienType.Crab),
    Alien(3 * CELL_WIDTH, 3 * CELL_HEIGHT, AlienType.Crab),
    Alien(4 * CELL_WIDTH, 3 * CELL_HEIGHT, AlienType.Crab),
    Alien(5 * CELL_WIDTH, 3 * CELL_HEIGHT, AlienType.Crab),
    Alien(6 * CELL_WIDTH, 3 * CELL_HEIGHT, AlienType.Crab),
    Alien(7 * CELL_WIDTH, 3 * CELL_HEIGHT, AlienType.Crab),
    Alien(8 * CELL_WIDTH, 3 * CELL_HEIGHT, AlienType.Crab),
    Alien(9 * CELL_WIDTH, 3 * CELL_HEIGHT, AlienType.Crab),
    Alien(10 * CELL_WIDTH, 3 * CELL_HEIGHT, AlienType.Crab),
    Alien(11 * CELL_WIDTH, 3 * CELL_HEIGHT, AlienType.Crab),
    Alien(0 * CELL_WIDTH, 4 * CELL_HEIGHT, AlienType.Crab),
    Alien(1 * CELL_WIDTH, 4 * CELL_HEIGHT, AlienType.Crab),
    Alien(2 * CELL_WIDTH, 4 * CELL_HEIGHT, AlienType.Crab),
    Alien(3 * CELL_WIDTH, 4 * CELL_HEIGHT, AlienType.Crab),
    Alien(4 * CELL_WIDTH, 4 * CELL_HEIGHT, AlienType.Crab),
    Alien(5 * CELL_WIDTH, 4 * CELL_HEIGHT, AlienType.Crab),
    Alien(6 * CELL_WIDTH, 4 * CELL_HEIGHT, AlienType.Crab),
    Alien(7 * CELL_WIDTH, 4 * CELL_HEIGHT, AlienType.Crab),
    Alien(8 * CELL_WIDTH, 4 * CELL_HEIGHT, AlienType.Crab),
    Alien(9 * CELL_WIDTH, 4 * CELL_HEIGHT, AlienType.Crab),
    Alien(10 * CELL_WIDTH, 4 * CELL_HEIGHT, AlienType.Crab),
    Alien(11 * CELL_WIDTH, 4 * CELL_HEIGHT, AlienType.Crab),
    Alien(0 * CELL_WIDTH, 5 * CELL_HEIGHT, AlienType.Squid),
    Alien(1 * CELL_WIDTH, 5 * CELL_HEIGHT, AlienType.Squid),
    Alien(2 * CELL_WIDTH, 5 * CELL_HEIGHT, AlienType.Squid),
    Alien(3 * CELL_WIDTH, 5 * CELL_HEIGHT, AlienType.Squid),
    Alien(4 * CELL_WIDTH, 5 * CELL_HEIGHT, AlienType.Squid),
    Alien(5 * CELL_WIDTH, 5 * CELL_HEIGHT, AlienType.Squid),
    Alien(6 * CELL_WIDTH, 5 * CELL_HEIGHT, AlienType.Squid),
    Alien(7 * CELL_WIDTH, 5 * CELL_HEIGHT, AlienType.Squid),
    Alien(8 * CELL_WIDTH, 5 * CELL_HEIGHT, AlienType.Squid),
    Alien(9 * CELL_WIDTH, 5 * CELL_HEIGHT, AlienType.Squid),
    Alien(10 * CELL_WIDTH, 5 * CELL_HEIGHT, AlienType.Squid),
    Alien(11 * CELL_WIDTH, 5 * CELL_HEIGHT, AlienType.Squid)
)

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

fun Game.alienMove() =
    Game(area, alienShots, shipShot, ship, alienList.map { Alien(it.x + STEP, it.y, it.type) }, over, !animationStep)

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
            animationStep
        )
    else copy()
}

fun Game.alienHit(): Game =
    if (shipShot != null)
        Game(
            area,
            alienShots,
            if (shipShot.y < 0 || alienList.any { shipShot.getBoundingBox().intersectsWith(it.getBoundingBox()) }) null else shipShot,
            ship,
            alienList.filter { !shipShot.getBoundingBox().intersectsWith(it.getBoundingBox()) },
            over,
            animationStep
        )
    else copy()


// extension function that determinate if the spaceship has been hit by any of the alien shots and if so returns Game with the value of over with true
fun Game.gameOver() = Game(
    area,
    alienShots,
    shipShot,
    ship,
    alienList,
    over = !(alienShots.all { !it.getBoundingBox().intersectsWith(ship.getBoundingBox()) }),
    animationStep
)

// extension function that receives 2 areas (boundingboxes) of different objects, and checks if they intersect with one another, returning true if they did
fun BoundingBox.intersectsWith(box: BoundingBox) =
    corner.x in box.corner.x..box.corner.x + box.width && corner.y in box.corner.y..box.corner.y + box.height ||
            box.corner.x in corner.x..corner.x + width && box.corner.y in corner.y..corner.y + height

// extension function that returns the area (boundingboxes) of the spaceship
fun Spaceship.getBoundingBox() = BoundingBox(Position(x, y), width, height)

fun Alien.getBoundingBox() = BoundingBox(Position(x, y), CELL_WIDTH, CELL_HEIGHT)

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
        animationStep
    )

// extension function that returns a new game with a new spaceship shot, only if there isn't one already
fun Game.shot(mouseX: Int): Game = copy(
    shipShot = Shot(
        mouseX - GUN_SHOT_WIDHT / 2,
        ship.y,
        SHIP_SHOT_SPEED
    )
)

// extension function of canvas that draws a shot from the spaceship
fun Canvas.drawShot(game: Game) {
    drawRect(
        game.shipShot!!.x,
        game.shipShot!!.y,
        GUN_SHOT_WIDHT,
        GUN_SHOT_HEIGHT,
        GUN_SHOT_COLOR
    )
}

// extension function that returns a new game with the spaceship shot moved based on its speed
fun Game.moveShot(): Game =
    Game(
        area,
        alienShots,
        shipShot?.let { Shot(x = shipShot.x, y = shipShot.y - shipShot.speed, speed = shipShot.speed) },
        ship,
        alienList,
        over,
        animationStep
    )

// extension function that returns a new game with one additional shot on a 50% chance
fun Game.addAlienShot(): Game {
    val x = alienList.random().x + CELL_WIDTH / 2
    val y = alienList.random().y + CELL_HEIGHT / 2
    return if (lst.random() == 0) copy(alienShots = (alienShots + Shot(x, y))) else copy()
}

// extension function that return a new game with the shots that passed beyond the canvas limit removed from the alien shots list
fun Game.removeAlienShot() =
    Game(area, alienShots.filter { it.y <= CANVAS_HEIGHT }, shipShot, ship, alienList, over, animationStep)


// function that determinate if a given mouseEvent is within canvas limit
fun isOnLimit(mouseEvent: MouseEvent): Boolean =
    mouseEvent.x < CANVAS_WIDTH - SPACESHIP_WIDTH / 2 && mouseEvent.x > SPACESHIP_WIDTH / 2


//fun Game.alienIsOnLimit():Boolean {
//
//}
