"An open class or interface, with open type arguments.
 
 For example, `List<T>` is an open interface type, with a type argument which is the
 [[OpenTypeVariable]] `T`."
shared sealed interface OpenClassOrInterfaceType
    of OpenClassType | OpenInterfaceType
    satisfies OpenType {
    
    "The class or interface declaration for this open type."
    shared formal ClassOrInterfaceDeclaration declaration;
    
    "The extended type of this open type."
    shared formal OpenClassType? extendedType;
    
    "The satisfied types of this open type."
    shared formal OpenInterfaceType[] satisfiedTypes;

    "The map of open type arguments."
    shared formal Map<TypeParameter, OpenType> typeArguments;

    "The list of open type arguments."
    shared formal OpenType[] typeArgumentList;

    "The map of type parameter declaration to open type arguments and use-site variance."
    shared formal Map<TypeParameter, OpenTypeArgument> typeArgumentWithVariances;

    "The list of open type arguments with use-site variance."
    shared formal OpenTypeArgument[] typeArgumentWithVarianceList;

    // FIXME: pretty sure we're missing an optional container type here
}
