package com.fathzer.chess.test.utils;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

import com.fathzer.chess.utils.test.PerftTest;

@Suite
@SuiteDisplayName("Tests from chess-test-utils")
@SelectClasses({PerftTest.class})
public class SuiteTest {
}