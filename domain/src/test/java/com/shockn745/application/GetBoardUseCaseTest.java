package com.shockn745.application;

import org.junit.Test;

/**
 * @author Kempenich Florian
 */
public class GetBoardUseCaseTest {

    @Test
    public void getBoard_() throws Exception {

        /*
        NOTE :
        The idea is that the board is created in the activity and then passed to the game, etc . . .

        All the use-cases must take the Game as a parameter.
        ++++ Use callback as response mechanism (See physical note for UX handling)

        Which means persistence is only there when the activity exist (in other words, there is
        actually no persistence)

        But that's an OK first step I think. I'll think about the persistence mechanism by
        implementing it in a new module. Either database/inMemory/File/Cloud. (in practice most
        likely file or inMemory)
         */
    }
}
