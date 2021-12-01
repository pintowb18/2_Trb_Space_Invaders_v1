import pt.isel.canvas.*

fun Canvas.drawGame(game: Game, shot: Shot) {
    erase()
    drawSpaceShip(game)
    drawAlienShots(game)
    drawShot(shot)
}

fun Canvas.drawSpaceShip(game: Game) {
    drawRect(game.ship.x, game.ship.y, game.ship.width, game.ship.height, game.ship.color)
    drawRect(game.ship.x + SPACESHIP_WIDTH / 2, game.ship.y - GUN_HEIGHT, GUN_WIDTH, GUN_HEIGHT, GUN_COLOR)
}
fun Canvas.drawShot(shot: Shot) {
    drawRect(shot.x + SPACESHIP_WIDTH / 2, shot.y - SPACESHIP_HEIGHT - GUN_HEIGHT, GUN_SHOT_WIDHT, GUN_SHOT_HEIGHT, GUN_SHOT_COLOR)
}
fun Canvas.drawAlienShots(game: Game) {
    game.alienShots.forEach {
        drawRect(it.x, it.y, GUN_SHOT_WIDHT, GUN_SHOT_HEIGHT, ALIEN_SHOT_COLOR)
    }
}

fun Game.shot() {

}



fun Game.moveSpaceship(mouseX: Int) = Game(area, alienShots, ship.copy(x = mouseX - ship.width / 2))
fun Game.moveAlienShot() = Game(area, alienShots.map { Shot(x = it.x, y = it.y + it.speed, speed = it.speed) }, ship)
fun Game.moveShot() = Shot(x)
fun Game.addAlienShot() = if (alienShots.size < 6)  copy(alienShots = (alienShots + Shot())) else copy()
fun Game.removeAlienShot() = Game(area, alienShots.filter { it.y <= CANVAS_HEIGHT }, ship)


fun isOnLimit(mouseEvent: MouseEvent): Boolean =
    (mouseEvent.x < CANVAS_WIDTH - SPACESHIP_WIDTH / 2 && mouseEvent.x > SPACESHIP_WIDTH / 2)


