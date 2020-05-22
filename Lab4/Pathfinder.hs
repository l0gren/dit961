module Pathfinder
  (shortestPath) where

import Graph
import Data.PSQueue (PSQ)
import qualified Data.PSQueue as P
import Data.Map (Map)
import qualified Data.Map as M
import qualified Data.Maybe as Maybe

-- Exposed method that returns shortest path between two nodes in a given graph
shortestPath :: (Ord name, Ord cost, Num cost) => Graph name cost -> name -> name -> Maybe ([name], cost)
shortestPath g from to = parsePath from to cost paths ([], 0)
  where
    paths = dijkstra g (enqueue (from, 0, from) P.empty) M.empty
    cost = case (M.lookup to paths) of
      Nothing -> error "No path found"
      Just cost -> fst cost 

-- Implementation of Dijkstra's shortest path. Returns a map of nodes with their distance from starting node, and previous node
dijkstra :: (Ord name, Ord cost, Num cost) =>  Graph name cost -> PSQ name (cost, name) -> Map name (cost, name) -> Map name (cost, name)
dijkstra g q s 
  | P.null q = s
  | otherwise = dijkstra g q' s'
    where
      min = Maybe.fromJust (P.findMin q) -- Safe since we check P.null
      src = P.key min 
      trail = P.prio min
      q' = enqueueList src (fst trail) adj (P.deleteMin q)
        where
          adj = case (Graph.neighbors g src) of
            Nothing -> error "Error: Node not in graph"
            Just adj -> Maybe.fromJust (Graph.neighbors g src)
      s'
        | M.member src s = s
        | otherwise = M.insert src trail s         

-- Insert target of an edge into queue {Target: (weight, Source)}
enqueue :: (Ord name, Ord cost, Num cost) => (name, cost, name) -> PSQ name (cost, name) -> PSQ name (cost, name)
enqueue (src, cost, target) q = P.insert target (cost, src) q

-- Insert list of nodes from source-node into queue, adding their weights {Target: (weight, Source)}
enqueueList :: (Ord name, Ord cost, Num cost) =>  name -> cost -> [(cost, name)] -> PSQ name (cost, name) -> PSQ name (cost, name)
enqueueList _ _ [] q = q
enqueueList src c (x:xs) q = enqueueList src c xs (enqueue (src, ((fst x)+c), (snd x)) q)

-- Parse resulting map from Dijkstra to return cost and node-sequence of path
parsePath :: (Ord name, Ord cost, Num cost) =>  name -> name -> cost -> Map name (cost, name) -> ([name], cost) -> Maybe ([name], cost)
parsePath from to cost s (trail, c)
  | from == to = Just (from:trail, cost)
  | (M.notMember from s) = Nothing
  | (M.notMember to s) = Nothing
  | otherwise = parsePath from to' cost s' (trail', c)
    where
      s' = M.delete to s
      node = Maybe.fromJust (M.lookup to s) --Safe since we check in guard
      to' = snd node
      trail' = to:trail