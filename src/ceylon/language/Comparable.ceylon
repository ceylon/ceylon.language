"""The general contract for values whose magnitude can be 
   compared. `Comparable` imposes a total ordering upon
   instances of any type that satisfies the interface.
   
   If a type `T` satisfies `Comparable<T>`, then instances 
   of `T` may be compared using the comparison operators
   `<`, `>`, `<=`, `>=`.
   
       assert (x>=0.0);
   
   A _ternary comparison_ is useful for asserting lower and 
   upper bounds.
   
       assert (0.0<=x<1.0);
   
   Finally, the _compare_ operator `<=>` may be used to 
   produce an instance of [[Comparison]].
   
       switch (x<=>y)
       case (equal) {
           print("same same");
       }
       case (smaller) {
           print("x smaller");
       }
       case (larger) {
           print("y smaller");
       }
   
   The total order of a type must be consistent with the 
   definition of equality for the type. That is, there are 
   three mutually exclusive possibilities:
   
   - `x<y`,
   - `x>y`, or
   - `x==y`
   
   (These possibilities are expressed by the enumerated
   instances [[smaller]], [[larger]], and [[equal]] of
   `Comparison`.)
   
   The order imposed by `Comparable` is sometimes called the
   _natural order_ of a type, to reflect the fact that any
   function of type `Comparison(T,T)` might determine a 
   different order. Thus, some order-related operations come 
   in two flavors: a flavor that depends upon the natural 
   order, and a flavor which accepts an arbitrary comparator 
   function. Examples are:
   
   - [[sort]] vs [[Iterable.sort]] and
   - [[max]] vs [[Iterable.max]]."""
see (`class Comparison`,
     `function sort`, 
     `function max`, `function min`,
     `function largest`, `function smallest`)
by ("Gavin")
tagged("Comparisons")
shared interface Comparable<in Other> of Other 
        given Other satisfies Comparable<Other> {
    
    "Compares this value with the given value. 
     Implementations must respect the constraints that: 
     
     - `x==y` if and only if `x<=>y == equal` 
        (consistency with `equals()`), 
     - if `x>y` then `y<x` (symmetry), and 
     - if `x>y` and `y>z` then `x>z` (transitivity)."
    see (`function equals`)
    shared formal Comparison compare(Other other);
    
    "Determines if this value is strictly larger than the 
     given value."
    shared default Boolean largerThan(Other other)
            => compare(other)===larger; 
    
    "Determines if this value is strictly smaller than the 
     given value."
    shared default Boolean smallerThan(Other other)
            => compare(other)===smaller; 
    
    "Determines if this value is larger than or equal to the 
     given value."
    shared default Boolean notSmallerThan(Other other)
            => !compare(other)===smaller; 
    
    "Determines if this value is smaller than or equal to 
     the given value."
    shared default Boolean notLargerThan(Other other)
            => !compare(other)===larger; 
    
}