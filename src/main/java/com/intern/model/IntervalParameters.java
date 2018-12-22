package com.intern.model;

/**
 * stores interval parameters
 */
public class IntervalParameters {
    /**
     * how many times message should be sent
     */
    private int repetitions;
    /**
     * interval between messages sent
     */
    private int intervalInSeconds;

    /**
     * initalize private field values
     * @param repetitionSpinnerValue value of repetition from GUI spinner
     * @param intervalSpinnerValue value of interval from GUI spinner
     */
    public IntervalParameters(Integer repetitionSpinnerValue, Integer intervalSpinnerValue) {
        repetitions = repetitionSpinnerValue;
        intervalInSeconds = intervalSpinnerValue;
    }

    /**
     *
     * @return repetitions value
     */
    public int getRepetitions() {
        return repetitions;
    }

    /**
     *
     * @return interval value
     */
    public int getIntervalInSeconds() {
        return intervalInSeconds;
    }
}
