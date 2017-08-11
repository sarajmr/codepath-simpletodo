# Pre-work - *Simple ToDo*

**Simple ToDo** is an android app that allows building a todo list and basic todo items management functionality including adding new items, editing and deleting an existing item.

Submitted by: **Saraj Mudigonda**

Time spent: **20** hours spent in total

## User Stories

The following **required** functionality is completed:

* [X] User can **successfully add and remove items** from the todo list
* [X] User can **tap a todo item in the list and bring up an edit screen for the todo item** and then have any changes to the text reflected in the todo list.
* [X] User can **persist todo items** and retrieve them properly on app restart

The following **optional** features are implemented (not implemented yet in the basic app version, will target these features in the advanced version):

* [ ] Persist the todo items [into SQLite](http://guides.codepath.com/android/Persisting-Data-to-the-Device#sqlite) instead of a text file
* [ ] Improve style of the todo items in the list [using a custom adapter](http://guides.codepath.com/android/Using-an-ArrayAdapter-with-ListView)
* [ ] Add support for completion due dates for todo items (and display within listview item)
* [ ] Use a [DialogFragment](http://guides.codepath.com/android/Using-DialogFragment) instead of new Activity for editing items
* [ ] Add support for selecting the priority of each todo item (and display in listview item)
* [ ] Tweak the style improving the UI / UX, play with colors, images or backgrounds

The following **additional** features are implemented (not implemented yet in the basic app version, will target these features in the advanced version):

* [ ] List anything else that you can get done to improve the app functionality!

## Video Walkthrough

Here's a walkthrough of implemented user stories:

<img src='https://github.com/sarajmr/codepath-simpletodo/blob/master/SimpleToDo-Demo.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />

GIF created with [LiceCap](http://www.cockos.com/licecap/).

## Project Analysis

As part of your pre-work submission, please find answers to the following questions below:

**Question 1:** "What are your reactions to the Android app development platform so far? Compare and contrast Android's approach to layouts and user interfaces in past platforms you've used."

**Answer:** Android Studio IDE is pretty intuitive. It is easy to build a basic app using the in-built layouts/UI. However, it also allows custom UI/layout options thus improving the customer experience drastically.  Need to understand the tricks and best practices to make the app more appealing.   

**Question 2:** "Take a moment to reflect on the `ArrayAdapter` used in your pre-work. How would you describe an adapter in this context and what is its function in Android? Why do you think the adapter is important? Explain the purpose of the `convertView` in the `getView` method of the `ArrayAdapter`."

**Answer:** Adapter is an interface between data source and layout. In other words, Adapters are way to connect a View (ListView in this case) with some kind of data source (array in this case). Adapters are important as they help not only optimize the implementation but  improves the performance as well. Adpaters render only those View objects that are either already on-screen or that are about to move on-screen. This way, the memory consumed by an adapter view can be constant and independent of the size of the data set.
They also allow developers to minimize expensive layout inflate operations and recycle existing View objects that have move off-screen. This keeps CPU consumption low. The method getView() has a parameter called convertView which points to the unused view in the recycler. Through the convertview, the Adapter tries to get hold of the unused view and reuse it to display the new view. This is partuclarly useful 

## Notes
These are some of the challanges I encountered:
1. There are multiple ways to implement the same functionality, the challenge is find the most optimal option
2. While there is tons of documentation available on developer.android.com and help on stackoverflow sometimes it can be very time consuming to get to the best solution to your problem

## License

    Copyright [2017] [Saraj Mudigonda]

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
