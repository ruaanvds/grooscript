/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.grooscript.convert

import org.grooscript.convert.util.RequireJsDependency
import org.grooscript.test.ConversionMixin
import org.grooscript.util.GrooScriptException
import spock.lang.Specification

import static org.grooscript.util.Util.LINE_SEPARATOR

@Mixin([ConversionMixin])
class GsConverterSpec extends Specification {

    void 'test basic conversion'() {
        expect:
        converter.toJs(BASIC_CLASS).startsWith '''function A() {'''
    }

    void 'convert as a require.js module add spaces'() {
        expect:
        converter.toJs(BASIC_CLASS, conversionOptionsWithRequireJs()).startsWith("  ${LINE_SEPARATOR}  function A() {")
    }

    void 'initialize require.js modules (ast) converting as require.js module'() {
        expect:
        converter.toJs(CLASS_WITH_REQUIRE_MODULE, conversionOptionsWithRequireJs()).contains('gSobject.module = module;')
        converter.requireJsDependencies == [new RequireJsDependency(path: 'any/path', name: 'module')]
    }

    void 'ignore require.js modules (ast) converting normal'() {
        expect:
        converter.toJs(CLASS_WITH_REQUIRE_MODULE).contains('gSobject.module = null;')
        converter.requireJsDependencies == []
    }

    private static final BASIC_CLASS = 'class A {}'
    private static final CLASS_WITH_REQUIRE_MODULE = '''class A {
    @org.grooscript.asts.RequireJsModule(path = "any/path")
    def module
}'''

    void 'add requireJs dependencies'() {
        expect:
        converter.requireJsDependencies == []

        when:
        converter.addRequireJsDependency(PATH, NAME)

        then:
        converter.requireJsDependencies == [new RequireJsDependency(path: PATH, name: NAME)]
    }

    void 'incompatible conversion options'() {
        given:
        def conversionOptions = [:]
        conversionOptions.put(ConversionOptions.REQUIRE_JS_MODULE.text, true)
        conversionOptions.put(ConversionOptions.INCLUDE_DEPENDENCIES.text, true)

        when:
        converter.toJs('println "Hola!"', conversionOptions)

        then:
        def e = thrown(GrooScriptException)
        e.message == "Incompatible conversion options (${ConversionOptions.REQUIRE_JS_MODULE.text} " +
                "- ${ConversionOptions.INCLUDE_DEPENDENCIES.text})"
    }

    private GsConverter converter = new GsConverter()

    private static final PATH = 'path'
    private static final NAME = 'name'

    private Map conversionOptionsWithRequireJs() {
        Map conversionOptions = [:]
        conversionOptions[ConversionOptions.REQUIRE_JS_MODULE.text] = true
        conversionOptions
    }
}
