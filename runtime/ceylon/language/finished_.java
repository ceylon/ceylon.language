package ceylon.language;

import com.redhat.ceylon.compiler.java.metadata.Ceylon;
import com.redhat.ceylon.compiler.java.metadata.Object;

@Ceylon(major = 3) @Object
public class finished_ extends Finished {
    
    private final static finished_ finished = new finished_();
    
    public static finished_ getFinished$(){
        return finished;
    }

    @Override
    public java.lang.String toString() {
        return "finished";
    }
}
