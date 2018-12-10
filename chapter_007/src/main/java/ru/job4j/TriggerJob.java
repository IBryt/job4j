package ru.job4j;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.storage.Config;
import ru.job4j.storage.StoreSQL;

import java.sql.SQLException;

public class TriggerJob implements Job {
    private static final Logger LOG = LoggerFactory.getLogger(TriggerJob.class.getName());

    @Override
    public void execute(JobExecutionContext context) {
        Config config = new Config();
        try (StoreSQL sql = new StoreSQL(config) {
            @Override
            public void execute() {
                new SQLExecute().execute();
            }
        }) {
            sql.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            LOG.error(e.getMessage(), e);
        }
    }
}
