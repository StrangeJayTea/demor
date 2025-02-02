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
    indexes = { @Index(columnList = "cameraId", name = "camera_id_hidx")})
public class CameraEntity {

    /** Unique table ID created by the persistence layer. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Unique ID for the camera, exposed throughout the system. */
    private int cameraId;

    /** Unique ID for the floor plan that houses this camera. */
    private int floorId;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public int getCameraId() {
        return cameraId;
    }

    public void setCameraId(int cameraId) {
        this.cameraId = cameraId;
    }

    public int getFloorId() {
        return floorId;
    }

    public void setFloorId(int floorId) {
        this.floorId = floorId;
    }

    @Override
    public String toString() {
        return "CameraEntity{" +
            "id=" + id +
            ", cameraId=" + cameraId +
            ", floorId=" + floorId +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CameraEntity that = (CameraEntity) o;
        return cameraId == that.cameraId && floorId == that.floorId && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cameraId, floorId);
    }
}
