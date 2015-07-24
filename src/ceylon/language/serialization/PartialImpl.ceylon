class PartialImpl(Object id) extends Partial(id) {

  shared actual void instantiate() {}
  shared actual void initialize<Id>(DeserializationContextImpl<Id> context)
      given Id satisfies Object {}
}
