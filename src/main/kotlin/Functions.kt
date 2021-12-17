import pt.isel.canvas.*

// extension function of canvas that erases the canvas and draws again with a new Game
fun Canvas.drawGame(game: Game) {
    erase()
    drawSpaceShip(game)
    drawAlienShots(game)
    drawGrid()

    if (game.over) {
        drawText(GAME_OVER_X, GAME_OVER_Y, "Game Over", RED, GAME_OVER_FONT)
    }
}

fun alienList() = listOf(
        Position(0, 1), Position(1, 1), Position(2, 1), Position(3, 1),
        Position(4, 1), Position(5, 1), Position(6, 1), Position(7, 1),
        Position(8, 1), Position(9, 1), Position(10, 1), Position(11, 1),
        Position(0, 2), Position(1, 2), Position(2, 2), Position(3, 2),
        Position(4, 2), Position(5, 2), Position(6, 2), Position(7, 2),
        Position(8, 2), Position(9, 2), Position(10, 2), Position(11, 2),
        Position(0, 3), Position(1, 3), Position(2, 3), Position(3, 3),
        Position(4, 3), Position(5, 3), Position(6, 3), Position(7, 3),
        Position(8, 3), Position(9, 3), Position(10, 3), Position(11, 3),
        Position(0, 4), Position(1, 4), Position(2, 4), Position(3, 4),
        Position(4, 4), Position(5, 4), Position(6, 4), Position(7, 4),
        Position(8, 4), Position(9, 4), Position(10, 4), Position(11, 4),
        Position(0, 5), Position(1, 5), Position(2, 5), Position(3, 5),
        Position(4, 5), Position(5, 5), Position(6, 5), Position(7, 5),
        Position(8, 5), Position(9, 5), Position(10, 5), Position(11, 5)
    )



fun Canvas.drawAliens(game: Game) {
    game.alienList.forEach {
        val x = it.x * CELL_WIDTH
        val y = it.y * CELL_HEIGHT
        val spriteX = it.x * SPRITE_WIDTH
        val spriteY = it.y * SPRITE_HEIGHT
        val coords ="$spriteX,$spriteY,$SPRITE_WIDTH,$SPRITE_HEIGHT"
        when (it.y) {
             1 -> drawImage("invaders.png|$coords", x, y, CELL_WIDTH, CELL_HEIGHT)
             2 -> drawImage("invaders.png|$coords", x, y, CELL_WIDTH, CELL_HEIGHT)
             3 -> drawImage("invaders.png|$coords", x, y, CELL_WIDTH, CELL_HEIGHT)
             4 -> drawImage("invaders.png|$coords", x, y, CELL_WIDTH, CELL_HEIGHT)
             5 -> drawImage("invaders.png|$coords", x, y, CELL_WIDTH, CELL_HEIGHT)
        }

    }
}

// extension function of canvas that draws the spaceship
fun Canvas.drawSpaceShip(game: Game) {
    drawImage("spaceship.png", game.ship.x, game.ship.y, SPACESHIP_WIDTH, SPACESHIP_HEIGHT)
}

// extension function of canvas that draws a shot from the spaceship
fun Canvas.drawShot(game: Game) {
    drawRect(game.shipShot.x, game.shipShot.y, GUN_SHOT_WIDHT, GUN_SHOT_HEIGHT, GUN_SHOT_COLOR)
}

// extension function of canvas that draws the Alien shots from their list
fun Canvas.drawAlienShots(game: Game) {
    game.alienShots.forEach {
        drawRect(it.x, it.y, GUN_SHOT_WIDHT, GUN_SHOT_HEIGHT, ALIEN_SHOT_COLOR)
    }
}

// extension function that filters the alien shots list and returns Game with a new one with only the shots that were not hit
fun Game.shotHit() = Game(
    area,
    alienShots.filter { !shipShot.getBoundingBox().intersectsWith(it.getBoundingBox()) },
    shipShot,
    ship,
    alienList
)

// extension function that determinate if the spaceship has been hit by any of the alien shots and if so returns Game with the value of over with true
fun Game.gameOver() = Game(
    area,
    alienShots,
    shipShot,
    ship,
    alienList,
    over = !(alienShots.all { !it.getBoundingBox().intersectsWith(ship.getBoundingBox()) })
)

// extension function that receives 2 areas (boundingboxes) of different objects, and checks if they intersect with one another, returning true if they did
fun BoundingBox.intersectsWith(box: BoundingBox) =
    corner.x in box.corner.x..box.corner.x + box.width && corner.y in box.corner.y..box.corner.y + box.height ||
            box.corner.x in corner.x..corner.x + width && box.corner.y in corner.y..corner.y + height

// extension function that returns the area (boundingboxes) of the spaceship
fun Spaceship.getBoundingBox() = BoundingBox(Position(x, y), width, height)

// extension function that returns the areas (boundingboxes) of the shot
fun Shot.getBoundingBox() = BoundingBox(Position(x, y), GUN_SHOT_WIDHT, GUN_SHOT_HEIGHT)

// extension function that return a new Game with new coordinates to the spaceship based on the x received
fun Game.moveSpaceship(mouseX: Int) = copy(ship = ship.copy(x = mouseX - ship.width / 2))

// extension function that returns a new game with each of the alien shots moved based on their speed
fun Game.moveAlienShot() =
    Game(area, alienShots.map { Shot(x = it.x, y = it.y + it.speed, speed = it.speed) }, shipShot, ship, alienList)

// extension function that returns a new game with the spaceship shot moved based on its speed
fun Game.moveShot() =
    Game(
        area,
        alienShots,
        Shot(x = shipShot.x, y = shipShot.y - shipShot.speed, speed = shipShot.speed),
        ship,
        alienList
    )

// extension function that returns a new game with a new spaceship shot, only if there isn't one already
fun Game.shot(mouseX: Int) = if (shipShot.y < 0) copy(
    shipShot = Shot(
        mouseX - GUN_SHOT_WIDHT / 2,
        ship.y,
        SHIP_SHOT_SPEED
    )
) else copy()

// extension function that returns a new game with one additional shot on a 50% chance
fun Game.addAlienShot() = if (lst.random() == 0) copy(alienShots = (alienShots + Shot())) else copy()

// extension function that return a new game with the shots that passed beyond the canvas limit removed from the alien shots list
fun Game.removeAlienShot() = Game(area, alienShots.filter { it.y <= CANVAS_HEIGHT }, shipShot, ship, alienList)

// function that determinate if a given mouseEvent is within canvas limit
fun isOnLimit(mouseEvent: MouseEvent): Boolean =
    mouseEvent.x < CANVAS_WIDTH - SPACESHIP_WIDTH / 2 && mouseEvent.x > SPACESHIP_WIDTH / 2

fun Canvas.drawGrid() {
    val CELL_SIDE = 56
    val CELL_HEIGHT = 50
    (CELL_SIDE..width step CELL_SIDE).forEach { x ->
        drawLine(x, 0, x, height, WHITE, 1)
    }
    (CELL_HEIGHT..height step CELL_HEIGHT).forEach { y ->
        drawLine(0, y, width, y, WHITE, 1)
    }
}


