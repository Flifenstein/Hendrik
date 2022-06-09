package furhatos.app.Hendrik.flow.formal

import furhatos.app.myadvancedskill.flow.mode
import furhatos.flow.kotlin.*

/**
 * Subflow for the administration part of the conversation with the formal CA.
 **/

/** Ask about the Master's programme **/
val MasterProgramQuestion: State = state {
    onEntry {
        println("entering ${thisState.name} " + mode)
        furhat.ask (
            "Which Master's programme do you follow?"
        )

        goto(EnrolledUniversityQuestion)
    }
}

/** Ask at which university is the user enrolled **/
val EnrolledUniversityQuestion: State = state {
    onEntry {
        println("entering ${thisState.name} " + mode)
        furhat.ask (
            "At which university are you primary enrolled in (hoofdinschrijving)?"
        )

        goto(LearningGoalsQuestion)
    }
}