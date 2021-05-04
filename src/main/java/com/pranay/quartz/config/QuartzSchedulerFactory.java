package com.pranay.quartz.config;

import org.quartz.Scheduler;
import org.quartz.impl.DirectSchedulerFactory;
import org.quartz.impl.jdbcjobstore.InvalidConfigurationException;
import org.quartz.impl.jdbcjobstore.JobStoreTX;
import org.quartz.simpl.SimpleThreadPool;
import org.quartz.utils.ConnectionProvider;
import org.quartz.utils.DBConnectionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

import static java.util.Objects.requireNonNull;


@Configuration
public class QuartzSchedulerFactory {

    public static final String INSTANCE_NAME = "quartzScheduler";
    public static final String INSTANCE_ID = INSTANCE_NAME + "1";
    public static final String DATA_SOURCE = "SHARED_DATA_SOURCE";

    private Scheduler scheduler;
    private final DataSource dataSource;

    @Autowired
    public QuartzSchedulerFactory(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public Scheduler getScheduler() throws InvalidConfigurationException {

        DBConnectionManager.getInstance().addConnectionProvider(DATA_SOURCE, new ConnectionProvider() {
            @Override
            public Connection getConnection() throws SQLException {
                return dataSource.getConnection();
            }

            @Override
            public void shutdown() throws SQLException {
            }

            @Override
            public void initialize() throws SQLException {
                requireNonNull(dataSource, "DataSource initialization failed");
            }
        });

        JobStoreTX jdbcJobStore = new JobStoreTX();
        jdbcJobStore.setInstanceName(INSTANCE_NAME);
        jdbcJobStore.setDataSource(DATA_SOURCE);
        jdbcJobStore.setDbRetryInterval(15000L);
        jdbcJobStore.setIsClustered(false);
        jdbcJobStore.setDriverDelegateClass("org.quartz.impl.jdbcjobstore.PostgreSQLDelegate");

        try {
            DirectSchedulerFactory.getInstance().createScheduler(INSTANCE_NAME, INSTANCE_ID,
                    new SimpleThreadPool(5, Thread.NORM_PRIORITY), jdbcJobStore);
            this.scheduler = DirectSchedulerFactory.getInstance().getScheduler(INSTANCE_NAME);

            scheduler.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.scheduler;
    }
}
