package com.redhat.ceylon.compiler.java.runtime.metamodel;

import ceylon.language.meta.declaration.AliasDeclaration;
import ceylon.language.meta.declaration.ClassDeclaration;
import ceylon.language.meta.declaration.ClassOrInterfaceDeclaration;
import ceylon.language.meta.declaration.ConstructorDeclaration;
import ceylon.language.meta.declaration.Declaration;
import ceylon.language.meta.declaration.FunctionDeclaration;
import ceylon.language.meta.declaration.Module;
import ceylon.language.meta.declaration.Package;
import ceylon.language.meta.declaration.TypeParameter;
import ceylon.language.meta.declaration.ValueDeclaration;

/**
 * <p>A reusable but non-threadsafe parser for the serialized form of 
 * Declarations used for annotations.</p>
 * 
 * <pre> 
 * ref              ::= version? module ;
 *                      // note: version is optional to support looking up the
 *                      // runtime version of a package, once we support this
 * version          ::= ':' SENTINEL ANYCHAR* SENTINEL ;
 * module           ::= dottedIdent package? ;
 * dottedIdent      ::= ident ('.' ident)* ;
 * package          ::= ':' ( relativePackage | absolutePackage ) ? ( ':' declaration ) ? ;
 *                      // note: if no absolute or relative package given, it's the 
 *                      // root package of the module
 * relativePackage  ::= dottedIdent ;
 * absolutePackage  ::= '.' dottedIdent ;
 *                      // note: to suport package names which don't start 
 *                      // with the module name
 * declaration      ::= type | function | value;
 * type             ::= class | interface | alias ;
 * class            ::= 'C' ident ( '.' memberOrTp )? ;
 * interface        ::= 'I' ident ( '.' memberOrTp )? ;
 * alias            ::= 'A' ident ( '.' memberOrTp )? ;
 * memberOrTp       ::= typeParameter | member ;
 * member           ::= declaration ;
 * typeParameter    ::= 'P' ident ;
 * function         ::= 'F' ident ( '.' typeParameter )?;
 * value            ::= 'V' ident ;
 * </pre>
 * Note: The scope of a type parameter (or value parameter) is the scope of 
 * the class or function, which means you can't make references to them 
 * from outside, so although `given Element` makes sense within the scope 
 * of `sort`, `given sort.Element` is disallowed.
 */

class DeclarationParser {
    private int i = 0;
    private String ref;
    
    private java.lang.RuntimeException parseError(String msg) {
        throw Metamodel.newModelError(msg + " at index " + i + ": " + ref);
    }
    
    private java.lang.RuntimeException unexpectedToken () {
        return parseError("Unexpected token");
    }

    private boolean atEnd() {
        return i == ref.length();
    }
    
    private boolean at(char token) {
        if (!atEnd() && ref.charAt(i) == token) {
            i += 1;
            return true;
        }
        return false;
    }
    
    public Declaration ref(String ref) {
        i = 0;
        this.ref = ref;
        String version = version();
        Declaration module = module(version);
        return module;
    }
    
    private String version() {
        if (!at(':')) {
            return null;
        }
        // Because a Ceylon version can contain *any* character 
        // the next character we read is the sentinel and 
        // the version is everything after that, until that sentinal next 
        // occurs
        char sentinal = ref.charAt(i);
        i++;
        int start = i;
        while(i < ref.length()) {
            if (ref.charAt(i) == sentinal) {
                i++;
                return ref.substring(start, i-1);
            }
            i++;
        }
        throw parseError("Unterminated version");
    }

    private Declaration module(String version) {
        String moduleName = moduleName();
        if (moduleName == null || moduleName.isEmpty()) {
            throw parseError("Missing module name");
        }
        Module module = makeModule(moduleName, version);
        if (atEnd()) {
            return module;
        }
        return package_(module);
        
    }
    
    private String moduleName() {
        return dottedIdent();
    }
    
    private boolean atIdent() {
        int start = i;
        while (i < ref.length()) {
            char ch = ref.charAt(i);
            if (!(Character.isLetter(ch)
                    || Character.isDigit(ch)
                    || ch == '_')) {
                break;
            }
            i++;
        }
        return i > start;
    }
    
    private String ident() {
        int start = i;
        if (atIdent()) {
            return ref.substring(start, i);
        }
        return null;
    }
    
    private String dottedIdent() {
        int start = i;
        while (atIdent()) {
            if (atEnd() || !at('.')) {
                break;
            }
        }
        return ref.substring(start, i);
    }
    
    private Declaration package_(Module module) {
        if (!at(':')) {
            throw parseError("Expecting a colon at start of package");
        }
        String packageName;
        boolean expectColon = false;
        if (at(':')) {
            packageName = module.getName();
        } else if (at('.')) {
           packageName = packageName();
           expectColon = true;
        } else {
            packageName = module.getName() + '.' + packageName();
            expectColon = true;
        }
        Package package_ = makePackage(module, packageName);
        if (atEnd()) {
            return package_;
        } else if (expectColon
                && !at(':')) {
            throw parseError("Expecting a colon at end of package");
        }
        return declaration(package_);
    }

    private String packageName() {
        return dottedIdent();
    }
    
    private Declaration declaration(Declaration packageOrType) {
        Declaration result = type(packageOrType);
        if (result == null) {
            result = function(packageOrType);
        }
        if (result == null) {
            result = value(packageOrType);
        }
        if (result == null) {
            result = constructor(packageOrType);
        }
        if (result == null) {
            throw parseError("Expected type or function or value");
        }
        return result;
    }
    
    private Declaration function(Declaration packageOrType) {
        if (!at('F')) {
            return null;
        }
        Declaration result;
        String fn = ident();
        if (fn != null) {
            result = makeFunction(packageOrType, fn);
            if (at('.')) {
                result = typeParameter(result);
            }
            if (!atEnd()) {
                throw unexpectedToken();
            }
            return result;
        } else {
            throw unexpectedToken();
        }
    }
    
    private Declaration value(Declaration packageOrType) {
        if (!at('V')) {
            return null;
        }
        String fn = ident();
        if (fn != null) {
            return makeValue(packageOrType, fn);
        } else {
            throw unexpectedToken();
        }
    }
    
    private Declaration constructor(Declaration packageOrType) {
        if (!at('c')) {
            return null;
        }
        String fn = ident();
        if (fn != null) {
            return makeConstructor(packageOrType, fn);
        } else {
            throw unexpectedToken();
        }
    }

    private Declaration type(Declaration packageOrType) {
        Declaration result = class_(packageOrType);
        if (result == null) {
            result = interface_(packageOrType);
        }
        if (result == null) {
            result = alias(packageOrType);
        }
        if (result != null && at('.')) {
            return tpOrMember(result);
        }
        return result;
    }
    
    private Declaration tpOrMember(Declaration type) {
        Declaration result = typeParameter(type);
        if (result == null) {
            result = declaration(type);
        }
        return result;
    }

    private ClassOrInterfaceDeclaration class_(Declaration packageOrType) {
        if (!at('C')) {
            return null;
        }
        String fn = ident();
        if (fn != null) {
            return makeClassOrInterface(packageOrType, fn);
        }
        throw unexpectedToken();
    }

    private ClassOrInterfaceDeclaration interface_(Declaration packageOrType) {
        if (!at('I')) {
            return null;
        }
        String fn = ident();
        if (fn != null) {
            return makeClassOrInterface(packageOrType, fn);
        }
        throw unexpectedToken();
    }

    private AliasDeclaration alias(Declaration packageOrType) {
        if (!at('A')) {
            return null;
        }
        String fn = ident();
        if (fn != null) {
            return makeAlias(packageOrType, fn);
        }
        throw unexpectedToken();
    }
    
    private TypeParameter typeParameter(Declaration packageOrType) {
        if (!at('P')) {
            return null;
        }
        String fn = ident();
        if (fn != null) {
            return makeTypeParameter(packageOrType, fn);
        }
        throw unexpectedToken();
    }

    private java.lang.RuntimeException metamodelError(String msg) {
        return Metamodel.newModelError(msg);
    }
    
    private java.lang.RuntimeException metamodelNotFound(String msg) {
        return Metamodel.newModelError(msg);
    }
    
    protected Module makeModule(String moduleName, String version) {
        if (version == null) {
            throw metamodelError("Runtime versions not yet supported");
        }
        Module module = ceylon.language.meta.modules_.get_().find(moduleName, version);
        if (module == null) {
            throw metamodelNotFound("Could not find module: " + moduleName + ", version: " + version);
        }
        return module;
    }
    
    protected Package makePackage(Module module, String packageName) {
        Package package_ = module.findPackage(packageName);
        if (package_ == null) {
            throw metamodelNotFound("Could not find package: " + packageName + " of module: " + module.getName() + ", version: " + module.getVersion());
        }
        return package_;
    }
    
    // .ceylon.language.meta.model.typeLiteral_.typeLiteral(.ceylon.language.Anything.$TypeDescriptor$)
    protected ClassOrInterfaceDeclaration makeClassOrInterface(Declaration packageOrType, String typeName) {
        final ClassOrInterfaceDeclaration result;
        if (packageOrType instanceof Package) {
            result = ((Package)packageOrType).getMember(ClassOrInterfaceDeclaration.$TypeDescriptor$, typeName);
        } else if (packageOrType instanceof ClassOrInterfaceDeclaration) {
            result = ((ClassOrInterfaceDeclaration)packageOrType).<ClassOrInterfaceDeclaration>getMemberDeclaration(ClassOrInterfaceDeclaration.$TypeDescriptor$, typeName);
        } else {
            throw metamodelError("Unexpected container " + packageOrType.getClass() + " for type " + typeName);
        }
        if (result == null) {
            throw metamodelNotFound("Could not find type: " + typeName + " in " + packageOrType.getName());
        }
        return result;
    }

    protected AliasDeclaration makeAlias(Declaration packageOrType, String aliasName) {
        final AliasDeclaration result;
        if (packageOrType instanceof Package) {
            result = ((Package)packageOrType).getAlias(aliasName);
        } else if (packageOrType instanceof ClassOrInterfaceDeclaration) {
            result = ((ClassOrInterfaceDeclaration)packageOrType).<AliasDeclaration>getMemberDeclaration(AliasDeclaration.$TypeDescriptor$, aliasName);
        } else {
            throw metamodelError("Unexpected container " + packageOrType.getClass() + " for alias " + aliasName);
        }
        if (result == null) {
            throw metamodelNotFound("Could not find alias: " + aliasName + " in " + packageOrType.getName());
        }
        return result;
    }
    
    protected TypeParameter makeTypeParameter(Declaration packageOrType, String typeParameter) {
        final TypeParameter result;
        if (packageOrType instanceof ClassOrInterfaceDeclaration) {
            result = ((ClassOrInterfaceDeclaration)packageOrType).getTypeParameterDeclaration(typeParameter);
        } else if (packageOrType instanceof FunctionDeclaration) {
            result = ((FunctionDeclaration)packageOrType).getTypeParameterDeclaration(typeParameter);
        } else if (packageOrType instanceof AliasDeclaration) {
            result = ((AliasDeclaration)packageOrType).getTypeParameterDeclaration(typeParameter);
        } else {
            throw metamodelError("Unexpected container " + packageOrType.getClass() + " for type parameter " + typeParameter);
        }
        if (result == null) {
            throw metamodelNotFound("Could not find alias: " + typeParameter + " in " + packageOrType.getName());
        }
        return result;
    }

    protected FunctionDeclaration makeFunction(Declaration packageOrType, String fn) {
        final FunctionDeclaration result;
        if (packageOrType instanceof Package) {
            result = ((Package)packageOrType).getFunction(fn);
        } else if (packageOrType instanceof ClassOrInterfaceDeclaration) {
            result = ((ClassOrInterfaceDeclaration)packageOrType).<FunctionDeclaration>getMemberDeclaration(FunctionDeclaration.$TypeDescriptor$, fn);
        } else {
            throw metamodelError("Unexpected container " + packageOrType.getClass() + " for function " + fn);
        }
        if (result == null) {
            throw metamodelNotFound("Could not find function: " + fn + " in " + packageOrType.getName());
        }
        return result;
    }
    
    
    protected ValueDeclaration makeValue(Declaration packageOrType, String val) {
        final ValueDeclaration result;
        if (packageOrType instanceof Package) {
            result = ((Package)packageOrType).getValue(val);
        } else if (packageOrType instanceof ClassOrInterfaceDeclaration) {
            result = ((ClassOrInterfaceDeclaration)packageOrType).<ValueDeclaration>getMemberDeclaration(ValueDeclaration.$TypeDescriptor$, val);
        } else {
            throw metamodelError("Unexpected container " + packageOrType.getClass() + " for value " + val);
        }
        if (result == null) {
            throw metamodelNotFound("Could not find value: " + val + " in " + packageOrType.getName());
        }
        return result;
    }
    
    protected ConstructorDeclaration makeConstructor(Declaration packageOrType, String val) {
        final ConstructorDeclaration result;
        if (packageOrType instanceof ClassDeclaration) {
            result = ((ClassDeclaration)packageOrType).getConstructorDeclaration(val);
        } else {
            throw metamodelError("Unexpected container " + packageOrType.getClass() + " for value " + val);
        }
        if (result == null) {
            throw metamodelNotFound("Could not find value: " + val + " in " + packageOrType.getName());
        }
        return result;
    }

}
