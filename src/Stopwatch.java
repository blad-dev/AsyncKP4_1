import java.time.Duration;
import java.time.Instant;

public class Stopwatch {
    protected Instant startTime;
    protected boolean stopwatchWasStarted;

    public Stopwatch() {
        this.startTime = null;
        this.stopwatchWasStarted = false;
    }

    public Stopwatch(Stopwatch watch) {
        this.startTime = watch.startTime;
        this.stopwatchWasStarted = watch.stopwatchWasStarted;
    }

    public void startStopwatch() {
        if (stopwatchWasStarted)
            throw new IllegalStateException("Stopwatch was already started, you must reset it at first!");
        stopwatchWasStarted = true;
        startTime = Instant.now();
    }

    public Duration stopStopwatchAndGetDuration() {
        Instant endTime = Instant.now();
        if (!stopwatchWasStarted)
            throw new IllegalStateException("Stopwatch was not started so it cannot be stopped, you must start it first!");
        stopwatchWasStarted = false;
        return Duration.between(startTime, endTime);
    }

    public void resetStopwatch() {
        if (!stopwatchWasStarted)
            throw new IllegalStateException("Stopwatch was not started so it cannot be reset, you must start it first!");
        stopwatchWasStarted = false;
    }

    public String stopAndGetFormatedTime(){
        Duration timeDuration = stopStopwatchAndGetDuration();
        return "%.3f".formatted(timeDuration.toNanos() / 1_000_000_000.);
    }
}
