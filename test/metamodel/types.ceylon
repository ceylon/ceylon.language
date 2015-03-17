import ceylon.language.meta.declaration { 
    ClassDeclaration, 
    ConstructorDeclaration 
}
import ceylon.language.meta.model { 
    Class, 
    MemberClass, 
    MemberClassConstructor, 
    Constructor,
    InvocationException,
    IncompatibleTypeException
}
import ceylon.language.meta{ type }

shared String toplevelString = "a";
shared Integer toplevelInteger = 1;
shared Float toplevelFloat = 1.2;
shared Character toplevelCharacter = 'a';
shared Boolean toplevelBoolean = true;
shared Object toplevelObject = 2;

shared variable String toplevelString2 = "a";
shared variable Integer toplevelInteger2 = 1;
shared variable Float toplevelFloat2 = 1.2;
shared variable Character toplevelCharacter2 = 'a';
shared variable Boolean toplevelBoolean2 = true;
shared variable Object toplevelObject2 = 2;

String privateToplevelAttribute = "a";
String privateToplevelFunction(){
    return "b";
}

shared object topLevelObjectDeclaration {
}

class PrivateClass(){
    String privateString = "a";
    String privateMethod(){
        // capture privateString
        privateString.iterator();
        return "b";
    }
    class Inner(){
        string = "c";
    }
    string = "d";
    shared class OtherInner(){}
}
class PrivateSubclass() extends PrivateClass() {}

shared class NoParams(){
    shared variable String str2 = "a";
    shared variable Integer integer2 = 1;
    shared variable Float float2 = 1.2;
    shared variable Character character2 = 'a';
    shared variable Boolean boolean2 = true;
    shared variable Object obj2 = 2;

    shared String str = "a";
    shared Integer integer = 1;
    shared Float float = 1.2;
    shared Character character = 'a';
    shared Boolean boolean = true;
    shared NoParams obj => this;

    shared NoParams noParams() => this;

    shared NoParams fixedParams(String s, Integer i, Float f, Character c, Boolean b, Object o){
        assert(s == "a");
        assert(i == 1);
        assert(f == 1.2);
        assert(c == 'a');
        assert(b == true);
        assert(is NoParams o);
        
        return this;
    }
    
    shared NoParams typeParams<T>(T s, Integer i)
        given T satisfies Object {
        
        assert(s == "a");
        assert(i == 1);
        
        // check that our reified T got passed correctly
        assert(is TypeParams<String> t = TypeParams<T>(s, i));
        
        return this;
    }
    
    shared String getString() => "a";
    shared Integer getInteger() => 1;
    shared Float getFloat() => 1.2;
    shared Character getCharacter() => 'a';
    shared Boolean getBoolean() => true;
    
    shared TPA & TPB intersection1 => nothing;
    shared TPB & TPA intersection2 => nothing;
    shared TPA & TPB & NoParams intersection3 => nothing;
    shared TPA | TPB union1 => nothing;
    shared TPB | TPA union2 => nothing;
    shared TPB | TPA | NoParams union3 => nothing;
    
    shared void tp1<T>(){}
}

shared class FixedParams(String s, Integer i, Float f, Character c, Boolean b, Object o){
    assert(s == "a");
    assert(i == 1);
    assert(f == 1.2);
    assert(c == 'a');
    assert(b == true);
    assert(is NoParams o);
}

shared class DefaultedParams(Integer lastGiven = 0, String s = "a", Boolean b = true){
    if(lastGiven == 0){
        assert(s == "a");
        assert(b == true);
    }else if(lastGiven == 1){
        assert(s == "b");
        assert(b == true);
    }else if(lastGiven == 2){
        assert(s == "b");
        assert(b == false);
    }
}

shared class DefaultedParams2(Boolean set, Integer a = 1, Integer b = 2, Integer c = 3, Integer d = 4){
    if(set){
        assert(a == -1);
        assert(b == -2);
        assert(c == -3);
        assert(d == -4);
    }else{
        assert(a == 1);
        assert(b == 2);
        assert(c == 3);
        assert(d == 4);
    }
}

shared class TypeParams<T>(T s, Integer i)
    given T satisfies Object {
    
    assert(s == "a");
    assert(i == 1);
    
    shared T t1 = s;
    shared T t2 = s;
    
    shared T method<S>(T t, S s) => t;
}

shared class TypeParams2<T>() {
    shared T t1 => nothing;
}

shared class Sub1() extends TypeParams<Integer>(1, 1){}
shared class Sub2() extends TypeParams<String>("A", 1){}

shared void fixedParams(String s, Integer i, Float f, Character c, Boolean b, Object o, NoParams oTyped){
    assert(s == "a");
    assert(i == 1);
    assert(f == 1.2);
    assert(c == 'a');
    assert(b == true);
    assert(is NoParams o);
}

shared void defaultedParams(Integer lastGiven = 0, String s = "a", Boolean b = true){
    if(lastGiven == 0){
        assert(s == "a");
        assert(b == true);
    }else if(lastGiven == 1){
        assert(s == "b");
        assert(b == true);
    }else if(lastGiven == 2){
        assert(s == "b");
        assert(b == false);
    }
}

shared void defaultedParams2(Boolean set, Integer a = 1, Integer b = 2, Integer c = 3, Integer d = 4){
    if(set){
        assert(a == -1);
        assert(b == -2);
        assert(c == -3);
        assert(d == -4);
    }else{
        assert(a == 1);
        assert(b == 2);
        assert(c == 3);
        assert(d == 4);
    }
}

shared void variadicParams(Integer count = 0, String* strings){
    assert(count == strings.size);
    for(s in strings){
        assert(s == "a");
    }
}

shared class VariadicParams(Integer count = 0, String* strings){
    assert(count == strings.size);
    for(s in strings){
        assert(s == "a");
    }
}
        
shared T typeParams<T>(T s, Integer i)
    given T satisfies Object {
    
    assert(s == "a");
    assert(i == 1);
    
    // check that our reified T got passed correctly
    assert(is TypeParams<String> t = TypeParams<T>(s, i));
    
    return s;
}

shared String getString() => "a";
shared Integer getInteger() => 1;
shared Float getFloat() => 1.2;
shared Character getCharacter() => 'a';
shared Boolean getBoolean() => true;
shared Object getObject() => 2;

shared NoParams getAndTakeNoParams(NoParams o) => o;

shared String toplevelWithMultipleParameterLists(Integer i)(String s) => s + i.string;

shared class ContainerClass(){
    shared class InnerClass(){}
    shared class DefaultedParams(Integer expected, Integer toCheck = 0){
        assert(expected == toCheck);
    }
    shared interface InnerInterface {}
    shared interface InnerInterface2 {}
    shared class InnerSubClass() extends InnerClass() satisfies InnerInterface {}
}

shared class ParameterisedContainerClass<Outer>(){
    shared class InnerClass<Inner>(){}
    shared interface InnerInterface<Inner>{}
}

shared interface ContainerInterface{
    shared class InnerClass(){}
}

shared class ContainerInterfaceImpl() satisfies ContainerInterface {}

shared alias TypeAliasToClass => NoParams;

shared alias TypeAliasToClassTP<J>
    given J satisfies Object
    => TypeParams<J>;

shared alias TypeAliasToUnion => Integer | String;

shared alias TypeAliasToMemberAndTopLevel => TPA & ContainerInterface.InnerClass;

shared interface TPA {}
shared interface TPB {}

shared class TP1() satisfies TPA & TPB {}
shared class TP2() satisfies TPA & TPB {}

shared class TypeParameterTest<P, in T = P, out V = Integer>()
    given P of TP1 | TP2 satisfies TPA & TPB {}

shared interface InterfaceWithCaseTypes of iwcta | iwctb {}
shared object iwcta satisfies InterfaceWithCaseTypes {}
shared object iwctb satisfies InterfaceWithCaseTypes {}

shared T typeParameterTest<T>() => nothing;

shared interface InterfaceWithSelfType<T> of T given T satisfies InterfaceWithSelfType<T>{}

shared abstract class Modifiers(){
    class NonShared(){}
    shared formal Boolean method();
    shared actual default String string = "yup";
    shared class Private2() {}
}
shared abstract class SubModifiers() extends Modifiers() {
    class SubPrivate(){}
}

shared final class Final(){}

shared class MyException() extends Exception("my exception"){}

shared class ThrowsMyException(Boolean t){
    if(t){
        throw MyException();
    }
    shared Integer getter {
        throw MyException();
    }
    assign getter {
        throw MyException();
    }
    shared Integer method() {
        throw MyException();
    }
}

shared class ThrowsException(Boolean t){
    if(t){
        throw Exception("exception");
    }
    shared Integer getter {
        throw Exception("exception");
    }
    assign getter {
        throw Exception("exception");
    }
    shared Integer method() {
        throw Exception("exception");
    }
}

shared class MyAssertionError() extends AssertionError("my error"){}

shared class ThrowsMyAssertionError(Boolean t){
    if(t){
        throw MyAssertionError();
    }
    shared Integer getter {
        throw MyAssertionError();
    }
    assign getter {
        throw MyAssertionError();
    }
    shared Integer method() {
        throw MyAssertionError();
    }
}

shared class ThrowsThrowable(Boolean t){
    if(t){
        throw (Exception("error") of Throwable);
    }
    shared Integer getter {
        throw (Exception("error") of Throwable);
    }
    assign getter {
        throw (Exception("error") of Throwable);
    }
    shared Integer method() {
        throw (Exception("error") of Throwable);
    }
}

shared class ConstrainedTypeParams<A, B>()
    given A of String | Integer
    given B satisfies TPA {}

shared void constrainedTypeParams<A, B>()
    given A of String | Integer
    given B satisfies TPA {}

shared annotation final class Annot() satisfies OptionalAnnotation<Annot> {}
shared annotation Annot annot() => Annot();

shared interface Top<out A>{
    shared formal A inheritedMethod();
    shared formal A inheritedAttribute;
    shared class InheritedClass(){}
    shared interface InheritedInterface{}
}
shared interface Middle<out A> satisfies Top<A>{
}
shared abstract class MiddleClass<out A>() satisfies Middle<A>{}

shared abstract class BottomClass() extends MiddleClass<Object>() satisfies Middle<String>{
    shared formal String declaredMethod(String s);
    shared formal String declaredAttribute;
    shared class DeclaredClass(){}
    shared interface DeclaredInterface{}
    shared void myOwnBottomMethod(){}
}

class MemberObjectContainer<T>(){
    shared object memberObject {
        shared Integer attribute = 2;
        shared T method<T>(T t) => t;
    }
    shared void test(){
        assert (`memberObject`.declaration==`value memberObject`);
        assert (`memberObject`.declaration.container==`class MemberObjectContainer`);
        assert(`memberObject`(this).get()==memberObject);
        
        assert(`value memberObject.attribute`.name == "attribute");
        assert(is ClassDeclaration memberObjectDecl = `value memberObject.attribute`.container);
        assert(memberObjectDecl == `class memberObject`);
        assert(is ClassDeclaration fooDecl = memberObjectDecl.container);
        assert(fooDecl == `class MemberObjectContainer`);
        
        // Commented until https://github.com/ceylon/ceylon-spec/issues/1162 is cleared up
        //assert(`memberObject.attribute`.declaration == `value memberObject.attribute`);
        //assert(`memberObject.attribute`(this).get() == 2);
        //assert(is MemberClass<MemberObjectContainer<T>,Basic,Nothing> memberObjectClass = `memberObject.attribute`.container);
        //assert(is Class<MemberObjectContainer<T>,[]> fooClass = memberObjectClass.container);
        //assert(fooClass == `MemberObjectContainer<T>`);
        
        assert(`function memberObject.method`.name == "method");
        assert(is ClassDeclaration memberObjectDecl2 = `function memberObject.method`.container);
        assert(memberObjectDecl2 == `class memberObject`);
        assert(is ClassDeclaration fooDecl2 = memberObjectDecl2.container);
        assert(fooDecl2 == `class MemberObjectContainer`);
        
        // Commented until https://github.com/ceylon/ceylon-spec/issues/1162 is cleared up
        //assert(`memberObject.method<Integer>`.declaration == `function memberObject.method`);
        //assert(`memberObject.method<Integer>`(this)(3) == 3);
        //assert(is MemberClass<MemberObjectContainer<T>,Basic,Nothing> memberObjectClass2 = `memberObject.method<Integer>`.container);
        //assert(is Class<MemberObjectContainer<T>,[]> fooClass2 = memberObjectClass2.container);
        //assert(fooClass2 == `MemberObjectContainer<T>`);
        
    }
}

shared object obj {
    shared Integer attribute = 2;
    shared T method<T>(T t) => t;
}

shared class Constructors<T> {
    shared Anything arg;
    shared new (T? t=null){
        arg = t;
    }
    shared new Other(Integer i){
        arg = i;
    }
    new NonShared(Boolean b){
        arg = b;
    }
    shared class Member {
        shared new (T? t=null) {}
        shared new Other(Integer i) {}
        new NonShared(Boolean b) {}
        shared MemberClassConstructor<Constructors<T>, Member, [Boolean]> nonShared => `NonShared`;
        shared ConstructorDeclaration nonSharedDecl => `new NonShared`;
    }
    class NonSharedMember {
        shared new (T? t=null) {}
        shared new Other(Integer i) {}
        new NonShared(Boolean b) {}
        shared MemberClassConstructor<Constructors<T>, NonSharedMember, [Boolean]> nonShared => `NonShared`;
        shared ConstructorDeclaration nonSharedDecl => `new NonShared`;
    }
    shared void test() {
        testDeclarations();
        testModels();
        testMemberModels();
    }
    
    shared void testMemberModels() {
        // TODO test constructors of member classes of interfaces
        value member = Member();
        print(type(`Member`.getConstructor<[T?]|[]>("")));
        assert(is MemberClassConstructor<Constructors<T>,Member,[T?]|[]> memberMember = `Member`.getConstructor<[T?]|[]>(""));
        value memberOther = `Member.Other`;
        value memberNonShared = member.nonShared;
        
        value nonSharedMember = NonSharedMember();
        assert(exists nonSharedMemberMember = `NonSharedMember`.getConstructor<[T?]|[]>(""));
        value nonSharedMemberOther = `NonSharedMember.Other`;
        value nonSharedMemberNonShared = nonSharedMember.nonShared;
        
        // declaration
        assert(`new Member` == memberMember.declaration);
        assert(`new Member.Other` == memberOther.declaration);
        assert(member.nonSharedDecl == memberNonShared.declaration);
        //containers
        //assert(type(member) == memberMember.container);
        //assert(type(member) == memberOther.container);
        //assert(type(member) == memberNonShared.container);
        
        // parameterTypes
        assert(memberMember.parameterTypes.size==1);
        assert(exists t = memberMember.parameterTypes[0],
            `String`.union(`Null`) == t);
        assert(memberOther.parameterTypes.size==1);
        assert(exists i = memberOther.parameterTypes[0],
            `Integer` == i);
        assert(memberNonShared.parameterTypes.size==1);
        assert(exists b = memberNonShared.parameterTypes[0],
            `Boolean` == b);
        
        // call
        Constructor<Member, []|[T?]> memberMemberCtor = memberMember(this);
        memberMemberCtor();
        assert(is T tt = "");
        memberMemberCtor(tt);
        Constructor<Member, [Integer]> memberOtherCtor = memberOther(this);
        memberOtherCtor(1);
        Constructor<Member, [Boolean]> memberNonSharedCtor = memberNonShared(this);
        memberNonSharedCtor(true);
        
        Constructor<NonSharedMember, []|[T?]> nonSharedMemberMemberCtor = nonSharedMemberMember(this);
        nonSharedMemberMemberCtor();
        nonSharedMemberMemberCtor(tt);
        Constructor<NonSharedMember, [Integer]> nonSharedMemberOtherCtor = nonSharedMemberOther(this);
        nonSharedMemberOtherCtor(1);
        Constructor<NonSharedMember, [Boolean]> nonSharedMemberNonSharedCtor = nonSharedMemberNonShared(this);
        nonSharedMemberNonSharedCtor(true);

        // bind
        memberMember.bind(this)();
        memberMember.bind(this)(tt);
        memberOther.bind(this)(1);
        memberNonShared.bind(this)(true);
        
        nonSharedMemberMember.bind(this)();
        nonSharedMemberMember.bind(this)(tt);
        nonSharedMemberOther.bind(this)(1);
        nonSharedMemberNonShared.bind(this)(true);
    }
    shared void testModels() {
        assert(exists def = `Constructors<T>`.getConstructor<[T?]|[]>(""));
        value other = `Other`;
        value nonShared = `NonShared`;
        
        // declaration
        assert(`new Constructors` == def.declaration);
        assert(`new Other` == other.declaration);
        assert(`new NonShared` == nonShared.declaration);
        //container
        //assert(type(this) == def.container);
        //assert(type(this) == other.container);
        //assert(type(this) == nonShared.container);
        // parameterTypes
        assert(def.parameterTypes.size==1);
        assert(exists t = def.parameterTypes[0],
            `String`.union(`Null`) == t);
        assert(other.parameterTypes.size==1);
        assert(exists i = other.parameterTypes[0],
            `Integer` == i);
        assert(nonShared.parameterTypes.size==1);
        assert(exists b = nonShared.parameterTypes[0],
            `Boolean` == b);
        // call
        "calling Constructor model of default constructor with defaulted argument" 
        assert(! def().arg exists);
        "calling Constructor model of default constructor with given argument"
        assert(is T a = "",
            exists a1 = def(a).arg,
            "" == a1);
        "calling Constructor model of non-default constructor"
        assert(exists a2 = other(1).arg, 
            1==a2);
        "calling Constructor model of non-shared, non-default constructor"
        assert(exists a3 = nonShared(true).arg, 
            a3==true);
        
        // apply
        //Anything xx = def.apply();
        "apply()ing Constructor model of default constructor with defaulted argument"
        assert(is Constructors<String> x1 = def.apply(),
            ! x1.arg exists);
        //assert(! def.apply().arg exists); TODO enable when https://github.com/ceylon/ceylon-compiler/issues/1928 is fixed
        assert(is Constructors<String> x2 = def.apply(""),
            exists y2 = x2.arg,
            "" == y2);
        try {
            def.apply("", "");
            throw;
        } catch (InvocationException e) {
        }
        
        //assert(is Constructors<String> x3 = other.apply(),
        //    ! x3.arg exists);
        assert(is Constructors<String> x4 = other.apply(1),
            exists y4 = x4.arg,
            1 == y4);
        try {
            other.apply(1, "");
            throw;
        } catch (InvocationException e) {
        }
        try {
            other.apply("");
            throw;
        } catch (IncompatibleTypeException e) {
        }
        
        //assert(is Constructors<String> x5 = nonShared.apply(),
            //! x5.arg exists);
        assert(is Constructors<String> x6 = nonShared.apply(true),
            exists y6 = x6.arg,
            true == y6);
        try {
            nonShared.apply(true, "");
            throw;
        } catch (InvocationException e) {
        }
        try {
            nonShared.apply("");
            throw;
        } catch (IncompatibleTypeException e) {
        }
        
        // TODO namedApply()
    }
    
    shared void testDeclarations() {
        value def = `new Constructors`;
        value other = `new Other`;
        value nonShared = `new NonShared`;
        
        assert(def.defaultConstructor);
        assert(!other.defaultConstructor);
        assert(!nonShared.defaultConstructor);
        
        assert("" == def.name);
        assert("Other" == other.name);
        assert("NonShared" == nonShared.name);
        
        assert("metamodel::Constructors" == def.qualifiedName);
        assert("metamodel::Constructors.Other" == other.qualifiedName);
        assert("metamodel::Constructors.NonShared" == nonShared.qualifiedName);
        
        assert(!def.annotation);
        assert(!other.annotation);
        assert(!nonShared.annotation);
        
        assert(def.shared);
        assert(other.shared);
        assert(!nonShared.shared);
        
        assert(def.shared);
        assert(other.shared);
        assert(!nonShared.shared);
        
        assert(is ClassDeclaration cls = `package`.getClassOrInterface("Constructors"));
        
        assert(cls == def.container);
        assert(cls == other.container);
        assert(cls == nonShared.container);
        
        assert(cls.openType == def.openType);
        assert(cls.openType == other.openType);
        assert(cls.openType == nonShared.openType);
        
        //parameters
        assert(1 == def.parameterDeclarations.size);
        assert(exists def1 = def.parameterDeclarations.first);
        assert(def1.name == "t");
        assert(exists def12 = def.getParameterDeclaration("t"));
        assert(def12 == def1);
        
        assert(1 == other.parameterDeclarations.size);
        assert(exists other1 = other.parameterDeclarations.first);
        assert(other1.name == "i");
        assert(exists other12 = other.getParameterDeclaration("i"));
        assert(other12 == other1);
        
        assert(1 == nonShared.parameterDeclarations.size);
        assert(exists nonShared1 = nonShared.parameterDeclarations.first);
        assert(nonShared1.name == "b");
        assert(exists nonShared12 = nonShared.getParameterDeclaration("b"));
        assert(nonShared12 == nonShared1);
        
        assert(!def.annotations<SharedAnnotation>().empty);
        assert(!other.annotations<SharedAnnotation>().empty);
        assert(nonShared.annotations<SharedAnnotation>().empty);
        
        assert(exists c1 = cls.getConstructorDeclaration(""),
            def == c1);
        assert(exists c2 = cls.getConstructorDeclaration("Other"),
            other == c2);
        assert(exists c3 = cls.getConstructorDeclaration("NonShared"),
            nonShared == c3);
        
        assert(exists c4 = cls.defaultConstructorDeclaration,
            def == c4);
        
        value ctors = cls.constructorDeclarations();
        assert(ctors.size == 3);
        assert(def in ctors);
        assert(other in ctors);
        assert(nonShared in ctors);
    }
}

shared interface InterfaceConstructors<T> {
    // basically the same as above, but with member classes 
    // of an interface rather than a class
    shared class Member {
        shared new (T? t=null) {
            
        }
        new NonShared(T? t=null) {
            
        }
        shared MemberClassConstructor<InterfaceConstructors<T>, Member, []|[T?]> nonShared => `NonShared`; 
        shared ConstructorDeclaration nonSharedDecl => `new NonShared`;
    }
    class NonSharedMember {
        shared new (T? t=null) {
            
        }
        new NonShared(T? t=null) {
            
        }
        shared MemberClassConstructor<InterfaceConstructors<T>, NonSharedMember, []|[T?]> nonShared => `NonShared`; 
        shared ConstructorDeclaration nonSharedDecl => `new NonShared`;
    }
    shared void test() {
        assert(is T tt = "");
        value memberInst = Member();
        value nonSharedMemberInst = NonSharedMember();
        
        assert(exists mmm = `Member`.getConstructor<[T?]|[]>(""));
        assert(exists nsm = `NonSharedMember`.getConstructor<[T?]|[]>(""));
        
        assert(`new Member` == mmm.declaration);
        assert(memberInst.nonSharedDecl == memberInst.nonShared.declaration);
        assert(`new NonSharedMember` == nsm.declaration);
        assert(nonSharedMemberInst.nonSharedDecl == nonSharedMemberInst.nonShared.declaration);
    
        mmm(this)();
        mmm(this)(tt);
        memberInst.nonShared(this)();
        memberInst.nonShared(this)(tt);
        nsm(this)();
        nsm(this)(tt);
        nonSharedMemberInst.nonShared(this)();
        nonSharedMemberInst.nonShared(this)(tt);
    }
}
class ClassWithInitializer(String s) {
    
}
class ClassWithDefaultConstructor {
    shared new (String s) {
        
    }
}
class ClassWithNonDefaultConstructor {
    shared new New(String s) {
    }
}
class UninstantiableClass {
    shared new New(String s) {
    }
}
class ClassConstructorsOfEveryArity {
    shared new Fixed0() {}
    shared new Fixed1(String s1) {}
    shared new Fixed2(String s1, String s2) {}
    shared new Fixed3(String s1, String s2, String s3) {}
    shared new Fixed4(String s1, String s2, String s3, String s4) {}
    shared new Fixed5(String s1, String s2, String s3, String s4, String s5) {}
    
    shared new Star1(String* s1) {}
    shared new Star2(String s1, String* s2) {}
    shared new Star3(String s1, String s2, String* s3) {}
    shared new Star4(String s1, String s2, String s3, String* s4) {}
    shared new Star5(String s1, String s2, String s3, String s4, String* s5) {}
    
    shared new Plus1(String+ s1) {}
    shared new Plus2(String s1, String+ s2) {}
    shared new Plus3(String s1, String s2, String+ s3) {}
    shared new Plus4(String s1, String s2, String s3, String+ s4) {}
    shared new Plus5(String s1, String s2, String s3, String s4, String+ s5) {}
}
