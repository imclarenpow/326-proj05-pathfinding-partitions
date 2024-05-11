# Pathfinding Partitions
A Ferrers board is a way of representing a partition, (a1, a2, . . . , ak) as a set of rows of
dots, where the ith row has ai dots. For example, the partition (5, 4, 3, 1) is represented
by the following Ferrers board:
![ferrers board](readme/ferrersboard.png)
We could choose one of the columns of the Ferrers board, remove it, and replace it with
a row of the same length as shown below.
![ferrers board becomes](readme/ferrersboardbecomes.png)<br>
Call such a rearrangement a move. The purpose of this étude is to find a sequence of
moves that will transform one partition into another, whenever this is possible.
## Implementation
main()<br>
    Calls 'stdin()' method which deals with input, putting it in another method keeps main tidier.<br>
    Checks that the input size is even, this should only happen if there is a single line of numbers at the end after a seperator, in which case this line is removed<br>
    iterates through the length of the nested arraylist 'input', checking that the sum of both partitions are equal to each other - if not, there is no point calling the aStar method as it isn't possible.<br>
    Calls aStar() inputting the next two arrays.<br>
    Prints moves then iterates through all moves and prints them. <br>
    Else there is no solution, so print '# No solution'
aStar() - i used [this page](https://en.wikipedia.org/wiki/A*_search_algorithm) as reference mainly<br>
    Implementation of a basic A* search algorithm that looks for the moves needed to get to the goalPosition.<br>
    Initialise two maps 'currCost' keeping track of the cost of reaching each state from init state and 'from' that keeps track of the previous state.<br>
    While the costs priority queue still has stuff in it<br>
        Retrieve the state with lowest cost from costs priority queue<br>
        If state matches goal position, reconstruct the path to the initial state (works backward)<br>
        Else generates neighbour states by making all possible moves from current state.<br>
        For each neighbour state: Calculate the cost of reaching the neighbour state from the original state. If it hasn't been visited before or the new cost is lower than the previous cost, update the cost and add the neighbouring state to the costs priority queue.<br>
        Puts previous state in 'from'<br>    
    Reconstruct Path is goal state is reached - basically just adds from curr.configuration until null, then reverses it because the algorithm works backward from our "moves"<br>
    If no solution is found, it prints '# No solution' followed by the first position on another line and the goal. After this is clears the bestMoves so its not printed (it shouldn't be regardless)<br>
makeMove()<br>
    Simple method that makes the move specified, then sorts it back into reverse order so that it looks right.<br>
heuris()<br>
    The hueristic for the A* method, this is probably as optimal as possible for this problem without overstepping and missing possible steps.<br>
    It iterates through the largest length out of the current ArrayList and the Goal and uses ternary operators for when the length of the shorter array runs out.<br>
stdin()<br>
    takes in the raw input from std in and puts it into a way that the program can work with.
    it only considers the first two partitions after the seperator as a way to avoid the impossible while also not exiting the program over something as minor as this.<br>
    puts the partitions in reverse order and adds them to the output arraylist.<br>
partitionFormatting()<br>
    Simple method that deals with printing partitions as specified rather than just using .toString().

## Task
Write a program that takes input from stdin a data file formatted according to the rules
of the Parsing Partitions étude where each scenario in the file will consist of exactly two
partitions of the same integer.<br>
The output (to stdout) should be in standard form and represent the solution to each
scenario, i.e., a shortest sequence of moves that transforms the first partition to the
second. Each scenario in the output should be in standard form and should start with
a comment line of the form: 
```# Moves required: <number of moves>```
(with a suitable replacement for ```<number of moves>```). This should be followed by
a sequence of lines, starting with the first partition, and ending with the last, so that
each line is obtained from the previous one by a move, and the number of moves used
is minimal. In the event that no such sequence of moves exists, the output should start
with:<br>
```# No solution possible```
followed just by the two given partitions.

## Standards
For an achieved standard, the program must work correctly on valid input representing
partitions of size 25 or less.<br>
Merit criteria include the ability to handle much larger partitions efficiently, handling
poorly-formatted input gracefully, and clearly written code.<br>
Excellence criteria include some significant extension to the functionality of the pro-
gram, or an investigation of general properties of the problem.<br>