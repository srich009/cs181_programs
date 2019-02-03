

(* ----- Question 1 ----- *)

(* 2-3 Tree data type *)

datatype 'a baltree = EmptyTree
                    | Node2 of 'a * 'a baltree * 'a baltree
                    | Node3 of 'a * 'a * 'a baltree * 'a baltree * 'a baltree;


(* ----- Question 2 ----- *)

(* Helper comparison function for find23 *)
(* first argument = value for the search , second argument = value from the tree *)

fun compare23 x y =
    if x < y then ~1
    else if x > y then 1
    else 0 ;

(* 2 3 Tree find function *)

(*  Type:  (’a * ’b -> int) -> ’b baltree -> ’a -> ’b option  *)

fun find23 _ EmptyTree _ =  NONE
|   find23 cmp (Node2(midval,ltree,rtree)) item =
        if (cmp item midval) = 0
            then SOME item
        else if (cmp item midval) = ~1
            then find23 cmp ltree item
        else
            find23 cmp rtree item
|   find23 cmp (Node3(loval,hival,ltree,mtree,rtree)) item =
        if (cmp item loval) = 0 orelse (cmp item hival) = 0
            then SOME item
        else if (cmp item loval) = ~1
            then find23 cmp ltree item
        else if (cmp item loval) = 1 andalso (cmp item hival) = ~1
            then find23 cmp mtree item
        else
            find23 cmp rtree item ;



