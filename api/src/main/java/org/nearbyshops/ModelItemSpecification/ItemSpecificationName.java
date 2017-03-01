package org.nearbyshops.ModelItemSpecification;

import org.nearbyshops.Model.Item;
import org.nearbyshops.Model.ItemCategory;

/**
 * Created by sumeet on 13/2/17.
 */
public class ItemSpecificationName {


    // Table Name
    public static final String TABLE_NAME = "ITEM_SPECIFICATION_NAME";


    // column names
    public static final String ITEM_SPECIFICATION_ID = "ITEM_SPECIFICATION_ID";
    public static final String ITEM_SPECIFICATION_NAME = "ITEM_SPECIFICATION_NAME";
    public static final String IMAGE_PATH = "IMAGE_PATH";


    // Create Table Statement
    public static final String createTableItemSpecificationNamePostgres = "CREATE TABLE IF NOT EXISTS "
            + ItemSpecificationName.TABLE_NAME + "("
            + " " + ItemSpecificationName.ITEM_SPECIFICATION_ID + " SERIAL PRIMARY KEY,"
            + " " + ItemSpecificationName.ITEM_SPECIFICATION_NAME + " text,"
            + " " + ItemSpecificationName.IMAGE_PATH + " text"
            + ")";



    // Instance Variables

    private int itemSpecificationID;
    private String itemSpecificationName;
    private String imagePath;


    public int getItemSpecificationID() {
        return itemSpecificationID;
    }

    public void setItemSpecificationID(int itemSpecificationID) {
        this.itemSpecificationID = itemSpecificationID;
    }

    public String getItemSpecificationName() {
        return itemSpecificationName;
    }

    public void setItemSpecificationName(String itemSpecificationName) {
        this.itemSpecificationName = itemSpecificationName;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
