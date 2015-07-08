package org.kompiro

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import org.antlr.v4.runtime.ANTLRInputStream
import org.antlr.v4.runtime.CommonTokenStream

import java.nio.file.Path
import java.util.jar.JarFile

/**
 * Created by kompiro on 2015/07/06.
 */
@EqualsAndHashCode
@ToString
class EclipsePlugin {

    @ToString
    class Require {

        String name
        String version

        Require(String name, String version) {
            this.name = name
            this.version = version
        }

    }

    String name
    String version
    String fileVersion
    List<Require> dependencies = [];
    Path pluginPath

    EclipsePlugin(Path pluginPath) {
        this.pluginPath = pluginPath
        def jarFile = new JarFile(pluginPath.toFile())
        def manifest = jarFile.getManifest()
        def main = manifest.getMainAttributes()
        this.name = main.getValue("Bundle-SymbolicName").split(';')[0]
        def bundleVersion = main.getValue("Bundle-Version")
        def lastPeriod = bundleVersion.lastIndexOf('.')
        this.version = bundleVersion.substring(0, lastPeriod)
        this.fileVersion = bundleVersion
        def depends = main.getValue("Require-Bundle")
        parseDependencies(depends)

    }

    def binding() {
        def binding_dependencies = dependencies.collect {
            [
                    name   : it.name,
                    version: it.version
            ]
        }
        return [name        : name,
                version     : version,
                fileVersion : fileVersion,
                dependencies: binding_dependencies]
    }

    def parseDependencies(String depends) {
        if (depends == null) return
        def stream = new ANTLRInputStream(depends)
        def lexer = new BundleDependencyLexer(stream)
        def tokens = new CommonTokenStream(lexer)
        def parser = new BundleDependencyParser(tokens)
        parser.bundles().bundle().each {
            def target_name = it.ID().text
            def bundle_version = it.bundle_options().bundle_version()[0].version_range().text
            def require = new Require(target_name, bundle_version)
            dependencies.add(require)
        }
    }
}
