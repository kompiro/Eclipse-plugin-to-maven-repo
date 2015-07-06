package org.kompiro

import java.nio.file.Path
import java.util.jar.JarFile

/**
 * Created by kompiro on 2015/07/06.
 */
class EclipsePlugin {

    Path pluginPath
    String name
    String version

    EclipsePlugin(Path pluginPath){
        this.pluginPath = pluginPath
        def jarFile = new JarFile(pluginPath.toFile())
        def manifest = jarFile.getManifest()
        def main = manifest.getMainAttributes()
        this.name = main.getValue("Bundle-SymbolicName").split(';')[0]
        this.version = main.getValue("Bundle-Version")
s    }

}
