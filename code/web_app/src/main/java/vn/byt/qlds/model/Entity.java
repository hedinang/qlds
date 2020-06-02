package vn.byt.qlds.model;

import java.sql.Timestamp;

public abstract class Entity {
    public Boolean delFlag;
    public Long userCreated;
    public Long userUpdated;
    public Timestamp timeCreated;
    public Timestamp timeUpdated;
    public Entity(){
        long currentTime = System.currentTimeMillis();
        this.timeUpdated = new Timestamp(currentTime);
        this.timeCreated = new Timestamp(currentTime);
        this.delFlag = false;
    }

}
