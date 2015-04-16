package ceylon.language;

import com.redhat.ceylon.compiler.java.Util;
import com.redhat.ceylon.compiler.java.metadata.Ceylon;
import com.redhat.ceylon.compiler.java.metadata.Class;
import com.redhat.ceylon.compiler.java.metadata.Ignore;
import com.redhat.ceylon.compiler.java.metadata.Name;
import com.redhat.ceylon.compiler.java.metadata.SatisfiedTypes;
import com.redhat.ceylon.compiler.java.metadata.Transient;
import com.redhat.ceylon.compiler.java.metadata.ValueType;
import com.redhat.ceylon.compiler.java.runtime.model.ReifiedType;
import com.redhat.ceylon.compiler.java.runtime.model.TypeDescriptor;

@Ceylon(major = 8)
@Class(extendsType="ceylon.language::Object", basic = false, identifiable = false)
@SatisfiedTypes({
    "ceylon.language::Number<ceylon.language::Float>",
    "ceylon.language::Exponentiable<ceylon.language::Float,ceylon.language::Float>"
})
@ValueType
public final class Float
    implements Number<Float>, Exponentiable<Float,Float>, ReifiedType {
    
    @Ignore
    public final static TypeDescriptor $TypeDescriptor$ = TypeDescriptor.klass(Float.class);

    @Ignore
    final double value;
    
    public Float(@Name("float") double f) {
        value = f;
    }
    
    @Ignore
    @Override
    public Number$impl<Float> $ceylon$language$Number$impl(){
        // drags Numeric<Float> Comparable<Float>
        throw Util.makeUnimplementedMixinAccessException();
    }

    @Ignore
    @Override
    public Invertible$impl<Float> $ceylon$language$Invertible$impl(){
        throw Util.makeUnimplementedMixinAccessException();
    }

    @Ignore
    @Override
    public Comparable$impl<Float> $ceylon$language$Comparable$impl(){
        throw Util.makeUnimplementedMixinAccessException();
    }
    
    @Ignore
    public static Float instance(double d) {
        return new Float(d);
    }
    
    @Ignore
    public double doubleValue() {
        return value;
    }
    
    @Override
    public Float plus(@Name("other") Float other) {
        return instance(value + other.value);
    }
    
    @Ignore
    public static double plus(double value, double otherValue) {
        return value + otherValue;
    }
    
    @Override
    public Float minus(@Name("other") Float other) {
        return instance(value - other.value);
    }
    
    @Ignore
    public static double minus(double value, double otherValue) {
        return value - otherValue;
    }
    
    @Override
    public Float times(@Name("other") Float other) {
        return instance(value * other.value);
    }
    
    @Ignore
    public static double times(double value, double otherValue) {
        return value * otherValue;
    }
    
    @Override
    public Float divided(@Name("other") Float other) {
        return instance(value / other.value);
    }
    
    @Ignore
    public static double divided(double value, double otherValue) {
        return value / otherValue;
    }
    
    @Override
    public Float power(@Name("other") Float other) {
        return instance(power(value, other.value));
    }
    
    @Ignore
    public static double power(double value, double otherValue) {
        if (otherValue==0.0) {
            return 1.0;
        }
        else if (otherValue==1.0) {
            return value;
        }
        else if (otherValue==2.0) {
            return value*value;
        }
        else if (otherValue==3.0) {
            return value*value*value;
        }
        //TODO: other positive integer powers for which
        //      multiplying is faster than pow()
        else if (otherValue==0.5) {
            return Math.sqrt(value);
        }
        else if (otherValue==0.25) {
            return Math.sqrt(Math.sqrt(value));
        }
        else if (otherValue==-1.0) {
            return 1.0/value;
        }
        else if (otherValue==-2.0) {
            return 1.0/value/value;
        }
        else if (otherValue==-3.0) {
            return 1.0/value/value/value;
        }
        else if (otherValue==-0.5) {
            return 1.0/Math.sqrt(value);
        }
        else if (otherValue==-0.25) {
            return 1.0/Math.sqrt(Math.sqrt(value));
        }
        else {
            //NOTE: this function is _really_ slow!
            return Math.pow(value, otherValue);
        }
    }
    
    @Ignore
    public Float plus(Integer other) {
        return instance(value + other.value);
    }
    
    @Ignore
    public static double plus(double value, long otherValue) {
        return value + otherValue;
    }
    
    @Ignore
    public Float minus(Integer other) {
        return instance(value - other.value);
    }
    
    @Ignore
    public static double minus(double value, long otherValue) {
        return value - otherValue;
    }
    
    @Ignore
    public Float times(Integer other) {
        return instance(value * other.value);
    }
    
    @Ignore
    public static double times(double value, long otherValue) {
        return value * otherValue;
    }
    
    @Ignore
    public Float divided(Integer other) {
        return instance(value / other.value);
    }
    
    @Ignore
    public static double divided(double value, long otherValue) {
        return value / otherValue;
    }
    
    @Ignore
    public Float power(Integer other) {
        return instance(powerOfInteger(value, other.value));
    }
    
    @Ignore
    public static double power(double value, long otherValue) {
        return powerOfInteger(value, otherValue);
    }
    
    @Override
    public Float getMagnitude() {
        return instance(Math.abs(value));
    }
    
    @Ignore
    public static double getMagnitude(double value) {
        return Math.abs(value);
    }
    
    @Override
    public Float getFractionalPart() {
        return instance(value > 0.0D ? value - (long)value : (long)value - value);
    }
    
    @Ignore
    public static double getFractionalPart(double value) {
        return value > 0.0D ? value - (long)value : (long)value - value;
    }
    
    @Override
    public Float getWholePart() {		
        return instance(getInteger(value));
    }
    
    @Ignore
    public static double getWholePart(double value) {
        return getInteger(value);
    }
    
    @Override
    public boolean getPositive() {
        return value > 0;
    }
    
    @Ignore
    public static boolean getPositive(double value) {
        return value > 0;
    }
    
    @Override
    public boolean getNegative() {
        return value < 0;
    }
    
    @Ignore
    public static boolean getNegative(double value) {
        return value < 0;
    }
    
    public boolean getStrictlyPositive() {
        return (Double.doubleToRawLongBits(value) >> 63)==0
                && !Double.isNaN(value);
    }
    
    @Ignore
    public static boolean getStrictlyPositive(double value) {
        return (Double.doubleToRawLongBits(value) >> 63)==0
                && !Double.isNaN(value);
    }
    
    public boolean getStrictlyNegative() {
        return (Double.doubleToRawLongBits(value) >> 63)!=0
        		&& !Double.isNaN(value);
    }
    
    @Ignore
    public static boolean getStrictlyNegative(double value) {
        return (Double.doubleToRawLongBits(value) >> 63)!=0
                && !Double.isNaN(value);
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
    public static long getSign(double value) {
        if (value > 0)
            return 1;
        if (value < 0)
            return -1;
        return 0;
    }   
    
    @Override
    public Float getNegated() {
        return instance(-value);
    }
    
    @Ignore
    public static double getNegated(double value) {
        return -value;
    }
    
    @Override
    public Comparison compare(@Name("other") Float other) {
        double x = value;
        double y = other.value;
        return (x < y) ? smaller_.get_() :
            ((x == y) ? equal_.get_() : larger_.get_());
    }
    
    @Ignore
    public static Comparison compare(double value, double otherValue) {
        double x = value;
        double y = otherValue;
        return (x < y) ? smaller_.get_() :
            ((x == y) ? equal_.get_() : larger_.get_());
    }
    
    @Override
    public java.lang.String toString() {
        return java.lang.Double.toString(value);
    }
    
    @Ignore
    public static java.lang.String toString(double value) {
        return java.lang.Double.toString(value);
    }
    
    // Conversions between numeric types
    
    public long getInteger() {
        return getInteger(value);
    }
    
    @Ignore
    public static long getInteger(double value) {
        if (value >= Long.MIN_VALUE
                && value <= Long.MAX_VALUE) {
            return (long)value;
        }
        else {
        	throw new OverflowException();
        }
    }
    
    @Transient
    public boolean getUndefined() {
        return Double.isNaN(this.value);
    }
    
    @Ignore
    public static boolean getUndefined(double value) {
        return Double.isNaN(value);
    }
    
    @Transient
    public boolean getFinite() {
        return !Double.isInfinite(this.value) && !getUndefined();
    }
    
    @Ignore
    public static boolean getFinite(double value) {
        return !Double.isInfinite(value) && !getUndefined(value);
    }
    
    @Transient
    public boolean getInfinite() {
        return Double.isInfinite(value);
    }
    
    @Ignore
    public static boolean getInfinite(double value) {
        return Double.isInfinite(value);
    }
    
    @Override
    public boolean equals(@Name("that") java.lang.Object that) {
        return equals(value, that);
    }
    
    @Ignore
    public static boolean equals(double value, java.lang.Object that) {
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
        return hashCode(value);
    }
    
    @Ignore
    public static int hashCode(double value) {
    	long wholePart = (long) value;
        if (value == wholePart) {// make integers and floats have consistent hashes
            return Integer.hashCode(wholePart);
        } else {
            final long bits = Double.doubleToLongBits(value);
            return (int)(bits ^ (bits >>> 32));
        }
    }

    @Override
    @Ignore
    public TypeDescriptor $getType$() {
        return $TypeDescriptor$;
    }

    public static boolean largerThan(double value, Float other) {
    	return value>other.value;
    }

    public static boolean largerThan(double value, double other) {
        return value>other;
    }
    
    @Override
    public boolean largerThan(@Name("other")Float other) {
    	return value>other.value;
    }

    public static boolean notSmallerThan(double value, Float other) {
    	return value>=other.value;
    }

    public static boolean notSmallerThan(double value, double other) {
        return value>=other;
    }

    @Override
    public boolean notSmallerThan(@Name("other") Float other) {
    	return value>=other.value;
    }

    public static boolean smallerThan(double value, Float other) {
    	return value<other.value;
    }

    public static boolean smallerThan(double value, double other) {
        return value<other;
    }

    @Override
    public boolean smallerThan(@Name("other") Float other) {
    	return value<other.value;
    }

    public static boolean notLargerThan(double value, Float other) {
    	return value<=other.value;
    }

    public static boolean notLargerThan(double value, double other) {
        return value<=other;
    }

    @Override
    public boolean notLargerThan(@Name("other") Float other) {
    	return value<=other.value;
    }
    
    @Override
    public Float timesInteger(@Name("integer") long integer) {
    	return instance(value*integer);
    }
    
    public static double timesInteger(double value, long integer) {
    	return value*integer;
    }
    
    @Override
    public Float plusInteger(@Name("integer") long integer) {
    	return instance(value+integer);
    }
    
    public static double plusInteger(double value, long integer) {
    	return value+integer;
    }
    
    @Override
    public Float powerOfInteger(@Name("integer") long integer) {
        return instance(powerOfInteger(value,integer));
    }
    
    public static double powerOfInteger(double value, long integer) {
        if (integer == 0) {
            return 1.0;
        }
        else if (integer == 1) {
            return value;
        }
        else if (integer == 2) {
            return value*value;
        }
        else if (integer == 3) {
            return value*value*value;
        }
        //TODO: other positive integer powers for which
        //      multiplication is more efficient than pow()
        else if (integer == -1) {
            return 1/value;
        }
        else if (integer == -2) {
            return 1/value/value;
        }
        else if (integer == -3) {
            return 1/value/value/value;
        }
        else {
            //NOTE: this function is _really_ slow!
            return Math.pow(value,integer);
        }
    }
    
}

