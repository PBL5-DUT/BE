package com.pbl.pbl_be.scheduler;

import com.pbl.pbl_be.service.impl.ProjectSchedulerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProjectStatusScheduler {

    private final ProjectSchedulerService projectSchedulerService;

    // Chạy hàng ngày lúc 00:01 (1 phút sau nửa đêm)
    @Scheduled(cron = "1 0 0 * * ?")
    public void updateExpiredProjectsDaily() {
        log.info("Starting daily project status update task");
        try {
            projectSchedulerService.updateExpiredProjectsStatus();
            log.info("Daily project status update task completed successfully");
        } catch (Exception e) {
            log.error("Daily project status update task failed", e);
        }
    }

    // Tùy chọn: Chạy mỗi giờ để kiểm tra thường xuyên hơn
    @Scheduled(fixedRate = 3600000) // 1 giờ = 3600000ms
    public void updateExpiredProjectsHourly() {
        log.info("Starting hourly project status update task");
        try {
            projectSchedulerService.updateExpiredProjectsStatus();
            log.info("Hourly project status update task completed successfully");
        } catch (Exception e) {
            log.error("Hourly project status update task failed", e);
        }
    }
}