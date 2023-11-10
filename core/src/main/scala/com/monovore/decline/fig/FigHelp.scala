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

package com.monovore.decline.fig

import com.monovore.decline.Command
import com.monovore.decline.Help
import com.monovore.decline.Opt.Argument
import com.monovore.decline.Opt.Flag
import com.monovore.decline.Opt.OptionalOptArg
import com.monovore.decline.Opt.Regular
import com.monovore.decline.Opts
import com.monovore.decline.Opts.*
import io.github.xela85.decline.fig.Arg
import io.github.xela85.decline.fig.Generator
import io.github.xela85.decline.fig.Opt

object FigHelp {

  def commandToCompletions[A](
      command: Command[A],
      generators: Map[String, Generator]
  ): io.github.xela85.decline.fig.Command = {
    val declineOpts = Help.optionList(command.options).getOrElse(Nil)
    val subCommands = Help.commandList(command.options)
    val options = declineOpts.collect {
      case (r: Regular, repeated) =>
        val names = generateFlagNames(r.names)
        Opt(
          names,
          r.help,
          Some(
            Arg(
              name = Some(r.metavar),
              description = r.help,
              isVariadic = repeated,
              isOptional = false,
              generators.get(names.head)
            )
          )
        )
      case (o: OptionalOptArg, repeated) =>
        val names = generateFlagNames(o.names)
        Opt(
          names,
          o.help,
          Some(
            Arg(
              name = Some(o.metavar),
              description = o.help,
              isVariadic = repeated,
              isOptional = true,
              generators.get(names.head)
            )
          )
        )
      case (f: Flag, repeated) =>
        Opt(generateFlagNames(f.names), f.help, None)
    }
    val args = declineOpts.collect { case (a: Argument, repeated) =>
      Arg(
        name = Some(a.metavar),
        description = a.metavar,
        isVariadic = repeated,
        isOptional = false,
        generators.get(a.metavar)
      )
    }

    io.github.xela85.decline.fig.Command(
      name = List(command.name),
      subcommands = subCommands.map(commandToCompletions(_, generators)),
      description = command.header,
      requiresSubcommand = requiresSubCommand(command.options),
      options = options,
      args = args
    )
  }

  private def requiresSubCommand(opts: Opts[?]): Boolean = {
    opts match {
      case Pure(a)             => false
      case Opts.Missing        => true
      case App(f, a)           => requiresSubCommand(f)
      case OrElse(a, b)        => requiresSubCommand(a) && requiresSubCommand(b)
      case Single(opt)         => true
      case Repeated(opt)       => true
      case Subcommand(command) => true
      case Validate(_, _)      => false
      case HelpFlag(flag)      => true
      case Env(name, help, metavar) => true
    }
  }

  private def generateFlagNames(
      names: List[Opts.Name]
  ): List[String] = {
    val long = names.collect { case Opts.LongName(flag) =>
      s"--$flag"
    }
    val short = names.collect { case Opts.ShortName(flag) =>
      s"-$flag"
    }
    long ++ short
  }

}
