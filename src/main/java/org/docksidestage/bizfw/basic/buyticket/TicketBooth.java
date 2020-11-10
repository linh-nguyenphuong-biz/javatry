/*
 * Copyright 2019-2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.docksidestage.bizfw.basic.buyticket;

/**
 * @author jflute
 */
public class TicketBooth {

    // ===================================================================================
    //                                                                          Definition
    //                                                                          ==========
    private static final int MAX_QUANTITY = 10;
    public static final int ONE_DAY_PRICE = 7400; // when 2019/06/15
    public static final int TWO_DAY_PRICE = 13200;
    public static final int FOUR_DAY_PRICE = 22400;

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private int oneDayQuantity = MAX_QUANTITY;
    private int twoDayQuantity = MAX_QUANTITY;
    private int fourDayQuantity = MAX_QUANTITY;
    private Integer salesProceeds;

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    public TicketBooth() {
    }

    // ===================================================================================
    //                                                                          Buy Ticket
    //                                                                          ==========
    public TicketOneDay buyOneDayPassport(int handedMoney) {
        this.oneDayQuantity = buyPassport(handedMoney, 1, this.oneDayQuantity);
        return new TicketOneDay(ONE_DAY_PRICE);
    }

    public TicketBuyResult buyTwoDayPassport(int handedMoney){
        this.twoDayQuantity = buyPassport(handedMoney, 2, this.twoDayQuantity);
        return new TicketBuyResult(new TicketPluralDays(TWO_DAY_PRICE, 2), handedMoney - TWO_DAY_PRICE);
    }

    public TicketBuyResult buy4DaysPassport(int handedMoney){
        this.fourDayQuantity = buyPassport(handedMoney, 4, this.fourDayQuantity);
        return new TicketBuyResult(new TicketPluralDays(FOUR_DAY_PRICE, 4), handedMoney - FOUR_DAY_PRICE);
    }

    private int buyPassport(int handedMoney, int type, int quantity){
        int price = type == 1? ONE_DAY_PRICE : type == 2? TWO_DAY_PRICE : FOUR_DAY_PRICE;
        if (quantity <= 0) {
            throw new TicketSoldOutException("Sold out");
        }
        if (handedMoney < price) {
            throw new TicketShortMoneyException("Short money: " + handedMoney);
        }
        --quantity;
        if (salesProceeds != null) {
            salesProceeds = salesProceeds + price;
        } else {
            salesProceeds = price;
        }
        return quantity;
    }

    public int getTwoDayQuantity() {
        return twoDayQuantity;
    }
    public void setTwoDayQuantity(int twoDayQuantity) {
        this.twoDayQuantity = twoDayQuantity;
    }
    public static class TicketSoldOutException extends RuntimeException {

        private static final long serialVersionUID = 1L;

        public TicketSoldOutException(String msg) {
            super(msg);
        }
    }

    public static class TicketShortMoneyException extends RuntimeException {

        private static final long serialVersionUID = 1L;

        public TicketShortMoneyException(String msg) {
            super(msg);
        }
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    public int getOneDayQuantity() {
        return oneDayQuantity;
    }

    public Integer getSalesProceeds() {
        return salesProceeds;
    }

    public int getFourDayQuantity() {
        return fourDayQuantity;
    }
}
