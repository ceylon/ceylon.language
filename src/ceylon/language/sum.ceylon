"Given a nonempty stream of [[Summable]] values, return the 
 sum of the values."
see (`function product`)
shared Value sum<Value>({Value+} values) 
        given Value satisfies Summable<Value> {
    value it = values.iterator();
    value first = it.next();
    if (is Integer first) {
        // unbox; don't infer type Value&Integer
        variable Integer sum = first;
        while (is Integer val = it.next()) {
            Integer unboxed = val;
            sum += unboxed;
        }
        assert (is Value result = sum);
        return result;
    }
    else if (is Float first) {
        // unbox; don't infer type Value&Float
        variable Float sum = first;
        while (is Float val = it.next()) {
            Float unboxed = val;
            sum += unboxed;
        }
        assert (is Value result = sum);
        return result;
    }
    else {
        assert (!is Finished first);
        variable value sum = first;
        while (!is Finished val = it.next()) {
            sum += val;
        }
        return sum;
    }
}
