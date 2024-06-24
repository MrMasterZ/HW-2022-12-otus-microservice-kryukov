FROM liquibase/liquibase:4.9.1
RUN mkdir -p /liquibase
COPY ./liquibase /liquibase
EXPOSE 5432
ENTRYPOINT ["tail", "-f", "/dev/null"]
