package com.redhat.ceylon.compiler.java.runtime.metamodel.meta;

import ceylon.language.Sequential;
import ceylon.language.empty_;
import ceylon.language.meta.declaration.InterfaceDeclaration;
import ceylon.language.meta.model.Interface;

import com.redhat.ceylon.compiler.java.metadata.Ignore;
import com.redhat.ceylon.compiler.java.metadata.Name;
import com.redhat.ceylon.compiler.java.metadata.TypeInfo;
import com.redhat.ceylon.compiler.java.runtime.metamodel.Metamodel;
import com.redhat.ceylon.compiler.java.runtime.model.TypeDescriptor;

public class MemberInterfaceImpl<Container, Type> 
    extends ClassOrInterfaceImpl<Type>
    implements ceylon.language.meta.model.MemberInterface<Container, Type> {

    @Ignore
    public final TypeDescriptor $reifiedContainer;

    public MemberInterfaceImpl(@Ignore TypeDescriptor $reifiedContainer,
                           @Ignore TypeDescriptor $reifiedType,
                           com.redhat.ceylon.model.typechecker.model.Type producedType) {
        super($reifiedType, producedType);
        this.$reifiedContainer = $reifiedContainer;
    }

    @Override
    @Ignore
    public Interface<? extends Type> $call$() {
        throw new UnsupportedOperationException();
    }

    @Override
    @Ignore
    public Interface<? extends Type> $call$(Object arg0) {
        return new InterfaceImpl<Type>($reifiedType, super.producedType, getContainer(), arg0);
    }

    @Override
    @Ignore
    public Interface<? extends Type> $call$(Object arg0, Object arg1) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Ignore
    public Interface<? extends Type> $call$(Object arg0, Object arg1, Object arg2) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Ignore
    public Interface<? extends Type> $call$(Object... args) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Ignore
    public short $getVariadicParameterIndex$() {
        return -1;
    }

    @Override
    @TypeInfo("ceylon.language.meta.declaration::InterfaceDeclaration")
    public InterfaceDeclaration getDeclaration() {
        return (InterfaceDeclaration) super.getDeclaration();
    }
    
    @Override
    @TypeInfo("ceylon.language.meta.model::Type<ceylon.language::Anything>")
    public ceylon.language.meta.model.Type<? extends Object> getDeclaringType() {
        return Metamodel.getAppliedMetamodel(producedType.getQualifyingType());
    }

    @Override
    @Ignore
    public TypeDescriptor $getType$() {
        return TypeDescriptor.klass(MemberInterfaceImpl.class, $reifiedContainer, $reifiedType);
    }

    @Override
    @Ignore
    public Interface<? extends Type> $callvariadic$() {
        return $callvariadic$(empty_.get_());
    }
    
    @Override
    @Ignore
    public Interface<? extends Type> $callvariadic$(
            Sequential<?> varargs) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Ignore
    public Interface<? extends Type> $callvariadic$(
            Object arg0, Sequential<?> varargs) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Ignore
    public Interface<? extends Type> $callvariadic$(
            Object arg0, Object arg1, Sequential<?> varargs) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Ignore
    public Interface<? extends Type> $callvariadic$(
            Object arg0, Object arg1, Object arg2, Sequential<?> varargs) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Ignore
    public Interface<? extends Type> $callvariadic$(Object... argsAndVarargs) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Ignore
    public Interface<? extends Type> $callvariadic$(Object arg0) {
        return $callvariadic$(arg0, empty_.get_());
    }

    @Override
    @Ignore
    public Interface<? extends Type> $callvariadic$(Object arg0, Object arg1) {
        return $callvariadic$(arg0, arg1, empty_.get_());
    }

    @Override
    @Ignore
    public Interface<? extends Type> $callvariadic$(Object arg0, Object arg1,
            Object arg2) {
        return $callvariadic$(arg0, arg1, arg2, empty_.get_());
    }

    @Override
    public Interface<? extends Type> bind(@TypeInfo("ceylon.language::Object") @Name("container") java.lang.Object container){
        return (Interface<? extends Type>) Metamodel.bind(this, this.producedType.getQualifyingType(), container);
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 37 * result + getDeclaringType().hashCode();
        result = 37 * result + getDeclaration().hashCode();
        result = 37 * result + getTypeArgumentWithVariances().hashCode();
        return result;
    }
    
    @Override
    public boolean equals(Object obj) {
        if(obj == null)
            return false;
        if(obj == this)
            return true;
        if(obj instanceof ceylon.language.meta.model.MemberInterface == false)
            return false;
        ceylon.language.meta.model.MemberInterface<?, ?> other = (ceylon.language.meta.model.MemberInterface<?, ?>) obj;
        return getDeclaration().equals(other.getDeclaration())
                && getDeclaringType().equals(other.getDeclaringType())
                && getTypeArgumentWithVariances().equals(other.getTypeArgumentWithVariances());
    }

    @Override
    @TypeInfo("ceylon.language.meta.model::Type<ceylon.language::Anything>")
    public ceylon.language.meta.model.Type<?> getContainer(){
        return getDeclaringType();
    }
}
