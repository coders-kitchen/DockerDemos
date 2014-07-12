FROM mr_paeddah/interactive_node_js
ADD src /src
RUN npm cache clean
RUN cd /src; ls -al; npm install
EXPOSE 8080
ENTRYPOINT ["node", "/src/index.js"]
CMD ["/bin/bash"]
