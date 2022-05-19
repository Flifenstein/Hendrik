package furhatos.app.myadvancedskill.utils

import furhatos.event.Event
import furhatos.records.User

class RandomHeadEvent : Event()
class UserAttentionGainedEvent(user: User) : Event() {
    val user = user
}

class UserStartAttendEvent(user: User) : Event() {
    val user = user
}

class unexpectedIdlingEvent() : Event() {
}