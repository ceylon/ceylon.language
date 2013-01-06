package ceylon.language;

import com.redhat.ceylon.compiler.java.metadata.Ceylon;
import com.redhat.ceylon.compiler.java.metadata.Method;

@Ceylon(major = 3)
@Method
public final class optional {
    public static Null optional(){
        return null;
    }
    private optional(){}
}
