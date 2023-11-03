import entity.Bill;
import entity.Building;
import entity.MonthlyBill;
import entity.NewBuilding;
import entity.Room;
import jsonUtils.JsonUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author l30049897
 * @Date 2023/8/2 17:42
 * @Version 1.0
 */
public class Main {
    public static void main(String[] args) throws IOException {


        String jsonFilePath = "src/data.json";
        String newJsonFilePath = "src/newA.json";
        // 读取JSON为对象
        Building building = JsonUtils.parseFromFile(jsonFilePath);

        // 写入数据，初始化，计算
//        JsonUtils.writeData(building);

        // 修改水电表数据
//        JsonUtils.rewriteBillMeters(building, "101");

        // 修改日期
//        JsonUtils.rewriteBillDate(building, "101");

        // 显示数据，生成文本


        // 测试功能
//        JsonUtils.addNewBill(building, "101", 45, 4100, 2023, 8);

        // JsonUtils.generateSendInfo(building);

        List<NewBuilding> newBuildings = new ArrayList<>();

        for (Room room : building.getRooms()) {
            List<Bill> newBills = new ArrayList<>();
            for (MonthlyBill oldBill : room.getMonthlyBills()) {
                Bill bill = new Bill(oldBill.getYear(), oldBill.getMonth(), oldBill.getPeriod(), oldBill.getWaterMeter(),
                    oldBill.getElectricMeter(), oldBill.getBaseRent());
                newBills.add(bill);
            }
            NewBuilding newBuilding = new NewBuilding(room.getRoomNum(), newBills);
            newBuildings.add(newBuilding);
        }

        System.out.println(newBuildings);
        // 对象写入JSON
        JsonUtils.writeToFile(building, newJsonFilePath);

        JsonUtils.writeToNewFile(newBuildings, newJsonFilePath);
    }
}

