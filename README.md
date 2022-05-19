# Skill
Template skill

## Description
This is a skill template. For more example skills go to furhat https://github.com/FurhatRobotics/

## Usage
Max number of users is set to: 5
No other specific requirements. 

## Flow
# Start of skill
Start in sleeping. Or start in idle. Idle is default. 

# WaitToStart (idling)
Wait for users to enter - robot looks around randomly to keep itself busy.
When user enter - stop idling and go to GetAttention (passive)

No users for a while - go to nap

# GetAttention (passive)
Try to catch their attention by seeking eye contact. Get the attention of user standing closest or user last spoke. Alternate attention.  

Switch attention to eew users entering, or users starting to attend the robot. 

A user that looks at the robot for a while will trigger the robot to start a conversation. 

No users present - go back to WaitToStart. 

# Interaction(Listening)
Maintain eye contact with user and wait for them to initiate the conversation with a greeting. 
Switch attention when another person speaks, or when current user looks away. 
Make a listening face and return their smiles

If the say hello, or how are you - respond politely but don't engage further, and find new user to attend. 
Glance at new users entering - but keep focus on the user speaking. 

No users attending  - go back to GetAttention

# Nap (Sleep)
Sleep behavior. 
Wake up on user entering. 

# DeepSleep (PowerSave)
Turn off projector
Only wake up on wizard button. 