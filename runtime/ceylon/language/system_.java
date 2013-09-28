package ceylon.language;

import java.util.Locale;
import java.util.TimeZone;

import com.redhat.ceylon.compiler.java.metadata.Ceylon;
import com.redhat.ceylon.compiler.java.metadata.Object;

@Ceylon(major = 5) @Object
public final class system_ {
    
    private static final system_ value = new system_();
    
    public static system_ $get() {
        return value;
    }
    
    private system_() {}
	
    public long getMilliseconds() {
    	return System.currentTimeMillis();
    }
    
    public long getNanoseconds() {
        return System.nanoTime();
    }
     
    public int getTimezoneOffset() {
        return TimeZone.getDefault().getOffset(getMilliseconds());
    }
    
    public java.lang.String getLocale() {
        return Locale.getDefault().toLanguageTag();
    }
    
    @Override
    public java.lang.String toString() {
    	return "system";
    }
}
