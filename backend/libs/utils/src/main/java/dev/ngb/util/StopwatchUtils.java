package dev.ngb.util;

import lombok.experimental.UtilityClass;

import java.time.Duration;
import java.time.Instant;
import java.util.function.LongConsumer;
import java.util.function.Supplier;

@UtilityClass
public final class StopwatchUtils {

    public static <T> T measure(Supplier<T> supplier, LongConsumer costConsumer) {
        Instant start = Instant.now();
        try {
            return supplier.get();
        } finally {
            long costMillis = Duration.between(start, Instant.now()).toMillis();
            costConsumer.accept(costMillis);
        }
    }

}
