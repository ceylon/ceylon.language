package com.redhat.ceylon.compiler.java.language;

import javax.annotation.Generated;

import ceylon.language.Array;

import com.redhat.ceylon.compiler.java.Util;
import com.redhat.ceylon.compiler.java.metadata.Ceylon;
import com.redhat.ceylon.compiler.java.metadata.Class;
import com.redhat.ceylon.compiler.java.metadata.Defaulted;
import com.redhat.ceylon.compiler.java.metadata.Ignore;
import com.redhat.ceylon.compiler.java.metadata.Name;
import com.redhat.ceylon.compiler.java.metadata.TypeInfo;
import com.redhat.ceylon.compiler.java.metadata.ValueType;
import com.redhat.ceylon.compiler.java.runtime.model.ReifiedType;
import com.redhat.ceylon.compiler.java.runtime.model.TypeDescriptor;

import ceylon.language.Callable;
import ceylon.language.Category$impl;
import ceylon.language.Comparison;
import ceylon.language.Entry;
import ceylon.language.Finished;
import ceylon.language.Iterable;
import ceylon.language.Iterable$impl;
import ceylon.language.Iterator;
import ceylon.language.List;
import ceylon.language.Null;
import ceylon.language.Sequential;
import ceylon.language.empty_;
import ceylon.language.finished_;
import ceylon.language.impl.BaseIterable;

/*
 * THIS IS A GENERATED FILE - DO NOT EDIT 
 */
/**
 * A type representing Java primitive arrays of type 
 * <code>boolean[]</code>.
 *
 * @author Stéphane Épardaud <stef@epardaud.fr>
 */
/*
 * THIS IS A GENERATED FILE - DO NOT EDIT 
 */
// This type is never instantiated, it is completely erased to 
// <code>boolean[]</code>.
// 
// The {@link #get(int)}, {@link #set(int,boolean)}, 
// {@link #length size} methods and the constructor are also 
// completely erased to Java array operators, or 
// {@link Util#fillArray(boolean[],boolean)} 
// in the case that an initial element is specified.
// 
// Only the value type static methods are really invoked.
@Ceylon(major = 8)
@Class
@ValueType
@Generated(value="ant")
public final class BooleanArray implements ReifiedType {
    
    @Ignore
    public final static TypeDescriptor $TypeDescriptor$ = 
    TypeDescriptor.klass(BooleanArray.class);
    
    public BooleanArray(
            /**
             * The size of the array.
             */
            @Name("size") int size, 
            /**
             * The initial value of the array elements.
             */
            @TypeInfo("ceylon.language::Boolean") 
            @Defaulted @Name("element") 
            boolean element){
        throw Util.makeJavaArrayWrapperException();
    }

    @Ignore
    public BooleanArray(
            /**
             * The size of the array.
             */
            @Name("size") int size){
        throw Util.makeJavaArrayWrapperException();
    }

    @Ignore
    // For consistency with the rules for ValueTypes
    public static BooleanArray instance(boolean[] value){
        throw Util.makeJavaArrayWrapperException();
    }

    /**
     * Get the element with the given {@link index}.
     */
    public boolean get(@Name("index") int index) {
        throw Util.makeJavaArrayWrapperException();
    }

    @Ignore
    public static boolean get(boolean[] value, int index) {
        throw Util.makeJavaArrayWrapperException();
    }

    /**
     * Set the element with the given {@link index} to the
     * given {@link element} value.
     */
    public void set(@Name("index") int index, 
            @Name("element") boolean element) {
        throw Util.makeJavaArrayWrapperException();
    }

    @Ignore
    public static void set(boolean[] value, int index, 
            boolean element) {
        throw Util.makeJavaArrayWrapperException();
    }
    
    /**
     * The size of this Java primitive array.
     */
    @Name("size")
    public final int length = 0;

    /**
     * A view of this array as a Ceylon 
     * <code>Array&lt;java.lang::Boolean&gt;</code>
     * where <code>java.lang::Boolean</code> is the Java 
     * wrapper type corresponding to the primitive type 
     * <code>boolean</code> of elements of this 
     * Java array.
     */
    @TypeInfo("ceylon.language::Array<java.lang::Boolean>")
    public ceylon.language.Array<java.lang.Boolean> getArray(){
        throw Util.makeJavaArrayWrapperException();
    }
    
    @Ignore
    public static ceylon.language.Array<java.lang.Boolean> 
    getArray(boolean[] array){
        return Array.instance(array);
    }

    /**
     * A view of this array as a Ceylon 
     * <code>Array&lt;ceylon.language::Boolean&gt;</code>
     * where <code>ceylon.language::Boolean</code>  
     * is the Ceylon type corresponding to the 
     * primitive type <code>boolean</code> 
     * of elements of this Java array.
     */
    @TypeInfo("ceylon.language::Array<ceylon.language::Boolean>")
    public ceylon.language.Array<ceylon.language.Boolean> 
    getBooleanArray(){
        throw Util.makeJavaArrayWrapperException();
    }
    
    @Ignore
    public static ceylon.language.Array<ceylon.language.Boolean> 
    getBooleanArray(boolean[] array){
        return Array.instanceForBooleans(array);
    }
    
    /**
     * Efficiently copy a measure of this Java primitive 
     * array to the given Java primitive array.
     */
    public void copyTo(@Name("destination") boolean[] destination, 
                       @Name("sourcePosition") @Defaulted int sourcePosition, 
                       @Name("destinationPosition") @Defaulted int destinationPosition, 
                       @Name("length") @Defaulted int length){
        throw Util.makeJavaArrayWrapperException();
    }
    
    @Ignore
    public static void copyTo(boolean[] array, 
            boolean[] destination){
        System.arraycopy(array, 0, destination, 0, array.length);
    }

    @Ignore
    public static void copyTo(boolean[] array, 
            boolean[] destination, 
            int sourcePosition){
        System.arraycopy(array, sourcePosition, destination, 
                0, array.length-sourcePosition);
    }

    @Ignore
    public static void copyTo(boolean[] array, 
            boolean[] destination, 
            int sourcePosition, int destinationPosition){
        System.arraycopy(array, sourcePosition, destination, 
                destinationPosition, array.length-sourcePosition);
    }

    @Ignore
    public static void copyTo(boolean[] array, 
            boolean[] destination, 
            int sourcePosition, int destinationPosition, 
            int length){
        System.arraycopy(array, sourcePosition, destination, 
                destinationPosition, length);
    }

    @Ignore
    public int copyTo$sourcePosition(boolean[] destination){
        throw Util.makeJavaArrayWrapperException();
    }

    @Ignore
    public int copyTo$destinationPosition(boolean[] destination, 
            int sourcePosition){
        throw Util.makeJavaArrayWrapperException();
    }

    @Ignore
    public int copyTo$length(boolean[] destination, 
            int sourcePosition, int destinationPosition){
        throw Util.makeJavaArrayWrapperException();
    }

    @Ignore
    public void copyTo(boolean[] destination){
        throw Util.makeJavaArrayWrapperException();
    }

    @Ignore
    public void copyTo(boolean[] destination, 
                       int sourcePosition){
        throw Util.makeJavaArrayWrapperException();
    }

    @Ignore
    public void copyTo(boolean[] destination, 
                       int sourcePosition, 
                       int destinationPosition){
        throw Util.makeJavaArrayWrapperException();
    }
    
    @Ignore
    @Override
    public TypeDescriptor $getType$() {
        throw Util.makeJavaArrayWrapperException();
    }

    @Override
    public boolean equals(@Name("that") java.lang.Object that) {
        throw Util.makeJavaArrayWrapperException();
    }

    @Ignore
    public static boolean equals(boolean[] value, 
            java.lang.Object that) {
        return value.equals(that);
    }

    @Override
    public int hashCode() {
        throw Util.makeJavaArrayWrapperException();
    }

    @Ignore
    public static int hashCode(boolean[] value) {
        return value.hashCode();
    }

    @Override
    public java.lang.String toString() {
        throw Util.makeJavaArrayWrapperException();
    }

    @Ignore
    public static java.lang.String toString(boolean[] value) {
        return value.toString();
    }
    
    /**
     * A clone of this primitive Java array.
     */
    public boolean[] $clone() {
        throw Util.makeJavaArrayWrapperException();
    }
    
    @Ignore
    public static boolean[] $clone(boolean[] value) {
        return value.clone();
    }
    
    /**
     * A Ceylon <code>Iterable<code> containing the
     * elements of this primitive Java array.
     */
    public BooleanArrayIterable getIterable() {
        throw Util.makeJavaArrayWrapperException();
    }
    
    @Ignore
    public static BooleanArrayIterable getIterable(boolean[] value) {
        return new BooleanArrayIterable(value);
    }
    
    /* Implement Iterable */

    public static class BooleanArrayIterable 
    extends BaseIterable<ceylon.language.Boolean, ceylon.language.Null> {
        
        /** The array over which we iterate */
        private final boolean[] array;
        /** The index where iteration starts */
        private final int start;
        /** The step size of iteration */
        private final int step;
        /** The index one beyond where iteration ends */
        private final int end;
        
        @Ignore
        public BooleanArrayIterable(boolean[] array) {
            this(array, 0, array.length, 1);
        }
        
        @Ignore
        private BooleanArrayIterable(boolean[] array, 
                int start, int end, int step) {
        	super(ceylon.language.Boolean.$TypeDescriptor$, Null.$TypeDescriptor$);
            if (start < 0) {
                throw new ceylon.language.AssertionError("start must be positive");
            }
            if (end < 0) {
                throw new ceylon.language.AssertionError("end must be positive");
            }
            if (step <= 0) {
                throw new ceylon.language.AssertionError("step size must be greater than zero");
            }
            
            this.array = array;
            this.start = start;
            this.end = end;
            this.step = step;
        }
        
        @Override
        public boolean containsAny(Iterable<? extends Object, ? extends Object> arg0) {
            Iterator<? extends Object> iter = arg0.iterator();
            Object item;
            while (!((item = iter.next()) instanceof Finished)) {
                if (item instanceof ceylon.language.Boolean) {
                    for (int ii = this.start; ii < this.end; ii+=this.step) {
                        if (array[ii] == ((ceylon.language.Boolean)item).booleanValue()) {
                            return true;
                        }
                    }
                }
            }
            return false;
        }
        
        @Override
        public boolean containsEvery(
                Iterable<? extends Object, ? extends Object> arg0) {
            Iterator<? extends Object> iter = arg0.iterator();
            Object item;
            OUTER: while (!((item = iter.next()) instanceof Finished)) {
                if (item instanceof ceylon.language.Boolean) {
                    for (int ii = this.start; ii < this.end; ii+=this.step) {
                        if (array[ii] == ((ceylon.language.Boolean)item).booleanValue()) {
                            continue OUTER;
                        }
                    }
                } 
                return false;
            }
            return true;
        }
        
        @Override
        public boolean any(Callable<? extends ceylon.language.Boolean> arg0) {
            for (int ii=this.start; ii < this.end; ii+=this.step) {
                if (arg0.$call$(ceylon.language.Boolean.instance(array[ii])).booleanValue()) {
                    return true;
                }
            }
            return false;
        }
        
        @Override
        public boolean contains(Object item) {
            for (int ii = this.start; ii < this.end; ii+=this.step) {
                if (item instanceof ceylon.language.Boolean 
                        && array[ii] == ((ceylon.language.Boolean)item).booleanValue()) {
                    return true;
                }
            }
            return false;
        }
        
        @Override
        public <Default> Iterable<? extends Object, ? extends Null> defaultNullElements(
                @Ignore
                TypeDescriptor $reified$Default, 
                Default defaultValue) {
            return this;
        }
        
        @Override
        public Iterable<? extends ceylon.language.Boolean, ? extends Object> getCoalesced() {
            return this;
        }
        
        @Override
        public boolean getEmpty() {
            return this.end <= this.start;
        }
        
        @Override
        public long getSize() {
            return java.lang.Math.max(0, (this.end-this.start+this.step-1)/this.step);
        }
        
        @Override
        public ceylon.language.Boolean getFirst() {
            return this.getEmpty() ? null : ceylon.language.Boolean.instance(this.array[this.start]);
        }
        
        
        @Override
        public ceylon.language.Boolean getLast() {
            return this.getEmpty() ? null : ceylon.language.Boolean.instance(this.array[this.end-1]);
        }
        
        @Override
        public BooleanArrayIterable getRest() {
            return new BooleanArrayIterable(this.array, this.start+1, this.end, this.step);
        }
        
        @Override
        public Sequential<? extends ceylon.language.Boolean> sequence() {
            // Note: Sequential is immutable, and we don't know where the array
            // came from, so however we create the sequence we must take a copy
        	//TODO: use a more efficient imple, like in List.sequence()
            return super.sequence();
        }
        
        @Override
        public Iterator<? extends ceylon.language.Boolean> iterator() {
            if (this.getEmpty()) {
                return (Iterator)ceylon.language.emptyIterator_.get_();
            }
            return new Iterator<ceylon.language.Boolean>() {
                
                private int index = BooleanArrayIterable.this.start;
                
                @Override
                public Object next() {
                    if (index < BooleanArrayIterable.this.end) {
                        ceylon.language.Boolean result = ceylon.language.Boolean.instance(BooleanArrayIterable.this.array[index]);
                        index += BooleanArrayIterable.this.step;
                        return result;
                    } else {
                        return finished_.get_();
                    }
                }
            };
        }
        
        @Override
        public boolean longerThan(long length) {
            return this.getSize() > length;
        }
        
        @Override
        public boolean shorterThan(long length) {
            return this.getSize() < length;
        }
        
        @Override
        public BooleanArrayIterable by(long step) {
            return new BooleanArrayIterable(this.array, 
                    this.start, 
                    this.end, 
                    com.redhat.ceylon.compiler.java.Util.toInt(this.step*step));
        }
        
        @Override
        public BooleanArrayIterable skip(long skip) {
            if (skip <= 0) {
                return this;
            }
            return new BooleanArrayIterable(this.array, 
                    com.redhat.ceylon.compiler.java.Util.toInt(this.start+skip*this.step), 
                    this.end, 
                    this.step);
        }
        
        @Override
        public BooleanArrayIterable take(long take) {
            if (take >= this.getSize()) {
                return this;
            }
            return new BooleanArrayIterable(this.array, 
                    this.start, 
                    com.redhat.ceylon.compiler.java.Util.toInt(take*this.step+1), 
                    this.step);
        }
        
    }
    
}
