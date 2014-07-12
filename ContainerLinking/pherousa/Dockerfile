FROM tyntec/jdk7

# Set correct environment variables.
ENV HOME /root

# Regenerate SSH host keys. baseimage-docker does not contain any, so you
# have to do that yourself. You may also comment out this instruction; the
# init system will auto-generate one during boot.
RUN /etc/my_init.d/00_regen_ssh_host_keys.sh

# Use baseimage-docker's init system.
CMD ["/sbin/my_init"]

RUN mkdir -p /opt/pherousa
VOLUME /var/log
ADD build/libs/pherousa-1.0.0.jar /opt/pherousa/pherousa-1.0.0.jar
# ...put your own build instructions here...

RUN echo "#!/bin/bash\ncd /opt/pherousa && java -jar pherousa-1.0.0.jar &" >> /etc/my_init.d/pherousa
RUN chmod +x /etc/my_init.d/pherousa
RUN cat /etc/my_init.d/pherousa

EXPOSE 8080

# Clean up APT when done.
RUN apt-get clean && rm -rf /var/lib/apt/lists/* /tmp/* /var/tmp/*

