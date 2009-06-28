package org.drools.solver.examples.patientadmissionschedule.domain;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.drools.solver.examples.common.domain.AbstractPersistable;
import org.drools.solver.examples.patientadmissionschedule.domain.solver.AdmissionPartConflict;

/**
 * @author Geoffrey De Smet
 */
public class AdmissionPart extends AbstractPersistable implements Comparable<AdmissionPart> {

    private Patient patient;
    private Night firstNight;
    private Night lastNight;
    private Specialism specialism;

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Night getFirstNight() {
        return firstNight;
    }

    public void setFirstNight(Night firstNight) {
        this.firstNight = firstNight;
    }

    public Night getLastNight() {
        return lastNight;
    }

    public void setLastNight(Night lastNight) {
        this.lastNight = lastNight;
    }

    public Specialism getSpecialism() {
        return specialism;
    }

    public void setSpecialism(Specialism specialism) {
        this.specialism = specialism;
    }

    public int compareTo(AdmissionPart other) {
        return new CompareToBuilder()
                .append(patient, other.patient)
                .append(firstNight, other.firstNight)
                .append(id, other.id)
                .toComparison();
    }

    @Override
    public String toString() {
        return patient + "(" + firstNight + "-" + lastNight + ")";
    }

    public int calculateSameNightCount(AdmissionPart other) {
        int firstNightIndex = Math.max(getFirstNight().getIndex(), other.getFirstNight().getIndex());
        int lastNightIndex = Math.min(getLastNight().getIndex(), other.getLastNight().getIndex());
        return Math.max(0, lastNightIndex - firstNightIndex + 1);
    }

}