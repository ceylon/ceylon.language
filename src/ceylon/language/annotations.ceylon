import ceylon.language.model {
    SequencedAnnotation, OptionalAnnotation, 
    Annotated }
import ceylon.language.model.declaration {
    Module, Package, Import,
    ClassDeclaration,
    ClassOrInterfaceDeclaration,
    InterfaceDeclaration,
    FunctionDeclaration,
    Declaration,
    ValueDeclaration} 

"The annotation class for [[annotation]]."
shared final annotation class Annotation()
        satisfies OptionalAnnotation<Annotation, ClassDeclaration|FunctionDeclaration> {}

"Annotation to mark a class as an annotation class, or a 
 method as an annotation method."
shared annotation Annotation annotation() => Annotation(); 

"The annotation class for [[shared]]."
shared final annotation class Shared()
        satisfies OptionalAnnotation<Shared, ValueDeclaration|FunctionDeclaration|ClassOrInterfaceDeclaration|Package|Import> {}

"Annotation to mark a type or member as shared. A `shared` 
 member is visible outside the block of code in which it is 
 declared."
shared annotation Shared shared() => Shared();

"The annotation class for [[variable]]."
shared final annotation class Variable()
        satisfies OptionalAnnotation<Variable, ValueDeclaration> {}

"Annotation to mark an value as variable. A `variable` value 
 may be assigned multiple times."
shared annotation Variable variable() => Variable();

"The annotation class for [[abstract]]."
shared final annotation class Abstract()
        satisfies OptionalAnnotation<Abstract, ClassDeclaration> {}

"Annotation to mark a class as abstract. An `abstract` class 
 may not be directly instantiated. An `abstract` class may 
 have enumerated cases."
shared annotation Abstract abstract() => Abstract();

"The annotation class for [[final]]."
shared final annotation class Final()
        satisfies OptionalAnnotation<Final, ClassDeclaration> {}

"Annotation to mark a class as final. A `final` class may 
 not be extended. Marking a class as final affects disjoint
 type analysis."
shared annotation Final final() => Final();

"The annotation class for [[actual]]."
shared final annotation class Actual()
        satisfies OptionalAnnotation<Actual, ValueDeclaration|FunctionDeclaration|ClassOrInterfaceDeclaration> {}

"Annotation to mark a member of a type as refining a member 
 of a supertype."
shared annotation Actual actual() => Actual();

"The annotation class for [[formal]]."
shared final annotation class Formal()
        satisfies OptionalAnnotation<Formal, ValueDeclaration|FunctionDeclaration|ClassOrInterfaceDeclaration> {}

"Annotation to mark a member whose implementation must be 
 provided by subtypes."  
shared annotation Formal formal() => Formal();

"The annotation class for [[default]]."
shared final annotation class Default()
        satisfies OptionalAnnotation<Default, ValueDeclaration|FunctionDeclaration|ClassOrInterfaceDeclaration> {}

"Annotation to mark a member whose implementation may be 
 refined by subtypes. Non-`default` declarations may not be 
 refined."
shared annotation Default default() => Default();

"The annotation class for [[late]]."
shared final annotation class Late()
        satisfies OptionalAnnotation<Late, ValueDeclaration> {}

"Annotation to disable definite initialization analysis for 
 a reference."  
shared annotation Late late() => Late();

"The annotation class for [[native]]."
shared final annotation class Native()
        satisfies OptionalAnnotation<Native, Annotated> {}

"Annotation to mark a member whose implementation is defined 
 in platform-native code."  
shared annotation Native native() => Native();

shared final annotation class Doc(shared String description)
        satisfies OptionalAnnotation<Doc, Annotated> {}

"Annotation to specify API documentation of a program
 element." 
shared annotation Doc doc(String description) => Doc(description);

shared final annotation class See(shared Declaration* programElements)
        satisfies SequencedAnnotation<See, Annotated> {}

"Annotation to specify API references to other related 
 program elements."
shared annotation See see(Declaration* programElements) => See(*programElements);

"The annotation class for [[by]]."
shared final annotation class Authors(shared String* authors)
        satisfies OptionalAnnotation<Authors, Annotated> {}

"Annotation to specify API authors."
shared annotation Authors by(String* authors) => Authors(*authors);

shared final annotation class ThrownException(shared Declaration type, shared String when) 
        satisfies SequencedAnnotation<ThrownException, ValueDeclaration|FunctionDeclaration|ClassDeclaration> {}

"Annotation to mark a program element that throws an 
 exception."
shared annotation ThrownException throws(Declaration type, String when="") => ThrownException(type, when);

"The annotation class for [[deprecated]]."
shared final annotation class Deprecation(shared String description)
        satisfies OptionalAnnotation<Deprecation, Annotated> {
    shared String? reason {
        if (description.empty) {
            return null;
        }
        return description;
    }
}

"Annotation to mark program elements which should not be 
 used anymore."
shared annotation Deprecation deprecated(String reason="") => Deprecation(reason);

"The annotation class for [[tagged]]."
shared final annotation class Tags(shared String* tags)
        satisfies OptionalAnnotation<Tags, Annotated> {}

"Annotation to categorize the API by tag." 
shared annotation Tags tagged(String* tags) => Tags(*tags);

"The annotation class for [[license]]."
shared final annotation class License(shared String url) 
        satisfies OptionalAnnotation<License, Module> {}

"Annotation to specify the URL of the license of a module or 
 package." 
shared annotation License license(String url) => License(url);

"The annotation class for [[optional]]."
shared final annotation class Optional() 
        satisfies OptionalAnnotation<Optional, Import> {}

"Annotation to specify that a module can be executed even if 
 the annotated dependency is not available."
shared annotation Optional optional() => Optional();

