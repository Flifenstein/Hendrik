package furhatos.app.Hendrik.flow


import furhatos.app.myadvancedskill.flow.Active
import furhatos.app.myadvancedskill.flow.mode
import furhatos.app.myadvancedskill.utils.attendOtherOrNobody
import furhatos.flow.kotlin.*
import furhatos.gestures.Gestures

/**
 * Subflow for the formal introduction.
 **/

/** The Furhat presents itself and the program **/
val Presentation: State = state(Active) {
    onEntry {
        println("entering ${thisState.name} " + mode)
        furhat.say {
            "This is the formal introduction."
        }
        goto(ConsentForm)
    }
}

/** Inform about the consent form **/
val ConsentForm: State = state(Active) {
    onEntry {
        furhat.say {
            "Inform consent."
        }
        goto(Privacy)
    }
}

/** Explain the privacy policy **/
val Privacy: State = state(Active) {
    onEntry {
        furhat.say {
            "Privacy policy."
        }
        goto(Confirmation)
    }
}

/** Ask for confirmation **/
val Confirmation: State = state(Active) {
    var consent: Boolean? = false

    onEntry {
        consent = furhat.askYN ("Yes or no. Do you confirm we can proceed?") {
            onNoResponse {
                furhat.say("I'll wait until you are ready")
                furhat.attendOtherOrNobody() // Make sure to shift attention to signal end of conversation.
            }
        }
    }

    onResponse {
        if (consent == true) {
            furhat.say("Perfect. Let's start.")
            // goto() // TODO jump to next state
        } else {
            // goto() // TODO jump to end state
        }
        terminate()
    }
}
