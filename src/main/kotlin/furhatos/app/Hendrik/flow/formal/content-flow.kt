package furhatos.app.Hendrik.flow.formal

import furhatos.app.myadvancedskill.flow.mode
import furhatos.flow.kotlin.*

/**
 * Subflow for asking about the course content.
 **/

/** Ask about learning goals and assessment criteria **/
val LearningGoalsQuestion: State = state {
    onEntry {
        println("entering ${thisState.name} " + mode)
        furhat.ask (
            "Were the learning goals and the related assessment criteria clear?"
        )
    }
    onResponse {
        goto(CourseTopicQuestion)
    }
}

/** Ask about the course topics **/
val CourseTopicQuestion: State = state {
    onEntry {
        println("entering ${thisState.name} " + mode)
        furhat.ask (
            "Were the course topics relevant for the educational programme?"
        )
    }
    onResponse {
        goto(ChallengingActivitiesQuestion)
    }
}