package io.github.takzhanov.umbrella.hw04;

import java.util.Deque;
import java.util.concurrent.LinkedBlockingDeque;


/**
 * Написать приложение, которое следит за сборками мусора и пишет в лог количество сборок каждого типа (young, old) и время которое ушло на сборки в минуту.
 * Добиться OutOfMemory в этом приложении через медленное подтекание по памяти (например добавлять элементы в List и удалять только половину).
 * Настроить приложение (можно добавлять Thread.sleep(...)) так чтобы оно падало с OOM примерно через 5 минут после начала работы.
 * Собрать статистику (количество сборок, время на сборрки) по разным типам GC.
 */
public class Demo {
    public static void main(String[] args) {
        Deque<String> memory = new LinkedBlockingDeque<>();
        long start = System.nanoTime();
        int i = 0;
        try {
            while (true) {
                memory.addLast(String.valueOf(i++));
                if (i % 1000 == 0) {
                    for (int j = memory.size() / 5; j > 0; j--) {
                        memory.removeFirst();
                    }
                }
            }
        } catch (OutOfMemoryError error) {
            long finish = System.nanoTime();
            System.out.println(String.format("%s ms", (finish - start) / 1_000_000));
        }
    }
}
