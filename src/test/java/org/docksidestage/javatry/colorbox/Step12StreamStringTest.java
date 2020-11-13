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

import java.io.File;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.docksidestage.bizfw.colorbox.ColorBox;
import org.docksidestage.bizfw.colorbox.yours.YourPrivateRoom;
import org.docksidestage.unit.PlainTestCase;

/**
 * The test of String with color-box, using Stream API you can. <br>
 * Show answer by log() for question of javadoc.
 * <pre>
 * addition:
 * o e.g. "string in color-boxes" means String-type content in space of color-box
 * o don't fix the YourPrivateRoom class and color-box classes
 * </pre>
 * @author jflute
 * @author nguyen_phuong_linh
 */
public class Step12StreamStringTest extends PlainTestCase {

    // ===================================================================================
    //                                                                            length()
    //                                                                            ========
    /**
     * What is color name length of first color-box? <br>
     * (最初のカラーボックスの色の名前の文字数は？)
     */
    public void test_length_basic() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        String answer = colorBoxList.stream()
                .findFirst()
                .map(colorBox -> colorBox.getColor().getColorName())
                .map(colorName -> colorName.length() + " (" + colorName + ")")
                .orElse("*not found");
        log(answer); // color name's length: 5
    }

    /**
     * Which string has max length in color-boxes? <br>
     * (カラーボックスに入ってる文字列の中で、一番長い文字列は？)
     */
    public void test_length_findMax() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        String ans = colorBoxList.stream()
                .flatMap(box -> box.getSpaceList().stream())
                .map(space -> space.getContent())
                .filter(content -> content instanceof String)
                .max(Comparator.comparingInt(s -> ((String) s).length()))
                .orElse("*not found")
                .toString();
        log(ans); // おるぐどっくさいどすてーじ
    }

    /**
     * How many characters are difference between max and min length of string in color-boxes? <br>
     * (カラーボックスに入ってる文字列の中で、一番長いものと短いものの差は何文字？)
     */
    public void test_length_findMaxMinDiff() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        List<Integer> lengths = colorBoxList.stream()
                .flatMap(box -> box.getSpaceList().stream())
                .map(space -> space.getContent())
                .filter(content -> content instanceof String)
                .map(s -> ((String) s).length())
                .collect(Collectors.toList());
        int maxL = lengths.stream().max(Comparator.comparingInt(i -> i)).orElse(-1);
        int minL = lengths.stream().min(Comparator.comparingInt(i -> i)).orElse(-1);
        if (maxL > 0 && minL > 0)
            log(maxL - minL); // 3
        else
            log("*not found");
    }

    // has small #adjustmemts from ClassicStringTest
    //  o sort allowed in Stream
    /**
     * Which value (toString() if non-string) has second-max length in color-boxes? (sort allowed in Stream)<br>
     * (カラーボックスに入ってる値 (文字列以外はtoString()) の中で、二番目に長い文字列は？ (Streamでのソートありで))
     */
    public void test_length_findSecondMax() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        List<String> strings = colorBoxList.stream()
                .flatMap(box -> box.getSpaceList().stream())
                .map(space -> space.getContent())
                .map(s -> String.valueOf(s))
                .sorted(Comparator.comparingInt(String::length))
                .collect(Collectors.toList());
        log(strings.get(strings.size() - 2));
        // {sea={dockside=[over, table, hello], hanger=[mystic, shadow, mirage], harbor={spring=fashion, summer=pirates, autumn=vi, winter=jazz}}, land={orleans=[oh, party], showbase=[oneman]}}
    }

    /**
     * How many total lengths of strings in color-boxes? <br>
     * (カラーボックスに入ってる文字列の長さの合計は？)
     */
    public void test_length_calculateLengthSum() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        long lengthSum = colorBoxList.stream()
                .flatMap(box -> box.getSpaceList().stream())
                .map(space -> space.getContent())
                .filter(content -> content instanceof String)
                .map(s -> ((String) s).length())
                .distinct()
                .reduce(0, (start, next) -> start + next);
        log(lengthSum); // 23
    }

    /**
     * Which color name has max length in color-boxes? <br>
     * (カラーボックスの中で、色の名前が一番長いものは？)
     */
    public void test_length_findMaxColorSize() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        String ans = colorBoxList.stream()
                .map(box -> box.getColor().getColorName())
                .max(Comparator.comparingInt(color -> color.length()))
                .orElse("*not found");
        log(ans); // yellow
    }

    // ===================================================================================
    //                                                            startsWith(), endsWith()
    //                                                            ========================
    /**
     * What is color in the color-box that has string starting with "Water"? <br>
     * ("Water" で始まる文字列をしまっているカラーボックスの色は？)
     */
    public void test_startsWith_findFirstWord() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        List<String> colors = colorBoxList.stream()
                .filter(box -> box.getSpaceList()
                        .stream()
                        .map(space -> space.getContent())
                        .filter(content -> content instanceof String)
                        .map(content -> content.toString())
                        .anyMatch(content -> content.startsWith("Water")))
                .map(box -> box.getColor().getColorName())
                .collect(Collectors.toList());
        colors.forEach(c -> log(c)); // red
    }

    /**
     * What is color in the color-box that has string ending with "front"? <br>
     * ("front" で終わる文字列をしまっているカラーボックスの色は？)
     */
    public void test_endsWith_findLastWord() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        List<String> colors = colorBoxList.stream()
                .filter(box -> box.getSpaceList()
                        .stream()
                        .map(space -> space.getContent())
                        .filter(content -> content instanceof String)
                        .map(content -> content.toString())
                        .anyMatch(content -> content.endsWith("front")))
                .map(box -> box.getColor().getColorName())
                .collect(Collectors.toList());
        colors.forEach(c -> log(c)); // red
    }

    // ===================================================================================
    //                                                            indexOf(), lastIndexOf()
    //                                                            ========================
    /**
     * What number character is starting with first "front" of string ending with "front" in color-boxes? <br>
     * (カラーボックスに入ってる "front" で終わる文字列で、最初の "front" は何文字目から始まる？)
     */
    public void test_indexOf_findIndex() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        int ans = colorBoxList.stream()
                .flatMap(box -> box.getSpaceList().stream())
                .map(space -> space.getContent())
                .filter(content -> content instanceof String)
                .map(content -> content.toString())
                .filter(content -> content.endsWith("front"))
                .map(content -> content.indexOf("front"))
                .findFirst()
                .orElse(-1);
        log(ans); // 5
    }

    /**
     * What number character is starting with the late "ど" of string containing plural "ど"s in color-boxes? (e.g. "どんどん" => 3) <br>
     * (カラーボックスに入ってる「ど」を二つ以上含む文字列で、最後の「ど」は何文字目から始まる？ (e.g. "どんどん" => 3))
     */
    public void test_lastIndexOf_findIndex() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        int ans = colorBoxList.stream()
                .flatMap(box -> box.getSpaceList().stream())
                .map(space -> space.getContent())
                .filter(content -> content instanceof String)
                .map(content -> content.toString())
                .filter(content -> content.contains("ど"))
                .map(content -> content.lastIndexOf("ど"))
                .findFirst()
                .orElse(-1);
        log(ans); // 8
    }

    // ===================================================================================
    //                                                                         substring()
    //                                                                         ===========
    /**
     * What character is first of string ending with "front" in color-boxes? <br>
     * (カラーボックスに入ってる "front" で終わる文字列の最初の一文字は？)
     */
    public void test_substring_findFirstChar() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        char ans = colorBoxList.stream()
                .flatMap(box -> box.getSpaceList().stream())
                .map(space -> space.getContent())
                .filter(content -> content instanceof String)
                .map(content -> content.toString())
                .filter(content -> content.endsWith("front"))
                .findFirst()
                .map(s -> s.charAt(0))
                .orElse(' ');
        log(ans); // W
    }

    /**
     * What character is last of string starting with "Water" in color-boxes? <br>
     * (カラーボックスに入ってる "Water" で始まる文字列の最後の一文字は？)
     */
    public void test_substring_findLastChar() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        String s = colorBoxList.stream()
                .flatMap(box -> box.getSpaceList().stream())
                .map(space -> space.getContent())
                .filter(content -> content instanceof String)
                .map(content -> content.toString())
                .filter(content -> content.startsWith("Water"))
                .findFirst()
                .orElse("*not found");
        log(s == "*not found" ? "*not found" : s.charAt(s.length() - 1)); // t
    }

    // ===================================================================================
    //                                                                           replace()
    //                                                                           =========
    /**
     * How many characters does string that contains "o" in color-boxes and removing "o" have? <br>
     * (カラーボックスに入ってる "o" (おー) を含んだ文字列から "o" を全て除去したら何文字？)
     */
    public void test_replace_remove_o() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        int ans = colorBoxList.stream()
                .flatMap(box -> box.getSpaceList().stream())
                .map(space -> space.getContent())
                .filter(content -> content instanceof String)
                .map(content -> content.toString().replace("o", "").length())
                .reduce(0, (start, next) -> start + next);
        log(ans); // 22
    }

    /**
     * What string is path string of java.io.File in color-boxes, which is replaced with "/" to Windows file separator? <br>
     * カラーボックスに入ってる java.io.File のパス文字列のファイルセパレーターの "/" を、Windowsのファイルセパレーターに置き換えた文字列は？
     */
    public void test_replace_fileseparator() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        List<String> paths = colorBoxList.stream()
                .flatMap(box -> box.getSpaceList().stream())
                .map(space -> space.getContent())
                .filter(content -> content instanceof File)
                .map(content -> ((File) content).getPath().replace("/", "¥"))
                .collect(Collectors.toList());
        paths.forEach(path -> log(path)); // ¥tmp¥javatry¥docksidestage.txt
    }

    // ===================================================================================
    //                                                                    Welcome to Devil
    //                                                                    ================
    /**
     * What is total length of text of DevilBox class in color-boxes? <br>
     * (カラーボックスの中に入っているDevilBoxクラスのtextの長さの合計は？)
     */
    public void test_welcomeToDevil() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        int length1 = colorBoxList.stream().filter(box -> box instanceof YourPrivateRoom.DevilBox).map(box -> {
            try {
                ((YourPrivateRoom.DevilBox) box).wakeUp();
                ((YourPrivateRoom.DevilBox) box).allowMe();
                ((YourPrivateRoom.DevilBox) box).open();
                return ((YourPrivateRoom.DevilBox) box).getText().length();
            } catch (YourPrivateRoom.DevilBoxTextNotFoundException e) {
                return 0;
            }
        }).reduce(0, (start, next) -> start + next);
        int length2 = colorBoxList.stream()
                .flatMap(box -> box.getSpaceList().stream())
                .map(space -> space.getContent())
                .filter(cnt -> cnt instanceof YourPrivateRoom.DevilBox)
                .map(box -> {
                    try {
                        ((YourPrivateRoom.DevilBox) box).wakeUp();
                        ((YourPrivateRoom.DevilBox) box).allowMe();
                        ((YourPrivateRoom.DevilBox) box).open();
                        return ((YourPrivateRoom.DevilBox) box).getText().length();
                    } catch (YourPrivateRoom.DevilBoxTextNotFoundException e) {
                        return 0;
                    }
                })
                .reduce(0, (start, next) -> start + next);
        log(length1 + length2); // 22
    }

    // ===================================================================================
    //                                                                           Challenge
    //                                                                           =========
    /**
     * What string is converted to style "map:{ key = value ; key = value ; ... }" from java.util.Map in color-boxes? <br>
     * (カラーボックスの中に入っている java.util.Map を "map:{ key = value ; key = value ; ... }" という形式で表示すると？)
     */
    public void test_showMap_flat() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        List<String> ans = colorBoxList.stream()
                .flatMap(box -> box.getSpaceList().stream())
                .map(space -> space.getContent())
                .filter(cnt -> cnt instanceof Map)
                .map(cnt -> {
                    String s = "map:{ ";
                    Map<?,?> map = (Map<?,?>) cnt;
                    for (Object key : map.keySet()){
                        s += key.toString() + " = " + map.get(key) + " ; ";
                    }
                    s = s.replaceAll("; $", "}");
                    return s;
                })
                .collect(Collectors.toList());
        log(ans);
        // [map:{ 1-Day Passport = 7400 ; Starlight Passport = 5400 ; After 6 Passport = 4200 ; 2-Day Passport = 13200 ; 3-Day Magic Passport = 17800 ; 4-Day Magic Passport = 22400 ; Land Annual Passport = 61000 ; Sea Annual Passport = 61000 ; Group Passport = 6700 }, map:{ sea = {dockside=[over, table, hello], hanger=[mystic, shadow, mirage], harbor={spring=fashion, summer=pirates, autumn=vi, winter=jazz}} ; land = {orleans=[oh, party], showbase=[oneman]} }, map:{ Small Coin Locker = 300 ; Resort Line = 250 ; Cinema Piari = 1800 ; Middle Coin Locker = 4O0 }]
    }

    /**
     * What string is converted to style "map:{ key = value ; key = map:{ key = value ; ... } ; ... }" from java.util.Map in color-boxes? <br>
     * (カラーボックスの中に入っている java.util.Map を "map:{ key = value ; key = map:{ key = value ; ... } ; ... }" という形式で表示すると？)
     */
    public void test_showMap_nested() {
    }

    // ===================================================================================
    //                                                                           Good Luck
    //                                                                           =========
    // has small #adjustmemts from ClassicStringTest
    //  o comment out because of too difficult to be stream?
    ///**
    // * What string of toString() is converted from text of SecretBox class in upper space on the "white" color-box to java.util.Map? <br>
    // * (whiteのカラーボックスのupperスペースに入っているSecretBoxクラスのtextをMapに変換してtoString()すると？)
    // */
    //public void test_parseMap_flat() {
    //}
    //
    ///**
    // * What string of toString() is converted from text of SecretBox class in both middle and lower spaces on the "white" color-box to java.util.Map? <br>
    // * (whiteのカラーボックスのmiddleおよびlowerスペースに入っているSecretBoxクラスのtextをMapに変換してtoString()すると？)
    // */
    //public void test_parseMap_nested() {
    //}
}
