"A possibly-empty, immutable sequence of values. The type 
 `Sequential<Element>` may be abbreviated `[Element*]` or 
 `Element[]`. 
 
 `Sequential` has two enumerated subtypes:
 
 - [[Empty]], abbreviated `[]`, represents an empty 
   sequence, and
 - [[Sequence]]`<Element>`, abbreviated `[Element+]` 
   represents a non-empty sequence, and has the very 
   important subclass [[Tuple]]."
see (`class Tuple`)
tagged("Sequences")
shared interface Sequential<out Element=Anything>
        of []|[Element+]
        satisfies List<Element> & 
                  Ranged<Integer,Element,Element[]> {
    
    "The strictly-positive length of this sequence, that is, 
     the number of elements in this sequence."
    shared actual formal Integer size;
    
    "A sequence containing all indexes of this sequence,
     that is, every index in the range `0:sequence.size`."
    shared actual default Integer[] keys => 0:size;
    
    "This sequence."
    shared actual default Element[] sequence() => this;
    
    "The rest of the sequence, without the first element."
    shared actual formal Element[] rest;
    
    "A sequence containing the elements of this sequence in
     reverse order to the order in which they occur in this
     sequence, or the [[empty sequence|empty]] if this
     sequence is the empty sequence."
    shared actual formal Element[] reversed;
    
    "Produces a sequence formed by repeating the elements of
     this sequence the given [[number of times|times]], or
     the [[empty sequence|empty]] if `times<=0` or if this
     sequence is the empty sequence."
    shared actual formal Element[] repeat(Integer times);
    
    "Select the first elements of this sequence, returning 
     a sequence no longer than the given length. If this 
     sequence is shorter than the given length, return this 
     sequence. Otherwise return a sequence of the given 
     length."
    shared actual default Element[] initial(Integer length)
            => this[...length-1];
    
    "Select the last elements of the sequence, returning a 
     sequence no longer than the given length. If this 
     sequence is shorter than the given length, return this 
     sequence. Otherwise return a sequence of the given 
     length."
    shared actual default Element[] terminal(Integer length)
            => this[size-length...]; 
    
    "This sequence."
    shared actual default Element[] clone() => this;
    
    "Trim the elements satisfying the given predicate
     function from the start and end of this sequence, 
     returning a sequence no longer than this sequence."
    shared actual default 
    Element[] trim(
        "The predicate function that determines if an 
         element at the start or end of this sequence should
         be trimmed"
        Boolean trimming(Element&Object element))
            => super.trim(trimming).sequence(); //TODO: inefficient?
    
    "Trim the elements satisfying the given predicate
     function from the start of this sequence, returning 
     a sequence no longer than this sequence."
    shared actual default 
    Element[] trimLeading(
        "The predicate function that determines if an 
         element at the start of this sequence should be 
         trimmed"
        Boolean trimming(Element&Object element))
            => super.trimLeading(trimming).sequence(); //TODO: inefficient?
    
    "Trim the elements satisfying the given predicate
     function from the end of this sequence, returning a 
     sequence no longer than this sequence."
    shared actual default 
    Element[] trimTrailing(
        "The predicate function that determines if an 
         element at the end of this sequence should be 
         trimmed"
        Boolean trimming(Element&Object element))
            => super.trimTrailing(trimming).sequence(); //TODO: inefficient?
    
    "Return two sequences, the first containing the elements
     that occur before the given [[index]], the second with
     the elements that occur after the given `index`. If the
     given `index` is outside the range of indices of this
     list, one of the returned sequences will be empty."
    shared actual default 
    [Element[],Element[]] slice(Integer index)
            => [this[...index-1], this[index...]];
    
    "Returns a new sequence that starts with the specified
     [[element]], followed by the elements of this sequence,
     in the order they occur in this sequence."
    see (`function prepend`,
         `function withTrailing`,
         `function follow`)
    shared formal 
    [Other,Element*] withLeading<Other>(
        "The first element of the resulting sequence."
        Other element);
    
    "Returns a new sequence that starts with the elements of 
     this sequence, in the order they occur in this sequence, 
     and ends with the specified [[element]]."
    see (`function append`,
         `function withLeading`)
    shared formal 
    [Element|Other+] withTrailing<Other>(
        "The last element of the resulting sequence."
        Other element);
    
    "Return a sequence containing the elements of this 
     sequence, in the order in which they occur in this 
     sequence, followed by the given [[elements]], in the 
     order in which they occur in the given sequence."
    see (`function prepend`,
         `function withTrailing`,
         `function concatenate`,
         `function chain`)
    shared formal 
    [Element|Other*] append<Other>(Other[] elements);
    
    "Return a sequence containing the given [[elements]], in 
     the order in which they occur in the given sequence,
     followed by the elements of this sequence, in the order 
     in which they occur in this sequence."
    see (`function append`,
         `function withLeading`,
         `function concatenate`)
    shared formal 
    [Element|Other*] prepend<Other>(Other[] elements);
    
    "A string of form `\"[ x, y, z ]\"` where `x`, `y`, and 
     `z` are the `string` representations of the elements of 
     this collection, as produced by the iterator of the 
     collection, or the string `\"{}\"` if this collection 
     is empty. If the collection iterator produces the value 
     `null`, the string representation contains the string 
     `\"null\"`."
    shared actual default 
    String string 
            => empty then "[]" else "[``commaList(this)``]";
    
}
