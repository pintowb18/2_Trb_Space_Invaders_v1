import pt.isel.canvas.*

fun Canvas.drawGame(game: Game){
    erase()
    drawSpaceShip(game)
    drawShots(game)
}
fun Canvas.drawSpaceShip(game: Game) {
    drawRect(game.ship.x, game.ship.y, game.ship.width, game.ship.height, game.ship.color)
    drawRect(game.ship.x + SPACESHIP_WIDTH / 2, game.ship.y - GUN_HEIGHT, GUN_WIDTH, GUN_HEIGHT, GUN_COLOR)
}

fun Game.moveSpaceship(mouseX: Int) = Game(area, alienShots, ship.copy(x = mouseX - ship.width / 2))

fun Game.moveShot(): Game{
    return Game(area,alienShots.map {
        Shot(x = it.x, y = it.y + it.speed)
    },ship)
}

fun Canvas.drawShots(game: Game) {
    game.alienShots.forEach {
        drawRect(it.x, it.y, GUN_SHOT_WIDHT, GUN_SHOT_HEIGHT, ALIEN_SHOT_COLOR)
    }
}



fun isOnLimit(mouseEvent: MouseEvent): Boolean =
    (mouseEvent.x < CANVAS_WIDTH - SPACESHIP_WIDTH / 2 && mouseEvent.x > SPACESHIP_WIDTH / 2)


