package ceylon.language;

import com.redhat.ceylon.compiler.java.Util;
import com.redhat.ceylon.compiler.java.metadata.Ceylon;
import com.redhat.ceylon.compiler.java.metadata.Class;
import com.redhat.ceylon.compiler.java.metadata.Ignore;
import com.redhat.ceylon.compiler.java.metadata.Name;
import com.redhat.ceylon.compiler.java.metadata.SatisfiedTypes;
import com.redhat.ceylon.compiler.java.metadata.TypeInfo;
import com.redhat.ceylon.compiler.java.metadata.ValueType;
import com.redhat.ceylon.compiler.java.runtime.model.ReifiedType;
import com.redhat.ceylon.compiler.java.runtime.model.TypeDescriptor;

@Ceylon(major = 8)
@Class(extendsType="ceylon.language::Object", basic = false, identifiable = false)
@SatisfiedTypes({"ceylon.language::Comparable<ceylon.language::Character>",
		        "ceylon.language::Enumerable<ceylon.language::Character>"})
@ValueType
public final class Character
        implements Comparable<Character>, Enumerable<Character>, ReifiedType {

    @Ignore
    public final static TypeDescriptor $TypeDescriptor$ = TypeDescriptor.klass(Character.class);

    @Ignore
    public final int codePoint;
    
    public Character(@Name("character") 
    @TypeInfo("ceylon.language::Character") 
    int character) {
        this.codePoint = character;
    }

    @Ignore
    @Override
    public Enumerable$impl<Character> $ceylon$language$Enumerable$impl(){
        throw Util.makeUnimplementedMixinAccessException();
    }

    @Ignore
    @Override
    public Comparable$impl<Character> $ceylon$language$Comparable$impl(){
        throw Util.makeUnimplementedMixinAccessException();
    }

    @Ignore
    public static Character instance(int c) {
        return new Character(c);
    }

    @Ignore
    public int intValue() {
        return codePoint;
    }

    public java.lang.String toString() {
        return java.lang.String.valueOf(java.lang.Character.toChars(codePoint));
    }

    @Ignore
    public static java.lang.String toString(int codePoint) {
        return java.lang.String.valueOf(java.lang.Character.toChars(codePoint));
    }

    public boolean getLowercase() {
        return java.lang.Character.isLowerCase(codePoint);
    }

    @Ignore
    public static boolean getLowercase(int codePoint) {
        return java.lang.Character.isLowerCase(codePoint);
    }

    public boolean getUppercase(){
        return java.lang.Character.isUpperCase(codePoint);
    }

    @Ignore
    public static boolean getUppercase(int codePoint) {
        return java.lang.Character.isUpperCase(codePoint);
    }

    public boolean getTitlecase(){
        return java.lang.Character.isTitleCase(codePoint);
    }

    @Ignore
    public static boolean getTitlecase(int codePoint) {
        return java.lang.Character.isTitleCase(codePoint);
    }

    public boolean getDigit(){
        return java.lang.Character.isDigit(codePoint);
    }

    @Ignore
    public static boolean getDigit(int codePoint) {
        return java.lang.Character.isDigit(codePoint);
    }

    public boolean getLetter(){
        return java.lang.Character.isLetter(codePoint);
    }

    @Ignore
    public static boolean getLetter(int codePoint) {
        return java.lang.Character.isLetter(codePoint);
    }
    
    public boolean getWhitespace(){
        return java.lang.Character.isWhitespace(codePoint);
    }

    @Ignore
    public static boolean getWhitespace(int codePoint) {
        return java.lang.Character.isWhitespace(codePoint);
    }

    public boolean getControl(){
        return java.lang.Character.isISOControl(codePoint);
    }

    @Ignore
    public static boolean getControl(int codePoint) {
        return java.lang.Character.isISOControl(codePoint);
    }

    public ceylon.language.Character getLowercased() {
        return new Character(java.lang.Character.toLowerCase(codePoint));
    }

    @Ignore
    public static int getLowercased(int codePoint) {
        return java.lang.Character.toLowerCase(codePoint);
    }

    public ceylon.language.Character getUppercased() {
        return new Character(java.lang.Character.toUpperCase(codePoint));
    }

    @Ignore
    public static int getUppercased(int codePoint) {
        return java.lang.Character.toUpperCase(codePoint);
    }

    public ceylon.language.Character getTitlecased() {
        return new Character(java.lang.Character.toTitleCase(codePoint));
    }

    @Ignore
    public static int getTitlecased(int codePoint) {
        return java.lang.Character.toTitleCase(codePoint);
    }

    @Override
    public int hashCode() {
        return codePoint;
    }

    @Ignore
    public static int hashCode(int codePoint) {
        return codePoint;
    }

    @Override
    public boolean equals(@Name("that") java.lang.Object obj) {
        if (this == obj)
            return true;
        if (obj instanceof Character) {
            Character other = (Character) obj;
            return codePoint == other.codePoint;
        }
        else {
            return false;
        }
    }

    @Ignore
    public static boolean equals(int codePoint, java.lang.Object obj) {
        if (obj instanceof Character) {
            Character other = (Character) obj;
            return codePoint == other.codePoint;
        }
        else {
            return false;
        }
    }

	@Override
	public Comparison compare(@Name("other") Character other) {
        int x = codePoint;
        int y = other.codePoint;
        return (x < y) ? smaller_.get_() :
            ((x == y) ? equal_.get_() : larger_.get_());
	}

    @Ignore
    public static Comparison compare(int codePoint, int otherCodePoint) {
    	int x = codePoint;
    	int y = otherCodePoint;
        return (x < y) ? smaller_.get_() :
            ((x == y) ? equal_.get_() : larger_.get_());
    }

    @Override
    public Character getPredecessor() {
    	return new Character(getPredecessor(codePoint));
    }

    @Ignore
    public static int getPredecessor(int codePoint) {
        return codepoint(codePoint-1);
    }

    @Override
    public Character getSuccessor() {
    	return new Character(getSuccessor(codePoint));
    }

    @Ignore
    public static int getSuccessor(int codePoint) {
        return codepoint(codePoint+1);
    }

    public long getInteger() {
        return codePoint;
    }

    @Ignore
    public static long getInteger(int codePoint) {
        return codePoint;
    }

    @Override
    public Character neighbour(@Name("offset") long offset) {
        return instance(Util.toInt(codePoint+offset));
    }

    @Ignore
    public static int neighbour(int codePoint, long offset) {
        return Util.toInt(codePoint+offset);
    }

    @Override
    public long offset(@Name("other") Character other) {
        return codePoint-other.codePoint;
    }

    @Ignore
    public static long offsetSign(int codePoint, int other) {
        return codePoint-other;
    }

    @Override
    public long offsetSign(@Name("other") Character other) {
        return codePoint-other.codePoint;
    }

    @Ignore
    public static long offset(int codePoint, int other) {
        return codePoint-other;
    }

    @Override
    @Ignore
    public TypeDescriptor $getType$() {
        return $TypeDescriptor$;
    }

    public static boolean largerThan(int codePoint, Character other) {
    	return codePoint>other.codePoint;
    }

    public static boolean largerThan(int codePoint, int other) {
        return codePoint>other;
    }
    
    @Override
    public boolean largerThan(@Name("other") Character other) {
    	return codePoint>other.codePoint;
    }

    public static boolean notSmallerThan(int codePoint, Character other) {
    	return codePoint>=other.codePoint;
    }

    public static boolean notSmallerThan(int codePoint, int other) {
        return codePoint>=other;
    }

    @Override
    public boolean notSmallerThan(@Name("other") Character other) {
    	return codePoint>=other.codePoint;
    }

    public static boolean smallerThan(int codePoint, Character other) {
    	return codePoint<other.codePoint;
    }

    public static boolean smallerThan(int codePoint, int other) {
        return codePoint<other;
    }

    @Override
    public boolean smallerThan(@Name("other") Character other) {
    	return codePoint<other.codePoint;
    }

    public static boolean notLargerThan(int codePoint, Character other) {
    	return codePoint<=other.codePoint;
    }

    public static boolean notLargerThan(int codePoint, int other) {
        return codePoint<=other;
    }

    @Override
    public boolean notLargerThan(@Name("other") Character other) {
    	return codePoint<=other.codePoint;
    }

    static int codepoint(long value) {
        if (value>java.lang.Integer.MAX_VALUE ||
            value<java.lang.Integer.MIN_VALUE) {
            throw new OverflowException(value + " is not a 32-bit integer");
        }
        return codepoint(Util.toInt(value));
    }

    private static int codepoint(int value) {
        if (!java.lang.Character.isDefined(value)) {
            throw new OverflowException(value + " is not a Unicode code point");
        }
        return value;
    }
    
}
