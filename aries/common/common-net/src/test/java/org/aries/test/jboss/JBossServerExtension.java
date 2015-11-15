package org.aries.test.jboss;

import org.jboss.arquillian.container.spi.ServerKillProcessor;
import org.jboss.arquillian.core.spi.LoadableExtension;


public class JBossServerExtension implements LoadableExtension {

    public void register(ExtensionBuilder builder) {
        builder.service(ServerKillProcessor.class, JBossServerKillProcessor.class);
    }
    
}
