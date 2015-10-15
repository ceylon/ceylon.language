package com.redhat.ceylon.compiler.java.runtime.metamodel.meta;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.redhat.ceylon.compiler.java.Util;
import com.redhat.ceylon.compiler.java.metadata.Ceylon;
import com.redhat.ceylon.compiler.java.metadata.Ignore;
import com.redhat.ceylon.compiler.java.metadata.Name;
import com.redhat.ceylon.compiler.java.metadata.Sequenced;
import com.redhat.ceylon.compiler.java.metadata.TypeInfo;
import com.redhat.ceylon.compiler.java.metadata.TypeParameter;
import com.redhat.ceylon.compiler.java.metadata.TypeParameters;
import com.redhat.ceylon.compiler.java.metadata.Variance;
import com.redhat.ceylon.compiler.java.runtime.metamodel.DefaultValueProvider;
import com.redhat.ceylon.compiler.java.runtime.metamodel.Metamodel;
import com.redhat.ceylon.compiler.java.runtime.metamodel.MethodHandleUtil;
import com.redhat.ceylon.compiler.java.runtime.metamodel.decl.FunctionDeclarationImpl;
import com.redhat.ceylon.compiler.java.runtime.model.ReifiedType;
import com.redhat.ceylon.compiler.java.runtime.model.TypeDescriptor;
import com.redhat.ceylon.model.typechecker.model.Parameter;
import com.redhat.ceylon.model.typechecker.model.Reference;

import ceylon.language.Array;
import ceylon.language.Sequence;
import ceylon.language.Sequential;
import ceylon.language.empty_;
import ceylon.language.meta.declaration.FunctionDeclaration;

@Ceylon(major = 8)
@com.redhat.ceylon.compiler.java.metadata.Class
@TypeParameters({
    @TypeParameter(value = "Type", variance = Variance.OUT),
    @TypeParameter(value = "Arguments", variance = Variance.IN, satisfies = "ceylon.language::Sequential<ceylon.language::Anything>"),
    })
public class FunctionImpl<Type, Arguments extends Sequential<? extends Object>> 
    implements ceylon.language.meta.model.Function<Type, Arguments>, ReifiedType, DefaultValueProvider {

    @Ignore
    private final TypeDescriptor $reifiedType;
    @Ignore
    private final TypeDescriptor $reifiedArguments;
    
    private ceylon.language.meta.model.Type<? extends Type> type;
    protected final FunctionDeclarationImpl declaration;
    private MethodHandle method;
    private MethodHandle[] dispatch;
    private int firstDefaulted = -1;
    private int variadicIndex = -1;
    private ceylon.language.Map<? extends ceylon.language.meta.declaration.TypeParameter, ? extends ceylon.language.meta.model.Type<?>> typeArguments;
    private ceylon.language.Map<? extends ceylon.language.meta.declaration.TypeParameter, ? extends Sequence<? extends Object>> typeArgumentWithVariances;
    private Object instance;
    private ceylon.language.meta.model.Type<?> container;
    private List<com.redhat.ceylon.model.typechecker.model.Type> parameterProducedTypes;
    private Sequential<? extends ceylon.language.meta.model.Type<? extends Object>> parameterTypes;
    private Reference appliedFunction;

    public FunctionImpl(@Ignore TypeDescriptor $reifiedType, 
                           @Ignore TypeDescriptor $reifiedArguments,
                           Reference appliedFunction, FunctionDeclarationImpl function, 
                           ceylon.language.meta.model.Type<?> container,
                           Object instance) {
        this.$reifiedType = $reifiedType;
        this.$reifiedArguments = $reifiedArguments;
        this.container = container;
        this.instance = instance;
        this.appliedFunction = appliedFunction;
        
        com.redhat.ceylon.model.typechecker.model.Functional decl = (com.redhat.ceylon.model.typechecker.model.Functional) function.declaration;
        List<Parameter> parameters = decl.getFirstParameterList().getParameters();

        this.firstDefaulted = Metamodel.getFirstDefaultedParameter(parameters);
        this.variadicIndex = Metamodel.getVariadicParameter(parameters);

        Method[] defaultedMethods = null;
        if(firstDefaulted != -1){
            // if we have 2 params and first is defaulted we need 2 + 1 - 0 = 3 methods:
            // f(), f(a) and f(a, b)
            this.dispatch = new MethodHandle[parameters.size() + 1 - firstDefaulted];
            defaultedMethods = new Method[dispatch.length];
        }

        this.type = Metamodel.getAppliedMetamodel(Metamodel.getFunctionReturnType(appliedFunction));
        
        this.declaration = function;
        
        this.typeArguments = Metamodel.getTypeArguments(declaration, appliedFunction);
        this.typeArgumentWithVariances = Metamodel.getTypeArgumentWithVariances(declaration, appliedFunction);

        // get a list of produced parameter types
        this.parameterProducedTypes = Metamodel.getParameterProducedTypes(parameters, appliedFunction);
        this.parameterTypes = Metamodel.getAppliedMetamodelSequential(this.parameterProducedTypes);

        
        java.lang.Class<?> javaClass = Metamodel.getJavaClass(function.declaration);
        java.lang.reflect.Method found = null;
        
        // FIXME: delay method setup for when we actually use it?
        String name = Metamodel.getJavaMethodName((com.redhat.ceylon.model.typechecker.model.Functional) function.declaration);
        
        // special cases for some erased types
        if(javaClass == ceylon.language.Object.class
                || javaClass == ceylon.language.Basic.class
                || javaClass == ceylon.language.Identifiable.class){
            if("equals".equals(name)){
                // go fetch the method Object.equals
                try {
                    found = java.lang.Object.class.getDeclaredMethod("equals", java.lang.Object.class);
                } catch (NoSuchMethodException e) {
                    throw Metamodel.newModelError("Missing equals method in ceylon.language::Object");
                } catch (SecurityException e) {
                    throw Metamodel.newModelError("Security exception getting equals method in ceylon.language::Object");
                }
            }else{
                throw Metamodel.newModelError("Object/Basic/Identifiable member not supported: "+decl.getName());
            }
        } else if (javaClass == ceylon.language.Throwable.class) {
            if("printStackTrace".equals(decl.getName())){
                try {
                    found = java.lang.Throwable.class.getDeclaredMethod("printStackTrace");
                } catch (NoSuchMethodException e) {
                    throw Metamodel.newModelError("Missing printStackTrace method in ceylon.language::Throwable");
                } catch (SecurityException e) {
                    throw Metamodel.newModelError("Security exception getting printStackTrace method in ceylon.language::Throwable");
                }
            }
        } else{
            // FIXME: deal with Java classes and overloading
            // FIXME: faster lookup with types? but then we have to deal with erasure and stuff
            found = Metamodel.getJavaMethod((com.redhat.ceylon.model.typechecker.model.Function) function.declaration);
            
            int reifiedTypeParameterCount = MethodHandleUtil.isReifiedTypeSupported(found, false) ? found.getTypeParameters().length : 0;
            boolean isArray = MethodHandleUtil.isJavaArray(javaClass);
            for(Method method : javaClass.getDeclaredMethods()){
                if(!method.getName().equals(name))
                    continue;
                if(method.isBridge() || method.isSynthetic())
                    continue;
                // skip static methods if we're in array types
                if(isArray && Modifier.isStatic(method.getModifiers()))
                    continue;
                if(method.isAnnotationPresent(Ignore.class)){
                    // save method for later
                    // FIXME: proper checks
                    if(firstDefaulted != -1){
                        // do not count reified type parameters
                        int params = method.getParameterTypes().length - reifiedTypeParameterCount;
                        defaultedMethods[params - firstDefaulted] = method;
                    }
                    continue;
                }
                // FIXME: deal with private stuff?
            }
        }
        if(found != null){
            
            boolean variadic = found.isVarArgs();
            method = reflectionToMethodHandle(found, javaClass, instance, appliedFunction, parameterProducedTypes, variadic, false);
            if(defaultedMethods != null && !variadic){
                // this won't find the last one, but its method
                int i=0;
                for(;i<defaultedMethods.length-1;i++){
                    if(defaultedMethods[i] == null)
                        throw Metamodel.newModelError("Missing defaulted method "+found.getName()
                                +" with "+(i+firstDefaulted)+" parameters in "+found.getDeclaringClass());
                    dispatch[i] = reflectionToMethodHandle(defaultedMethods[i], javaClass, instance, appliedFunction, parameterProducedTypes, variadic, false);
                }
                dispatch[i] = method;
            }else if(variadic){
                // variadic methods don't have defaulted parameters, but we will simulate one because our calling convention is that
                // we treat variadic methods as if the last parameter is optional
                // firstDefaulted and dispatch already set up because getFirstDefaultedParameter treats java variadics like ceylon variadics
                dispatch[0] = reflectionToMethodHandle(found, javaClass, instance, appliedFunction, parameterProducedTypes, variadic, true);
                dispatch[1] = method;
            }
        }
    }

    private MethodHandle reflectionToMethodHandle(Method found, java.lang.Class<?> javaClass, Object instance, 
                                                  Reference appliedFunction, 
                                                  List<com.redhat.ceylon.model.typechecker.model.Type> parameterProducedTypes,
                                                  boolean variadic, boolean bindVariadicParameterToEmptyArray) {
        // save the parameter types before we mess with "found"
        java.lang.Class<?>[] parameterTypes;
        java.lang.Class<?> returnType;
        MethodHandle method = null;
        boolean isJavaArray;
        int mods;
        int typeParametersCount;
        int skipParameters = 0;
        List<com.redhat.ceylon.model.typechecker.model.TypeParameter> reifiedTypeParameters;
        if (found instanceof java.lang.reflect.Method) {
            com.redhat.ceylon.model.typechecker.model.Function functionModel = (com.redhat.ceylon.model.typechecker.model.Function)appliedFunction.getDeclaration();
            java.lang.reflect.Method foundMethod = (java.lang.reflect.Method)found;
            parameterTypes = foundMethod.getParameterTypes();
            returnType = foundMethod.getReturnType();
            mods = foundMethod.getModifiers();
            isJavaArray = MethodHandleUtil.isJavaArray(javaClass);
            typeParametersCount = foundMethod.getTypeParameters().length;
            try {
                if(isJavaArray){
                    if(foundMethod.getName().equals("get"))
                        method = MethodHandleUtil.getJavaArrayGetterMethodHandle(javaClass);
                    else if(foundMethod.getName().equals("set"))
                        method = MethodHandleUtil.getJavaArraySetterMethodHandle(javaClass);
                    else if(foundMethod.getName().equals("copyTo")){
                        foundMethod = MethodHandleUtil.getJavaArrayCopyToMethod(javaClass, foundMethod);
                    }
                }
                if(method == null){
                    foundMethod.setAccessible(true);
                    method = MethodHandles.lookup().unreflect(foundMethod);
                }
            } catch (IllegalAccessException e) {
                throw Metamodel.newModelError("Problem getting a MH for constructor for: "+javaClass, e);
            }
            reifiedTypeParameters = functionModel.getTypeParameters();
        } else {
            throw new RuntimeException();
        }
        
        // box the return type
        method = MethodHandleUtil.boxReturnValue(method, returnType, appliedFunction.getType());
        // we need to cast to Object because this is what comes out when calling it in $call
        if(instance != null 
                && (isJavaArray || !Modifier.isStatic(mods)))
            method = method.bindTo(instance);
        method = method.asType(MethodType.methodType(Object.class, parameterTypes));
        // insert any required type descriptors
        if(typeParametersCount != 0 && MethodHandleUtil.isReifiedTypeSupported(found, false)){
            List<com.redhat.ceylon.model.typechecker.model.Type> typeArguments = new ArrayList<com.redhat.ceylon.model.typechecker.model.Type>();
            Map<com.redhat.ceylon.model.typechecker.model.TypeParameter, com.redhat.ceylon.model.typechecker.model.Type> typeArgumentMap = appliedFunction.getTypeArguments();
            for (com.redhat.ceylon.model.typechecker.model.TypeParameter tp : reifiedTypeParameters) {
                typeArguments.add(typeArgumentMap.get(tp));
            }
            method = MethodHandleUtil.insertReifiedTypeArguments(method, 0, typeArguments);
            skipParameters += typeParametersCount;
        }
        // now convert all arguments (we may need to unbox)
        method = MethodHandleUtil.unboxArguments(method, skipParameters, 0, parameterTypes,
                                                 parameterProducedTypes, variadic, bindVariadicParameterToEmptyArray);
        return method;
    }

    @Override
    public FunctionDeclaration getDeclaration(){
        return declaration;
    }
    
    @Override
    @TypeInfo("ceylon.language::Map<ceylon.language.meta.declaration::TypeParameter,ceylon.language.meta.model::Type<ceylon.language::Anything>>")
    public ceylon.language.Map<? extends ceylon.language.meta.declaration.TypeParameter, ? extends ceylon.language.meta.model.Type<?>> getTypeArguments() {
        return typeArguments;
    }
    
    @Override
    public ceylon.language.Sequential<? extends ceylon.language.meta.model.Type<?>> getTypeArgumentList() {
        return Metamodel.getTypeArgumentList(this);
    }

    @Override
    @TypeInfo("ceylon.language::Map<ceylon.language.meta.declaration::TypeParameter,[ceylon.language.meta.model::Type<ceylon.language::Anything>,ceylon.language.meta.declaration::Variance]>")
    public ceylon.language.Map<? extends ceylon.language.meta.declaration.TypeParameter, ? extends ceylon.language.Sequence<? extends Object>> getTypeArgumentWithVariances() {
        return typeArgumentWithVariances;
    }
    
    @Override
    @TypeInfo("ceylon.language::Sequential<[ceylon.language.meta.model::Type<ceylon.language::Anything>,ceylon.language.meta.declaration::Variance]>")
    public ceylon.language.Sequential<? extends ceylon.language.Sequence<? extends Object>> getTypeArgumentWithVarianceList() {
        return Metamodel.getTypeArgumentWithVarianceList(this);
    }

    private void checkMethod(){
        if(method == null)
            throw Metamodel.newModelError("No method found for: "+declaration.getName());
    }
    
    @Ignore
    @Override
    public Type $call$() {
        checkMethod();
        try {
            if(firstDefaulted == -1)
                return (Type)method.invokeExact();
            // FIXME: proper checks
            return (Type)dispatch[0].invokeExact();
        } catch (Throwable e) {
            Util.rethrow(e);
            return null;
        }
    }

    @Ignore
    @Override
    public Type $call$(Object arg0) {
        checkMethod();
        try {
            if(firstDefaulted == -1)
                return (Type)method.invokeExact(arg0);
            // FIXME: proper checks
            return (Type)dispatch[1-firstDefaulted].invokeExact(arg0);
        } catch (Throwable e) {
            Util.rethrow(e);
            return null;
        }
    }

    @Ignore
    @Override
    public Type $call$(Object arg0, Object arg1) {
        checkMethod();
        try {
            if(firstDefaulted == -1)
                return (Type)method.invokeExact(arg0, arg1);
            // FIXME: proper checks
            return (Type)dispatch[2-firstDefaulted].invokeExact(arg0, arg1);
        } catch (Throwable e) {
            Util.rethrow(e);
            return null;
        }
    }

    @Ignore
    @Override
    public Type $call$(Object arg0, Object arg1, Object arg2) {
        checkMethod();
        try {
            if(firstDefaulted == -1)
                return (Type)method.invokeExact(arg0, arg1, arg2);
            // FIXME: proper checks
            return (Type)dispatch[3-firstDefaulted].invokeExact(arg0, arg1, arg2);
        } catch (Throwable e) {
            Util.rethrow(e);
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    @Ignore
    @Override
    public Type $call$(Object... args) {
        checkMethod();
        try {
            // FIXME: this does not do invokeExact and does boxing/widening
            if(firstDefaulted == -1)
                return (Type)method.invokeWithArguments(args);
            // FIXME: proper checks
            return (Type)dispatch[args.length-firstDefaulted].invokeWithArguments(args);
        } catch (Throwable e) {
            Util.rethrow(e);
            return null;
        }
    }

    @Ignore
    @Override
    public short $getVariadicParameterIndex$() {
        return (short)variadicIndex;
    }

    @Override
    @TypeInfo("ceylon.language.meta.model::Type<Type>")
    public ceylon.language.meta.model.Type<? extends Type> getType() {
        return type;
    }

    @Override
    @Ignore
    public Type $callvariadic$() {
        return $call$();
    }
    
    @Override
    @Ignore
    public Type $callvariadic$(Sequential<?> varargs) {
        return $call$(varargs);
    }

    @Override
    @Ignore
    public Type $callvariadic$(Object arg0,
            Sequential<?> varargs) {
        return $call$(arg0, varargs);
    }

    @Override
    @Ignore
    public Type $callvariadic$(Object arg0,
            Object arg1, Sequential<?> varargs) {
        return $call$(arg0, arg1, varargs);
    }

    @Override
    @Ignore
    public Type $callvariadic$(Object arg0,
            Object arg1, Object arg2, Sequential<?> varargs) {
        return $call$(arg0, arg1, arg2, varargs);
    }

    @Override
    @Ignore
    public Type $callvariadic$(Object... argsAndVarargs) {
        return $call$((Object[])argsAndVarargs);
    }

    @Override
    @Ignore
    public Type $callvariadic$(Object arg0) {
        return $call$(arg0, empty_.get_());
    }

    @Override
    @Ignore
    public Type $callvariadic$(Object arg0, Object arg1) {
        return $call$(arg0, arg1, empty_.get_());
    }

    @Override
    @Ignore
    public Type $callvariadic$(Object arg0, Object arg1, Object arg2) {
        return $call$(arg0, arg1, arg2, empty_.get_());
    }

    @Ignore
    @Override
    public Type apply(){
        return apply(empty_.get_());
    }

    @Override
    public Type apply(@Name("arguments")
        @Sequenced
        @TypeInfo("ceylon.language::Sequential<ceylon.language::Anything>")
        Sequential<?> arguments){
        
        return Metamodel.apply(this, arguments, parameterProducedTypes, firstDefaulted, variadicIndex);
    }

    @Override
    public Type namedApply(@Name("arguments")
        @TypeInfo("ceylon.language::Iterable<ceylon.language::Entry<ceylon.language::String,ceylon.language::Anything>,ceylon.language::Null>")
        ceylon.language.Iterable<? extends ceylon.language.Entry<? extends ceylon.language.String,? extends java.lang.Object>,? extends java.lang.Object> arguments){

        return Metamodel.namedApply(this, this, 
                (com.redhat.ceylon.model.typechecker.model.Functional)declaration.declaration, 
                arguments, parameterProducedTypes);
    }

    @Override
    public Object getDefaultParameterValue(Parameter parameter, Array<Object> values, int collectedValueCount) {
        // find the right class
        java.lang.Class<?> javaClass = Metamodel.getJavaClass(declaration.declaration);
        // default method name
        String name = declaration.getName()+"$"+parameter.getName();
        Method found = null;
        // iterate to find it, rather than figure out its parameter types
        for(Method m : javaClass.getDeclaredMethods()){
            if(m.getName().equals(name)){
                found = m;
                break;
            }
        }
        if(found == null)
            throw Metamodel.newModelError("Default argument method for "+parameter.getName()+" not found");
        int parameterCount = found.getParameterTypes().length;
        if(MethodHandleUtil.isReifiedTypeSupported(found, false))
            parameterCount -= found.getTypeParameters().length;
        if(parameterCount != collectedValueCount)
            throw Metamodel.newModelError("Default argument method for "+parameter.getName()+" requires wrong number of parameters: "+parameterCount+" should be "+collectedValueCount);
        
        // AFAIK default value methods cannot be Java-variadic 
        MethodHandle methodHandle = reflectionToMethodHandle(found, javaClass, instance, appliedFunction, parameterProducedTypes, false, false);
        // sucks that we have to copy the array, but that's the MH API
        java.lang.Object[] arguments = new java.lang.Object[collectedValueCount];
        System.arraycopy(values.toArray(), 0, arguments, 0, collectedValueCount);
        try {
            return methodHandle.invokeWithArguments(arguments);
        } catch (Throwable e) {
            Util.rethrow(e);
            return null;
        }
    }

    @TypeInfo("ceylon.language::Sequential<ceylon.language.meta.model::Type<ceylon.language::Anything>>")
    @Override
    public ceylon.language.Sequential<? extends ceylon.language.meta.model.Type<? extends Object>> getParameterTypes(){
        return parameterTypes;
    }

    @Override
    public int hashCode() {
        int result = 1;
        // in theory, if our instance is the same, our containing type should be the same
        // and if we don't have an instance we're a toplevel and have no containing type
        result = 37 * result + (instance == null ? 0 : instance.hashCode());
        result = 37 * result + getDeclaration().hashCode();
        // so far, functions can't have use-site variance
        result = 37 * result + getTypeArguments().hashCode();
        return result;
    }
    
    @Override
    public boolean equals(Object obj) {
        if(obj == null)
            return false;
        if(obj == this)
            return true;
        if(obj instanceof FunctionImpl == false)
            return false;
        FunctionImpl<?,?> other = (FunctionImpl<?,?>) obj;
        // in theory, if our instance is the same, our containing type should be the same
        // and if we don't have an instance we're a toplevel and have no containing type
        return getDeclaration().equals(other.getDeclaration())
                && Util.eq(instance, other.instance)
                // so far, functions can't have use-site variance
                && getTypeArguments().equals(other.getTypeArguments());
    }


    @Override
    @TypeInfo("ceylon.language.meta.model::Type<ceylon.language::Anything>|ceylon.language::Null")
    public ceylon.language.meta.model.Type<?> getContainer(){
        return container;
    }

    @Override
    public String toString() {
        return Metamodel.toTypeString(this);
    }

    @Ignore
    @Override
    public TypeDescriptor $getType$() {
        return TypeDescriptor.klass(FunctionImpl.class, $reifiedType, $reifiedArguments);
    }
}
