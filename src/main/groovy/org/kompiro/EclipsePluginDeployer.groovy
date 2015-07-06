package org.kompiro
/**
 * Created by kompiro on 2015/07/06.
 */
class EclipsePluginDeployer {
    static String ECLIPSE_BASE = '/Applications/Eclipse.app/Contents/Eclipse'

    static def main(String[] args) {
        def plugins = new EclipsePlugins(ECLIPSE_BASE);

        println "start Eclipse Plugin Deployer"
        println "target eclipse: ${ECLIPSE_BASE}"

        def plugin = plugins.get("org.eclipse.core.runtime")
        def generator = new PomGenerator()
        println generator.generate(plugin.binding())
    }
}
