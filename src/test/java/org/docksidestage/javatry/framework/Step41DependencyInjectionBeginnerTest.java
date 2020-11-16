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
package org.docksidestage.javatry.framework;

import java.util.Map;

import org.docksidestage.bizfw.basic.objanimal.Animal;
import org.docksidestage.bizfw.basic.objanimal.Cat;
import org.docksidestage.bizfw.basic.supercar.SupercarDealer;
import org.docksidestage.bizfw.basic.supercar.SupercarManufacturer;
import org.docksidestage.bizfw.di.cast.TooLazyDog;
import org.docksidestage.bizfw.di.container.SimpleDiContainer;
import org.docksidestage.bizfw.di.container.component.DiContainerModule;
import org.docksidestage.bizfw.di.usingdi.*;
import org.docksidestage.bizfw.di.usingdi.settings.UsingDiModule;
import org.docksidestage.unit.PlainTestCase;

/**
 * The test of Dependency Injection (DI) as beginner level. <br>
 * Show answer by log() or write answer on comment for question of javadoc.
 * @author jflute
 * @author nguyen_phuong_linh
 */
public class Step41DependencyInjectionBeginnerTest extends PlainTestCase {

    // ===================================================================================
    //                                                                        Precondition
    //                                                                        ============
    /**
     * Search "Dependency Injection" by internet and learn it in thirty minutes. (study only) <br>
     * ("Dependency Injection" をインターネットで検索して、30分ほど学んでみましょう。(勉強のみ))
     */
    public void test_whatis_DependencyInjection() {
        // _/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/
        // What is Dependency Injection?
        // - - - - - (your answer?)
/*
        Dependency: if class A neads some functionality of class B to done it's tasks,
         class A is dependent on class B, and class B is a dependency of class A.
        => Before functions of class B being used in class A, an instance of class B must be instantiated.
        It can be done in class A, but it will cause some problems:
        1. If class B is changed, class A's code may be changed too.
        2. If class B depends on class C, class A has to aware of class C too.
        3. Class A should focus on its responsibility instead of initializing dependencies.
        => Dependencies should be created by another object and delivered to class A to use.
        => Dependency Injection
        Dependency Injection is a style of object configuration which allows objects and their dependencies
        to be set by an external entity.

        Types of DI:
        1. Constructor injection: to pass dependencies through object's constructor.
        => Should be used if dependency is mandatory.
        2. Setter injection: to pass dependencies through object's setters.
        => Should be used if dependency is optional.
        3. Interface injection: Dependency injects itself to clients passed into it. The client needs to have an setter for the dependency.
*/
        // _/_/_/_/_/_/_/_/_/_/
    }

    // ===================================================================================
    //                                                                 Non DI Code Reading
    //                                                                 ===================
    /**
     * What is the difference between NonDiDirectFirstAction and NonDiDirectSecondAction? <br>
     * (NonDiDirectFirstAction と NonDiDirectSecondAction の違いは？)
     */
    public void test_nondi_difference_between_first_and_second() {
        // your answer? =>

        // and your confirmation code here freely
    }

    /**
     * What is the difference between NonDiDirectSecondAction and NonDiFactoryMethodAction? <br>
     * (NonDiDirectSecondAction と NonDiFactoryMethodAction の違いは？)
     */
    public void test_nondi_difference_between_second_and_FactoryMethod() {
        // your answer? =>
/*
        - Code is reused. Initializing code now is done by only 1 method, which make it easier to refactor when these code is changed.
        - Dog process: Using abstract class instead of concrete class => more abstract
         => only need to change createAnimal() function if wanting another animal, like Cat, without bother other functions' code.

*/
        // and your confirmation code here freely
    }

    /**
     * What is the difference between NonDiFactoryMethodAction and NonDiIndividualFactoryAction? <br>
     * (NonDiFactoryMethodAction と NonDiIndividualFactoryAction の違いは？)
     */
    public void test_nondi_difference_between_FactoryMethod_and_IndividualFactory() {
        // your answer? =>
        /*
        * Object creation is assigned to another class.
        * NonDiIndividualFactoryAction now don't need to concern about what animal will be created and how to create them.
        * Instead, it gets the Animal object provided by NonDiAnimalFactory's method. Therefore, it can concentrate on their own responsibility.
        * As a result, the relationship between NonDiIndividualFactoryAction and Animal became looser.
        * (Same in Supercar Process)
         */
        // and your confirmation code here freely
    }

    // ===================================================================================
    //                                                               Using DI Code Reading
    //                                                               =====================
    /**
     * What is the difference between UsingDiAccessorAction and UsingDiAnnotationAction? <br>
     * (UsingDiAccessorAction と UsingDiAnnotationAction の違いは？)
     */
    public void test_usingdi_difference_between_Accessor_and_Annotation() {
        // your answer? =>
        /*
        * Accessor:
        * - Using setter to inject dependencies.
        * - Inject dependencies by preparing objects and calling setter function directly to pass them => Simple and easy.
        * Annotation:
        * - Need DI container to take responsibility of finding annotation and assigning objects.
        * - Just need to declare all objects and the DI container will do the configure based on annotation written beforehand.
        * - This way is better when there are a number of objects depending on others.
        * It avoids time consuming in configuring every dependency but more difficult to understand and write code than setter injection.
        *
         */
        /*----Using accessor------*/
        log("--------Using accessor-------");
        TooLazyDog dog = new TooLazyDog("tofu");
        dog.petMe();
        dog.playWith(new Cat());
        UsingDiAccessorAction usingDiAccessorAction = new UsingDiAccessorAction();
        usingDiAccessorAction.setAnimal(dog);
        usingDiAccessorAction.callFriend();
        /*------Using annotation------*/
        log("--------Using annotation-------");
        SimpleDiContainer simpleDiContainer = SimpleDiContainer.getInstance();
        simpleDiContainer.registerModule(new DiContainerModule() {
            @Override
            public void bind(Map<Class<?>, Object> componentMap) {
                TooLazyDog dog = new TooLazyDog("tofu");
                dog.petMe();
                dog.playWith(new Cat());
                componentMap.put(Animal.class, dog);
            }
        });
        simpleDiContainer.registerModule(new DiContainerModule() {
            @Override
            public void bind(Map<Class<?>, Object> componentMap) {
                componentMap.put(UsingDiAnnotationAction.class, new UsingDiAnnotationAction());
            }
        });
        simpleDiContainer.resolveDependency();
        UsingDiAnnotationAction usingDiAnnotationAction = (UsingDiAnnotationAction) simpleDiContainer.getComponent(UsingDiAnnotationAction.class);
        usingDiAnnotationAction.callFriend();
        // and your confirmation code here freely
    }

    /**
     * What is the difference between UsingDiAnnotationAction and UsingDiDelegatingAction? <br>
     * (UsingDiAnnotationAction と UsingDiDelegatingAction の違いは？)
     */
    public void test_usingdi_difference_between_Annotation_and_Delegating() {
        // your answer? =>
        /*
        * Delegating is more "abstract" than Annotation because it contains both animal and supercarDealer,
        * which makes the class now just depends on 1 other class.
        * delegatingLogic can contains more objects than just animal and supercarDealer, and make the code scaled.
         */
        // and your confirmation code here freely
        SimpleDiContainer simpleDiContainer = SimpleDiContainer.getInstance();
        simpleDiContainer.registerModule(new DiContainerModule() {
            @Override
            public void bind(Map<Class<?>, Object> componentMap) {
                TooLazyDog dog = new TooLazyDog("tofu");
                dog.petMe();
                dog.playWith(new Cat());
                componentMap.put(Animal.class, dog);
            }
        });
        simpleDiContainer.registerModule(new DiContainerModule() {
            @Override
            public void bind(Map<Class<?>, Object> componentMap) {
                componentMap.put(SupercarDealer.class, new SupercarDealer() {
                    @Override
                    protected SupercarManufacturer createSupercarManufacturer() {
                        return new SupercarManufacturer() {
                            @Override
                            public Supercar makeSupercar(String catalogKey) {
                                log("...Making supercar by {}", catalogKey); // extension here
                                return super.makeSupercar(catalogKey);
                            }
                        };
                    }
                });
            }
        });
        simpleDiContainer.registerModule(new DiContainerModule() {
            @Override
            public void bind(Map<Class<?>, Object> componentMap) {
                componentMap.put(UsingDiDelegatingAction.class, new UsingDiDelegatingAction());
            }
        });
        simpleDiContainer.registerModule(new DiContainerModule() {
            @Override
            public void bind(Map<Class<?>, Object> componentMap) {
                componentMap.put(UsingDiDelegatingLogic.class, new UsingDiDelegatingLogic());
            }
        });
        simpleDiContainer.resolveDependency();
        UsingDiDelegatingAction usingDiDelegatingAction = (UsingDiDelegatingAction) simpleDiContainer.getComponent(UsingDiDelegatingAction.class);
        log("--------Call friend--------");
        usingDiDelegatingAction.callFriend();
        log("---------Send gift----------");
        usingDiDelegatingAction.sendGift();

    }

    // ===================================================================================
    //                                                           Execute like WebFramework
    //                                                           =========================
    /**
     * Execute callFriend() of accessor action by UsingDiWebFrameworkProcess. (Animal as TooLazyDog) <br>
     * (accessor の Action の callFriend() を UsingDiWebFrameworkProcess 経由で実行してみましょう (Animal は TooLazyDog として))
     */
    public void test_usingdi_UsingDiWebFrameworkProcess_callfriend_accessor() {
        // execution code here
        UsingDiWebFrameworkProcess usingDiWebFrameworkProcess = new UsingDiWebFrameworkProcess();
        SimpleDiContainer simpleDiContainer = SimpleDiContainer.getInstance();
        simpleDiContainer.registerModule(new DiContainerModule() {
            @Override
            public void bind(Map<Class<?>, Object> componentMap) {
                TooLazyDog dog = new TooLazyDog("tofu");
                dog.petMe();
                dog.playWith(new Cat());
                componentMap.put(Animal.class, dog);
            }
        });
        simpleDiContainer.registerModule(new DiContainerModule() {
            @Override
            public void bind(Map<Class<?>, Object> componentMap) {
                componentMap.put(UsingDiAccessorAction.class, new UsingDiAccessorAction());
            }
        });
        simpleDiContainer.resolveDependency();
        usingDiWebFrameworkProcess.requestAccessorCallFriend();
    }

    /**
     * Execute callFriend() of annotation and delegating actions by UsingDiWebFrameworkProcess. <br>
     *  (Animal as TooLazyDog...so you can increase hit-points of sleepy cat in this method) <br>
     * <br>
     * (annotation, delegating の Action の callFriend() を UsingDiWebFrameworkProcess 経由で実行してみましょう <br>
     *  (Animal は TooLazyDog として...ということで眠い猫のヒットポイントをこのメソッド内で増やしてもOK))
     */
    public void test_usingdi_UsingDiWebFrameworkProcess_callfriend_annotation_delegating() {
        // execution code here
        UsingDiWebFrameworkProcess usingDiWebFrameworkProcess = new UsingDiWebFrameworkProcess();
        SimpleDiContainer simpleDiContainer = SimpleDiContainer.getInstance();
        simpleDiContainer.registerModule(new DiContainerModule() {
            @Override
            public void bind(Map<Class<?>, Object> componentMap) {
                TooLazyDog dog = new TooLazyDog("tofu");
                dog.petMe();
                dog.playWith(new Cat());
                componentMap.put(Animal.class, dog);
            }
        });
        simpleDiContainer.registerModule(new DiContainerModule() {
            @Override
            public void bind(Map<Class<?>, Object> componentMap) {
                componentMap.put(UsingDiAnnotationAction.class, new UsingDiAnnotationAction());
            }
        });
        log("------Using annotation--------");
        simpleDiContainer.resolveDependency();
        usingDiWebFrameworkProcess.requestAnnotationCallFriend();

        simpleDiContainer.registerModule(new DiContainerModule() {
            @Override
            public void bind(Map<Class<?>, Object> componentMap) {
                componentMap.put(UsingDiDelegatingLogic.class, new UsingDiDelegatingLogic());
            }
        });
        simpleDiContainer.registerModule(new DiContainerModule() {
            @Override
            public void bind(Map<Class<?>, Object> componentMap) {
                componentMap.put(UsingDiDelegatingAction.class, new UsingDiDelegatingAction());
            }
        });
        log("------Using delegate--------");
        simpleDiContainer.resolveDependency();
        usingDiWebFrameworkProcess.requestDelegatingCallFriend();

    }

    /**
     * What is concrete class of instance variable "animal" of UsingDiAnnotationAction? (when registering UsingDiModule) <br>
     * (UsingDiAnnotationAction のインスタンス変数 "animal" の実体クラスは？ (UsingDiModuleを登録した時))
     */
    public void test_usingdi_whatis_animal() {
        // your answer? => TooLazyDog
        // and your confirmation code here freely
        UsingDiWebFrameworkProcess usingDiWebFrameworkProcess = new UsingDiWebFrameworkProcess();
        SimpleDiContainer simpleDiContainer = SimpleDiContainer.getInstance();
        simpleDiContainer.registerModule(new UsingDiModule());
        simpleDiContainer.resolveDependency();
        ((UsingDiAnnotationAction)simpleDiContainer.getComponent(UsingDiAnnotationAction.class)).callFriend();
    }

    // ===================================================================================
    //                                                                        DI Container
    //                                                                        ============
    /**
     * What is DI container? <br>
     * (DIコンテナとは？)
     */
    public void test_whatis_DIContainer() {
        // your answer? =>
        // DI container is an object that create registered dependencies and inject them automatically when required.
        // In above examples, SimpleDiContainer create dependencies when they are registered through registerModule() function,
        // and inject them when resolveDependency() function called.

        // and your confirmation code here freely
    }

    // ===================================================================================
    //                                                                           Good Luck
    //                                                                           =========
    /**
     * What is class or file of DI settings that defines MemberBhv class as DI component in the following Lasta Di application? <br>
     * (以下のLasta DiアプリケーションでMemberBhvクラスをDIコンポーネントとして定義しているDI設定クラスもしくはファイルは？) <br>
     * 
     * https://github.com/lastaflute/lastaflute-example-harbor
     */
    public void test_zone_search_component_on_LastaDi() {
        // your answer? => 
    }

    /**
     * What is class or file of DI settings that defines MemberBhv class as DI component in the following Spring application? <br>
     * (以下のSpringアプリケーションでMemberBhvクラスをDIコンポーネントとして定義しているDI設定クラスもしくはファイルは？) <br>
     * 
     * https://github.com/dbflute-example/dbflute-example-on-springboot
     */
    public void test_zone_search_component_on_Spring() {
        // your answer? => 
    }
}
