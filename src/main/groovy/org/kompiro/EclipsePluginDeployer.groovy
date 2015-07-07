package org.kompiro

import groovy.util.logging.Slf4j

/**
 * Created by kompiro on 2015/07/06.
 */
@Slf4j
class EclipsePluginDeployer {
    static String ECLIPSE_BASE = '/Applications/Eclipse.app/Contents/Eclipse'

    PomGenerator pomGenerator = new PomGenerator()
    EclipsePlugins plugins

    EclipsePluginDeployer(String base){
        log.info("target eclipse : {}",base)
        this.plugins = new EclipsePlugins(base)
    }

    def deploy(String pluginName) {
        log.info "start Eclipse Plugin Deployer"
        def plugin = plugins.get(pluginName)
        def binding = plugin.binding()
        def generated = pomGenerator.generate binding
        log.info generated
    }

    static def main(String[] args) {
        def deployer = new EclipsePluginDeployer(ECLIPSE_BASE)
        deployer.deploy('org.eclipse.core.runtime')
    }
}
