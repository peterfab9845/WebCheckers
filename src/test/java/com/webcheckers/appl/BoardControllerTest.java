package com.webcheckers.appl;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;

/**
 * Test class for BoardController
 */
@Tag("Application-tier")
public class BoardControllerTest {

    /**
     * Instantiate the class despite it being all-static because of coverage
     */
    @BeforeAll
    public void instantiate() {
        BoardController boardController = new BoardController();
    }

}
