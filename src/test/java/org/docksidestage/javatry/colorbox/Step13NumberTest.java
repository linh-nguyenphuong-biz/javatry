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
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.swing.*;

import javafx.util.Pair;
import org.docksidestage.bizfw.colorbox.ColorBox;
import org.docksidestage.bizfw.colorbox.space.BoxSpace;
import org.docksidestage.bizfw.colorbox.yours.YourPrivateRoom;
import org.docksidestage.unit.PlainTestCase;

/**
 * The test of Number with color-box. <br>
 * Show answer by log() for question of javadoc.
 * @author jflute
 * @author nguyen_phuong_linh
 */
public class Step13NumberTest extends PlainTestCase {

    // ===================================================================================
    //                                                                               Basic
    //                                                                               =====
    /**
     * How many integer-type values in color-boxes are between 0 and 54 (includes)? <br>
     * (カラーボックの中に入っているInteger型で、0から54までの値は何個ある？)
     */
    public void test_countZeroToFiftyFour_IntegerOnly() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        long ans = colorBoxList.stream()
                .flatMap(box -> box.getSpaceList().stream())
                .map(space -> space.getContent())
                .filter(cnt -> cnt instanceof Integer && (Integer) cnt >= 0 && (Integer) cnt <= 54)
                .count();
        log(ans); // 1
    }

    /**
     * How many number values in color-boxes are between 0 and 54? <br>
     * (カラーボックの中に入っている数値で、0から54までの値は何個ある？)
     */
    public void test_countZeroToFiftyFour_Number() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        long numInList = colorBoxList.stream()
                .flatMap(box -> box.getSpaceList().stream())
                .map(space -> space.getContent())
                .filter(cnt -> cnt instanceof List)
                .flatMap(list -> ((List<?>) list).stream())
                .filter(val -> val instanceof Number && (new BigDecimal(((Number) val).doubleValue())).compareTo(new BigDecimal(0)) >= 0
                        && (new BigDecimal(((Number) val).doubleValue())).compareTo(new BigDecimal(54)) <= 0)
                .count();
        long num = colorBoxList.stream()
                .flatMap(box -> box.getSpaceList().stream())
                .map(space -> space.getContent())
                .filter(cnt -> cnt instanceof Number && (new BigDecimal(((Number) cnt).doubleValue())).compareTo(new BigDecimal(0)) >= 0
                        && (new BigDecimal(((Number) cnt).doubleValue())).compareTo(new BigDecimal(54)) <= 0)
                .count();
        log(num + numInList); // 8
    }

    /**
     * What color name is used by color-box that has integer-type content and the biggest width in them? <br>
     * (カラーボックスの中で、Integer型の Content を持っていてBoxSizeの幅が一番大きいカラーボックスの色は？)
     */
    public void test_findColorBigWidthHasInteger() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        List<ColorBox> intType = colorBoxList.stream().filter(box -> {
            List<BoxSpace> spaces = box.getSpaceList();
            for (BoxSpace bs : spaces) {
                if (bs.getContent() instanceof Integer)
                    return true;
            }
            return false;
        }).collect(Collectors.toList());
        int maxWidth = intType.stream().map(box -> box.getSize().getWidth()).max(Comparator.comparingInt(i -> i)).orElse(-1);
        String color = intType.stream()
                .filter(box -> box.getSize().getWidth() == maxWidth)
                .map(box -> box.getColor().getColorName())
                .findFirst()
                .orElse("*not found");
        log(color); // green
    }

    /**
     * What is total of BigDecimal values in List in color-boxes? <br>
     * (カラーボックスの中に入ってる List の中の BigDecimal を全て足し合わせると？)
     */
    public void test_sumBigDecimalInList() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        BigDecimal ans = colorBoxList.stream()
                .flatMap(box -> box.getSpaceList().stream())
                .map(space -> space.getContent())
                .filter(cnt -> cnt instanceof List)
                .flatMap(list -> ((List<?>) list).stream())
                .filter(val -> val instanceof BigDecimal)
                .map(val -> (BigDecimal) val)
                .reduce(new BigDecimal(0), (a, b) -> a.add(b));
        log(ans); // 13.29459
    }

    // ===================================================================================
    //                                                                           Challenge
    //                                                                           =========
    /**
     * What key is related to value that is max number in Map that has only number in color-boxes? <br>
     * (カラーボックスに入ってる、valueが数値のみの Map の中で一番大きいvalueのkeyは？)
     */
    public void test_findMaxMapNumberValue() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        List<Pair<?, BigDecimal>> intValues = colorBoxList.stream()
                .flatMap(box -> box.getSpaceList().stream())
                .map(space -> space.getContent())
                .filter(map -> map instanceof Map)
                .filter(map -> ((Map<?, ?>) map).values().stream().noneMatch(val -> !(val instanceof Number)))
                .flatMap(map -> {
                    List<Pair<?,BigDecimal>> list = new ArrayList<>();
                    ((Map<?, ?>) map).forEach((k,v) -> list.add(new Pair<>(k,new BigDecimal(((Number) v).doubleValue()))));
                    return list.stream();
                })
                .collect(Collectors.toList());
        BigDecimal maxVal = intValues.stream()
                .map(pair -> pair.getValue())
                .max(Comparator.comparing(i -> i))
                .orElse(new BigDecimal(0));
        Object ans = intValues.stream()
                .filter(pair -> pair.getValue().compareTo(maxVal) == 0)
                .findFirst()
                .map(pair -> pair.getKey().toString())
                .orElse("*not found");
        log(ans); // Land Annual Passport
    }

    /**
     * What is total of number or number-character values in Map in purple color-box? <br> 
     * (purpleのカラーボックスに入ってる Map の中のvalueの数値・数字の合計は？)
     */
    public void test_sumMapNumberValue() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        BigDecimal ans = colorBoxList.stream()
                .filter(box -> box.getColor().getColorName() == "purple")
                .flatMap(box -> box.getSpaceList().stream())
                .map(space -> space.getContent())
                .filter(cnt -> cnt instanceof Map)
                .flatMap(map -> ((Map<?, ?>) map).values().stream())
                .map(val -> {
                    if (val instanceof String) {
                        try {
                            return new BigDecimal((String) val);
                        } catch (NumberFormatException e) {
                            return new BigDecimal(0);
                        }
                    } else
                        return val instanceof Number ? new BigDecimal(((Number) val).doubleValue()) : new BigDecimal(0);
                })
                .reduce(new BigDecimal(0), (a, b) -> a.add(b));
        log(ans); // 2350
    }
}
