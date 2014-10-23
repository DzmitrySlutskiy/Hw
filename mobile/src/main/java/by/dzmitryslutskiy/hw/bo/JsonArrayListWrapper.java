package by.dzmitryslutskiy.hw.bo;

import android.support.annotation.NonNull;
import android.util.Log;

import org.json.JSONArray;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * JsonArrayListWrapper
 * Version information
 * 22.10.2014
 * Created by Dzmitry Slutskiy.
 */
public class JsonArrayListWrapper<T extends JsonObjectWrapper> implements List<T> {

    private CreateObject<T> mCreator;
    private JSONArray mArray;

    public JsonArrayListWrapper(JSONArray array, CreateObject<T> creator) throws Exception {
        this.mCreator = creator;
        this.mArray = array;
    }

    @Override
    public void add(int location, T object) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public boolean add(T object) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public boolean addAll(int location, Collection<? extends T> collection) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public boolean addAll(Collection<? extends T> collection) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public boolean contains(Object object) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public T get(int location) {
        try {
            Log.d("get in arrays", "get " + location);
            return mCreator.createObject(mArray.getJSONObject(location));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int indexOf(Object object) {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return mArray.length() == 0;
    }

    @NonNull
    @Override
    public Iterator<T> iterator() {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public int lastIndexOf(Object object) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @NonNull
    @Override
    public ListIterator<T> listIterator() {
        throw new UnsupportedOperationException("Not implemented");
    }

    @NonNull
    @Override
    public ListIterator<T> listIterator(int location) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public T remove(int location) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public boolean remove(Object object) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public T set(int location, T object) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public int size() {
        return mArray.length();
    }

    @NonNull
    @Override
    public List<T> subList(int start, int end) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @NonNull
    @Override
    public Object[] toArray() {
        throw new UnsupportedOperationException("Not implemented");
    }

    @NonNull
    @Override
    public <T1> T1[] toArray(T1[] array) {
        throw new UnsupportedOperationException("Not implemented");
    }
}
