import ceylon.language.meta.model{
    Type, 
    CallableConstructor,
    MemberClassCallableConstructor
}

"""Declaration model for callable constructors, for example
 
       class WithConstructors {
           shared new () {}
           shared new clone(WithConstructors other) {}
       
       // ...
       
       CallableConstructorDeclaration default = `new WithConstructors`;
       CallableConstructorDeclaration clone = `new WithConstructors.clone`;
       
   The initializer of a class with a parameter list can also be 
   [[represented|ClassWithInitializerDeclaration.defaultConstructor]] 
   as a `CallableConstructorDeclaration`.
"""
see (`interface ValueConstructorDeclaration`)
shared sealed interface CallableConstructorDeclaration 
        satisfies FunctionalDeclaration & ConstructorDeclaration {
    
    "True if the constructor has an [[abstract|ceylon.language::abstract]] annotation."
    shared formal Boolean abstract;
    
    "Whether this is the default constructor. The default constructor of a class is the constructor with no name."
    shared formal Boolean defaultConstructor;
    
    "The class this constructor constructs"
    shared actual formal ClassDeclaration container;
    
    shared actual formal Object invoke(Type<>[] typeArguments, Anything* arguments);
    
    shared actual formal Object memberInvoke
            (Object container, Type<>[] typeArguments, Anything* arguments);
    
    "Applies the given closed type arguments to the declaration of the class 
     enclosing this constructor declaration, returning a function model 
     for the constructor"
    shared actual formal CallableConstructor<Result,Arguments> apply
            <Result=Object,Arguments=Nothing>
            (Type<>* typeArguments)
                given Arguments satisfies Anything[];
    
    "Applies the given closed type arguments to the declaration of the member class 
     enclosing this constructor declaration, returning a method model 
     for the constructor"
    shared actual formal MemberClassCallableConstructor<Container,Result,Arguments> memberApply
            <Container=Nothing,Result=Object,Arguments=Nothing>
            (Type<Object> containerType, Type<>* typeArguments)
                given Arguments satisfies Anything[];
}