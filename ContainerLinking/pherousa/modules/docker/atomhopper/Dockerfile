# Use phusion/baseimage as base image. To make your builds reproducible, make
# sure you lock down to a specific version, not to `latest`!
# See https://github.com/phusion/baseimage-docker/blob/master/Changelog.md for
# a list of version numbers.
FROM phusion/baseimage:0.9.10

# Set correct environment variables.
ENV HOME /root

# Regenerate SSH host keys. baseimage-docker does not contain any, so you
# have to do that yourself. You may also comment out this instruction; the
# init system will auto-generate one during boot.
RUN /etc/my_init.d/00_regen_ssh_host_keys.sh

# Use baseimage-docker's init system.
CMD ["/sbin/my_init"]

RUN apt-get update
RUN apt-get install -y tomcat7
RUN apt-get install -y wget

RUN mkdir /etc/atomhopper
RUN mkdir /var/log/atomhopper
RUN mkdir /opt/atomhopper && chmod -R 777 /opt/atomhopper
RUN wget http://maven.research.rackspacecloud.com/content/repositories/releases/org/atomhopper/atomhopper/1.2.22/atomhopper-1.2.22.war  -O /var/lib/tomcat7/webapps/atomhopper.war

ADD application-context.xml /etc/atomhopper/
ADD atom-server.cfg.xml /etc/atomhopper/
ADD logback.xml /etc/atomhopper/
ADD tomcat7Start.sh /etc/my_init.d/01tomcat7Start.sh

EXPOSE 8080

# Clean up APT when done.
RUN apt-get clean && rm -rf /var/lib/apt/lists/* /tmp/* /var/tmp/* 
