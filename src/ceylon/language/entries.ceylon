doc "Produces a sequence of each index to element `Entry` 
     for the given sequence of values."
shared {Integer->Element&Value...} entries<Element>
            (Element... elements) 
                    => elements.indexed;
