package org.kompiro

import groovy.util.logging.Slf4j

/**
 * Created by kompiro on 2015/07/06.
 */
@Slf4j
class EclipsePlugins {

    String base
    File pluginPath
    boolean valid

    EclipsePlugins(String eclipseBase) {
        this.base = eclipseBase
        this.pluginPath = new File(eclipseBase, "plugins")
        valid = pluginPath.exists()
    }

    def list() {
        return pluginPath.listFiles()
    }

    def get(String pluginName) {
        assert pluginName != ""
        def candidates = list().findAll { it.name.startsWith("${pluginName}_") }
        assert candidates.size() == 1
        def pluginPath = candidates.get(0).toPath()
        return new EclipsePlugin(pluginPath)
    }
}
