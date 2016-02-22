# Rover Navigation: A Knowledge Based System

Expert system software programs, also known as knowledge-based systems, are computer programs that emulate the **knowledge** and **analytical** skills of one or more human experts, related to a specific subject. In the past, rover navigation required input from a human to travel. However, with the rotation of the earth and other factors, communication with rovers was limited to only a couple times a day. So once the rover got to it's goal, it had to wait for the human to communicate. This made traveling very slow.

Why was the human instructions required?


Planetary surface rovers have many means to navigate from a location to a goal. For this project, we will be focusing on the navigation of local obstacles. Obstacles can vary in type and problem:

- Blocking: The rover must go around or avoid.
- Difficult: The rover can traverse but with a consequence. Such as getting stuck or causing damage. While these may be avoided, there can be a scenario where the rover must traverse it.
- Resources: The rover's resources are low and can't complete the journey.

Navigating these obstacles will require some analysis and decision making. Using various sensors as inputs, and the type of obstacle, the rover will have to decide what action to take.

The objective of the rover will be to travel to a goal location, to gather samples, and return to the original location. The path will be planned without knowledge of the obstacles, and as the robot travels it will add knowledge of those obstacles to it's database. The obstacles may move...

## Modules

The following are some of the major modules (packages?) that will be needed.

- Grid based GUI
- Landscape Engine for creating random landscapes (grid with obstacles). Also need static landscape for testing.
- Inference Engine for decision making.
    * Knowledge Base class
- Robot (user) input
    * Sensor classes
- Robot (user) output
    * The robot performs actions

## Action Items

- Define Sensors for inference engine input.

## Refs

https://spinoff.nasa.gov/Spinoff2008/ct_4.html
