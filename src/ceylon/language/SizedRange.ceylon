"A range of adjacent [[Enumerable]] values generated by a 
 [[first]] element, and a strictly positive [[size]]. The
 range includes all values whose offset from `first` is
 non-negative and less than the `size`.
 
 More precisely, if `x` and `first` are of `Enumerable` 
 type `X`, and `size` is an integer, then `x in first:size` 
 if and only if `0 <= x.offset(first) < size`.
 
 A sized range is always nonempty, containing at least one 
 value. Thus, it is a [[Sequence]].
 
 The _segment_ operator `:` is an abbreviation for
 `SizedRange` instantiation.
 
     for (i in start:size) { ... }
     for (char in '0':10) { ... }
 
 The segment operator accepts the first index and size of 
 the range:
 
     0:5     // [0, 1, 2, 3, 4]
 
 If the size is nonpositive, the range is empty:
 
     0:0     // []
     5:0     // []
     0:-5    // []"
see (`class Span`, 
     `interface Enumerable`)
shared sealed final class SizedRange<Element>(first, size) 
        extends Object() 
        satisfies [Element+]
        given Element satisfies Enumerable<Element> {

    "The start of the range."
    shared actual Element first;
    
    "The size of the range."
    shared actual Integer size;
    
    "Can't be used for empty segments"
    assert (size > 0);
    
    shared actual String string 
            => first.string + ":" + size.string;
    
    shared actual Element last => first.neighbour(size-1);
    
    "Determines if this sized range has more elements than 
     the given [[length]]."
    shared actual Boolean longerThan(Integer length)
            => size > length;
    
    "Determines if this sized range has fewer elements than 
     the given [[length]]."
    shared actual Boolean shorterThan(Integer length)
            => size < length;
    
    "The index of the end of the sized range."
    shared actual Integer lastIndex => size-1; 
    
    "The rest of the range, without its first element."
    shared actual Element[] rest 
            => size==1 then [] 
                       else SizedRange(first.successor,size-1);
    
    "The element of the sized range that occurs [[index]] values
     after the start of the sized range. Note that, depending on
     [[Element]]'s [[neighbour|Enumerable.neighbour]] implementation,
     this operation may be inefficient for large ranges."
    shared actual Element? getFromFirst(Integer index) {
        if (index<0 || index >= size) {
            return null;
        }
        return first.neighbour(index);
    }
    
    "An iterator for the elements of the sized range."
    shared actual Iterator<Element> iterator() {
        object iterator
                satisfies Iterator<Element> {
            variable value count = 0;
            variable value current = first;
            shared actual Element|Finished next() {
                if (++count>size) {
                    return finished;
                }
                else {
                    return current++;
                } 
            }
            string => "(``outer.string``).iterator()";
        }
        return iterator;
    }
    
    shared actual {Element+} by(Integer step) {
        "step size must be greater than zero"
        assert (step > 0);
        return step == 1 then this else By(step);
    }
    
    class By(Integer step)
            satisfies {Element+} {
        
        size => 1 + (outer.size - 1) / step;
        
        first => outer.first;
        
        string => "(``outer.string`` by ``step``)";
        
        shared actual Iterator<Element> iterator() {
            object iterator
                    satisfies Iterator<Element> {
                variable value count = 0;
                variable value current = first;
                shared actual Element|Finished next() {
                    if (++count>size) {
                        return finished;
                    }
                    else {
                        value result = current;
                        current = current.neighbour(step);
                        return result;
                    } 
                }
                string => "``outer.string``.iterator()";
            }
            return iterator;
        }
    }
    
    "Returns a sized range of the same size and type as this
     range, with its starting point shifted by the given 
     number of elements, where:
     
     - a negative [[shift]] measures 
       [[decrements|Ordinal.predecessor]], and 
     - a positive `shift` measures 
       [[increments|Ordinal.successor]]."
    shared SizedRange<Element> shifted(Integer shift) {
        if (shift==0) {
            return this;
        }
        else {
            return SizedRange(first.neighbour(shift),size);
        }
    }
    
    "Determines if this range includes the given object."
    shared actual Boolean contains(Object element) {
        if (is Element element) {
            return containsElement(element);
        }
        else {
            return false;
        }
    }
    
    "Determines if this range includes the given value."
    shared actual Boolean occurs(Anything element) {
        if (is Element element) {
            return containsElement(element);
        }
        else {
            return false;
        }
    }
    
    "Determines if this range includes the given value."
    shared Boolean containsElement(Element x)
            => 0 <= x.offset(first) < size;
    
    shared actual Boolean includes(List<Anything> sublist) {
        if (sublist.empty) {
            return true;
        }
        else if (is SizedRange<Element> sublist) {
            return includesSizedRange(sublist);
        }
        else {
            return super.includes(sublist);
        }
    }
    
    "Determines if this range includes the given sized 
     range."
    shared Boolean includesSizedRange(SizedRange<Element> sublist) {
        value offset = sublist.first.offset(first);
        return offset >= 0 && offset + sublist.size <= size;
    }
    
    "Efficiently determines if two sized ranges are the same
     by comparing their sizes and start points."
    shared actual Boolean equals(Object that) {
        if (is SizedRange<Object> that) {
            //optimize for another SizedRange
            return that.size==size && that.first==first;
        }
        else {
            //it might be another sort of List
            return super.equals(that);
        }
    }
    
    "Returns the range itself, since sized ranges are 
     immutable."
    shared actual SizedRange<Element> clone() => this;
    
    "Returns the range itself, since a sized range cannot 
     contain nulls."
    shared actual SizedRange<Element> coalesced => this;
    
    "Returns this range."
    shared actual SizedRange<Element> sequence() => this;
    
    shared actual Element[] segment(Integer from, Integer length) {
        if (length<=0) {
             return []; 
        }
        else {
            value len = from+length < size then length 
                                           else size-from;
            return SizedRange(first.neighbour(from),len);
        }
    }
    
    shared actual Element[] span(Integer from, Integer to) {
        if (from<=to) {
            if (to<0 || from>=size) {
                return [];
            }
            else {
                value len = to < size then to-from+1
                                      else size-from;
                return SizedRange(first.neighbour(from),len);
            }
        }
        else {
            if (from<0 || to>=size) {
                return [];
            }
            else {
                value len = from < size then from-to+1 
                                        else size-to;
                return SizedRange(first.neighbour(to),len).reverse();
            }
        }
    }
    
    shared actual Element[] spanFrom(Integer from) {
        if (from <= 0) {
            return this;
        }
        else if (from < size) {
            return SizedRange(first.neighbour(from),size-from);
        }
        else {
            return [];
        }
    }
    
    shared actual Element[] spanTo(Integer to) {
        if (to<0) {
            return [];
        }
        else if (to < size-1) {
            return SizedRange(first,to);
        }
        else {
            return this;
        }
    }
}

"Create a new [[SizedRange]] if the given [[size]] is 
 strictly positive, or return the 
 [[empty sequence|empty]] if `size <= 0`."
shared SizedRange<Element>|[] sizedRange<Element>
            (Element first, Integer size) 
        given Element satisfies Enumerable<Element> 
        => size <= 0 then [] else SizedRange(first, size);
