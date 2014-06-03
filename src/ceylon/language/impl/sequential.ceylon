"Obtains a Sequential containing the same elements as a given iterable.
 The effect is broadly equivalent to
   
     iterable.empty then empty else ArraySequence(iterable)

 Note in particular that `iterable.sequence` is not used, since 
 this is used for evaluating `[*iterable]`.
"
shared Element[] sequential<Element>({Element*} iterable) {
    if (is Element[] iterable) {
        return iterable;
    }
    value initialIt = iterable.iterator();
    variable Iterator<Element>? firstIt = initialIt;
    variable Element|Finished first = initialIt.next();
    if (!is Finished firstFirst = first) {
        object notempty satisfies {Element+} {
            shared actual Iterator<Element> iterator() {
                if (exists currentIt = firstIt) {
                    // this is the first call to iterator()
                    // reuse firstFirst and firstIt
                    firstIt = null; // prevent later iterator() calls to reuse them as well
                    object iterator satisfies Iterator<Element> {
                        variable Element|Finished firstElement = firstFirst;
                        shared actual Element|Finished next() {
                            if (!is Finished theFirst = firstElement) {
                                firstElement = finished;
                                return theFirst;
                            }
                            return currentIt.next();
                        }
                    }
                    return iterator;
                } else {
                    // this is a later call to iterator()
                    // do not reuse firstFirst and firstIt;
                    // instead, get them again
                    value it = iterable.iterator();
                    object iterator satisfies Iterator<Element> {
                        variable value first = true;
                        shared actual Element|Finished next() {
                            value next = it.next();
                            if (first) {
                                first = false;
                                assert (!next is Finished);
                            }
                            return next;
                        }
                    }
                    return iterator;
                }
            }
        }
        return ArraySequence(notempty);
    } else {
        return empty;
    }
}