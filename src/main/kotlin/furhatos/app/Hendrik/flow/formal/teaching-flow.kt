package furhatos.app.Hendrik.flow.formal

import furhatos.app.myadvancedskill.flow.Active
import furhatos.app.myadvancedskill.flow.mode
import furhatos.flow.kotlin.*

/**
 * Subflow for the questions about the teacher.
 **/

/** Ask if the activities were challenging the user to study **/
val ChallengingActivitiesQuestion: State = state(Active) {
    onEntry {
        println("entering ${thisState.name} " + mode)
        furhat.ask (
            "Did the teaching activities challenged you to study?"
        )
    }
    onResponse {
        goto(ThinkForYourselfQuestion)
    }
}

/** Ask if the teaching staff encouraged you to think for yourself **/
val ThinkForYourselfQuestion: State = state(Active) {
    onEntry {
        println("entering ${thisState.name} " + mode)
        furhat.ask (
            "Did the teaching staff encourage you to think for yourself?"
        )
    }
    onResponse {
        goto(TeacherInsightQuestion)
    }
}

/** Ask about teacher's insight **/
val TeacherInsightQuestion: State = state(Active) {
    onEntry {
        println("entering ${thisState.name} " + mode)
        furhat.ask (
            "Did you feel that the teacher had a good insight in how the students were keeping up with the content matter and acted adequately when necessary?"
        )
    }
    onResponse {
        goto(CourseFeedbackQuestion)
    }
}

/** Ask about course feedback **/
val CourseFeedbackQuestion: State = state(Active) {
    onEntry {
        println("entering ${thisState.name} " + mode)
        furhat.ask (
            "Did the feedback during the course give you sufficient information for further learning?"
        )
    }
    onResponse {
        goto(KnowledgePersistentQuestion)
    }
}