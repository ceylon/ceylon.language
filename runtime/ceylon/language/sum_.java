package ceylon.language;

import com.redhat.ceylon.compiler.java.metadata.Ceylon;
import com.redhat.ceylon.compiler.java.metadata.Method;
import com.redhat.ceylon.compiler.java.metadata.Name;
import com.redhat.ceylon.compiler.java.metadata.TypeInfo;
import com.redhat.ceylon.compiler.java.metadata.TypeParameter;
import com.redhat.ceylon.compiler.java.metadata.TypeParameters;

@Ceylon(major = 3)
@Method
public final class sum_ {
    
    private sum_() {
    }
    
    @TypeParameters(@TypeParameter(value="Element", satisfies="ceylon.language::Summable<Element>"))
    @TypeInfo("Element")
    public static <Element extends Summable<Element>>Element sum(@Name("values")
    @TypeInfo("ceylon.language::Sequence<Element>")
    final Sequence<? extends Element> values) {
        Element sum = values.getFirst();
        java.lang.Object $tmp;
        for (Iterator<? extends Element> $val$iter$0 = values.getRest().getIterator(); 
                !(($tmp = $val$iter$0.next()) instanceof Finished);) {
            sum = sum.plus((Element) $tmp);
        }
        return sum;
    }
}
