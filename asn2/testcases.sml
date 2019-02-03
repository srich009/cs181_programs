(* Testcases *)

use "tree23.sml";

(* Tree Data *)

val garbagetree =
Node2 ( 10,
        Node2 ( 5,
                Node2 ( 3,
                        EmptyTree,
                        EmptyTree
                      ),
                Node2 ( 7,
                        EmptyTree,
                        EmptyTree
                      )
              ),
        Node3 ( 20,
                30,
                Node2 ( 15,
                        EmptyTree,
                        EmptyTree
                      ),
                Node2 ( 25,
                        EmptyTree,
                        EmptyTree
                      ),
                Node2 ( 35,
                        EmptyTree,
                        EmptyTree
                      )
              )
      );


(* Test Functions *)

(* find23 *)
find23 compare23 garbagetree 3;
find23 compare23 garbagetree 5;
find23 compare23 garbagetree 7;
find23 compare23 garbagetree 10;
find23 compare23 garbagetree 15;
find23 compare23 garbagetree 20;
find23 compare23 garbagetree 25;
find23 compare23 garbagetree 30;
find23 compare23 garbagetree 35;

find23 compare23 garbagetree 0;
find23 compare23 garbagetree 100;


