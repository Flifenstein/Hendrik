package furhatos.app.Hendrik.flow.formal

import furhatos.app.myadvancedskill.flow.Active
import furhatos.app.myadvancedskill.flow.mode
import furhatos.flow.kotlin.*

/**
 * Subflow for the opening question of the formal agent.
 **/

/** The Furhat presents itself and the program **/
val OpeningQuestion: State = state(Active) {
    onEntry {
        println("entering ${thisState.name} " + mode)
        furhat.ask (
            "What do you think about the course?"
        )
    }
    onResponse {// <intent_class> needs to be created
        goto(MasterProgramQuestion)
    }
}