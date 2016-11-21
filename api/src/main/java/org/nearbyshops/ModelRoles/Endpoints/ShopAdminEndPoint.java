package org.nearbyshops.ModelRoles.Endpoints;

import org.nearbyshops.ModelRoles.Distributor;
import org.nearbyshops.ModelRoles.ShopAdmin;

import java.util.List;

/**
 * Created by sumeet on 30/6/16.
 */
public class ShopAdminEndPoint {

    private Integer itemCount;
    private Integer offset;
    private Integer limit;
    private Integer max_limit;
    private List<ShopAdmin> results;


    public Integer getItemCount() {
        return itemCount;
    }

    public void setItemCount(Integer itemCount) {
        this.itemCount = itemCount;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getMax_limit() {
        return max_limit;
    }

    public void setMax_limit(Integer max_limit) {
        this.max_limit = max_limit;
    }

    public List<ShopAdmin> getResults() {
        return results;
    }

    public void setResults(List<ShopAdmin> results) {
        this.results = results;
    }
}
