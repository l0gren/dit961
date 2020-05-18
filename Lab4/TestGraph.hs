module TestGraph (testGraph) where

import Graph

testGraph =
  addEdge "D" 4 "F" $
  addEdge "C" 2 "D" $
  addEdge "C" 3 "B" $
  addEdge "D" 3 "E" $
  addEdge "E" 2 "B" $
  addEdge "A" 1 "E" $
  addEdge "A" 1 "B" $
  addNode "A" $
  addNode "B" $
  addNode "C" $
  addNode "D" $
  addNode "E" $
  addNode "F" $
  empty