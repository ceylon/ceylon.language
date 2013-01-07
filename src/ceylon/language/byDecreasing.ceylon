doc "A comparator which orders elements in decreasing order 
     according to the `Comparable` returned by the given 
     `comparable()` function."
see(byIncreasing)
shared Comparison? byDecreasing<Element,Ordered>(Ordered? comparable(Element e))
            (Element x, Element y)
        given Ordered satisfies Comparable<Ordered> {
    if (exists cx = comparable(x), 
        exists cy = comparable(y)) {
        return cy<=>cx;
    }
    else {
        return null;
    }
}