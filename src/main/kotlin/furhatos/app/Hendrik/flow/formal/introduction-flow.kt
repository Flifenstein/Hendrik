package furhatos.app.Hendrik.flow.formal

import furhatos.app.myadvancedskill.flow.mode
import furhatos.flow.kotlin.*

/**
 * Subflow for the formal introduction.
 **/

/** The Furhat presents itself and the program **/
val Presentation: State = state {
    onEntry {
        println("entering ${thisState.name} " + mode)
        furhat.say (
            "This is the formal introduction."
        )

        goto(ConsentForm)
    }
}

/** Inform about the consent form **/
val ConsentForm: State = state {
    onEntry {
        furhat.say (
            "Inform consent."
        )

        goto(Privacy)
    }
}

/** Explain the privacy policy **/
val Privacy: State = state {
    onEntry {
        furhat.say (
            "Privacy policy."
        )

        goto(Confirmation)
    }
}

/** Ask for confirmation **/
val Confirmation: State = state {
    var consent: Boolean?

    onEntry {
        consent = furhat.askYN (
            "Yes or no. Do you confirm we can proceed?"
        ) {
            onNoResponse {
                furhat.say("I'll wait until you are ready")
            }
        }

        if (consent == true) {
            furhat.say(
                "Perfect. Let's start."
            )

            goto(OpeningQuestion)
        } else {
            goto(EndState)
        }
    }
}
