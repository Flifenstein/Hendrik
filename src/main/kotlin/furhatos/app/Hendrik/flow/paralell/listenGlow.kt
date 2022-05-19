package furhatos.app.myadvancedskill.flow.paralell

import furhatos.app.myadvancedskill.flow.Modes
import furhatos.app.myadvancedskill.flow.mode
import furhatos.flow.kotlin.*
import java.awt.Color

// TODO break out into library asset collection

/**
 * listenGlow makes the led ring glow when furhat is listening.
 * use it by creating a parallell flow
 *
 *         parallel(abortOnExit = false) { goto(ListenGlow) }
 *
 */

/** Resusable colors
 * Since the LEDs can generate a significant amount of heat we advise to not use RGB values larger than 127 for
 * extended periods of time.
 * **/
val SOFT_PURPLE = Color(20, 5, 40)
val SOFT_WHITE = Color(20, 20, 20)
val VERY_SOFT_WHITE = Color(5, 5, 5)
val OFF_COLOUR = Color.BLACK

/** Colours to use for our different modes **/
val LISTEN_COLOUR = SOFT_PURPLE
val ACTIVE_COLOUR = SOFT_WHITE
val PASSIVE_COLOUR = VERY_SOFT_WHITE
val IDLE_COLOUR = VERY_SOFT_WHITE
val SLEEPING_COLOUR = VERY_SOFT_WHITE

/** Controls if we want to use LED's when in theses states **/
var listenGlow = true
var activeGlow = true
var passiveGlow = true
var idlingGlow = false
var sleepingGlow = false
var listenLedColor = LISTEN_COLOUR

var listenColorIsSet = false
var activeColorIsSet = false
var passiveColorIsSet = false
var idleColorIsSet = false
var sleepingColorIsSet = false

// TODO I think we should achieve this with events - not by onTime looping checks.
/**
 * State to monitor and set LED colour
 */
val InteractionGlow: State = state {
    onTime(repeat = 200) {
        // Turn on LED's when active or passive
        // but do not override listenColour
        if (!listenColorIsSet) {
            furhat.setModeGlow()
        }
        // Turn on LED's when listening
        if (listenGlow) {
            when {
                // Set listen colour when furhat is listening
                furhat.isListening && !listenColorIsSet -> {
                    furhat.setListenGlow()
                }
                // Reset colour when furhat is not listening
                !furhat.isListening && listenColorIsSet -> {
                    listenColorIsSet = false
                    furhat.setModeGlow() // reset the colour to the appropriate for the mode we are in.
                }
            }
        }
    }
}

fun Furhat.setListenGlow() {
    ledStrip.solid(listenLedColor)
    listenColorIsSet = true
}

fun Furhat.setModeGlow() {
    when (mode) {
        Modes.ACTIVE -> {
            when {
                !activeGlow -> ledStrip.solid(OFF_COLOUR)
                !activeColorIsSet -> {
                    ledStrip.solid(ACTIVE_COLOUR)
                    activeColorIsSet = true
                }
            }
        }
        Modes.PASSIVE -> {
            when {
                !passiveGlow -> ledStrip.solid(OFF_COLOUR)
                !passiveColorIsSet -> {
                    ledStrip.solid(PASSIVE_COLOUR)
                    passiveColorIsSet = true
                }
            }
        }
        Modes.IDLE -> {
            when {
                !idlingGlow -> ledStrip.solid(OFF_COLOUR)
                !idleColorIsSet -> {
                    ledStrip.solid(IDLE_COLOUR)
                    idleColorIsSet = true
                }
            }
        }
        Modes.SLEEPING -> {
            when {
                !sleepingGlow -> ledStrip.solid(OFF_COLOUR)
                !sleepingColorIsSet -> {
                    ledStrip.solid(SLEEPING_COLOUR)
                    sleepingColorIsSet = true
                }
            }
        }
        else -> {
            ledStrip.solid(OFF_COLOUR)
            activeColorIsSet = false
            passiveColorIsSet = false
            idleColorIsSet = false
            sleepingColorIsSet = false
        }
    }
}

/**
 * Use instead of regular listen to make the glow start fading out before listening is over.
 * (to stop furhat not catching speech right at the end of a listen)
 * @param listenTime How long to listen for noSpeechTimeout
 */
fun FlowControlRunner.fadeListen(listenTime: kotlin.Int = furhat.param.noSpeechTimeout) {
    parallel { goto(ListenFader(listenTime)) }
    furhat.listen(listenTime)
}

/**
 * State that runs in parallell to fade the led-strip a little bit before listening stops
 * @param fadeTime The led should fade 500 ms before this
 */
fun ListenFader(fadeTime: kotlin.Int): State = state {
    onEntry {
        print(1)
    }
    onTime(fadeTime - 500) {
        print(500)
        listenLedColor = OFF_COLOUR
        furhat.ledStrip.solid(listenLedColor)
    }
    onTime(fadeTime) {
        listenLedColor = LISTEN_COLOUR
    }
    onTime(fadeTime - 1000) {
        print(1000)
        //listenLedColor = java.awt.Color(0, 30,15)
        listenLedColor =
                java.awt.Color(LISTEN_COLOUR.red / 2, LISTEN_COLOUR.green / 2, LISTEN_COLOUR.blue / 2)
        furhat.ledStrip.solid(listenLedColor)
    }
    onExit {
        print("exit")
        listenLedColor = LISTEN_COLOUR
    }
}