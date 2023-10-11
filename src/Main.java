import entity.*;
import jsonUtils.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


/**
 * @Author l30049897
 * @Date 2023/8/2 17:42
 * @Version 1.0
 */
public class Main {
    public static void main(String[] args) throws IOException {


        String jsonFilePath = "src/data.json";
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

        JsonUtils.generateSendInfo(building);

        // 对象写入JSON
        JsonUtils.writeToFile(building, jsonFilePath);


    }
}

