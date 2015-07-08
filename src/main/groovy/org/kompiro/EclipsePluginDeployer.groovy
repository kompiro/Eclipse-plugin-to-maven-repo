package org.kompiro

import groovy.util.logging.Slf4j

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

/**
 * Created by kompiro on 2015/07/06.
 */
@Slf4j
class EclipsePluginDeployer {
    static final String ECLIPSE_BASE = '/Applications/Eclipse.app/Contents/Eclipse'
    static final String DEPLOY_URL = 'http://nexus.kompiro.org/content/repositories/thirdparty/'
    static final String REPOSITORY_ID = 'nexus.kompiro.org'
    static final Path GENERATE_PATH = Paths.get('/tmp/', 'generated')

    PomGenerator pomGenerator = new PomGenerator()
    DeployShGenerator bashGenerator
    EclipsePlugins plugins
    Path generatePath
    Path deployShPath
    def generatedPlugin = []

    EclipsePluginDeployer(String eclipseBase, Path generatePath) {
        log.info("target eclipse : {}", eclipseBase)
        this.plugins = new EclipsePlugins(eclipseBase)
        this.generatePath = generatePath
        def pluginsPath = Paths.get(ECLIPSE_BASE, 'plugins')
        def deployUrl = new URL(DEPLOY_URL)
        def repositoryId = REPOSITORY_ID
        this.bashGenerator = new DeployShGenerator(pluginsPath, deployUrl, repositoryId)
    }

    def deploy(String pluginName) {
        log.info "start Eclipse Plugin Deployer"
        if (!Files.exists(generatePath)) {
            Files.createDirectories(generatePath)
        }
        deployShPath = generatePath.resolve('deploy.sh')
        deployShPath.write('#!/usr/bin/env bash\n\n')
        def topPlugin = plugins.get(pluginName)
        generate(topPlugin)
    }

    def generate(EclipsePlugin plugin) {
        if (generatedPlugin.contains(plugin.name)) return
        log.debug("Generate : $plugin.name")
        generatedPlugin.add(plugin.name)
        deployShPath.append("sh ${plugin.name}.sh\n")
        generatePom(plugin)
        generateDeployScript(plugin)
        plugin.dependencies.each {
            log.debug("  Depends : ${it.name}")
            def dependPlugin = plugins.get(it.name)
            generate(dependPlugin)
        }
    }

    def generateDeployScript(EclipsePlugin plugin) {
        def deployShellPath = generatePath.resolve("${plugin.name}.sh")
        log.debug("Generate script : $deployShellPath")
        def binding = plugin.binding()
        def generated = bashGenerator.generate binding
        deployShellPath.write(generated)
    }

    def generatePom(EclipsePlugin plugin) {
        def pluginPomPath = generatePath.resolve("${plugin.name}_pom.xml")
        log.debug("Generate pom : $pluginPomPath")
        def binding = plugin.binding()
        def generated = pomGenerator.generate binding
        pluginPomPath.write(generated)
    }

    static def main(String[] args) {
        def deployer = new EclipsePluginDeployer(ECLIPSE_BASE, GENERATE_PATH)
        deployer.deploy('org.eclipse.uml2.uml')
    }
}
