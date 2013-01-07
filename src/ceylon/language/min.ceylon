doc "Given a nonempty sequence of `Comparable` values, 
     return the smallest value in the sequence."
see (Comparable, max, smallest)
shared Absent|Element min<Element,Absent>({Element...}&ContainerWithFirstElement<Element,Absent> values) 
        given Element satisfies Comparable<Element>
        given Absent satisfies Null {
    ContainerWithFirstElement<Element,Absent> cwfe = values;
    value first = cwfe.first;
    if (exists first) {
        variable value min=first;
        for (val in values.rest) {
            if (val<min) {
                min=val;
            }
        }
        return min;
    }
    else {
        return first;
    }
}
