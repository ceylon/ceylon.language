import ceylon.language.meta.declaration {
    ClassOrInterfaceDeclaration
}

import ceylon.language.meta.model {
    ClosedType = Type
}

"Model of a class or interface that you can inspect.
 
 The models of classes and interfaces are also closed types."
shared sealed interface ClassOrInterface<out Type=Anything> 
    of ClassModel<Type, Nothing> | InterfaceModel<Type>
    satisfies Model & Generic & ClosedType<Type> {
    
    "The declaration for this class or interface."
    shared formal actual ClassOrInterfaceDeclaration declaration;

    "The extended closed type for this class or interface. Note that the [[Anything|ceylon.language::Anything]] type
     has no extended type since it is the top of the type hierarchy."
    shared formal ClassModel<>? extendedType;
    
    "The list of closed types that this class or interface satisfies."
    shared formal InterfaceModel<>[] satisfiedTypes;

    "The list of case values for this type. This omits any case type to only contain case values."
    shared formal Type[] caseValues;

    // FIXME: move all these to Type
    // FIXME: introduce MemberClassOrInterface?
    // if I do that I have to give up the enumerated type of ClassModel | InterfaceModel here, so let's not do that for now,
    // since I don't quite see what we would gain

    "Gets a member class or interface by name. Returns `null` if not found."
    throws(`class IncompatibleTypeException`, "If the specified `Container` or `Kind` type arguments are not compatible with the actual result.")
    throws(`class TypeApplicationException`, "If the specified closed type argument values are not compatible with the actual result's type parameters.")
    shared formal Member<Container, Kind>? getClassOrInterface<Container=Nothing, Kind=ClassOrInterface<>>(String name, ClosedType<Anything>* types)
        given Kind satisfies ClassOrInterface<Anything>;

    "Gets a member class or interface by name. Returns `null` if not found."
    throws(`class IncompatibleTypeException`, "If the specified `Container` or `Kind` type arguments are not compatible with the actual result.")
    throws(`class TypeApplicationException`, "If the specified closed type argument values are not compatible with the actual result's type parameters.")
    shared formal Member<Container, Kind>? getDeclaredClassOrInterface<Container=Nothing, Kind=ClassOrInterface<>>(String name, ClosedType<Anything>* types)
        given Kind satisfies ClassOrInterface<Anything>;

    "Gets a member class by name. Returns `null` if not found."
    throws(`class IncompatibleTypeException`, "If the specified `Container`, `Type` or `Arguments` type arguments are not compatible with the actual result, 
                                               or if the corresponding member is not a `MemberClass`.")
    throws(`class TypeApplicationException`, "If the specified closed type argument values are not compatible with the actual result's type parameters.")
    shared formal MemberClass<Container, Type, Arguments>? getClass<Container=Nothing, Type=Anything, Arguments=Nothing>(String name, ClosedType<Anything>* types)
        given Arguments satisfies Anything[];

    "Gets a member class by name. Returns `null` if not found."
    throws(`class IncompatibleTypeException`, "If the specified `Container`, `Type` or `Arguments` type arguments are not compatible with the actual result, 
                                               or if the corresponding member is not a `MemberClass`.")
    throws(`class TypeApplicationException`, "If the specified closed type argument values are not compatible with the actual result's type parameters.")
    shared formal MemberClass<Container, Type, Arguments>? getDeclaredClass<Container=Nothing, Type=Anything, Arguments=Nothing>(String name, ClosedType<Anything>* types)
        given Arguments satisfies Anything[];

    "Gets a member interface by name. Returns `null` if not found."
    throws(`class IncompatibleTypeException`, "If the specified `Container` or `Type` type arguments are not compatible with the actual result, 
                                               or if the corresponding member is not a `MemberInterface`.")
    throws(`class TypeApplicationException`, "If the specified closed type argument values are not compatible with the actual result's type parameters.")
    shared formal MemberInterface<Container, Type>? getInterface<Container=Nothing, Type=Anything>(String name, ClosedType<Anything>* types);
    
    "Gets a member interface by name. Returns `null` if not found."
    throws(`class IncompatibleTypeException`, "If the specified `Container` or `Type` type arguments are not compatible with the actual result, 
                                               or if the corresponding member is not a `MemberInterface`.")
    throws(`class TypeApplicationException`, "If the specified closed type argument values are not compatible with the actual result's type parameters.")
    shared formal MemberInterface<Container, Type>? getDeclaredInterface<Container=Nothing, Type=Anything>(String name, ClosedType<Anything>* types);
    
    "Gets a method by name. Returns `null` if not found."
    throws(`class IncompatibleTypeException`, "If the specified `Container`, `Type` or `Arguments` type arguments are not compatible with the actual result.")
    throws(`class TypeApplicationException`, "If the specified closed type argument values are not compatible with the actual result's type parameters.")
    shared formal Method<Container, Type, Arguments>? getMethod<Container=Nothing, Type=Anything, Arguments=Nothing>(String name, ClosedType<Anything>* types)
        given Arguments satisfies Anything[];

    "Gets a method by name. Returns `null` if not found."
    throws(`class IncompatibleTypeException`, "If the specified `Container`, `Type` or `Arguments` type arguments are not compatible with the actual result.")
    throws(`class TypeApplicationException`, "If the specified closed type argument values are not compatible with the actual result's type parameters.")
    shared formal Method<Container, Type, Arguments>? getDeclaredMethod<Container=Nothing, Type=Anything, Arguments=Nothing>(String name, ClosedType<Anything>* types)
        given Arguments satisfies Anything[];
    
    "Gets an attribute by name. Returns `null` if not found."
    throws(`class IncompatibleTypeException`, "If the specified `Container`, `Get` or `Set` type arguments are not compatible with the actual result.")
    shared formal Attribute<Container, Get, Set>? getAttribute<Container=Nothing, Get=Anything, Set=Nothing>(String name);

    "Gets an attribute by name. Returns `null` if not found."
    throws(`class IncompatibleTypeException`, "If the specified `Container`, `Get` or `Set` type arguments are not compatible with the actual result.")
    shared formal Attribute<Container, Get, Set>? getDeclaredAttribute<Container=Nothing, Get=Anything, Set=Nothing>(String name);

    "Gets a list of attributes matching the given container and attribute type, annotated with all the
     specified annotations, which are directly declared on this type."
    shared formal Attribute<Container, Get, Set>[] getDeclaredAttributes<Container=Nothing, Get=Anything, Set=Nothing>(ClosedType<Annotation>* annotationTypes);

    "Gets a list of attributes matching the given container and attribute type, annotated with all the
     specified annotations, which are declared on this type or inherited."
    shared formal Attribute<Container, Get, Set>[] getAttributes<Container=Nothing, Get=Anything, Set=Nothing>(ClosedType<Annotation>* annotationTypes);

    "Gets a list of methods matching the given container, return and parameter types, annotated with all the
     specified annotations, which are directly declared on this type."
    shared formal Method<Container, Type, Arguments>[] getDeclaredMethods<Container=Nothing, Type=Anything, Arguments=Nothing>(ClosedType<Annotation>* annotationTypes)
        given Arguments satisfies Anything[];

    "Gets a list of methods matching the given container, return and parameter types, annotated with all the
     specified annotations, which are declared on this type or inherited."
    shared formal Method<Container, Type, Arguments>[] getMethods<Container=Nothing, Type=Anything, Arguments=Nothing>(ClosedType<Annotation>* annotationTypes)
            given Arguments satisfies Anything[];

    "Gets a list of member classes matching the given container, return and parameter types, annotated with all the
     specified annotations, which are directly declared on this type."
    shared formal MemberClass<Container, Type, Arguments>[] getDeclaredClasses<Container=Nothing, Type=Anything, Arguments=Nothing>(ClosedType<Annotation>* annotationTypes)
            given Arguments satisfies Anything[];
    
    "Gets a list of member classes matching the given container, return and parameter types, annotated with all the
     specified annotations, which are declared on this type or inherited."
    shared formal MemberClass<Container, Type, Arguments>[] getClasses<Container=Nothing, Type=Anything, Arguments=Nothing>(ClosedType<Annotation>* annotationTypes)
            given Arguments satisfies Anything[];

    "Gets a list of interfaces matching the given container and interface types, annotated with all the
     specified annotations, which are directly declared on this type."
    shared formal MemberInterface<Container, Type>[] getDeclaredInterfaces<Container=Nothing, Type=Anything>(ClosedType<Annotation>* annotationTypes);
    
    "Gets a list of interfaces matching the given container and interface types, annotated with all the
     specified annotations, which are declared on this type or inherited."
    shared formal MemberInterface<Container, Type>[] getInterfaces<Container=Nothing, Type=Anything>(ClosedType<Annotation>* annotationTypes);
}

