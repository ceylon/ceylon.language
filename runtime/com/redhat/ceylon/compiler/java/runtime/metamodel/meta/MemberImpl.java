package com.redhat.ceylon.compiler.java.runtime.metamodel.meta;

import com.redhat.ceylon.compiler.java.metadata.Ceylon;
import com.redhat.ceylon.compiler.java.metadata.Ignore;
import com.redhat.ceylon.compiler.java.metadata.TypeInfo;
import com.redhat.ceylon.compiler.java.metadata.TypeParameter;
import com.redhat.ceylon.compiler.java.metadata.TypeParameters;
import com.redhat.ceylon.compiler.java.metadata.Variance;
import com.redhat.ceylon.compiler.java.runtime.model.ReifiedType;
import com.redhat.ceylon.compiler.java.runtime.model.TypeDescriptor;

@Ceylon(major = 8)
@com.redhat.ceylon.compiler.java.metadata.Class
@TypeParameters({
    @TypeParameter(value = "Container", variance = Variance.IN),
    @TypeParameter(value = "Kind", variance = Variance.OUT, satisfies = "ceylon.language.meta.model::Model")
})
public abstract class MemberImpl<Container, Kind extends ceylon.language.meta.model.Model> 
    implements ceylon.language.meta.model.Member<Container, Kind>, ReifiedType {

    private final ceylon.language.meta.model.Type<? extends Object> container;
    @Ignore
    protected final TypeDescriptor $reifiedKind;
    @Ignore
    protected final TypeDescriptor $reifiedContainer;

    public MemberImpl(@Ignore TypeDescriptor $reifiedContainer, @Ignore TypeDescriptor $reifiedKind,
                         ceylon.language.meta.model.Type<? extends Object> container){
        this.$reifiedContainer = $reifiedContainer;
        this.$reifiedKind = $reifiedKind;
        this.container = container;
    }
    
    @Override
    @TypeInfo("ceylon.language.meta.model::Type<ceylon.language::Anything>")
    public ceylon.language.meta.model.Type<? extends Object> getDeclaringType() {
        return container;
    }

    @Override
    @Ignore
    public Kind $call$() {
        throw new UnsupportedOperationException();
    }

    protected abstract Kind bindTo(Object instance);
    
    @Override
    @Ignore
    public Kind $call$(Object instance) {
        return bindTo(instance);
    }

    @Override
    @Ignore
    public Kind $call$(Object arg0, Object arg1) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Ignore
    public Kind $call$(Object arg0, Object arg1, Object arg2) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Ignore
    public Kind $call$(Object... args) {
        throw new UnsupportedOperationException();
    }

    @Ignore
    @Override
    public short $getVariadicParameterIndex$() {
        return -1;
    }

    @Ignore
    @Override
    public TypeDescriptor $getType$() {
        return TypeDescriptor.klass(MemberImpl.class, $reifiedContainer, $reifiedKind);
    }
}
