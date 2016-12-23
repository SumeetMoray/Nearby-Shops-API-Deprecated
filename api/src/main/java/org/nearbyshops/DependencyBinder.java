package org.nearbyshops;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.nearbyshops.DAOsPrepared.ShopDAO;
import org.nearbyshops.DAOsPreparedRoles.ShopStaffDAOPrepared;
import org.nearbyshops.RESTEndpointRoles.ShopStaffResource;

import javax.inject.Singleton;

/**
 * Created by sumeet on 9/9/16.
 */

public class DependencyBinder extends AbstractBinder {

    @Override
    protected void configure() {

        // supply dependencies to ShopStaffResource
        bind(ShopStaffResource.class).to(ShopStaffDAOPrepared.class).in(Singleton.class);
//        bind(ShopDAO.class).to(ShopStaffResource.class).in(Singleton.class);


    }


}
