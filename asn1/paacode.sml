(* rename this file <lastname>-<studentID>-pa1.sml before
* submitting
*)


(* ----- dictionary add ----- *)


(*    (’a * ’b) list -> ’a * ’b -> (’a * ’b) list    *)

fun listdictadd (dict:('a * 'b) list) =
    fn (pair:('a * 'b)) => dict @ [ pair ];


(* ----- dictionary find ----- *)


(*    (’’a * ’b) list -> ’’a -> ’b option    *)

fun listdictfind (dict:(''a * 'b) list) =
    fn (key:(''a)) =>
        if (null(dict)) then NONE
        else if (#1(hd(dict))) = key then SOME(#2(hd(dict)))
        else listdictfind(tl(dict)) key;


(* ----- compression ----- *)

(*
    Not sure how to do this
    the encode takes a dictionary, the input list of characters, the index, the substring, the list to return ?
    need the substring because cant look back after an call
    need the return list because the add and find will produce a list of pairs like (char,int) and it needs to be (int,char)
*)

fun lz78e (book,lookup,addto) = fn (charlist:('b list)) =>
    let
        fun encode (dict, nil, _, _, rlist) =  (rlist,dict) (* empty char list, end of input, return the (int * char) list *)
        |   encode (dict, input, index, substr, rlist)  =
            let
                val newstr     = substr @ [hd(input)]    (* current substr plus the next char *)
                val lookedfor  = lookup dict newstr      (* try and find newstr in dictionary *)
                val lookedfor2 = lookup dict substr      (* try and find substr in dictionary *)
            in
                if lookedfor = NONE then      (* if not found, then add to pair to dict and reset index *)
                    if lookedfor2 = NONE then (* case for single letters *)
                        let
                            val newdict  = addto dict (newstr,index)
                            val newrlist = rlist @ [ ( 0, hd(input) ) ]
                        in
                            encode( newdict, tl(input), index+1, [], newrlist ) (* added 1 thing to dictionary, increment index + 1 *)
                        end
                    else                    (* case for more than 1 letter *)
                        let
                            val newdict  = addto dict (newstr,index)
                            val newrlist = rlist @ [ ( valOf(lookedfor2)+1, hd(input) ) ]   (* want the index of the substr in dictionary + 1 *)
                        in
                            encode( newdict, tl(input), index+1, [], newrlist ) (* added 1 thing to dictionary, increment index + 1 *)
                        end
                else                        (* current string exists in dict, don't add, dont increment index, look at next *)
                    encode( dict, tl(input), index, newstr, rlist )
                    (* FIXME should handle the special last case here *)
            end
    in
        encode(book, charlist, 0, [], [])
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


(* ----- function wrappers for compress and decompress ----- *)

(* uncomment below when you've added listdictfind and listdictadd *)
fun lz78ld l = lz78d ([],listdictfind,listdictadd) l;


(* uncomment below when you've added the above, plus lz78e *)
fun lz78le l = lz78e ([],listdictfind,listdictadd) l;


(* ----- test cases ----- *)

(*

lz78le (explode "aababbaba");

lz78le (explode "hihihiyahiyahiya!");

lz78le [1,5,5,5,5,1,5,5,5,5];

val it = [(0, #"a"),(1,#"b"),( 2 ,#"b"),(2,#"a")];
implode (lz78ld it);

lz78le(lz78le(explode "aababbabb"));

*)


