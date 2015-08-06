import ceylon.language.meta.declaration{FunctionDeclaration}

"""A function model represents the model of a Ceylon function that you can invoke and inspect.
   
   A method is a member function: it is declared on classes or interfaces.
   
   This is both a [[FunctionModel]] and a [[Member]]: you can invoke it with an instance value
   to bind it to that instance and obtain a [[Function]]:
   
       class Outer(){
           shared String foo(String name) => "Hello "+name;
       }
       
       void test(){
           Method<Outer,String,[String]> method = `Outer.foo`;
           // Bind it to an instance value
           Function<String,[String]> f = method(Outer());
           // This will print: Hello Stef
           print(f("Stef"));
       }
 """
shared sealed interface Method<in Container=Nothing, out Type=Anything, in Arguments=Nothing>
        satisfies FunctionModel<Type, Arguments> & Member<Container, Function<Type, Arguments>>
        given Arguments satisfies Anything[] {

    "This function's declaration."
    shared formal actual FunctionDeclaration declaration;

    shared actual formal Function<Type, Arguments> bind(Object container);
}
