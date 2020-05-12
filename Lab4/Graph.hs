{-# OPTIONS -Wall #-}

--------------------------------------------------------------------------------

module Graph (
  Graph        -- type of Graph
 ) where

--------------------------------------------------------------------------------
--import Data.Map (Map)
import qualified Data.Map as Map

type Vertex a = a
type Edge = Vertex Int
data Graph a = Map (Vertex a) [Edge]
  deriving Show

insert :: Graph a -> Graph a -> Graph a
insert x graph = Map.insert x graph