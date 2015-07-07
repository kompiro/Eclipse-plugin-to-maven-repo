package org.kompiro

import org.antlr.v4.runtime.CommonTokenStream
import org.antlr.v4.runtime.ANTLRInputStream

/**
 * Created by kompiro on 2015/07/06.
 */
class AntlrTest extends spock.lang.Specification{

    def "parse single bundle" () {
        given:
        def stream = new ANTLRInputStream('javax.annotation;bundle-version="1.1.0";')
        def lexer = new BundleDependencyLexer(stream)
        def tokens = new CommonTokenStream(lexer)
        def parser = new BundleDependencyParser(tokens)
        when:
        def bundles = parser.bundles()
        then:
        bundles.childCount == 1
        def bundle = bundles.bundle(0)
        bundle.ID().text == 'javax.annotation'
        bundle.bundle_options().bundle_version()[0].version_range().VERSION()[0].text == '1.1.0'
    }

    def "parse multi bundles" () {
        given:
        def actual_text = 'javax.annotation;bundle-version="1.1.0";resolution:=optional;visibility:=reexport,' +
                'javax.inject;bundle-version="1.0.0";resolution:=optional;visibility:=reexport'
        def stream = new ANTLRInputStream(actual_text)
        def lexer = new BundleDependencyLexer(stream)
        def tokens = new CommonTokenStream(lexer)
        def parser = new BundleDependencyParser(tokens)
        when:
        def bundles = parser.bundles()
        then:
        bundles.bundle().size() == 2
        def bundle = bundles.bundle(1)
        bundle.ID().text == 'javax.inject'
        bundle.bundle_options().bundle_version()[0].version_range().text == '1.0.0'
    }

    def "parse version range" () {
        given:
        def actual_text = 'org.eclipse.osgi;bundle-version="[3.7.0,4.0.0)";visibility:=reexport,' +
                'org.eclipse.equinox.common;bundle-version="[3.7.0,4.0.0)";visibility:=reexport,' +
                'org.eclipse.core.jobs;'
        def stream = new ANTLRInputStream(actual_text)
        def lexer = new BundleDependencyLexer(stream)
        def tokens = new CommonTokenStream(lexer)
        def parser = new BundleDependencyParser(tokens)
        when:
        def bundles = parser.bundles()
        then:
        bundles.bundle().size() == 3
        def bundle = bundles.bundle(0)
        bundle.ID().text == 'org.eclipse.osgi'
        bundle.bundle_options().bundle_version()[0].version_range().text == '[3.7.0,4.0.0)'
    }
}
