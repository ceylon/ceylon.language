import ceylon.language.meta.declaration { ClassDeclaration, FunctionDeclaration, ValueDeclaration, SetterDeclaration }

shared class Locals_OuterLocalClass<Outer>(){

    ClassDeclaration classDecl1;
    if(true){
        class LocalClass<Inner>(){}
        class LocalClass2() extends LocalClass<String>(){
            shared LocalClass<String>&LocalClass2 f(){ return nothing; }
        }
        classDecl1 = `class LocalClass`;
        assert(classDecl1.container == `class Locals_OuterLocalClass`);
        assert(`class LocalClass2`.container == `class Locals_OuterLocalClass`);
    }
    ClassDeclaration classDecl2;
    if(true){
        class LocalClass<Inner>(){}
        classDecl2 = `class LocalClass`;
        assert(classDecl2.container == `class Locals_OuterLocalClass`);
    }
    assert(classDecl1 != classDecl2);
    
    if(true){
        void localMethod(){
            class LocalClassInLocalMethod<Inner>(){}
            value d = `class LocalClassInLocalMethod`;
            assert(is FunctionDeclaration lm = d.container);
            assert(lm.name == "localMethod");
            assert(lm.container == `class Locals_OuterLocalClass`);
        }
        localMethod();
        
        variable ClassDeclaration? classDecl3 = null;
        variable ClassDeclaration? classDecl4 = null;
        Integer localAttribute {
            class LocalClassInLocalAttribute<Inner>(){}
            value d = `class LocalClassInLocalAttribute`;
            assert(is ValueDeclaration lm = d.container);
            assert(lm.variable);
            assert(lm.name == "localAttribute");
            assert(lm.container == `class Locals_OuterLocalClass`);
            classDecl3 = d;
            return 1;
        }
        assign localAttribute {
            class LocalClassInLocalAttribute<Inner>(){}
            value d = `class LocalClassInLocalAttribute`;
            assert(is SetterDeclaration lm = d.container);
            assert(lm.name == "localAttribute");
            assert(lm.container == `class Locals_OuterLocalClass`);
            classDecl4 = d;
        }
        value attr0 = localAttribute;
        localAttribute = 1;
        assert(exists cd3 = classDecl3, exists cd4 = classDecl4, cd3 != cd4);
    }
    
    variable ClassDeclaration? classDecl3_1 = null;
    variable ClassDeclaration? classDecl4_1 = null;
    Integer privateAttribute {
        class LocalClassInLocalAttribute<Inner>(){}
        value d = `class LocalClassInLocalAttribute`;
        assert(is ValueDeclaration lm = d.container);
        assert(lm.variable);
        assert(lm.name == "privateAttribute");
        assert(lm.container == `class Locals_OuterLocalClass`);
        classDecl3_1 = d;
        
        // make sure types contained in getter are supported in type parser
        class LocalClass<Inner>(){}
        class LocalClass2() extends LocalClass<String>(){
            shared LocalClass<String>&LocalClass2 f(){ return nothing; }
        }
        value lc = `class LocalClass`;
        assert(lc.container == lm);
        // this causes loading of the return type
        assert(`function LocalClass2.f`.name == "f");

        return 1;
    }
    assign privateAttribute {
        class LocalClassInLocalAttribute<Inner>(){}
        value d = `class LocalClassInLocalAttribute`;
        assert(is SetterDeclaration lm = d.container);
        assert(lm.name == "privateAttribute");
        assert(lm.container == `class Locals_OuterLocalClass`);
        classDecl4_1 = d;

        // make sure types contained in setter are supported in type parser
        class LocalClass<Inner>(){}
        class LocalClass2() extends LocalClass<String>(){
            shared LocalClass<String>&LocalClass2 f(){ return nothing; }
        }
        value lc = `class LocalClass`;
        assert(lc.container == lm);
        // this causes loading of the return type
        assert(`function LocalClass2.f`.name == "f");
    }
    // run getter and setter
    value attr = privateAttribute;
    privateAttribute = 1;
    
    assert(exists cd3 = classDecl3_1, exists cd4 = classDecl4_1, cd3 != cd4);
    
    // this method turns privateAttribute from a local declaration to a member
    shared void capture(){
        value attr = privateAttribute;
    }

    shared Integer outerAttribute {
        ClassDeclaration classDecl1;
        if(true){
            class LocalClass<Inner>(){}
            class LocalClass2() extends LocalClass<String>(){
                shared LocalClass<String>&LocalClass2 f(){ return nothing; }
            }
            classDecl1 = `class LocalClass`;
            assert(classDecl1.container == `value outerAttribute`);
            assert(`class LocalClass2`.container == `value outerAttribute`);
        }
        ClassDeclaration classDecl2;
        if(true){
            class LocalClass<Inner>(){}
            classDecl2 = `class LocalClass`;
            assert(classDecl2.container == `value outerAttribute`);
        }
        assert(classDecl1 != classDecl2);
        
        void localMethod(){
            class LocalClassInLocalMethod<Inner>(){}
            value d = `class LocalClassInLocalMethod`;
            assert(is FunctionDeclaration lm = d.container);
            assert(lm.name == "localMethod");
            assert(lm.container == `value outerAttribute`);
        }
        localMethod();
        
        Integer localAttribute {
            class LocalClassInLocalAttribute<Inner>(){}
            value d = `class LocalClassInLocalAttribute`;
            assert(is ValueDeclaration lm = d.container);
            assert(lm.name == "localAttribute");
            assert(lm.container == `value outerAttribute`);
            return 1;
        }
        value attr = localAttribute;

        return 1;
    }
    
    shared void outerMethod<Method>(Object o){
        ClassDeclaration classDecl1;
        if(true){
            class LocalClass<Inner>(){}
            class LocalClass2() extends LocalClass<Method>(){
                shared LocalClass<String>&LocalClass2 f(){ return nothing; }
            }
            classDecl1 = `class LocalClass`;
            assert(classDecl1.container == `function outerMethod`);
            assert(`class LocalClass2`.container == `function outerMethod`);
        }
        ClassDeclaration classDecl2;
        if(true){
            class LocalClass<Inner>(){}
            classDecl2 = `class LocalClass`;
            assert(classDecl2.container == `function outerMethod`);
        }
        assert(classDecl1 != classDecl2);
        
        void localMethod(){
            class LocalClassInLocalMethod<Inner>(){}
            value d = `class LocalClassInLocalMethod`;
            assert(is FunctionDeclaration lm = d.container);
            assert(lm.name == "localMethod");
            assert(lm.container == `function outerMethod`);
        }
        localMethod();
        
        Integer localAttribute {
            class LocalClassInLocalAttribute<Inner>(){}
            value d = `class LocalClassInLocalAttribute`;
            assert(is ValueDeclaration lm = d.container);
            assert(lm.name == "localAttribute");
            assert(lm.container == `function outerMethod`);
            return 1;
        }
        value attr1 = localAttribute;
        
        reifiedMethod<String>(null);
        value attr2 = outerAttribute;
        
        String str = string;
        Integer h = hash;
    }
    
    void reifiedMethod<Method>(Object? arg){
        class Inner<T>(){}
        
        if(exists arg){
            assert(`Method` == `Integer`);
            // it is not an Locals_OuterLocalClass<Integer>.reifiedMethod<Integer>.Inner<Integer>
            assert(! is Inner<Integer> arg);
        }else{
            assert(`Method` == `String`);
            // build a Locals_OuterLocalClass<Integer>.reifiedMethod<String>.Inner<Integer>
            Object inner = Inner<Integer>();
            assert(is Inner<Integer> inner);
            reifiedMethod<Integer>(inner);
        }
    }
    
    shared actual String string {
        class Local(){}
        assert(`class Local`.container == `value string`);
        return "";
    }

    shared actual Integer hash {
        class Local(){}
        assert(`class Local`.container == `value hash`);
        return 1;
    }
}

variable ClassDeclaration? locals_classDecl3 = null;
variable ClassDeclaration? locals_classDecl4 = null;

Integer locals_toplevelAttribute {
    class LocalClassInLocalAttribute<Inner>(){}
    value d = `class LocalClassInLocalAttribute`;
    assert(is ValueDeclaration lm = d.container);
    assert(lm.variable);
    assert(lm.name == "locals_toplevelAttribute");
    assert(lm.container == `package metamodel`);
    locals_classDecl3 = d;
    
    // make sure types contained in getter are supported in type parser
    class LocalClass<Inner>(){}
    class LocalClass2() extends LocalClass<String>(){
        shared LocalClass<String>&LocalClass2 f(){ return nothing; }
    }
    value lc = `class LocalClass`;
    assert(lc.container == lm);
    // this causes loading of the return type
    assert(`function LocalClass2.f`.name == "f");
    
    Object o = LocalClass<Integer>();
    assert(is LocalClass<Integer> o);
    
    return 1;
}
assign locals_toplevelAttribute {
    class LocalClassInLocalAttribute<Inner>(){}
    value d = `class LocalClassInLocalAttribute`;
    assert(is SetterDeclaration lm = d.container);
    assert(lm.name == "locals_toplevelAttribute");
    assert(lm.container == `package metamodel`);
    locals_classDecl4 = d;
    
    // make sure types contained in setter are supported in type parser
    class LocalClass<Inner>(){}
    class LocalClass2() extends LocalClass<String>(){
        shared LocalClass<String>&LocalClass2 f(){ return nothing; }
    }
    value lc = `class LocalClass`;
    assert(lc.container == lm);
    // this causes loading of the return type
    assert(`function LocalClass2.f`.name == "f");

    Object o = LocalClass<Integer>();
    assert(is LocalClass<Integer> o);
}

void locals_reifiedMethod<Method>(Object? arg){
    class Inner<T>(){}
    
    if(exists arg){
        assert(`Method` == `Integer`);
        // it is not an locals_reifiedMethod<Integer>.Inner<Integer>
        assert(! is Inner<Integer> arg);
    }else{
        assert(`Method` == `String`);
        // build a locals_reifiedMethod<String>.Inner<Integer>
        Object inner = Inner<Integer>();
        assert(is Inner<Integer> inner);
        locals_reifiedMethod<Integer>(inner);
    }
}

void locals_testToplevels(){
    // run getter and setter
    value attr = locals_toplevelAttribute;
    locals_toplevelAttribute = 1;
    assert(exists cd3 = locals_classDecl3, exists cd4 = locals_classDecl4, cd3 != cd4);
    
    locals_reifiedMethod<String>(null);
}

class Locals_Outer<O>(){
    shared class Inner<O>(){}
    shared void f<O>(Object? o){
        class LocalClass<O>(){}
        assert(`O` == `String`);
        if(exists o){
            // make sure it is a f<String>.LocalClass<Integer>
            assert(is LocalClass<Integer> o);
        }else{
            // if LocalClass.$getType refers to f.O incorrectly as LocalClass.O,
            // this instance will be f<Integer>.LocalClass<Integer>
            // instead of f<String>.LocalClass<Integer>
            f<String>(LocalClass<Integer>());
        }
    }
}

interface Locals_OuterInterface<Outer>{
    // make sure these ones have annotations
    Integer privateAttr {
        class Local(){}
        return 1;
    }
    assign privateAttr {
        class Local(){}
    }
    // make sure this one has annotations
    void privateDefaulted(Integer i=1){}
    // make sure this one has no annotation
    shared void defaulted(Integer i=1){}
    
    shared Integer outerAttribute {
        ClassDeclaration classDecl1;
        if(true){
            class LocalClass<Inner>(){}
            class LocalClass2() extends LocalClass<String>(){
                shared LocalClass<String>&LocalClass2 f(){ return nothing; }
            }
            classDecl1 = `class LocalClass`;
            assert(classDecl1.container == `value outerAttribute`);
            assert(`class LocalClass2`.container == `value outerAttribute`);
        }
        ClassDeclaration classDecl2;
        if(true){
            class LocalClass<Inner>(){}
            classDecl2 = `class LocalClass`;
            assert(classDecl2.container == `value outerAttribute`);
        }
        assert(classDecl1 != classDecl2);
        
        void localMethod(){
            class LocalClassInLocalMethod<Inner>(){}
            value d = `class LocalClassInLocalMethod`;
            assert(is FunctionDeclaration lm = d.container);
            assert(lm.name == "localMethod");
            assert(lm.container == `value outerAttribute`);
        }
        localMethod();
        
        Integer localAttribute {
            class LocalClassInLocalAttribute<Inner>(){}
            value d = `class LocalClassInLocalAttribute`;
            assert(is ValueDeclaration lm = d.container);
            assert(lm.name == "localAttribute");
            assert(lm.container == `value outerAttribute`);
            return 1;
        }
        value attr = localAttribute;
        
        return 1;
    }

    shared void outerMethod<Method>(Object o){
        ClassDeclaration classDecl1;
        if(true){
            class LocalClass<Inner>(){}
            class LocalClass2() extends LocalClass<Method>(){
                shared LocalClass<String>&LocalClass2 f(){ return nothing; }
            }
            classDecl1 = `class LocalClass`;
            assert(classDecl1.container == `function outerMethod`);
            assert(`class LocalClass2`.container == `function outerMethod`);
        }
        ClassDeclaration classDecl2;
        if(true){
            class LocalClass<Inner>(){}
            classDecl2 = `class LocalClass`;
            assert(classDecl2.container == `function outerMethod`);
        }
        assert(classDecl1 != classDecl2);
        
        void localMethod(){
            class LocalClassInLocalMethod<Inner>(){}
            value d = `class LocalClassInLocalMethod`;
            assert(is FunctionDeclaration lm = d.container);
            assert(lm.name == "localMethod");
            assert(lm.container == `function outerMethod`);
        }
        localMethod();
        
        Integer localAttribute {
            class LocalClassInLocalAttribute<Inner>(){}
            value d = `class LocalClassInLocalAttribute`;
            assert(is ValueDeclaration lm = d.container);
            assert(lm.name == "localAttribute");
            assert(lm.container == `function outerMethod`);
            return 1;
        }
        value attr1 = localAttribute;
        
        reifiedMethod<String>(null);
        
        value outerAttr = outerAttribute;
        
        privateMethodForLocalInterfaces();
    }
    
    void privateMethodForLocalInterfaces(){
        // make sure private methods are loaded for local interfaces
        interface LocalI1 {
            void privateMethod(){
                interface LocalI2{
                }
                assert(`interface LocalI2`.container.name == "privateMethod");
            }
            shared void f(){
                privateMethod();
            }
        }
        class LocalC() satisfies LocalI1{}
        LocalC().f();
    }
    
    void reifiedMethod<Method>(Object? arg){
        class Inner<T>(){}
        
        if(exists arg){
            assert(`Method` == `Integer`);
            // it is not an Locals_OuterInterface<Integer>.reifiedMethod<Integer>.Inner<Integer>
            assert(! is Inner<Integer> arg);
        }else{
            assert(`Method` == `String`);
            // build a Locals_OuterInterface<Integer>.reifiedMethod<String>.Inner<Integer>
            Object inner = Inner<Integer>();
            assert(is Inner<Integer> inner);
            reifiedMethod<Integer>(inner);
        }
    }
}

class Locals_OuterInterfaceImpl<T>() satisfies Locals_OuterInterface<T>{}

variable Object? locals_packageLocalClass1 = null;
variable Object? locals_packageLocalClass2 = null;

Anything() locals_packageLocal1 = void(){
    class PackageLocal() {}
    locals_packageLocalClass1 = `class PackageLocal`;
};
Anything() locals_packageLocal2 = void(){
    class PackageLocal() {}
    locals_packageLocalClass2 = `class PackageLocal`;
};

void locals_testPackageLocals(){
    locals_packageLocal1();
    locals_packageLocal2();
    assert(is ClassDeclaration c1 = locals_packageLocalClass1);
    assert(is ClassDeclaration c2 = locals_packageLocalClass2);
    assert(c1 != c2);
    assert(c1.container == `package metamodel`);
    assert(c2.container == `package metamodel`);
    assert(!c1.toplevel, !c2.toplevel);
    Locals_ClassLocalsInFunctions().check();
}

class Locals_ClassLocalsInFunctions(){
    variable ClassDeclaration? localClass1 = null;
    variable ClassDeclaration? localClass2 = null;
    Anything() local1 = void(){
        class Local() {}
        localClass1 = `class Local`;
    };
    Anything() local2 = void(){
        class Local() {}
        localClass2 = `class Local`;
    };
    shared void check(){
        local1();
        local2();
        assert(is ClassDeclaration c1 = localClass1);
        assert(is ClassDeclaration c2 = localClass2);
        assert(c1 != c2);
        assert(c1.container == `class Locals_ClassLocalsInFunctions`);
        assert(c2.container == `class Locals_ClassLocalsInFunctions`);
        assert(!c1.toplevel, !c2.toplevel);
    }
}

void locals_metamodelEquality(){
    variable Object? value1 = null;
    variable Object? value2 = null;
    variable Object? method1 = null;
    variable Object? method2 = null;
    variable Object? class1 = null;
    variable Object? class2 = null;
    variable Object? interface1 = null;
    variable Object? interface2 = null;
    variable Object? setter1 = null;
    variable Object? setter2 = null;
    if(true){
        Integer getter {
            class C(){}
            interface I{}
            class1 = `class C`;
            interface1 = `interface I`;
            value1 = `class C`.container;
            return 1;
        }
        assign getter {
            class C(){}
            setter1 = `class C`.container;
        }
        void m(){
            class C(){}
            method1 = `class C`.container;
        }
        Integer i = getter;
        getter = 1;
        m();
    }
    if(true){
        Integer getter {
            class C(){}
            interface I{}
            class2 = `class C`;
            interface2 = `interface I`;
            value2 = `class C`.container;
            return 1;
        }
        assign getter {
            class C(){}
            setter2 = `class C`.container;
        }
        void m(){
            class C(){}
            method2 = `class C`.container;
        }
        Integer i = getter;
        getter = 1;
        m();
    }
    assert(exists c1=class1, exists c2=class2, c1 != c2);
    assert(exists i1=interface1, exists i2=interface2, i1 != i2);
    assert(exists v1=value1, exists v2=value2, v1 != v2);
    assert(exists s1=setter1, exists s2=setter2, s1 != s2);
    assert(exists m1=method1, exists m2=method2, m1 != m2);
}

void locals_localWithoutTypes(){
    void localFunction(){}
    Integer localGetter {
        return 1;
    }
}

class Locals_C(){
    shared object l{}
}

shared void locals(){
    Locals_OuterLocalClass<Integer>().outerMethod<String>(1);
    Locals_OuterInterfaceImpl<Integer>().outerMethod<String>(1);
    locals_testToplevels();
    value o = Locals_Outer<String>();
    Object i = o.Inner<Integer>();
    assert(is Locals_Outer<String>.Inner<Integer> i);
    o.f<String>(null);
    locals_metamodelEquality();
    // make sure we can load the getter for l
    assert(`value Locals_C.l`.name == "l");
    value m1 = Locals_MapTest<Integer, String>(1->"A", 2->"B", 3->"C", 4->"B");
    assert(m1.clone() == m1);
    locals_testPackageLocals();
}

final annotation class Locals_Foo() satisfies OptionalAnnotation<Locals_Foo> {}

interface Locals_MapTestBase<out Key, out Item> satisfies Map<Key, Item>
        given Key satisfies Object
        given Item satisfies Object {
    shared formal Entry<Key, Item>[] entries;
}

class Locals_MapTest<Key, Item>(<Key->Item>* entry)
        extends Object()
        satisfies Locals_MapTestBase<Key, Item>
        given Key satisfies Object
        given Item satisfies Object {
    shared actual Entry<Key, Item>[] entries = entry.sequence();
    shared actual Integer size { return entries.size; }
    shared actual Boolean empty { return entries.empty; }
    shared actual Locals_MapTest<Key, Item> clone() { return this; }
    shared actual Boolean defines(Object key) {
        for (k->v in entry) {
            if (k==key) {
                return true;
            }
        }
        return false;
    }
    shared actual Iterator<Key->Item> iterator() { return entries.iterator(); }
    shared actual Item? get(Object key) {
        for (e in entries) {
            if (e.key == key) { return e.item; }
        }
        return null;
    }
}

shared interface Locals_MyIterable<out Element, out Absent=Null>
        satisfies Category
        given Absent satisfies Null {

    shared default {<Integer->Element&Object>*} indexed {
        object indexes
            satisfies {<Integer->Element&Object>*} {
            iterator() => nothing;
        }
        return indexes;
    }
}
