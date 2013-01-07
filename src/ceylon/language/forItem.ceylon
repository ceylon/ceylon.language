doc "A function that returns the result of the given `resulting()` function 
     on the item of a given `Entry`."
see(forKey)
shared Result forItem<Item,Result>(Result resulting(Item item))
            (Value->Item entry) 
        given Item satisfies Value =>
                resulting(entry.item);