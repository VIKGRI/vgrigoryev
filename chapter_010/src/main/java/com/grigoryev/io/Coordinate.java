package com.grigoryev.io;

public class Coordinate {

    private int id;

    private Double xCoord;

    private Double yCoord;

    public Double getxCoord() {
        return xCoord;
    }

    public void setxCoord(Double xCoord) {
        this.xCoord = xCoord;
    }

    public Double getyCoord() {
        return yCoord;
    }

    public void setyCoord(Double yCoord) {
        this.yCoord = yCoord;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isSameCoordinate(Coordinate coordinate) {
        return this.xCoord == coordinate.xCoord && this.yCoord == coordinate.yCoord;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Coordinate that = (Coordinate) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "Coordinate{" +
                "id=" + id +
                ", xCoord=" + xCoord +
                ", yCoord=" + yCoord +
                '}';
    }
}
