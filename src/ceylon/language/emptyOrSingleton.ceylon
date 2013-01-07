doc "A `Singleton` if the given element is non-null, otherwise `Empty`."
see(Singleton, Empty)
shared Element[] emptyOrSingleton<Element>(Element? element) 
        given Element satisfies Value {
    if (exists element) {
        return Singleton(element);
    }
    else {
        return {};
    }
}