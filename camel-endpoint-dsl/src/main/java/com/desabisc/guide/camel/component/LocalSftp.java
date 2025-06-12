package com.desabisc.guide.camel.component;

import org.apache.camel.Component;
import org.apache.camel.component.file.remote.SftpComponent;

public class LocalSftp {
    public Component localSftp(){
        SftpComponent localSftp = new SftpComponent();
        // add config for localSftp
        return new SftpComponent();
    }
}
