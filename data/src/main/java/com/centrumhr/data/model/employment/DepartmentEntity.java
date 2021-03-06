package com.centrumhr.data.model.employment;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.UUID;

/**
 * Created by Seweryn on 23.10.2016.
 */
@DatabaseTable(tableName = "department")
public class DepartmentEntity {

    @DatabaseField(generatedId = true)
    private Long id;

    @DatabaseField( dataType = DataType.UUID , unique = true)
    private UUID uniqueId;

    @DatabaseField
    private String name;

    public DepartmentEntity( ) { }

    public DepartmentEntity(UUID uniqueId, String name) {
        this.uniqueId = uniqueId;
        this.name = name;
    }

    public DepartmentEntity(String name ) {
        this.uniqueId = UUID.randomUUID();
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null)return false;
        if(obj instanceof DepartmentEntity){
            DepartmentEntity item = (DepartmentEntity)obj;
            return item.getUniqueId().equals(uniqueId);
        }
        return false;
    }

    public Long getId() {
        return id;
    }

    public UUID getUniqueId() {
        return uniqueId;
    }

    public String getName() {
        return name;
    }
}
