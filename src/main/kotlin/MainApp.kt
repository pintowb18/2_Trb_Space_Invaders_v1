import pt.isel.canvas.*

fun main() {
    onStart {
        val canvas = Canvas(CANVAS_WIDTH, CANVAS_HEIGHT, BACKGROUND_COLOR)
        var game = Game(
            Area(canvas.width, canvas.height),
            emptyList(),
            Shot(),
            Spaceship(SPACESHIP_WIDTH, SPACESHIP_HEIGHT, canvas.width / 2, SPACESHIP_BASE_LINE, SPACESHIP_COLOR)
        )

        canvas.drawGame(game)

        canvas.onTimeProgress(7){
            game = game.addAlienShot()
            game = game.removeAlienShot()
            game = game.moveAlienShot()
            game = game.moveShot()
            game = game.shotHit()
            canvas.drawGame(game)
            canvas.drawShot(game)
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

