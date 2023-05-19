package com.test.service;

import com.test.dto.TripSummaryDto;
import com.test.dto.UserInfoResponse;
import com.test.dto.UsersAllowSendingMessageDto;
import com.test.mapper.AuthMapper;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public interface ReportService {
    DateTimeFormatter inputFormatter = DateTimeFormatter.ISO_DATE_TIME;
    DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    void createMessageAndSaveInTable(List<TripSummaryDto> tripDetails, String fullName, int adminId);

    List<UserInfoResponse> findAllUsersOfAdmin(int adminID);

    List<UsersAllowSendingMessageDto> getLevelAndReportAllow(int level);

    List<TripSummaryDto> findByUserIDAllTravelThatWereAccordingToTime(Integer userid, Instant minus);

    //default for editing text to send!
    default String generateTripReport(List<TripSummaryDto> trips, String fullName) {

        // Generate the trip table
        StringBuilder tripTable = new StringBuilder();
        tripTable.append("<table>");
        tripTable.append("<tr><th>Travel Start</th><th>Travel End</th>" +
                "<th>Duration</th><th>Distance Traveled (miles)</th>" +
                "<th>Pedestrian And Cyclist Collision Warning Count</th><th>Lane Departure Warning Count</th>" +
                "<th>Forward Warning Directions Count</th><th>Num Times Exceeded Speed Limit</th>" +
                "<th>Average Speed (mph)</th><th>Max Speed (mph)</th>" +
                "<th>Trip Score</th></tr>");


        for (TripSummaryDto trip : trips) {
            String travelStart = trip.getTravelStart().toString();
            String travelEnd = trip.getTravelEnd().toString();
            LocalDateTime startDateTime = LocalDateTime.parse(travelStart, inputFormatter);
            LocalDateTime endDateTime = LocalDateTime.parse(travelEnd, inputFormatter);
            tripTable.append("<tr>");
            tripTable.append("<td>").append(startDateTime.format(outputFormatter)).append("</td>");
            tripTable.append("<td>").append(endDateTime.format(outputFormatter)).append("</td>");
            tripTable.append("<td>").append(trip.getDuration()).append("</td>");
            tripTable.append("<td>").append(trip.getDistanceTraveledMile()).append("</td>");
            tripTable.append("<td>").append(trip.getPedestrianAndCyclistCollisionWarningCount()).append("</td>");
            tripTable.append("<td>").append(trip.getLaneDepartureWarningCount()).append("</td>");
            tripTable.append("<td>").append(trip.getForwardWarningDirectionsCount()).append("</td>");
            tripTable.append("<td>").append(trip.getNumTimesExceededSpeedLimit()).append("</td>");
            tripTable.append("<td>").append(trip.getAverageSpeed()).append("</td>");
            tripTable.append("<td>").append(trip.getMaxSpeed()).append("</td>");
            tripTable.append("<td>").append(trip.getTripScore()).append("</td>");
            tripTable.append("</tr>");

        }

        tripTable.append("</table>");

        // Generate the summary
        int totalTrips = 0;
        double totalDistance = 0;
        double averageSpeed = 0;
        double totalTripScore = 0;
        int totalPedestrianAndCyclistCollisions = 0;
        int totalLeftLaneDepartures = 0;
        int totalRightLaneDepartures = 0;
        int totalForwardWarningDirectionsUp = 0;
        int totalForwardWarningDirectionsLeft = 0;
        int totalForwardWarningDirectionsRight = 0;
        int totalTimesExceededSpeedLimit = 0;

        for (TripSummaryDto trip : trips) {
            totalTrips++;
            totalDistance += trip.getDistanceTraveledMile().doubleValue();
            totalTripScore += trip.getTripScore();
            averageSpeed += trip.getAverageSpeed().doubleValue();
            totalPedestrianAndCyclistCollisions += trip.getPedestrianAndCyclistCollisionWarningCount();
            totalLeftLaneDepartures += trip.getNumLeftLaneDeparture();
            totalRightLaneDepartures += trip.getNumRightLaneDeparture();
            totalForwardWarningDirectionsUp += trip.getNumForwardWarningDirectionsUp();
            totalForwardWarningDirectionsLeft += trip.getNumForwardWarningDirectionsLeft();
            totalForwardWarningDirectionsRight += trip.getNumForwardWarningDirectionsRight();
            totalTimesExceededSpeedLimit += trip.getNumTimesExceededSpeedLimit();


        }

        if (totalTrips > 0) {
            averageSpeed /= totalTrips;
            totalTripScore /= totalTrips;
        }

        String summary = "Summary for Driver : " + fullName + "\n";
        summary += "<br>Total Trips: " + totalTrips + "\n";
        summary += "<br>Total Distance Traveled: " + totalDistance + " miles\n";
        summary += "<br>Average Speed: " + averageSpeed + " mph\n";
        summary += "<br>Total Pedestrian and Cyclist Collision Warnings: " + totalPedestrianAndCyclistCollisions + "\n";
        summary += "<br>Total Left Lane Departures: " + totalLeftLaneDepartures + "\n";
        summary += "<br>Total Right Lane Departures: " + totalRightLaneDepartures + "\n";
        summary += "<br>Total Forward Warning Directions Up: " + totalForwardWarningDirectionsUp + "\n";
        summary += "<br>Total Forward Warning Directions Left: " + totalForwardWarningDirectionsLeft + "\n";
        summary += "<br>Total Forward Warning Directions Right: " + totalForwardWarningDirectionsRight + "\n";
        summary += "<br>Total Times Exceeded Speed Limit: " + totalTimesExceededSpeedLimit;
        summary += "<br>Average Trips Score: " + totalTripScore + "\n";

        // Send the report via email or display it
        String emailContent = "<html><body>";
        emailContent += "<h2>Trip Report for Driver: " + fullName + "</h2>";
        emailContent += "<h3>Trips:</h3>";
        emailContent += tripTable.toString();
        emailContent += "<h3>Summary:</h3>";
        emailContent += summary;
        emailContent += "</body></html>";

        return emailContent;
    }
}
