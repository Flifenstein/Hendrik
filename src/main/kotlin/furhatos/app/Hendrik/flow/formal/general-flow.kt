package furhatos.app.Hendrik.flow.formal

import furhatos.app.myadvancedskill.flow.Active
import furhatos.app.myadvancedskill.flow.mode
import furhatos.flow.kotlin.*

/**
 * Subflow for the general part of the conversation with the formal CA.
 **/

/** Ask about the gained knowledge persistent **/
val KnowledgePersistentQuestion: State = state(Active) {
    onEntry {
        println("entering ${thisState.name} " + mode)
        furhat.ask (
            "Will the knowledge and the skills gained in this course not quickly fade away? That is: they are lasting."
        )
    }
    onResponse {
        goto(StudyTimeQuestion)
    }
}

/** Ask about the study time **/
val StudyTimeQuestion: State = state(Active){
    onEntry {
        println("entering ${thisState.name} " + mode)
        furhat.ask (
            "In general, how was the amount of study time you had to put in this course compared to the number of ECs granted?"
        )
    }
    onResponse {
        goto(MarkQuestion)
    }
}

/** Ask about giving the course a mark **/
val MarkQuestion: State = state(Active){
    onEntry {
        println("entering ${thisState.name} " + mode)
        furhat.ask (
            "What mark, from 1 to 10, would you give this course?"
        )
    }
    onResponse {
        goto(StudyMaterialQuestion)
    }
}

/** Ask about the study material **/
val StudyMaterialQuestion: State = state(Active){
    onEntry {
        println("entering ${thisState.name} " + mode)
        furhat.ask (
            "What is your opinion on the study material? Consider book, lecture notes, guidelines, study guide, articles, etcetera."
        )
    }
    onResponse {
        goto(StrongPointsQuestion)
    }
}