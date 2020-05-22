{-# LANGUAGE BlockArguments #-}

module Main where
import System.Environment
import Route
import Graph
import Pathfinder
import qualified Data.Maybe as Maybe

main :: IO ()
main = do
  args <- getArgs
  if length args /= 4
    then error "Number of arguments not matching requirements"
    else return ()
  Right stops <- readStops (args !! 0)
  Right lines <- readLines (args !! 1)
  let from = args !! 2
  let to = args !! 3
  let graph = toGraph stops lines
  let path = case (shortestPath graph from to) of {Nothing -> error "error"; Just path -> path}
  putStr (show (fst path) ++ " with cost " ++ show (snd path))

toGraph :: (Ord weight, Num weight) => [Stop] -> [LineTable] -> Graph String weight
toGraph s l = addLines l (addStops s empty)

addStops :: (Ord weight, Num weight) => [Stop] -> Graph String weight -> Graph String weight
addStops [] g = g
addStops (x:xs) g = addStops xs (addNode (name x) g)

addLines :: (Ord weight, Num weight) => [LineTable] -> Graph String weight -> Graph String weight
addLines [] g = g
addLines (ln:lns) g = addLines lns (go ln g)
  where
  go :: (Ord weight, Num weight) => LineTable -> Graph String weight -> Graph String weight
  go ln g
    | null xs = edge
    | otherwise = go ln' edge
      where
        x:y:xs = stops ln
        ln' = LineTable {lineNumber = lineNumber ln, stops = (y:xs)}
        edge = addEdge (stopName x) ((fromIntegral . time) y) (stopName y) g
