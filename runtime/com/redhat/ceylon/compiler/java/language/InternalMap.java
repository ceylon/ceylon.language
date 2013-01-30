package com.redhat.ceylon.compiler.java.language;

import ceylon.language.Array;
import ceylon.language.Boolean;
import ceylon.language.Callable;
import ceylon.language.Category$impl;
import ceylon.language.Cloneable$impl;
import ceylon.language.Collection;
import ceylon.language.Collection$impl;
import ceylon.language.Comparison;
import ceylon.language.Container$impl;
import ceylon.language.Correspondence$impl;
import ceylon.language.Entry;
import ceylon.language.Integer;
import ceylon.language.Iterable;
import ceylon.language.Iterable$impl;
import ceylon.language.Iterator;
import ceylon.language.Iterator$impl;
import ceylon.language.List;
import ceylon.language.List$impl;
import ceylon.language.Map;
import ceylon.language.Map$impl;
import ceylon.language.Ranged$impl;
import ceylon.language.Sequence;
import ceylon.language.Sequential;
import ceylon.language.Set;

import com.redhat.ceylon.compiler.java.metadata.Annotation;
import com.redhat.ceylon.compiler.java.metadata.Annotations;
import com.redhat.ceylon.compiler.java.metadata.Ignore;
import com.redhat.ceylon.compiler.java.metadata.TypeInfo;
import com.redhat.ceylon.compiler.java.metadata.TypeParameter;
import com.redhat.ceylon.compiler.java.metadata.TypeParameters;

/** An immutable map returned by certain methods and functions of the language module.
 * This is only to avoid depending on ceylon.collection.
 * 
 * @author Enrique Zamudio
 */
public class InternalMap<Key, Item> implements Map<Key, Item> {

    private final java.util.Map<? extends Key, ? extends Item> m;

    private final ceylon.language.Category$impl $ceylon$language$Category$this;
    private final ceylon.language.Container$impl<Entry<? extends Key,? extends Item>,java.lang.Object> $ceylon$language$Container$this;
    private final ceylon.language.Iterable$impl<Entry<? extends Key,? extends Item>,java.lang.Object> $ceylon$language$Iterable$this;
    private final ceylon.language.Correspondence$impl<java.lang.Object, Item> $ceylon$language$Correspondence$this;
    private final ceylon.language.Map$impl<Key, Item> $ceylon$language$Map$this;
    private final ceylon.language.Collection$impl<Entry<? extends Key,? extends Item>> $ceylon$language$Collection$this;
    private final ceylon.language.Cloneable$impl $ceylon$language$Cloneable$this;

    public InternalMap(final java.util.Map<? extends Key, ? extends Item> map) {
        this.$ceylon$language$Category$this = new ceylon.language.Category$impl(this);
        this.$ceylon$language$Container$this = new ceylon.language.Container$impl<Entry<? extends Key,? extends Item>,java.lang.Object>(this);;
        this.$ceylon$language$Iterable$this = new ceylon.language.Iterable$impl<Entry<? extends Key,? extends Item>,java.lang.Object>(this);
        this.$ceylon$language$Correspondence$this = new ceylon.language.Correspondence$impl<java.lang.Object, Item>(this);
        this.$ceylon$language$Map$this = new ceylon.language.Map$impl<Key, Item>(this);
        this.$ceylon$language$Collection$this = new ceylon.language.Collection$impl<Entry<? extends Key,? extends Item>>(this);
        this.$ceylon$language$Cloneable$this = new ceylon.language.Cloneable$impl((Map)this);

        this.m = map;
    }


    @Ignore
    @Override
    public Category$impl $ceylon$language$Category$impl(){
        return $ceylon$language$Category$this;
    }

    @Ignore
    @Override
    public Container$impl<Entry<? extends Key,? extends Item>,java.lang.Object> $ceylon$language$Container$impl(){
        return $ceylon$language$Container$this;
    }

    @Ignore
    @Override
    public Iterable$impl<Entry<? extends Key,? extends Item>,java.lang.Object> $ceylon$language$Iterable$impl(){
        return $ceylon$language$Iterable$this;
    }

    @Ignore
    @Override
    public Collection$impl<Entry<? extends Key,? extends Item>> $ceylon$language$Collection$impl(){
        return $ceylon$language$Collection$this;
    }

    @Ignore
    @Override
    public Map$impl<Key,Item> $ceylon$language$Map$impl(){
        return $ceylon$language$Map$this;
    }

    @Ignore
    @Override
    public Correspondence$impl<java.lang.Object,Item> $ceylon$language$Correspondence$impl(){
        return $ceylon$language$Correspondence$this;
    }

    @Ignore
    @Override
    public Cloneable$impl $ceylon$language$Cloneable$impl(){
        return $ceylon$language$Cloneable$this;
    }

    @Override
    @Annotations(@Annotation("formal"))
    @TypeInfo("Item|ceylon.language::Nothing")
    public Item get(Object key) {
        return m.get(key);
    }

    @Override
    @Annotations(@Annotation("default"))
    public boolean defines(Object key) {
        return m.containsKey(key);
    }

    @Override
    @Annotations(@Annotation("default"))
    public boolean definesEvery(Iterable<? extends Object,? extends java.lang.Object> keys) {
        return $ceylon$language$Correspondence$this.definesEvery(keys);
    }

//    @Override
//    @Ignore
//    public boolean definesEvery() {
//        return corr$impl.definesEvery();
//    }

//    @Override
//    @Ignore
//    public Iterable<? extends Object,? extends java.lang.Object> definesEvery$keys() {
//        return corr$impl.definesEvery$keys();
//    }

    @Override
    @Annotations(@Annotation("default"))
    public boolean definesAny(Iterable<? extends Object,? extends java.lang.Object> keys) {
        return $ceylon$language$Correspondence$this.definesAny(keys);
    }

//    @Override
//    @Ignore
//    public boolean definesAny() {
//        return corr$impl.definesAny();
//    }

//    @Override
//    @Ignore
//    public Iterable<? extends Object,? extends java.lang.Object> definesAny$keys() {
//        return corr$impl.definesAny$keys();
//    }

    @Override
    @Annotations(@Annotation("default"))
    @TypeInfo("ceylon.language::Sequential<Item|ceylon.language::Nothing>")
    public Sequential<? extends Item> items(Iterable<? extends Object,? extends java.lang.Object> keys) {
        return $ceylon$language$Correspondence$this.items(keys);
    }

//    @Override
//    @Ignore
//    public Sequential<? extends Item> items() {
//        return corr$impl.items();
//    }

//    @Override
//    @Ignore
//    public Iterable<? extends Object,? extends java.lang.Object> items$keys() {
//        return corr$impl.items$keys();
//    }

    @Override
    @Annotations({ @Annotation("actual"), @Annotation("default") })
    public boolean getEmpty() {
        return m.isEmpty();
    }

    @Override
    @Annotations({ @Annotation("actual"), @Annotation("default") })
    public boolean contains(Object element) {
        return $ceylon$language$Collection$this.contains(element);
    }

    @Override
    @Annotations(@Annotation("formal"))
    @TypeInfo("ceylon.language::Iterator<Element>")
    public Iterator<? extends Entry<? extends Key, ? extends Item>> getIterator() {
        return new AbstractIterator<Entry<? extends Key, ? extends Item>>(){
            private final java.util.Iterator<? extends java.util.Map.Entry<? extends Key, ? extends Item>> inner = m.entrySet().iterator();
            
            @Override
            @Annotations(@Annotation("formal"))
            @TypeInfo(value="Element|ceylon.language::Finished", erased=true)
            public Object next() {
                if (inner.hasNext()) {
                    java.util.Map.Entry<? extends Key, ? extends Item> e = inner.next();
                    return new ceylon.language.Entry<Key, Item>(e.getKey(), e.getValue());
                }
                return ceylon.language.finished_.getFinished$();
            }
        };
    }

    @Override
    @Annotations({ @Annotation("actual"), @Annotation("default") })
    @TypeInfo("ceylon.language::Nothing|Element")
    public Entry<? extends Key, ? extends Item> getFirst() {
        return (Entry<? extends Key, ? extends Item>) $ceylon$language$Iterable$this.getFirst();
    }

    @Override
    @Annotations({ @Annotation("actual"), @Annotation("default") })
    @TypeInfo("ceylon.language::Nothing|Element")
    public Entry<? extends Key, ? extends Item> getLast() {
        return (Entry<? extends Key, ? extends Item>) $ceylon$language$Iterable$this.getLast();
    }

    @Override
    @Annotations(@Annotation("default"))
    @TypeInfo("ceylon.language::Iterable<Element,ceylon.language::Null>")
    public Iterable<? extends Entry<? extends Key, ? extends Item>, ?> getRest() {
        return $ceylon$language$Iterable$this.getRest();
    }

    @Override
    @Annotations(@Annotation("default"))
    @TypeInfo("ceylon.language::Sequential<Element>")
    public Sequential<? extends Entry<? extends Key, ? extends Item>> getSequence() {
        return $ceylon$language$Iterable$this.getSequence();
    }

    @Override
    @Annotations(@Annotation("default"))
    @TypeInfo("ceylon.language::Iterable<Result>")
    @TypeParameters(@TypeParameter("Result"))
    public <Result> Iterable<? extends Result, ?> map(
            Callable<? extends Result> collecting) {
        return $ceylon$language$Iterable$this.map(collecting);
    }

    @Override
    @Annotations(@Annotation("default"))
    @TypeInfo("ceylon.language::Iterable<Element,ceylon.language::Null>")
    public Iterable<? extends Entry<? extends Key, ? extends Item>, ?> filter(
            Callable<? extends Boolean> selecting) {
        return $ceylon$language$Iterable$this.filter(selecting);
    }

    @Override
    @Annotations(@Annotation("default"))
    @TypeInfo("Result")
    @TypeParameters(@TypeParameter("Result"))
    public <Result> Result fold(Result initial,
            Callable<? extends Result> accumulating) {
        return $ceylon$language$Iterable$this.fold(initial, accumulating);
    }

    @Override
    @Annotations(@Annotation("default"))
    @TypeInfo("Element|ceylon.language::Nothing")
    public Entry<? extends Key, ? extends Item> find(
            Callable<? extends Boolean> selecting) {
        return $ceylon$language$Iterable$this.find(selecting);
    }

    @Override
    @Annotations(@Annotation("default"))
    @TypeInfo("Element|ceylon.language::Nothing")
    public Entry<? extends Key, ? extends Item> findLast(
            Callable<? extends Boolean> selecting) {
        return $ceylon$language$Iterable$this.findLast(selecting);
    }

    @Override
    @Annotations(@Annotation("default"))
    @TypeInfo("ceylon.language::Sequential<Element>")
    public Sequential<? extends Entry<? extends Key, ? extends Item>> sort(
            Callable<? extends Comparison> comparing) {
        return $ceylon$language$Iterable$this.sort(comparing);
    }

    @Override
    @Annotations(@Annotation("default"))
    @TypeInfo("ceylon.language::Sequential<Result>")
    @TypeParameters(@TypeParameter("Result"))
    public <Result> Sequential<? extends Result> collect(
            Callable<? extends Result> collecting) {
        return $ceylon$language$Iterable$this.collect(collecting);
    }

    @Override
    @Annotations(@Annotation("default"))
    @TypeInfo("ceylon.language::Sequential<Element>")
    public Sequential<? extends Entry<? extends Key, ? extends Item>> select(
            Callable<? extends Boolean> selecting) {
        return $ceylon$language$Iterable$this.select(selecting);
    }

    @Override
    @Annotations(@Annotation("default"))
    @TypeInfo("ceylon.language::Boolean")
    public boolean any(Callable<? extends Boolean> selecting) {
        return $ceylon$language$Iterable$this.any(selecting);
    }

    @Override
    @Annotations(@Annotation("default"))
    @TypeInfo("ceylon.language::Boolean")
    public boolean every(Callable<? extends Boolean> selecting) {
        return $ceylon$language$Iterable$this.every(selecting);
    }

    @Override
    @Annotations(@Annotation("default"))
    @TypeInfo("ceylon.language::Iterable<Element,ceylon.language::Null>")
    public Iterable<? extends Entry<? extends Key, ? extends Item>, ? extends java.lang.Object> skipping(
            long skip) {
        return $ceylon$language$Iterable$this.skipping(skip);
    }

    @Override
    @Annotations(@Annotation("default"))
    @TypeInfo("ceylon.language::Iterable<Element,ceylon.language::Null>")
    public Iterable<? extends Entry<? extends Key, ? extends Item>, ? extends java.lang.Object> taking(
            long take) {
        return $ceylon$language$Iterable$this.taking(take);
    }

    @Override
    @Annotations(@Annotation("default"))
    @TypeInfo("ceylon.language::Iterable<Element,ceylon.language::Null>")
    public Iterable<? extends Entry<? extends Key, ? extends Item>, ? extends java.lang.Object> by(long step) {
        return $ceylon$language$Iterable$this.by(step);
    }

    @Override
    @Annotations(@Annotation("default"))
    @TypeInfo("ceylon.language::Integer")
    public long count(Callable<? extends Boolean> selecting) {
        return $ceylon$language$Iterable$this.count(selecting);
    }

    @Override
    @Annotations(@Annotation("default"))
    @TypeInfo("ceylon.language::Iterable<Element&ceylon.language::Object>")
    public Iterable<? extends Entry<? extends Key, ? extends Item>, ? extends java.lang.Object> getCoalesced() {
        return $ceylon$language$Iterable$this.getCoalesced();
    }

    @Override
    @Annotations(@Annotation("default"))
    @TypeInfo("ceylon.language::Iterable<ceylon.language::Entry<ceylon.language::Integer,Element&ceylon.language::Object>>")
    public Iterable<? extends Entry<? extends Integer, ? extends Entry<? extends Key, ? extends Item>>, ? extends java.lang.Object> getIndexed() {
        return $ceylon$language$Iterable$this.getIndexed();
    }

    @Override
    @Annotations(@Annotation("default"))
    @TypeInfo("ceylon.language::Iterable<Element|Other>")
    @TypeParameters(@TypeParameter("Other"))
    public <Other> Iterable chain(Iterable<? extends Other, ? extends java.lang.Object> other) {
        return $ceylon$language$Iterable$this.chain(other);
    }
    @Override @Ignore
    public <Default>Iterable<?,?> defaultNullElements(Default defaultValue) {
        return $ceylon$language$Iterable$this.defaultNullElements(defaultValue);
    }
    /*@Override @Ignore
    public <Key2> Map<? extends Key2, ? extends Sequence<? extends Entry<? extends Key, ? extends Item>>> group(Callable<? extends Key2> grouping) {
        return iter$impl.group(grouping);
    }*/

    @Override
    @Annotations(@Annotation("formal"))
    public long getSize() {
        return m.size();
    }

    @Override
    @Annotations(@Annotation("default"))
    public boolean containsEvery(Iterable<?,?> elements) {
        return $ceylon$language$Category$this.containsEvery(elements);
    }

//    @Override
//    @Ignore
//    public boolean containsEvery() {
//        return cat$impl.containsEvery();
//    }
//
//    @Override
//    @Ignore
//    public Sequential<?> containsEvery$elements() {
//        return cat$impl.containsEvery$elements();
//    }

    @Override
    @Annotations(@Annotation("default"))
    public boolean containsAny(Iterable<?,?> elements) {
        return $ceylon$language$Category$this.containsAny(elements);
    }

//    @Override
//    @Ignore
//    public boolean containsAny() {
//        return cat$impl.containsAny();
//    }
//
//    @Override
//    @Ignore
//    public Sequential<?> containsAny$elements() {
//        return cat$impl.containsAny$elements();
//    }

    @Override
    @Annotations(@Annotation("formal"))
    public InternalMap<? extends Key, ? extends Item> getClone() {
        return new InternalMap<Key,Item>(java.util.Collections.unmodifiableMap(m));
    }

    @Override
    @Annotations({ @Annotation("actual"), @Annotation("default") })
    @TypeInfo("ceylon.language::Set<Key>")
    public Set<? extends Key> getKeys() {
        return $ceylon$language$Map$this.getKeys();
    }

    @Override
    @Annotations(@Annotation("default"))
    @TypeInfo("ceylon.language::Collection<Item>")
    public Collection<? extends Item> getValues() {
        return $ceylon$language$Map$this.getValues();
    }

    @Override
    @Annotations(@Annotation("default"))
    @TypeInfo("ceylon.language::Map<Item,ceylon.language::Set<Key>>")
    public Map<? extends Item, ? extends Set<? extends Key>> getInverse() {
        return $ceylon$language$Map$this.getInverse();
    }

    @Override
    @Annotations(@Annotation("default"))
    @TypeInfo("ceylon.language::Map<Key,Result>")
    @TypeParameters(@TypeParameter(value="Result", satisfies="ceylon.language::Object"))
    public <Result> Map<? extends Key, ? extends Result> mapItems(
            Callable<? extends Result> mapping) {
        return $ceylon$language$Map$this.mapItems(mapping);
    }

//    @Override
//    @Ignore
//    public Correspondence$impl<java.lang.Object, ? extends Item> $ceylon$language$Correspondence$impl() {
//        return corr$impl;
//    }

}
