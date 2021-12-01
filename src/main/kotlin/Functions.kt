import pt.isel.canvas.*

fun Canvas.drawGame(game: Game) {
    erase()
    drawSpaceShip(game)
    drawAlienShots(game)
}

fun Canvas.drawSpaceShip(game: Game) {
    drawRect(game.ship.x, game.ship.y, game.ship.width, game.ship.height, game.ship.color)
    drawRect(game.ship.x + SPACESHIP_WIDTH / 2, game.ship.y - GUN_HEIGHT, GUN_WIDTH, GUN_HEIGHT, GUN_COLOR)
}
fun Canvas.drawShot(game: Game) {
    drawRect(game.shipShot.x, game.shipShot.y, GUN_SHOT_WIDHT, GUN_SHOT_HEIGHT, GUN_SHOT_COLOR)
}

fun Canvas.drawAlienShots(game: Game) {
    game.alienShots.forEach {
        drawRect(it.x, it.y, GUN_SHOT_WIDHT, GUN_SHOT_HEIGHT, ALIEN_SHOT_COLOR)
    }
}

fun Game.shotHit() = Game(area, alienShots.filter {it.x != shipShot.x && it.y != shipShot.y},shipShot, ship)


fun Game.moveSpaceship(mouseX: Int) = copy(ship = ship.copy(x = mouseX - ship.width / 2))

fun Game.moveAlienShot() = Game(area, alienShots.map { Shot(x = it.x, y = it.y + it.speed, speed = it.speed) }, shipShot, ship)

fun Game.moveShot() = Game(area, alienShots, Shot(x = shipShot.x, y = shipShot.y - shipShot.speed, speed = shipShot.speed), ship)

fun Game.shot(mouseX: Int) = if (shipShot.y < 0) copy(shipShot = Shot(mouseX, ship.y - SPACESHIP_HEIGHT - GUN_HEIGHT, SHIP_SHOT_SPEED)) else copy()

fun Game.addAlienShot() = if (alienShots.size < 6)  copy(alienShots = (alienShots + Shot())) else copy()

fun Game.removeAlienShot() = Game(area, alienShots.filter { it.y <= CANVAS_HEIGHT },shipShot, ship)

fun isOnLimit(mouseEvent: MouseEvent): Boolean =
    (mouseEvent.x < CANVAS_WIDTH - SPACESHIP_WIDTH / 2 && mouseEvent.x > SPACESHIP_WIDTH / 2)


