package br.helis.architecture.notifications.service;

import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.RetryListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomRetryListener implements RetryListener {

    private static final Logger logger = LoggerFactory.getLogger(CustomRetryListener.class);

    @Override
    public <T, E extends Throwable> void close(RetryContext context, RetryCallback<T, E> callback, 
                                                Throwable throwable) {
        if (throwable != null) {
            logger.error("Retry failed after {} attempts", context.getRetryCount());
        }
    }

    @Override
    public <T, E extends Throwable> boolean open(RetryContext context, RetryCallback<T, E> callback) {
        logger.info("Starting retry process...");
        return true;
    }

    @Override
    public <T, E extends Throwable> void onError(RetryContext context, RetryCallback<T, E> callback, 
                                                  Throwable throwable) {
        logger.warn("Retry attempt {} failed with error: {}", context.getRetryCount(), throwable.getMessage());
    }
}
