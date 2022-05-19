package furhatos.app.myadvancedskill.setting

import furhat.libraries.standard.AutomaticHeadMovements.gazeStraight
import furhatos.app.myadvancedskill.utils.RandomHeadEvent
import furhatos.app.myadvancedskill.utils.attendOtherOrNobody
import furhatos.autobehavior.defineMicroexpression
import furhatos.flow.kotlin.furhat
import furhatos.flow.kotlin.partialState
import furhatos.gestures.BasicParams
import furhatos.gestures.Gestures
import furhatos.records.Location
import randomHeadmovements.napHeadMovements

var enableNapHeadMovements = true

val NapHeadMovements = partialState {
    onTime(2000..2500, 5200..9000, instant = true) {
        raise(RandomHeadEvent())
    }
    onExit {
        furhat.gesture(gazeStraight)
    }
    onEvent<RandomHeadEvent>(instant = true) {
        if (enableNapHeadMovements) {
            // Regulates when Furhat does idle headmovements
            val gesture = napHeadMovements(
                    // these overrides the ones defined in idleheadmovements, was also affecting other functions that have been removed. This is kept if we want to expand this in the future.
                    strength = 1.0,
                    duration = 2.0, // Affect how fast the idle head movements will be
                    amplitude = 5.0, // How big the headmovements will be
                    gazeAway = false // Function removed, but kept here if we re-introduce
            )
            furhat.gesture(gesture)
        }
    }
}

/** avoid too long awkward silences **/
val AutoUserSwitching = partialState {
    val offset = 1000
    onTime(
            delay = MAX_SILENT_ATTEND_TIME,
            repeat = (MAX_SILENT_ATTEND_TIME - offset)..(MAX_SILENT_ATTEND_TIME + offset), // Interval of how often to repeat
            instant = false) {
        println("attending other user, or nobody")
        furhat.attendOtherOrNobody()
        furhat.listen()
    }
}


/** Avoid prolonged eye contact **/
val AutoGlanceAway = partialState {
    val offset = 500
    onTime(
            delay = MAX_SILENT_EYE_CONTACT,
            repeat = (MAX_SILENT_EYE_CONTACT - offset)..(MAX_SILENT_EYE_CONTACT + offset), // Interval of how often to repeat
            instant = false) {
        println("breaking eye contact")
        furhat.glance(Location.DOWN)
        furhat.listen()
    }
}

val DEFAULT_MICROEXPRESSIONS = defineMicroexpression {
    // Fluctuade facial movements. First parameter is frequency, second amplitude, third adjustment.
    fluctuate(0.025, 0.06, 0.12, BasicParams.BROW_UP_LEFT, BasicParams.BROW_UP_RIGHT);
    fluctuate(0.025, 0.2, 0.5, BasicParams.SMILE_CLOSED);
    fluctuate(0.025, 0.2, 0.5, BasicParams.EXPR_SAD);
    // Note! We have to set all values defined by other MicroExpressions to make sure we overwriting them
    fluctuate(0.025, 0.0, 0.0, BasicParams.EYE_SQUINT_LEFT, BasicParams.EYE_SQUINT_RIGHT);
    fluctuate(0.025, 0.0, 0.0, BasicParams.PHONE_BIGAAH);
    // Adjust eye gaze randomly (between -3 and 3 degrees) with a random interval of 200-400 ms.
    repeat(200..400) {
        adjust(-3.0..3.0, BasicParams.GAZE_PAN)
        adjust(-3.0..3.0, BasicParams.GAZE_TILT)
    }
    // Blinking with a random interval of 2-8 seconds
    repeat(2000..8000, Gestures.Blink)
}

// TODO define listening Micro expressions. Raise eyebrows. Smile more. Open eyes more wide. Open mouth slightly. Blink more often
val LISTENING_MICROEXPRESSIONS = defineMicroexpression {
    // Fluctuate facial movements. First parameter is frequency, second amplitude, third adjustment.
    fluctuate(0.025, 0.4, 0.2, BasicParams.BROW_UP_LEFT, BasicParams.BROW_UP_RIGHT);
    fluctuate(0.025, 0.4, 0.6, BasicParams.SMILE_CLOSED);
    // Note! We have to set all values defined by other MicroExpressions to make sure we overwriting them
    fluctuate(0.025, 0.0, 0.0, BasicParams.EXPR_SAD);
    fluctuate(0.025, -0.2, 0.2, BasicParams.EYE_SQUINT_LEFT, BasicParams.EYE_SQUINT_RIGHT); // TODO test if we really can make the eyes squint less?
    fluctuate(0.025, 0.05, 0.1, BasicParams.PHONE_BIGAAH);
    // Adjust eye gaze randomly (between -3 and 3 degrees) with a random interval of 200-400 ms.
    repeat(200..400) {
        adjust(-3.0..3.0, BasicParams.GAZE_PAN)
        adjust(-3.0..3.0, BasicParams.GAZE_TILT)
    }
    // Blinking with a random interval of 2-6 seconds
    repeat(2000..6000, Gestures.Blink)
}