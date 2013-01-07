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
public class byDecreasing_ {

    private byDecreasing_(){}

    @TypeParameters({@TypeParameter(value="Element"),
            @TypeParameter(value="Ordered", satisfies="ceylon.language::Comparable<Ordered>")})
    @TypeInfo("ceylon.language::Callable<ceylon.language::Null|ceylon.language::Comparison,ceylon.language::Tuple<Element,Element,ceylon.language::Tuple<Element,Element,ceylon.language::Empty>>>")
    public static <Element,Ordered extends Comparable<? super Ordered>> Callable<? extends Comparison> byDecreasing(
            @Name("comparable")
            @TypeInfo("ceylon.language::Callable<ceylon.language::Null|Ordered,ceylon.language::Tuple<Element,Element,ceylon.language::Empty>>")
            final Callable<? extends Ordered> comparable) {
        return new AbstractCallable<Comparison>("byDecreasing") {
            public Comparison $call(java.lang.Object x, java.lang.Object y) {
                Ordered cx = comparable.$call(x);
                Ordered cy = comparable.$call(y);
                if (cx!=null && cy!=null) {
                    return cy.compare(cx);
                }
                else {
                    return null;
                }
            }
            
        };
    }
}
