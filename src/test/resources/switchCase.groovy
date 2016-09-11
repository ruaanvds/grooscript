/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed underå the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

def isAGoodPerson = false

def person = "Ghandi"

switch (person) {
    case 'Hitler':
        isAGoodPerson = false
        break
    case "Ghandi":
    case "Mother Theresa":
        isAGoodPerson = true
}

assert isAGoodPerson