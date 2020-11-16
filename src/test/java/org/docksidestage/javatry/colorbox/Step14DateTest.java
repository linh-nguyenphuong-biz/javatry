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
package org.docksidestage.javatry.colorbox;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.docksidestage.bizfw.colorbox.ColorBox;
import org.docksidestage.bizfw.colorbox.space.DoorBoxSpace;
import org.docksidestage.bizfw.colorbox.yours.YourPrivateRoom;
import org.docksidestage.unit.PlainTestCase;

/**
 * The test of Date with color-box. <br>
 * Show answer by log() for question of javadoc.
 * @author jflute
 * @author nguyen_phuong_linh
 */
public class Step14DateTest extends PlainTestCase {

    // ===================================================================================
    //                                                                               Basic
    //                                                                               =====
    /**
     * What string is date in color-boxes formatted as plus-separated (e.g. 2019+04+24)? <br>
     * (カラーボックスに入っている日付をプラス記号区切り (e.g. 2019+04+24) のフォーマットしたら？)
     */
    public void test_formatDate() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        List<String> formattedDate = colorBoxList.stream()
                .flatMap(box -> box.getSpaceList().stream())
                .map(space -> space.getContent())
                .filter(cnt -> cnt instanceof LocalDate || cnt instanceof LocalDateTime)
                .map(date -> date.toString().replace('-', '+'))
                .collect(Collectors.toList());
        formattedDate.forEach(d -> log(d));
        //1983+04+15T23:59:59
        //2001+09+04
    }

    /**
     * How is it going to be if the slash-separated date string in yellow color-box is converted to LocaDate and toString() is used? <br>
     * (yellowのカラーボックスに入っているSetの中のスラッシュ区切り (e.g. 2019/04/24) の日付文字列をLocalDateに変換してtoString()したら？)
     */
    public void test_parseDate() {
    }

    /**
     * What is total of month numbers of date in color-boxes? <br>
     * (カラーボックスに入っている日付の月を全て足したら？)
     */
    public void test_sumMonth() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        int sumMonth = colorBoxList.stream()
                .flatMap(box -> box.getSpaceList().stream())
                .map(space -> space.getContent())
                .filter(cnt -> cnt instanceof LocalDate || cnt instanceof LocalDateTime)
                .map(date -> {
                    if (date instanceof LocalDateTime) return ((LocalDateTime) date).getMonthValue();
                    return ((LocalDate) date).getMonthValue();
                })
                .reduce(0, (a, b) -> a + b);
        log(sumMonth); // 13
    }

    /**
     * Add 3 days to second-found date in color-boxes, what day of week is it? <br>
     * (カラーボックスに入っている二番目に見つかる日付に3日進めると何曜日？)
     */
    public void test_plusDays_weekOfDay() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        List<Object> dates = colorBoxList.stream()
                .flatMap(box -> box.getSpaceList().stream())
                .map(space -> space.getContent())
                .filter(cnt -> cnt instanceof LocalDate || cnt instanceof LocalDateTime)
                .collect(Collectors.toList());
        Object date = dates.get(1);
        if (date instanceof LocalDate) {
            log(((LocalDate) date).plusDays(3).getDayOfWeek());
        } else if (date instanceof LocalDateTime){
            log(((LocalDateTime) date).plusDays(3).getDayOfWeek());
        }
        // FRIDAY

    }

    // ===================================================================================
    //                                                                           Challenge
    //                                                                           =========
    /**
     * How many days (number of day) are between two dates in yellow color-boxes? <br>
     * (yellowのカラーボックスに入っている二つの日付は何日離れている？)
     */
    public void test_diffDay() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        List<LocalDate> dates = colorBoxList.stream()
                .flatMap(box -> box.getSpaceList().stream())
                .map(space -> space.getContent())
                .filter(cnt -> cnt instanceof LocalDate || cnt instanceof LocalDateTime)
                .map(date -> {
                    if (date instanceof LocalDateTime) return ((LocalDateTime) date).toLocalDate();
                    return (LocalDate) date;
                })
                .collect(Collectors.toList());
        log(Math.abs(dates.get(0).toEpochDay() - dates.get(1).toEpochDay())); // 6717
    }

    /**
     * Find LocalDate in yellow color-box,
     * and add same color-box's LocalDateTime's seconds as number of months to it,
     * and add red color-box's Long number as days to it,
     * and subtract the first decimal place of BigDecimal that has three(3) as integer in List in color-boxes from it,
     * What date is it? <br>
     * (yellowのカラーボックスに入っているLocalDateに、同じカラーボックスに入っているLocalDateTimeの秒数を月数として足して、
     * redのカラーボックスに入っているLong型を日数として足して、カラーボックスに入っているリストの中のBigDecimalの整数値が3の小数点第一位の数を日数として引いた日付は？)
     */
    public void test_birthdate() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        LocalDate date = colorBoxList.stream()
                .filter(box -> box.getColor().getColorName() == "yellow")
                .flatMap(box -> box.getSpaceList().stream())
                .map(space -> space.getContent())
                .filter(cnt -> cnt instanceof LocalDate)
                .map(cnt -> (LocalDate)cnt)
                .findFirst()
                .orElse(null);
        int seconds = colorBoxList.stream()
                .filter(box -> box.getColor().getColorName() == "yellow")
                .flatMap(box -> box.getSpaceList().stream())
                .map(space -> space.getContent())
                .filter(cnt -> cnt instanceof LocalDateTime)
                .findFirst()
                .map(cnt -> ((LocalDateTime) cnt).getSecond())
                .orElse(-1);
        if (seconds != -1) date = date.plusMonths(seconds);
        Long longNum = colorBoxList.stream()
                .filter(box -> box.getColor().getColorName() == "red")
                .flatMap(box -> box.getSpaceList().stream())
                .map(space -> space.getContent())
                .filter(cnt -> cnt instanceof Long)
                .findFirst()
                .map(cnt -> (long) cnt)
                .orElse(null);
        if (longNum != null) date = date.plusDays(longNum);
        int decimal = colorBoxList.stream()
                .flatMap(box -> box.getSpaceList().stream())
                .map(space -> space.getContent())
                .filter(cnt -> cnt instanceof List)
                .flatMap(cnt -> ((List<?>) cnt).stream())
                .filter(cnt -> cnt instanceof BigDecimal)
                .map(cnt -> (BigDecimal) cnt)
                .filter(cnt -> cnt.intValue() == 3)
                .findFirst()
                .map(cnt -> cnt.movePointRight(1).intValue() % 10)
                .orElse(-1);
        if (decimal > -1) date = date.minusDays(decimal);
        log(date.toString()); // 2006-09-26
    }

    /**
     * What second is LocalTime in color-boxes? <br>
     * (カラーボックスに入っているLocalTimeの秒は？)
     */
    public void test_beReader() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        int second = colorBoxList.stream()
                .flatMap(box -> box.getSpaceList().stream())
                .filter(space -> space instanceof DoorBoxSpace)
                .map(space -> {
                    ((DoorBoxSpace) space).openTheDoor();
                    Object cnt = space.getContent();
                    ((DoorBoxSpace) space).closeTheDoor();
                    return cnt;
                })
                .filter(cnt -> cnt instanceof LocalTime)
                .findFirst()
                .map(time -> ((LocalTime) time).getSecond())
                .orElse(-1);
        log(second); // 24
    }
}
