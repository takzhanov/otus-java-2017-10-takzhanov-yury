package io.github.takzhanov.umbrella.hw03;

import java.util.*;

public class MyArrayList<T> implements List<T> {
    private T[] items;
    private int size;

    public MyArrayList(int capacity) {
        items = (T[]) new Object[capacity];
        size = 0;
    }

    public MyArrayList() {
        this(0);
    }

    public static <T> MyArrayList<T> of(T... args) {
        MyArrayList<T> arr = new MyArrayList<>(args.length);
        arr.addAll(Arrays.asList(args));
        return arr;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return 0 == size;
    }

    @Override
    public boolean contains(Object o) {
        if (o == null) {
            return false;
        }
        for (T item : items) {
            if (o.equals(item)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return listIterator();
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(items, size);
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        if (a.length >= size) {
            for (int i = 0; i < size; i++) {
                a[i] = (T1) items[i];
            }
            for (int i = size; i < a.length; i++) {
                a[i] = null;
            }
            return a;
        } else {
            return (T1[]) Arrays.copyOf(items, size, a.getClass());
        }
    }

    @Override
    public boolean add(T t) {
        int newSize = size + 1;
        if (newSize > items.length) {
            items = Arrays.copyOf(items, newSize);
        }
        items[size] = t;
        size++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        if (null == o) {
            return false;
        }
        int index = -1;
        for (int i = 0; i < size; i++) {
            if (o.equals(items[i])) {
                index = i;
                break;
            }
        }
        if (index != -1) {
            T[] newItems = (T[]) new Object[items.length - 1];
            System.arraycopy(items, 0, newItems, 0, index);
            if (index < items.length) {
                System.arraycopy(items, index + 1, newItems, index, items.length - 1 - index);
            }
            items = newItems;
            return true;
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        int newSize = size + c.size();
        if (newSize > items.length) {
            items = Arrays.copyOf(items, newSize);
        }
        for (T newItem : c) {
            add(newItem);
        }
        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public T get(int index) {
        return items[index];
    }

    @Override
    public T set(int index, T element) {
        return null;
    }

    @Override
    public void add(int index, T element) {

    }

    @Override
    public T remove(int index) {
        return null;
    }

    @Override
    public int indexOf(Object o) {
        return 0;
    }

    @Override
    public int lastIndexOf(Object o) {
        return 0;
    }

    @Override
    public ListIterator<T> listIterator() {
        final ListIterator<T> listIterator = new ListIterator<T>() {
            private int cur = 0;
            private boolean modified = false;
            private int lastIndex = -1;

            @Override
            public boolean hasNext() {
                return cur < size;
            }

            @Override
            public T next() {
                lastIndex = cur;
                cur++;
                return items[lastIndex];
            }

            @Override
            public boolean hasPrevious() {
                return cur > 0;
            }

            @Override
            public T previous() {
                cur--;
                lastIndex = cur;
                return items[lastIndex];
            }

            @Override
            public int nextIndex() {
                return 0;
            }

            @Override
            public int previousIndex() {
                return 0;
            }

            @Override
            public void remove() {
                T[] newItems = Arrays.copyOf(items, lastIndex);
                for (int i = lastIndex + 1; i < size; i++) {
                    newItems[i - 1] = items[i];
                }
                size--;
                items = newItems;
                modified = true;
            }

            @Override
            public void set(T t) {
                items[lastIndex] = t;
            }

            @Override
            public void add(T t) {
                modified = true;
            }
        };
        return listIterator;
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        return null;
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        return null;
    }
}
