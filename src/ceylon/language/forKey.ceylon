doc "A function that returns the result of the given `resulting()` function 
     on the key of a given `Entry`."
see(forItem)
shared Result forKey<Key,Result>(Result resulting(Key key))
            (Key->Value entry) 
        given Key satisfies Value =>
                resulting(entry.key);