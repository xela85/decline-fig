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

/** Options add additional information to a Subcommand. They usually start with
  * \- or --. See [[https://fig.io/docs/reference/option]]
  * @param name
  *   The exact name of the subcommand as defined in the CLI tool.
  * @param description
  *   The text that gets rendered at the bottom of the autocomplete box (or the
  *   side if you hit ⌘i)
  * @param args
  *   An array of arg objects or a single arg object
  */
final case class Opt(
    name: List[String],
    description: String,
    args: Option[Arg]
) derives Encoder.AsObject
