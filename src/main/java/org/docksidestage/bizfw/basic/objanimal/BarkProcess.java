package org.docksidestage.bizfw.basic.objanimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BarkProcess {
    private static final Logger logger = LoggerFactory.getLogger(BarkProcess.class);
    private final String barkSound;
    protected int hitPoint;

    public BarkProcess(String barkSound, int hitPoint) {
        this.barkSound = barkSound;
        this.hitPoint = hitPoint;
    }
    public BarkProcess(String barkSound) {
        this(barkSound, 10);
    }

    protected int getInitialHitPoint() {
        return 10; // as default
    }

    public BarkedSound bark() {
        breatheIn();
        prepareAbdominalMuscle();
        BarkedSound barkedSound = doBark(barkSound);
        return barkedSound;
    }

    protected void prepareAbdominalMuscle() {
        logger.debug("...Using my abdominal muscle"); // dummy implementation
        downHitPoint();
    }

    protected void breatheIn() {
        logger.debug("...Breathing in"); // dummy implementation
        downHitPoint();
    }
    protected BarkedSound doBark(String barkWord) {
        downHitPoint();
        return new BarkedSound(barkWord);
    }
    protected void downHitPoint() {
        --hitPoint;
        if (hitPoint == 0) {
            throw new IllegalStateException("I'm very tired, so I want to sleep" + barkSound);
        }
    }
    public int getHitPoint() {
        return hitPoint;
    }
}
