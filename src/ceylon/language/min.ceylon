"Given a stream of [[Comparable]] values, return the 
 smallest value in the stream, or `null` if the stream is
 empty.
 
 For any nonempty stream `it`, `min(it)` evaluates to the 
 first element of `it` such that for every element `e` of 
 `it`, `min(it) <= e`.
 
 Note that [[Iterable.min]] may be used to find the smallest 
 value in any stream, as determined by a given comparator 
 function."
see (`interface Comparable`, 
     `function max`,
     `function smallest`,
     `function Iterable.min`)
shared Absent|Value min<Value,Absent>
        (Iterable<Value,Absent> values) 
        given Value satisfies Comparable<Value>
        given Absent satisfies Null {
    value it = values.iterator();
    if (!is Finished first = it.next()) {
        variable value min = first;
        while (!is Finished val = it.next()) {
            if (val<min) {
                min = val;
            }
        }
        return min;
    }
    else {
        "iterable must be empty"
        assert (is Absent null);
        return null;
    }
}
