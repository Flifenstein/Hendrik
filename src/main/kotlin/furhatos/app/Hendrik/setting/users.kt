package furhatos.app.myadvancedskill.setting

import furhatos.flow.kotlin.*
import furhatos.records.User

var User.relation: String? by UserDataDelegate()
var User.startedAttendingFurhat: Long by NullSafeUserDataDelegate { 0.toLong() } // We are keeping track of when user started attended Furhat, but not when it stopeed.
var User.furhatStartedAttendingUser: Long by NullSafeUserDataDelegate { 0.toLong()} // We are keeping track of when Furhat starts to attend a user, but not when it stops.
var User.isSmiling by NullSafeUserDataDelegate { false }