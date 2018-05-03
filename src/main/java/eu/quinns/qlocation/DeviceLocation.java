package eu.quinns.qlocation;

import java.util.Objects;

public class DeviceLocation {
    private String deviceId;
    private long timestamp;
    private double latitude;
    private double longitude;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeviceLocation that = (DeviceLocation) o;
        return getTimestamp() == that.getTimestamp() &&
                Objects.equals(getDeviceId(), that.getDeviceId());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getDeviceId(), getTimestamp());
    }

    @Override
    public String toString() {
        return "DeviceLocation{" +
                "deviceId='" + deviceId + '\'' +
                ", timestamp=" + timestamp +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
