package com.robosoftin.lorem_food_app.utility;
import java.util.concurrent.atomic.AtomicLong;
public class IdGeneratorUtility {

        private static AtomicLong counter = new AtomicLong(0);

        public static long nextId() {
            return counter.incrementAndGet();
        }
}
