interface SpreadTest {
    shared formal String x();
}
class Spread1() satisfies SpreadTest {
    shared actual String x() { return "S1"; }
    shared class Internal(){
      shared Spread1 fuera => outer;
    }
}
class Spread2() satisfies SpreadTest {
    shared actual String x() { return "S2"; }
}

class Rectangle(width, height) satisfies Scalable<Float,Rectangle> {
    shared variable Float width;
    shared variable Float height;
    string => "Rectangle ``width`` by ``height``";
    shared actual Boolean equals(Object other) {
        if (is Rectangle other) {
            return other.width==width && other.height==height;
        }
        return false;
    }
    shared actual Rectangle scale(Float d) => Rectangle(width*d, height*d);
    shared Rectangle invert() {
        value t = width;
        width = height;
        height = t;
        return this;
    }
}

@test
shared void operators() {
    String? maybe = "hello";
    String? maybeNot = null;
    check(maybe?.uppercased exists, "?.");
    check(!maybeNot?.uppercased exists, "?.");
    check((maybe else "goodbye")=="hello", "?");
    check((maybeNot else "goodbye")=="goodbye", "?");
    //check(maybe?[0] exists, "?[]");
    //check(maybe?[4] exists, "?[]");
    //check(!maybe?[10] exists, "?[]");
    //check(!maybeNot?[0] exists, "?[]");
    //check(!maybeNot?[10] exists, "?[]");

    String[] empty = [];
    String[] full = [ "hello", "world" ];
    check(!empty*.uppercased nonempty, "spread 1");
    check(full*.uppercased nonempty, "spread 2");
    value spread1 = full*.uppercased;
    value spread2 = full*.get(1);
	if (exists s1s=spread1[0]) {
        check(s1s == "HELLO", "spread 3");
    } else { fail("spread 3"); }
    if (exists s1s=spread1[1]) {
        check(s1s == "WORLD", "spread 4");
    } else { fail("spread 4"); }
    check(spread1.size == 2, "spread 5");
    check(spread2.size == 2, "spread 6");
    if (exists s2s=spread2[0]) {
        check(s2s == 'e', "spread 7");
    } else { fail("spread 7"); }
    if (exists s2s=spread2[1]) {
        check(s2s == 'o', "spread 8");
    } else { fail("spread 8"); }
    /*
    Character?[] spread3(Integer x) = full*.item;
    //Callable<Character?[], Integer> spread3 = full*.item;
    value spread4 = spread3(1);
    check(spread4.size == 2, "spread 10");
    if (exists s4s=spread4[0]) {
        check(s4s == 'e', "spread 11");
    } else { fail("spread 11"); }
    if (exists s4s=spread4[1]) {
        check(s4s == 'o', "spread 12");
    } else { fail("spread 12"); }
    */
    value spreadList = [ Spread1(), Spread2() ];
    value spread13 = spreadList*.x();
    check(spread13.size == 2, "spread 13 size");
    check(spread13[0].lowercased=="s1", "spread 13 item 0");
    if (is String s13_1 = spread13[1]) {
        check(s13_1 == "S2", "spread 13 item 1");
    } else { fail("spread 13 item 1"); }
    if (runtime.name=="jvm") {
        print("Spread method refs tests DISABLED for now");
    } else {
    value spread14 = [ for (i in 1..3) "HELLO!" ]*.initial;
    check(spread14(5).size == 3, "spread 14 size");
    check(spread14(1)[0] == "H", "spread 14 item 0");
    if (is String s14_1 = spread14(2)[1]) {
        check(s14_1 == "HE", "spread 14 item 1");
    } else { fail("spread 14 item 1");}
        check(spread14(2) == ["HE", "HE", "HE"], "spread14(2) == ``spread14(2)``");
    }
    value spreadTypes = { spreadList.first, Spread1() }*.Internal();
    check(spreadTypes.size==2, "Spread 15");
    check(spreadTypes.first.fuera == spreadList.first, "Spread 16");

    check("hello" in "hello world", "in 1");
    check("world" in "hello world", "in 2");

    Correspondence<Integer, String> c1 = [];
    check(!c1[0] exists, "empty correspondence");
    
    Ranged<Integer,String,String[]> sequence = ["foo", "bar"];
    String[] subrange = sequence[1..2];
    check(subrange.size==1, "subrange size");
    check(subrange nonempty, "subrange nonempty");
    check(sequence[1...].size==1, "open subrange size 1");
    check(sequence[0...].size==2, "open subrange size 2");
    check(sequence[1...] nonempty, "open subrange nonempty");
    check(!sequence[2...] nonempty, "open subrange empty");
                                
    Float x = 0.5;
    check((x>0.0 then x) exists, "then not null");
    check(!(x<0.0 then x) exists, "then null");
    check((x<0.0 then x else 1.0) == 1.0, "then else");
    check((x>0.0 then x else 1.0) == 0.5, "then");
    
    check((maybe else "goodbye")=="hello", "else");
    check((maybeNot else "goodbye")=="goodbye", "else");
    
    class X() {}
    X? xx = X();
    Object? obj(Object? x) { return x; }
    check(obj(xx else X()) is X, "something");
    check(obj(true then X()) is X, "something");
    check(obj(true then X() else X()) is X, "something");

    value scaleTestRectangle = Rectangle(2.0, 3.0);
    check(2.0**scaleTestRectangle == Rectangle(4.0, 6.0), "scaling ``2.0**Rectangle(2.0, 3.0)``");
    check(scaleTestRectangle.invert().width ** Rectangle(scaleTestRectangle.width, scaleTestRectangle.width) == Rectangle(9.0, 9.0), "scaling: LHS must be evaluated first!");
}
