doc "A comparator for `Entry`s which compares their items 
     according to the given `comparing()` function."
see(byKey)
shared Comparison? byItem<Item>(Comparison? comparing(Item x, Item y))
            (Value->Item x, Value->Item y) 
        given Item satisfies Value => 
                comparing(x.item, y.item);