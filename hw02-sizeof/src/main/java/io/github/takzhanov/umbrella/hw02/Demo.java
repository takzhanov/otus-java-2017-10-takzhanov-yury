package io.github.takzhanov.umbrella.hw02;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.LinkedList;

public class Demo {
    private static final Logger LOGGER = LoggerFactory.getLogger(Demo.class);

    public static void main(String[] args) {
        printInfo(null);
        printInfo(new Object());
        printInfo("Empty array of Objects", new Object[0]);
        printInfo("Empty array of 1000 Objects", new Object[1000]);
        printInfo("Empty String", "");
        printInfo("\"Stringgggggg\"", "Stringgggggg");

        printInfo("int[0]", new int[0]);
        printInfo("int[1000]", new int[1000]);


        printInfo("Integer[0]", new Integer[0]);
        Integer[] integerArray = new Integer[1000];
        printInfo("Empty Integer[1000]", integerArray);
        for (int i = 0; i < integerArray.length; i++) {
            integerArray[i] = new Integer(77);
        }
        printInfo("Integer[1000] filled new Integer(77)", integerArray);
        for (int i = 0; i < integerArray.length; i++) {
            integerArray[i] = 77;
        }
        printInfo("Integer[1000] filled 77", integerArray);


        ArrayList<Integer> integerArrayList = new ArrayList<>(0);
        printInfo("Empty ArrayList<Integer>", integerArrayList);
        integerArrayList = new ArrayList<>(1000);
        printInfo("Empty ArrayList<Integer>(1000)", integerArrayList);
        for (int i = 0; i < 1000; i++) {
            integerArrayList.add(new Integer(77));
        }
        printInfo("ArrayList<Integer>(1000) filled new Integer(77)", integerArrayList);
        integerArrayList.clear();
        for (int i = 0; i < 1000; i++) {
            integerArrayList.add(77);
        }
        printInfo("ArrayList<Integer>(1000) filled 77", integerArrayList);


        LinkedList<Integer> integerLinkedList = new LinkedList<>();
        printInfo("Empty LinkedList<Integer>", integerLinkedList);
        for (int i = 0; i < 1000; i++) {
            integerLinkedList.add(new Integer(77));
        }
        printInfo("LinkedList<Integer>(1000) filled new Integer(77)", integerLinkedList);
        integerLinkedList.clear();
        for (int i = 0; i < 1000; i++) {
            integerLinkedList.add(77);
        }
        printInfo("LinkedList<Integer>(1000) filled 77", integerLinkedList);
    }

    public static void printInfo(final Object o) {
        final long objectSize = ObjectSizeAnalyzer.getDeepObjectSize(o);
        LOGGER.info("Size of {} is {} bytes", o == null ? "null" : o.getClass().getSimpleName(), objectSize);
    }

    public static void printInfo(final String title, final Object o) {
        final long objectSize = ObjectSizeAnalyzer.getDeepObjectSize(o);
        LOGGER.info("{} is {} bytes", o == null ? "null" : title, objectSize);
    }

}