module Graph
  ( Graph
  , addEdge
  , addNode
  , empty
  , member
  , neighbors
  , notMember
  , nodes
  ) where

import qualified Data.Map as M

data Graph id weight = Graph
  { table :: (M.Map id [(weight, id)])
  } deriving Show

addEdge :: Ord id => id -> weight -> id -> Graph id weight -> Graph id weight
addEdge start _ stop graph
  | notMember start graph = error "beginning of edge not in graph"
  | notMember stop graph = error "end of edge not in graph"
addEdge start w stop (Graph table) = Graph table'
  where table' = M.insertWith (++) key val map
        key = start
        val = [(w, stop)]
        map = table

addNode :: Ord id => id -> Graph id weight -> Graph id weight
addNode node (Graph table)
  | M.member node table = Graph table
  | otherwise = Graph (M.insert node [] table)

empty :: Graph a b
empty = Graph M.empty

member :: Ord id => id -> Graph id weight -> Bool
member node (Graph table) = case M.lookup node table of
    Nothing -> False
    Just _  -> True

neighbors :: Ord id => Graph id weight -> id -> Maybe [(weight, id)]
neighbors graph node = M.lookup node (table graph)

notMember :: Ord id => id -> Graph id weight -> Bool
notMember node graph = not $ member node graph

nodes :: Graph id weight -> [id]
nodes = M.keys . table