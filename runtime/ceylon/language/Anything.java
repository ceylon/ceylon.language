package ceylon.language;

import com.redhat.ceylon.compiler.java.metadata.CaseTypes;
import com.redhat.ceylon.compiler.java.metadata.Ceylon;
import com.redhat.ceylon.compiler.java.metadata.Class;

@Ceylon(major = 3)
@Class(extendsType="")
@CaseTypes({"ceylon.language::Null", "ceylon.language::Value"})
public abstract class Anything {
}
