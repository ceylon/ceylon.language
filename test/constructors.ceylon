class Outer1129() {
  shared class Inner {
    shared String name;
    shared new foo {
      name = "foo";
    }
    shared new() {
      name = "Inner";
    }
  }
  shared void test() {
    check(Inner.foo.name=="foo", "#1129.4");
  }
}

class Simple1129 {
  shared String name;
  shared new() {
    name="default";
  }
  shared new foo {
    name="Foo!";
  }
}

class Delegating1129 {
  shared String name;
  shared new(String s) {
    name=s;
  }
  shared new foo extends Delegating1129("foo") {}
}

@test
void testConstructors() {
  value o=Outer1129();
  check(o.Inner.foo.name=="foo", "spec#1129.1");
  check(Outer1129().Inner.foo.name=="foo", "spec#1129.2");
  value oi=o.Inner();
  check(oi.foo.name=="foo", "spec#1129.3");
  o.test();
  value ref=Outer1129.Inner.foo;
  check(ref(o).name=="foo", "spec#1129.5");
  check(Simple1129.foo.name=="Foo!", "spec#1129.6");
  check(Simple1129().foo.name=="Foo!", "spec#1129.7");
  check(Delegating1129.foo.name=="foo", "spec#1129.8");
}
