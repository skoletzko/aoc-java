FROM gradle:8.5-jdk21

WORKDIR /workspace
ENV GRADLE_USER_HOME=/home/gradle/.gradle

COPY docker/entrypoint.sh /usr/local/bin/aoc-entrypoint
RUN chmod +x /usr/local/bin/aoc-entrypoint

ENTRYPOINT ["/usr/local/bin/aoc-entrypoint"]
CMD ["gradle", "--version"]
