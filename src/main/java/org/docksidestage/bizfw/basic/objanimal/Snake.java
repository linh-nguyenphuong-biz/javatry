package org.docksidestage.bizfw.basic.objanimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Snake extends Animal implements Carnivore{
    private static final Logger logger = LoggerFactory.getLogger(Dog.class);

    @Override
    protected String getBarkWord() {
        return "xii---";
    }
    @Override
    public void eat(Animal animal) {
        logger.debug("Snake is eating a " + animal.getClass().getSimpleName());
    }
}
