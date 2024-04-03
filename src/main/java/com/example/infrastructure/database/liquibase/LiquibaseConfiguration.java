package com.example.infrastructure.database.liquibase;

import com.example.domain.exception.ErrorsEnum;
import com.example.domain.exception.ProductException;
import liquibase.command.CommandResults;
import liquibase.command.CommandScope;
import liquibase.command.core.UpdateCommandStep;
import liquibase.command.core.helpers.DbUrlConnectionCommandStep;
import liquibase.exception.LiquibaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class LiquibaseConfiguration {
  @Value("${spring.data.mongodb.uri}")
  private String connectionUrl;

  @Bean
  public CommandResults liquibaseUpdate() {
    try {
      String changelogFile = "database/changelog/changelog-master.json";
      return new CommandScope("update")
          .addArgumentValue(UpdateCommandStep.CHANGELOG_FILE_ARG, changelogFile)
          .addArgumentValue(DbUrlConnectionCommandStep.URL_ARG, connectionUrl)
          .execute();
    } catch (LiquibaseException e) {
      log.error("Error running liquibase {}", e.getMessage());
      throw new ProductException(ErrorsEnum.INTERNAL_SERVER_ERROR, e);
    }
  }
}
