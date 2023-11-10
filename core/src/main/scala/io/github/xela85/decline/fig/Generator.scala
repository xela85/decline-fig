/*
 * Copyright 2023 xela85
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

package io.github.xela85.decline.fig

import io.circe.Encoder
import io.circe.Json
import io.circe.syntax.*
import io.github.xela85.decline.fig.Generator.GeneratorCache

case class Generator(
    argName: String,
    cache: GeneratorCache,
    script: String,
    postProcess: Option[String]
)

object Generator {

  case class GeneratorCache(ttl: Long, strategy: Option[String])
      derives Encoder.AsObject

  implicit val figGeneratorEncoder: Encoder[Generator] =
    Encoder.instance { gen =>
      Json.obj(
        "cache" -> gen.cache.asJson,
        "script" -> gen.script.asJson,
        "postProcess" -> s"<typescript>${gen.argName}Generator</typescript>".asJson
      )
    }

}
