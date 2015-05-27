package com.redhat.ceylon.compiler.java.runtime.metamodel;

import java.lang.reflect.AnnotatedElement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import ceylon.language.Anything;
import ceylon.language.Empty;
import ceylon.language.Sequential;
import ceylon.language.empty_;
import ceylon.language.meta.declaration.OpenType;
import ceylon.language.meta.model.ClassOrInterface;
import ceylon.language.meta.model.Member;

import com.redhat.ceylon.compiler.java.Util;
import com.redhat.ceylon.compiler.java.language.ObjectArray.ObjectArrayIterable;
import com.redhat.ceylon.compiler.java.metadata.Ceylon;
import com.redhat.ceylon.compiler.java.metadata.Ignore;
import com.redhat.ceylon.compiler.java.metadata.Name;
import com.redhat.ceylon.compiler.java.metadata.Sequenced;
import com.redhat.ceylon.compiler.java.metadata.TypeInfo;
import com.redhat.ceylon.compiler.java.metadata.TypeParameter;
import com.redhat.ceylon.compiler.java.metadata.TypeParameters;
import com.redhat.ceylon.compiler.java.metadata.Variance;
import com.redhat.ceylon.compiler.java.runtime.model.TypeDescriptor;
import com.redhat.ceylon.model.typechecker.model.Declaration;
import com.redhat.ceylon.model.typechecker.model.ProducedReference;
import com.redhat.ceylon.model.typechecker.model.ProducedType;
import com.redhat.ceylon.model.typechecker.model.TypeDeclaration;

@Ceylon(major = 8)
@com.redhat.ceylon.compiler.java.metadata.Class
public abstract class FreeClassOrInterface
    extends FreeNestableDeclaration
    implements ceylon.language.meta.declaration.ClassOrInterfaceDeclaration, AnnotationBearing {

    @Ignore
    public static final TypeDescriptor $TypeDescriptor$ = TypeDescriptor.klass(FreeClassOrInterface.class);
    
    @Ignore
    private static final TypeDescriptor $FunctionTypeDescriptor = TypeDescriptor.klass(ceylon.language.meta.declaration.FunctionDeclaration.class, Anything.$TypeDescriptor$, Empty.$TypeDescriptor$);
    @Ignore
    private static final TypeDescriptor $AttributeTypeDescriptor = TypeDescriptor.klass(ceylon.language.meta.declaration.ValueDeclaration.class, Anything.$TypeDescriptor$);
    @Ignore
    private static final TypeDescriptor $ClassOrInterfaceTypeDescriptor = TypeDescriptor.klass(ceylon.language.meta.declaration.ClassOrInterfaceDeclaration.class, Anything.$TypeDescriptor$);
    
    private volatile boolean initialised = false;
    private ceylon.language.meta.declaration.OpenClassType superclass;
    private Sequential<ceylon.language.meta.declaration.OpenInterfaceType> interfaces;
    private Sequential<? extends ceylon.language.meta.declaration.TypeParameter> typeParameters;

    private List<ceylon.language.meta.declaration.NestableDeclaration> declaredDeclarations;
    private List<ceylon.language.meta.declaration.NestableDeclaration> declarations;

    private Sequential<? extends ceylon.language.meta.declaration.OpenType> caseTypes;

    public FreeClassOrInterface(com.redhat.ceylon.model.typechecker.model.ClassOrInterface declaration) {
        super(declaration);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    protected void init(){
        com.redhat.ceylon.model.typechecker.model.ClassOrInterface declaration = (com.redhat.ceylon.model.typechecker.model.ClassOrInterface) this.declaration;
        
        ProducedType superType = declaration.getExtendedType();
        if(superType != null)
            this.superclass = (ceylon.language.meta.declaration.OpenClassType) Metamodel.getMetamodel(superType);
        
        List<ProducedType> satisfiedTypes = declaration.getSatisfiedTypes();
        ceylon.language.meta.declaration.OpenInterfaceType[] interfaces = new ceylon.language.meta.declaration.OpenInterfaceType[satisfiedTypes.size()];
        int i=0;
        for(ProducedType pt : satisfiedTypes){
            interfaces[i++] = (ceylon.language.meta.declaration.OpenInterfaceType) Metamodel.getMetamodel(pt);
        }
        this.interfaces = Util.sequentialWrapper(ceylon.language.meta.declaration.OpenInterfaceType.$TypeDescriptor$, interfaces);

        if(declaration.getCaseTypes() != null)
            this.caseTypes = Metamodel.getMetamodelSequential(declaration.getCaseTypes());
        else
            this.caseTypes = (Sequential<? extends ceylon.language.meta.declaration.OpenType>)(Sequential)empty_.get_();

        this.typeParameters = Metamodel.getTypeParameters(declaration);
        
        List<com.redhat.ceylon.model.typechecker.model.Declaration> memberModelDeclarations = declaration.getMembers();
        this.declaredDeclarations = new LinkedList<ceylon.language.meta.declaration.NestableDeclaration>();
        for(com.redhat.ceylon.model.typechecker.model.Declaration memberModelDeclaration : memberModelDeclarations){
            if(isSupportedType(memberModelDeclaration))
                declaredDeclarations.add(Metamodel.<ceylon.language.meta.declaration.NestableDeclaration>getOrCreateMetamodel(memberModelDeclaration));
        }

        Collection<com.redhat.ceylon.model.typechecker.model.Declaration> inheritedModelDeclarations = 
                collectMembers(declaration);
        this.declarations = new LinkedList<ceylon.language.meta.declaration.NestableDeclaration>();
        for(com.redhat.ceylon.model.typechecker.model.Declaration memberModelDeclaration : inheritedModelDeclarations){
            if(isSupportedType(memberModelDeclaration))
                declarations.add(Metamodel.<ceylon.language.meta.declaration.NestableDeclaration>getOrCreateMetamodel(memberModelDeclaration));
        }
    }

    private boolean isSupportedType(Declaration memberModelDeclaration) {
        return memberModelDeclaration instanceof com.redhat.ceylon.model.typechecker.model.Value
                || (memberModelDeclaration instanceof com.redhat.ceylon.model.typechecker.model.Method
                        && !((com.redhat.ceylon.model.typechecker.model.Method)memberModelDeclaration).isAbstraction())
                || memberModelDeclaration instanceof com.redhat.ceylon.model.typechecker.model.TypeAlias
                || memberModelDeclaration instanceof com.redhat.ceylon.model.typechecker.model.Interface
                || (memberModelDeclaration instanceof com.redhat.ceylon.model.typechecker.model.Class
                        && !((com.redhat.ceylon.model.typechecker.model.Class)memberModelDeclaration).isAbstraction());
    }

    private Collection<com.redhat.ceylon.model.typechecker.model.Declaration> collectMembers(com.redhat.ceylon.model.typechecker.model.TypeDeclaration base){
        Map<String, com.redhat.ceylon.model.typechecker.model.Declaration> byName = 
                new HashMap<String, com.redhat.ceylon.model.typechecker.model.Declaration>();
        collectMembers(base, byName);
        return byName.values();
    }
    
    private void collectMembers(com.redhat.ceylon.model.typechecker.model.TypeDeclaration base, Map<String, Declaration> byName) {
        for(com.redhat.ceylon.model.typechecker.model.Declaration decl : base.getMembers()){
            if(decl.isShared() && com.redhat.ceylon.model.typechecker.model.Util.isResolvable(decl)){
                Declaration otherDeclaration = byName.get(decl.getName());
                if(otherDeclaration == null || decl.refines(otherDeclaration))
                    byName.put(decl.getName(), decl);
            }
        }
        com.redhat.ceylon.model.typechecker.model.ProducedType et = base.getExtendedType();
        if(et != null) {
            collectMembers(et.getDeclaration(), byName);
        }
        for(com.redhat.ceylon.model.typechecker.model.ProducedType st : base.getSatisfiedTypes()){
            if(st != null) {
                collectMembers(st.getDeclaration(), byName);
            }
        }
    }

    protected final void checkInit(){
        if(!initialised){
            synchronized(Metamodel.getLock()){
                if(!initialised){
                    init();
                    initialised = true;
                }
            }
        }
    }
    
    @Override
    @TypeInfo("ceylon.language::Sequential<Kind>")
    @TypeParameters(@TypeParameter(value = "Kind", satisfies = "ceylon.language.meta.declaration::NestableDeclaration"))
    public <Kind extends ceylon.language.meta.declaration.NestableDeclaration> Sequential<? extends Kind> 
    memberDeclarations(@Ignore TypeDescriptor $reifiedKind) {
        
        Predicates.Predicate<?> predicate = Predicates.isDeclarationOfKind($reifiedKind);
        
        return filteredMembers($reifiedKind, predicate);
    }

    @Override
    @TypeInfo("ceylon.language::Sequential<Kind>")
    @TypeParameters(@TypeParameter(value = "Kind", satisfies = "ceylon.language.meta.declaration::NestableDeclaration"))
    public <Kind extends ceylon.language.meta.declaration.NestableDeclaration> Sequential<? extends Kind> 
    declaredMemberDeclarations(@Ignore TypeDescriptor $reifiedKind) {
        
        Predicates.Predicate<?> predicate = Predicates.isDeclarationOfKind($reifiedKind);
        
        return filteredDeclaredMembers($reifiedKind, predicate);
    }

    @Override
    @TypeInfo("ceylon.language::Sequential<Kind>")
    @TypeParameters({
            @TypeParameter(value = "Kind", satisfies = "ceylon.language.meta.declaration::NestableDeclaration"),
            @TypeParameter(value = "Annotation", satisfies = "ceylon.language::Annotation")
    })
    public <Kind extends ceylon.language.meta.declaration.NestableDeclaration, Annotation extends java.lang.annotation.Annotation> Sequential<? extends Kind> 
    annotatedMemberDeclarations(@Ignore TypeDescriptor $reifiedKind, @Ignore TypeDescriptor $reifiedAnnotation) {
        
        Predicates.Predicate<?> predicate = Predicates.and(
                Predicates.isDeclarationOfKind($reifiedKind),
                Predicates.isDeclarationAnnotatedWith($reifiedAnnotation));
        
        return filteredMembers($reifiedKind, predicate);
    }

    @Override
    @TypeInfo("ceylon.language::Sequential<Kind>")
    @TypeParameters({
            @TypeParameter(value = "Kind", satisfies = "ceylon.language.meta.declaration::NestableDeclaration"),
            @TypeParameter(value = "Annotation", satisfies = "ceylon.language::Annotation")
    })
    public <Kind extends ceylon.language.meta.declaration.NestableDeclaration, Annotation extends java.lang.annotation.Annotation> Sequential<? extends Kind> 
    annotatedDeclaredMemberDeclarations(@Ignore TypeDescriptor $reifiedKind, @Ignore TypeDescriptor $reifiedAnnotation) {
        
        Predicates.Predicate<?> predicate = Predicates.and(
                Predicates.isDeclarationOfKind($reifiedKind),
                Predicates.isDeclarationAnnotatedWith($reifiedAnnotation));
        
        return filteredDeclaredMembers($reifiedKind, predicate);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private <Kind> Sequential<? extends Kind> filteredMembers(
            @Ignore TypeDescriptor $reifiedKind,
            Predicates.Predicate predicate) {
        if (predicate == Predicates.false_()) {
            return (Sequential<? extends Kind>)empty_.get_();
        }
        checkInit();
        ArrayList<Kind> members = new ArrayList<Kind>(declarations.size());
        for(ceylon.language.meta.declaration.NestableDeclaration decl : declarations){
            if (predicate.accept(((FreeNestableDeclaration)decl).declaration)) {
                members.add((Kind) decl);
            }
        }
        java.lang.Object[] array = members.toArray(new java.lang.Object[0]);
		ObjectArrayIterable<Kind> iterable = 
				new ObjectArrayIterable<Kind>($reifiedKind, (Kind[]) array);
		return (ceylon.language.Sequential) iterable.sequence();
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private <Kind> Sequential<? extends Kind> filteredDeclaredMembers(
            @Ignore TypeDescriptor $reifiedKind,
            Predicates.Predicate predicate) {
        if (predicate == Predicates.false_()) {
            return (Sequential<? extends Kind>)empty_.get_();
        }
        checkInit();
        ArrayList<Kind> members = new ArrayList<Kind>(declarations.size());
        for(ceylon.language.meta.declaration.NestableDeclaration decl : declaredDeclarations){
            if (predicate.accept(((FreeNestableDeclaration)decl).declaration)) {
                members.add((Kind) decl);
            }
        }
        java.lang.Object[] array = members.toArray(new java.lang.Object[0]);
		ObjectArrayIterable<Kind> iterable = 
				new ObjectArrayIterable<Kind>($reifiedKind, (Kind[]) array);
		return (ceylon.language.Sequential) iterable.sequence();
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private <Kind> Kind filteredMember(
            @Ignore TypeDescriptor $reifiedKind,
            Predicates.Predicate predicate) {
        if (predicate == Predicates.false_()) {
            return null;
        }
        checkInit();
        for(ceylon.language.meta.declaration.NestableDeclaration decl : declarations){
            if (predicate.accept(((FreeNestableDeclaration)decl).declaration)) {
                return (Kind)decl;
            }
        }
        return null;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private <Kind> Kind filteredDeclaredMember(
            @Ignore TypeDescriptor $reifiedKind,
            Predicates.Predicate predicate) {
        if (predicate == Predicates.false_()) {
            return null;
        }
        checkInit();
        for(ceylon.language.meta.declaration.NestableDeclaration decl : declaredDeclarations){
            if (predicate.accept(((FreeNestableDeclaration)decl).declaration)) {
                return (Kind)decl;
            }
        }
        return null;
    }

    @Override
    @TypeInfo("Kind")
    @TypeParameters(@TypeParameter(value = "Kind", satisfies = "ceylon.language.meta.declaration::NestableDeclaration"))
    public <Kind extends ceylon.language.meta.declaration.NestableDeclaration> Kind 
    getMemberDeclaration(@Ignore TypeDescriptor $reifiedKind, @Name("name") String name) {
        
        Predicates.Predicate<?> predicate = Predicates.and(
                Predicates.isDeclarationNamed(name),
                Predicates.isDeclarationOfKind($reifiedKind)
        );
        
        return filteredMember($reifiedKind, predicate);
    }

    @Override
    @TypeInfo("Kind")
    @TypeParameters(@TypeParameter(value = "Kind", satisfies = "ceylon.language.meta.declaration::NestableDeclaration"))
    public <Kind extends ceylon.language.meta.declaration.NestableDeclaration> Kind 
    getDeclaredMemberDeclaration(@Ignore TypeDescriptor $reifiedKind, @Name("name") String name) {
        
        Predicates.Predicate<?> predicate = Predicates.and(
                Predicates.isDeclarationNamed(name),
                Predicates.isDeclarationOfKind($reifiedKind)
        );
        
        return filteredDeclaredMember($reifiedKind, predicate);
    }

    @Override
    @TypeInfo("ceylon.language::Sequential<ceylon.language.meta.declaration::OpenInterfaceType>")
    public Sequential<? extends ceylon.language.meta.declaration.OpenInterfaceType> getSatisfiedTypes() {
        checkInit();
        return interfaces;
    }

    @Override
    @TypeInfo("ceylon.language.meta.declaration::OpenClassType|ceylon.language::Null")
    public ceylon.language.meta.declaration.OpenClassType getExtendedType() {
        checkInit();
        return superclass;
    }


    @TypeInfo("ceylon.language::Sequential<ceylon.language.meta.declaration::OpenType>")
    @Override
    public ceylon.language.Sequential<? extends ceylon.language.meta.declaration.OpenType> getCaseTypes(){
        checkInit();
        return caseTypes;
    }

    @Override
    @TypeInfo("ceylon.language::Sequential<ceylon.language.meta.declaration::TypeParameter>")
    public Sequential<? extends ceylon.language.meta.declaration.TypeParameter> getTypeParameterDeclarations() {
        checkInit();
        return typeParameters;
    }

    @Override
    public boolean getIsAlias(){
        return ((com.redhat.ceylon.model.typechecker.model.ClassOrInterface)declaration).isAlias();
    }

    @Override
    public OpenType getOpenType() {
        return Metamodel.getMetamodel(((com.redhat.ceylon.model.typechecker.model.ClassOrInterface)declaration).getType());
    }

    @Override
    @TypeInfo("ceylon.language.meta.declaration::TypeParameter|ceylon.language::Null")
    public ceylon.language.meta.declaration.TypeParameter getTypeParameterDeclaration(@Name("name") String name) {
        return Metamodel.findDeclarationByName(getTypeParameterDeclarations(), name);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Ignore
    @Override
    public <Type> ceylon.language.meta.model.ClassOrInterface<Type> apply(@Ignore TypeDescriptor $reifiedType){
        return apply($reifiedType, (Sequential)empty_.get_());
    }

    @SuppressWarnings("unchecked")
    @Override
    @TypeInfo("ceylon.language.meta.model::ClassOrInterface<Type>")
    @TypeParameters({
        @TypeParameter("Type"),
    })
    public <Type extends Object> ceylon.language.meta.model.ClassOrInterface<Type> apply(@Ignore TypeDescriptor $reifiedType,
            @Name("typeArguments") @TypeInfo("ceylon.language::Sequential<ceylon.language.meta.model::Type<ceylon.language::Anything>>") @Sequenced Sequential<? extends ceylon.language.meta.model.Type<?>> typeArguments){
        if(!getToplevel())
            throw new ceylon.language.meta.model.TypeApplicationException("Cannot apply a member declaration with no container type: use memberApply");
        List<com.redhat.ceylon.model.typechecker.model.ProducedType> producedTypes = Metamodel.getProducedTypes(typeArguments);
        Metamodel.checkTypeArguments(null, declaration, producedTypes);
        com.redhat.ceylon.model.typechecker.model.ProducedReference appliedType = declaration.getProducedReference(null, producedTypes);
        Metamodel.checkReifiedTypeArgument("apply", "ClassOrInterface<$1>", Variance.OUT, appliedType.getType(), $reifiedType);
        return (ClassOrInterface<Type>) Metamodel.getAppliedMetamodel(appliedType.getType());
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Ignore
    @Override
    public <Container, Type extends Object>
        java.lang.Object memberApply(TypeDescriptor $reifiedContainer,
                                     TypeDescriptor $reifiedType,
                                     ceylon.language.meta.model.Type<? extends Object> containerType){
        
        return this.<Container, Type>memberApply($reifiedContainer,
                                                 $reifiedType,
                                                 containerType,
                                                 (Sequential)empty_.get_());
    }

    @SuppressWarnings("rawtypes")
    @TypeInfo("ceylon.language.meta.model::Member<Container,ceylon.language.meta.model::ClassOrInterface<Type>>&ceylon.language.meta.model::ClassOrInterface<Type>")
    @TypeParameters({
        @TypeParameter("Container"),
        @TypeParameter("Type"),
    })
    @Override
    public <Container, Type extends Object>
        java.lang.Object memberApply(
                @Ignore TypeDescriptor $reifiedContainer,
                @Ignore TypeDescriptor $reifiedType,
                @Name("containerType") ceylon.language.meta.model.Type<? extends Object> containerType,
                @Name("typeArguments") @Sequenced Sequential<? extends ceylon.language.meta.model.Type<?>> typeArguments){
        if(getToplevel())
            throw new ceylon.language.meta.model.TypeApplicationException("Cannot apply a toplevel declaration to a container type: use apply");

        ceylon.language.meta.model.Member<? extends Container, ceylon.language.meta.model.ClassOrInterface<?>> member 
            = getAppliedClassOrInterface(null, null, typeArguments, containerType);
        
        // This is all very ugly but we're trying to make it cheaper and friendlier than just checking the full type and showing
        // implementation types to the user, such as AppliedMemberClass
        TypeDescriptor actualReifiedContainer;
        if(member instanceof AppliedMemberClass)
            actualReifiedContainer = ((AppliedMemberClass)member).$reifiedContainer;
        else
            actualReifiedContainer = ((AppliedMemberInterface)member).$reifiedContainer;
        ProducedType actualType = Metamodel.getModel((ceylon.language.meta.model.Type<?>) member);
        Metamodel.checkReifiedTypeArgument("memberApply", "Member<$1,ClassOrInterface<$2>>&ClassOrInterface<$2>", 
                Variance.IN, Metamodel.getProducedType(actualReifiedContainer), $reifiedContainer, 
                Variance.OUT, actualType, $reifiedType);
        return member;
    }

    @SuppressWarnings("unchecked")
    <Container, Kind extends ceylon.language.meta.model.ClassOrInterface<? extends Object>>
    ceylon.language.meta.model.Member<Container, Kind> getAppliedClassOrInterface(@Ignore TypeDescriptor $reifiedContainer, 
                                                                        @Ignore TypeDescriptor $reifiedKind, 
                                                                        Sequential<? extends ceylon.language.meta.model.Type<?>> types,
                                                                        ceylon.language.meta.model.Type<? extends Object> container){
        List<com.redhat.ceylon.model.typechecker.model.ProducedType> producedTypes = Metamodel.getProducedTypes(types);
        ProducedType qualifyingType = Metamodel.getModel(container);
        Metamodel.checkQualifyingType(qualifyingType, declaration);
        Metamodel.checkTypeArguments(qualifyingType, declaration, producedTypes);
        // find the proper qualifying type
        ProducedType memberQualifyingType = qualifyingType.getSupertype((TypeDeclaration) declaration.getContainer());
        ProducedReference producedReference = declaration.getProducedReference(memberQualifyingType, producedTypes);
        final ProducedType appliedType = producedReference.getType();
        return (Member<Container, Kind>) Metamodel.getAppliedMetamodel(appliedType);
    }

    @Override
    @Ignore
    public TypeDescriptor $getType$() {
        return $TypeDescriptor$;
    }

    FreeFunction findMethod(String name) {
        FreeNestableDeclaration decl = this.findDeclaration(null, name);
        return decl instanceof FreeFunction ? (FreeFunction)decl : null;
    }

    FreeFunction findDeclaredMethod(String name) {
        FreeNestableDeclaration decl = this.findDeclaredDeclaration(null, name);
        return decl instanceof FreeFunction ? (FreeFunction)decl : null;
    }

    FreeValue findValue(String name) {
        FreeNestableDeclaration decl = this.findDeclaration(null, name);
        return decl instanceof FreeValue ? (FreeValue)decl : null;
    }

    FreeValue findDeclaredValue(String name) {
        FreeNestableDeclaration decl = this.findDeclaredDeclaration(null, name);
        return decl instanceof FreeValue ? (FreeValue)decl : null;
    }

    FreeClassOrInterface findType(String name) {
        FreeNestableDeclaration decl = this.findDeclaration(null, name);
        return decl instanceof FreeClassOrInterface ? (FreeClassOrInterface)decl : null;
    }

    FreeClassOrInterface findDeclaredType(String name) {
        FreeNestableDeclaration decl = this.findDeclaredDeclaration(null, name);
        return decl instanceof FreeClassOrInterface ? (FreeClassOrInterface)decl : null;
    }

    <T extends FreeNestableDeclaration> T findDeclaration(@Ignore TypeDescriptor $reifiedT, String name) {
        checkInit();
        return findDeclaration($reifiedT, name, declarations);
    }

    <T extends FreeNestableDeclaration> T findDeclaredDeclaration(@Ignore TypeDescriptor $reifiedT, String name) {
        checkInit();
        return findDeclaration($reifiedT, name, declaredDeclarations);
    }

    @SuppressWarnings("unchecked")
    <T extends FreeNestableDeclaration> T findDeclaration(@Ignore TypeDescriptor $reifiedT, String name,
            List<ceylon.language.meta.declaration.NestableDeclaration> declarations) {
        for(ceylon.language.meta.declaration.NestableDeclaration decl : declarations){
            // skip anonymous types which can't be looked up by name
            if(decl instanceof ceylon.language.meta.declaration.ClassDeclaration
                    && ((ceylon.language.meta.declaration.ClassDeclaration) decl).getAnonymous())
                continue;
            // in theory we can't have several members with the same name so no need to check the type
            // FIXME: interop and overloading
            if(decl.getName().equals(name))
                return (T) decl;
        }
        return null;
    }
    
    @Override
    @Ignore
    public java.lang.annotation.Annotation[] $getJavaAnnotations$() {
        return Metamodel.getJavaClass(declaration).getAnnotations();
    }
    
    @Override
    @Ignore
    public boolean $isAnnotated$(java.lang.Class<? extends java.lang.annotation.Annotation> annotationType) {
        final AnnotatedElement element = Metamodel.getJavaClass(declaration);
        return element != null ? element.isAnnotationPresent(annotationType) : false;
    }
    
    @Override
    public <AnnotationType extends java.lang.annotation.Annotation> boolean annotated(TypeDescriptor reifed$AnnotationType) {
        return Metamodel.isAnnotated(reifed$AnnotationType, this);
    }
}
