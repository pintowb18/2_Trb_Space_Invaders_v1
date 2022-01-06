import pt.isel.canvas.*

fun main() {

    // val canvas with the wanted sizes and color
    val canvas = Canvas(CANVAS_WIDTH, CANVAS_HEIGHT, BACKGROUND_COLOR)
    // var game, the only mutable point of the whole project
    var game = Game(
        Area(canvas.width, canvas.height),
        emptyList(),
        null,
        Spaceship(
            SPACESHIP_WIDTH,
            SPACESHIP_HEIGHT,
            canvas.width / 2 - SPACESHIP_WIDTH / 2,
            SPACESHIP_BASE_LINE - SPACESHIP_HEIGHT / 2,
            SPACESHIP_COLOR
        ),
        alienList(),
        false,
        false,
        STEP)

    onStart {
        // draws the first state of the game
        canvas.drawGame(game)

        // Cycle where the changes to the game happen 70 times per second
        canvas.onTimeProgress(FPS) {
            // this if makes that the game only gets iterated if the val over isn't true.
            if (!game.over) {
                game = game.changes()
                canvas.drawGame(game)

            }
        }
        // Cycle where the changes to the list of Aliens happen on a period of 300 milliseconds
        canvas.onTimeProgress(300) {
            if (!game.over) {
                game = game.alienIsOnLimit().alienMove()
            }
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
            if (game.shipShot == null )game = game.shot(it.x)
        }
    }
    onFinish { }
}
