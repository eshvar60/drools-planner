/*
 * Copyright 2012 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.drools.planner.core.heuristic.selector.common;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class RandomIterator<E> implements Iterator<E> {

    private final List<E> list;
    private final Random workingRandom;

    private int lastReturnedIndex;

    public RandomIterator(List<E> list, Random workingRandom) {
        this.list = list;
        this.workingRandom = workingRandom;
    }

    public boolean hasNext() {
        return true;
    }

    public E next() {
        lastReturnedIndex = workingRandom.nextInt(list.size());
        return list.get(lastReturnedIndex);
    }

    public void remove() {
        list.remove(lastReturnedIndex);
    }

}
