package org.kompiro

import groovy.util.logging.Slf4j

import java.nio.file.Path
import java.nio.file.Paths

/**
 * Created by kompiro on 2015/07/06.
 */
@Slf4j
class EclipsePluginDeployer {
    static final String ECLIPSE_BASE = '/Applications/Eclipse.app/Contents/Eclipse'
    static Path GENERATE_PATH = Paths.get('/tmp/','generated')

    PomGenerator pomGenerator = new PomGenerator()
    EclipsePlugins plugins
    Path generatePath

    EclipsePluginDeployer(String eclipseBase, Path generatePath){
        log.info("target eclipse : {}",eclipseBase)
        this.plugins = new EclipsePlugins(eclipseBase)
        this.generatePath = generatePath
    }

    def deploy(String pluginName) {
        log.info "start Eclipse Plugin Deployer"
        if(!generatePath.toFile().exists()) {
            generatePath.toFile().mkdirs()
        }
        def plugin = plugins.get(pluginName)
        generatePom(plugin)
    }

    def generatePom(EclipsePlugin plugin){
        def pluginPomPath = generatePath.resolve("${plugin.name}_pom.xml")
        log.info("Generate pom : $pluginPomPath")
        def binding = plugin.binding()
        def generated = pomGenerator.generate binding
        pluginPomPath.write(generated)
    }

    static def main(String[] args) {
        def deployer = new EclipsePluginDeployer(ECLIPSE_BASE,GENERATE_PATH)
        deployer.deploy('org.eclipse.core.runtime')
    }
}
