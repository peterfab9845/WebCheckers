package com.webcheckers.appl;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;

/**
 * Test class for MoveChecker
 */
@Tag("Application-tier")
public class MoveCheckerTest {

    /**
     * Instantiate the class despite it being all-static because of coverage
     */
    @BeforeAll
    public void instantiate() {
        MoveChecker moveChecker = new MoveChecker();
    }

}
