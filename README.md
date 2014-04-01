# pedestal-immutant

A Leiningen template for generating a
[Pedestal](https://github.com/pedestal/pedestal) service hosted in an
[Immutant](http://immutant.org/) container.

## Usage

This is a lein project template, and can be used as such.

```sh
$ lein new sonian-pedestal-immutant my-awesome-project
```

## Features provided

The project skeleton sets up a lot of things that are generally useful
for building an http service:

- A `ctl` script and a carousel admin server.
- carousel style modules in general.
- An nrepl listener.
- A `hello world` implementation in Pedestal.
- Unit tests structured properly.
- Unit tests test the `hello world` implementation.
- `only-in-immutant-fixture` is available.

## FAQ

### Where are my logs?

When running Immutant, the logs are in `target/isolated-immutant/standalone/log`

### How do I run a REPL inside Immutant, so I can test things that rely on features it provides?

The :dev profile starts an nrepl listener, and leaves a `.nrepl-port` file in the project directory, which nrepl/cider will pick up automatically when you run `M-x cider`

### My REPL is connected, how can I test?

Use `clojure.test` as usual, i.e. `C-c M-n` to put the REPL into your test's namespace and call `(run-tests)` or `C-c M-,`

Be aware that you must (re)compile anything that you're testing, and that tests that were compiled before are now resident, so commenting them out won't make them stop running until you explicitly remove them. You can do that either by simply restarting the JVM, or with `user/refresh` -- `clojure.tools.namespace.repl` is included in the project for that.

## License

Copyright © 2014 Sonian, Inc.

Distributed under the Apache 2.0 License.
