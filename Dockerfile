FROM jangrewe/gitlab-ci-android

RUN apt-get -qq update \
    && apt-get remove openjdk-11* -qqy \
    && apt-get install -qqy --no-install-recommends \
        openjdk-8-jdk \
    && rm -rf /var/lib/apt/lists/* /tmp/* /var/tmp/*