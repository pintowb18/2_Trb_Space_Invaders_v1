import pt.isel.canvas.*


data class Game(
    val area: Area,
    val alienShots: List<Shot>,
    val shipShot: Shot,
    val ship: Spaceship,
    val over: Boolean = false
)

data class Area(val width: Int, val height: Int)
data class Shot(
    val x: Int = SHOT_SPOT.random(),
    val y: Int = TOP_CANVAS,
    val speed: Int = SHOT_SPEED.random()
)

data class Spaceship(val width: Int, val height: Int, val x: Int, val y: Int, val color: Int)
data class Position(val x: Int, val y: Int)
data class BoundingBox(val corner: Position, val width: Int, val height: Int)

const val CANVAS_WIDTH = 700
const val CANVAS_HEIGHT = 500
const val TOP_CANVAS = 0
const val BACKGROUND_COLOR = BLACK
const val SPACESHIP_WIDTH = 50
const val SPACESHIP_HEIGHT = 10
const val SPACESHIP_COLOR = GREEN
const val SPACESHIP_BASE_LINE = 450
const val GUN_WIDTH = 4
const val GUN_HEIGHT = 5
const val GUN_COLOR = YELLOW
const val GUN_SHOT_WIDHT = 4
const val GUN_SHOT_HEIGHT = 7
const val GUN_SHOT_COLOR = WHITE
const val ALIEN_SHOT_COLOR = RED
const val SHIP_SHOT_SPEED = 4
const val FPS = (1000 / 70)
const val GAME_OVER = "Game Over"
val SHOT_SPOT = 0 until CANVAS_WIDTH - 1
val SHOT_SPEED = 1..4
val lst = listOf(0, 1)
