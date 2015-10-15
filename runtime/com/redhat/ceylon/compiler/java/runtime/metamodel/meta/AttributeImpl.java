package com.redhat.ceylon.compiler.java.runtime.metamodel.meta;

import ceylon.language.Sequential;
import ceylon.language.empty_;
import ceylon.language.meta.declaration.ValueDeclaration;
import ceylon.language.meta.model.Value;

import com.redhat.ceylon.compiler.java.metadata.Ceylon;
import com.redhat.ceylon.compiler.java.metadata.Ignore;
import com.redhat.ceylon.compiler.java.metadata.Name;
import com.redhat.ceylon.compiler.java.metadata.TypeInfo;
import com.redhat.ceylon.compiler.java.metadata.TypeParameter;
import com.redhat.ceylon.compiler.java.metadata.TypeParameters;
import com.redhat.ceylon.compiler.java.metadata.Variance;
import com.redhat.ceylon.compiler.java.runtime.metamodel.Metamodel;
import com.redhat.ceylon.compiler.java.runtime.metamodel.decl.ValueDeclarationImpl;
import com.redhat.ceylon.compiler.java.runtime.model.TypeDescriptor;
import com.redhat.ceylon.model.typechecker.model.TypedReference;

@Ceylon(major = 8)
@com.redhat.ceylon.compiler.java.metadata.Class
@TypeParameters({
    @TypeParameter(value = "Container", variance = Variance.IN),
    @TypeParameter(value = "Get", variance = Variance.OUT),
    @TypeParameter(value = "Set", variance = Variance.IN),
})
public class AttributeImpl<Container, Get, Set> 
    extends MemberImpl<Container, ceylon.language.meta.model.Value<? extends Get, ? super Set>>
    implements ceylon.language.meta.model.Attribute<Container, Get, Set> {

    protected final ValueDeclarationImpl declaration;
    protected final TypedReference typedReference;
    private final ceylon.language.meta.model.Type<? extends Get> closedType;
    @Ignore
    protected final TypeDescriptor $reifiedGet;
    @Ignore
    protected final TypeDescriptor $reifiedSet;
    
    public AttributeImpl(@Ignore TypeDescriptor $reifiedContainer, 
                            @Ignore TypeDescriptor $reifiedGet,
                            @Ignore TypeDescriptor $reifiedSet,
                            ValueDeclarationImpl declaration, TypedReference typedReference,
                            ceylon.language.meta.model.Type<? extends Object> container) {
        super($reifiedContainer, TypeDescriptor.klass(ceylon.language.meta.model.Value.class, $reifiedGet, $reifiedSet), container);
        this.declaration = declaration;
        this.typedReference = typedReference;
        this.closedType = Metamodel.getAppliedMetamodel(typedReference.getType());
        this.$reifiedGet = $reifiedGet;
        this.$reifiedSet = $reifiedSet;
    }

    @Override
    @TypeInfo("ceylon.language.meta.declaration::ValueDeclaration")
    public ValueDeclaration getDeclaration() {
        return declaration;
    }

    @Override
    @TypeInfo("ceylon.language.meta.model::Type<Get>")
    public ceylon.language.meta.model.Type<? extends Get> getType() {
        return closedType;
    }
    
    @Override
    protected Value<? extends Get, ? super Set> bindTo(Object instance) {
        return new ValueImpl<Get,Set>($reifiedGet, $reifiedSet, declaration, typedReference, getContainer(), instance);
    }

    @Ignore
    @Override
    public TypeDescriptor $getType$() {
        return TypeDescriptor.klass(AttributeImpl.class, super.$reifiedContainer, $reifiedGet, $reifiedSet);
    }

    @Override
    @Ignore
    public Value<? extends Get, ? super Set> $callvariadic$() {
        return $callvariadic$(empty_.get_());
    }
    
    @Override
    @Ignore
    public Value<? extends Get, ? super Set> $callvariadic$(
            Sequential<?> varargs) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Ignore
    public Value<? extends Get, ? super Set> $callvariadic$(
            Object arg0, Sequential<?> varargs) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Ignore
    public Value<? extends Get, ? super Set> $callvariadic$(
            Object arg0, Object arg1, Sequential<?> varargs) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Ignore
    public Value<? extends Get, ? super Set> $callvariadic$(
            Object arg0, Object arg1, Object arg2, Sequential<?> varargs) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Ignore
    public Value<? extends Get, ? super Set> $callvariadic$(Object... argsAndVarargs) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Ignore
    public Value<? extends Get, ? super Set> $callvariadic$(Object arg0) {
        return $callvariadic$(arg0, empty_.get_());
    }

    @Override
    @Ignore
    public Value<? extends Get, ? super Set> $callvariadic$(Object arg0, Object arg1) {
        return $callvariadic$(arg0, arg1, empty_.get_());
    }

    @Override
    @Ignore
    public Value<? extends Get, ? super Set> $callvariadic$(Object arg0, Object arg1,
            Object arg2) {
        return $callvariadic$(arg0, arg1, arg2, empty_.get_());
    }

    @Override
    public Value<? extends Get, ? super Set> bind(@TypeInfo("ceylon.language::Object") @Name("container") java.lang.Object container){
        return (Value<? extends Get, ? super Set>) Metamodel.bind(this, this.typedReference.getQualifyingType(), container);
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 37 * result + getDeclaringType().hashCode();
        result = 37 * result + getDeclaration().hashCode();
        return result;
    }
    
    @Override
    public boolean equals(Object obj) {
        if(obj == null)
            return false;
        if(obj == this)
            return true;
        if(obj instanceof ceylon.language.meta.model.Attribute == false)
            return false;
        ceylon.language.meta.model.Attribute<?,?,?> other = (ceylon.language.meta.model.Attribute<?,?,?>) obj;
        return getDeclaration().equals(other.getDeclaration())
                && getDeclaringType().equals(other.getDeclaringType());
    }


    @Override
    @TypeInfo("ceylon.language.meta.model::Type<ceylon.language::Anything>")
    public ceylon.language.meta.model.Type<?> getContainer(){
        return getDeclaringType();
    }

    @Override
    public String toString() {
        return Metamodel.toTypeString(this);
    }
}
