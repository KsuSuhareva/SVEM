package by.suhareva.svem.worker;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


@Component
@RequiredArgsConstructor
@Slf4j
public class StarterWorker {

    private final Worker worker;
    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    @PostConstruct
    public void start() {
        log.info("Worker started");
        executorService.submit(worker);
    }

    @PreDestroy
    public void close() {
        executorService.shutdown();
        log.info("Worker close");
    }


}
