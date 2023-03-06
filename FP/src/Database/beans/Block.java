/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database.beans;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 *
 * @author Sachintha
 */
public class Block {

    private int id;
    private String name;
    private BigDecimal price;
    private boolean status;
    private String code;

    /**
     * * derived fields *
     */
    private int max;
    private int noOfLocations;

    private ArrayList<Integer> locations;

    public Block() {
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the price
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * @return the status
     */
    public boolean isStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(boolean status) {
        this.status = status;
    }

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return the max
     */
    public int getMax() {
        return max;
    }

    /**
     * @param max the max to set
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * @return the noOfLocations
     */
    public int getNoOfLocations() {
        return noOfLocations;
    }

    /**
     * @param noOfLocations the noOfLocations to set
     */
    public void setNoOfLocations(int noOfLocations) {
        this.noOfLocations = noOfLocations;
    }

    /**
     * @return the locations
     */
    public ArrayList<Integer> getLocations() {
        return locations;
    }

    /**
     * @param locations the locations to set
     */
    public void setLocations(ArrayList<Integer> locations) {
        this.locations = locations;
    }

}
