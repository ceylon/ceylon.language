package ceylon.language;

import com.redhat.ceylon.compiler.java.metadata.Ceylon;
import com.redhat.ceylon.compiler.java.metadata.Ignore;
import com.redhat.ceylon.compiler.java.metadata.Method;
import com.redhat.ceylon.compiler.java.metadata.Name;
import com.redhat.ceylon.compiler.java.metadata.Sequenced;
import com.redhat.ceylon.compiler.java.metadata.TypeInfo;

@Ceylon(major = 3)
@Method
public class see_
{
    public static Null see(@Name("programElements") @Sequenced
            @TypeInfo("ceylon.language::Sequential<ceylon.language::Anything>")
            final ceylon.language.Sequential<? extends java.lang.Object> value) {
        return null;
    }
    @Ignore
    public static Null see() {
        return null;
    }
    private see_(){}
}
