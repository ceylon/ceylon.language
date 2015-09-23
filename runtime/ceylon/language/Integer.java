package ceylon.language;

import static ceylon.language.Character.codepoint;

import com.redhat.ceylon.compiler.java.Util;
import com.redhat.ceylon.compiler.java.metadata.Ceylon;
import com.redhat.ceylon.compiler.java.metadata.Class;
import com.redhat.ceylon.compiler.java.metadata.Defaulted;
import com.redhat.ceylon.compiler.java.metadata.Ignore;
import com.redhat.ceylon.compiler.java.metadata.Name;
import com.redhat.ceylon.compiler.java.metadata.SatisfiedTypes;
import com.redhat.ceylon.compiler.java.metadata.Transient;
import com.redhat.ceylon.compiler.java.metadata.TypeInfo;
import com.redhat.ceylon.compiler.java.metadata.ValueType;
import com.redhat.ceylon.compiler.java.runtime.model.ReifiedType;
import com.redhat.ceylon.compiler.java.runtime.model.TypeDescriptor;

@Ceylon(major = 8)
@SatisfiedTypes({
    "ceylon.language::Integral<ceylon.language::Integer>",
    "ceylon.language::Binary<ceylon.language::Integer>",
    "ceylon.language::Exponentiable<ceylon.language::Integer,ceylon.language::Integer>"
})
@Class(extendsType="ceylon.language::Object", basic = false, identifiable = false)
@ValueType
public final class Integer
    implements Integral<Integer>,
               Binary<Integer>,
               Exponentiable<Integer,Integer>, 
               ReifiedType {

    @Ignore
    public final static TypeDescriptor $TypeDescriptor$ = TypeDescriptor.klass(Integer.class);

    @Ignore
    final long value;

    public Integer(@Name("integer") long integer) {
        value = integer;
    }

    @Ignore
    @Override
    public Number$impl<Integer> $ceylon$language$Number$impl(){
        // drags Numeric<Integer> Comparable<Integer>
        throw Util.makeUnimplementedMixinAccessException();
    }

    @Ignore
    @Override
    public Invertible$impl<Integer> $ceylon$language$Invertible$impl(){
        throw Util.makeUnimplementedMixinAccessException();
    }

    @Ignore
    @Override
    public Comparable$impl<Integer> $ceylon$language$Comparable$impl(){
        throw Util.makeUnimplementedMixinAccessException();
    }
    
    @Ignore
    @Override
    public Integral$impl<Integer> $ceylon$language$Integral$impl(){
        // drags Numeric<Integer> Enumerable<Integer>
        throw Util.makeUnimplementedMixinAccessException();
    }

    @Ignore
    @Override
    public Enumerable$impl<Integer> $ceylon$language$Enumerable$impl(){
        // drags Ordinal<Integer>
        throw Util.makeUnimplementedMixinAccessException();
    }

    @Ignore
    @Override
    public Binary$impl<Integer> $ceylon$language$Binary$impl(){
        throw Util.makeUnimplementedMixinAccessException();
    }

    @Ignore
    public static Integer instance(long l) {
        return new Integer(l);
    }

    @Ignore
    public long longValue() {
        return value;
    }

    @Override
    public Integer plus(@Name("other") Integer other) {
        return instance(value + other.value);
    }

    @Ignore
    public static long plus(long value, long otherValue) {
        return value + otherValue;
    }

    @Override
    public Integer minus(@Name("other") Integer other) {
        return instance(value - other.value);
    }

    @Ignore
    public static long minus(long value, long otherValue) {
        return value - otherValue;
    }

    @Override
    public Integer times(@Name("other") Integer other) {
        return instance(value * other.value);
    }

    @Ignore
    public static long times(long value, long otherValue) {
        return value * otherValue;
    }

    @Override
    public Integer divided(@Name("other") Integer other) {
        return instance(value / other.value);
    }

    @Ignore
    public static long divided(long value, long otherValue) {
        return value / otherValue;
    }

    private static final long POWER_BY_SQUARING_BREAKEVEN = 6;

    private static long powerBySquaring(long base, long power) {
        long result = 1;
        long x = base;
        while (power != 0) {
            if ((power & 1L) == 1L) {
                result *= x;
                power -= 1;
            }
            x *= x;
            power /= 2;
        }
        return result;
    }

    private static long powerByMultiplying(long base, long power) {
        long result = 1;
        while (power > 0) {
            result *= base;
            power--;
        }
        return result;
    }

    @Override
    public Integer power(@Name("other") Integer other) {
        return instance(power(value, other.value));
    }

    @Ignore
    public static long power(long value, long otherValue) {
        long power = otherValue;
        if (value == -1) {
            return power % 2 == 0 ? 1L : -1L;
        }
        else if (value == 1) {
            return 1L;
        }
        else if (power < 0) {
            throw new AssertionError(value + "^" + power + 
                    " cannot be represented as an Integer");
        }
        else if (power == 0) {
            return 1L;
        }
        else if (power == 1) {
            return value;
        }
        else if (power >= POWER_BY_SQUARING_BREAKEVEN) {
            return powerBySquaring(value, power);
        }
        else {
            return powerByMultiplying(value, power);
        }
    }

    @Ignore
    public Float plus(Float other) {
        return Float.instance(value + other.value);
    }

    @Ignore
    public static double plus(long value, double otherValue) {
        return value + otherValue;
    }

    @Ignore
    public Float minus(Float other) {
        return Float.instance(value - other.value);
    }

    @Ignore
    public static double minus(long value, double otherValue) {
        return value - otherValue;
    }

    @Ignore
    public Float times(Float other) {
        return Float.instance(value * other.value);
    }

    @Ignore
    public static double times(long value, double otherValue) {
        return value * otherValue;
    }

    @Ignore
    public Float divided(Float other) {
        return Float.instance(value / other.value);
    }

    @Ignore
    public static double divided(long value, double otherValue) {
        return value / otherValue;
    }

    @Ignore
    public Float power(Float other) {
        return Float.instance(Math.pow(value, other.value)); // FIXME: ugly
    }

    @Ignore
    public static double power(long value, double otherValue) {
        return Math.pow(value, otherValue); // FIXME: ugly
    }

    @AliasesAnnotation$annotation$(aliases = "absolute")
    @Override
    public Integer getMagnitude() {
        return instance(Math.abs(value));
    }

    @Ignore
    public static long getMagnitude(long value) {
        return Math.abs(value);
    }

    @Override
    public Integer getFractionalPart() {
        return instance(0);
    }

    @Ignore
    public static long getFractionalPart(long value) {
        return 0;
    }

    @Override
    public Integer getWholePart() {
        return this;
    }

    @Ignore
    public static long getWholePart(long value) {
        return value;
    }

    @Override
    public boolean getPositive() {
        return value > 0;
    }

    @Ignore
    public static boolean getPositive(long value) {
        return value > 0;
    }

    @Override
    public boolean getNegative() {
        return value < 0;
    }

    @Ignore
    public static boolean getNegative(long value) {
        return value < 0;
    }

    @Override
    public long getSign() {
        if (value > 0)
            return 1;
        if (value < 0)
            return -1;
        return 0;
    }

    @Ignore
    public static long getSign(long value) {
        if (value > 0)
            return 1;
        if (value < 0)
            return -1;
        return 0;
    }

    @Override
    public Integer remainder(@Name("other") Integer other) {
        return instance(value % other.value);
    }

    @Ignore
    public static long remainder(long value, long otherValue) {
        return value % otherValue;
    }

    @Override
    public Integer modulo(@Name("modulus") Integer modulus) {
        return instance(modulo(value, modulus.value));
    }

    @Ignore
    public static long modulo(long value, long modulus) {
        if(modulus < 0)
            throw new AssertionError("modulus must be positive: "+modulus);
        long ret = value % modulus;
        if(ret < 0)
            return ret + modulus;
        return ret;
    }

    @Override
    public final boolean divides(@Name("other") Integer other) {
        return other.value % value == 0;
    }

    @Ignore
    public static boolean divides(long value, long otherValue) {
        return otherValue % value == 0;
    }

    @Override
    public Integer getNegated() {
        return instance(-value);
    }

    @Ignore
    public static long getNegated(long value) {
        return -value;
    }

    @Override
    public Comparison compare(@Name("other") Integer other) {
        long x = value;
        long y = other.value;
        return (x < y) ? smaller_.get_() :
            ((x == y) ? equal_.get_() : larger_.get_());
    }

    @Ignore
    public static Comparison compare(long value, long otherValue) {
        long x = value;
        long y = otherValue;
        return (x < y) ? smaller_.get_() :
            ((x == y) ? equal_.get_() : larger_.get_());
    }

    @Override
    public java.lang.String toString() {
        return java.lang.Long.toString(value);
    }

    @Ignore
    public static java.lang.String toString(long value) {
        return java.lang.Long.toString(value);
    }

    // Enumerable
    
    @Ignore
    public static long neighbour(long value, long offset) {
        long neighbour = value+offset;
        //Overflow iff both arguments have the opposite sign of the result
        if (((value^neighbour) & (offset^neighbour)) < 0) {
            throw new OverflowException(value + " has no neighbour with offset " + offset);
        }
        return neighbour;
    }

    @Override
    public Integer neighbour(@Name("offset") long offset) {
        return instance(neighbour(value,offset));
    }

    @Ignore
    public static long offset(long value, long other) {
        long offset = value-other;
        //Overflow iff the arguments have different signs and
        //the sign of the result is different than the sign of x
        if (((value^other) & (value^offset)) < 0) {
            throw new OverflowException(
                    "offset from " + value + " to " + other + " cannot be represented as a 64 bit integer.");
        }
        return offset;
    }

    @Override
    public long offset(@Name("other") Integer other) {
        return offset(value, other.value);
    }

    @Ignore
    public static long offsetSign(long value, long other) {
        if (value>other) {
            return 1;
        }
        if (value<other) {
            return -1;
        }
        return 0;
    }

    @Override
    public long offsetSign(@Name("other") Integer other) {
        return offsetSign(value, other.value);
    }

    // Conversions between numeric types
    
    public double getFloat() {
        return getFloat(value);
    }

    @Ignore
    public static double getFloat(long value) {
        if (value >= 9007199254740992L
                || value <= -9007199254740992L) {
            throw new OverflowException(value + " cannot be coerced into a 64 bit floating point value");
        }
        else {
        	return (double) value;
        }
    }

    public double getNearestFloat() {
        return (double) value;
    }

    @Ignore
    public static double getNearestFloat(long value) {
        return (double) value;
    }

    public byte getByte() {
        return getByte(value);
    }

    @Ignore
    public static byte getByte(long value) {
        return (byte) value;
    }

    @TypeInfo("ceylon.language::Character")
    public int getCharacter() {
        return getCharacter(value);
    }

    @Ignore
    @TypeInfo("ceylon.language::Character")
    public static int getCharacter(long value) {
        return codepoint(value);
    }

    @Transient
    public boolean getEven() {
        return (value&1)==0;
    }

    @Ignore
    public static boolean getEven(long value) {
        return (value&1)==0;
    }

    @Override
    public boolean getUnit() {
        return value==1;
    }

    @Ignore
    public static boolean getUnit(long value) {
        return value==1;
    }

    @Override
    public boolean getZero() {
        return value==0;
    }

    @Ignore
    public static boolean getZero(long value) {
        return value==0;
    }

    @Override
    public Integer getPredecessor() {
        return Integer.instance(value - 1);
    }

    @Ignore
    public static long getPredecessor(long value) {
        return value - 1;
    }

    @Override
    public Integer getSuccessor() {
        return Integer.instance(value + 1);
    }

    @Ignore
    public static long getSuccessor(long value) {
        return value + 1;
    }

    @Override
    public boolean equals(@Name("that") java.lang.Object that) {
        if (that instanceof Integer) {
            return value == ((Integer)that).value;
        }
        else if (that instanceof Float) {
            return value == ((Float)that).value;
        }
        else {
            return false;
        }
    }

    @Ignore
    public static boolean equals(long value, java.lang.Object that) {
        if (that instanceof Integer) {
            return value == ((Integer)that).value;
        }
        else if (that instanceof Float) {
            return value == ((Float)that).value;
        }
        else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return (int)(value ^ (value >>> 32));
    }

    @Ignore
    public static int hashCode(long value) {
        return (int)(value ^ (value >>> 32));
    }

    @Override
    public Integer getNot() {
        return instance(~value);
    }
    
    @Ignore
    public static long getNot(long value){
        return ~value;
    }

    @Override
    public Integer leftLogicalShift(@Name("shift") long shift) {
        return instance(value << shift);
    }
    
    @Ignore
    public static long leftLogicalShift(long value, long shift){
        return value << shift;
    }

    @Override
    public Integer rightLogicalShift(@Name("shift") long shift) {
        return instance(value >>> shift);
    }
    
    @Ignore
    public static long rightLogicalShift(long value, long shift){
        return value >>> shift;
    }

    @Override
    public Integer rightArithmeticShift(@Name("shift") long shift) {
        return instance(value >> shift);
    }
    
    @Ignore
    public static long rightArithmeticShift(long value, long shift){
        return value >> shift;
    }

    @Override
    public Integer and(@Name("other") Integer other) {
        return instance(value & other.value);
    }
    
    @Ignore
    public static long and(long value, long other){
        return value & other;
    }

    @Override
    public Integer or(@Name("other") Integer other) {
        return instance(value | other.value);
    }
    
    @Ignore
    public static long or(long value, long other){
        return value | other;
    }

    @Override
    public Integer xor(@Name("other") Integer other) {
        return instance(value ^ other.value);
    }
    
    @Ignore
    public static long xor(long value, long other){
        return value ^ other;
    }

    @Override
    public boolean get(@Name("index") long index) {
        return get(value, index);
    }

    @Ignore
    public static boolean get(long value, long index){
        if (index < 0 || index > 63) {
            return false;
        }
        long mask = 1l << index;
        return (value & mask) != 0;
    }
    
    @Override
    @Ignore
    public Integer set(long index) {
        return instance(set(value, index));
    }

    @Override
    public Integer set(@Name("index") long index, @Name("bit") @Defaulted boolean bit) {
        return instance(set(value, index, bit));
    }

    @Override
    @Ignore
    public boolean set$bit(long index) {
        return true;
    }

    @Ignore
    public static long set(long value, long index) {
        return set(value, index, true);
    }

    @Ignore
    public static long set(long value, long index, boolean bit) {
        if (index < 0 || index > 63) {
            return value;
        }
        long mask = 1l << index;
        return bit ? value | mask : value & ~mask;
    }

    @Override
    public Integer clear(@Name("index") long index) {
        return instance(clear(value, index));
    }

    @Ignore
    public static long clear(long value, long index) {
        if (index < 0 || index > 63) {
            return value;
        }
        long mask = 1l << index;
        return value & ~mask;
    }

    @Override
    public Integer flip(@Name("index") long index) {
        return instance(flip(value, index));
    }

    @Ignore
    public static long flip(long value, long index) {
        if (index < 0 || index > 63) {
            return value;
        }
        long mask = 1l << index;
        return value ^ mask;
    }

    @Ignore
    @Override
    public TypeDescriptor $getType$() {
        return $TypeDescriptor$;
    }
    
    public static boolean largerThan(long value, Integer other) {
    	return value>other.value;
    }

    public static boolean largerThan(long value, long other) {
        return value>other;
    }
    
    @Override
    public boolean largerThan(@Name("other") Integer other) {
    	return value>other.value;
    }

    public static boolean notSmallerThan(long value, Integer other) {
    	return value>=other.value;
    }

    public static boolean notSmallerThan(long value, long other) {
        return value>=other;
    }

    @Override
    public boolean notSmallerThan(@Name("other") Integer other) {
    	return value>=other.value;
    }

    public static boolean smallerThan(long value, Integer other) {
    	return value<other.value;
    }

    public static boolean smallerThan(long value, long other) {
        return value<other;
    }

    @Override
    public boolean smallerThan(@Name("other") Integer other) {
    	return value<other.value;
    }

    public static boolean notLargerThan(long value, Integer other) {
    	return value<=other.value;
    }

    public static boolean notLargerThan(long value, long other) {
        return value<=other;
    }

    @Override
    public boolean notLargerThan(@Name("other") Integer other) {
    	return value<=other.value;
    }

    @Override
    public Integer timesInteger(@Name("integer") long integer) {
    	return instance(value*integer);
    }
    
    public static long timesInteger(long value, long integer) {
    	return value*integer;
    }
    
    @Override
    public Integer plusInteger(@Name("integer") long integer) {
    	return instance(value+integer);
    }
    
    public static long plusInteger(long value, long integer) {
    	return value+integer;
    }
    
    @Override
    public Integer powerOfInteger(@Name("integer") long integer) {
        return instance(power(value,integer));
    }
    
    public static long powerOfInteger(long value, long integer) {
        return power(value,integer);
    }
    
}
