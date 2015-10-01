import ceylon.language {
    seq=sequence
}
"A [[Sequence]] backed by an [[Array]]. 
 
 Since [[Array]]s are mutable, this class is private to the
 language module, where we can be sure the `Array` is not
 modified after the `ArraySequence` has been initialized."
by ("Tom")
see (`function seq`)
shared sealed final
serializable
tagged("Collections", "Sequences")
class ArraySequence<out Element>(array)
        extends Object()
        satisfies [Element+] {
    
    Array<Element> array;
    
    assert (!array.empty);
    
    getFromFirst(Integer index) 
            => array.getFromFirst(index);
    
    contains(Object element) 
            => array.contains(element);
    
    size => array.size;
    
    iterator() => array.iterator();
    
    shared actual Element first {
        if (exists first = array.first) {
            return first;
        }
        else {
            assert (is Element null);
            return null;
        }
    }
    
    shared actual Element last {
        if (exists last = array.last) {
            return last;
        }
        else {
            assert (is Element null);
            return null;
        }
    }
    
    rest => size == 1 
        then [] 
        else ArraySequence(array[1...]);
    
    clone() => ArraySequence(array.clone());
    
    each(void step(Element element)) => array.each(step);
    
    count(Boolean selecting(Element element))
            => array.count(selecting);
    
    every(Boolean selecting(Element element))
            => array.every(selecting);
    
    any(Boolean selecting(Element element))
            => array.any(selecting);
    
    find(Boolean selecting(Element&Object element))
            => array.find(selecting);
    
    findLast(Boolean selecting(Element&Object element))
            => array.findLast(selecting);
    
    shared actual 
    Result|Element reduce<Result>(
        Result accumulating(Result|Element partial, 
                            Element element)) {
        assert (exists result 
            = array.reduce(accumulating));
        return result;
    }

    shared actual 
    [Result+] collect<Result>
            (Result collecting(Element element)) {
        assert (nonempty sequence 
            = array.collect(collecting));
        return sequence;
    }
    
    shared actual 
    [Element+] sort
            (Comparison comparing(Element x, Element y)) {
        assert (nonempty sequence 
            = array.sort(comparing));
        return sequence;
    }
    
    shared actual 
    Element[] measure(Integer from, Integer length) {
        if (from > lastIndex || 
            length <= 0 || 
            from + length <= 0) {
            return [];
        }
        else {
            return ArraySequence(array[from : length]);
        }
    }
    
    shared actual 
    Element[] span(Integer from, Integer to) {
        if (from <= to) {
            return 
                if (to < 0 || from > lastIndex)
                then [] 
                else ArraySequence(array[from..to]);
        }
        else {
            return 
                if (from < 0 || to > lastIndex)
                then [] 
                else ArraySequence(array[from..to]);
        }
    }
    
    shared actual ArraySequence<Element>|[] spanFrom(Integer from) {
        if (from <= 0) {
            return this;
        }
        else if (from < size) {
            return ArraySequence(array[from...]);
        }
        else {
            return [];
        }
    }
    
    shared actual ArraySequence<Element>|[] spanTo(Integer to) {
        if (to >= lastIndex) {
            return this;
        }
        else if (to >= 0) {
            return ArraySequence(array[...to]);
        }
        else {
            return [];
        }
    }
    
}
