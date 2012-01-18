package ceylon.language;

import com.redhat.ceylon.compiler.java.metadata.Ceylon;
import com.redhat.ceylon.compiler.java.metadata.Ignore;
import com.redhat.ceylon.compiler.java.metadata.Name;
import com.redhat.ceylon.compiler.java.metadata.SatisfiedTypes;
import com.redhat.ceylon.compiler.java.metadata.TypeInfo;

@Ceylon
@SatisfiedTypes({
    "ceylon.language.Correspondence<ceylon.language.Integer,ceylon.language.Bottom>",
    "ceylon.language.Ordered<ceylon.language.Bottom>",
    "ceylon.language.Ranged<ceylon.language.Integer,ceylon.language.Empty>",
    "ceylon.language.Sized"
})
public interface Empty 
        extends Correspondence, Ordered, Sized, 
                Ranged<Integer,Empty> {
	
    @TypeInfo("ceylon.language.Integer")
    public long getSize(); 
    
    public boolean getEmpty();
    
    @TypeInfo("ceylon.language.Nothing")
    public Iterator getIterator();
    
    @TypeInfo("ceylon.language.Nothing")
    public java.lang.Object item(@Name("key") java.lang.Object key);
    
    @TypeInfo("ceylon.language.Nothing")
    public java.lang.Object getFirst();
    
    @Ignore
    public static final class Empty$impl {
        public static long getSize(Empty $this){
            return 0;
        }
        public static boolean getEmpty(Empty $this){
            return true;
        }
        public static Iterator<java.lang.Object> getIterator(Empty $this){
            return new Iterator<java.lang.Object>() {
                @Override
                public java.lang.Object next() {
                    return $finished.getFinished();
                }
            };
        }
        public static java.lang.Object item(Empty $this, Integer key){
            return null;
        }
        public static java.lang.Object getFirst(Empty $this){
            return null;
        }
        public static java.lang.String toString(Empty $this) {
            return "{}";
        }
    }
}
