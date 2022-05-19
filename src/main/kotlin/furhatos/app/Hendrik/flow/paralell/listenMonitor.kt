package furhatos.app.myadvancedskill.flow.paralell

import furhatos.app.myadvancedskill.flow.Modes
import furhatos.app.myadvancedskill.flow.mode
import furhatos.app.myadvancedskill.utils.unexpectedIdlingEvent
import furhatos.flow.kotlin.*


// TODO I think we should achieve this with events - not by onTime looping checks.
/**
 * State to monitor and set LED colour
 */
val ListenMonitor: State = state {
    onTime(repeat = 5000) { // repeat every 5 seconds
        if (!furhat.isListening || !furhat.isSpeaking)
            if (mode == Modes.ACTIVE)
                raise(unexpectedIdlingEvent())
    }
}