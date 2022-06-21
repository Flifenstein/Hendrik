package furhatos.app.Hendrik.flow.formal

import furhatos.app.myadvancedskill.flow.Active
import furhatos.app.myadvancedskill.flow.mode
import furhatos.flow.kotlin.*

/**
 * Subflow for the opening question of the formal agent.
 **/

/**
 * The Furhat starts the conversation by asking a general question.
 * To help the user in the memory recover process, we ask either a bad, good, or recent experience.
 * It is too early to ask about a general question.
 *  **/
val OpeningQuestion: State = state(Active) {
    onEntry {
        println("entering ${thisState.name} " + mode)
        furhat.ask (
            "Can you describe a bad, good, or recent event that happened during the course?"
        )
    }
    onResponse {// <intent_class> needs to be created
        goto(MasterProgramQuestion)
    }
}