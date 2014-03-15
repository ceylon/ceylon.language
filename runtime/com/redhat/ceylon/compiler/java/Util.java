package com.redhat.ceylon.compiler.java;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import ceylon.language.ArraySequence;
import ceylon.language.AssertionException;
import ceylon.language.Callable;
import ceylon.language.Integer;
import ceylon.language.Iterable;
import ceylon.language.Iterator;
import ceylon.language.Ranged;
import ceylon.language.Sequential;
import ceylon.language.empty_;
import ceylon.language.finished_;

import com.redhat.ceylon.cmr.api.ArtifactResult;
import com.redhat.ceylon.compiler.java.metadata.Ceylon;
import com.redhat.ceylon.compiler.java.metadata.Class;
import com.redhat.ceylon.compiler.java.metadata.SatisfiedTypes;
import com.redhat.ceylon.compiler.java.runtime.metamodel.Metamodel;
import com.redhat.ceylon.compiler.java.runtime.model.TypeDescriptor;

/**
 * Helper class for generated Ceylon code that needs to call implementation logic.
 * 
 * @author Stéphane Épardaud <stef@epardaud.fr>
 */
public class Util {

    static {
        // Make sure the rethrow class is loaded if ever we need to rethrow
        // errors such as StackOverflowError, otherwise if we have to rethrow it
        // we will not be able to load that class since we've ran out of stack
        // see https://github.com/ceylon/ceylon.language/issues/311
        ceylon.language.impl.rethrow_.class.toString();
    }
    
    public static String declClassName(String name) {
        return name.replace("::", ".");
    }

    public static void loadModule(String name, String version, 
    		ArtifactResult result, ClassLoader classLoader){
        Metamodel.loadModule(name, version, result, classLoader);
    }
    
    public static boolean isReified(java.lang.Object o, TypeDescriptor type){
        return Metamodel.isReified(o, type);
    }

    /**
     * Returns true if the given object satisfies ceylon.language.Identifiable
     */
    public static boolean isIdentifiable(java.lang.Object o){
        return satisfiesInterface(o, "ceylon.language.Identifiable");
    }
    
    /**
     * Returns true if the given object extends ceylon.language.Basic
     */
    public static boolean isBasic(java.lang.Object o){
        return extendsClass(o, "ceylon.language.Basic");
    }
    
    /**
     * Returns true if the given object extends the given class
     */
    public static boolean extendsClass(java.lang.Object o, String className) {
        if(o == null)
            return false;
        if(className == null)
            throw new AssertionException("Type name cannot be null");
        return classExtendsClass(o.getClass(), className);
    }
    
    private static boolean classExtendsClass(java.lang.Class<?> klass, String className) {
        if(klass == null)
            return false;
        if (klass.getName().equals(className))
            return true;
        if ((className.equals("ceylon.language.Basic"))
                && klass!=java.lang.Object.class
                //&& klass!=java.lang.String.class
        		&& !klass.isAnnotationPresent(Class.class)
        		&& (!klass.isInterface() || !klass.isAnnotationPresent(Ceylon.class))) {
        	//TODO: this is broken for a Java class that
        	//      extends a Ceylon class
        	return true;
        }
        return classExtendsClass(getCeylonSuperClass(klass), className);
    }
    
    private static java.lang.Class<?> getCeylonSuperClass(java.lang.Class<?> klass) {
        Class classAnnotation = klass.getAnnotation(Class.class);
        // only consider Class.extendsType() if non-empty
        if (classAnnotation != null && !classAnnotation.extendsType().isEmpty()) {
            String superclassName = declClassName(classAnnotation.extendsType());
            int i = superclassName.indexOf('<');
            if (i>0) {
                superclassName = superclassName.substring(0, i);
            }
            if (superclassName.isEmpty()) {
                throw new RuntimeException("Malformed @Class.extendsType() annotation value: "+classAnnotation.extendsType());
            }
            try {
                return java.lang.Class.forName(superclassName, true, klass.getClassLoader()); 
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        // we consider that subclasses of Object that do not have any @Ceylon.extendsType() are in fact subclasses of Basic
        if(klass.getSuperclass() != java.lang.Object.class)
            return klass.getSuperclass();
        // Anything has no super class
        if(klass == ceylon.language.Anything.class)
            return null;
        // The default super class is Basic
        return ceylon.language.Basic.class;
    }

    /**
     * Returns true if the given object satisfies the given interface
     */
    public static boolean satisfiesInterface(java.lang.Object o, String className){
        if(o == null)
            return false;
        if(className == null)
            throw new AssertionException("Type name cannot be null");
        // we use a hash set to speed things up for interfaces, to avoid looking at them twice
        Set<java.lang.Class<?>> alreadyVisited = new HashSet<java.lang.Class<?>>();
        return classSatisfiesInterface(o.getClass(), className, alreadyVisited);
    }

    private static boolean classSatisfiesInterface(java.lang.Class<?> klass, String className, 
            Set<java.lang.Class<?>> alreadyVisited) {
        if(klass == null
                || klass == ceylon.language.Anything.class)
            return false;
        if ((className.equals("ceylon.language.Identifiable"))
                && klass!=java.lang.Object.class
                //&& klass!=java.lang.String.class
                && !klass.isAnnotationPresent(Ceylon.class)) {
            //TODO: this is broken for a Java class that
            //      extends a Ceylon class
            return true;
        }
        // try the interfaces
        if(lookForInterface(klass, className, alreadyVisited))
            return true;
        // try its superclass
        return classSatisfiesInterface(getCeylonSuperClass(klass), className, alreadyVisited);
    }

    private static boolean lookForInterface(java.lang.Class<?> klass, String className, 
            Set<java.lang.Class<?>> alreadyVisited){
        if (klass.getName().equals(className))
            return true;
        // did we already visit this type?
        if(!alreadyVisited.add(klass))
            return false;
        // first see if it satisfies it directly
        SatisfiedTypes satisfiesAnnotation = klass.getAnnotation(SatisfiedTypes.class);
        if (satisfiesAnnotation!=null){
            for (String satisfiedType : satisfiesAnnotation.value()){
                satisfiedType = declClassName(satisfiedType);
                int i = satisfiedType.indexOf('<');
                if (i>0) {
                    satisfiedType = satisfiedType.substring(0, i);
                }
                try {
                    if (lookForInterface(
                            java.lang.Class.forName(satisfiedType, true, klass.getClassLoader()), 
                            className, alreadyVisited)) {
                        return true;
                    }
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        }else{
            // otherwise look at this class's interfaces
            for (java.lang.Class<?> intrface : klass.getInterfaces()){
                if (lookForInterface(intrface, className, alreadyVisited))
                    return true;
            }
        }
        // no luck
        return false;
    }
    
    //
    // Java variadic conversions
    
    @SuppressWarnings("unchecked")
    public static <T> List<T> collectIterable(Iterable<? extends T, ?> sequence) {
        List<T> list = new LinkedList<T>();
        if (sequence != null) {
            Iterator<? extends T> iterator = sequence.iterator();
            Object o; 
            while((o = iterator.next()) != finished_.get_()){
                list.add((T)o);
            }
        }
        return list;
    }

    @SuppressWarnings("unchecked")
    public static boolean[] 
    toBooleanArray(ceylon.language.Iterable<? extends ceylon.language.Boolean, ?> sequence,
            boolean... initialElements){
        if(sequence instanceof ceylon.language.List)
            return toBooleanArray((ceylon.language.List<? extends ceylon.language.Boolean>)sequence,
                    initialElements);
        List<ceylon.language.Boolean> list = collectIterable(sequence);
        int i=initialElements.length;
        boolean[] ret = new boolean[list.size() + i];
        System.arraycopy(initialElements, 0, ret, 0, i);
        for(ceylon.language.Boolean e : list){
            ret[i++] = e.booleanValue();
        }
        return ret;
    }

    @SuppressWarnings("unchecked")
    public static boolean[]
    toBooleanArray(ceylon.language.List<? extends ceylon.language.Boolean> list,
            boolean... initialElements){
        if(list == null)
            return initialElements;
        int i=initialElements.length;
        boolean[] ret = new boolean[(int) list.getSize() + i];
        System.arraycopy(initialElements, 0, ret, 0, i);
        Iterator<? extends ceylon.language.Boolean> iterator = list.iterator();
        Object o;
        while((o = iterator.next()) != finished_.get_()){
            ret[i++] = ((ceylon.language.Boolean)o).booleanValue();
        }
        return ret;
    }
    
    @SuppressWarnings("unchecked")
    public static byte[] 
    toByteArray(ceylon.language.Iterable<? extends ceylon.language.Integer, ?> sequence,
            long... initialElements){
        if(sequence instanceof ceylon.language.List)
            return toByteArray((ceylon.language.List<? extends ceylon.language.Integer>)sequence,
                    initialElements);
        List<ceylon.language.Integer> list = collectIterable(sequence);
        byte[] ret = new byte[list.size() + initialElements.length];
        int i=0;
        for(;i<initialElements.length;i++){
            ret[i] = (byte) initialElements[i];
        }
        for(ceylon.language.Integer e : list){
            ret[i++] = (byte)e.longValue();
        }
        return ret;
    }

    @SuppressWarnings("unchecked")
    public static byte[]
    toByteArray(ceylon.language.List<? extends ceylon.language.Integer> list,
            long... initialElements){
        byte[] ret = new byte[(list == null ? 0 : (int) list.getSize()) + initialElements.length];
        int i=0;
        for(;i<initialElements.length;i++){
            ret[i] = (byte) initialElements[i];
        }
        if(list != null){
            Iterator<? extends ceylon.language.Integer> iterator = list.iterator();
            Object o;
            while((o = iterator.next()) != finished_.get_()){
                ret[i++] = (byte)((ceylon.language.Integer)o).longValue();
            }
        }
        return ret;
    }
    
    @SuppressWarnings("unchecked")
    public static short[] 
    toShortArray(ceylon.language.Iterable<? extends ceylon.language.Integer, ?> sequence,
            long... initialElements){
        if(sequence instanceof ceylon.language.List)
            return toShortArray((ceylon.language.List<? extends ceylon.language.Integer>)sequence,
                    initialElements);
        List<ceylon.language.Integer> list = collectIterable(sequence);
        short[] ret = new short[list.size() + initialElements.length];
        int i=0;
        for(;i<initialElements.length;i++){
            ret[i] = (short) initialElements[i];
        }
        for(ceylon.language.Integer e : list){
            ret[i++] = (short)e.longValue();
        }
        return ret;
    }

    @SuppressWarnings("unchecked")
    public static short[]
    toShortArray(ceylon.language.List<? extends ceylon.language.Integer> list,
            long... initialElements){
        short[] ret = new short[(list == null ? 0 : (int) list.getSize()) + initialElements.length];
        int i=0;
        for(;i<initialElements.length;i++){
            ret[i] = (short) initialElements[i];
        }
        if(list != null){
            Iterator<? extends ceylon.language.Integer> iterator = list.iterator();
            Object o;
            while((o = iterator.next()) != finished_.get_()){
                ret[i++] = (short)((ceylon.language.Integer)o).longValue();
            }
        }
        return ret;
    }
    
    @SuppressWarnings("unchecked")
    public static int[] 
    toIntArray(ceylon.language.Iterable<? extends ceylon.language.Integer, ?> sequence,
            long... initialElements){
        if(sequence instanceof ceylon.language.List)
            return toIntArray((ceylon.language.List<? extends ceylon.language.Integer>)sequence,
                    initialElements);
        List<ceylon.language.Integer> list = collectIterable(sequence);
        int[] ret = new int[list.size() + initialElements.length];
        int i=0;
        for(;i<initialElements.length;i++){
            ret[i] = (int) initialElements[i];
        }
        for(ceylon.language.Integer e : list){
            ret[i++] = (int)e.longValue();
        }
        return ret;
    }

    @SuppressWarnings("unchecked")
    public static int[]
    toIntArray(ceylon.language.List<? extends ceylon.language.Integer> list,
            long... initialElements){
        int[] ret = new int[(list == null ? 0 : (int) list.getSize()) + initialElements.length];
        int i=0;
        for(;i<initialElements.length;i++){
            ret[i] = (int) initialElements[i];
        }
        if(list != null){
            Iterator<? extends ceylon.language.Integer> iterator = list.iterator();
            Object o;
            while((o = iterator.next()) != finished_.get_()){
                ret[i++] = (int)((ceylon.language.Integer)o).longValue();
            }
        }
        return ret;
    }
    
    @SuppressWarnings("unchecked")
    public static long[] 
    toLongArray(ceylon.language.Iterable<? extends ceylon.language.Integer, ?> sequence,
            long... initialElements){
        if(sequence instanceof ceylon.language.List)
            return toLongArray((ceylon.language.List<? extends ceylon.language.Integer>)sequence,
                    initialElements);
        List<ceylon.language.Integer> list = collectIterable(sequence);
        int i=initialElements.length;
        long[] ret = new long[list.size() + i];
        System.arraycopy(initialElements, 0, ret, 0, i);
        for(ceylon.language.Integer e : list){
            ret[i++] = e.longValue();
        }
        return ret;
    }

    @SuppressWarnings("unchecked")
    public static long[]
    toLongArray(ceylon.language.List<? extends ceylon.language.Integer> list,
            long... initialElements){
        if(list == null)
            return initialElements;
        int i=initialElements.length;
        long[] ret = new long[(int) list.getSize() + i];
        System.arraycopy(initialElements, 0, ret, 0, i);
        Iterator<? extends ceylon.language.Integer> iterator = list.iterator();
        Object o;
        while((o = iterator.next()) != finished_.get_()){
            ret[i++] = ((ceylon.language.Integer)o).longValue();
        }
        return ret;
    }

    @SuppressWarnings("unchecked")
    public static float[] 
    toFloatArray(ceylon.language.Iterable<? extends ceylon.language.Float, ?> sequence, 
            double... initialElements){
        if(sequence instanceof ceylon.language.List)
            return toFloatArray((ceylon.language.List<? extends ceylon.language.Float>)sequence,
                    initialElements);
        List<ceylon.language.Float> list = collectIterable(sequence);
        float[] ret = new float[list.size() + initialElements.length];
        int i=0;
        for(;i<initialElements.length;i++){
            ret[i] = (float) initialElements[i];
        }
        for(ceylon.language.Float e : list){
            ret[i++] = (float)e.doubleValue();
        }
        return ret;
    }

    @SuppressWarnings("unchecked")
    public static float[]
    toFloatArray(ceylon.language.List<? extends ceylon.language.Float> list,
            double... initialElements){
        float[] ret = new float[(list == null ? 0 : (int) list.getSize()) + initialElements.length];
        int i=0;
        for(;i<initialElements.length;i++){
            ret[i] = (float) initialElements[i];
        }
        if(list != null){
            Iterator<? extends ceylon.language.Float> iterator = list.iterator();
            Object o;
            while((o = iterator.next()) != finished_.get_()){
                ret[i++] = (float)((ceylon.language.Float)o).doubleValue();
            }
        }
        return ret;
    }

    @SuppressWarnings("unchecked")
    public static double[] 
    toDoubleArray(ceylon.language.Iterable<? extends ceylon.language.Float, ?> sequence,
            double... initialElements){
        if(sequence instanceof ceylon.language.List)
            return toDoubleArray((ceylon.language.List<? extends ceylon.language.Float>)sequence,
                    initialElements);
        List<ceylon.language.Float> list = collectIterable(sequence);
        int i=initialElements.length;
        double[] ret = new double[list.size() + i];
        System.arraycopy(initialElements, 0, ret, 0, i);
        for(ceylon.language.Float e : list){
            ret[i++] = e.doubleValue();
        }
        return ret;
    }

    @SuppressWarnings("unchecked")
    public static double[]
    toDoubleArray(ceylon.language.List<? extends ceylon.language.Float> list,
            double... initialElements){
        if(list == null)
            return initialElements;
        int i=initialElements.length;
        double[] ret = new double[(int) list.getSize() + i];
        System.arraycopy(initialElements, 0, ret, 0, i);
        Iterator<? extends ceylon.language.Float> iterator = list.iterator();
        Object o;
        while((o = iterator.next()) != finished_.get_()){
            ret[i++] = ((ceylon.language.Float)o).doubleValue();
        }
        return ret;
    }

    @SuppressWarnings("unchecked")
    public static char[] 
	toCharArray(ceylon.language.Iterable<? extends ceylon.language.Character, ?> sequence,
	        int... initialElements){
        if(sequence instanceof ceylon.language.List)
            return toCharArray((ceylon.language.List<? extends ceylon.language.Character>)sequence, 
                    initialElements);
        List<ceylon.language.Character> list = collectIterable(sequence);
        char[] ret = new char[list.size() + initialElements.length];
        int i=0;
        // FIXME: this is invalid and should yield a larger array by splitting chars > 16 bits in two
        for(;i<initialElements.length;i++){
            ret[i] = (char) initialElements[i];
        }
        for(ceylon.language.Character e : list){
            ret[i++] = (char)e.intValue();
        }
        return ret;
    }

    public static char[] 
	toCharArray(ceylon.language.List<? extends ceylon.language.Character> sequence,
	        int... initialElements){
        char[] ret = new char[(sequence == null ? 0 : (int) sequence.getSize()) + initialElements.length];
        int i=0;
        // FIXME: this is invalid and should yield a larger array by splitting chars > 16 bits in two
        for(;i<initialElements.length;i++){
            ret[i] = (char) initialElements[i];
        }
        while(sequence != null && !sequence.getEmpty()){
            ret[i++] = (char) sequence.getFirst().intValue();
            sequence = (ceylon.language.List<? extends ceylon.language.Character>)sequence.getRest();
        }
        return ret;
    }

    @SuppressWarnings("unchecked")
    public static int[] 
	toCodepointArray(ceylon.language.Iterable<? extends ceylon.language.Character, ?> sequence,
	        int... initialElements){
        if(sequence instanceof ceylon.language.List)
            return toCodepointArray((ceylon.language.List<? extends ceylon.language.Character>)sequence,
                    initialElements);
        List<ceylon.language.Character> list = collectIterable(sequence);
        int i=initialElements.length;
        int[] ret = new int[list.size() + i];
        System.arraycopy(initialElements, 0, ret, 0, i);
        for(ceylon.language.Character e : list){
            ret[i++] = e.intValue();
        }
        return ret;
    }

    @SuppressWarnings("unchecked")
    public static int[]
    toCodepointArray(ceylon.language.List<? extends ceylon.language.Character> list,
            int... initialElements){
        if(list == null)
            return initialElements;
        int i=initialElements.length;
        int[] ret = new int[(int) list.getSize() + i];
        System.arraycopy(initialElements, 0, ret, 0, i);
        Iterator<? extends ceylon.language.Character> iterator = list.iterator();
        Object o;
        while((o = iterator.next()) != finished_.get_()){
            ret[i++] = ((ceylon.language.Character)o).intValue();
        }
        return ret;
    }

    @SuppressWarnings("unchecked")
    public static java.lang.String[] 
    toJavaStringArray(ceylon.language.Iterable<? extends ceylon.language.String, ?> sequence,
            java.lang.String... initialElements){
        if(sequence instanceof ceylon.language.List)
            return toJavaStringArray((ceylon.language.List<? extends ceylon.language.String>)sequence,
                    initialElements);
        List<ceylon.language.String> list = collectIterable(sequence);
        int i=initialElements.length;
        java.lang.String[] ret = new java.lang.String[list.size() + i];
        System.arraycopy(initialElements, 0, ret, 0, i);
        for(ceylon.language.String e : list){
            ret[i++] = e.toString();
        }
        return ret;
    }

    @SuppressWarnings("unchecked")
    public static java.lang.String[]
    toJavaStringArray(ceylon.language.List<? extends ceylon.language.String> list,
            java.lang.String... initialElements){
        if(list == null)
            return initialElements;
        int i=initialElements.length;
        java.lang.String[] ret = new java.lang.String[(int) list.getSize() + i];
        System.arraycopy(initialElements, 0, ret, 0, i);
        Iterator<? extends ceylon.language.String> iterator = list.iterator();
        Object o;
        while((o = iterator.next()) != finished_.get_()){
            ret[i++] = ((ceylon.language.String)o).toString();
        }
        return ret;
    }

    @SuppressWarnings("unchecked")
    public static <T> T[] toArray(ceylon.language.List<? extends T> sequence,
            T[] ret, T... initialElements){
        if(sequence == null)
            return initialElements;
        int i=initialElements.length;
        System.arraycopy(initialElements, 0, ret, 0, i);
        Iterator<? extends T> iterator = sequence.iterator();
        Object o;
        while((o = iterator.next()) != finished_.get_()){
            ret[i++] = (T)o;
        }
        return ret;
    }

    @SuppressWarnings("unchecked")
    public static <T> T[] toArray(ceylon.language.Iterable<? extends T, ?> iterable,
            java.lang.Class<T> klass, T... initialElements){
        List<T> list = collectIterable(iterable);
        T[] ret = (T[]) java.lang.reflect.Array.newInstance(klass, 
        		list.size() + initialElements.length);
        // fast path
        if(initialElements.length == 0){
            // fast copy of list
            list.toArray(ret);
        }else{
            // fast copy of initialElements
            System.arraycopy(initialElements, 0, ret, 0, initialElements.length);
            // slow iteration for list :(
            int i = initialElements.length;
            for(T o : list)
                ret[i++] = o;
        }
        return ret;
    }

    @SuppressWarnings("unchecked")
    public static <T> T[] toArray(ceylon.language.List<? extends T> list,
            java.lang.Class<T> klass, T... initialElements){
        if(list == null)
            return initialElements;
        int i=initialElements.length;
        T[] ret = (T[]) java.lang.reflect.Array.newInstance(klass,
                        (int)list.getSize() + i);
        System.arraycopy(initialElements, 0, ret, 0, i);
        Iterator<? extends T> iterator = list.iterator();
        Object o;
        while((o = iterator.next()) != finished_.get_()){
            ret[i++] = (T)o;
        }
        return ret;
    }
    
    public static <T> T checkNull(T t) {
        if(t == null)
            throw new AssertionException("null value returned from native call not assignable to Object");
        return t;
    }
    
    /**
     * Return {@link empty_#getEmpty$ empty} or an {@link ArraySequence}
     * wrapping the given elements, depending on whether the given array is 
     * empty
     * @param elements The elements
     * @return A Sequential
     */
    @SuppressWarnings({"unchecked","rawtypes"})
    public static <T> Sequential<T> 
    sequentialInstance(TypeDescriptor $reifiedT, T[] elements) {
        if (elements.length == 0) {
            return (Sequential)empty_.get_();
        }
        // Annoyingly this implies an extra copy
        return ArraySequence.<T>instance($reifiedT, elements);
    }

    @SuppressWarnings({"unchecked","rawtypes"})
    public static Sequential<? extends ceylon.language.String> 
    sequentialInstanceBoxed(java.lang.String[] elements) {
        if (elements.length == 0){
            return (Sequential)empty_.get_();

        }
        int total = elements.length;
        java.lang.Object[] newArray = new java.lang.Object[total];
        int i = 0;
        for(java.lang.String element : elements){
            newArray[i++] = ceylon.language.String.instance(element);
        }
        // TODO Annoyingly this results in an extra copy
        return ArraySequence.instance(ceylon.language.String.$TypeDescriptor$, 
        		newArray);
    }

    @SuppressWarnings({"unchecked","rawtypes"})
    public static Sequential<? extends ceylon.language.Integer> 
    sequentialInstanceBoxed(long[] elements) {
        if (elements.length == 0){
            return (Sequential)empty_.get_();

        }
        int total = elements.length;
        java.lang.Object[] newArray = new java.lang.Object[total];
        int i = 0;
        for(long element : elements){
            newArray[i++] = ceylon.language.Integer.instance(element);
        }
        // TODO Annoyingly this results in an extra copy
        return ArraySequence.instance(ceylon.language.Integer.$TypeDescriptor$, 
        		newArray);
    }

    @SuppressWarnings({"unchecked","rawtypes"})
    public static Sequential<? extends ceylon.language.Character> 
    sequentialInstanceBoxed(int[] elements) {
        if (elements.length == 0){
            return (Sequential)empty_.get_();

        }
        int total = elements.length;
        java.lang.Object[] newArray = new java.lang.Object[total];
        int i = 0;
        for(int element : elements){
            newArray[i++] = ceylon.language.Character.instance(element);
        }
        // TODO Annoyingly this results in an extra copy
        return ArraySequence.instance(ceylon.language.Character.$TypeDescriptor$, 
        		newArray);
    }

    @SuppressWarnings({"unchecked","rawtypes"})
    public static Sequential<? extends ceylon.language.Boolean> 
    sequentialInstanceBoxed(boolean[] elements) {
        if (elements.length == 0){
            return (Sequential)empty_.get_();

        }
        int total = elements.length;
        java.lang.Object[] newArray = new java.lang.Object[total];
        int i = 0;
        for(boolean element : elements){
            newArray[i++] = ceylon.language.Boolean.instance(element);
        }
        // TODO Annoyingly this results in an extra copy
        return ArraySequence.instance(ceylon.language.Boolean.$TypeDescriptor$, 
        		newArray);
    }

    @SuppressWarnings({"unchecked","rawtypes"})
    public static Sequential<? extends ceylon.language.Float> 
    sequentialInstanceBoxed(double[] elements) {
        if (elements.length == 0){
            return (Sequential)empty_.get_();

        }
        int total = elements.length;
        java.lang.Object[] newArray = new java.lang.Object[total];
        int i = 0;
        for(double element : elements){
            newArray[i++] = ceylon.language.Float.instance(element);
        }
        // TODO Annoyingly this results in an extra copy
        return ArraySequence.instance(ceylon.language.Float.$TypeDescriptor$, 
        		newArray);
    }


    /**
     * Return {@link empty_#getEmpty$ empty} or an {@link ArraySequence}
     * wrapping the given elements, depending on whether the given array 
     * and varargs are empty
     * @param rest The elements at the end of the sequence
     * @param elements the elements at the start of the sequence
     * @return A Sequential
     */
    @SuppressWarnings({"unchecked"})
    public static <T> Sequential<? extends T> 
    sequentialInstance(TypeDescriptor $reifiedT, Sequential<? extends T> rest, 
    		T... elements) {
        return sequentialInstance($reifiedT, 0, 
        		elements.length, elements, true, rest);
    }
        
    /**
     * Returns a Sequential made by concatenating the {@code length} elements 
     * of {@code elements} starting from {@code state} with the elements of
     * {@code rest}: <code> {*elements[start:length], *rest}</code>. 
     * 
     * <strong>This method does not copy {@code elements} unless it has to</strong>
     */
    @SuppressWarnings("unchecked")
    public static <T> Sequential<? extends T> sequentialInstance(
            TypeDescriptor $reifiedT,  
            int start, int length, T[] elements, boolean copy,
            Sequential<? extends T> rest) {
        if (length == 0){
            if(rest.getEmpty()) {
                return (Sequential<T>)empty_.get_();
            }
            return rest;
        }
        // elements is not empty
        if(rest.getEmpty()) {
            return new ArraySequence<T>($reifiedT, elements, 
            		start, length, copy);
        }
        // we have both, let's find the total size
        int total = (int) (rest.getSize() + length);
        java.lang.Object[] newArray = new java.lang.Object[total];
        System.arraycopy(elements, start, newArray, 0, length);
        Iterator<? extends T> iterator = rest.iterator();
        int i = length;
        for(Object elem; (elem = iterator.next()) != finished_.get_(); i++){
            newArray[i] = elem;
        }
        return ArraySequence.<T>instance($reifiedT, newArray);
    }
    
    /**
     * Method for instantiating a Range (or Empty) from a Tree.SpreadOp, 
     * {@code start:length}. 
     * @param start The start
     * @param length The size of the Range to create
     * @return A range
     */
    @SuppressWarnings({"unchecked","rawtypes"})
    public static <T extends ceylon.language.Ordinal<? extends T>> Sequential<T> 
    spreadOp(TypeDescriptor $reifiedT, T start, long length) {
        if (length <= 0) {
            return (Sequential)empty_.get_();
        }
        if (start instanceof ceylon.language.Integer) {
            ceylon.language.Integer startInt = (ceylon.language.Integer)start;
            return new ceylon.language.Range($reifiedT, startInt, 
                    ceylon.language.Integer.instance(startInt.longValue() + (length - 1)));
        } else if (start instanceof ceylon.language.Character) {
            ceylon.language.Character startChar = (ceylon.language.Character)start;
            return new ceylon.language.Range($reifiedT, startChar, 
                    ceylon.language.Character.instance((int)(startChar.intValue() + length - 1)));
        } else {
            T end = start;
            long ii = 0L;
            while (++ii < length) {
                end = end.getSuccessor();
            }
            return new ceylon.language.Range($reifiedT, start, end);
        }
    }

    /**
     * Returns a runtime exception. To be used by implementors 
     * of mixin methods used to access super-interfaces $impl fields
     * for final classes that don't and will never need them
     */
    public static RuntimeException makeUnimplementedMixinAccessException() {
        return new RuntimeException("Internal error: should never be called");
    }

    /**
     * Specialised version of Tuple.spanFrom for when the
     * typechecker determines that it can do better than the 
     * generic one that returns a Sequential. Here we return 
     * a Tuple, although our type signature hides this.
     */
    public static Sequential<?> tuple_spanFrom(Ranged tuple, 
    		ceylon.language.Integer index){
        Sequential<?> seq = (Sequential<?>)tuple;
        long i = index.longValue();
        while(i-- > 0){
            seq = seq.getRest();
        }
        return seq;
    }
    
    public static boolean[] fillArray(boolean[] array, boolean val){
        Arrays.fill(array, val);
        return array;
    }
    
    public static byte[] fillArray(byte[] array, byte val){
        Arrays.fill(array, val);
        return array;
    }
    
    public static short[] fillArray(short[] array, short val){
        Arrays.fill(array, val);
        return array;
    }
    
    public static int[] fillArray(int[] array, int val){
        Arrays.fill(array, val);
        return array;
    }
    
    public static long[] fillArray(long[] array, long val){
        Arrays.fill(array, val);
        return array;
    }
    
    public static float[] fillArray(float[] array, float val){
        Arrays.fill(array, val);
        return array;
    }
    
    public static double[] fillArray(double[] array, double val){
        Arrays.fill(array, val);
        return array;
    }
    
    public static char[] fillArray(char[] array, char val){
        Arrays.fill(array, val);
        return array;
    }
    
    public static <T> T[] fillArray(T[] array, T val){
        Arrays.fill(array, val);
        return array;
    }

    @SuppressWarnings("unchecked")
    public static <T> T[] makeArray(TypeDescriptor $reifiedElement, int size){
        return (T[]) java.lang.reflect.Array.newInstance($reifiedElement.getArrayElementClass(), size);
    }

    @SuppressWarnings("unchecked")
    public static <T> T[] makeArray(TypeDescriptor $reifiedElement, int... dimensions){
        return (T[]) java.lang.reflect.Array.newInstance($reifiedElement.getArrayElementClass(), dimensions);
    }

    /**
     * Returns a runtime exception. To be used by implementors 
     * of Java array methods used to make sure they are never 
     * called
     */
    public static RuntimeException makeJavaArrayWrapperException() {
        return new RuntimeException("Internal error: should never be called");
    }

    /**
     * Throws an exception without having to declare it. This 
     * uses a Ceylon helper that does this because Ceylon does 
     * not have checked exceptions. This is merely to avoid a 
     * javac check wrt. checked exceptions.
     * Stef tried using Unsafe.throwException() but 
     * Unsafe.getUnsafe() throws if we have a ClassLoader, and 
     * the only other way is using reflection to get to it, 
     * which starts to smell real bad when we can just use a 
     * Ceylon helper.
     */
    public static void rethrow(final Throwable t){
        ceylon.language.impl.rethrow_.rethrow(t);
    }

    /**
     * Null-safe equals.
     */
    public static boolean eq(Object a, Object b) {
        if(a == null)
            return b == null;
        if(b == null)
            return false;
        return a.equals(b);
    }
    
    /**
     * Applies the given function to the given arguments. The 
     * argument types are assumed to be correct and will not 
     * be checked. This method will properly deal with variadic 
     * functions. The arguments are expected to be spread in the 
     * given sequential, even in the case of variadic functions, 
     * which means that there will be no spreading of any 
     * Sequential instance in the given arguments. On the 
     * contrary, a portion of the given arguments may be packaged 
     * into a Sequential if the given function is variadic.
     * 
     * @param function the function to apply
     * @param arguments the argument values to pass to the function
     * @return the function's return value
     */
    public static <Return> Return 
    apply(Callable<? extends Return> function, 
    		Sequential<? extends Object> arguments){
        int variadicParameterIndex = function.$getVariadicParameterIndex$();
        switch ((int) arguments.getSize()) {
        case 0:
            // even if the function is variadic it will overload $call so we're good
            return function.$call$();
        case 1:
            // if the first param is variadic, just pass the sequence along
            if(variadicParameterIndex == 0)
                return function.$callvariadic$(arguments);
            return function.$call$(arguments.get(Integer.instance(0)));
        case 2:
            switch(variadicParameterIndex){
            // pass the sequence along
            case 0: return function.$callvariadic$(arguments);
            // extract the first, pass the rest
            case 1: return function.$callvariadic$(arguments.get(Integer.instance(0)), 
                    (Sequential<?>)arguments.spanFrom(Integer.instance(1)));
            // no variadic param, or after we run out of elements to pass
            default:
                return function.$call$(arguments.get(Integer.instance(0)), 
                                          arguments.get(Integer.instance(1)));
            }
        case 3:
            switch(variadicParameterIndex){
            // pass the sequence along
            case 0: return function.$callvariadic$(arguments);
            // extract the first, pass the rest
            case 1: return function.$callvariadic$(arguments.get(Integer.instance(0)), 
                    (Sequential<?>)arguments.spanFrom(Integer.instance(1)));
            // extract the first and second, pass the rest
            case 2: return function.$callvariadic$(arguments.get(Integer.instance(0)),
                    arguments.get(Integer.instance(1)),
                    (Sequential<?>)arguments.spanFrom(Integer.instance(2)));
            // no variadic param, or after we run out of elements to pass
            default:
            return function.$call$(arguments.get(Integer.instance(0)), 
                    arguments.get(Integer.instance(1)), 
                    arguments.get(Integer.instance(2)));
            }
        default:
            switch(variadicParameterIndex){
            // pass the sequence along
            case 0: return function.$callvariadic$(arguments);
            // extract the first, pass the rest
            case 1: return function.$callvariadic$(arguments.get(Integer.instance(0)), 
                    (Sequential<?>)arguments.spanFrom(Integer.instance(1)));
            // extract the first and second, pass the rest
            case 2: return function.$callvariadic$(arguments.get(Integer.instance(0)),
                    arguments.get(Integer.instance(1)),
                    (Sequential<?>)arguments.spanFrom(Integer.instance(2)));
            case 3: return function.$callvariadic$(arguments.get(Integer.instance(0)),
                    arguments.get(Integer.instance(1)),
                    arguments.get(Integer.instance(2)),
                    (Sequential<?>)arguments.spanFrom(Integer.instance(3)));
            // no variadic param
            case -1:
                java.lang.Object[] args = Util.toArray(arguments, 
                		new java.lang.Object[(int) arguments.getSize()]);
                return function.$call$(args);
            // we have a variadic param in there bothering us
            default:
                // we stuff everything before the variadic into an array
                int beforeVariadic = (int)Math.min(arguments.getSize(), 
                		variadicParameterIndex);
                boolean needsVariadic = beforeVariadic < arguments.getSize();
                args = new java.lang.Object[beforeVariadic + (needsVariadic ? 1 : 0)];
                Iterator<?> iterator = arguments.iterator();
                java.lang.Object it;
                int i=0;
                while(i < beforeVariadic && 
                		(it = iterator.next()) != finished_.get_()){
                    args[i++] = it;
                }
                // add the remainder as a variadic arg if required
                if(needsVariadic){
                    args[i] = arguments.spanFrom(Integer.instance(beforeVariadic));
                    return function.$callvariadic$(args);
                }
                return function.$call$(args);
            }
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> java.lang.Class<T> 
    getJavaClassForDescriptor(TypeDescriptor descriptor) {
        if(descriptor == TypeDescriptor.NothingType
           || descriptor == ceylon.language.Object.$TypeDescriptor$
           || descriptor == ceylon.language.Anything.$TypeDescriptor$
           || descriptor == ceylon.language.Basic.$TypeDescriptor$
           || descriptor == ceylon.language.Null.$TypeDescriptor$
           || descriptor == ceylon.language.Identifiable.$TypeDescriptor$)
            return (java.lang.Class<T>) Object.class;
        if(descriptor instanceof TypeDescriptor.Class)
            return (java.lang.Class<T>) ((TypeDescriptor.Class) descriptor).getKlass();
        if(descriptor instanceof TypeDescriptor.Member)
            return getJavaClassForDescriptor(((TypeDescriptor.Member) descriptor).getMember());
        if(descriptor instanceof TypeDescriptor.Intersection)
            return (java.lang.Class<T>) Object.class;
        if(descriptor instanceof TypeDescriptor.Union){
            TypeDescriptor.Union union = (TypeDescriptor.Union) descriptor;
            TypeDescriptor[] members = union.getMembers();
            // special case for optional types
            if(members.length == 2){
                if(members[0] == ceylon.language.Null.$TypeDescriptor$)
                    return getJavaClassForDescriptor(members[1]);
                if(members[1] == ceylon.language.Null.$TypeDescriptor$)
                    return getJavaClassForDescriptor(members[0]);
            }
            return (java.lang.Class<T>) Object.class;
        }
        return (java.lang.Class<T>) Object.class;
    }
    
    public static int arrayLength(Object o) {
        if (o instanceof Object[]) 
            return ((Object[])o).length;
        else if (o instanceof boolean[])
            return ((boolean[])o).length;
        else if (o instanceof float[])
            return ((float[])o).length;
        else if (o instanceof double[])
            return ((double[])o).length;
        else if (o instanceof char[])
            return ((char[])o).length;
        else if (o instanceof byte[])
            return ((byte[])o).length;
        else if (o instanceof short[])
            return ((short[])o).length;
        else if (o instanceof int[])
            return ((int[])o).length;
        else if (o instanceof long[])
            return ((long[])o).length;
        throw new ClassCastException(notArrayType(o));
    }
    
    /**
     * Used by the JVM backend to get unboxed items from an Array&lt;Integer> backing array
     */
    public static long getIntegerArray(Object o, int index) {
        if (o instanceof byte[])
            return ((byte[])o)[index];
        else if (o instanceof short[])
            return ((short[])o)[index];
        else if (o instanceof int[])
            return ((int[])o)[index];
        else if (o instanceof long[])
            return ((long[])o)[index];
        throw new ClassCastException(notArrayType(o));
    }
    
    /**
     * Used by the JVM backend to get unboxed items from an Array&lt;Float> backing array
     */
    public static double getFloatArray(Object o, int index) {
        if (o instanceof float[])
            return ((float[])o)[index];
        else if (o instanceof double[])
            return ((double[])o)[index];
        throw new ClassCastException(notArrayType(o));
    }
    
    /**
     * Used by the JVM backend to get unboxed items from an Array&lt;Character> backing array
     */
    public static int getCharacterArray(Object o, int index) {
        if (o instanceof int[])
            return ((int[])o)[index];
        throw new ClassCastException(notArrayType(o));
    }
    
    /**
     * Used by the JVM backend to get unboxed items from an Array&lt;Boolean> backing array
     */
    public static boolean getBooleanArray(Object o, int index) {
        if (o instanceof boolean[])
            return ((boolean[])o)[index];
        throw new ClassCastException(notArrayType(o));
    }
    
    /**
     * Used by the JVM backend to get items from an ArraySequence object. Beware: do not use that
     * for Array&lt;Object> as there's too much magic in there.
     */
    public static Object getObjectArray(Object o, int index) {
        if (o instanceof Object[])
            return ((Object[])o)[index];
        else if (o instanceof boolean[])
            return ceylon.language.Boolean.instance(((boolean[])o)[index]);
        else if (o instanceof float[])
            return ceylon.language.Float.instance(((float[])o)[index]);
        else if (o instanceof double[])
            return ceylon.language.Float.instance(((double[])o)[index]);
        else if (o instanceof char[])
            return ceylon.language.Character.instance(((char[])o)[index]);
        else if (o instanceof byte[])
            return ceylon.language.Integer.instance(((byte[])o)[index]);
        else if (o instanceof short[])
            return ceylon.language.Integer.instance(((short[])o)[index]);
        else if (o instanceof int[])
            return ceylon.language.Integer.instance(((int[])o)[index]);
        else if (o instanceof long[])
            return ceylon.language.Integer.instance(((long[])o)[index]);
        throw new ClassCastException(notArrayType(o));
    }
    
    private static String notArrayType(Object o) {
        return (o == null ? "null" : o.getClass().getName()) + " is not an array type";
    }
    
}
