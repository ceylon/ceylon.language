package ceylon.language;

import com.redhat.ceylon.compiler.java.metadata.Attribute;
import com.redhat.ceylon.compiler.java.metadata.Ceylon;
import com.redhat.ceylon.compiler.java.metadata.TypeInfo;

@Ceylon(major = 3) @Attribute
public class bottom_ {
    @TypeInfo("ceylon.language::Nothing")
    public static <T> T getNothing$(){
        throw new Exception(null, null);
    }
}