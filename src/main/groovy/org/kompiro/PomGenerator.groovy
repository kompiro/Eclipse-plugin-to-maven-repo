package org.kompiro

import groovy.text.GStringTemplateEngine

/**
 * Created by kompiro on 2015/07/06.
 */
class PomGenerator {
    GStringTemplateEngine engine = new GStringTemplateEngine()

    String TEMPLATE_POM_XML = '''
<project xmlns="http://maven.apache.org/POM/4.0.0"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/POM/4.0.0
                      http://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>
  <groupId>org.kompiro</groupId>
  <artifactId>${name}</artifactId>
  <version>${version}</version>
  <dependencies>
    ${dependencies}
  </dependencies>
</project>
'''

    String TEMPLATE_DEPENDENCY_POM =
            '''
    <dependency>
      <groupId>org.kompiro</groupId>
      <artifactId>${name}</artifactId>
      <version>$version</version>
    </dependency>
'''


    def generate(Map binding) {
        def dependencies = binding.get('dependencies')
        def generated = generateDependencies(dependencies)
        binding.put('dependencies', generated)
        def template = engine.createTemplate(TEMPLATE_POM_XML)
        return template.make(binding).toString()
    }

    def generateDependencies(List dependencies) {
        def generated = ''
        def template = engine.createTemplate(TEMPLATE_DEPENDENCY_POM);
        dependencies.each {
            generated += template.make(it).toString()
        }
        return generated
    }

}
