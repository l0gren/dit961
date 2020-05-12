Class invariant for PriorityQueue.java
---------------------------------------
 - The heap property must always be upheld
 - Completeness must always be satisfied
 - There must for every element in the queue exist exactly one entry in the HashMap 'positions' with the element and its corresponding index in the queue, for fast retrieval

Complexity
---------------------------------------
 - minimum(): O(1) time
    - The minimum element is always first in the queue
 - deleteMinimum(): O(logn) time
    - The last element is placed first to the queue, then sifted down, which takes O(logn) time due to the height of the tree
 - add(): O(logn) time
    - The element is placed last in the queue, then sifted up, which takes O(logn) time due to the height of the tree
 - update(): O(logn) time
    - The element is found in O(1) time due to the hashmap
    - The element is modified in O(1) time
    - The element is sifted up/down, which takes O(logn) time due to the height of the tree 



