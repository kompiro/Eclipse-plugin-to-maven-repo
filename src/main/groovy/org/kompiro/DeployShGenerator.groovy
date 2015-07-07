package org.kompiro

import groovy.text.GStringTemplateEngine

import java.nio.file.Path
import java.nio.file.Paths

/**
 * Created by kompiro on 2015/07/07.
 */
class DeployShGenerator {

    def engine = new GStringTemplateEngine()

    def TEMPLATE_SCRIPT = '''
#!/usr/bin/env sh
mvn org.apache.maven.plugins:maven-deploy-plugin:2.8.2:deploy-file \\\\
  -Durl=${deployURL} \\\\
  -DrepositoryId=${repositoryId} \\\\
  -Dfile=${pluginsDir}/${name}_${version}.jar \\\\
  -Dsources=${pluginsDir}/${name}.source_${version}.jar \\\\
  -DpomFile=${name}_pom.xml
'''

    Path pluginsPath
    URL deployURL
    String repositoryId

    DeployShGenerator(Path pluginsPath,URL deployURL,String repositoryId){
        this.pluginsPath = pluginsPath
        this.deployURL = deployURL
        this.repositoryId = repositoryId
    }

    def generate(Map binding) {
        binding.put('pluginsDir',pluginsPath.toAbsolutePath().toString())
        binding.put('deployURL',this.deployURL)
        binding.put('repositoryId',repositoryId)
        def template = engine.createTemplate(TEMPLATE_SCRIPT)
        return template.make(binding).toString()
    }
}
