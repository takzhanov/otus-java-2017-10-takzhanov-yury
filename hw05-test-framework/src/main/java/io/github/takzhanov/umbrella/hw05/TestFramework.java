package io.github.takzhanov.umbrella.hw05;

import com.google.common.reflect.ClassPath;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

import static io.github.takzhanov.umbrella.hw05.ReflectionHelper.getMethodsAnnotatedWith;

public class TestFramework {
    public static void testClass(String className) {
        try {
            Class clazz = Class.forName(className);
            testClass(clazz);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void testClass(Class<?> clazz) {
        List<Method> beforeMethods = getMethodsAnnotatedWith(clazz, Before.class);
        List<Method> testMethods = getMethodsAnnotatedWith(clazz, Test.class);
        List<Method> afterMethods = getMethodsAnnotatedWith(clazz, After.class);

        for (Method testMethod : testMethods) {
            Object testingObject = ReflectionHelper.instantiate(clazz);
            for (Method beforeMethod : beforeMethods) {
                ReflectionHelper.callMethod(testingObject, beforeMethod.getName());
            }
            ReflectionHelper.callMethod(testingObject, testMethod.getName());
            for (Method afterMethod : afterMethods) {
                ReflectionHelper.callMethod(testingObject, afterMethod.getName());
            }
        }
    }

    public static void testPackage(String packageName) {
        try {
            ClassPath.from(TestFramework.class.getClassLoader()).getTopLevelClasses(packageName).stream()
                    .map(ClassPath.ClassInfo::getName)
                    .forEach(TestFramework::testClass);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        System.out.println("test TestSample1");
        testClass(TestSample1.class.getName());
        System.out.println("test TestSample2");
        testClass(TestSample2.class);
        System.out.println("test io.github.takzhanov.umbrella.hw05");
        testPackage("io.github.takzhanov.umbrella.hw05");
    }
}
