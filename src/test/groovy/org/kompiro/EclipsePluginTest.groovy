package org.kompiro

import java.nio.file.Paths

/**
 * Created by kompiro on 2015/07/06.
 */
class EclipsePluginTest extends spock.lang.Specification {

    def sut = new EclipsePlugin(Paths.get("/Applications/Eclipse.app/Contents/Eclipse/plugins/org.eclipse.core.runtime_3.11.0.v20150405-1723.jar"))

    def "name" () {
        expect:
        sut.name == "org.eclipse.core.runtime"
    }

    def "version" (){
        expect:
        sut.version == '3.11.0.v20150405-1723'
    }
}
