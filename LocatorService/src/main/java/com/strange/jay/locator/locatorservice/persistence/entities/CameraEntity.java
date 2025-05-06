package com.strange.jay.locator.locatorservice.persistence.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import java.util.Objects;

/**
 * Represents a Camera item in a relational database.
 */
@Entity
@Table(name = "camera",
    indexes = { @Index(columnList = "cameraId", name = "camera_id_hidx"),
                @Index(columnList="floorId", name = "floor_id_hidx")})
public class CameraEntity {

    /** Unique table ID created by the persistence layer. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Unique ID for the camera, exposed throughout the system. */
    private int cameraId;

    /** Unique ID for the floor plan that houses this camera. */
    private int floorId;

    /** The x-distance offset in feet from the southwest corner of the floor. */
    private double xFeet;

    /** The y-distance offset in feet from the southwest corner of the floor. **/
    private double yFeet;

    public void setId(final Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public int getCameraId() {
        return this.cameraId;
    }

    public void setCameraId(final int cameraId) {
        this.cameraId = cameraId;
    }

    public int getFloorId() {
        return this.floorId;
    }

    public void setFloorId(final int floorId) {
        this.floorId = floorId;
    }

    public double getxFeet() {
        return this.xFeet;
    }

    public void setxFeet(final double xFeet) {
        this.xFeet = xFeet;
    }

    public double getyFeet() {
        return this.yFeet;
    }

    public void setyFeet(final double yFeet) {
        this.yFeet = yFeet;
    }

    @Override
    public String toString() {
        return "CameraEntity{" +
            "id=" + this.id +
            ", cameraId=" + this.cameraId +
            ", floorId=" + this.floorId +
            ", xFeet=" + this.xFeet +
            ", yFeet=" + this.yFeet +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final CameraEntity that = (CameraEntity) o;
        return this.cameraId == that.cameraId
            && this.floorId == that.floorId
            && Objects.equals(this.id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.cameraId, this.floorId);
    }
}
