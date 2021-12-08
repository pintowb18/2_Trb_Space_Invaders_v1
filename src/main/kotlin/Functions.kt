import pt.isel.canvas.*

// extension function of canvas that erases the canvas and draws again with a new Game
fun Canvas.drawGame(game: Game) {
    erase()
    drawSpaceShip(game)
    drawAlienShots(game)
    if (game.over) {
        drawText(game.area.width / 2, game.area.height / 2, GAME_OVER, RED)

    }
}

// extension function of canvas that draws the spaceship
fun Canvas.drawSpaceShip(game: Game) {
    drawRect(game.ship.x, game.ship.y, game.ship.width, game.ship.height, game.ship.color)
    drawRect(
        game.ship.x + SPACESHIP_WIDTH / 2 - GUN_WIDTH / 2,
        game.ship.y - GUN_HEIGHT,
        GUN_WIDTH,
        GUN_HEIGHT,
        GUN_COLOR
    )
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
    ship
)

// extension function that determinate if the spaceship has been hit by any of the alien shots and if so returns Game with the value of over with true
fun Game.gameOver() = Game(
    area,
    alienShots,
    shipShot,
    ship,
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
    Game(area, alienShots.map { Shot(x = it.x, y = it.y + it.speed, speed = it.speed) }, shipShot, ship)

// extension function that returns a new game with the spaceship shot moved based on its speed
fun Game.moveShot() =
    Game(area, alienShots, Shot(x = shipShot.x, y = shipShot.y - shipShot.speed, speed = shipShot.speed), ship)

// extension function that returns a new game with a new spaceship shot, only if there isn't one already
fun Game.shot(mouseX: Int) = if (shipShot.y < 0) copy(
    shipShot = Shot(
        mouseX,
        ship.y - SPACESHIP_HEIGHT - GUN_HEIGHT,
        SHIP_SHOT_SPEED
    )
) else copy()

// extension function that returns a new game with one additional shot on a 50% chance
fun Game.addAlienShot() = if (lst.random() == 0) copy(alienShots = (alienShots + Shot())) else copy()

// extension function that return a new game with the shots that passed beyond the canvas limit removed from the alien shots list
fun Game.removeAlienShot() = Game(area, alienShots.filter { it.y <= CANVAS_HEIGHT }, shipShot, ship)

// function that determinate if a given mouseEvent is within canvas limit
fun isOnLimit(mouseEvent: MouseEvent): Boolean =
    mouseEvent.x < CANVAS_WIDTH - SPACESHIP_WIDTH / 2 && mouseEvent.x > SPACESHIP_WIDTH / 2


