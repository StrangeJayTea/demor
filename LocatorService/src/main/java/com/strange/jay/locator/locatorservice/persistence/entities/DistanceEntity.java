package com.strange.jay.locator.locatorservice.persistence.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "distance",
    indexes = { @Index(columnList = "camera1", name = "camera1_hidx")})
public class DistanceEntity {

    /** Unique table ID created by the persistence layer. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** The unique ID for camera 1. */
    private int camera1;

    /** The unique ID for another camera on the same floor. */
    private int camera2;

    /** The distance between the 2 cameras. */
    private double distance;

    @Override
    public String toString() {
        return "DistanceEntity{" +
            "id=" + this.id +
            ", camera1=" + this.camera1 +
            ", camera2=" + this.camera2 +
            ", distance=" + this.distance +
            '}';
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final DistanceEntity that = (DistanceEntity) o;
        return this.camera1 == that.camera1
            && this.camera2 == that.camera2
            && Double.compare(this.distance, that.distance) == 0
            && Objects.equals(this.id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.camera1, this.camera2, this.distance);
    }
}
