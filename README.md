# Brain Game - An neurons simulator

Brain Game is an basic simulator that simulate how synapse worked. It find the shortest path and send the nerve impulse to the destination neurons through synapses.

## Introduction
This is an assignment for WIA 1002 Data Structure of University of Malaya. For the topic we are assigned with, which is Brain Game, we are required to create a simulation program to visualize how human brain works. Human brains consist of numerous neurons connected to each other through synapse. Basically, the simulation we created will have to send ‘messages’ from one selected neuron to the destination neuron through several interconnected neurons. The synapse connecting the neurons has a different length and time needed to pass the ‘message’. We are required to calculate the distance travelled and time taken for each ‘messages’ to be delivered.

The network will definitely have more than 2 neurons. There is a chance that one neuron will not be connected to another neuron. This means that some message task cannot be completed. Each neuron serves as a node in the network. It can have zero or more connections with other neurons. However, two neurons can only have one and only one connection. Each synapse is similar to edges connecting two neurons. The connection has two properties, one being the length of synapse, the other is time needed for a message to travel through it.

## Instructions
To run the program, use Maven to build it

## Explanations
Our program is able to accept inputs from the users. The type of inputs including number of neurons, number of synapse attached to that certain neuron, the index of the connected neurons, the lifetime of the synapse, the distance of the synapse, the time needed for the ‘message’ to pass through the synapse, source neuron and lastly the destination neuron. 

With all these information, our program is capable of calculating and picking the shortest path for the ‘message’ to be delivered from the source neuron to the destination neuron prioritizing the time on the synapse over the distance. If the time on the synapse is the same, only the distance will be taken into account for selection of the path. If the message can reach its destination, our program will display both the total distance travelled and total time needed for the message as the output. Otherwise, it will display “No path available” as the output. 

For each message, user have to key in the indexes of source neuron and destination neuron                and click on the “send message” button in order to get the output. For the subsequent ‘messages’, user has to erase the value in the source and destination neuron textboxes with the new indexes. User can only send one message at a time. 

We have also implemented some cool extra features to make our program more interesting. For the first extra features implemented, we have changed the code from bidirectional synapse to unidirectional synapse meaning that each synapse can only pass message one way. This now means that there can be at most two synapses between any two neurons, each representing forward or backward.

Secondly, we have also added Graphical User Interface (GUI) to visualize the network in the brain. The GUI will show how the neurons are interconnected with each other. Also, it will display the path taken to deliver the ‘message’ which is from the starting vertex to the targeted vertex input by the user.

Thirdly, we have optimized the algorithm whereby the synapse with the shortest distance and/or time will be picked. We prioritize the time before the distance. So if there are synapses with the same time, only the distance will be taken into account.

Lastly, we have also implemented the synapse lifetime. This feature restricts the number of times a synapse can pass messages before it dies and disconnects. The input of each synapse will now contain an extra integer, i, which range between 1 and 10. Once the synapse dies, it cannot pass any message between the neurons anymore.

