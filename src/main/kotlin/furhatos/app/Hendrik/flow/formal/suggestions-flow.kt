package furhatos.app.Hendrik.flow.formal

import furhatos.app.myadvancedskill.flow.mode
import furhatos.flow.kotlin.*

/**
 * Subflow for the suggestion part of the conversation with the formal CA.
 **/

/** Ask about the strong point of the course **/
val StrongPointsQuestion: State = state {
    onEntry {
        println("entering ${thisState.name} " + mode)
        furhat.ask (
            "What are the strong points of this course?"
        )
    }
    onResponse {
        goto(SuggestionsQuestion)
    }
}

/** Ask if the user has any suggestion for the course improvement **/
val SuggestionsQuestion: State = state {
    onEntry {
        println("entering ${thisState.name} " + mode)
        furhat.ask (
            "Do you have any suggestions to improve this course, please elaborate?"
        )
    }
    onResponse {
        goto(AnswerRegistration)
    }
}
