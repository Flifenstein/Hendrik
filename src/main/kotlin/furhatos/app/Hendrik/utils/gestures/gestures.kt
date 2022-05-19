package furhatos.app.myadvancedskill.utils.gestures

import furhatos.gestures.CharParams
import furhatos.gestures.Gestures
import furhatos.gestures.defineGesture


val hearSpeechGesture = defineGesture("hearSpeechGesture") {
    frame(0.4, persist = true) {
        CharParams.EYEBROW_UP to 1.0
    }
    reset(2.5)
}

// Raise the eyebrows slightly instead to signal interest
val Listening = defineGesture("Listening") {
    frame(0.4, persist = true) {
        Gestures.BrowRaise to 0.8
        Gestures.Smile to 0.6
    }
}

val Normal = defineGesture("Normal") {
    frame(0.4, persist = true) {
        Gestures.BrowRaise to 0.0
        Gestures.Smile to 0.0
    }
    reset(0.5)
}