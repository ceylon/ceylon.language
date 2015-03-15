"A [[Sequence]] with exactly one [[element]], which may be 
 null."
shared final serializable class Singleton<out Element>
        (Element element)
        extends Object()
        satisfies [Element+] {
    
    "Returns the element contained in this `Singleton`."
    shared actual Element first => element;
    
    "Returns the element contained in this `Singleton`."
    shared actual Element last => element;
    
    "Returns `0`."
    shared actual Integer lastIndex => 0;
    
    "Returns `1`."
    shared actual Integer size => 1;
    
    "Returns `Empty`."
    shared actual Empty rest => [];
    
    "Returns the contained element, if the specified 
     index is `0`."
    shared actual Element? getFromFirst(Integer index)
            => if (index == 0) then element else null;
    
    "Return this singleton."
    shared actual Singleton<Element> reversed => this;
    
    "Returns a `Singleton` with the same element."
    shared actual 
    Singleton<Element> clone() => this;
    
    "Returns `true` if the specified element is this 
     `Singleton`\'s element."
    shared actual Boolean contains(Object element)
            => if (exists e = this.element) 
            then e == element
            else false;
    
    string => "[``stringify(element)``]";
    
    shared actual Iterator<Element> iterator()
            => object 
            satisfies Iterator<Element> {
        variable Boolean done = false;
        shared actual Element|Finished next() {
            if (done) {
                return finished;
            }
            else {
                done = true;
                return element;
            }
        }
        string => "``outer.string``.iterator()";
    };
    
    "A `Singleton` can be equal to another `List` if 
     that `List` has only one element which is equal to 
     this `Singleton`\'s element."
    shared actual Boolean equals(Object that) {
        if (is List<Anything> that, that.size == 1) {
            value elem = that.first;
            return if (exists element, exists elem) 
            then elem == element
            else !element exists && !elem exists;
        }
        else {
            return false;
        }
    }
    
    shared actual Integer hash 
            => 31 + (element?.hash else 0);
    
    "Returns a `Singleton` if the given starting index 
     is `0` and the given `length` is greater than `0`.
     Otherwise, returns an instance of `Empty`."
    shared actual 
    Empty|Singleton<Element> measure
            (Integer from, Integer length)
            => from <= 0 && from + length > 0 
            then this else [];
    
    "Returns a `Singleton` if the given starting index 
     is `0`. Otherwise, returns an instance of `Empty`."
    shared actual 
    Empty|Singleton<Element> span
            (Integer from, Integer to)
            => from <= 0 && to >= 0 ||
               from >= 0 && to <= 0
            then this else [];
    
    shared actual 
    Empty|Singleton<Element> spanTo(Integer to) 
            => to < 0 then [] else this;
    
    shared actual 
    Empty|Singleton<Element> spanFrom(Integer from) 
            => from > 0 then [] else this;
    
    "Returns `1` if this `Singleton`\'s element
     satisfies the predicate, or `0` otherwise."
    shared actual 
    Integer count(Boolean selecting(Element element))
            => selecting(element) then 1 else 0;
    
    shared actual 
    Singleton<Result> map<Result>
            (Result collecting(Element e))
            => Singleton(collecting(element));
    
    shared actual 
    Singleton<Element>|[] filter
            (Boolean selecting(Element e))
            => selecting(element) then this else [];
    
    shared actual 
    Result fold<Result>(Result initial)
            (Result accumulating(Result partial, 
                                 Element e))
            => accumulating(initial, element);
    
    shared actual 
    Element reduce<Result>
            (Result accumulating(Result|Element partial, 
                                 Element e))
            => element;
    
    shared actual 
    Singleton<Result> collect<Result>
            (Result collecting(Element element))
            => Singleton(collecting(element));
    
    shared actual 
    Singleton<Element>|[] select
            (Boolean selecting(Element element))
            => selecting(element) then this else [];
    
    shared actual 
    Element? find(Boolean selecting(Element&Object e))
            => if (exists element, selecting(element))
                    then element else null;
    
    shared actual 
    Element? findLast(Boolean selecting(Element&Object e))
            => find(selecting);
    
    shared actual 
    Singleton<Element> sort
            (Comparison comparing(Element a, Element b))
            => this;
    
    shared actual 
    Boolean any(Boolean selecting(Element e))
            => selecting(element);
    
    shared actual 
    Boolean every(Boolean selecting(Element e))
            => selecting(element);
    
    shared actual 
    Singleton<Element>|Empty skip(Integer skipping)
            => skipping < 1 then this else [];
    
    shared actual 
    Singleton<Element>|Empty take(Integer taking)
            => taking > 0 then this else [];
    
    shared actual 
    Singleton<Element&Object>|Empty coalesced
            => if (exists element)
                    then Singleton(element) else [];
    
    shared actual 
    {Element|Other+} chain<Other,OtherAbsent>
            (Iterable<Other,OtherAbsent> other)
            given OtherAbsent satisfies Null
            => other.follow(element);
    
    each(void step(Element element)) => step(element);
}
