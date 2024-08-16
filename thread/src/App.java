import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class App {
    private static final int NUM_THREADS = 100000000; // Threads

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis(); // Start Timer

        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()); // Sử dụng pool thread
        AtomicInteger counter = new AtomicInteger(0); // AtomicInteger để đếm an toàn trong môi trường đa luồng

        CountDownLatch latch = new CountDownLatch(NUM_THREADS);

        for (int i = 0; i < NUM_THREADS; i++) {
            executor.submit(() -> {
                doWork(counter);
                latch.countDown(); // Giảm số lượng latch sau khi công việc hoàn tất
            });
        }

        try {
            latch.await(); // Chờ cho tất cả các thread hoàn thành
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            executor.shutdown(); // Đóng pool thread
        }

        long endTime = System.currentTimeMillis(); // Kết thúc đo thời gian
        long elapsedTime = endTime - startTime; // Tính toán thời gian đã trôi qua

        System.out.println("Finished all threads. Total completed: " + counter.get());
        System.out.println("Execution Time: " + elapsedTime + " ms");
    }

    private static void doWork(AtomicInteger counter) {
        // Giả lập công việc
        counter.incrementAndGet(); // Increment đa luồng
    }
}
