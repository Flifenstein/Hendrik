package randomHeadmovements

import furhatos.gestures.BasicParams
import furhatos.gestures.defineGesture

fun getRandomDirection(): Double {
    // Randomizes if Furhat is looking left/right and up/down
    return if (Math.random() < 0.5)
        -1.0 // negative
    else
        +1.0 // positive
}

fun getScaleParameter(): Double {
    return getRandomDirection() * (Math.random() + 0.55)
    // Adds variation to the amplitude of the random headmovements and sets the range
}

fun idleHeadMovements(strength: Double = 1.0, duration: Double = 1.0, amplitude: Double = 5.0, gazeAway: Boolean = false) =
    defineGesture("headMove", strength = strength, duration = duration) {
        frame(1.5, 8.5) {
            // This regulates how long the position will be held and how fast Furhat gets there. Multiplied with duration.
            BasicParams.NECK_TILT to amplitude * getScaleParameter()
            BasicParams.NECK_ROLL to amplitude * getScaleParameter()
            BasicParams.NECK_PAN to amplitude * getScaleParameter()
            // the direction (tilt/roll/pan) of the position shift is completely random. Does not affect attention
        }
        reset(10.0)
    }
fun napHeadMovements(strength: Double = 1.0, duration: Double = 1.0, amplitude: Double = 5.0, gazeAway: Boolean = false) =
    defineGesture("headMove", strength = strength, duration = duration) {
        frame(1.5, 8.5) {
            // This regulates how long the position will be held and how fast Furhat gets there. Multiplied with duration.
            BasicParams.NECK_TILT to amplitude * getScaleParameter()
            BasicParams.NECK_ROLL to amplitude * getScaleParameter()
            BasicParams.NECK_PAN to 0.0
            // the direction (tilt/roll/pan) of the position shift is completely random. Does not affect attention
        }
        reset(10.0)
    }

val gesturePosReset = defineGesture("gesturePosReset") {
    frame(0.3) {
        BasicParams.NECK_PAN to 0.0
        BasicParams.NECK_ROLL to 0.0
        BasicParams.NECK_TILT to 0.0
    }
    reset(0.4)
}
