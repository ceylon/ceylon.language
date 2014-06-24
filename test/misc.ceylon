@test
by()
//see()
tagged()
shared void misc() {
    
    function stringify(Character* chars) {
        StringBuilder sb = StringBuilder();
        for (c in chars) {
            sb.appendCharacter(c);
        }
        return sb.string;
    }
    
    check(stringify(*"hello")=="hello", "args");
    check(stringify( 'h', 'i' )=="hi", "sequenced args");
    //unusable check(stringify { chars="hello".characters; }=="hello", "named args");
    // FIXME: Disabled until we fix the backend
    //check(stringify { chars=['h', 'i']; }=="hi", "named sequenced args");
    check(stringify()=="", "no args");
    check(stringify{}=="", "no named args");
            
    variable Integer? x = 0;
    while (exists y = x) { 
        x = null; 
    }
    check(!x exists, "while exists");
    
    variable value s = "hello";
    while (nonempty chars = s.sequence()) { 
        s=""; 
    }
    check(s=="", "while nonempty");
    
    for (n->e in Array(0..10).sequence().indexed) {
        check(n==e, "entry iteration ``n`` != ``e``");
    }
    
    //Test empty varargs
    //see(); 
    by(); tagged();
    concatenate();
    ",".join{};
    StringBuilder().appendAll{};
    //LazyList<Nothing>(); LazyMap<Nothing,Nothing>(); LazySet<Nothing>();
    [1,2,3].items([]);
    [1,2,3].definesAny([]);
    [1,2,3].definesEvery([]);
    [1,2,3].containsAny([]);
    [1,2,3].containsEvery([]);
    check({1,null,3}.first exists, "first [1]");
    check(!{null,2,3}.first exists, "first [2]");
    check(!{null,null,3}.first exists, "first [3]");
    {Integer*} noints={};
    check(!noints.first exists, "first [4]");
    print(null);
}
