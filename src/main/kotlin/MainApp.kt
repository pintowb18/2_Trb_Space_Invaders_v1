import pt.isel.canvas.*

fun main() {
    onStart {
        val canvas = Canvas(CANVAS_WIDTH, CANVAS_HEIGHT, BACKGROUND_COLOR)
        var game = Game(
            Area(canvas.width, canvas.height),
            emptyList(),
            Spaceship(SPACESHIP_WIDTH, SPACESHIP_HEIGHT, CANVAS_WIDTH / 2, SPACESHIP_BASE_LINE, SPACESHIP_COLOR)
        )

        canvas.drawGame(game)

        canvas.onTimeProgress(70){
//            game.addShot()
            if (game.alienShots.size < 6)game = game.copy(alienShots = (game.alienShots + Shot()))
            canvas.drawGame(game)
            game = game.moveShot()
        }

        canvas.onMouseMove {
            if (isOnLimit(it)) game = game.moveSpaceship(it.x)
            canvas.drawGame(game)
        }
    }
    onFinish { }
}

