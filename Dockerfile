FROM hseeberger/scala-sbt

COPY . /usr/app
WORKDIR /usr/app

RUN sbt stage

#ENTRYPOINT [ "./target/universal/stage/bin/CalGeneratorBack", "-Dapplication.secret=GrosSecret", "-Dhttp.port=80" ]
CMD [ "./target/universal/stage/bin/calgeneratorback", "-Dapplication.secret=GrosSecret", "-Dhttp.port=80" ]
EXPOSE 80