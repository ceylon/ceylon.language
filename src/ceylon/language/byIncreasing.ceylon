doc "A comparator which orders elements in increasing order 
     according to the `Comparable` returned by the given 
     `comparable()` function."
see(byDecreasing)
shared Comparison? byIncreasing<Element,Ordered>(Ordered? comparable(Element e))
            (Element x, Element y)
        given Ordered satisfies Comparable<Ordered> {
    if (exists cx = comparable(x),
        exists cy = comparable(y)) {
        return cx<=>cy;
    }
    else {
        return null;
    }
}