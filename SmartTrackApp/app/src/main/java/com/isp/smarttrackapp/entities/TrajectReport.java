package com.isp.smarttrackapp.entities;

import java.util.List;

public class TrajectReport {
    public List<TrajectReportLine> lines;
    public double totalDuration;
    public double totalDistance;

    public List<TrajectReportLine> getLines() {
        return lines;
    }

    public void setLines(List<TrajectReportLine> lines) {
        this.lines = lines;
    }

    public double getTotalDuration() {
        return totalDuration;
    }

    public void setTotalDuration(double totalDuration) {
        this.totalDuration = totalDuration;
    }

    public double getTotalDistance() {
        return totalDistance;
    }

    public void setTotalDistance(double totalDistance) {
        this.totalDistance = totalDistance;
    }
}
