package com.demo.deliveryapp;

import com.demo.deliveryapp.data.Adress;

import io.realm.DynamicRealm;
import io.realm.DynamicRealmObject;
import io.realm.Realm;
import io.realm.RealmMigration;
import io.realm.RealmObject;
import io.realm.RealmObjectSchema;
import io.realm.RealmSchema;

class MyMigration implements RealmMigration {
    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
        RealmSchema schema = realm.getSchema();

        if (oldVersion == 0) {

            RealmObjectSchema adressSchema = schema.get(Adress._SELF);



            DynamicRealmObject adress1 = realm.createObject(Adress._SELF,System.currentTimeMillis());
            adress1.set(Adress.TITLE,"Home");
            adress1.set(Adress.ADRESS_LINE_ONE,"1001 US Hwy 72 East");
            adress1.set(Adress.ADRESS_LINE_TWO,"1717 South College Street");
            adress1.set(Adress.CITY,"Mcmeans");
            adress1.set(Adress.POSTAL_CODE,"36830");


            DynamicRealmObject adress2 = realm.createObject(Adress._SELF,System.currentTimeMillis());
            adress2.set(Adress.TITLE,"Home");
            adress2.set(Adress.ADRESS_LINE_ONE,"1001 US Hwy 72 East");
            adress2.set(Adress.ADRESS_LINE_TWO,"1717 South College Street");
            adress2.set(Adress.CITY,"Mcmeans");
            adress2.set(Adress.POSTAL_CODE,"36830");




            oldVersion++;
        }

    }
}
