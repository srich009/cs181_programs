(* rename this file <lastname>-<studentID>-pa1.sml before
* submitting
*)

(* add the functions
*
* listdictfind
* listdictadd
* lz78e
*
* as specified in the assignment
*)


(* ----- add ----- *)


(*    (’a * ’b) list -> ’a * ’b -> (’a * ’b) list    *)

fun listdictadd (dict:('a * 'b) list) =
    fn (pair:('a * 'b)) => dict @ [ pair ];


(* ----- find ----- *)


(*    (’’a * ’b) list -> ’’a -> ’b option    *)

fun listdictfind (dict:(''a * 'b) list) =
    fn (key:(''a)) =>
        if (null(dict)) then NONE
        else if (#1(hd(dict))) = key then SOME(#2(hd(dict)))
        else listdictfind(tl(dict)) key;


(* ----- compression ----- *)

(*
    ([],listdictfind,listdictadd) []
    the encode takes a dictionary, the list of characters, the index, the substring, the list to return ?
*)

fun lz78e (book,lookup,addto) = fn (charlist:('b list)) =>
    let
        fun encode (dict, clist, index)  =
            if null(clist) then
                dict
            else
                if (lookup(dict) (hd(clist)) = NONE) then
                        encode( (addto(dict) (hd(clist), index), tl(clist), index+1))
                else
                    encode (dict, tl(clist), index+1)
    in
        encode (book, charlist, 0)
    end;


(* ----- decompression ----- *)


(* LZ78 decompression algorithm *)
fun lz78d (emptybook,inbook,addbook) l =
let

  (* decode takes a codebook, a list of pairs, and the next index *)
  fun decode _ nil _ = nil
    | decode dict ((ind,h)::t) c =
    (* ind is the index of the codeword and h is the value to add at the end *)
    (* c is the index of the next code word *)
    let
      val str = if ind=0 then [] else
               case inbook dict ind of
                    NONE => [] (* this should never happen! *)
                  | SOME s => s
      val cword = h::str
    in
      foldl op::  (* cons together... *)
          (decode (addbook dict (c,cword)) t (c+1))
			(* ... the decoding of
			the rest of the input, with a dictionary
			with an extra entry ... *)
          cword  (* ... with the new code word (as the "seed") *)
    end
in
   decode emptybook l 1
end;


(* ----- test cases ----- *)

(* uncomment below when you've added listdictfind and listdictadd *)
(* fun lz78ld l = lz78d ([],listdictfind,listdictadd) l; *)


(* uncomment below when you've added the above, plus lz78e *)
(* fun lz78le l = lz78e ([],listdictfind,listdictadd) l; *)
