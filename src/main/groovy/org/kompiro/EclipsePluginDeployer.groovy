package org.kompiro
/**
 * Created by kompiro on 2015/07/06.
 */
class EclipsePluginDeployer {
    static String ECLIPCE_BASE = '/Applications/Eclipse.app/Contents/Eclipse'

    static def main(String[] args) {
        def plugins = new EclipsePlugins(EclipsePluginDeployer.ECLIPSE_BASE);

        println "start Eclipse Plugin Deployer"
        println "target eclipse: ${EclipsePluginDeployer.ECLIPCE_BASE}"
    }
}
