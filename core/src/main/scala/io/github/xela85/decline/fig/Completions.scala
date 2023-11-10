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

import com.monovore.decline.Command
import com.monovore.decline.fig.FigHelp
import io.circe.syntax.*

object Completions {

  def generateCompletions[A](
      command: Command[A],
      generatorMap: Map[String, Generator]
  ): String = {
    val generatorFunctions = generatorMap.values.flatMap { gen =>
      gen.postProcess.map(fun => s"const ${gen.argName}Generator = $fun;\n")
    }.mkString
    val figSpecification =
      FigHelp
        .commandToCompletions(command, generatorMap)
        .asJson
        .noSpaces
        .replaceAll("\"<typescript>", "")
        .replaceAll("</typescript>\"", "")
    s"""$generatorFunctions
       |const completionSpec: Fig.Spec = $figSpecification;
       |export default completionSpec;
       |""".stripMargin
  }

}
