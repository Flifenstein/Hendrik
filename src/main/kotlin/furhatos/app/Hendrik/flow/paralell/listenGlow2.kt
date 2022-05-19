package furhatos.app.myadvancedskill.flow.paralell

import furhatos.app.myadvancedskill.flow.Modes
import furhatos.app.myadvancedskill.flow.mode
import furhatos.event.monitors.MonitorListenStart
import furhatos.event.monitors.MonitorSpeechEnd
import furhatos.flow.kotlin.furhat
import furhatos.flow.kotlin.partialState

// TODO can we get the LED listening state to be as simple as this?

val ListenGlow2 = partialState() {
    onEvent<MonitorListenStart> {
        if (listenGlow) {
            furhat.ledStrip.solid(LISTEN_COLOUR)
        }
    }
    onEvent<MonitorSpeechEnd> {
        if (activeGlow) {
            when {
                mode == Modes.ACTIVE -> furhat.ledStrip.solid(ACTIVE_COLOUR)
                else -> furhat.ledStrip.solid(OFF_COLOUR)
            }
        }
    }
}