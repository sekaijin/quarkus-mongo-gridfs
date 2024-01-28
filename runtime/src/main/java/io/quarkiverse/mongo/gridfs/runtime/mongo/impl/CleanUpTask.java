package io.quarkiverse.mongo.gridfs.runtime.mongo.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import io.quarkus.scheduler.Scheduled;
import io.quarkus.scheduler.ScheduledExecution;

@ApplicationScoped
public class CleanUpTask {

    @Inject
    FileDataRepository fileDataRepository;

    @Scheduled(every = "{file.clean.period}")
    public void clean(ScheduledExecution execution) {
        fileDataRepository.cleanUp();
        System.out.println("clean data at: " + execution.getScheduledFireTime());
    }
}
