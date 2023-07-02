package cn.PoStudio.EntityNBT;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class handleNBT {
    public static void setNBT(Entity entity, NamespacedKey key, Object value){
        PersistentDataContainer dataContainer = entity.getPersistentDataContainer();
        if (value instanceof String){
            dataContainer.set(key, PersistentDataType.STRING, (String) value);
        }else if(value instanceof Integer){
            dataContainer.set(key, PersistentDataType.INTEGER, (Integer) value);
        }else if (value instanceof Boolean){
            dataContainer.set(key, PersistentDataType.BOOLEAN, (Boolean) value);
        }else if (value instanceof Long){
            dataContainer.set(key, PersistentDataType.LONG, (Long) value);
        }else if (value instanceof Short){
            dataContainer.set(key, PersistentDataType.SHORT, (Short) value);
        }else if (value instanceof Float){
            dataContainer.set(key, PersistentDataType.FLOAT, (Float) value);
        }else if (value instanceof Byte){
            dataContainer.set(key, PersistentDataType.BYTE, (Byte) value);
        }else if (value instanceof Double){
            dataContainer.set(key, PersistentDataType.DOUBLE, (Double) value);
        }else{
            System.out.print("data类型错误");
        }
    }
    public Object getNBT(Entity entity, NamespacedKey key){
        PersistentDataContainer dataContainer = entity.getPersistentDataContainer();
        Object object = null;
        if (dataContainer.has(key, PersistentDataType.STRING)){
            object = dataContainer.get(key, PersistentDataType.STRING);
        }else if (dataContainer.has(key, PersistentDataType.INTEGER)){
            object = dataContainer.get(key, PersistentDataType.INTEGER);
        }else if (dataContainer.has(key, PersistentDataType.BOOLEAN)){
            object = dataContainer.get(key, PersistentDataType.BOOLEAN);
        }else if (dataContainer.has(key, PersistentDataType.LONG)){
            object = dataContainer.get(key, PersistentDataType.LONG);
        }else if (dataContainer.has(key, PersistentDataType.SHORT)){
            object = dataContainer.get(key, PersistentDataType.SHORT);
        }else if (dataContainer.has(key, PersistentDataType.FLOAT)){
            object = dataContainer.get(key, PersistentDataType.FLOAT);
        }else if (dataContainer.has(key, PersistentDataType.BYTE)){
            object = dataContainer.get(key, PersistentDataType.BYTE);
        }else if (dataContainer.has(key, PersistentDataType.DOUBLE)){
            object = dataContainer.get(key, PersistentDataType.DOUBLE);
        }else{
            System.out.print("找不到data类型");
        }
        return object;
    }
    public void removeNBT(Entity entity, NamespacedKey key){
        PersistentDataContainer dataContainer = entity.getPersistentDataContainer();
        dataContainer.remove(key);
    }
}
