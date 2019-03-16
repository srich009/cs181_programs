% randelem(+list,?elem)
randelem(L,X) :- length(L,LL), I is random(LL), nth0(I,L,X).

% selandrem(+index,+list,-list,-elem)
selandrem0(0,[X|R],R,X) :- !.
selandrem0(I,[A|R],[A|Rout],X) :- NI is I-1, selandrem0(NI,R,Rout,X).

% just a helper function to boggleboard, below
% bbhelp(-board,+nleft,+cubes)
bbhelp([],0,[]) :- !.
bbhelp([X|R],L,CS) :- I is random(L), selandrem0(I,CS,NCS,C), randelem(C,X), !,
				NL is L-1, bbhelp(R,NL,NCS).

% returns a randomly chosen boggle board.
% No choice points -- will not backtrack
% boggleboard(-board)
boggleboard(B) :- bbhelp(B,16,
		[['A','A','E','E','G','N'],['A','B','B','J','O','O'],
		 ['A','C','H','O','P','S'],['A','F','F','K','P','S'],
		 ['A','O','O','T','T','W'],['C','I','M','O','T','U'],
		 ['D','E','I','L','R','X'],['D','E','L','R','V','Y'],
		 ['D','I','S','T','T','Y'],['E','E','G','H','N','W'],
		 ['E','E','I','N','S','U'],['E','H','R','T','V','W'],
		 ['E','I','O','S','S','T'],['E','L','R','T','T','Y'],
		 ['H','I','M','N','Q','U'],['H','L','N','N','R','Z']]).

% boggleletter(+board,?xindex,?yindex,?character)
boggleletter(B,X,Y,C) :- nth0(N,B,C), XR is N/4, X is floor(XR), Y is mod(N,4).

% you should not use this... you will need to traverse the dictionary
% yourself.  However, this checks whether the first argument (a list of
% characters) is in the dictionary (the second argument)
% order it adds the word to the dictionary
% indict(?word,?dictionary)
indict([X],node(X,true,_,_)).
indict([X|RX],node(X,_,Y,_)) :- indict(RX,Y).
indict([X|RX],node(_,_,_,Y)) :- indict([X|RX],Y).

% a helper function to loaddict, below
loaddicthelp(S,_) :- at_end_of_stream(S), !.
loaddicthelp(S,D) :- read_line_to_codes(S,C), atom_codes(Str,C),
		atom_chars(Str,W), indict(W,D), !, loaddicthelp(S,D).

% a helper function to loaddict, only succeeds if the dictionary is grounded.
restrictdict(null) :- !.
restrictdict(node(_,false,L,R)) :- !, restrictdict(L), !, restrictdict(R).
restrictdict(node(_,true,L,R)) :- !, restrictdict(L), !, restrictdict(R).

% loads a dictionary
% loaddict(+filename,-dictionary)
loaddict(Filename,Dict) :-
	open(Filename,read,S), !,
	loaddicthelp(S,Dict), !,
	restrictdict(Dict),
	close(S).

% draws a boggle board
% drawboard(+board).
drawboard(B) :- drawboard(B,16).
drawboard(_,0) :- !.
drawboard([X|Y],I) :- 1 =:= mod(I,4), !, writeln(X),
		II is I-1, drawboard(Y,II).
drawboard([X|Y],I) :- write(X), II is I-1, drawboard(Y,II).

% word is a list of characters
% boggleword(+board,?word)
boggleword(B,X) :- loaddict(bogwords,D), isboggleword(B,D,X).

% below is only needed for Q3
%---------------------

% converttostr(?listlistchar,?liststr)
converttostr([],[]).
converttostr([X|XR],[Y|YR]) :- atom_chars(Y,X), converttostr(XR,YR).

% allbogglewords(+board,-words)
allbogglewords(B,X) :- loaddict(bogwords,D),
	findall(W,isboggleword(B,D,W),XL),
	removedup(XL,XL2), converttostr(XL2,X).

% ----- MY CODE ----- %

% question 1
% isboggleword(+board,+dictionary,?word)

isboggleword(B,D,W) :-
	isboggleword2(B,D,W,[]).

isboggleword2(B, node(X,true,_,_), [X], LST) :-
	boggleletter(B, _, _, X).

isboggleword2(B, node(H,_,G,_), [H,H2|T], LST) :-
	boggleletter(B,X,Y,H),	    % set x,y
	visited(L,X,Y),				% check visit
	append([(X,Y)],LST,L2),			% append point to list
	boghelper(B,X,Y,H2),	    % adjacent letter
	isboggleword2(B,G,[H2|T],L2).	% recurse on subtree down change D

isboggleword2(B, node(_,_,_,Y), X, LST) :-
	isboggleword2(B,Y,X,LST).	% recurse on subtree right change D

% check in 8 directions
% -board, ?x,y,c

boghelper(B,X,Y,C) :- XX is X+1, boggleletter(B,XX,Y,C).	% right
boghelper(B,X,Y,C) :- XX is X-1, boggleletter(B,XX,Y,C).	% left
boghelper(B,X,Y,C) :- YY is Y+1, boggleletter(B,X,YY,C).	% up
boghelper(B,X,Y,C) :- YY is Y-1, boggleletter(B,X,YY,C).	% down

boghelper(B,X,Y,C) :- XX is X+1, YY is Y+1, boggleletter(B,XX,YY,C).
boghelper(B,X,Y,C) :- XX is X-1, YY is Y+1, boggleletter(B,XX,YY,C).
boghelper(B,X,Y,C) :- XX is X+1, YY is Y-1, boggleletter(B,XX,YY,C).
boghelper(B,X,Y,C) :- XX is X-1, YY is Y-1, boggleletter(B,XX,YY,C).


% check for word in dict
% dictionaries are nodes
% dict is node(char,bool,subtree1_down,subtree2_right)

% node(X,true,_,_), [X]
% node(X,_,Y,_),    [X|RX]
% node(_,_,_,Y),    [X|RX]

% success on reach empty lsit because then never saw befpre

visited([],XN,YN).	% base never visited

visited([(P1,P2)|T],XN,YN) :-	% recurse because didnt match
	visited(T,XN,YN).


% question 2
% should not do the go back on the same already seem chars.

% question 3
% removedup(+inlist,-outlist)

removedup(IL,OL) :-
	rmhelp(IL,[],OL).

rmhelp([],IL,IL).

rmhelp([H|T],IL,OL) :-
	member(H,IL),
	rmhelp(T,IL,OL).

rmhelp([H|T],IL,OL) :-
	rmhelp(T,[H|IL],OL).

/*

SREU
CGHF
SNLT
ROLS

atom_chars('SREUCGHFSNLTROLS',B), drawboard(B), boggleword(B,X).

atom_chars('SREUCGHFSNLTROLS',B), drawboard(B), allbogglewords(B,X).

*/