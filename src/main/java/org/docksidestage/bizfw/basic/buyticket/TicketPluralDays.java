package org.docksidestage.bizfw.basic.buyticket;

public class TicketPluralDays implements Ticket {
    private final int displayPrice;
    private int enterTimes;

    public TicketPluralDays(int displayPrice, int enterTimes) {
        this.displayPrice = displayPrice;
        this.enterTimes = enterTimes;
    }
    @Override
    public int getDisplayPrice() {
        return displayPrice;
    }
    @Override
    public void doInPark() {
        if (enterTimes <= 0){
            throw new IllegalStateException("Ticket invalid: displayedPrice=" + displayPrice);
        }
        --enterTimes;
    }
}
