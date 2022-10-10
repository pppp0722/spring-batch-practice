package com.pppp0722.springbatchpractice;

import static org.springframework.batch.repeat.RepeatStatus.FINISHED;

import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class JobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job job() {
        return jobBuilderFactory.get("job")
            .start(step1())
            .next(step2())
            .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
            .tasklet((stepContribution, chunkContext) -> {
                JobParameters jobParameters
                    = stepContribution.getStepExecution().getJobParameters();
                String name = jobParameters.getString("name");
                Date date = jobParameters.getDate("date");

                System.out.println(name + ", " + date);
                return FINISHED;
            })
            .build();
    }

    @Bean
    public Step step2() {
        return stepBuilderFactory.get("step2")
            .tasklet((stepContribution, chunkContext) -> {
                System.out.println("step2 was executed");
                return FINISHED;
            })
            .build();
    }
}
