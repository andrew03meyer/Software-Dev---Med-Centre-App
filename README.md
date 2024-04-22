# Software Development - Assignment 1/2

## Overview

* Create an interface from the perspective of  a doctor to organise patients, bookings and prescriptions.
* Group 15b

## User Stories

* Callum
  * "As a doctor, I want to log all access to the system, so I can track who has viewed/modified the system"
  * "As a doctor, I want to ensure that only I can access my account, so that my data is secure"
* Jai
  * "As a doctor, I want to enter the patients prescriptions and visit details so I can keep a record of what i've prescribed and I can organise what a patients needs in the future"
  * "As a doctor, I want to be able to assign a new doctor or member of staff to a patient in case they're more comfortable having a female or male doctor"
* Andrew
  * "As a Doctor, I want to be able to view visit details and prescriptions in a clear and understandable format, so that I can effectively organise my patients information"
  * "As a Doctor, I want to be able to view only my own patients, so that there is no unnecessary data that I don't need"
* Louis
  * "As a doctor, I want to be able to contact the receptionist, so that I can let my next patient know I'm ready to see them"
  * "As a doctor, I want to edit my patients visit details and prescriptions, so that I can kep client details up to date"
* Kris
  * "As a doctor, I want to be able to view bookings of all patients so that I can access the information from other doctors"
  * "As a doctor, I want to be able to see all patients details, so that I can give second opinions and keep track of other doctors patients"

## Conversations

* Callum
  * Developers: Callum, Jai
  * Users: Louis, Andrew, Kris
  * User story 1:
    * Q: Do you want that to be in an audit log?
    * A: Yes, I want an audit log of every user and I want to have a selector for each patient. So, everything that is changed about that patient.
    * Q: Is that everything about the patient?
    * A: Yes, a log of previous prescriptions, diagnosis, if a booking has changed.
    * Q: Would you like a list log of all users, or more like a selector (filter)?
    * A: I would like a filter, and specifically see certain users.
    * Q: What would you like to see as the metadata of the changes in the log?
    * A: Date, person who performed the changes, a small description of what, and why.
  * User story 2:
    * Q: Would you prefer just a username password pair, or would you like other means?
    * A: It would be really handy if I could log in with my email or my staff ID.
    * Q: Would you like to have a password auto-reset?
    * A: Yes, it would be really handy and keeps my account more safe
* Jai
  * Developers: Jai, Andrew
  * Users: Callum, Louis, Kris
  * User story 1:
    * Q: What specifically would you like to enter on the prescription screen?
    * A: I'd like to enter their allergies, past prescriptions, dosage, when the previous prescription was prescribed, name of the prescription, information on prescriptions which are known to be dangerous together, what the prescription was prescribed for.
    * Q: How would you like the layout to look?
    * A: I'd want symtons to be a category with a date next to it. I'd like a place to record other notes from an appointment. I'd also like to record their current and past prescriptions. Also, date and time.
    * Q: Do you want a review stage of your input?
    * A: Yes, it would be useful to make sure all the data i've inputted is correct.
  * User story 2:
    * Q: Would like have a note that goes with the change to explain why you're transfering a patient?
    * A: Yes, so that you can explain why the transfer is happening. But there should be the option to leave blank.
    * Q: Would you like the option to show if the tranfer is permenant or temporary?
    * A: Yes, if there is one checkup that the patient is transferred to, I'd like to be able to note that it is temporary
* Andrew
  * Developers: Andrew, Jai, Kris
  * Users: Callum, Louis
  * User Story 1:
    * Q: What format would you like to view your visit details and prescriptions in?
    * A: I'd like to have a section where my previous visits are timestamped and have notes from me about what happened and if I assigned any prescriptions to that patient
    * Q: What information would you like to be able to input?
    * A: Time and date of visit, any key points, any prescriptions and if they are returning at all (if so, when)
    * Q: How would you like to access this information?
    * A: I'd like to be able to see it per patient, in a specific history section
  * User story 2:
    * Q: Would you like be able to see all patients of the practice or only your own?
    * A: I would only like to be able to see my patients, to keep my screen clearer.
    * Q: How would you like to see your patients?
    * A: I would like to see a picture of them with their name and the date of their next appointment, if applicable. Then get more information if I click on them
* Louis
  * Developers: Louis, Jai
  * Users: Callum, Andrew
  * User Story 1:
    * Q: What layout would you like, for contacting the reception?
    * A: I'd like to be able to send a message to the receptionist, I would also like a button I can press where I don't have to write anything
    * Q: Where would you like us to put this feature?
    * A: I want it easily accessible as I will be using it often
    * Q: Would you like a response from the receptionist?
    * A: Yes, I would like an indicator to show they've acknowledged it
  * User Story 2:
    * Q: What details/records would you like to be able to edit?
    * A: I would like to add and edit the prescriptions on the patients account and give them a diagnosis. But if it comes to it, I would like to be able to view/change some of the other details.
    * Q: How would you like to edit these medical records?
    * A: After I search for a patient, I'd like to be able to click on their profile and edit the information from there.
    * Q: Would you like some feature that helps you prevent mistakes?
    * A: Yes, I would like some checks that the information I put in is valid, also I'd like to be able to revert to an older version if the changes I make are incorrect
* Kris
  * Developers: Kris, Louis, Andrew
  * Users: Jai, Callum
  * User Story 1:
    * Q: How would you like to view your bookings?
    * A: I'd like to have a calendar that shows me my whole month, and have sections for individual days. I'd also like to have a section to the side that shows my daily schedule
    * Q: What counts as a task, what should be on the calendar?
    * A: Anything that I have scheduled. For example: appointments, meetings with other doctors, personal things.
    * Q: How would you like to order and categorise the tasks?
    * A: It would be great if they could be visually categorised as well as filterable. They need to be shown in chronological order so that I know which task is next. Also they should be time stamped, and have a description of what it is, where it is, and with whom.
  * User story 2:
    * Q: Would you like the ability to comment on other doctors opinions?
    * A: Yes, if the doctor has a different opinion to me, I'd like to be able to tell them about it
    * Q: What data would you like it to show?
    * A: Have the details of patient, name, prescriptions.
    * Q: How do you want it layed out?
    * A: I'd like my comments ad changes to go on the left and the previous doctor's on the right-hand side.
