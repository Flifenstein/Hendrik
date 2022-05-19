package furhatos.app.myadvancedskill.setting

import java.util.concurrent.TimeUnit

/** Engagement policy parameters */
// Number of users to actively engage with
val MAX_NUMBER_OF_USERS = 6
// defining the size of the interaction space, controls when a user detected (inner), and when they are dropped (outer)
val INTERACTION_SPACE_INNER = 1.0 // meters
val INTERACTION_SPACE_OUTER = 4.0 // meters

//  Skill specific parameter to set a distance on when to greet the user and kick of the interaction
val MAX_DISTANCE_TO_GREET = 1.3 // meters // TODO change size of interaction space instead?

/** Switching modes **/
// after idle for this long, go to nap
val WAIT_TIME_TO_NAP_IN_SECONDS: Long = 300 // seconds. Use this to define the time in seconds
val WAIT_TIME_TO_NAP = TimeUnit.SECONDS.toMillis(WAIT_TIME_TO_NAP_IN_SECONDS).toInt() // Use this to input the time in milliseconds

// after napping for this long, go to sleep
val NAP_TIME_BEFORE_SLEEP_IN_SECONDS: Long = 600 // seconds. Use this to define the time in seconds
val NAP_TIME_BEFORE_DEEP_SLEEP = TimeUnit.SECONDS.toMillis(NAP_TIME_BEFORE_SLEEP_IN_SECONDS).toInt() // Use this to input the time in milliseconds

/** Attention switching **/
// after idling not attending a user, find a user to attend
val WAIT_TIME_TO_ATTEND_IN_SECONDS: Long = 30 // seconds. Use this to define the time in seconds
val WAIT_TIME_TO_ATTEND = TimeUnit.SECONDS.toMillis(WAIT_TIME_TO_ATTEND_IN_SECONDS).toInt() // Use this to input the time in milliseconds

// When attending a user
val MAX_SILENT_EYE_CONTACT = 5000 //miliseconds
val MAX_SILENT_ATTEND_TIME_IN_SECONDS: Long = 10 // seconds. Use this to define the time in seconds
val MAX_SILENT_ATTEND_TIME = TimeUnit.SECONDS.toMillis(MAX_SILENT_ATTEND_TIME_IN_SECONDS).toInt() // Use this to input the time in milliseconds

var lastUserSwitchTimeStamp: Long = 0

val MIN_TIME_TO_ATTEND_USER = 3000 // milliseconds
val MAX_TIME_TO_ATTEND_USER = 15000 // milliseconds

/** Attention parameters **/
//  Set angle when users are no longer considered attending Furhat
val DEFAULT_ATTENTION_GAINED_THRESHOLD = 20.0
val DEFAULT_ATTENTION_LOST_THRESHOLD = 35.0

// this value determines how long a user has to attend furhat before the main interaction starts */
val ATTEND_TIME_TO_START = 2300 //milliseconds

/** Speech parameters **/
// skill default values - give user longer time to think
val DEFAULT_END_SIL_TIMEOUT = 1600 //milliseconds
val DEFAULT_NO_SPEECH_TIMEOUT = 7000 //milliseconds

/** Behavior parameters **/
// skill default values for smiling back behaviour
val DEFAULT_ENABLE_SMILE_BACK = true
val DEFAULT_SMILE_PROBABILITY = 0.4
val DEFAULT_BIG_SMILE_PROBABILITY = 0.3
val DEFAULT_SMILE_BLOCK_DELAY: Long = 5000 //milliseconds

/** Flow parameters */
var localOnNoMatchCount = 0
var localOnNoResponseCount = 0