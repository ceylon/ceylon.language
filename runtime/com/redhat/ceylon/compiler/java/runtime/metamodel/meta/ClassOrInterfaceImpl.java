package com.redhat.ceylon.compiler.java.runtime.metamodel.meta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.redhat.ceylon.compiler.java.Util;
import com.redhat.ceylon.compiler.java.language.ObjectArrayIterable;
import com.redhat.ceylon.compiler.java.metadata.Ceylon;
import com.redhat.ceylon.compiler.java.metadata.Ignore;
import com.redhat.ceylon.compiler.java.metadata.Name;
import com.redhat.ceylon.compiler.java.metadata.Sequenced;
import com.redhat.ceylon.compiler.java.metadata.TypeInfo;
import com.redhat.ceylon.compiler.java.metadata.TypeParameter;
import com.redhat.ceylon.compiler.java.metadata.TypeParameters;
import com.redhat.ceylon.compiler.java.metadata.Variance;
import com.redhat.ceylon.compiler.java.runtime.metamodel.Metamodel;
import com.redhat.ceylon.compiler.java.runtime.metamodel.decl.ClassDeclarationImpl;
import com.redhat.ceylon.compiler.java.runtime.metamodel.decl.ClassOrInterfaceDeclarationImpl;
import com.redhat.ceylon.compiler.java.runtime.metamodel.decl.FunctionDeclarationImpl;
import com.redhat.ceylon.compiler.java.runtime.metamodel.decl.InterfaceDeclarationImpl;
import com.redhat.ceylon.compiler.java.runtime.metamodel.decl.NestableDeclarationImpl;
import com.redhat.ceylon.compiler.java.runtime.metamodel.decl.ValueDeclarationImpl;
import com.redhat.ceylon.compiler.java.runtime.model.ReifiedType;
import com.redhat.ceylon.compiler.java.runtime.model.TypeDescriptor;
import com.redhat.ceylon.model.typechecker.model.ClassOrInterface;
import com.redhat.ceylon.model.typechecker.model.Declaration;
import com.redhat.ceylon.model.typechecker.model.Functional;
import com.redhat.ceylon.model.typechecker.model.Reference;
import com.redhat.ceylon.model.typechecker.model.TypeDeclaration;

import ceylon.language.Array;
import ceylon.language.Iterator;
import ceylon.language.Map;
import ceylon.language.Sequence;
import ceylon.language.Sequential;
import ceylon.language.empty_;
import ceylon.language.finished_;
import ceylon.language.meta.declaration.AnnotatedDeclaration;
import ceylon.language.meta.declaration.ClassDeclaration;
import ceylon.language.meta.declaration.FunctionDeclaration;
import ceylon.language.meta.declaration.InterfaceDeclaration;
import ceylon.language.meta.declaration.ValueDeclaration;
import ceylon.language.meta.model.Attribute;
import ceylon.language.meta.model.IncompatibleTypeException;
import ceylon.language.meta.model.Member;

@Ceylon(major = 8)
@com.redhat.ceylon.compiler.java.metadata.Class
@TypeParameters({
    @TypeParameter(value = "Type", variance = Variance.OUT),
    })
public abstract class ClassOrInterfaceImpl<Type> 
    implements ceylon.language.meta.model.ClassOrInterface<Type>, ReifiedType {

    private static final List<com.redhat.ceylon.model.typechecker.model.Type> NO_TYPE_ARGS = Collections.<com.redhat.ceylon.model.typechecker.model.Type>emptyList();
    private volatile boolean initialised;
    public final com.redhat.ceylon.model.typechecker.model.Type producedType;
    public final com.redhat.ceylon.compiler.java.runtime.metamodel.decl.ClassOrInterfaceDeclarationImpl declaration;
    protected ceylon.language.Map<? extends ceylon.language.meta.declaration.TypeParameter, ? extends ceylon.language.meta.model.Type<?>> typeArguments;
    private Map<? extends ceylon.language.meta.declaration.TypeParameter, ? extends Sequence<? extends Object>> typeArgumentWithVariances;
    protected ceylon.language.meta.model.ClassModel<? extends Object, ? super Sequential<? extends Object>> superclass;
    protected Sequential<ceylon.language.meta.model.InterfaceModel<? extends Object>> interfaces;
    @Ignore
    public final TypeDescriptor $reifiedType;
    
    ClassOrInterfaceImpl(@Ignore TypeDescriptor $reifiedType, com.redhat.ceylon.model.typechecker.model.Type producedType){
        this.producedType = producedType;
        this.declaration = Metamodel.getOrCreateMetamodel(producedType.getDeclaration());
        this.$reifiedType = Metamodel.getTypeDescriptorForProducedType(producedType);
    }

    @Override
    @Ignore
    public ceylon.language.meta.model.Type$impl<Type> $ceylon$language$meta$model$Type$impl() {
        return null;
    }

    protected void checkInit(){
        if(!initialised){
            synchronized(Metamodel.getLock()){
                if(!initialised){
                    init();
                    initialised = true;
                }
            }
        }
    }
    
    @SuppressWarnings("unchecked")
    protected void init() {
        com.redhat.ceylon.model.typechecker.model.ClassOrInterface decl = (com.redhat.ceylon.model.typechecker.model.ClassOrInterface) producedType.getDeclaration();
        this.typeArguments = Metamodel.getTypeArguments(declaration, producedType);
        this.typeArgumentWithVariances = Metamodel.getTypeArgumentWithVariances(declaration, producedType);
        
        com.redhat.ceylon.model.typechecker.model.Type superType = decl.getExtendedType();
        if(superType != null){
            com.redhat.ceylon.model.typechecker.model.Type superTypeResolved = superType.substitute(producedType);
            this.superclass = (ceylon.language.meta.model.ClassModel<?,? super Sequential<? extends Object>>) Metamodel.getAppliedMetamodel(superTypeResolved);
        }
        
        List<com.redhat.ceylon.model.typechecker.model.Type> satisfiedTypes = decl.getSatisfiedTypes();
        ceylon.language.meta.model.InterfaceModel<?>[] interfaces = new ceylon.language.meta.model.InterfaceModel[satisfiedTypes.size()];
        int i=0;
        for(com.redhat.ceylon.model.typechecker.model.Type pt : satisfiedTypes){
            com.redhat.ceylon.model.typechecker.model.Type resolvedPt = pt.substitute(producedType);
            interfaces[i++] = (ceylon.language.meta.model.InterfaceModel<?>) Metamodel.getAppliedMetamodel(resolvedPt);
        }
        this.interfaces = Util.sequentialWrapper(TypeDescriptor.klass(ceylon.language.meta.model.InterfaceModel.class, ceylon.language.Anything.$TypeDescriptor$), interfaces);
    }

    @Override
    @TypeInfo("ceylon.language::Map<ceylon.language.meta.declaration::TypeParameter,ceylon.language.meta.model::Type<ceylon.language::Anything>>")
    public Map<? extends ceylon.language.meta.declaration.TypeParameter, ? extends ceylon.language.meta.model.Type<?>> getTypeArguments() {
        //checkInit();
        if (typeArguments == null) {
            this.typeArguments = Metamodel.getTypeArguments(declaration, producedType);
        }
        return typeArguments;
    }
    
    @Override
    public ceylon.language.Sequential<? extends ceylon.language.meta.model.Type<?>> getTypeArgumentList() {
        return Metamodel.getTypeArgumentList(this);
    }

    @Override
    @TypeInfo("ceylon.language::Map<ceylon.language.meta.declaration::TypeParameter,[ceylon.language.meta.model::Type<ceylon.language::Anything>,ceylon.language.meta.declaration::Variance]>")
    public Map<? extends ceylon.language.meta.declaration.TypeParameter, ? extends ceylon.language.Sequence<? extends Object>> getTypeArgumentWithVariances() {
        checkInit();
        return typeArgumentWithVariances;
    }
    
    @Override
    @TypeInfo("ceylon.language::Sequential<[ceylon.language.meta.model::Type<ceylon.language::Anything>,ceylon.language.meta.declaration::Variance]>")
    public ceylon.language.Sequential<? extends ceylon.language.Sequence<? extends Object>> getTypeArgumentWithVarianceList() {
        return Metamodel.getTypeArgumentWithVarianceList(this);
    }

    @Override
    @TypeInfo("ceylon.language.meta.declaration::ClassOrInterfaceDeclaration")
    public ceylon.language.meta.declaration.ClassOrInterfaceDeclaration getDeclaration() {
        return declaration;
    }
    

    @Override
    @TypeInfo("ceylon.language::Sequential<ceylon.language.meta.model::InterfaceModel<ceylon.language::Anything>>")
    public Sequential<? extends ceylon.language.meta.model.InterfaceModel<? extends Object>> getSatisfiedTypes() {
        checkInit();
        return interfaces;
    }

    @Override
    @TypeInfo("ceylon.language.meta.model::ClassModel<ceylon.language::Anything,ceylon.language::Nothing>|ceylon.language::Null")
    public ceylon.language.meta.model.ClassModel<? extends Object, ? super Sequential<? extends Object>> getExtendedType() {
        checkInit();
        return superclass;
    }

    @SuppressWarnings({ "hiding", "unchecked", "rawtypes" })
    @Ignore
    @Override
    public <Container, Type, Arguments extends Sequential<? extends Object>>
    ceylon.language.meta.model.Method<Container, Type, Arguments> getMethod(@Ignore TypeDescriptor $reifiedContainer, 
                                                                         @Ignore TypeDescriptor $reifiedType, 
                                                                         @Ignore TypeDescriptor $reifiedArguments, 
                                                                         String name){
        
        return getMethod($reifiedContainer, $reifiedType, $reifiedArguments, name, 
                (Sequential<? extends ceylon.language.meta.model.Type<?>>)(Sequential)empty_.get_());
    }

    @SuppressWarnings("hiding")
    @Override
    @TypeParameters({
        @TypeParameter(value = "Container"),
        @TypeParameter(value = "Type"),
        @TypeParameter(value = "Arguments", satisfies = "ceylon.language::Sequential<ceylon.language::Anything>")
    })
    @TypeInfo("ceylon.language.meta.model::Method<Container,Type,Arguments>|ceylon.language::Null")
    public <Container, Type, Arguments extends Sequential<? extends Object>>
        ceylon.language.meta.model.Method<Container, Type, Arguments> getMethod(@Ignore TypeDescriptor $reifiedContainer, 
                                                                             @Ignore TypeDescriptor $reifiedType, 
                                                                             @Ignore TypeDescriptor $reifiedArguments, 
                                                                             String name, 
                                                                             @Name("types") @Sequenced Sequential<? extends ceylon.language.meta.model.Type<?>> types) {
        
        checkInit();
        final FunctionDeclarationImpl method = declaration.findMethod(name);
        if(method == null)
            return null;
        MemberLookup<FunctionDeclarationImpl, Container> lookup = lookupMember(FunctionDeclarationImpl.$TypeDescriptor$, $reifiedContainer, method);
        if(lookup == null)
            return null;
        
        return lookup.declaration.memberApply($reifiedContainer, $reifiedType, $reifiedArguments, lookup.containerMetamodel, types);
    }

    @SuppressWarnings({ "hiding", "unchecked", "rawtypes" })
    @Ignore
    @Override
    public <Container, Type, Arguments extends Sequential<? extends Object>>
    ceylon.language.meta.model.Method<Container, Type, Arguments> getDeclaredMethod(@Ignore TypeDescriptor $reifiedContainer, 
                                                                         @Ignore TypeDescriptor $reifiedType, 
                                                                         @Ignore TypeDescriptor $reifiedArguments, 
                                                                         String name){
        
        return getDeclaredMethod($reifiedContainer, $reifiedType, $reifiedArguments, name, 
                (Sequential<? extends ceylon.language.meta.model.Type<?>>)(Sequential)empty_.get_());
    }

    @SuppressWarnings({ "unchecked", "hiding" })
    @Override
    @TypeParameters({
        @TypeParameter(value = "Container"),
        @TypeParameter(value = "Type"),
        @TypeParameter(value = "Arguments", satisfies = "ceylon.language::Sequential<ceylon.language::Anything>")
    })
    @TypeInfo("ceylon.language.meta.model::Method<Container,Type,Arguments>|ceylon.language::Null")
    public <Container, Type, Arguments extends Sequential<? extends Object>>
        ceylon.language.meta.model.Method<Container, Type, Arguments> getDeclaredMethod(@Ignore TypeDescriptor $reifiedContainer, 
                                                                             @Ignore TypeDescriptor $reifiedType, 
                                                                             @Ignore TypeDescriptor $reifiedArguments, 
                                                                             String name, 
                                                                             @Name("types") @Sequenced Sequential<? extends ceylon.language.meta.model.Type<?>> types) {
        
        checkInit();
        // do not return the attribute if the container is not a subtype of this type
        com.redhat.ceylon.model.typechecker.model.Type reifiedContainer = Metamodel.getProducedType($reifiedContainer);
        if(!reifiedContainer.isSubtypeOf(producedType))
            throw new IncompatibleTypeException("Specified container type '"+reifiedContainer.asString()+"' is not a valid subtype of this type");
        final FunctionDeclarationImpl method = declaration.findDeclaredMethod(name);
        if(method == null)
            return null;
        return method.memberApply($reifiedContainer, $reifiedType, $reifiedArguments, 
                (ceylon.language.meta.model.Type<Container>)this, types);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Ignore
    @Override
    public <Container, Kind extends ceylon.language.meta.model.ClassOrInterface<? extends java.lang.Object>>
        ceylon.language.meta.model.Member<Container, Kind> getClassOrInterface(@Ignore TypeDescriptor $reifiedContainer, 
                                                                            @Ignore TypeDescriptor $reifiedKind, 
                                                                            String name){
        
        return getClassOrInterface($reifiedContainer, $reifiedKind, name, 
                (Sequential<? extends ceylon.language.meta.model.Type<?>>)(Sequential)empty_.get_());
    }

    @Override
    @TypeParameters({
        @TypeParameter(value = "Container"),
        @TypeParameter(value = "Kind", satisfies = "ceylon.language.meta.model::ClassOrInterface<ceylon.language::Anything,ceylon.language::Nothing>")
    })
    @TypeInfo("ceylon.language.meta.model::Member<Container,Kind>|ceylon.language::Null")
    public <Container, Kind extends ceylon.language.meta.model.ClassOrInterface<? extends java.lang.Object>>
        ceylon.language.meta.model.Member<Container, Kind> getClassOrInterface(@Ignore TypeDescriptor $reifiedContainer, 
                                                                            @Ignore TypeDescriptor $reifiedKind, 
                                                                            String name, 
                                                                            @Name("types") @Sequenced Sequential<? extends ceylon.language.meta.model.Type<?>> types) {
        
        checkInit();
        final ClassOrInterfaceDeclarationImpl type = declaration.findType(name);
        if(type == null)
            return null;
        MemberLookup<ClassOrInterfaceDeclarationImpl, Container> lookup = lookupMember(ClassOrInterfaceDeclarationImpl.$TypeDescriptor$, $reifiedContainer, type);
        if(lookup == null)
            return null;
        return applyClassOrInterface($reifiedContainer, $reifiedKind, lookup.declaration, types);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Ignore
    @Override
    public <Container, Kind extends ceylon.language.meta.model.ClassOrInterface<? extends java.lang.Object>>
        ceylon.language.meta.model.Member<Container, Kind> getDeclaredClassOrInterface(@Ignore TypeDescriptor $reifiedContainer, 
                                                                            @Ignore TypeDescriptor $reifiedKind, 
                                                                            String name){
        
        return getDeclaredClassOrInterface($reifiedContainer, $reifiedKind, name, 
                (Sequential<? extends ceylon.language.meta.model.Type<?>>)(Sequential)empty_.get_());
    }

    @Override
    @TypeParameters({
        @TypeParameter(value = "Container"),
        @TypeParameter(value = "Kind", satisfies = "ceylon.language.meta.model::ClassOrInterface<ceylon.language::Anything,ceylon.language::Nothing>")
    })
    @TypeInfo("ceylon.language.meta.model::Member<Container,Kind>|ceylon.language::Null")
    public <Container, Kind extends ceylon.language.meta.model.ClassOrInterface<? extends java.lang.Object>>
        ceylon.language.meta.model.Member<Container, Kind> getDeclaredClassOrInterface(@Ignore TypeDescriptor $reifiedContainer, 
                                                                            @Ignore TypeDescriptor $reifiedKind, 
                                                                            String name, 
                                                                            @Name("types") @Sequenced Sequential<? extends ceylon.language.meta.model.Type<?>> types) {
        
        checkInit();
        // do not return the attribute if the container is not a subtype of this type
        com.redhat.ceylon.model.typechecker.model.Type reifiedContainer = Metamodel.getProducedType($reifiedContainer);
        if(!reifiedContainer.isSubtypeOf(producedType))
            throw new IncompatibleTypeException("Specified container type '"+reifiedContainer.asString()+"' is not a valid subtype of this type");
        final ClassOrInterfaceDeclarationImpl type = declaration.findDeclaredType(name);
        if(type == null)
            return null;
        return applyClassOrInterface($reifiedContainer, $reifiedKind, type, types);
    }
    
    @SuppressWarnings("rawtypes")
    private <Container, Kind extends ceylon.language.meta.model.ClassOrInterface<?>>
    ceylon.language.meta.model.Member<Container, Kind> applyClassOrInterface(@Ignore TypeDescriptor $reifiedContainer, 
                                                                        @Ignore TypeDescriptor $reifiedKind, 
                                                                        ClassOrInterfaceDeclarationImpl type, 
                                                                        Sequential<? extends ceylon.language.meta.model.Type<?>> types) {
        if(type == null)
            return null;
        ceylon.language.meta.model.Type<Container> appliedContainer = getAppliedContainer($reifiedContainer, type);
        Member<Container, Kind> member = type.getAppliedClassOrInterface(this.$reifiedType, $reifiedKind, types, appliedContainer);

        // This is all very ugly but we're trying to make it cheaper and friendlier than just checking the full type and showing
        // implementation types to the user, such as AppliedMemberClass
        TypeDescriptor actualReifiedContainer;
        TypeDescriptor actualKind;
        
        if(member instanceof MemberClassImpl){
            actualReifiedContainer = ((MemberClassImpl)member).$reifiedContainer;
            actualKind = TypeDescriptor.klass(ceylon.language.meta.model.Class.class,
                    ((MemberClassImpl) member).$reifiedType, 
                    ((MemberClassImpl) member).$reifiedArguments);
        }else{
            actualReifiedContainer = ((MemberInterfaceImpl)member).$reifiedContainer;
            actualKind = TypeDescriptor.klass(ceylon.language.meta.model.Interface.class,
                    ((MemberInterfaceImpl) member).$reifiedType);
        }
        
        Metamodel.checkReifiedTypeArgument("getClassOrInterface", "Member<$1,$2>&$2", 
                Variance.IN, Metamodel.getProducedType(actualReifiedContainer), $reifiedContainer, 
                Variance.OUT, Metamodel.getProducedType(actualKind), $reifiedKind);

        return member;
    }
    
    @SuppressWarnings("unchecked")
    private <Container> ClassOrInterfaceImpl<Container> getAppliedContainer(@Ignore TypeDescriptor $reifiedContainer, 
            NestableDeclarationImpl decl) {
        ClassOrInterfaceDeclarationImpl valueContainer = (ClassOrInterfaceDeclarationImpl) decl.getContainer();
        if(valueContainer != declaration){
            com.redhat.ceylon.model.typechecker.model.Type valueContainerType = this.producedType.getSupertype((com.redhat.ceylon.model.typechecker.model.TypeDeclaration)valueContainer.declaration);
            return (ClassOrInterfaceImpl<Container>) Metamodel.getAppliedMetamodel(valueContainerType);
        }else{
            return (ClassOrInterfaceImpl<Container>) this;
        }
    }

    @SuppressWarnings({ "hiding", "unchecked", "rawtypes" })
    @Ignore
    @Override
    public <Container, Type, Arguments extends ceylon.language.Sequential<? extends java.lang.Object>>
        ceylon.language.meta.model.MemberClass<Container, Type, Arguments> $getClass(@Ignore TypeDescriptor $reifiedContainer, 
                                                                            @Ignore TypeDescriptor $reifiedType, 
                                                                            @Ignore TypeDescriptor $reifiedArguments, 
                                                                            String name){
        
        return $getClass($reifiedContainer, $reifiedType, $reifiedArguments, name, 
                (Sequential<? extends ceylon.language.meta.model.Type<?>>)(Sequential)empty_.get_());
    }

    @SuppressWarnings("hiding")
    @Override
    @TypeParameters({
        @TypeParameter(value = "Container"),
        @TypeParameter(value = "Type"),
        @TypeParameter(value = "Arguments", satisfies = "ceylon.language::Sequential<ceylon.language::Anything>")
    })
    @TypeInfo("ceylon.language.meta.model::MemberClass<Container,Type,Arguments>|ceylon.language::Null")
    public <Container, Type, Arguments extends ceylon.language.Sequential<? extends java.lang.Object>>
    ceylon.language.meta.model.MemberClass<Container, Type, Arguments> $getClass(@Ignore TypeDescriptor $reifiedContainer, 
                                                                            @Ignore TypeDescriptor $reifiedType, 
                                                                            @Ignore TypeDescriptor $reifiedArguments, 
                                                                            String name, 
                                                                            @Name("types") @Sequenced Sequential<? extends ceylon.language.meta.model.Type<?>> types) {
        
        checkInit();
        final ClassOrInterfaceDeclarationImpl type = declaration.findType(name);
        if(type == null)
            return null;
        if(type instanceof ClassDeclarationImpl == false)
            throw new IncompatibleTypeException("Specified member is not a class: "+name);
        MemberLookup<ClassDeclarationImpl, Container> lookup = lookupMember(ClassDeclarationImpl.$TypeDescriptor$, $reifiedContainer, (ClassDeclarationImpl)type);
        if(lookup == null)
            return null;
        return lookup.declaration.memberClassApply($reifiedContainer, $reifiedType, $reifiedArguments, 
                                                   lookup.containerMetamodel, types);
    }
    
    @SuppressWarnings({ "hiding", "unchecked", "rawtypes" })
    @Ignore
    @Override
    public <Container, Type, Arguments extends ceylon.language.Sequential<? extends java.lang.Object>>
        ceylon.language.meta.model.MemberClass<Container, Type, Arguments> getDeclaredClass(@Ignore TypeDescriptor $reifiedContainer, 
                                                                            @Ignore TypeDescriptor $reifiedType, 
                                                                            @Ignore TypeDescriptor $reifiedArguments, 
                                                                            String name){
        
        return getDeclaredClass($reifiedContainer, $reifiedType, $reifiedArguments, name, 
                (Sequential<? extends ceylon.language.meta.model.Type<?>>)(Sequential)empty_.get_());
    }

    @SuppressWarnings({ "unchecked", "hiding" })
    @Override
    @TypeParameters({
        @TypeParameter(value = "Container"),
        @TypeParameter(value = "Type"),
        @TypeParameter(value = "Arguments", satisfies = "ceylon.language::Sequential<ceylon.language::Anything>")
    })
    @TypeInfo("ceylon.language.meta.model::MemberClass<Container,Type,Arguments>|ceylon.language::Null")
    public <Container, Type, Arguments extends ceylon.language.Sequential<? extends java.lang.Object>>
    ceylon.language.meta.model.MemberClass<Container, Type, Arguments> getDeclaredClass(@Ignore TypeDescriptor $reifiedContainer, 
                                                                            @Ignore TypeDescriptor $reifiedType, 
                                                                            @Ignore TypeDescriptor $reifiedArguments, 
                                                                            String name, 
                                                                            @Name("types") @Sequenced Sequential<? extends ceylon.language.meta.model.Type<?>> types) {
        
        checkInit();
        // do not return the attribute if the container is not a subtype of this type
        com.redhat.ceylon.model.typechecker.model.Type reifiedContainer = Metamodel.getProducedType($reifiedContainer);
        if(!reifiedContainer.isSubtypeOf(producedType))
            throw new IncompatibleTypeException("Specified container type '"+reifiedContainer.asString()+"' is not a valid subtype of this type");
        final ClassOrInterfaceDeclarationImpl type = declaration.findDeclaredType(name);
        if(type == null)
            return null;
        if(type instanceof ClassDeclarationImpl == false)
            throw new IncompatibleTypeException("Specified member is not a class: "+name);
        return ((ClassDeclarationImpl)type).memberClassApply($reifiedContainer, $reifiedType, $reifiedArguments, 
                                                  (ceylon.language.meta.model.Type<Container>)this, types);
    }

    @SuppressWarnings({ "hiding", "unchecked", "rawtypes" })
    @Ignore
    @Override
    public <Container, Type>
        ceylon.language.meta.model.MemberInterface<Container, Type> getInterface(@Ignore TypeDescriptor $reifiedContainer, 
                                                                            @Ignore TypeDescriptor $reifiedType, 
                                                                            String name){
        
        return getInterface($reifiedContainer, $reifiedType, name, 
                (Sequential<? extends ceylon.language.meta.model.Type<?>>)(Sequential)empty_.get_());
    }

    @SuppressWarnings({ "unchecked", "hiding" })
    @Override
    @TypeParameters({
        @TypeParameter(value = "Container"),
        @TypeParameter(value = "Type"),
    })
    @TypeInfo("ceylon.language.meta.model::MemberInterface<Container,Type>|ceylon.language::Null")
    public <Container, Type>
    ceylon.language.meta.model.MemberInterface<Container, Type> getInterface(@Ignore TypeDescriptor $reifiedContainer, 
                                                                            @Ignore TypeDescriptor $reifiedType, 
                                                                            String name, 
                                                                            @Name("types") @Sequenced Sequential<? extends ceylon.language.meta.model.Type<?>> types) {
        
        checkInit();
        final ClassOrInterfaceDeclarationImpl type = declaration.findType(name);
        if(type == null)
            return null;
        if(type instanceof InterfaceDeclarationImpl == false)
            throw new IncompatibleTypeException("Specified member is not an interface: "+name);
        MemberLookup<InterfaceDeclarationImpl, Container> lookup = lookupMember(InterfaceDeclarationImpl.$TypeDescriptor$, $reifiedContainer, (InterfaceDeclarationImpl)type);
        if(lookup == null)
            return null;
        
        return (ceylon.language.meta.model.MemberInterface<Container, Type>) 
                lookup.declaration.memberApply($reifiedContainer, $reifiedType, lookup.containerMetamodel, types);
    }

    @SuppressWarnings({ "hiding", "unchecked", "rawtypes" })
    @Ignore
    @Override
    public <Container, Type>
        ceylon.language.meta.model.MemberInterface<Container, Type> getDeclaredInterface(@Ignore TypeDescriptor $reifiedContainer, 
                                                                            @Ignore TypeDescriptor $reifiedType, 
                                                                            String name){
        
        return getDeclaredInterface($reifiedContainer, $reifiedType, name, 
                (Sequential<? extends ceylon.language.meta.model.Type<?>>)(Sequential)empty_.get_());
    }

    @SuppressWarnings({ "unchecked", "hiding" })
    @Override
    @TypeParameters({
        @TypeParameter(value = "Container"),
        @TypeParameter(value = "Type"),
    })
    @TypeInfo("ceylon.language.meta.model::MemberInterface<Container,Type>|ceylon.language::Null")
    public <Container, Type>
    ceylon.language.meta.model.MemberInterface<Container, Type> getDeclaredInterface(@Ignore TypeDescriptor $reifiedContainer, 
                                                                            @Ignore TypeDescriptor $reifiedType, 
                                                                            String name, 
                                                                            @Name("types") @Sequenced Sequential<? extends ceylon.language.meta.model.Type<?>> types) {
        
        checkInit();
        // do not return the attribute if the container is not a subtype of this type
        com.redhat.ceylon.model.typechecker.model.Type reifiedContainer = Metamodel.getProducedType($reifiedContainer);
        if(!reifiedContainer.isSubtypeOf(producedType))
            throw new IncompatibleTypeException("Specified container type '"+reifiedContainer.asString()+"' is not a valid subtype of this type");
        final ClassOrInterfaceDeclarationImpl type = declaration.findDeclaredType(name);
        if(type == null)
            return null;
        if(type instanceof InterfaceDeclarationImpl == false)
            throw new IncompatibleTypeException("Specified member is not an interface: "+name);
        return (ceylon.language.meta.model.MemberInterface<Container, Type>) 
                type.memberApply($reifiedContainer, $reifiedType, 
                                 (ceylon.language.meta.model.Type<Container>)this, types);
    }

    @Override
    @TypeParameters({
        @TypeParameter(value = "Container"),
        @TypeParameter(value = "Get"),
        @TypeParameter(value = "Set")
    })
    @TypeInfo("ceylon.language.meta.model::Attribute<Container,Get,Set>|ceylon.language::Null")
    public <Container, Get, Set>
        ceylon.language.meta.model.Attribute<Container, Get, Set> getAttribute(@Ignore TypeDescriptor $reifiedContainer, 
                                                                        @Ignore TypeDescriptor $reifiedGet, 
                                                                        @Ignore TypeDescriptor $reifiedSet, 
                                                                        String name) {
        
        //checkInit();
        ValueDeclarationImpl value = declaration.findValue(name);
        if(value == null)
            return null;
        
        MemberLookup<ValueDeclarationImpl, Container> lookup = lookupMember(ValueDeclarationImpl.$TypeDescriptor$, $reifiedContainer, value);
        if(lookup == null)
            return null;
        
        return lookup.declaration.<Container, Get, Set>memberApply($reifiedContainer, $reifiedGet, $reifiedSet, lookup.containerMetamodel);
    }

    @SuppressWarnings({ "unchecked" })
    @Override
    @TypeParameters({
        @TypeParameter(value = "Container"),
        @TypeParameter(value = "Get"),
        @TypeParameter(value = "Set")
    })
    @TypeInfo("ceylon.language.meta.model::Attribute<Container,Get,Set>|ceylon.language::Null")
    public <Container, Get, Set>
        ceylon.language.meta.model.Attribute<Container, Get, Set> getDeclaredAttribute(@Ignore TypeDescriptor $reifiedContainer, 
                                                                        @Ignore TypeDescriptor $reifiedGet, 
                                                                        @Ignore TypeDescriptor $reifiedSet, 
                                                                        String name) {
        
        checkInit();
        // do not return the attribute if the container is not a subtype of this type
        com.redhat.ceylon.model.typechecker.model.Type reifiedContainer = Metamodel.getProducedType($reifiedContainer);
        if(!reifiedContainer.isSubtypeOf(producedType))
            throw new IncompatibleTypeException("Specified container type '"+reifiedContainer.asString()+"' is not a valid subtype of this type");
        final ValueDeclarationImpl value = declaration.findDeclaredValue(name);
        if(value == null)
            return null;
        return value.memberApply($reifiedContainer, $reifiedGet, $reifiedSet, 
                (ceylon.language.meta.model.Type<Container>)this);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    @Ignore
    public <Container, Get, Set>
        ceylon.language.Sequential<? extends ceylon.language.meta.model.Attribute<? super Container, ? extends Get, ? super Set>> 
            getDeclaredAttributes(@Ignore TypeDescriptor $reifiedContainer, 
                                  @Ignore TypeDescriptor $reifiedGet, 
                                  @Ignore TypeDescriptor $reifiedSet) {
        return getDeclaredAttributes($reifiedContainer, $reifiedGet, $reifiedSet, (ceylon.language.Sequential)empty_.get_());
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    @TypeParameters({
        @TypeParameter(value = "Container"),
        @TypeParameter(value = "Get"),
        @TypeParameter(value = "Set")
    })
    @TypeInfo("ceylon.language::Sequential<ceylon.language.meta.model::Attribute<Container,Get,Set>>")
    public <Container, Get, Set>
        ceylon.language.Sequential<? extends ceylon.language.meta.model.Attribute<? super Container, ? extends Get, ? super Set>> 
            getDeclaredAttributes(@Ignore TypeDescriptor $reifiedContainer, 
                                  @Ignore TypeDescriptor $reifiedGet, 
                                  @Ignore TypeDescriptor $reifiedSet,
                                  @Sequenced
                                  ceylon.language.Sequential<? extends ceylon.language.meta.model.Type<? extends java.lang.annotation.Annotation>> annotations) {
        checkInit();
        
        // check the container type first
        com.redhat.ceylon.model.typechecker.model.Type reifiedContainer = Metamodel.getProducedType($reifiedContainer);
        if(!reifiedContainer.isSubtypeOf(this.producedType))
            return (ceylon.language.Sequential)empty_.get_();
        
        Sequential<? extends ValueDeclaration> declaredDeclarations = declaration.<ValueDeclaration>declaredMemberDeclarations(ValueDeclaration.$TypeDescriptor$);
        if(declaredDeclarations.getEmpty())
            return (ceylon.language.Sequential)empty_.get_();
        
        com.redhat.ceylon.model.typechecker.model.Type reifiedGet = Metamodel.getProducedType($reifiedGet);
        com.redhat.ceylon.model.typechecker.model.Type reifiedSet = Metamodel.getProducedType($reifiedSet);
        
        Iterator<?> iterator = declaredDeclarations.iterator();
        Object it;
        TypeDescriptor[] annotationTypeDescriptors = Metamodel.getTypeDescriptors(annotations);
        TypeDescriptor reifiedKind = TypeDescriptor.klass(ceylon.language.meta.model.Attribute.class, $reifiedType, $reifiedGet, $reifiedSet);
        ArrayList<ceylon.language.meta.model.Attribute<? super Container,? extends Get,? super Set>> members = 
                new ArrayList<ceylon.language.meta.model.Attribute<? super Container,? extends Get,? super Set>>((int) declaredDeclarations.getSize());

        while((it = iterator.next()) != finished_.get_()){
            ValueDeclarationImpl decl = (ValueDeclarationImpl) it;

            // ATM this is an AND WRT annotation types: all must be present
            if(!Metamodel.hasAllAnnotations(decl, annotationTypeDescriptors))
                continue;

            addAttributeIfCompatible($reifiedContainer, $reifiedGet, $reifiedSet, members, decl, this.producedType, 
                    (ceylon.language.meta.model.Type<Container>)this, reifiedGet, reifiedSet);
        }
        Attribute[] array = members.toArray(new ceylon.language.meta.model.Attribute[0]);
		ObjectArrayIterable<Attribute> iterable = 
				new ObjectArrayIterable<ceylon.language.meta.model.Attribute>(reifiedKind, array);
		return (ceylon.language.Sequential) iterable.sequence();
    }
    
    private <Container,Get,Set> void addAttributeIfCompatible(@Ignore TypeDescriptor $reifiedContainer,
            @Ignore TypeDescriptor $reifiedGet,
            @Ignore TypeDescriptor $reifiedSet,
            ArrayList<ceylon.language.meta.model.Attribute<? super Container,? extends Get,? super Set>> members,
            ValueDeclarationImpl decl, com.redhat.ceylon.model.typechecker.model.Type qualifyingType, 
            ceylon.language.meta.model.Type<Container> qualifyingMetamodel,
            com.redhat.ceylon.model.typechecker.model.Type reifiedGet, 
            com.redhat.ceylon.model.typechecker.model.Type reifiedSet){
        // now the types
        Reference producedReference = decl.declaration.appliedReference(qualifyingType, NO_TYPE_ARGS);
        com.redhat.ceylon.model.typechecker.model.Type type = producedReference.getType();
        if(!type.isSubtypeOf(reifiedGet))
            return;
        com.redhat.ceylon.model.typechecker.model.Type setType = decl.getVariable() ? type : decl.declaration.getUnit().getNothingType();
        if(!reifiedSet.isSubtypeOf(setType))
            return;
        // it's compatible!
        members.add(decl.<Container,Get,Set>memberApply($reifiedContainer, $reifiedGet, $reifiedSet, qualifyingMetamodel));
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    @Ignore
    public <Container, Get, Set>
        ceylon.language.Sequential<? extends ceylon.language.meta.model.Attribute<? super Container, ? extends Get, ? super Set>> 
            getAttributes(@Ignore TypeDescriptor $reifiedContainer, 
                          @Ignore TypeDescriptor $reifiedGet, 
                          @Ignore TypeDescriptor $reifiedSet) {
        return getAttributes($reifiedContainer, $reifiedGet, $reifiedSet, (ceylon.language.Sequential)empty_.get_());
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    @TypeParameters({
        @TypeParameter(value = "Container"),
        @TypeParameter(value = "Get"),
        @TypeParameter(value = "Set")
    })
    @TypeInfo("ceylon.language::Sequential<ceylon.language.meta.model::Attribute<Container,Get,Set>>")
    public <Container, Get, Set>
        ceylon.language.Sequential<? extends ceylon.language.meta.model.Attribute<? super Container, ? extends Get, ? super Set>> 
            getAttributes(@Ignore TypeDescriptor $reifiedContainer, 
                          @Ignore TypeDescriptor $reifiedGet, 
                          @Ignore TypeDescriptor $reifiedSet,
                          @Sequenced
                          ceylon.language.Sequential<? extends ceylon.language.meta.model.Type<? extends java.lang.annotation.Annotation>> annotations) {
        checkInit();
        
        Sequential<? extends ValueDeclaration> declaredDeclarations = declaration.<ValueDeclaration>memberDeclarations(ValueDeclaration.$TypeDescriptor$);
        if(declaredDeclarations.getEmpty())
            return (ceylon.language.Sequential)empty_.get_();
        
        com.redhat.ceylon.model.typechecker.model.Type reifiedContainer = Metamodel.getProducedType($reifiedContainer);
        com.redhat.ceylon.model.typechecker.model.Type reifiedGet = Metamodel.getProducedType($reifiedGet);
        com.redhat.ceylon.model.typechecker.model.Type reifiedSet = Metamodel.getProducedType($reifiedSet);
        
        Iterator<?> iterator = declaredDeclarations.iterator();
        Object it;
        TypeDescriptor[] annotationTypeDescriptors = Metamodel.getTypeDescriptors(annotations);
        TypeDescriptor reifiedKind = TypeDescriptor.klass(ceylon.language.meta.model.Attribute.class, $reifiedContainer, $reifiedGet, $reifiedSet);
        ArrayList<ceylon.language.meta.model.Attribute<? super Container,? extends Get,? super Set>> members = 
                new ArrayList<ceylon.language.meta.model.Attribute<? super Container,? extends Get,? super Set>>((int) declaredDeclarations.getSize());

        while((it = iterator.next()) != finished_.get_()){
            ValueDeclarationImpl decl = (ValueDeclarationImpl) it;

            MemberLookup<ValueDeclarationImpl, Container> lookup = lookupMember(ValueDeclarationImpl.$TypeDescriptor$, $reifiedContainer, reifiedContainer, decl);
            if(lookup == null)
                continue;

            // ATM this is an AND WRT annotation types: all must be present
            if(!Metamodel.hasAllAnnotations(lookup.declaration, annotationTypeDescriptors))
                continue;

            addAttributeIfCompatible($reifiedContainer, $reifiedGet, $reifiedSet, members, lookup.declaration, 
                            lookup.qualifyingType, lookup.containerMetamodel, reifiedGet, reifiedSet);
        }
        Attribute[] array = members.toArray(new ceylon.language.meta.model.Attribute[members.size()]);
		ObjectArrayIterable<Attribute> iterable = 
				new ObjectArrayIterable<ceylon.language.meta.model.Attribute>(reifiedKind, array);
		return (ceylon.language.Sequential) iterable.sequence();
    }

    @SuppressWarnings({ "unchecked", "rawtypes", "hiding" })
    @Override
    @Ignore
    public <Container, Type, Arguments extends Sequential<? extends Object>>
        ceylon.language.Sequential<? extends ceylon.language.meta.model.Method<? super Container, ? extends Type, ? super Arguments>> 
            getDeclaredMethods(@Ignore TypeDescriptor $reifiedContainer, 
                               @Ignore TypeDescriptor $reifiedType, 
                               @Ignore TypeDescriptor $reifiedArguments) {
        return getDeclaredMethods($reifiedContainer, $reifiedType, $reifiedArguments, (ceylon.language.Sequential)empty_.get_());
    }

    @SuppressWarnings({ "unchecked", "rawtypes", "hiding" })
    @Override
    @TypeParameters({
        @TypeParameter(value = "Container"),
        @TypeParameter(value = "Type"),
        @TypeParameter(value = "Arguments", satisfies = "ceylon.language::Sequential<ceylon.language::Anything>")
    })
    @TypeInfo("ceylon.language::Sequential<ceylon.language.meta.model::Method<Container,Type,Arguments>>")
    public <Container, Type, Arguments extends Sequential<? extends Object>>
        ceylon.language.Sequential<? extends ceylon.language.meta.model.Method<? super Container, ? extends Type, ? super Arguments>> 
            getDeclaredMethods(@Ignore TypeDescriptor $reifiedContainer, 
                               @Ignore TypeDescriptor $reifiedType, 
                               @Ignore TypeDescriptor $reifiedArguments,
                               @Sequenced
                               ceylon.language.Sequential<? extends ceylon.language.meta.model.Type<? extends java.lang.annotation.Annotation>> annotations) {
        checkInit();
        
        // check the container type first
        com.redhat.ceylon.model.typechecker.model.Type reifiedContainer = Metamodel.getProducedType($reifiedContainer);
        if(!reifiedContainer.isSubtypeOf(this.producedType))
            return (ceylon.language.Sequential)empty_.get_();
        
        Sequential<? extends FunctionDeclaration> declaredDeclarations = declaration.<FunctionDeclaration>declaredMemberDeclarations(FunctionDeclaration.$TypeDescriptor$);
        if(declaredDeclarations.getEmpty())
            return (ceylon.language.Sequential)empty_.get_();
        
        com.redhat.ceylon.model.typechecker.model.Type reifiedType = Metamodel.getProducedType($reifiedType);
        com.redhat.ceylon.model.typechecker.model.Type reifiedArguments = Metamodel.getProducedType($reifiedArguments);
        
        Iterator<?> iterator = declaredDeclarations.iterator();
        Object it;
        TypeDescriptor[] annotationTypeDescriptors = Metamodel.getTypeDescriptors(annotations);
        TypeDescriptor reifiedKind = TypeDescriptor.klass(ceylon.language.meta.model.Method.class, $reifiedContainer, $reifiedType, $reifiedArguments);
        ArrayList<ceylon.language.meta.model.Method<? super Container,? extends Type,? super Arguments>> members = 
                new ArrayList<ceylon.language.meta.model.Method<? super Container,? extends Type,? super Arguments>>((int) declaredDeclarations.getSize());

        while((it = iterator.next()) != finished_.get_()){
            FunctionDeclarationImpl decl = (FunctionDeclarationImpl) it;

            // skip generic functions
            if(!decl.getTypeParameterDeclarations().getEmpty())
                continue;
            
            // ATM this is an AND WRT annotation types: all must be present
            if(!Metamodel.hasAllAnnotations(decl, annotationTypeDescriptors))
                continue;
            
            addMethodIfCompatible($reifiedContainer, $reifiedType, $reifiedArguments, members, decl, producedType, 
                    (ClassOrInterfaceImpl<Container>)this, reifiedType, reifiedArguments);
        }
        ceylon.language.meta.model.Method[] array = members.toArray(new ceylon.language.meta.model.Method[0]);
		ObjectArrayIterable<ceylon.language.meta.model.Method> iterable = 
				new ObjectArrayIterable<ceylon.language.meta.model.Method>(reifiedKind, array);
		return (ceylon.language.Sequential) iterable.sequence();
    }

    @SuppressWarnings({ "unchecked", "rawtypes", "hiding" })
    @Override
    @Ignore
    public <Container, Type, Arguments extends Sequential<? extends Object>>
        ceylon.language.Sequential<? extends ceylon.language.meta.model.Method<? super Container, ? extends Type, ? super Arguments>> 
            getMethods(@Ignore TypeDescriptor $reifiedContainer, 
                       @Ignore TypeDescriptor $reifiedType, 
                       @Ignore TypeDescriptor $reifiedArguments) {
        return getMethods($reifiedContainer, $reifiedType, $reifiedArguments, (ceylon.language.Sequential)empty_.get_());
    }

    @SuppressWarnings({ "unchecked", "rawtypes", "hiding" })
    @Override
    @TypeParameters({
        @TypeParameter(value = "Container"),
        @TypeParameter(value = "Type"),
        @TypeParameter(value = "Arguments", satisfies = "ceylon.language::Sequential<ceylon.language::Anything>")
    })
    @TypeInfo("ceylon.language::Sequential<ceylon.language.meta.model::Method<Container,Type,Arguments>>")
    public <Container, Type, Arguments extends Sequential<? extends Object>>
        ceylon.language.Sequential<? extends ceylon.language.meta.model.Method<? super Container, ? extends Type, ? super Arguments>> 
            getMethods(@Ignore TypeDescriptor $reifiedContainer, 
                       @Ignore TypeDescriptor $reifiedType, 
                       @Ignore TypeDescriptor $reifiedArguments,
                       @Sequenced
                       ceylon.language.Sequential<? extends ceylon.language.meta.model.Type<? extends java.lang.annotation.Annotation>> annotations) {
        checkInit();
        
        Sequential<? extends FunctionDeclaration> declaredDeclarations = declaration.<FunctionDeclaration>memberDeclarations(FunctionDeclaration.$TypeDescriptor$);
        if(declaredDeclarations.getEmpty())
            return (ceylon.language.Sequential)empty_.get_();
        
        com.redhat.ceylon.model.typechecker.model.Type reifiedContainer = Metamodel.getProducedType($reifiedContainer);
        com.redhat.ceylon.model.typechecker.model.Type reifiedType = Metamodel.getProducedType($reifiedType);
        com.redhat.ceylon.model.typechecker.model.Type reifiedArguments = Metamodel.getProducedType($reifiedArguments);
        
        Iterator<?> iterator = declaredDeclarations.iterator();
        Object it;
        TypeDescriptor[] annotationTypeDescriptors = Metamodel.getTypeDescriptors(annotations);
        TypeDescriptor reifiedKind = TypeDescriptor.klass(ceylon.language.meta.model.Method.class, $reifiedContainer, $reifiedType, $reifiedArguments);
        ArrayList<ceylon.language.meta.model.Method<? super Container,? extends Type,? super Arguments>> members = 
                new ArrayList<ceylon.language.meta.model.Method<? super Container,? extends Type,? super Arguments>>((int) declaredDeclarations.getSize());

        while((it = iterator.next()) != finished_.get_()){
            FunctionDeclarationImpl decl = (FunctionDeclarationImpl) it;

            MemberLookup<FunctionDeclarationImpl, Container> lookup = lookupMember(FunctionDeclarationImpl.$TypeDescriptor$, $reifiedContainer, reifiedContainer, decl);
            if(lookup == null)
                continue;

            // skip generic functions
            if(!lookup.declaration.getTypeParameterDeclarations().getEmpty())
                continue;
            
            // ATM this is an AND WRT annotation types: all must be present
            if(!Metamodel.hasAllAnnotations(lookup.declaration, annotationTypeDescriptors))
                continue;
            
            addMethodIfCompatible($reifiedContainer, $reifiedType, $reifiedArguments, members, lookup.declaration, lookup.qualifyingType, 
                            lookup.containerMetamodel, reifiedType, reifiedArguments);
        }
        ceylon.language.meta.model.Method[] array = members.toArray(new ceylon.language.meta.model.Method[0]);
		ObjectArrayIterable<ceylon.language.meta.model.Method> iterable = 
				new ObjectArrayIterable<ceylon.language.meta.model.Method>(reifiedKind, array);
		return (ceylon.language.Sequential) iterable.sequence();
    }

    @SuppressWarnings({ "hiding" })
    private <Container,Type,Arguments extends Sequential<? extends Object>> void addMethodIfCompatible(@Ignore TypeDescriptor $reifiedContainer,
            @Ignore TypeDescriptor $reifiedType,
            @Ignore TypeDescriptor $reifiedArguments,
            ArrayList<ceylon.language.meta.model.Method<? super Container,? extends Type,? super Arguments>> members,
            FunctionDeclarationImpl decl, com.redhat.ceylon.model.typechecker.model.Type qualifyingType, 
            ClassOrInterfaceImpl<Container> containerMetamodel,
            com.redhat.ceylon.model.typechecker.model.Type reifiedType, 
            com.redhat.ceylon.model.typechecker.model.Type reifiedArguments){
        // now the types
        Reference producedReference = decl.declaration.appliedReference(qualifyingType, Collections.<com.redhat.ceylon.model.typechecker.model.Type>emptyList());
        com.redhat.ceylon.model.typechecker.model.Type type = producedReference.getType();
        if(!type.isSubtypeOf(reifiedType))
            return;
        com.redhat.ceylon.model.typechecker.model.Type argumentsType = Metamodel.getProducedTypeForArguments(decl.declaration.getUnit(), (Functional) decl.declaration, producedReference);
        if(!reifiedArguments.isSubtypeOf(argumentsType))
            return;
        // it's compatible!
        members.add(decl.<Container,Type,Arguments>memberApply($reifiedContainer, $reifiedType, $reifiedArguments, containerMetamodel));
    }

    @SuppressWarnings({ "unchecked", "rawtypes", "hiding" })
    @Override
    @Ignore
    public <Container, Type, Arguments extends Sequential<? extends Object>>
        ceylon.language.Sequential<? extends ceylon.language.meta.model.MemberClass<? super Container, ? extends Type, ? super Arguments>> 
            getDeclaredClasses(@Ignore TypeDescriptor $reifiedContainer, 
                               @Ignore TypeDescriptor $reifiedType, 
                               @Ignore TypeDescriptor $reifiedArguments) {
        return getDeclaredClasses($reifiedContainer, $reifiedType, $reifiedArguments, (ceylon.language.Sequential)empty_.get_());
    }

    @SuppressWarnings({ "unchecked", "rawtypes", "hiding" })
    @Override
    @TypeParameters({
        @TypeParameter(value = "Container"),
        @TypeParameter(value = "Type"),
        @TypeParameter(value = "Arguments", satisfies = "ceylon.language::Sequential<ceylon.language::Anything>")
    })
    @TypeInfo("ceylon.language::Sequential<ceylon.language.meta.model::MemberClass<Container,Type,Arguments>>")
    public <Container, Type, Arguments extends Sequential<? extends Object>>
        ceylon.language.Sequential<? extends ceylon.language.meta.model.MemberClass<? super Container, ? extends Type, ? super Arguments>> 
            getDeclaredClasses(@Ignore TypeDescriptor $reifiedContainer, 
                               @Ignore TypeDescriptor $reifiedType, 
                               @Ignore TypeDescriptor $reifiedArguments,
                               @Sequenced
                               ceylon.language.Sequential<? extends ceylon.language.meta.model.Type<? extends java.lang.annotation.Annotation>> annotations) {
        checkInit();
        
        // check the container type first
        com.redhat.ceylon.model.typechecker.model.Type reifiedContainer = Metamodel.getProducedType($reifiedContainer);
        if(!reifiedContainer.isSubtypeOf(this.producedType))
            return (ceylon.language.Sequential)empty_.get_();
        
        Sequential<? extends ClassDeclaration> declaredDeclarations = declaration.<ClassDeclaration>declaredMemberDeclarations(ClassDeclaration.$TypeDescriptor$);
        if(declaredDeclarations.getEmpty())
            return (ceylon.language.Sequential)empty_.get_();
        
        com.redhat.ceylon.model.typechecker.model.Type reifiedType = Metamodel.getProducedType($reifiedType);
        com.redhat.ceylon.model.typechecker.model.Type reifiedArguments = Metamodel.getProducedType($reifiedArguments);
        
        Iterator<?> iterator = declaredDeclarations.iterator();
        Object it;
        TypeDescriptor[] annotationTypeDescriptors = Metamodel.getTypeDescriptors(annotations);
        TypeDescriptor reifiedKind = TypeDescriptor.klass(ceylon.language.meta.model.MemberClass.class, $reifiedContainer, $reifiedType, $reifiedArguments);
        ArrayList<ceylon.language.meta.model.MemberClass<? super Container,? extends Type,? super Arguments>> members = 
                new ArrayList<ceylon.language.meta.model.MemberClass<? super Container,? extends Type,? super Arguments>>((int) declaredDeclarations.getSize());

        while((it = iterator.next()) != finished_.get_()){
            ClassDeclarationImpl decl = (ClassDeclarationImpl) it;

            // skip generic classes
            if(!decl.getTypeParameterDeclarations().getEmpty())
                continue;
            
            // ATM this is an AND WRT annotation types: all must be present
            if(!Metamodel.hasAllAnnotations(decl, annotationTypeDescriptors))
                continue;
            
            addClassIfCompatible($reifiedContainer, $reifiedType, $reifiedArguments, members, decl, 
                            producedType, (ClassOrInterfaceImpl<Container>)this, reifiedType, reifiedArguments);
        }
        ceylon.language.meta.model.MemberClass[] array = members.toArray(new ceylon.language.meta.model.MemberClass[0]);
		ObjectArrayIterable<ceylon.language.meta.model.MemberClass> iterable = 
				new ObjectArrayIterable<ceylon.language.meta.model.MemberClass>(reifiedKind, array);
		return (ceylon.language.Sequential) iterable.sequence();
    }

    @SuppressWarnings({ "unchecked", "rawtypes", "hiding" })
    @Override
    @Ignore
    public <Container, Type, Arguments extends Sequential<? extends Object>>
        ceylon.language.Sequential<? extends ceylon.language.meta.model.MemberClass<? super Container, ? extends Type, ? super Arguments>> 
            getClasses(@Ignore TypeDescriptor $reifiedContainer, 
                       @Ignore TypeDescriptor $reifiedType, 
                       @Ignore TypeDescriptor $reifiedArguments) {
        return getClasses($reifiedContainer, $reifiedType, $reifiedArguments, (ceylon.language.Sequential)empty_.get_());
    }

    @SuppressWarnings({ "unchecked", "rawtypes", "hiding" })
    @Override
    @TypeParameters({
        @TypeParameter(value = "Container"),
        @TypeParameter(value = "Type"),
        @TypeParameter(value = "Arguments", satisfies = "ceylon.language::Sequential<ceylon.language::Anything>")
    })
    @TypeInfo("ceylon.language::Sequential<ceylon.language.meta.model::MemberClass<Container,Type,Arguments>>")
    public <Container, Type, Arguments extends Sequential<? extends Object>>
        ceylon.language.Sequential<? extends ceylon.language.meta.model.MemberClass<? super Container, ? extends Type, ? super Arguments>> 
            getClasses(@Ignore TypeDescriptor $reifiedContainer, 
                       @Ignore TypeDescriptor $reifiedType, 
                       @Ignore TypeDescriptor $reifiedArguments,
                       @Sequenced
                       ceylon.language.Sequential<? extends ceylon.language.meta.model.Type<? extends java.lang.annotation.Annotation>> annotations) {
        checkInit();
        
        Sequential<? extends ClassDeclaration> declaredDeclarations = declaration.<ClassDeclaration>memberDeclarations(ClassDeclaration.$TypeDescriptor$);
        if(declaredDeclarations.getEmpty())
            return (ceylon.language.Sequential)empty_.get_();
        
        com.redhat.ceylon.model.typechecker.model.Type reifiedContainer = Metamodel.getProducedType($reifiedContainer);
        com.redhat.ceylon.model.typechecker.model.Type reifiedType = Metamodel.getProducedType($reifiedType);
        com.redhat.ceylon.model.typechecker.model.Type reifiedArguments = Metamodel.getProducedType($reifiedArguments);
        
        Iterator<?> iterator = declaredDeclarations.iterator();
        Object it;
        TypeDescriptor[] annotationTypeDescriptors = Metamodel.getTypeDescriptors(annotations);
        TypeDescriptor reifiedKind = TypeDescriptor.klass(ceylon.language.meta.model.MemberClass.class, $reifiedContainer, $reifiedType, $reifiedArguments);
        ArrayList<ceylon.language.meta.model.MemberClass<? super Container,? extends Type,? super Arguments>> members = 
                new ArrayList<ceylon.language.meta.model.MemberClass<? super Container,? extends Type,? super Arguments>>((int) declaredDeclarations.getSize());

        while((it = iterator.next()) != finished_.get_()){
            ClassDeclarationImpl decl = (ClassDeclarationImpl) it;

            MemberLookup<ClassDeclarationImpl, Container> lookup = lookupMember(ClassDeclarationImpl.$TypeDescriptor$, $reifiedContainer, reifiedContainer, decl);
            if(lookup == null)
                continue;

            // skip generic classes
            if(!lookup.declaration.getTypeParameterDeclarations().getEmpty())
                continue;
            
            // ATM this is an AND WRT annotation types: all must be present
            if(!Metamodel.hasAllAnnotations(lookup.declaration, annotationTypeDescriptors))
                continue;
            
            addClassIfCompatible($reifiedContainer, $reifiedType, $reifiedArguments, members, lookup.declaration, 
                            lookup.qualifyingType, lookup.containerMetamodel, reifiedType, reifiedArguments);
        }
        ceylon.language.meta.model.MemberClass[] array = members.toArray(new ceylon.language.meta.model.MemberClass[0]);
		ObjectArrayIterable<ceylon.language.meta.model.MemberClass> iterable = 
				new ObjectArrayIterable<ceylon.language.meta.model.MemberClass>(reifiedKind, array);
		return (ceylon.language.Sequential) iterable.sequence();
    }

    @SuppressWarnings({ "hiding" })
    private <Container,Type,Arguments extends Sequential<? extends Object>> void addClassIfCompatible(@Ignore TypeDescriptor $reifiedContainer,
            @Ignore TypeDescriptor $reifiedType,
            @Ignore TypeDescriptor $reifiedArguments,
            ArrayList<ceylon.language.meta.model.MemberClass<? super Container,? extends Type,? super Arguments>> members,
            ClassDeclarationImpl decl, com.redhat.ceylon.model.typechecker.model.Type qualifyingType, 
            ClassOrInterfaceImpl<Container> containerMetamodel,
            com.redhat.ceylon.model.typechecker.model.Type reifiedType, 
            com.redhat.ceylon.model.typechecker.model.Type reifiedArguments){
        // now the types
        Reference producedReference = decl.declaration.appliedReference(qualifyingType, Collections.<com.redhat.ceylon.model.typechecker.model.Type>emptyList());
        com.redhat.ceylon.model.typechecker.model.Type type = producedReference.getType();
        if(!type.isSubtypeOf(reifiedType))
            return;
        com.redhat.ceylon.model.typechecker.model.Type argumentsType = Metamodel.getProducedTypeForArguments(decl.declaration.getUnit(), (Functional) decl.declaration, producedReference);
        if(!reifiedArguments.isSubtypeOf(argumentsType))
            return;
        // it's compatible!
        members.add(decl.<Container,Type,Arguments>memberClassApply($reifiedContainer, $reifiedType, $reifiedArguments, containerMetamodel));
    }

    @SuppressWarnings({ "unchecked", "rawtypes", "hiding" })
    @Override
    @Ignore
    public <Container, Type>
        ceylon.language.Sequential<? extends ceylon.language.meta.model.MemberInterface<? super Container, ? extends Type>> 
            getDeclaredInterfaces(@Ignore TypeDescriptor $reifiedContainer, 
                                  @Ignore TypeDescriptor $reifiedType) {
        return getDeclaredInterfaces($reifiedContainer, $reifiedType, (ceylon.language.Sequential)empty_.get_());
    }

    @SuppressWarnings({ "unchecked", "rawtypes", "hiding" })
    @Override
    @TypeParameters({
        @TypeParameter(value = "Container"),
        @TypeParameter(value = "Type"),
    })
    @TypeInfo("ceylon.language::Sequential<ceylon.language.meta.model::MemberInterface<Container,Type>>")
    public <Container, Type>
        ceylon.language.Sequential<? extends ceylon.language.meta.model.MemberInterface<? super Container, ? extends Type>> 
            getDeclaredInterfaces(@Ignore TypeDescriptor $reifiedContainer, 
                                  @Ignore TypeDescriptor $reifiedType, 
                                  @Sequenced
                                  ceylon.language.Sequential<? extends ceylon.language.meta.model.Type<? extends java.lang.annotation.Annotation>> annotations) {
        checkInit();
        
        // check the container type first
        com.redhat.ceylon.model.typechecker.model.Type reifiedContainer = Metamodel.getProducedType($reifiedContainer);
        if(!reifiedContainer.isSubtypeOf(this.producedType))
            return (ceylon.language.Sequential)empty_.get_();
        
        Sequential<? extends InterfaceDeclaration> declaredDeclarations = declaration.<InterfaceDeclaration>declaredMemberDeclarations(InterfaceDeclaration.$TypeDescriptor$);
        if(declaredDeclarations.getEmpty())
            return (ceylon.language.Sequential)empty_.get_();
        
        com.redhat.ceylon.model.typechecker.model.Type reifiedType = Metamodel.getProducedType($reifiedType);
        
        Iterator<?> iterator = declaredDeclarations.iterator();
        Object it;
        TypeDescriptor[] annotationTypeDescriptors = Metamodel.getTypeDescriptors(annotations);
        TypeDescriptor reifiedKind = TypeDescriptor.klass(ceylon.language.meta.model.MemberInterface.class, $reifiedContainer, $reifiedType);
        ArrayList<ceylon.language.meta.model.MemberInterface<? super Container,? extends Type>> members = 
                new ArrayList<ceylon.language.meta.model.MemberInterface<? super Container,? extends Type>>((int) declaredDeclarations.getSize());

        while((it = iterator.next()) != finished_.get_()){
            InterfaceDeclarationImpl decl = (InterfaceDeclarationImpl) it;

            // skip generic classes
            if(!decl.getTypeParameterDeclarations().getEmpty())
                continue;
            
            // ATM this is an AND WRT annotation types: all must be present
            if(!Metamodel.hasAllAnnotations(decl, annotationTypeDescriptors))
                continue;
            
            addInterfaceIfCompatible($reifiedContainer, $reifiedType, members, decl, producedType, 
                            (ClassOrInterfaceImpl<Container>)this, reifiedType);
        }
        ceylon.language.meta.model.MemberInterface[] array = members.toArray(new ceylon.language.meta.model.MemberInterface[0]);
		ObjectArrayIterable<ceylon.language.meta.model.MemberInterface> iterable = 
				new ObjectArrayIterable<ceylon.language.meta.model.MemberInterface>(reifiedKind, array);
		return (ceylon.language.Sequential) iterable.sequence();
    }

    @SuppressWarnings({ "unchecked", "rawtypes", "hiding" })
    @Override
    @Ignore
    public <Container, Type>
        ceylon.language.Sequential<? extends ceylon.language.meta.model.MemberInterface<? super Container, ? extends Type>> 
            getInterfaces(@Ignore TypeDescriptor $reifiedContainer, 
                          @Ignore TypeDescriptor $reifiedType) {
        return getInterfaces($reifiedContainer, $reifiedType, (ceylon.language.Sequential)empty_.get_());
    }

    @SuppressWarnings({ "unchecked", "rawtypes", "hiding" })
    @Override
    @TypeParameters({
        @TypeParameter(value = "Container"),
        @TypeParameter(value = "Type"),
    })
    @TypeInfo("ceylon.language::Sequential<ceylon.language.meta.model::MemberInterface<Container,Type>>")
    public <Container, Type>
        ceylon.language.Sequential<? extends ceylon.language.meta.model.MemberInterface<? super Container, ? extends Type>> 
            getInterfaces(@Ignore TypeDescriptor $reifiedContainer, 
                          @Ignore TypeDescriptor $reifiedType, 
                          @Sequenced
                          ceylon.language.Sequential<? extends ceylon.language.meta.model.Type<? extends java.lang.annotation.Annotation>> annotations) {
        checkInit();
        
        Sequential<? extends InterfaceDeclaration> declaredDeclarations = declaration.<InterfaceDeclaration>memberDeclarations(InterfaceDeclaration.$TypeDescriptor$);
        if(declaredDeclarations.getEmpty())
            return (ceylon.language.Sequential)empty_.get_();
        
        com.redhat.ceylon.model.typechecker.model.Type reifiedContainer = Metamodel.getProducedType($reifiedContainer);
        com.redhat.ceylon.model.typechecker.model.Type reifiedType = Metamodel.getProducedType($reifiedType);
        
        Iterator<?> iterator = declaredDeclarations.iterator();
        Object it;
        TypeDescriptor[] annotationTypeDescriptors = Metamodel.getTypeDescriptors(annotations);
        TypeDescriptor reifiedKind = TypeDescriptor.klass(ceylon.language.meta.model.MemberInterface.class, $reifiedContainer, $reifiedType);
        ArrayList<ceylon.language.meta.model.MemberInterface<? super Container,? extends Type>> members = 
                new ArrayList<ceylon.language.meta.model.MemberInterface<? super Container,? extends Type>>((int) declaredDeclarations.getSize());

        while((it = iterator.next()) != finished_.get_()){
            InterfaceDeclarationImpl decl = (InterfaceDeclarationImpl) it;

            MemberLookup<InterfaceDeclarationImpl, Container> lookup = lookupMember(InterfaceDeclarationImpl.$TypeDescriptor$, $reifiedContainer, reifiedContainer, decl);
            if(lookup == null)
                continue;

            // skip generic classes
            if(!lookup.declaration.getTypeParameterDeclarations().getEmpty())
                continue;
            
            // ATM this is an AND WRT annotation types: all must be present
            if(!Metamodel.hasAllAnnotations(lookup.declaration, annotationTypeDescriptors))
                continue;
            
            addInterfaceIfCompatible($reifiedContainer, $reifiedType, members, lookup.declaration, lookup.qualifyingType, 
                            lookup.containerMetamodel, reifiedType);
        }
        ceylon.language.meta.model.MemberInterface[] array = members.toArray(new ceylon.language.meta.model.MemberInterface[0]);
		ObjectArrayIterable<ceylon.language.meta.model.MemberInterface> iterable = 
				new ObjectArrayIterable<ceylon.language.meta.model.MemberInterface>(reifiedKind, array);
		return (ceylon.language.Sequential) iterable.sequence();
    }

    @SuppressWarnings({ "hiding" })
    private <Container,Type> void addInterfaceIfCompatible(@Ignore TypeDescriptor $reifiedContainer,
            @Ignore TypeDescriptor $reifiedType,
            ArrayList<ceylon.language.meta.model.MemberInterface<? super Container,? extends Type>> members,
            InterfaceDeclarationImpl decl, com.redhat.ceylon.model.typechecker.model.Type qualifyingType, 
            ClassOrInterfaceImpl<Container> containerMetamodel,
            com.redhat.ceylon.model.typechecker.model.Type reifiedType){
        // now the types
        Reference producedReference = decl.declaration.appliedReference(qualifyingType, Collections.<com.redhat.ceylon.model.typechecker.model.Type>emptyList());
        com.redhat.ceylon.model.typechecker.model.Type type = producedReference.getType();
        if(!type.isSubtypeOf(reifiedType))
            return;
        // it's compatible!
        members.add(decl.<Container,Type>memberInterfaceApply($reifiedContainer, $reifiedType, containerMetamodel));
    }

    @Override
    public String toString() {
        return Metamodel.toTypeString(this);
    }

    @Override
    public boolean typeOf(@TypeInfo("ceylon.language::Anything") Object instance){
        return Metamodel.isTypeOf(producedType, instance);
    }
    
    @Override
    public boolean supertypeOf(@TypeInfo("ceylon.language.meta.model::Type<ceylon.language::Anything>") ceylon.language.meta.model.Type<? extends Object> type){
        return Metamodel.isSuperTypeOf(producedType, type);
    }
    
    @Override
    public boolean subtypeOf(@TypeInfo("ceylon.language.meta.model::Type<ceylon.language::Anything>") ceylon.language.meta.model.Type<? extends Object> type){
        return Metamodel.isSubTypeOf(producedType, type);
    }

    @Override
    public boolean exactly(@TypeInfo("ceylon.language.meta.model::Type<ceylon.language::Anything>") ceylon.language.meta.model.Type<? extends Object> type){
        return Metamodel.isExactly(producedType, type);
    }

    @Override
    @TypeInfo("ceylon.language::Sequential<Type>")
    public ceylon.language.Sequential<? extends Type> getCaseValues(){
        if (!((ClassOrInterface)declaration.declaration).isAbstract()) {
            // optimization: a concrete class cannot have cases
            return (Sequential)empty_.get_();
        }
        Sequential<? extends ceylon.language.meta.declaration.OpenType> caseTypeDeclarations = getDeclaration().getCaseTypes();
        Iterator<? extends ceylon.language.meta.declaration.OpenType> iterator = caseTypeDeclarations.iterator();
        Object it;
        Array<Type> ret = new Array<Type>($reifiedType, (int) caseTypeDeclarations.getSize(), (Type) null);
        int count = 0;
        while((it = iterator.next()) != finished_.get_()){
            if(it instanceof ceylon.language.meta.declaration.OpenClassType == false)
                continue;
            ceylon.language.meta.declaration.OpenClassType caseClassType = (ceylon.language.meta.declaration.OpenClassType)it;
            ceylon.language.meta.declaration.ClassDeclaration caseClass = caseClassType.getDeclaration();
            if(!caseClass.getAnonymous())
                continue;
            ValueDeclaration valueDeclaration = caseClass.getContainingPackage().getValue(caseClass.getName());
            ceylon.language.meta.model.Value<? extends Type,? super Object> valueModel = 
                    valueDeclaration.<Type,Object>apply($reifiedType, TypeDescriptor.NothingType);
            Type value = valueModel.get();
            ret.set(count++, value);
        }
        return ret.take(count).sequence();
    }

    //
    // Used for member lookup
    
    static class MemberLookup<T, Container>{
        public final T declaration;
        public final com.redhat.ceylon.model.typechecker.model.Type qualifyingType;
        public final ClassOrInterfaceImpl<Container> containerMetamodel;
        
        MemberLookup(@Ignore TypeDescriptor $reifiedT,
                @Ignore TypeDescriptor $reifiedContainer,
                T declaration,
                com.redhat.ceylon.model.typechecker.model.Type qualifyingType,
                ClassOrInterfaceImpl<Container> containerMetamodel){
            this.declaration = declaration;
            this.qualifyingType = qualifyingType;
            this.containerMetamodel = containerMetamodel;
        }
    }

    private <T extends NestableDeclarationImpl, Container> MemberLookup<T, Container> lookupMember(@Ignore TypeDescriptor $reifiedT,
            @Ignore TypeDescriptor $reifiedContainer,
            T value) {
        return lookupMember($reifiedT, $reifiedContainer, Metamodel.getProducedType($reifiedContainer), value);
    }
    
    @SuppressWarnings("unchecked")
    private <T extends NestableDeclarationImpl, Container> MemberLookup<T, Container> lookupMember(@Ignore TypeDescriptor $reifiedT,
            @Ignore TypeDescriptor $reifiedContainer,
            com.redhat.ceylon.model.typechecker.model.Type reifiedContainer,
            T value) {
        ClassOrInterfaceImpl<Container> containerMetamodel;
        com.redhat.ceylon.model.typechecker.model.Type qualifyingType;
        if($reifiedContainer == TypeDescriptor.NothingType){
            // wildcard: everything goes
            qualifyingType = this.producedType.getSupertype((TypeDeclaration) value.declaration.getContainer());
        }else{
            // get the declaration as seen from the container
            Declaration memberInContainer = reifiedContainer.getDeclaration().getMember(value.getName(), null, false);
            // cheaper this way than through reflection type checks
            if($reifiedT == ValueDeclarationImpl.$TypeDescriptor$){
                if(memberInContainer instanceof com.redhat.ceylon.model.typechecker.model.Value == false)
                    return null;
            }else if($reifiedT == FunctionDeclarationImpl.$TypeDescriptor$){
                if(memberInContainer instanceof com.redhat.ceylon.model.typechecker.model.Function == false)
                    return null;
            }else if($reifiedT == InterfaceDeclarationImpl.$TypeDescriptor$){
                if(memberInContainer instanceof com.redhat.ceylon.model.typechecker.model.Interface == false)
                    return null;
            }else if($reifiedT == ClassDeclarationImpl.$TypeDescriptor$){
                if(memberInContainer instanceof com.redhat.ceylon.model.typechecker.model.Class == false)
                    return null;
            }else if($reifiedT == ClassOrInterfaceDeclarationImpl.$TypeDescriptor$){
                if(memberInContainer instanceof com.redhat.ceylon.model.typechecker.model.ClassOrInterface == false)
                    return null;
            }else{
                throw new AssertionError("Member type not supported: "+$reifiedT);
            }
            // get the right container type
            qualifyingType = reifiedContainer.getSupertype((TypeDeclaration) memberInContainer.getContainer());
        }
        if(qualifyingType == null)
            throw new AssertionError("Could not find qualifying type for reifiedContainer: "+$reifiedContainer);
        // now get the right metamodel for the qualifying type
        containerMetamodel = (ClassOrInterfaceImpl<Container>)Metamodel.getAppliedMetamodel(qualifyingType);
        if(containerMetamodel == null)
            throw new AssertionError("Could not find metamodel for qualifying type: "+qualifyingType);
        
        // we already have the right member for the wildcard
        if($reifiedContainer != TypeDescriptor.NothingType){
            // now get the right member
            value = ((ClassOrInterfaceDeclarationImpl)containerMetamodel.getDeclaration()).findDeclaredDeclaration($reifiedT, value.getName());
        }
        return new MemberLookup<T, Container>($reifiedT, $reifiedContainer, value, qualifyingType, containerMetamodel);
    }

    @Ignore
    @Override
    public TypeDescriptor $getType$() {
        return TypeDescriptor.klass(ClassOrInterfaceImpl.class, $reifiedType);
    }

    @Override
    public <Other> ceylon.language.meta.model.Type<?> union(TypeDescriptor reified$Other, ceylon.language.meta.model.Type<? extends Other> other) {
        return Metamodel.union(this, other);
    }
    
    @Override
    public <Other> ceylon.language.meta.model.Type<?> intersection(TypeDescriptor reified$Other, ceylon.language.meta.model.Type<? extends Other> other) {
        return Metamodel.intersection(this, other);
    }
}
