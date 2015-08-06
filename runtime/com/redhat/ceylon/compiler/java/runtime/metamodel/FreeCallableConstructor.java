package com.redhat.ceylon.compiler.java.runtime.metamodel;

import java.lang.reflect.AnnotatedElement;
import java.util.List;

import ceylon.language.Anything;
import ceylon.language.Iterator;
import ceylon.language.Sequential;
import ceylon.language.empty_;
import ceylon.language.finished_;
import ceylon.language.meta.declaration.CallableConstructorDeclaration;
import ceylon.language.meta.declaration.CallableConstructorDeclaration$impl;
import ceylon.language.meta.declaration.ClassDeclaration;
import ceylon.language.meta.declaration.FunctionOrValueDeclaration;
import ceylon.language.meta.declaration.FunctionalDeclaration$impl;
import ceylon.language.meta.declaration.Module;
import ceylon.language.meta.declaration.OpenType;
import ceylon.language.meta.declaration.Package;
import ceylon.language.meta.model.CallableConstructor;
import ceylon.language.meta.model.Function;
import ceylon.language.meta.model.MemberClassCallableConstructor;
import ceylon.language.meta.model.Method;
import ceylon.language.meta.model.Type;
import ceylon.language.meta.model.TypeApplicationException;

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
import com.redhat.ceylon.compiler.java.runtime.model.TypeDescriptor;
import com.redhat.ceylon.compiler.java.runtime.model.TypeDescriptor.Nothing;
import com.redhat.ceylon.model.typechecker.model.Class;
import com.redhat.ceylon.model.typechecker.model.Constructor;
import com.redhat.ceylon.model.typechecker.model.Functional;
import com.redhat.ceylon.model.typechecker.model.Generic;
import com.redhat.ceylon.model.typechecker.model.TypeDeclaration;
import com.redhat.ceylon.model.typechecker.model.TypedReference;

@Ceylon(major = 8)
@com.redhat.ceylon.compiler.java.metadata.Class
public class FreeCallableConstructor
        extends FreeFunctionOrValue
        implements CallableConstructorDeclaration, 
            ceylon.language.meta.declaration.FunctionalDeclaration, 
            AnnotationBearing {
    
    private OpenType type;
    
    @Ignore
    public static final TypeDescriptor $TypeDescriptor$ = TypeDescriptor.klass(FreeCallableConstructor.class);
    
    final Constructor constructor;
    private Sequential<FunctionOrValueDeclaration> parameterList;
    private Sequential<? extends ceylon.language.meta.declaration.TypeParameter> typeParameters;
    
    public FreeCallableConstructor(com.redhat.ceylon.model.typechecker.model.Functional function, Constructor constructor) {
        super((com.redhat.ceylon.model.typechecker.model.Declaration)function);
        List<com.redhat.ceylon.model.typechecker.model.TypeParameter> typeParameters = ((Generic) declaration).getTypeParameters();
        ceylon.language.meta.declaration.TypeParameter[] typeParametersArray = new ceylon.language.meta.declaration.TypeParameter[typeParameters.size()];
        int i=0;
        for(com.redhat.ceylon.model.typechecker.model.TypeParameter tp : typeParameters){
            typeParametersArray[i++] = new com.redhat.ceylon.compiler.java.runtime.metamodel.FreeTypeParameter(tp);
        }
        this.typeParameters = Util.sequentialWrapper(ceylon.language.meta.declaration.TypeParameter.$TypeDescriptor$, typeParametersArray);
        this.constructor = constructor;
        this.parameterList = FunctionalUtil.getParameters(constructor);
    }
    
    @Override
    public boolean getAnnotation() {
        return false;
    }
    
    @Override
    public boolean getAbstract() {
        return constructor.isAbstract();
    }

    @Override
    public FunctionOrValueDeclaration getParameterDeclaration(String name) {
        return FunctionalUtil.getParameterDeclaration(this.parameterList, name);
    }

    @Override
    @TypeInfo("ceylon.language::Sequential<ceylon.language.meta.declaration::FunctionOrValueDeclaration>")
    public Sequential<? extends FunctionOrValueDeclaration> getParameterDeclarations() {
        return this.parameterList;
    }

    @Override
    @TypeInfo("ceylon.language::Sequential<Annotation>")
    @TypeParameters(@TypeParameter(value = "Annotation", satisfies = "ceylon.language::Annotation"))
    public <Annotation extends java.lang.annotation.Annotation> Sequential<? extends Annotation> annotations(@Ignore TypeDescriptor $reifiedAnnotation) {
        return Metamodel.annotations($reifiedAnnotation, this);
    }

    @Override
    public String getName() {
        return constructor.getName() == null ? "" : constructor.getName();
    }

    @Override
    public String getQualifiedName() {
        String name = getName();
        return ((Class)constructor.getContainer()).getQualifiedNameString() + (name.isEmpty() ? "" : "." + getName());
    }

    @Override
    public OpenType getOpenType() {
        return Metamodel.getMetamodel(constructor.getType());
    }

    @Override
    public Module getContainingModule() {
        return getContainer().getContainingModule();
    }

    @Override
    public Package getContainingPackage() {
        return getContainer().getContainingPackage();
    }

    @Override
    public boolean getToplevel() {
        return false;
    }

    @Override
    public ClassDeclaration getContainer() {
        return ((ClassDeclaration)Metamodel.getOrCreateMetamodel(((Class)constructor.getContainer())));
    }

    @Override
    @Ignore
    public java.lang.annotation.Annotation[] $getJavaAnnotations$() {
        return Metamodel.getJavaConstructor(constructor).getAnnotations();
    }
    
    @Override
    @Ignore
    public boolean $isAnnotated$(java.lang.Class<? extends java.lang.annotation.Annotation> annotationType) {
        final AnnotatedElement element = Metamodel.getJavaConstructor(constructor);
        return element != null ? element.isAnnotationPresent(annotationType) : false;
    }
    
    @Override
    public <AnnotationType extends java.lang.annotation.Annotation> boolean annotated(TypeDescriptor reifed$AnnotationType) {
        return Metamodel.isAnnotated(reifed$AnnotationType, this);
    }

    @Override
    @Ignore
    public TypeDescriptor $getType$() {
        return $TypeDescriptor$;
    }
    
    @Override
    public String toString() {
        return "new "+getQualifiedName();
    }
    
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        } else if (other instanceof FreeCallableConstructor) {
            return getContainer().equals(((FreeCallableConstructor)other).getContainer())
                    && getName().equals(((FreeCallableConstructor)other).getName());
        } else {
            return false;
        }
    }
    
    @Override
    public int hashCode() {
        return getContainer().hashCode() ^ getName().hashCode();
    }

    @Override
    public CallableConstructorDeclaration$impl $ceylon$language$meta$declaration$CallableConstructorDeclaration$impl() {
        return null;
    }
    
    @Override
    public FunctionalDeclaration$impl $ceylon$language$meta$declaration$FunctionalDeclaration$impl() {
        return null;
    }

    @Override
    public Object invoke(Sequential<? extends Type<? extends Object>> typeArguments) {
        return invoke(typeArguments, empty_.get_());
    }

    @Override
    public Object invoke(Sequential<? extends Type<? extends Object>> typeArguments,
            Sequential<? extends Object> arguments) {
        if (!typeArguments.getEmpty()) {
            throw new TypeApplicationException("Constructors do not accept type arguments");
        }
        // TODO Auto-generated method stub
        apply(Anything.$TypeDescriptor$, TypeDescriptor.NothingType, typeArguments).apply(arguments);
        return null;
    }
    

    @Override
    public Object memberInvoke(Object containerInstance,
            Sequential<? extends Type<? extends Object>> typeArguments) {
        return memberInvoke(containerInstance, typeArguments, empty_.get_());
    }

    @Override
    public Object memberInvoke(Object containerInstance,
            Sequential<? extends Type<? extends Object>> typeArguments,
            Sequential<? extends Object> arguments) {
        if (!typeArguments.getEmpty()) {
            throw new TypeApplicationException("Constructors do not accept type arguments");
        }
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <Result, Arguments extends Sequential<? extends Object>> CallableConstructor<Result, Arguments> apply(
            TypeDescriptor $reified$Result, TypeDescriptor $reified$Arguments) {
        return apply($reified$Result, $reified$Arguments, (Sequential)empty_.get_());
    }
    
    @Override
    public <Result, Arguments extends Sequential<? extends Object>> CallableConstructor<Result, Arguments> apply(
            TypeDescriptor $reified$Result, TypeDescriptor $reified$Arguments,
            Sequential<? extends Type<? extends Object>> typeArguments) {
        // apply the given type arguments to the containing class
        ClassDeclaration clsDecl = getContainer();
        ceylon.language.meta.model.Class<? extends Result, ?> cls 
                = clsDecl.<Result, Sequential<? extends java.lang.Object>>classApply(
                        $reified$Result, Nothing.NothingType, typeArguments);
        // then get the constructor from that
        return (CallableConstructor)cls.<Arguments>getConstructor($reified$Arguments, getName());
    }
    
    @Override
    @Ignore
    public <Container, Result, Arguments extends Sequential<? extends Object>> MemberClassCallableConstructor<Container, Result, Arguments> memberApply(
            TypeDescriptor $reified$Container, TypeDescriptor $reified$Result, TypeDescriptor $reified$Arguments,
            Type<? extends Object> containerType) {
        return memberApply($reified$Container, $reified$Result, $reified$Arguments, containerType, (Sequential)empty_.get_());
    }
    
    @Override
    public <Container, Result, Arguments extends Sequential<? extends Object>> MemberClassCallableConstructor<Container, Result, Arguments> memberApply(
            TypeDescriptor $reified$Container, TypeDescriptor $reified$Result, TypeDescriptor $reified$Arguments,
            Type<? extends Object> containerType,
            Sequential<? extends Type<? extends Object>> typeArguments) {
        // apply the given type arguments to the containing class
        ceylon.language.meta.model.MemberClass<? super Container, ? extends Result, ?> cls 
                = getContainer().<Container, Result, Sequential<? extends Object>>memberClassApply(
                        $reified$Container, $reified$Result, Nothing.NothingType, containerType, typeArguments);
        // then get the constructor from that
        return (MemberClassCallableConstructor)cls.<Arguments>getConstructor($reified$Arguments, getName());
    }
    
    @Override
    public boolean getDefaultConstructor() {
        return getName().isEmpty();
    }
    
    /////////////////////////////////////////
   

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
    public ceylon.language.Sequential<? extends ceylon.language.meta.model.Type<?>> invoke$typeArguments(){
        return (ceylon.language.Sequential<? extends ceylon.language.meta.model.Type<?>>)(Sequential)empty_.get_();
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Ignore
    @Override
    public java.lang.Object invoke(){
        return invoke((ceylon.language.Sequential<? extends ceylon.language.meta.model.Type<?>>)(Sequential)empty_.get_());
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


}
