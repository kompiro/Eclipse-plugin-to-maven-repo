package org.kompiro

import spock.lang.Specification

import java.nio.file.Path
import java.nio.file.Paths

/**
 * Created by kompiro on 2015/07/07.
 */
class DeployShGeneratorTest extends Specification {

    DeployShGenerator sut

    def setup(){
        def pluginsPath = Paths.get(EclipsePluginDeployer.ECLIPSE_BASE,'plugins')
        def deployUrl = new URL('http://nexus.kompiro.org/content/repositories/thirdparty/')
        def repositoryId = 'nexus.kompiro.org'
        this.sut = new DeployShGenerator(pluginsPath, deployUrl, repositoryId)

    }

    def "generate"(){
        when:
        def binding = [
                name: "org.kompiro.plugin",
                version: '1.0.0',
                dependencies:
                        [[
                                 name: 'org.eclipse.core.runtime',
                                 version: '3.11.0'
                         ],[
                                 name: 'org.ecipse.osgi',
                                 version: '3.10.0'
                         ]]
        ]
        then:
        def generated = sut.generate(binding)
        generated != null
        println generated
    }

}
