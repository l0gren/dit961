import AATree

main :: IO ()
main = do
  contents <- getContents
  let listOfWords = words contents
  let wordTree = foldl (flip insert) emptyTree listOfWords

  let s = (show (size wordTree))
  let s' =  fromIntegral (size wordTree)
  putStrLn ("Size: " ++ s)

  let h = (show (height wordTree))
  let h' =  (height wordTree)
  putStrLn ("Height: " ++ h)

  let optimal = (ceiling $ logBase 2 (fromIntegral ( s' + 1))) - 1
  let opt = (show optimal)

  putStrLn ("Optimal height: " ++ opt)

  let quota =  show (fromIntegral h' / fromIntegral optimal)
  putStrLn ("Height / Optimal height: " ++ quota)

  let oktree = checkTree wordTree
  let treeisOK = if oktree then "True" else "False"
  putStrLn ("checkTree: " ++ treeisOK)

  let first20 = unwords $ take 20 $ inorder wordTree
  putStrLn ("First 20 words: " ++ first20)