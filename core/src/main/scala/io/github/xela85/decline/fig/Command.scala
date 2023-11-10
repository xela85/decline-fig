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

/** Used to define subcommands under a main command See
  * [[https://fig.io/docs/reference/subcommand]]
  *
  * @param name
  *   The name of the subcommand. Should exactly match the name defined by the
  *   CLI tool.
  * @param subcommands
  *   An array of [[Command]] objects representing all the subcommands that
  *   exist beneath the current command.
  * @param description
  *   The text that gets rendered at the bottom of the autocomplete box (or the
  *   side if you hit ⌘i)
  * @param requiresSubcommand
  *   Specifies whether the command requires a subcommand. This is false by
  *   default.
  * @param options
  *   An array of [[Opt]] objects representing the options that are available on
  *   this subcommand.
  * @param args
  *   An array of [[Arg]] objects representing the various parameters or
  *   "arguments" that can be passed to this subcommand.
  */
case class Command(
    name: List[String],
    subcommands: List[Command],
    description: String,
    requiresSubcommand: Boolean,
    options: List[Opt],
    args: List[Arg]
) derives Encoder.AsObject
