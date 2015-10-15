package com.redhat.ceylon.compiler.java.runtime.metamodel.meta;

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
import com.redhat.ceylon.compiler.java.runtime.metamodel.Metamodel;
import com.redhat.ceylon.compiler.java.runtime.model.ReifiedType;
import com.redhat.ceylon.compiler.java.runtime.model.TypeDescriptor;

@Ceylon(major = 8)
@com.redhat.ceylon.compiler.java.metadata.Class
@TypeParameters({
    @TypeParameter(value = "Intersection", variance = Variance.OUT),
})
public class IntersectionTypeImpl<Intersection>
    implements ceylon.language.meta.model.IntersectionType<Intersection>, ReifiedType {

    @Override
    @Ignore
    public TypeDescriptor $getType$() {
        return TypeDescriptor.klass(IntersectionTypeImpl.class, $reifiedIntersection);
    }
        
    @Ignore
    public final TypeDescriptor $reifiedIntersection;

    public final com.redhat.ceylon.model.typechecker.model.Type model;

    protected final Sequential<ceylon.language.meta.model.Type<?>> satisfiedTypes;
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Iterator<? extends Type<?>> iterator = satisfiedTypes.iterator();
        Object next=iterator.next();
        sb.append(next);
        while (!((next=iterator.next()) instanceof Finished)) {
            sb.append('&').append(next);
        }
        return sb.toString();
    }

    public IntersectionTypeImpl(@Ignore TypeDescriptor $reifiedType, com.redhat.ceylon.model.typechecker.model.IntersectionType intersection){
        List<com.redhat.ceylon.model.typechecker.model.Type> satisfiedTypes = intersection.getSatisfiedTypes();
        this.model = intersection.getType();
        this.$reifiedIntersection = $reifiedType;
        ceylon.language.meta.model.Type<?>[] types = new ceylon.language.meta.model.Type<?>[satisfiedTypes.size()];
        int i=0;
        for(com.redhat.ceylon.model.typechecker.model.Type pt : satisfiedTypes){
            types[i++] = Metamodel.getAppliedMetamodel(pt);
        }
        this.satisfiedTypes = Util.sequentialWrapper(TypeDescriptor.klass(ceylon.language.meta.model.Type.class, ceylon.language.Anything.$TypeDescriptor$), types);
    }

    @Override
    @Ignore
    public Type$impl<Intersection> $ceylon$language$meta$model$Type$impl() {
        return null;
    }

    @Override
    @TypeInfo("ceylon.language.Sequential<ceylon.language.meta.model::Type<ceylon.language::Anything>>")
    public ceylon.language.Sequential<? extends ceylon.language.meta.model.Type<?>> getSatisfiedTypes() {
        return satisfiedTypes;
    }

    @Override
    public int hashCode() {
        int result = 1;
        // do not use caseTypes.hashCode because order is not significant
        Iterator<? extends Type<?>> iterator = satisfiedTypes.iterator();
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
        if(obj instanceof IntersectionTypeImpl == false)
            return false;
        IntersectionTypeImpl<?> other = (IntersectionTypeImpl<?>) obj;
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
