package entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * @Author l30049897
 * @Date 2023/8/2 14:55
 * @Version 1.0
 */
public class Building {
    @JsonProperty("address")
    private String address;

    @JsonProperty("rooms")
    private List<Room> rooms;

    /**
     * 获取building的地址
     *
     * @return
     */
    public String getAddress() {
        return address;
    }

    /**
     * 获取房间列表
     *
     * @return
     */
    public List<Room> getRooms() {
        return rooms;
    }

    /**
     * 打印building信息
     *
     * @return
     */
    @Override
    public String toString() {
        return "Building {" +
                "address='" + address + '\'' +
                ", rooms=" + rooms +
                '}';
    }
}
