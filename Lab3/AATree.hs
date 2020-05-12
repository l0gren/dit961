{-# OPTIONS -Wall #-}

--------------------------------------------------------------------------------

module AATree (
  AATree,        -- type of AA search trees
  emptyTree,     -- AATree a
  get,           -- Ord a => a -> AATree a -> Maybe a
  insert,        -- Ord a => a -> AATree a -> AATree a
  inorder,       -- AATree a -> [a]
  remove,        -- Ord a => a -> AATree a -> AATree a
  size,          -- AATree a -> Int
  height,        -- AATree a -> Int
  checkTree      -- Ord a => AATree a -> Bool
 ) where

--------------------------------------------------------------------------------


-- AA search trees
data AATree a 
  = Empty
  | Node Int a (AATree a) (AATree a)
  deriving (Eq, Show, Read)

emptyTree :: AATree a
emptyTree = Empty

get :: Ord a => a -> AATree a -> Maybe a
get _ Empty = Nothing
get x (Node _ y l r) = case compare x y of
  LT -> get x l
  EQ -> Just y
  GT -> get x r

split :: AATree a -> AATree a
split Empty = Empty
split (Node lvl y l (Node lvlR yR lR (Node lvlRR yRR lRR rRR))) | lvl == lvlR && lvlR == lvlRR
  = (Node (lvl+1) yR (Node lvl y l lR) (Node lvl yRR lRR rRR))
split (Node lvl y l r) = (Node lvl y l r)

skew  :: AATree a -> AATree a
skew Empty = Empty
skew (Node lvl y (Node lvlL yL lL rL) r) | (lvl == lvlL) = (Node lvl yL lL (Node lvlL y rL r))
skew (Node lvl y l r) = (Node lvl y l r) 

insert :: Ord a => a -> AATree a -> AATree a
insert x Empty = Node 1 x Empty Empty
insert x (Node lvl y Empty Empty) = case compare x y of 
  LT -> skew (Node lvl y (Node lvl x Empty Empty) Empty)
  EQ -> (Node lvl y Empty Empty)
  GT -> (Node lvl y Empty (Node lvl x Empty Empty))

insert x (Node lvl y l (Node lvlR yR lR rR)) | lvl == lvlR = case compare x y of
  --LT not working properly, not skewing split from left
  LT -> split (skew (Node lvl y (insert x l) (Node lvl yR lR rR)))
  EQ -> (Node lvl y l (Node lvl yR lR rR))
  GT -> split ((Node lvl y l (insert x (Node lvl yR lR rR))))

insert x (Node lvl y (Node lvlL yL lL (Node lvlLR yLR lLR rLR)) r) | lvlL == lvlLR
  = case compare x y of
  LT -> skew (Node lvl y (insert x (Node lvlL yL lL (Node lvlLR yLR lLR rLR))) r)
  EQ -> (Node lvl y (Node lvlL yL lL (Node lvlLR yLR lLR rLR)) r)
  GT -> (Node lvl y (Node lvlL yL lL (Node lvlLR yLR lLR rLR)) (insert x r))

insert x (Node lvl y l r) = case compare x y of
  LT -> (Node lvl y (insert x l) r)
  EQ -> (Node lvl y l r)
  GT -> (Node lvl y l (insert x r))

-- Complexity: O(n)
inorder :: AATree a -> [a]
inorder Empty = []
inorder (Node _ y Empty Empty) = [y]
inorder (Node _ y l r) = inorder l ++ [y] ++ inorder r

-- Complexity: O(n)
size :: AATree a -> Int
size Empty = 0
size (Node _ _ Empty Empty) = 1
size (Node _ _ l r) = ((size l) + 1 + (size r))

-- Complexity: O(log n)
height :: AATree a -> Int
height Empty = 0
height (Node _ _ l r) = 1 + max (height l) (height r)

--------------------------------------------------------------------------------
-- Optional function

remove :: Ord a => a -> AATree a -> AATree a
remove = error "remove not implemented"

--------------------------------------------------------------------------------
-- Check that an AA tree is ordered and obeys the AA invariants

checkTree :: Ord a => AATree a -> Bool
checkTree root =
  isSorted (inorder root) &&
  all checkLevels (nodes root)
  where
    nodes x
      | isEmpty x = []
      | otherwise = x:nodes (leftSub x) ++ nodes (rightSub x)

-- True if the given list is ordered
isSorted :: Ord a => [a] -> Bool
isSorted list = case list of 
  []     -> True
  [_]    -> True
  x:y:ys -> (x<=y) && isSorted (y:ys)

checkLevels :: AATree a -> Bool
checkLevels Empty = True
checkLevels node =
  leftChildOK node &&
  rightChildOK node &&
  rightGrandchildOK node

leftChildOK :: AATree a -> Bool
leftChildOK Empty = True
leftChildOK (Node lvl _ (Node lvlL _ _ _) _) = (lvl == (lvlL+1))
leftChildOK (Node _ _ _ _) = True

rightChildOK :: AATree a -> Bool
rightChildOK Empty = True
rightChildOK (Node lvl _ _ (Node lvlR _ _ _)) = (lvl == (lvlR+1) || lvl == lvlR)
rightChildOK (Node _ _ _ _) = True

rightGrandchildOK :: AATree a -> Bool
rightGrandchildOK Empty = True
rightGrandchildOK (Node lvl _ _ (Node lvlR _ _ (Node lvlRR _ _ _))) | (lvl == lvlR)
  = (lvl == lvlRR+1)  
rightGrandchildOK (Node _ _ _ _) = True

isEmpty :: AATree a -> Bool
isEmpty Empty = True
isEmpty (Node _ _ _ _) = False

leftSub :: AATree a -> AATree a
leftSub Empty = Empty
leftSub (Node _ _ l _) = l 

rightSub :: AATree a -> AATree a
rightSub Empty = Empty
rightSub (Node _ _ _ r) = r

--------------------------------------------------------------------------------

