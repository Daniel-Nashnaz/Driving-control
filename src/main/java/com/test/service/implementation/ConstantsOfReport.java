package com.test.service.implementation;

public class ConstantsOfReport {
    public static final String SUDDEN_BRAKIMG_GREEN = "The driver maintains a smooth and steady pace, without sudden braking.";
    public static final String SUDDEN_BRAKIMG_ORANGE = "The driver usually maintains a smooth and steady pace, with only a few instances of sudden braking.";
    public static final String SUDDEN_BRAKIMG_RED = "The driver brakes suddenly numerous times, please work on this with the driver.";
    public static final String PEDESTRIAN_AND_CYCLIST_COLLISION_WARNING_GREEN = "The driver maintains a proper distance, without attaching to pedestrians and bicycles.";
    public static final String PEDESTRIAN_AND_CYCLIST_COLLISION_WARNING_ORANGE = "The driver usually maintains a proper and stable distance, with only a few cases of attaching to pedestrians and bicycles.";
    public static final String PEDESTRIAN_AND_CYCLIST_COLLISION_WARNING_RED = "The driver does not make sure to keep a proper distance between him and pedestrians and bicycles, please work on this with the driver.";
    public static final String EXCEEDED_SPEED_GREEN = "The driver maintains a normal speed, without exceeding the speed limit.";
    public static final String EXCEEDED_SPEED_ORANGE = "The driver usually maintains a normal speed, with only a few cases of exceeding the speed limit.";
    public static final String EXCEEDED_SPEED_RED = "The driver is not careful to keep the speed limit, please work on it with the driver.";
    public static final String LANE_DEPARTURE_WARNING_GREEN = "The driver maintains a normal and stable ride, without deviating from the lane.";
    public static final String LANE_DEPARTURE_WARNING_ORANGE = "The driver usually maintains a normal and stable ride, with only a few cases of deviating from the lane.";
    public static final String LANE_DEPARTURE_WARNING_RED = "The driver does not make sure to maintain a lane, please work on this with the driver.";
    public static final String FORWARD_WARNING_DIRECTIONS_GREEN = "The driver maintains a normal and stable ride, without attachments to the vehicle.";
    public static final String FORWARD_WARNING_DIRECTIONS_ORANGE = "The driver usually maintains a normal and stable ride, with only a few cases of attachment to the vehicle.";
    public static final String FORWARD_WARNING_DIRECTIONS_RED = "The driver does not take care to keep a distance from the vehicles around him, please work on this with the driver.";

    public static final int COUNT_SUDDEN_BRAKIMG_RED = 4;
    public static final int COUNT_SUDDEN_BRAKIMG_GREEN = 1;
    public static final int COUNT_PEDESTRIAN_AND_CYCLIST_COLLISION_WARNING_GREEN = 2;
    public static final int COUNT_PEDESTRIAN_AND_CYCLIST_COLLISION_WARNING_RED = 5;
    public static final int COUNT_EXCEEDED_SPEED_GREEN = 4;
    public static final int COUNT_EXCEEDED_SPEED_RED = 7;
    public static final int COUNT_LANE_DEPARTURE_WARNING_OR_FORWARD_WARNING_DIRECTIONS_GREEN = 5;
    public static final int COUNT_LANE_DEPARTURE_WARNING_OR_FORWARD_WARNING_DIRECTIONS_RED = 8;
    public static final String END_REPORT = "This is the driver's note reports of the vehicle attached below. We recommend taking these comments into account and following the improvement suggestions given to ensure driver safety.";



}
