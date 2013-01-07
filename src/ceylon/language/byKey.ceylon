doc "A comparator for `Entry`s which compares their keys 
     according to the given `comparing()` function."
see(byItem)
shared Comparison? byKey<Key>(Comparison? comparing(Key x, Key y))
            (Key->Value x, Key->Value y) 
        given Key satisfies Value =>
                comparing(x.key, y.key);