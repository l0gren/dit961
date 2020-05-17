module Dijkstra
  (shortestPath) where

import Graph
import Data.PSQueue (PSQ)
import qualified Data.PSQueue as P
import Data.Map (Map)
import qualified Data.Map as M

--Change to generic somehow?
type Name = [Char]
type Cost = Integer

--Test Dijkstra's
--shortestPath :: Graph Name Cost -> Name -> Name -> Map Name (Cost, Name)
--shortestPath g from to = dijkstra g (P.singleton from (0, from)) M.empty

--enqueue (from, 0, from) P.empty
--P.singleton from (0, from)

shortestPath :: Graph Name Cost -> Name -> Name -> Maybe ([Name], Cost)
shortestPath g from to = parsePath from to cost paths ([], 0)
  where
    paths = dijkstra g (enqueue (from, 0, from) P.empty) M.empty
    cost = case (M.lookup to paths) of
      Nothing -> error "No path found"
      Just cost -> fst cost

dijkstra :: Graph Name Cost -> PSQ Name (Cost, Name) -> Map Name (Cost, Name) -> Map Name (Cost, Name)
dijkstra g q s
  | P.null q = s
  | otherwise = dijkstra g q' s'
    where
      min = case (P.findMin q) of --Dangerous error handling?
        Nothing -> error "Could not find minimum in Priority Queue" 
        Just min -> min --How to write properly?
      src = (P.key min)
      trail = (P.prio min)
      cost = fst trail
      s'
        | M.member src s = s
        | otherwise = M.insert src trail s
      adj = case (Graph.neighbors g src) of --Dangerous error handling?
        Nothing -> error "Could not get neighbors of node"
        Just adj -> adj --How to write properly?
      q' = listToQueue src cost adj (P.deleteMin q)

-- Insert target of an edge into queue {Target: (weight, Source)}
enqueue :: (Name, Cost, Name) -> PSQ Name (Cost, Name) -> PSQ Name (Cost, Name)
enqueue (src, cost, target) q = P.insert target (cost, src) q --Need error/collision handling?

listToQueue :: Name -> Cost -> [(Cost, Name)] -> PSQ Name (Cost, Name) -> PSQ Name (Cost, Name)
listToQueue _ _ [] q = q
listToQueue src c (x:xs) q = listToQueue src c xs (enqueue (src, ((fst x)+c), (snd x)) q)

parsePath :: Name -> Name -> Cost -> Map Name (Cost, Name) -> ([Name], Cost) -> Maybe ([Name], Cost)
parsePath from to cost s (trail, c)
  | from == to = Just (from:trail, cost)
  | (M.notMember from s) = Nothing
  | otherwise = parsePath from to' cost s' (trail', c)
    where 
      s' = M.delete to s
      to' = case (M.lookup to s) of
        Nothing -> error "Could not find node in the map"
        Just to' -> snd to' --How to write properly?
      trail' = to:trail
      cost' = case (M.lookup to s) of
        Nothing -> error "Could not find node in the map"
        Just cost' -> fst cost' --How to write properly?

