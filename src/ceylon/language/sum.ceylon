doc "Given a nonempty sequence of `Summable` values, 
     return the sum of the values."
see (Summable)
shared Element sum<Element>(Sequence<Element> values) 
        given Element satisfies Summable<Element> {
    variable value sum = values.first;
    for (val in values.rest) {
        sum+=val;
    }
    return sum;
}
