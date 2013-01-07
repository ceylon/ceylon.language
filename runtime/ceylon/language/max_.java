package ceylon.language;

import com.redhat.ceylon.compiler.java.metadata.Ceylon;
import com.redhat.ceylon.compiler.java.metadata.Method;
import com.redhat.ceylon.compiler.java.metadata.Name;
import com.redhat.ceylon.compiler.java.metadata.TypeInfo;
import com.redhat.ceylon.compiler.java.metadata.TypeParameter;
import com.redhat.ceylon.compiler.java.metadata.TypeParameters;

@Ceylon(major = 3)
@Method
public final class max_ {
    
    private max_() {
    }
    
    @TypeParameters({@TypeParameter(value="Value", 
            satisfies="ceylon.language::Comparable<Value>"),
                     @TypeParameter(value="Absent", 
            satisfies="ceylon.language::Null")})
    @TypeInfo(value="Absent|Value", erased=true)
    public static <Value, Absent> 
    Value max(@Name("values")
    @TypeInfo(value="ceylon.language::Iterable<Value>&ceylon.language::ContainerWithFirstElement<Value,Absent>", erased=true)
    final Iterable<? extends Value> values) {
        Value max = (Value) values.getFirst();
        if (max!=null) {
        	java.lang.Object $tmp;
        	for (Iterator<? extends Value> $val$iter$0 = (Iterator<? extends Value>)values.getRest().getIterator(); 
        			!(($tmp = $val$iter$0.next()) instanceof Finished);) {
        		final Value val = (Value) $tmp;
        		if (((Comparable<? super Value>)val).compare(max).largerThan()) {
        			max = val;
        		}
        	}
        }
        return max;
    }
}
