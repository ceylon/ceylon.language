doc "Given a nonempty sequence of `Comparable` values, 
     return the largest value in the sequence."
see (Comparable, min, largest)
shared Absent|Element max<Element,Absent>({Element...}&ContainerWithFirstElement<Element,Absent> values) 
        given Element satisfies Comparable<Element>
        given Absent satisfies Null {
    ContainerWithFirstElement<Element,Absent> cwfe = values;
    value first = cwfe.first;
    if (exists first) {
        variable value max=first;
        for (val in values.rest) {
            if (val>max) {
                max=val;
            }
        }
        return max;
    }
    else {
        return first;
    }
}
