import pt.isel.canvas.*

fun main() {

    val canvas = Canvas(CANVAS_WIDTH, CANVAS_HEIGHT, BACKGROUND_COLOR)

    var game = Game(
        Area(canvas.width, canvas.height),
        emptyList(),
        Shot(),
        Spaceship(SPACESHIP_WIDTH, SPACESHIP_HEIGHT, canvas.width / 2, SPACESHIP_BASE_LINE, SPACESHIP_COLOR)
    )
    onStart {
        canvas.drawGame(game)

        canvas.onTimeProgress(FPS) {
            if (!game.over) {
                game = game.removeAlienShot().moveAlienShot().moveShot().shotHit().gameOver()
                canvas.drawGame(game)
                canvas.drawShot(game)
            }
        }
        canvas.onTimeProgress(250) {
            game = game.addAlienShot()
        }

        canvas.onMouseMove {
            if (isOnLimit(it)) game = game.moveSpaceship(it.x)
        }
        canvas.onMouseDown {
            game = game.shot(it.x)
        }
    }
    onFinish { }
}