"""Represents a collection which maps _keys_ to _items_,
   where a key can map to at most one item. Each such 
   mapping may be represented by an [[Entry]].
   
   A `Map` is a [[Collection]] of its `Entry`s, and a 
   [[Correspondence]] from keys to items.
   
   The presence of an entry in a map may be tested using the 
   `in` operator:
   
       if ("lang"->"en_AU" in settings) { ... }
 
   The entries of the map may be iterated using `for`:
   
       for (key->item in settings) { ... }
   
   The item for a key may be obtained using the item
   operator:
   
       String lang = settings["lang"] else "en_US";
   
   Keys are compared for equality using [[Object.equals]] or
   [[Comparable.compare]]. There may be at most one entry 
   per key."""
see (`class Entry`, 
     `function forKey`, `function forItem`, 
     `function byItem`, `function byKey`)
shared interface Map<out Key=Object, out Item=Anything>
        satisfies Collection<Key->Item> &
                  Correspondence<Object,Item>
        given Key satisfies Object {
    
    "Returns the item of the entry with the given [[key]], 
     or `null` if there is no entry with the given `key` in
     this map."
    shared actual formal Item? get(Object key);
    
    "Determines if there is an entry in this map with the
     given [[key]]."
    see (`function contains`)
    shared actual formal Boolean defines(Object key);
    
    "Determines if the given [[value|entry]] is an [[Entry]]
     belonging to this map."
    see (`function defines`)
    shared actual default Boolean contains(Object entry) {
        if (is Key->Anything entry, defines(entry.key)) {
            if (exists item = get(entry.key)) {
                return if (exists entryItem = entry.item) 
                    then item==entryItem 
                    else false;
            }
            else {
                return !entry.item exists;
            }
        }
        else {
            return false;
        }
    }
    
    "A shallow copy of this map, that is, a map with the
     same entries as this map, which do not change if the
     entries in this map change."
    shared actual formal Map<Key,Item> clone();
    
    "A [[Collection]] containing the keys of this map."
    shared actual default Collection<Key> keys
            => object
            satisfies Collection<Key> {
        contains(Object key) => outer.defines(key);
        iterator() => outer.map(Entry.key).iterator();
        clone() => [*this];
        size => outer.size;
    };
    
    "A [[Collection]] containing the items stored in this 
     map. An element can be stored under more than one key 
     in the map, and so it can occur more than once in the 
     resulting collection."
    shared default Collection<Item> items
            => object
            satisfies Collection<Item> {
        shared actual Boolean contains(Object item) {
            for (k->v in outer) {
                if (exists v, v==item) {
                    return true;
                }
            }
            else {
                return false;
            }
        }
        iterator() => outer.map(Entry.item).iterator();
        clone() => [*this];
        size => outer.size;
    };
    
    "Two maps are considered equal iff they have the same 
     _entry sets_. The entry set of a `Map` is the set of 
     `Entry`s belonging to the map. Therefore, the maps are 
     equal iff they have same set of `keys`, and for every 
     key in the key set, the maps have equal items."
    shared actual default Boolean equals(Object that) {
        if (is Map<Object,Anything> that,
            that.size==size) {
            for (entry in this) {
                value thatItem = that[entry.key];
                if (exists thisItem = entry.item) {
                    if (exists thatItem) {
                        if (thatItem!=thisItem) {
                            return false;
                        }
                    }
                    else {
                        return false;
                    }
                }
                else if (thatItem exists) {
                    return false;
                }
            }
            else {
                return true;
            }
        }
        else {
            return false;
        }
    }
    
    shared actual default Integer hash {
        variable Integer hashCode = 0;
        for (elem in this) {
            hashCode += elem.hash;
        }
        return hashCode;
    }
    
    "Produces a map with the same [[keys]] as this map. 
     For every key, the item is the result of applying the 
     given [[transformation|Map.mapItems.mapping]] function 
     to its associated item in this map. This is a lazy 
     operation, returning a view of this map."
    shared default 
    Map<Key,Result> mapItems<Result>(
        "The function that transforms a key/item pair of
         this map, producing the item of the resulting map."
        Result mapping(Key key, Item item)) 
            given Result satisfies Object
            => object
            extends Object()
            satisfies Map<Key,Result> {
        
        defines(Object key) => outer.defines(key);
        
        shared actual Result? get(Object key) {
            if (is Key key, defines(key)) {
                assert (is Item item = outer[key]);
                return mapping(key, item);
            }
            else {
                return null;
            }
        }
        
        function mapEntry(Key->Item entry) 
                => entry.key
                -> mapping(entry.key, entry.item);
        
        iterator() => outer.map(mapEntry).iterator();
        
        size => outer.size;
        
        clone() => outer.clone().mapItems(mapping);
        
    };
    
    "Produces a map by applying a [[filtering]] function 
     to the [[keys]] of this map. This is a lazy operation, 
     returning a view of this map."
    shared default 
    Map<Key,Item> filterKeys(
        "The predicate function that filters the keys of 
         this map, determining if there is a corresponding
         entry in the resulting map."
        Boolean filtering(Key key))
            => object
            extends Object()
            satisfies Map<Key,Item> {
        
        get(Object key)
                => if (is Key key, filtering(key))
                then outer[key] 
                else null;
        
        defines(Object key) 
                => if (is Key key, filtering(key))
                then outer.defines(key) 
                else false;
        
        iterator() => outer.filter(forKey(filtering)).iterator();
        
        clone() => outer.clone().filterKeys(filtering);
        
    };
    
    "Produces a map whose keys are the union of the keys
     of this map, with the keys of the given [[map|other]].
     For any given key in the resulting map, its associated
     item is the item associated with the key in the given
     map, if any, or the item associated with the key in 
     this map otherwise.
     
     That is, for any `key` in the resulting patched map:
     
         map.patch(other)[key] == other[key] else map[key]
     
     This is a lazy operation producing a view of this map
     and the given map."
    shared default
    Map<Key|OtherKey,Item|OtherItem> 
    patch<OtherKey,OtherItem>
            (Map<OtherKey,OtherItem> other) 
            given OtherKey satisfies Object 
            given OtherItem satisfies Object 
            => object 
            extends Object()
            satisfies Map<Key|OtherKey,Item|OtherItem> {
        
        get(Object key) => other[key] else outer[key];
        
        clone() => outer.clone().patch(other.clone());
        
        defines(Object key) 
                => other.defines(key) || 
                outer.defines(key);
        
        contains(Object entry)
            => if (is Entry<Object,Object> entry)
            then entry in other ||
                    !other.defines(entry.key)
                    && entry in outer
            else false;
        
        //efficient when map is much smaller than outer,
        //which is probably the common case 
        size => outer.size +
                other.keys.count(not(outer.defines));
        
        iterator() => ChainedIterator(other,
                        outer.filter(not(other.contains)));
        
    };
    
    "A map with every entry of this map whose item is
     non-null."
    shared default
    Map<Key,Item&Object> coalescedMap 
            => object
            extends Object()
            satisfies Map<Key,Item&Object> {
        
        defines(Object key) => outer[key] exists;
        
        get(Object key) => outer[key] of <Item&Object>?;
        
        iterator()
                => { for (entry in outer) 
                     if (exists it=entry.item) 
                            entry.key->it }
                        .iterator();
        
        clone() => outer.clone().coalescedMap;
        
    };
    
}

"An immutable [[Map]] with no entries."
shared object emptyMap 
        extends Object() 
        satisfies Map<Nothing, Nothing> {
    
    get(Object key) => null;
    
    keys => emptySet;
    items => emptySet;
    
    clone() => this;
    iterator() => emptyIterator;
    size => 0;
    empty => true;
    
    defines(Object index) => false;
    
    contains(Object element) => false;
    containsAny({Object*} elements) => false;
    containsEvery({Object*} elements) => false;
    
    shared actual 
    Map<Nothing, Nothing> mapItems<Result>
            (Result mapping(Nothing key, Nothing item))
            given Result satisfies Object 
            => emptyMap;
    
    count(Boolean selecting(Nothing->Nothing element)) => 0;
    any(Boolean selecting(Nothing->Nothing element)) => false;
    every(Boolean selecting(Nothing->Nothing element)) => true;
    
    shared actual 
    Null find(Boolean selecting(Nothing->Nothing element)) 
            => null;
    
    shared actual 
    Null findLast(Boolean selecting(Nothing->Nothing element)) 
            => null;
        
    skip(Integer skipping) => this;
    take(Integer taking) => this;
    by(Integer step) => this;
    
    shared actual 
    void each(void step(Nothing->Nothing element)) {}
    
}
