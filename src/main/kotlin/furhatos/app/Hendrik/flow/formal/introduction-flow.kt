package furhatos.app.Hendrik.flow.formal

import furhatos.app.myadvancedskill.flow.Active
import furhatos.app.myadvancedskill.flow.mode
import furhatos.flow.kotlin.*

/**
 * Subflow for the formal introduction.
 **/

/** The Furhat presents itself and the program **/
val Presentation: State = state(Active) {
    onEntry {
        println("entering ${thisState.name} " + mode)
        furhat.say (
            "This is the formal introduction."
        )

        goto(ConsentForm)
    }
}

/** Inform about the consent form **/
val ConsentForm: State = state(Active) {
    onEntry {
        println("entering ${thisState.name} " + mode)
        furhat.say (
            "Inform consent."
        )

        goto(Privacy)
    }
}

/** Explain the privacy policy **/
val Privacy: State = state(Active) {
    onEntry {
        println("entering ${thisState.name} " + mode)
        furhat.say (
            "Privacy policy."
        )

        goto(Confirmation)
    }
}

/** Ask for confirmation **/
val Confirmation: State = state(Active) {
    onEntry {
        val consent: Boolean? = furhat.askYN("Yes or no; do you confirm we can proceed?")

        if(consent == true){
            furhat.say("Let's start!")
            goto(OpeningQuestion)
        } else {
           goto(EndState)
        }
    }
}
