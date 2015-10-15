package com.redhat.ceylon.compiler.java.runtime.metamodel.decl;

import java.lang.reflect.AnnotatedElement;

import ceylon.language.Sequential;
import ceylon.language.meta.declaration.OpenType;

import com.redhat.ceylon.compiler.java.metadata.Ignore;
import com.redhat.ceylon.compiler.java.metadata.Name;
import com.redhat.ceylon.compiler.java.metadata.TypeInfo;
import com.redhat.ceylon.compiler.java.runtime.metamodel.AnnotationBearing;
import com.redhat.ceylon.compiler.java.runtime.metamodel.Metamodel;
import com.redhat.ceylon.compiler.java.runtime.model.TypeDescriptor;

public class AliasDeclarationImpl extends NestableDeclarationImpl 
    implements ceylon.language.meta.declaration.AliasDeclaration, AnnotationBearing {

    @Ignore
    public final static TypeDescriptor $TypeDescriptor$ = TypeDescriptor.klass(AliasDeclarationImpl.class);

    private OpenType openType;

    private boolean initialised = false;

    private Sequential<? extends ceylon.language.meta.declaration.TypeParameter> typeParameters;

    private OpenType extendedType;
    
    public AliasDeclarationImpl(com.redhat.ceylon.model.typechecker.model.TypeAlias declaration) {
        super(declaration);
        
        this.openType = Metamodel.getMetamodel(declaration.getType().resolveAliases());
    }

    protected final void checkInit(){
        if(!initialised ){
            synchronized(Metamodel.getLock()){
                if(!initialised){
                    init();
                    initialised = true;
                }
            }
        }
    }

    private void init() {
        com.redhat.ceylon.model.typechecker.model.TypeAlias declaration = (com.redhat.ceylon.model.typechecker.model.TypeAlias) this.declaration;
        
        this.typeParameters = Metamodel.getTypeParameters(declaration);
        
        this.extendedType = Metamodel.getMetamodel(declaration.getExtendedType());
    }

    @Override
    public OpenType getOpenType() {
        return openType;
    }

    @Override
    public OpenType getExtendedType() {
        checkInit();
        return extendedType;
    }
    
    @Override
    public ceylon.language.meta.declaration.TypeParameter getTypeParameterDeclaration(@Name("name") @TypeInfo("ceylon.language::String") String name) {
        return Metamodel.findDeclarationByName(getTypeParameterDeclarations(), name);
    }

    @Override
    @TypeInfo("ceylon.language::Sequential<ceylon.language.meta.declaration::TypeParameter>")
    public Sequential<? extends ceylon.language.meta.declaration.TypeParameter> getTypeParameterDeclarations() {
        checkInit();
        return typeParameters;
    }

    /* FIXME: this is all too shaky wrt member types

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Ignore
    @Override
    public <Type> ceylon.language.meta.model.Type<Type> apply(@Ignore TypeDescriptor $reifiedType){
        return apply($reifiedType, (Sequential)empty_.get_());
    }

    @Override
    @TypeInfo("ceylon.language.meta.model::Type<Type>")
    @TypeParameters({
        @TypeParameter("Type"),
    })
    public <Type extends Object> ceylon.language.meta.model.Type<Type> apply(@Ignore TypeDescriptor $reifiedType,
            @Name("typeArguments") @TypeInfo("ceylon.language::Sequential<ceylon.language.meta.model::Type<ceylon.language::Anything>>") @Sequenced Sequential<? extends ceylon.language.meta.model.Type<?>> typeArguments){
        if(!getToplevel())
            // FIXME: change type
            throw new RuntimeException("Cannot apply a member declaration with no container type: use memberApply");
        List<com.redhat.ceylon.compiler.typechecker.model.Type> producedTypes = Metamodel.getProducedTypes(typeArguments);
        Metamodel.checkTypeArguments(null, declaration, producedTypes);
        Reference producedReference = declaration.appliedReference(null, producedTypes);
        final Type appliedType = producedReference.getType();
        return Metamodel.getAppliedMetamodel(appliedType.resolveAliases());
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Ignore
    @Override
    public <Container, Type extends Object>
        java.lang.Object memberApply(TypeDescriptor $reifiedContainer,
                                     TypeDescriptor $reifiedType,
                                     ceylon.language.meta.model.Type<? extends Container> containerType){
        
        return this.<Container, Type>memberApply($reifiedContainer,
                                                 $reifiedType,
                                                 containerType,
                                                 (Sequential)empty_.get_());
    }

    @TypeInfo("ceylon.language.meta.model::Member<Container,ceylon.language.meta.model::Type<Type>>&ceylon.language.meta.model::Type<Type>")
    @TypeParameters({
        @TypeParameter("Container"),
        @TypeParameter("Type"),
    })
    @Override
    public <Container, Type extends Object>
        java.lang.Object memberApply(
                @Ignore TypeDescriptor $reifiedContainer,
                @Ignore TypeDescriptor $reifiedType,
                @Name("containerType") ceylon.language.meta.model.Type<? extends Container> containerType,
                @Name("typeArguments") @Sequenced Sequential<? extends ceylon.language.meta.model.Type<?>> typeArguments){
        if(getToplevel())
            // FIXME: change type
            throw new RuntimeException("Cannot apply a toplevel declaration to a container type: use apply");
        List<com.redhat.ceylon.compiler.typechecker.model.Type> producedTypes = Metamodel.getProducedTypes(typeArguments);
        Type qualifyingType = Metamodel.getModel(containerType);
        Metamodel.checkTypeArguments(qualifyingType, declaration, producedTypes);
        Reference producedReference = declaration.appliedReference(qualifyingType, producedTypes);
        final Type appliedType = producedReference.getType();
        return Metamodel.getAppliedMetamodel(appliedType.resolveAliases());
    }
*/

    @Override
    public int hashCode() {
        return Metamodel.hashCode(this, "type");
    }
    
    @Override
    public boolean equals(Object obj) {
        if(obj == null)
            return false;
        if(obj == this)
            return true;
        if(obj instanceof AliasDeclarationImpl == false)
            return false;
        return Metamodel.equalsForSameType(this, (AliasDeclarationImpl)obj);
    }

    @Override
    public String toString() {
        return "alias "+super.toString();
    }

    @Override
    @Ignore
    public TypeDescriptor $getType$() {
        return $TypeDescriptor$;
    }

    @Override
    @Ignore
    public java.lang.annotation.Annotation[] $getJavaAnnotations$() {
        return Metamodel.getJavaClass(declaration).getAnnotations();
    }
    
    @Override
    @Ignore
    public boolean $isAnnotated$(java.lang.Class<? extends java.lang.annotation.Annotation> annotationType) {
        final AnnotatedElement element = Metamodel.getJavaClass(declaration);;
        return element != null ? element.isAnnotationPresent(annotationType) : false;
    }
    
    @Override
    public <AnnotationType extends java.lang.annotation.Annotation> boolean annotated(TypeDescriptor reifed$AnnotationType) {
        return Metamodel.isAnnotated(reifed$AnnotationType, this);
    }
}
