package com.redhat.ceylon.compiler.java.runtime.metamodel.decl;

import java.lang.reflect.AnnotatedElement;
import java.util.List;

import ceylon.language.Anything;
import ceylon.language.Iterator;
import ceylon.language.Sequential;
import ceylon.language.empty_;
import ceylon.language.finished_;
import ceylon.language.meta.declaration.FunctionalDeclaration$impl;
import ceylon.language.meta.declaration.OpenType;

import com.redhat.ceylon.compiler.java.Util;
import com.redhat.ceylon.compiler.java.metadata.Ceylon;
import com.redhat.ceylon.compiler.java.metadata.Defaulted;
import com.redhat.ceylon.compiler.java.metadata.Ignore;
import com.redhat.ceylon.compiler.java.metadata.Name;
import com.redhat.ceylon.compiler.java.metadata.Sequenced;
import com.redhat.ceylon.compiler.java.metadata.TypeInfo;
import com.redhat.ceylon.compiler.java.metadata.TypeParameter;
import com.redhat.ceylon.compiler.java.metadata.TypeParameters;
import com.redhat.ceylon.compiler.java.metadata.Variance;
import com.redhat.ceylon.compiler.java.runtime.metamodel.AnnotationBearing;
import com.redhat.ceylon.compiler.java.runtime.metamodel.FunctionalUtil;
import com.redhat.ceylon.compiler.java.runtime.metamodel.Metamodel;
import com.redhat.ceylon.compiler.java.runtime.metamodel.meta.FunctionImpl;
import com.redhat.ceylon.compiler.java.runtime.metamodel.meta.MethodImpl;
import com.redhat.ceylon.compiler.java.runtime.model.TypeDescriptor;
import com.redhat.ceylon.model.typechecker.model.Function;
import com.redhat.ceylon.model.typechecker.model.Functional;
import com.redhat.ceylon.model.typechecker.model.Generic;
import com.redhat.ceylon.model.typechecker.model.TypeDeclaration;
import com.redhat.ceylon.model.typechecker.model.TypedReference;

@Ceylon(major = 8)
@com.redhat.ceylon.compiler.java.metadata.Class
public class FunctionDeclarationImpl 
    extends FunctionOrValueDeclarationImpl
    implements ceylon.language.meta.declaration.FunctionDeclaration, AnnotationBearing {

    @Ignore
    public static final TypeDescriptor $TypeDescriptor$ = TypeDescriptor.klass(FunctionDeclarationImpl.class);
    
    private Sequential<? extends ceylon.language.meta.declaration.TypeParameter> typeParameters;
    
    private OpenType type;

    private Sequential<? extends ceylon.language.meta.declaration.FunctionOrValueDeclaration> parameterList;

    public FunctionDeclarationImpl(com.redhat.ceylon.model.typechecker.model.Functional declaration) {
        super((com.redhat.ceylon.model.typechecker.model.Declaration)declaration);

        // FIXME: make lazy
        // FIXME: share with ClassOrInterface
        List<com.redhat.ceylon.model.typechecker.model.TypeParameter> typeParameters = ((Generic) declaration).getTypeParameters();
        ceylon.language.meta.declaration.TypeParameter[] typeParametersArray = new ceylon.language.meta.declaration.TypeParameter[typeParameters.size()];
        int i=0;
        for(com.redhat.ceylon.model.typechecker.model.TypeParameter tp : typeParameters){
            typeParametersArray[i++] = new com.redhat.ceylon.compiler.java.runtime.metamodel.decl.TypeParameterImpl(tp);
        }
        this.typeParameters = Util.sequentialWrapper(ceylon.language.meta.declaration.TypeParameter.$TypeDescriptor$, typeParametersArray);
        
        this.type = Metamodel.getMetamodel(declaration.getType());
        this.parameterList = FunctionalUtil.getParameters((Functional)declaration);
    }

    @Override
    @Ignore
    public FunctionalDeclaration$impl $ceylon$language$meta$declaration$FunctionalDeclaration$impl() {
        return null;
    }

    @Override
    @TypeInfo("ceylon.language::Sequential<ceylon.language.meta.declaration::FunctionOrValueDeclaration>")
    public Sequential<? extends ceylon.language.meta.declaration.FunctionOrValueDeclaration> getParameterDeclarations(){
        return parameterList;
    }

    @Override
    @TypeInfo("ceylon.language.meta.declaration::FunctionOrValueDeclaration|ceylon.language::Null")
    public ceylon.language.meta.declaration.FunctionOrValueDeclaration getParameterDeclaration(@Name("name") String name){
        return FunctionalUtil.getParameterDeclaration(this.parameterList, name);
    }

    @Override
    @TypeInfo("ceylon.language::Sequential<ceylon.language.meta.declaration::TypeParameter>")
    public Sequential<? extends ceylon.language.meta.declaration.TypeParameter> getTypeParameterDeclarations() {
        return typeParameters;
    }

    @Override
    @TypeInfo("ceylon.language.meta.declaration::TypeParameter|ceylon.language::Null")
    public ceylon.language.meta.declaration.TypeParameter getTypeParameterDeclaration(@Name("name") String name) {
        Iterator<? extends ceylon.language.meta.declaration.TypeParameter> iterator = typeParameters.iterator();
        Object it;
        while((it = iterator.next()) != finished_.get_()){
            ceylon.language.meta.declaration.TypeParameter tp = (ceylon.language.meta.declaration.TypeParameter) it;
            if(tp.getName().equals(name))
                return tp;
        }
        return null;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Ignore
    @Override
    public <Return extends Object, Arguments extends Sequential<? extends Object>> ceylon.language.meta.model.Function<Return, Arguments> apply(TypeDescriptor $reifiedReturn,
            TypeDescriptor $reifiedArguments){
        return apply($reifiedReturn,$reifiedArguments,(Sequential)empty_.get_());
    }

    @Override
    @TypeInfo("ceylon.language.meta.model::Function<Return,Arguments>")
    @TypeParameters({
        @TypeParameter("Return"),
        @TypeParameter(value = "Arguments", satisfies = "ceylon.language::Sequential<ceylon.language::Anything>")
    })
    public <Return extends Object, Arguments extends Sequential<? extends Object>> ceylon.language.meta.model.Function<Return, Arguments> apply(
            @Ignore TypeDescriptor $reifiedReturn,
            @Ignore TypeDescriptor $reifiedArguments,
            @Name("typeArguments") @TypeInfo("ceylon.language::Sequential<ceylon.language.meta.model::Type<ceylon.language::Anything>>") @Sequenced Sequential<? extends ceylon.language.meta.model.Type<?>> typeArguments){
        if(!getToplevel())
            throw new ceylon.language.meta.model.TypeApplicationException("Cannot apply a member declaration with no container type: use memberApply");
        List<com.redhat.ceylon.model.typechecker.model.Type> producedTypes = Metamodel.getProducedTypes(typeArguments);
        Metamodel.checkTypeArguments(null, declaration, producedTypes);
        com.redhat.ceylon.model.typechecker.model.Reference appliedFunction = declaration.appliedReference(null, producedTypes);
        TypeDescriptor reifiedType = Metamodel.getTypeDescriptorForFunction(appliedFunction);
        TypeDescriptor reifiedArguments = Metamodel.getTypeDescriptorForArguments(declaration.getUnit(), (Functional) declaration, appliedFunction);

        Metamodel.checkReifiedTypeArgument("apply", "Function<$1,$2>", Variance.OUT, 
                declaration.getUnit().getCallableReturnType(appliedFunction.getFullType()), $reifiedReturn, 
                Variance.IN, Metamodel.getProducedTypeForArguments(declaration.getUnit(), (Functional)declaration, appliedFunction), $reifiedArguments);
        return new FunctionImpl<Return,Arguments>(reifiedType, reifiedArguments, appliedFunction, this, null, null);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Ignore
    @Override
    public <Container, Return, Arguments extends Sequential<? extends Object>>
        ceylon.language.meta.model.Method<Container, Return, Arguments> memberApply(TypeDescriptor $reifiedContainer,
                                                                               TypeDescriptor $reifiedReturn,
                                                                               TypeDescriptor $reifiedArguments,
                                                                               ceylon.language.meta.model.Type<? extends Object> containerType){
        
        return this.<Container, Return, Arguments>memberApply($reifiedContainer,
                                                              $reifiedReturn,
                                                              $reifiedArguments,
                                                              containerType,
                                                              (Sequential)empty_.get_());
    }

    @TypeInfo("ceylon.language.meta.model::Method<Container,Return,Arguments>")
    @TypeParameters({
        @TypeParameter("Container"),
        @TypeParameter("Return"),
        @TypeParameter(value = "Arguments", satisfies = "ceylon.language::Sequential<ceylon.language::Anything>")
    })
    @Override
    public <Container, Return, Arguments extends Sequential<? extends Object>>
        ceylon.language.meta.model.Method<Container, Return, Arguments> memberApply(
                @Ignore TypeDescriptor $reifiedContainer,
                @Ignore TypeDescriptor $reifiedReturn,
                @Ignore TypeDescriptor $reifiedArguments,
                @Name("containerType") ceylon.language.meta.model.Type<? extends Object> containerType,
                @Name("typeArguments") @Sequenced Sequential<? extends ceylon.language.meta.model.Type<?>> typeArguments){
        if(getToplevel())
            throw new ceylon.language.meta.model.TypeApplicationException("Cannot apply a toplevel declaration to a container type: use apply");
        return getAppliedMethod($reifiedContainer, $reifiedReturn, $reifiedArguments, typeArguments, containerType);
    }

    <Container, Type, Arguments extends ceylon.language.Sequential<? extends Object>>
    ceylon.language.meta.model.Method<Container, Type, Arguments> getAppliedMethod(@Ignore TypeDescriptor $reifiedContainer, 
                                                                              @Ignore TypeDescriptor $reifiedType, 
                                                                              @Ignore TypeDescriptor $reifiedArguments, 
                                                                              Sequential<? extends ceylon.language.meta.model.Type<?>> typeArguments,
                                                                              ceylon.language.meta.model.Type<? extends Object> container){
        List<com.redhat.ceylon.model.typechecker.model.Type> producedTypes = Metamodel.getProducedTypes(typeArguments);
        com.redhat.ceylon.model.typechecker.model.Type containerType = Metamodel.getModel(container);
        Metamodel.checkQualifyingType(containerType, declaration);
        Metamodel.checkTypeArguments(containerType, declaration, producedTypes);
        // find the proper qualifying type
        com.redhat.ceylon.model.typechecker.model.Type memberQualifyingType = containerType.getSupertype((TypeDeclaration) declaration.getContainer());
        final TypedReference appliedFunction = ((com.redhat.ceylon.model.typechecker.model.TypedDeclaration)declaration).appliedTypedReference(memberQualifyingType, producedTypes);
        TypeDescriptor reifiedType = Metamodel.getTypeDescriptorForFunction(appliedFunction);
        TypeDescriptor reifiedArguments = Metamodel.getTypeDescriptorForArguments(declaration.getUnit(), (Functional) declaration, appliedFunction);
        TypeDescriptor reifiedContainer = Metamodel.getTypeDescriptorForProducedType(containerType);

        Metamodel.checkReifiedTypeArgument("memberApply", "Method<$1,$2,$3>", 
                Variance.IN, containerType, $reifiedContainer, 
                Variance.OUT, appliedFunction.getType(), $reifiedType,
                Variance.IN, Metamodel.getProducedTypeForArguments(declaration.getUnit(), (Functional)declaration, appliedFunction), $reifiedArguments);

        return new MethodImpl<Container, Type, Arguments>(reifiedContainer, reifiedType, reifiedArguments, appliedFunction, this, container);
    }
    
    @Override
    @TypeInfo("ceylon.language.meta.declaration::OpenType")
    public OpenType getOpenType() {
        return type;
    }

    @Override
    public boolean getAnnotation(){
        return declaration.isAnnotation();
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Ignore
    @Override
    public ceylon.language.Sequential<? extends ceylon.language.meta.model.Type<?>> invoke$typeArguments(){
        return (ceylon.language.Sequential<? extends ceylon.language.meta.model.Type<?>>)(Sequential)empty_.get_();
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Ignore
    @Override
    public java.lang.Object invoke(){
        return invoke((ceylon.language.Sequential<? extends ceylon.language.meta.model.Type<?>>)(Sequential)empty_.get_());
    }

    @Ignore
    @Override
    public java.lang.Object invoke(
            ceylon.language.Sequential<? extends ceylon.language.meta.model.Type<?>> typeArguments){
        return invoke(typeArguments, empty_.get_());
    }

    @TypeInfo("ceylon.language::Anything")
    @Override
    public java.lang.Object invoke(
            @Name("typeArguments") @Defaulted 
            @TypeInfo("ceylon.language::Sequential<ceylon.language.meta.model::Type<ceylon.language::Anything>>")
            ceylon.language.Sequential<? extends ceylon.language.meta.model.Type<?>> typeArguments,
            @Name("arguments") @Sequenced @TypeInfo("ceylon.language::Sequential<ceylon.language::Anything>") 
            ceylon.language.Sequential<?> arguments){
        return apply(Anything.$TypeDescriptor$, TypeDescriptor.NothingType, typeArguments).apply(arguments);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Ignore
    @Override
    public ceylon.language.Sequential<? extends ceylon.language.meta.model.Type<?>> 
        memberInvoke$typeArguments(java.lang.Object container){
        return (ceylon.language.Sequential<? extends ceylon.language.meta.model.Type<?>>)(Sequential)empty_.get_();
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Ignore
    @Override
    public java.lang.Object memberInvoke(java.lang.Object container){
        return memberInvoke(container, (ceylon.language.Sequential<? extends ceylon.language.meta.model.Type<?>>)(Sequential)empty_.get_());
    }

    @Ignore
    @Override
    public java.lang.Object memberInvoke(
            java.lang.Object container,
            ceylon.language.Sequential<? extends ceylon.language.meta.model.Type<?>> typeArguments){
        return memberInvoke(container, typeArguments, empty_.get_());
    }

    @SuppressWarnings("unchecked")
    @TypeInfo("ceylon.language::Anything")
    @Override
    public java.lang.Object memberInvoke(
            @Name("container") @TypeInfo("ceylon.language::Anything")
            java.lang.Object container,
            @Name("typeArguments") @Defaulted 
            @TypeInfo("ceylon.language::Sequential<ceylon.language.meta.model::Type<ceylon.language::Anything>>")
            ceylon.language.Sequential<? extends ceylon.language.meta.model.Type<?>> typeArguments,
            @Name("arguments") @Sequenced @TypeInfo("ceylon.language::Sequential<ceylon.language::Anything>") 
            ceylon.language.Sequential<?> arguments){
        ceylon.language.meta.model.Type<?> containerType = Metamodel.getAppliedMetamodel(Metamodel.getTypeDescriptor(container));
        return memberApply(TypeDescriptor.NothingType, Anything.$TypeDescriptor$, TypeDescriptor.NothingType, 
                containerType, typeArguments).bind(container).apply(arguments);
    }

    @Override
    public int hashCode() {
        return Metamodel.hashCode(this, "function");
    }
    
    @Override
    public boolean equals(Object obj) {
        if(obj == null)
            return false;
        if(obj == this)
            return true;
        if(obj instanceof FunctionDeclarationImpl == false)
            return false;
        return Metamodel.equalsForSameType(this, (FunctionDeclarationImpl)obj);
    }

    @Override
    public String toString() {
        return "function "+super.toString();
    }
    
    @Ignore
    @Override
    public TypeDescriptor $getType$() {
        return $TypeDescriptor$;
    }

    @Ignore
    @Override
    public java.lang.annotation.Annotation[] $getJavaAnnotations$() {
        // FIXME: this could be a FunctionalParameter!
        return Metamodel.getJavaMethod((Function) declaration).getAnnotations();
    }
    
    @Override
    @Ignore
    public boolean $isAnnotated$(java.lang.Class<? extends java.lang.annotation.Annotation> annotationType) {
        final AnnotatedElement element = Metamodel.getJavaMethod((Function) declaration);
        return element != null ? element.isAnnotationPresent(annotationType) : false;
    }
    
    @Override
    public <AnnotationType extends java.lang.annotation.Annotation> boolean annotated(TypeDescriptor reifed$AnnotationType) {
        return Metamodel.isAnnotated(reifed$AnnotationType, this);
    }
    
}