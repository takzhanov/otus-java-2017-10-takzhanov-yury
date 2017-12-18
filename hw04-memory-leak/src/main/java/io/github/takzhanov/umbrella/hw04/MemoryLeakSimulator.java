package io.github.takzhanov.umbrella.hw04;

import com.sun.management.GarbageCollectionNotificationInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.management.NotificationEmitter;
import javax.management.openmbean.CompositeData;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.LinkedBlockingDeque;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summarizingLong;

/**
 * Логгировать активность GC (количетство сборок в минуту, время паузы в минуту)
 */
public class MemoryLeakSimulator {
    private final static Logger LOGGER = LoggerFactory.getLogger(MemoryLeakSimulator.class);
    private final static List<GarbageCollectionNotificationInfo> gcNotifications = new ArrayList<>();

    public static void main(String[] args) {
        installGCMonitoring();
        generateMemoryLeak();
        analyzeGcEvents();
    }

    private static void analyzeGcEvents() {
        gcNotifications.stream().collect(
                groupingBy(GarbageCollectionNotificationInfo::getGcName,
                        groupingBy(notificationInfo -> notificationInfo.getGcInfo().getStartTime() / 60 / 1000,
                                summarizingLong(notificationInfo -> notificationInfo.getGcInfo().getDuration()))))
                .forEach((gcName, events) -> {
                    System.out.println("|-------------------------------|");
                    System.out.println(String.format("| %-30s|", gcName));
                    System.out.println("|-------------------------------|");
                    System.out.println("| min # | gc call | gc duration |");
                    System.out.println("|-------------------------------|");
                    events.forEach((minute, stat) -> {
                        System.out.println(String.format("|%6d |%8d |%10dms |",
                                minute, stat.getCount(), stat.getSum()));
                    });
                    System.out.println("|-------------------------------|");
                });
    }

    public static void generateMemoryLeak() {
        Deque<String> memory = new LinkedBlockingDeque<>();
        LOGGER.debug("Running...");
        final long start = System.nanoTime();
        long i = 0;
        try {
            while (true) {
                memory.push(UUID.randomUUID().toString());
                memory.pop();
                memory.push(UUID.randomUUID().toString());
                i++;
                if (i % 100 == 0) {
                    try {
                        Thread.sleep(60);
                    } catch (InterruptedException ignored) {
                    }
                }
            }
        } catch (OutOfMemoryError error) {
            final long totalTime = System.nanoTime() - start;
            memory.clear();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {
            }
            LOGGER.debug(String.format("OutOfMemoryError in %s ms, %d iterations", totalTime / 1_000_000, i));
        }
    }

    public static void installGCMonitoring() {
        for (GarbageCollectorMXBean gcBean : ManagementFactory.getGarbageCollectorMXBeans()) {
            ((NotificationEmitter) gcBean).addNotificationListener(
                    (notification, handback) -> {
                        GarbageCollectionNotificationInfo info = GarbageCollectionNotificationInfo.from((CompositeData) notification.getUserData());
                        gcNotifications.add(info);
                    },
                    notification -> GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION.equals(notification.getType()),
                    null
            );
        }
    }
}
