package com.desabisc.guide.camel.route;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.builder.endpoint.dsl.SftpEndpointBuilderFactory;

import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.sftp;

public class ExampleRoute extends RouteBuilder {

    /*
    @Override
    public void configure() throws Exception {
       /*from("localSftp:{{sftp.host}}:{{sftp.port}}//{{sftp.directory}}?" +
                "username={{sftp.username}}" +
                "&password={{sftp.password}}" +
                "&knownHostsFile={{sftp.knownHosts}}" +
                "&moveFailed=.error" +
                "&fileName=hello.txt")
        from(sftp("localSftp", "{{sftp.host}}:{{sftp.port}}//{{sftp.directory}}")
                .username("{{sftp.username}}")
                .password("sftp.password")
                .knownHostsFile("sftp.knownHosts")
                .moveFailed(".error")
                .fileName("hello.txt"))
                .log("Read document: ${header. " + Exchange.FILE_NAME + "}")
                .to("localSftp:{{sftp.host}}::{{sftp.port}}//{{sftp.directory}}?" +
                        "username={{sftp.username}}" +
                        "&password={{sftp.password}}" +
                        "&knownHostsFile={{sftp.knownHosts}}" +
                        "&fileName=done.txt");
    }*/

    public void configure() throws Exception {
        from(sftpEndpointBuilder()
                .moveFailed(".error")
                .fileName("hello.txt"))
                .log("Read document: ${header. " + Exchange.FILE_NAME + "}")
                .to(sftpEndpointBuilder().fileName("done.txt"));

    }

    private SftpEndpointBuilderFactory.SftpEndpointBuilder sftpEndpointBuilder() {
        return sftp("localSftp", "{{sftp.host}}:{{sftp.port}}//{{sftp.directory}}")
                .username("{{sftp.username}}")
                .password("sftp.password")
                .knownHostsFile("sftp.knownHosts");
    }
}
