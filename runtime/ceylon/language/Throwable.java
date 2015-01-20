package ceylon.language;

import com.redhat.ceylon.compiler.java.metadata.Ceylon;
import com.redhat.ceylon.compiler.java.metadata.Class;
import com.redhat.ceylon.compiler.java.metadata.Defaulted;
import com.redhat.ceylon.compiler.java.metadata.Ignore;
import com.redhat.ceylon.compiler.java.metadata.Name;
import com.redhat.ceylon.compiler.java.metadata.Transient;
import com.redhat.ceylon.compiler.java.metadata.TypeInfo;
import com.redhat.ceylon.compiler.java.runtime.model.ReifiedType;
import com.redhat.ceylon.compiler.java.runtime.model.TypeDescriptor;

@Ceylon(major = 8)
@Class(extendsType = "ceylon.language::Basic")
public abstract class Throwable extends java.lang.Object 
        implements ReifiedType {

    @Ignore
    public final static TypeDescriptor $TypeDescriptor$ = TypeDescriptor.klass(Throwable.class);

    @Ignore
    private final java.lang.String description;
    
    public Throwable(
            @TypeInfo("ceylon.language::Null|ceylon.language::String")
            @Name("description")
            @Defaulted
            java.lang.String description,
            @TypeInfo("ceylon.language::Null|ceylon.language::Throwable")
            @Name("cause")
            @Defaulted
            java.lang.Throwable cause) {
        //super(description==null ? null : description.toString(), cause);
        this.description = description;
    }
    
    @Ignore
    public Throwable(java.lang.String description) {
        this(description, $default$cause(description));
    }
    
    @Ignore
    public Throwable() {
        this($default$description());
    }
        
    @TypeInfo("ceylon.language::Null|ceylon.language::Throwable")
    public final java.lang.Throwable getCause() {
        return null;//super.getCause();
    }
    
    @TypeInfo("ceylon.language::String")
    @Transient
    public java.lang.String getMessage() {
        if (description != null) {
            return description.toString();
        } 
        else if (getCause() != null 
                && getCause().getMessage() != null) {
            return getCause().getMessage();
        }
        return "";
    }

    @TypeInfo("ceylon.language::String")
    @Transient
    public java.lang.String toString() {
        return className_.className(this) + " \"" + getMessage() +"\""; 
    }
    
    //@Override
    public final void printStackTrace() {
    	//super.printStackTrace();
    }

    @Ignore
    public static java.lang.String $default$description(){
        return null;
    }
    @Ignore
    public static java.lang.Throwable $default$cause(java.lang.String description){
        return null;
    }
    
    @TypeInfo("ceylon.language::Sequential<ceylon.language::Throwable>")
    public final Sequential<Throwable> getSuppressed() {
        return null;
    }
    
    public final void addSuppressed(
            @Name("suppressed")
            @TypeInfo("ceylon.language::Throwable")
            java.lang.Throwable suppressed) {
        
    }
    
    @Override
    @Ignore
    public TypeDescriptor $getType$() {
        return $TypeDescriptor$;
    }
}
