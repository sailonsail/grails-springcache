/*
 * Copyright 2010 Rob Fletcher
 *
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
package grails.plugin.springcache.web.key

import spock.lang.*
import grails.plugin.spock.*
import org.gmock.*
import grails.plugin.springcache.web.FilterContext
import javax.servlet.http.HttpServletRequest

@WithGMock
class MimeTypeAwareKeyGeneratorSpec extends UnitSpec {

	KeyGenerator generator = new MimeTypeAwareKeyGenerator()
	
	def setup() {
		registerMetaClass HttpServletRequest
	}
	
	def "keys differ for different request content types"() {
		given:
		// spock mocks can't handle interactions with dynamic methods so have to use something else
		def request = mock(HttpServletRequest) {
			format.returns("html")
			format.returns("html")
			format.returns("xml")
		}

		when:
		def key1
		def key2
		def key3
		play {
			key1 = generator.generateKey(new FilterContext(controllerName: "foo", actionName: "bar", request: request))
			key2 = generator.generateKey(new FilterContext(controllerName: "foo", actionName: "bar", request: request))
			key3 = generator.generateKey(new FilterContext(controllerName: "foo", actionName: "bar", request: request))
		}

		then:
		key1 == key2
		key1 != key3
	}

	def "content type 'all' is ignored"() {
		given:
		// spock mocks can't handle interactions with dynamic methods so have to use something else
		def request = mock(HttpServletRequest) {
			format.returns("all")
			format.returns(null)
		}
		
		when:
		def key1
		def key2
		play {
			key1 = generator.generateKey(new FilterContext(controllerName: "foo", actionName: "bar", request: request))
			key2 = generator.generateKey(new FilterContext(controllerName: "foo", actionName: "bar", request: request))
		}

		then:
		key1 == key2
	}

}