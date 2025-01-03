[![License](https://img.shields.io/badge/license-Apache%202.0-brightgreen.svg)](https://github.com/fathzer-games/chesslib-uci-engine/blob/master/LICENSE)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=fathzer-games_chesslib-uci-engine&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=fathzer-games_chesslib-uci-engine)

# chesslib-uci-engine
A basic [UCI](https://en.wikipedia.org/wiki/Universal_Chess_Interface) engine based on the [bhlangonijr/chesslib](https://github.com/bhlangonijr/chesslib) move generator, and the [games-core](https://github.com/fathzer-games/games-core) alpha beta search algorithm implementation. It uses the evaluation functions, the remaining move oracle and some other things from the [chess-utils](https://github.com/fathzer-games/chess-utils) library.

You can play against it on [Lichess](https://lichess.org/@/fathzer-jchess). It runs on a Minisforum U300 with [3867U processor](https://www.cpubenchmark.net/cpu.php?cpu=Intel+Celeron+3867U+%40+1.80GHz&id=3442).

## How to run the engine
It requires a Java 17+ virtual machine.

Download the jar [here](https://fathzer-games.github.io/chesslib-uci-engine/chesslib-uci-engine.jar), then Launch the engine with the following command: ```java -jar chesslib-uci-engine.jar```

### Openings library
You can use an opening library located at a URL using the ```openingsUrl``` system property.  
Currently such a library is available [here](https://fathzer-games.github.io/chesslib-uci-engine/masters-shrink.json.gz).

To use this library, launch the engine with:  
```java -DopeningsUrl=https://fathzer-games.github.io/chesslib-uci-engine/masters-shrink.json.gz -jar chesslib-uci-engine.jar```

## Known bugs
- The chesslib library method ```Board.doMove(m,true)``` used to safely play moves from transposition table plays illegal moves as if they were legal.  
An [issue](https://github.com/bhlangonijr/chesslib/issues/114) has been posted to GitHub, as the probability of occurence of this bug is low, I'll wait for an answer...  
The test class ```com.fathzer.jchess.chesslib.ChessLibMoveGeneratorTest``` has commented assertions that currently fails.

## Developer notes

### Tests settings
The *com.fathzer.jchess.chesslib.PerfTTest* class tests moves generation algorithm using a perfT data base.  
The duration and accuracy of this test greatly depends on its search depth.  
This depth is 1 by default (to limit Github's resources consumption - Every push trigger a mvn test action). In order to perform better test, you can set the **perftDepth** system property to a higher value.

### TODO
- The detection of invalid UNSAFE move does not work (An [issue](https://github.com/bhlangonijr/chesslib/issues/114) is opened in Chesslib project about this problem).