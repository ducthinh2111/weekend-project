import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) {
        Callable<String> timeoutExecutor = () -> {
            TimeUnit.MILLISECONDS.sleep(3000);
            return "timeout";
        };

        Callable<String> theTask = () -> {
            TimeUnit.MILLISECONDS.sleep(2000);
            return "Task result";
        };

        try (ExecutorService executorService = Executors.newFixedThreadPool(2)) {
            Future<String> timeoutFuture = executorService.submit(timeoutExecutor);
            Future<String> theTaskFuture = executorService.submit(theTask);
            boolean isTerminate = false;
            while (!isTerminate) {
                if (theTaskFuture.isDone()) {
                    isTerminate = true;
                    System.out.println(theTaskFuture.get());
                } else if (timeoutFuture != null && timeoutFuture.isDone()) {
                    System.out.println(timeoutFuture.get());
                    timeoutFuture = null;
                }
            }
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}