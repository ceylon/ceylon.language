package com.redhat.ceylon.compiler.java.runtime.metamodel;

import java.util.List;

import ceylon.language.Finished;
import ceylon.language.Iterator;
import ceylon.language.Sequential;
import ceylon.language.finished_;
import ceylon.language.meta.model.Type;
import ceylon.language.meta.model.Type$impl;

import com.redhat.ceylon.compiler.java.Util;
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
    @TypeParameter(value = "Union", variance = Variance.OUT),
})
public class AppliedUnionType<Union>
    implements ceylon.language.meta.model.UnionType<Union>, ReifiedType {

    @Override
    @Ignore
    public TypeDescriptor $getType$() {
        return TypeDescriptor.klass(AppliedUnionType.class, $reifiedUnion);
    }
    
    @Ignore
    protected final TypeDescriptor $reifiedUnion;
    
    final com.redhat.ceylon.model.typechecker.model.Type model;
    
    protected final Sequential<? extends ceylon.language.meta.model.Type<? extends Union>> caseTypes;
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Iterator<? extends Type<?>> iterator = caseTypes.iterator();
        Object next=iterator.next();
        sb.append(next);
        while (!((next=iterator.next()) instanceof Finished)) {
            sb.append('|').append(next);
        }
        return sb.toString();
    }

    AppliedUnionType(@Ignore TypeDescriptor $reifiedType, com.redhat.ceylon.model.typechecker.model.UnionType union){
        List<com.redhat.ceylon.model.typechecker.model.Type> caseTypes = union.getCaseTypes();
        this.model = union.getType();
        this.$reifiedUnion = $reifiedType;
        @SuppressWarnings("unchecked")
        ceylon.language.meta.model.Type<? extends Union>[] types = new ceylon.language.meta.model.Type[caseTypes.size()];
        int i=0;
        for(com.redhat.ceylon.model.typechecker.model.Type pt : caseTypes){
            types[i++] = Metamodel.getAppliedMetamodel(pt);
        }
        this.caseTypes = Util.<ceylon.language.meta.model.Type<? extends Union>>sequentialWrapper(TypeDescriptor.klass(ceylon.language.meta.model.Type.class, ceylon.language.Anything.$TypeDescriptor$), types);
    }
    
    @Override
    @Ignore
    public Type$impl<Union> $ceylon$language$meta$model$Type$impl() {
        return null;
    }

    @Override
    @TypeInfo("ceylon.language.Sequential<ceylon.language.meta.model::Type<Union>>")
    public ceylon.language.Sequential<? extends ceylon.language.meta.model.Type<? extends Union>> getCaseTypes() {
        return caseTypes;
    }

    @Override
    public int hashCode() {
        int result = 1;
        // do not use caseTypes.hashCode because order is not significant
        Iterator<? extends Type<?>> iterator = caseTypes.iterator();
        Object obj;
        while((obj = iterator.next()) != finished_.get_()){
            result = result + obj.hashCode();
        }
        return result;
    }
    
    @Override
    public boolean equals(Object obj) {
        if(obj == null)
            return false;
        if(obj == this)
            return true;
        if(obj instanceof AppliedUnionType == false)
            return false;
        AppliedUnionType<?> other = (AppliedUnionType<?>) obj;
        return other.model.isExactly(model);
    }

    @Override
    public boolean typeOf(@TypeInfo("ceylon.language::Anything") Object instance){
        return Metamodel.isTypeOf(model, instance);
    }

    @Override
    public boolean supertypeOf(@TypeInfo("ceylon.language.meta.model::Type<ceylon.language::Anything>") ceylon.language.meta.model.Type<? extends Object> type){
        return Metamodel.isSuperTypeOf(model, type);
    }
    
    @Override
    public boolean subtypeOf(@TypeInfo("ceylon.language.meta.model::Type<ceylon.language::Anything>") ceylon.language.meta.model.Type<? extends Object> type){
        return Metamodel.isSubTypeOf(model, type);
    }

    @Override
    public boolean exactly(@TypeInfo("ceylon.language.meta.model::Type<ceylon.language::Anything>") ceylon.language.meta.model.Type<? extends Object> type){
        return Metamodel.isExactly(model, type);
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
