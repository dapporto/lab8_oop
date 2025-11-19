package it.unibo.mvc.view;

import it.unibo.mvc.api.DrawNumberController;
import it.unibo.mvc.api.DrawNumberView;
import it.unibo.mvc.api.DrawResult;

/** 
 * This view implements {@link DrawNumberView}.
 * It's output only (it does not send new input to the controller).
 */
public class DrawNumberStandardOutputView implements DrawNumberView {
    @Override
    public void setController(final DrawNumberController observer) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void start() {
        System.out.println("Are you ready for a new game?"); // NOPMD
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    public void result(final DrawResult res) {
        System.out.println(res.getDescription()); // NOPMD
        if (res == DrawResult.YOU_WON || res == DrawResult.YOU_LOST) {
            this.start();
        }
    }

}
