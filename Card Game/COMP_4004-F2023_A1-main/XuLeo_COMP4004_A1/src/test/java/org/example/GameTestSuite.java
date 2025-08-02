package org.example;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@Suite
@SelectClasses({MainTest.class, PlayerTest.class, CardTest.class, GameAcceptanceTest.class})
@SuiteDisplayName("Unit Test Suite")
public class GameTestSuite {
}
