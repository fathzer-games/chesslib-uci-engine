[![Maven Central](https://img.shields.io/maven-central/v/com.fathzer/chesslib-uci-engine)](https://central.sonatype.com/artifact/com.fathzer/chesslib-uci-engine)
[![License](https://img.shields.io/badge/license-Apache%202.0-brightgreen.svg)](https://github.com/fathzer-games/chesslib-uci-engine/blob/master/LICENSE)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=fathzer-games_chesslib-uci-engine&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=fathzer-games_chesslib-uci-engine)
[![javadoc](https://javadoc.io/badge2/com.fathzer/chesslib-uci-engine/javadoc.svg)](https://javadoc.io/doc/com.fathzer/chesslib-uci-engine)

# chesslib-uci-engine
A basic [UCI](https://en.wikipedia.org/wiki/Universal_Chess_Interface) engine based on the [bhlangonijr/chesslib](https://github.com/bhlangonijr/chesslib) move generator, and the [games-core](https://github.com/fathzer-games/games-core) alpha beta search algorithm implementation.

## How to run the engine
It requires a Java 11+ virtual machine.

Download the jar, then Launch the engine with the following command: ```java -jar chesslib-uci-engine.jar```

## Known bugs
- The chesslib library method ```Board.doMove(m,true)``` used to safely play moves from transposition table plays illegal moves as if they were legal.  
An [issue](https://github.com/bhlangonijr/chesslib/issues/114) has been posted to GitHub, as the probability of occurence of this bug is low, I'll wait for an answer...

## Developer notes

### Tests settings
The *com.fathzer.jchess.chesslib.PerfTTest* class tests moves generation algorithm using a perfT data base.  
The duration and accuracy of this test greatly depends on its search depth.  
This depth is 1 by default (to limit Github's resources consumption - Every push trigger a mvn test action). In order to perform better test, you can set the **perftDepth** system property to a higher value.

### TODO
- The incremental evaluation integration with ChessLibMoveGenerator is ugly (the unsafe cast in the context creation is the symptom.