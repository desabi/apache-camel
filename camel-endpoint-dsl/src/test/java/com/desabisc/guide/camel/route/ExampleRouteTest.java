package com.desabisc.guide.camel.route;

import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.MountableFile;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
class ExampleRouteTest {

    @Container
    private final GenericContainer<?> sftpContainer = new GenericContainer<>("atmoz/sftp:alpine")
            .withExposedPorts(22)
            .withCopyToContainer(MountableFile.forClasspathResource("it/ssh_host", 0777),
            "/etc/ssh/ssh_host_ed25519_key")
            .withCommand("user:password::/tmp/files");
}