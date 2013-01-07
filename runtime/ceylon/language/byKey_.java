package ceylon.language;

import com.redhat.ceylon.compiler.java.language.AbstractCallable;
import com.redhat.ceylon.compiler.java.metadata.Ceylon;
import com.redhat.ceylon.compiler.java.metadata.Method;
import com.redhat.ceylon.compiler.java.metadata.Name;
import com.redhat.ceylon.compiler.java.metadata.TypeInfo;
import com.redhat.ceylon.compiler.java.metadata.TypeParameter;
import com.redhat.ceylon.compiler.java.metadata.TypeParameters;

@Ceylon(major = 3)
@Method
public class byKey_ {

    private byKey_(){}

    @TypeParameters(@TypeParameter(value="Key", satisfies="ceylon.language::Value"))
    @TypeInfo("ceylon.language::Callable<ceylon.language::Null|ceylon.language::Comparison,ceylon.language::Tuple<ceylon.language::Entry<Key,ceylon.language::Value>,ceylon.language::Entry<Key,ceylon.language::Value>,ceylon.language::Tuple<ceylon.language::Entry<Key,ceylon.language::Value>,ceylon.language::Entry<Key,ceylon.language::Value>,ceylon.language::Empty>>>")
    public static <Element> Callable<? extends Comparison> byKey(
            @Name("comparing")
            @TypeInfo("ceylon.language::Callable<ceylon.language::Null|ceylon.language::Comparison,ceylon.language::Tuple<Key,Key,ceylon.language::Tuple<Key,Key,ceylon.language::Empty>>>")
            final Callable<? extends Comparison> comparing) {
        return new AbstractCallable<Comparison>("byKey") {
            public Comparison $call(java.lang.Object x, java.lang.Object y) {
                return comparing.$call(((Entry)x).getKey(), ((Entry)y).getKey());
            }
            
        };
    }
}
