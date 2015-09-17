"Given a function with parameter types `P1`, `P2`, ..., `Pn`, 
 return a function with a single parameter of tuple type 
 `[P1, P2, ..., Pn]`.
 
 That is, if `fun` has type `W(X,Y,Z)` then `unflatten(fun)` 
 has type `W([X,Y,Z])`.
 
 In the case of a variadic function, the returned function 
 has a single parameter whose type is a sequence type or 
 unterminated tuple type:
 
 - if the given function has a single variadic parameter of 
   type `S*`, the returned function accepts `[S*]`,
 - if the given function has a single variadic parameter of 
   type `S+`, the returned function accepts `[S+]`,
 - if the given function has multiple parameters with types
   `P1`, `P2`, ..., `Pn`, `S*`, the returned function 
   accepts `[P1, P2, ..., Pn, S*]`, or
 - if the given function has multiple parameters with types
   `P1`, `P2`, ..., `Pn`, `S+`, the returned function 
   accepts `[P1, P2, ..., Pn, S+]`."
see(`function flatten`)
tagged("Functions")
shared native Return unflatten<Return,Args>
        (Return(*Args) flatFunction)(Args args)
        given Args satisfies Anything[];
