package org.kompiro

import spock.lang.Specification

/**
 * Created by kompiro on 2015/07/06.
 */
class PomGeneratorTest extends Specification {

    def sut = new PomGenerator()

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
        println generated
    }
}
