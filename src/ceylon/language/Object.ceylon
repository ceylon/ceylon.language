"""The abstract supertype of all types representing definite 
   values. Any two values which are assignable to `Object` 
   may be compared for value equality using the `==` and `!=` 
   operators, even if the values are of different concrete 
   type:
   
       true == false
       1 == "hello world"
       "hello"+" "+"world" == "hello world"
       Singleton("hello world") == ["hello world"]
   
   However, since [[Null]] is not a subtype of `Object`, the
   value [[null]] cannot be compared to any other value 
   using the `==` operator. Thus, value equality is not 
   defined for optional types. This neatly bypasses the 
   problem of deciding the value of the expression 
   `null==null`, which is simply illegal.
   
   A concrete subclass of `Object` must refine [[equals]] 
   and [[hash]] (or inherit concrete refinements), providing 
   a concrete definition of value equality for the class.
   
   In extreme cases it is acceptable for two values to be
   equal even when they are not instances of the same class.
   For example, the [[Integer]] value `1` and the [[Float]]
   value `1.0` are considered equal. Except in these extreme
   cases, instances of different classes are considered
   unequal."""
see (`class Basic`, `class Null`)
by ("Gavin")
shared abstract class Object() 
        extends Anything() {
    
    "Determine if two values are equal. Implementations
     should respect the constraints that:
     
     - if `x===y` then `x==y` (reflexivity), 
     - if `x==y` then `y==x` (symmetry), 
     - if `x==y` and `y==z` then `x==z` (transitivity).
     
     Furthermore it is recommended that implementations
     ensure that if `x==y` then `x` and `y` have the same 
     concrete class.
     
     A class which explicitly refines `equals()` is said to 
     support _value equality_, and the equality operator 
     `==` is considered much more meaningful for such 
     classes than for a class which simply inherits the
     default implementation of _identity equality_ from
     [[Identifiable]]."
    shared formal Boolean equals(Object that);
    
    "The hash value of the value, which allows the value to 
     be an element of a hash-based set or key of a
     hash-based map. Implementations must respect the
     constraint that:
     
     - if `x==y` then `x.hash==y.hash`.
     
     Therefore, a class which refines [[equals]] must also
     refine `hash`.
     
     Because the [[Integer]] type is platform-dependent 
     a compiler for a given platform is permitted to
     further manipulate the calculated hash for an object,
     and the resulting hash may differ between platforms."
    shared formal Integer hash;
    
    "A developer-friendly string representing the instance. 
     Concatenates the name of the concrete class of the 
     instance with the `hash` of the instance. Subclasses 
     are encouraged to refine this implementation to produce 
     a more meaningful representation."
    shared default String string
            => className(this) + "@" + 
               formatInteger(hash, #10);
    
}