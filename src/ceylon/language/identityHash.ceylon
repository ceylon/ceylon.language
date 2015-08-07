import java.lang { System { identityHashCode } }

"Return the system-defined identity hash value of the given 
 [[value|identifiable]]. This hash value is consistent with 
 [[identity equality|Identifiable.equals]]."
see (`function identical`)
shared native Integer identityHash(Identifiable identifiable);

shared native("jvm") Integer identityHash(Identifiable identifiable) {
    return identityHashCode(identifiable);
}

shared native("js") Integer identityHash(Identifiable identifiable) {
    dynamic {
        dynamic x = identifiable;
        dynamic h = x.\iBasicID;
        return if (h == undefined)
                then (x.\iBasicID = \iBasicID++)
                else h;
    }
}
