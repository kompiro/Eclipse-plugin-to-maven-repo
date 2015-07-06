package org.kompiro

/**
 * Created by kompiro on 2015/07/06.
 */
class EclipsePluginsTest extends spock.lang.Specification {

    def sut = new EclipsePlugins(EclipsePluginDeployer.ECLIPCE_BASE)

    def "valid path has plugins directories"() {
        expect:
        sut.valid == true
    }

    def "got plugins list."() {
        expect:
        sut.list().size() != 0
    }

    def "got null when the plugin is not exist."() {
        when:
        sut.get("");
        then:
        thrown(AssertionError)
    }

    def "got file when the plugin is exist."() {
        when:
        def plugin = sut.get("org.eclipse.core.runtime")
        then:
        plugin instanceof EclipsePlugin
    }

}
