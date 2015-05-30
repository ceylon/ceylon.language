package com.redhat.ceylon.compiler.java.runtime.metamodel;

import com.redhat.ceylon.compiler.java.metadata.Ceylon;
import com.redhat.ceylon.compiler.java.metadata.Ignore;
import com.redhat.ceylon.compiler.java.runtime.model.TypeDescriptor;
import com.redhat.ceylon.model.typechecker.model.Type;

@Ceylon(major = 8)
@com.redhat.ceylon.compiler.java.metadata.Class
public class FreeInterfaceType extends FreeClassOrInterfaceType implements ceylon.language.meta.declaration.OpenInterfaceType {

    @Ignore
    public static final TypeDescriptor $TypeDescriptor$ = TypeDescriptor.klass(FreeInterfaceType.class);

    FreeInterfaceType(Type producedType) {
        super(producedType);
    }

    @Override
    public ceylon.language.meta.declaration.InterfaceDeclaration getDeclaration() {
        return (ceylon.language.meta.declaration.InterfaceDeclaration)super.getDeclaration();
    }
    
    @Override
    @Ignore
    public TypeDescriptor $getType$() {
        return $TypeDescriptor$;
    }
}
