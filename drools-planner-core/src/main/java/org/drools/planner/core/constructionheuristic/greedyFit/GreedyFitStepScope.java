/*
 * Copyright 2011 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.drools.planner.core.constructionheuristic.greedyFit;

import java.util.Map;

import org.drools.planner.core.domain.variable.PlanningVariableDescriptor;
import org.drools.planner.core.move.Move;
import org.drools.planner.core.phase.AbstractSolverPhaseScope;
import org.drools.planner.core.phase.step.AbstractStepScope;

public class GreedyFitStepScope extends AbstractStepScope {

    private final GreedyFitSolverPhaseScope greedyFitSolverPhaseScope;

    private Object planningEntity;

    private Move step = null;
    private String stepString = null;
    private Move undoStep = null;

    public GreedyFitStepScope(GreedyFitSolverPhaseScope greedyFitSolverPhaseScope) {
        this.greedyFitSolverPhaseScope = greedyFitSolverPhaseScope;
    }

    public GreedyFitSolverPhaseScope getGreedyFitSolverPhaseScope() {
        return greedyFitSolverPhaseScope;
    }

    @Override
    public AbstractSolverPhaseScope getSolverPhaseScope() {
        return greedyFitSolverPhaseScope;
    }

    public Object getPlanningEntity() {
        return planningEntity;
    }

    public void setPlanningEntity(Object planningEntity) {
        this.planningEntity = planningEntity;
    }

    public Move getStep() {
        return step;
    }

    public void setStep(Move step) {
        this.step = step;
    }

    /**
     * @return null if logging level is to high
     */
    public String getStepString() {
        return stepString;
    }

    public void setStepString(String stepString) {
        this.stepString = stepString;
    }

    public Move getUndoStep() {
        return undoStep;
    }

    public void setUndoStep(Move undoStep) {
        this.undoStep = undoStep;
    }

    // ************************************************************************
    // Calculated methods
    // ************************************************************************

}
