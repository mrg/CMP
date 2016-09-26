/*****************************************************************
 *   Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 ****************************************************************/

package org.apache.cayenne.modeler.adapters;

import java.util.ArrayList;
import java.util.List;

import org.apache.cayenne.configuration.DataNodeDescriptor;
import org.apache.cayenne.map.DataMap;
import org.apache.cayenne.modeler.project.CayenneProject;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.adapter.JavaBeanBooleanPropertyBuilder;
import javafx.beans.property.adapter.JavaBeanStringPropertyBuilder;

public class DataDomainAdapter extends CayennePropertyAdapter // implements AdapterSupport<CayenneProject>
{
//    public static final String NAME               = "dataDomainName";
//    public static final String VALIDATING_OBJECTS = "dataDomainValidatingObjects";

    private final CayenneProject cayenneProject;
//    private BeanPathAdapter<CayenneProject> dataDomainAdapter;
//    private final ObservableList<DataMapAdapter> dataMapAdapters = FXCollections.emptyObservableList();
    private final List<DataMapAdapter> dataMapAdapters = new ArrayList<>(); //FXCollections.emptyObservableList();
    private final List<DataNodeAdapter> dataNodeAdapters = new ArrayList<>(); //FXCollections.emptyObservableList();

    private StringProperty  nameProperty;
    private BooleanProperty validatingObjectsProperty;

    public DataDomainAdapter(final CayenneProject cayenneProject)
    {
        this.cayenneProject = cayenneProject;

        for (final DataMap dataMap : cayenneProject.getDataMaps())
            dataMapAdapters.add(new DataMapAdapter(dataMap));

        for (final DataNodeDescriptor dataNode : cayenneProject.getDataNodes())
            dataNodeAdapters.add(new DataNodeAdapter(dataNode));

        try
        {
            nameProperty              = JavaBeanStringPropertyBuilder.create().bean(cayenneProject).name("dataDomainName").build();
            validatingObjectsProperty = JavaBeanBooleanPropertyBuilder.create().bean(cayenneProject).name("dataDomainValidatingObjects").build();
        }
        catch (final NoSuchMethodException e)
        {
            throw new RuntimeException("Fix the builder.");
        }

//        this.dataDomainAdapter = new BeanPathAdapter<CayenneProject>(cayenneProject);
    }

//    @Override
//    public BeanPathAdapter<CayenneProject> getBeanPathAdapter()
//    {
//        return dataDomainAdapter;
//    }

    public StringProperty nameProperty() { return nameProperty; }
    public String getName() { return nameProperty.get(); }
    public void setName(final String value) { nameProperty.set(value); }

    public BooleanProperty validatingObjectsProperty() { return validatingObjectsProperty; }
    public Boolean getValidatingObjectsProperty() { return validatingObjectsProperty.get(); }
    public void setValidatingObjectsProperty(final Boolean value) { validatingObjectsProperty.set(value); }

    public List<DataMapAdapter> getDataMapAdapters()
    {
//        if (dataMapAdapters.size() != cayenneProject.getDataMaps().size())
//        {
//            for (final DataMap dataMap : cayenneProject.getDataMaps())
//            {
//                dataMapAdapters.add(new DataMapAdapter(dataMap));
//            }
//        }

        return dataMapAdapters;
    }

    public List<DataNodeAdapter> getDataNodeAdapters()
    {
        return dataNodeAdapters;
    }
}
