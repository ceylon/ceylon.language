"Copy of `ceylon.language.impl.sequential`'s implementation
 to test its `notempty` right before it is passed to [[ArraySequence]].
 
 `notempty.string` is evaluated exactly three times, so `iterable`
 should be evaluated exactly three times."
void c_l_impl_sequential<Element>({Element*} iterable) {
    if (is Element[] iterable) {
        // return iterable;
        return;
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
        value s1 = notempty.string;
        value s2 = notempty.string;
        value s3 = notempty.string;
        check(s1 == s2, "sequential: second iteration works: expected '``s1``' == '``s2``'");
        check(s2 == s3, "sequential: third iteration works: expected '``s2``' == '``s3``'");
        // return ArraySequence(notempty);
    } else {
        // return empty;
    }
}

@test
shared void sequential() {
    c_l_impl_sequential({ 1, 2, 3 }); // easy
    variable Integer i = 0;
    Boolean f() => i++>=0; // always true, but with side-effect
    c_l_impl_sequential({ for (j in { 1, 2, 3, 4 }) if (f()) j });
    check(i == 12, "sequential: should have evaluated f() 12 times but evaluated ``i`` times");
}
