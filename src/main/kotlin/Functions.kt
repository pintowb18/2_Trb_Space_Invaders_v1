import pt.isel.canvas.*

fun Canvas.drawGame(game: Game) {
    erase()
    drawSpaceShip(game)
    drawAlienShots(game)
    if (game.over) {
        drawText(game.area.width / 2 , SPACESHIP_BASE_LINE, "GAME OVER", RED)

    }
}

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

fun Canvas.drawShot(game: Game) {
    drawRect(game.shipShot.x, game.shipShot.y, GUN_SHOT_WIDHT, GUN_SHOT_HEIGHT, GUN_SHOT_COLOR)
}

fun Canvas.drawAlienShots(game: Game) {
    game.alienShots.forEach {
        drawRect(it.x, it.y, GUN_SHOT_WIDHT, GUN_SHOT_HEIGHT, ALIEN_SHOT_COLOR)
    }
}

fun Game.shotHit() = Game(
    area,
    alienShots.filter { !shipShot.getBoundingBox().intersectsWith(it.getBoundingBox()) },
    shipShot,
    ship
)

fun Game.gameOver() = Game(
    area,
    alienShots,
    shipShot,
    ship,
    over = !(alienShots.all { !it.getBoundingBox().intersectsWith(ship.getBoundingBox()) })
)


fun BoundingBox.intersectsWith(box: BoundingBox) =
    corner.x in box.corner.x..box.corner.x + box.width && corner.y in box.corner.y..box.corner.y + box.height ||
            box.corner.x in corner.x..corner.x + width && box.corner.y in corner.y..corner.y + height


fun Spaceship.getBoundingBox() = BoundingBox(Position(x, y), width, height)

fun Shot.getBoundingBox() = BoundingBox(Position(x, y), GUN_SHOT_WIDHT, GUN_SHOT_HEIGHT)


fun Game.moveSpaceship(mouseX: Int) = copy(ship = ship.copy(x = mouseX - ship.width / 2))

fun Game.moveAlienShot() =
    Game(area, alienShots.map { Shot(x = it.x, y = it.y + it.speed, speed = it.speed) }, shipShot, ship)

fun Game.moveShot() =
    Game(area, alienShots, Shot(x = shipShot.x, y = shipShot.y - shipShot.speed, speed = shipShot.speed), ship)

fun Game.shot(mouseX: Int) = if (shipShot.y < 0) copy(
    shipShot = Shot(
        mouseX,
        ship.y - SPACESHIP_HEIGHT - GUN_HEIGHT,
        SHIP_SHOT_SPEED
    )
) else copy()


fun Game.addAlienShot() = if (lst.random() == 0) copy(alienShots = (alienShots + Shot())) else copy()


fun Game.removeAlienShot() = Game(area, alienShots.filter { it.y <= CANVAS_HEIGHT }, shipShot, ship)

fun isOnLimit(mouseEvent: MouseEvent): Boolean =
    (mouseEvent.x < CANVAS_WIDTH - SPACESHIP_WIDTH / 2 && mouseEvent.x > SPACESHIP_WIDTH / 2)


