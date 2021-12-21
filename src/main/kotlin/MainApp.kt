import pt.isel.canvas.*

fun main() {

    // val canvas with the wanted sizes and color
    val canvas = Canvas(CANVAS_WIDTH, CANVAS_HEIGHT, BACKGROUND_COLOR)
    // var game, the only mutable point of the whole project
    var game = Game(
        Area(canvas.width, canvas.height),
        emptyList(),
        Shot(),
        Spaceship(
            SPACESHIP_WIDTH,
            SPACESHIP_HEIGHT,
            canvas.width / 2 - SPACESHIP_WIDTH / 2,
            SPACESHIP_BASE_LINE - SPACESHIP_HEIGHT / 2,
            SPACESHIP_COLOR
        ),
        alienList()
    )

    onStart {
        // draws the first state of the game
        canvas.drawGame(game)

        // Cycle where the changes to the game happen 70 times per second
        canvas.onTimeProgress(FPS) {
            // this if makes that the game only gets iterated if the val over isn't true.
            if (!game.over) {
                game = game.removeAlienShot().moveAlienShot().moveShot().shotHit().gameOver()
                canvas.drawGame(game)
                canvas.drawShot(game)
                canvas.drawAliens(game)
            }
        }

        canvas.onTimeProgress(3000) {
            game = game.alienMove()
        }

        // cycle of 250 millisecond with 50% chance to add a alien shot to the list
        canvas.onTimeProgress(250) {
            game = game.addAlienShot()
        }
        // gives the coordenate x of the mouse to the function moveSpaceShip only if it (mouse cursor) is within limits
        canvas.onMouseMove {
            if (isOnLimit(it)) game = game.moveSpaceship(it.x)
        }
        // creates a shot from the spaceship with the press of the mouse click
        canvas.onMouseDown {
            game = game.shot(it.x)
        }
    }
    onFinish { }
}
