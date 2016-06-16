package org.nearbyshops.application;

import org.nearbyshops.ContractClasses.*;

/**
 * Created by sumeet on 17/6/16.
 */
public class DerbyQueries {



    void createTables()
    {

        System.out.println("Into the create table");

        String query2 = "CREATE TABLE ITEM_CATEGORY("
                + " ID INT PRIMARY KEY "
                + "GENERATED ALWAYS AS IDENTITY"
                + "(START WITH 1, INCREMENT BY 1),"
                + " ITEM_CATEGORY_NAME VARCHAR(40),"
                + " ITEM_CATEGORY_DESC VARCHAR(500)"
                + ")";


        String createTableItemCategoryDerby = "CREATE TABLE "
                + ItemCategoryContract.TABLE_NAME + "("
                + " " + ItemCategoryContract.ITEM_CATEGORY_ID + " INT PRIMARY KEY "
                + "GENERATED ALWAYS AS IDENTITY"
                + "(START WITH 1, INCREMENT BY 1),"
                + " " + ItemCategoryContract.ITEM_CATEGORY_NAME + " VARCHAR(40),"
                + " " + ItemCategoryContract.ITEM_CATEGORY_DESCRIPTION + " VARCHAR(500)"
                + ")";




        //}

        // create table ITEM
        //rs = dmd.getTables(null,null, ItemContract.TABLE_NAME,null);
        System.out.println("Outside the create table");

        //if (!rs.next()) {


        System.out.println("Into the create table");

        String createTableItem = "CREATE TABLE ITEM("
                + " ITEM_ID INT PRIMARY KEY "
                + "GENERATED ALWAYS AS IDENTITY"
                + "(START WITH 1, INCREMENT BY 1),"
                + " ITEM_NAME VARCHAR(40),"
                + " ITEM_DESC VARCHAR(500),"
                + " ITEM_IMAGE_URL VARCHAR(100),"
                + " ITEM_BRAND_NAME VARCHAR(100),"
                + " ITEM_CATEGORY_ID INT,"
                + " FOREIGN KEY(ITEM_CATEGORY_ID) REFERENCES ITEM_CATEGORY(ID))";

        String createTableItemDerby = "CREATE TABLE "
                + ItemContract.TABLE_NAME + "("
                + " " + ItemContract.ITEM_ID + " INT PRIMARY KEY "
                + "GENERATED ALWAYS AS IDENTITY"
                + "(START WITH 1, INCREMENT BY 1),"
                + " " + ItemContract.ITEM_NAME + " VARCHAR(40),"
                + " " + ItemContract.ITEM_DESC + " VARCHAR(500),"
                + " " + ItemContract.ITEM_IMAGE_URL + " VARCHAR(100),"
                + " " + ItemContract.ITEM_CATEGORY_ID + " INT,"
                + " FOREIGN KEY(" + ItemContract.ITEM_CATEGORY_ID +") REFERENCES ITEM_CATEGORY(ID))";


        //}

        //create table Distributor
        //rs = dmd.getTables(null,null, DistributorContract.TABLE_NAME,null);
        //System.out.println("Outside the create table");

        //if (!rs.next()) {


        System.out.println("Into the create table");


        String createTableDistributorDerby = "CREATE TABLE "
                + DistributorContract.TABLE_NAME + "("
                + " " + DistributorContract.DISTRIBUTOR_ID + " INT PRIMARY KEY "
                + "GENERATED ALWAYS AS IDENTITY"
                + "(START WITH 1, INCREMENT BY 1),"
                + " " + DistributorContract.DISTRIBUTOR_NAME + " VARCHAR(40)"
                + ")";




        //}


        // create table SHOP
        //rs = dmd.getTables(null,null, ShopContract.TABLE_NAME,null);
        //System.out.println("Outside the create table");

        //if (!rs.next()) {





        String createTableShopDerby = "CREATE TABLE " + ShopContract.TABLE_NAME + "("
                + " " + ShopContract.SHOP_ID + " INT PRIMARY KEY "
                + "GENERATED ALWAYS AS IDENTITY"
                + "(START WITH 1, INCREMENT BY 1),"
                + " " + ShopContract.SHOP_NAME + " VARCHAR(40),"
                + " " + ShopContract.DELIVERY_CHARGES + " FLOAT,"
                + " " + ShopContract.DISTRIBUTOR_ID + " INT,"
                + " " + ShopContract.IMAGE_PATH + " VARCHAR(60),"
                + " FOREIGN KEY(" + ShopContract.DISTRIBUTOR_ID +") REFERENCES DISTRIBUTOR(ID))";



        System.out.println("Into the create table");




        String createTableShopItemDerby = "CREATE TABLE " + ShopItemContract.TABLE_NAME + "("
                + " " + ShopItemContract.ITEM_ID + " INT,"
                + " " + ShopItemContract.SHOP_ID + " INT,"
                + " " + ShopItemContract.AVAILABLE_ITEM_QUANTITY + " INT,"
                + " " + ShopItemContract.ITEM_PRICE + " FLOAT,"
                + " FOREIGN KEY(" + ShopItemContract.SHOP_ID +") REFERENCES SHOP(SHOP_ID),"
                + " FOREIGN KEY(" + ShopItemContract.ITEM_ID +") REFERENCES ITEM(ITEM_ID),"
                + " PRIMARY KEY (" + ShopItemContract.SHOP_ID + ", " + ShopItemContract.ITEM_ID + ")"
                + ")";


        //+ " " + ShopItemContract.QUANTITY_UNIT + " VARCHAR(40),"
        //+ " " + ShopItemContract.QUANTITY_MULTIPLE + " INT,"


    }
}
