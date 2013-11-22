package org.grooscript

import org.grooscript.test.ConversionMixin
import spock.lang.Specification

/**
 * User: jorgefrancoleza
 * Date: 22/11/13
 */
@Mixin([ConversionMixin])
class TestWeb extends Specification {

    def 'test prefix and postfix undefined'() {
        expect:
        !readAndConvert('web/PrePostFix', false, [:], 'a = 0;', 'var a=0,b=0;func();gs.assert(a==1);' +
                'gs.assert(b==-1);gs.assert(c==1);gs.assert(d==-1);').assertFails
    }
}