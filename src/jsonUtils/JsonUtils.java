package jsonUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import entity.*;

import java.io.File;
import java.io.IOException;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @Author l30049897
 * @Date 2023/8/23 15:07
 * @Version 1.0
 */


public class JsonUtils {
    private static final double WATER_PRICE = 5.5;

    private static final double ELECTRIC_PRICE = 1.2;

    private static ObjectMapper mapper = new ObjectMapper();

    static {
        // 默认格式化JSON
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    // 从JSON文件解析为Building对象
    public static Building parseFromFile(String filePath) throws IOException {
        return mapper.readValue(new File(filePath), Building.class);
    }

    // 从JSON字符串解析为Building对象
    public static Building parseFromJson(String json) throws IOException {
        return mapper.readValue(json, Building.class);
    }

    // 将Building对象转换为JSON字符串
    public static String toJsonString(Building building) throws IOException {
        return mapper.writeValueAsString(building);
    }

    // 将Building对象写入JSON文件
    public static void writeToFile(Building building, String filePath) throws IOException {
        mapper.writeValue(new File(filePath), building);
    }

    /**
     *  写入水电表数据
     *
     * @param building building
     */
    public static void writeData(Building building) {
        List<Room> roomList = getRooms(building);

        Scanner scan = new Scanner(System.in);
        System.out.println("输入年");
        int year = scan.nextInt();
        System.out.println("输入月");
        int month = scan.nextInt();


        double waterMeter ;
        double electricMeter ;
        for (Room room : roomList) {
            System.out.println("输入" + room.getRoomNum() + "水表度数:");
            waterMeter = scan.nextDouble();

            System.out.println("输入" + room.getRoomNum() + "电表度数:");
            electricMeter = scan.nextDouble();
            JsonUtils.addNewBill(room, waterMeter, electricMeter, year, month);
        }
        scan.close();
    }

    /**
     *  更新房间最新账单的水电表数据
     *
     * @param building building
     * @param roomNum roomNum
     */
    public static void rewriteBillMeters(Building building, String roomNum) {
        Room room = getRoomByNum(building, roomNum);

        Scanner scan = new Scanner(System.in);
        System.out.println("正在修改" + roomNum + "水电表数据...");

        System.out.println("输入" + room.getRoomNum() + "水表度数:");
        double waterMeter = scan.nextDouble();

        System.out.println("输入" + room.getRoomNum() + "电表度数:");
        double electricMeter = scan.nextDouble();

        System.out.println(roomNum + "水电表已经修改完毕...");

        // 更新账单的水电表数据
        checkBill(room, getLatestBill(room), waterMeter, electricMeter);

        // 打印账单数据
        printBillInfo(getLatestBill(room));
    }

    /**
     *  更新房间的账单的日期
     *
     * @param building building
     * @param roomNum roomNum
     */
    public static void rewriteBillDate(Building building, String roomNum) {
        Room room = getRoomByNum(building, roomNum);

        Scanner scan = new Scanner(System.in);
        System.out.println("正在修改" + roomNum + "日期...");

        System.out.println("输入年：");
        int year = scan.nextInt();

        System.out.println("输入月：");
        int month = scan.nextInt();

        System.out.println(roomNum + "日期已经修改完毕...");

        // 更新账单的日期
        getLatestBill(room).setYear(year);
        getLatestBill(room).setMonth(month);

        // 打印账单数据
        printBillInfo(getLatestBill(room));
    }

    /**
     * 更新房间账单的其他费用
     *
     * @param building building
     * @param roomNum roomNum
     */
    public static void rewriteBillOtherFees(Building building, String roomNum) {
        Room room = getRoomByNum(building, roomNum);

        Scanner scan = new Scanner(System.in);
        System.out.println("正在修改" + roomNum + "其他费用...");

        System.out.println("输入其他费用：");
        double otherFees = scan.nextDouble();

        // 更新账单费用
        checkBill(room, otherFees);

        // 打印账单数据
        printBillInfo(getLatestBill(room));
    }

    /**
     * 生成所有账单信息
     *
     * @param building building
     */
    public static void generateSendInfo(Building building) {
        List<Room> roomList = getRooms(building);
        for (Room room : roomList) {
            printSendInfo(room);
        }
    }
    // TODO: 函数二，打印发送数据

    /**
     *  打印发送信息的格式
     *
     * @param room room
     */
    public static void printSendInfo(Room room) {
        String sendText = "测试信息:  " + room.getRoomNum() +
                "  所属期数：" + getLatestBill(room).getPeriod();
        System.out.println(sendText);
    }

    /**
     * 打印账单数据
     *
     * @param bill bill
     */
    public static void printBillInfo(MonthlyBill bill) {
        String billText = "\n账单日期：" + bill.getYear() + "," + bill.getMonth() + "\n" +
                "租户:" + bill.getTenant() + "\n" +
                "水表：" + bill.getWaterMeter() + "电表：" + bill.getElectricMeter() + "\n" +
                "水费：" + bill.getWaterFee() + "电费：" + bill.getElectricFee() + "\n" +
                "其他费用：" + bill.getOtherFees() + "总费用：" + bill.getTotal();

        System.out.println(billText);
    }

    /**
     *  更新账单的水电表数据
     *
     * @param room room
     * @param bill bill
     * @param waterMeter waterMeter
     * @param electricMeter electricMeter
     */
    public static void checkBill(Room room, MonthlyBill bill, double waterMeter, double electricMeter) {
        // 更新水电表
        bill.setWaterMeter(waterMeter);
        bill.setElectricMeter(electricMeter);

        // 更新水电费，总费
        updateNewBill(getPreBill(room), bill);
    }

    /**
     * 获取房间列表
     *
     * @return Rooms
     */
    public static List<Room> getRooms(Building building) {

        return building.getRooms();
    }

    /**
     * 从列表通过房间名称来获取房间
     *
     * @param roomList roomList
     * @param RoomNum RoomNum
     * @return Room
     */
    public static Room getRoomByNum(List<Room> roomList, String RoomNum) {
        for (Room r : roomList) {
            if (RoomNum.equals(r.getRoomNum())) {
                return r;
            }
        }
        return null;
    }

    /**
     * 从 building 通过房间名称来获取房间
     *
     * @param building building
     * @param RoomNum RoomNum
     * @return Room
     */
    public static Room getRoomByNum(Building building, String RoomNum) {
        for (Room r : getRooms(building)) {
            if (RoomNum.equals(r.getRoomNum())) {
                return r;
            }
        }
        return null;
    }

    /**
     *  获取房间号码
     *
     * @param building building
     * @return roomNumsList
     */

    public static List<String> getRoomNums(Building building) {
        List<String> roomNumsList = new ArrayList<>();
        for (Room room : building.getRooms()) {
            String roomNum = room.getRoomNum();
            roomNumsList.add(roomNum);
        }
        return roomNumsList;
    }

    /**
     * 获取房间的月账单列表
     *
     * @param room room
     * @return MonthlyBills
     */
    public static List<MonthlyBill> getMonthlyBills(Room room) {
        return room.getMonthlyBills();
    }

    /**
     *  根据年月日期来获取月账单
     *
     * @param monthlyBills monthlyBills
     * @param year year
     * @param month month
     * @return MonthlyBill
     */
    public static MonthlyBill getBillByDate(List<MonthlyBill> monthlyBills, int year, int month) {
        for (MonthlyBill bill : monthlyBills) {
            if (bill.getYear() == year && bill.getMonth() == month) {
                return bill;
            }
        }
        return null;
    }

    /**
     *  根据年月日期来获取月账单
     *
     * @param room room
     * @param year year
     * @param month month
     * @return MonthlyBill
     */
    public static MonthlyBill getBillByDate(Room room, int year, int month) {
        for (MonthlyBill bill : getMonthlyBills(room)) {
            if (bill.getYear() == year && bill.getMonth() == month) {
                return bill;
            }
        }
        return null;
    }

    /**
     * 根据年月日期来获取月账单
     *
     * @param building building
     * @param roomNum roomNum
     * @param year year
     * @param month month
     * @return MonthlyBill
     */
    public static MonthlyBill getBillByDate(Building building, String roomNum, int year, int month) {
        Room room = getRoomByNum(building, roomNum);
        for (MonthlyBill bill : getMonthlyBills(room)) {
            if (bill.getYear() == year && bill.getMonth() == month) {
                return bill;
            }
        }
        return null;
    }

    /**
     *  获取 building 的某个年月的月账单列表
     *
     * @param building building
     * @param year year
     * @param month month
     * @return dateBillsList
     */
    public static List<MonthlyBill> getBillsListByDate(Building building, int year, int month) {
        List<MonthlyBill> dateBillsList = new ArrayList<>();

        for (Room room : getRooms(building)) {
            dateBillsList.add(getBillByDate(room, year, month));
        }
        return dateBillsList;
    }

    /**
     *  获取 building 的最新月账单的列表
     *
     * @param building building
     * @return latestBillsList
     */
    public static List<MonthlyBill> getLatestBillsList(Building building) {
        List<MonthlyBill> latestBillsList = new ArrayList<>();

        for (Room room : getRooms(building)) {
            latestBillsList.add(getLatestBill(room));
        }
        return latestBillsList;
    }

    /**
     *  从月账单列表获取最新的月账单
     *
     * @param monthlyBills monthlyBills
     * @return MonthlyBill
     */
    public static MonthlyBill getLatestBill(List<MonthlyBill> monthlyBills) {
        return monthlyBills.get(monthlyBills.size() - 1);
    }

    /**
     *  获取一个房间的最新月账单
     *
     * @param room room
     * @return MonthlyBill
     */
    public static MonthlyBill getLatestBill(Room room) {
        List<MonthlyBill> monthlyBills = getMonthlyBills(room);
        return getLatestBill(monthlyBills);
    }

    /**
     *  从房间名称获取最新的月账单
     * @param building building
     * @param RoomNum RoomNum
     * @return MonthlyBill
     */
    public static MonthlyBill getLatestBill(Building building, String RoomNum) {
        Room room = getRoomByNum(building, RoomNum);
        return getLatestBill(room);
    }

    /**
     *  从月账单列表获取上一个的月账单
     *
     * @param monthlyBills monthlyBills
     * @return  MonthlyBill
     */
    public static MonthlyBill getPreBill(List<MonthlyBill> monthlyBills) {
        if (monthlyBills.size() > 1) {
            return monthlyBills.get(monthlyBills.size() - 2);
        } else {
            return monthlyBills.get(0);
        }
    }

    /**
     *  获取一个房间的上个月账单
     *
     * @param room room
     * @return MonthlyBill
     */
    public static MonthlyBill getPreBill(Room room) {
        List<MonthlyBill> monthlyBills = getMonthlyBills(room);
        return getPreBill(monthlyBills);
    }

    /**
     *  从房间名称获取上个月账单
     *
     * @param building building
     * @param RoomNum RoomNum
     * @return MonthlyBill
     */
    public static MonthlyBill getPreBill(Building building, String RoomNum) {
        Room room = getRoomByNum(building, RoomNum);
        return getPreBill(room);
    }

    /**
     *  依据水电表创建一个新月账单
     *
     * @param waterMeter waterMeter
     * @param electricMeter electricMeter
     * @return MonthlyBill
     */
    public static MonthlyBill createNewBill(double waterMeter, double electricMeter) {
        MonthlyBill bill = new MonthlyBill();
        bill.setWaterMeter(waterMeter);
        bill.setElectricMeter(electricMeter);
        return bill;
    }

    /**
     *  在月账单列表末尾添加一个新月账单
     *
     * @param bills bills
     * @param bill bill
     */
    public static void addMonthlyBill(List<MonthlyBill> bills ,MonthlyBill bill) {
        bills.add(bill);
    }

    /**
     * 给房间添加一个新的月账单
     *
     * @param room room
     * @param waterMeter waterMeter
     * @param electricMeter electricMeter
     * @param year year
     * @param month month
     */
    public static void addNewBill(Room room, double waterMeter, double electricMeter, int year, int month) {
        MonthlyBill newBill = createNewBill(waterMeter, electricMeter);

        // 初始化账单数据
        initNewBill(room, newBill);

        // 更新账单数据
        updateBillData(newBill, waterMeter, electricMeter);

        // 更新账单费用
        updateNewBill(getLatestBill(room), newBill);

        // 更新账单日期
        setBillDate(newBill, year, month);

        addMonthlyBill(getMonthlyBills(room), newBill);
    }

    /**
     * 通过房间号给房间添加一个新月账单
     *
     * @param building building
     * @param roomNum roomNum
     * @param waterMeter waterMeter
     * @param electricMeter electricMeter
     * @param year year
     * @param month month
     */
    public static void addNewBill(Building building, String roomNum, double waterMeter, double electricMeter, int year, int month) {
        Room room = getRoomByNum(building, roomNum);

        addNewBill(room, waterMeter, electricMeter, year, month);
    }

    /**
     * 设置账单的日期，期数
     *
     * @param bill bill
     * @param year year
     * @param month month
     */
    public static void setBillDate(MonthlyBill bill, int year, int month) {
        bill.setYear(year);
        bill.setMonth(month);
        int period = month == 1 ? 12 : month - 1;
        bill.setPeriod(period);
    }

    /**
     *  核算账单，添加 otherFees 数据
     *
     * @param bill bill
     * @param otherFees otherFees
     */
    public static void checkBill(MonthlyBill bill, double otherFees) {
        bill.setOtherFees(otherFees);
        calculateTotalFee(bill);
    }

    /**
     *  核算房间最新的账单
     *
     * @param room room
     * @param otherFees room
     */
    public static void checkBill(Room room, double otherFees) {
        MonthlyBill bill = getLatestBill(room);
        checkBill(bill, otherFees);
    }

    /**
     *  核算房间号最新的账单
     *
     * @param building building
     * @param roomNum roomNum
     * @param otherFees otherFees
     */
    public static void checkBill(Building building, String roomNum, double otherFees) {
        MonthlyBill bill = getLatestBill(building, roomNum);
        checkBill(bill, otherFees);
    }

    /**
     *  初始化月账单数据
     *
     * @param room room
     * @param bill bill
     */
    public static void initNewBill(Room room, MonthlyBill bill) {
        // 获取历史数据
        MonthlyBill oldBill = getLatestBill(room);

        // 初始化新账单, 租户与基本房租不变
        bill.setTenant(oldBill.getTenant());
        bill.setBaseRent(oldBill.getBaseRent());
    }

    /**
     * 更新月账单数据
     *
     * @param bill bill
     * @param waterMeter waterMeter
     * @param electricMeter electricMeter
     */
    public static void updateBillData(MonthlyBill bill, double waterMeter, double electricMeter) {
        // 更新数据
        bill.setWaterMeter(waterMeter);
        bill.setElectricMeter(electricMeter);
    }

    /**
     *  更新月账单费用
     *
     * @param oldBill oldBill
     * @param bill bill
     */
    public static void updateNewBill(MonthlyBill oldBill, MonthlyBill bill) {

        // 计算
        double waterFee = calculateWaterFee(oldBill, bill);
        double electricFee = calculateElectricFee(oldBill, bill);

        // 更新水电表费用
        bill.setWaterFee(waterFee);
        bill.setElectricFee(electricFee);

        // 更新总费
        double total = calculateTotalFee(bill);
        bill.setTotal(total);
    }

    /**
     *  计算用水量
     *
     * @param oldBill oldBill
     * @param newBill newBill
     * @return eaterUsed
     */
    public static double calculateWaterUsed(MonthlyBill oldBill, MonthlyBill newBill) {
        double oldWaterMeter = oldBill.getWaterMeter();
        double newWaterMeter = newBill.getWaterMeter();
        return newWaterMeter - oldWaterMeter;
    }

    /**
     *  计算用电量
     *
     * @param oldBill oldBill
     * @param newBill newBill
     * @return electricUsed
     */
    public static double calculateElectricUsed(MonthlyBill oldBill, MonthlyBill newBill) {
        double oldElectricMeter = oldBill.getElectricMeter();
        double newElectricMeter = newBill.getElectricMeter();
        return newElectricMeter - oldElectricMeter;
    }

    /**
     *  计算水费
     *
     * @param oldBill oldBill
     * @param newBill newBill
     * @return waterFee
     */
    public static double calculateWaterFee(MonthlyBill oldBill, MonthlyBill newBill) {
        double waterUsed = calculateWaterUsed(oldBill, newBill);
        return waterUsed * WATER_PRICE;
    }

    /**
     *  计算电费
     *
     * @param oldBill oldBill
     * @param newBill newBill
     * @return electricFee
     */
    public static double calculateElectricFee(MonthlyBill oldBill, MonthlyBill newBill) {
        double electricUsed = calculateElectricUsed(oldBill, newBill);
        return electricUsed * ELECTRIC_PRICE;
    }

    /**
     *  计算总房租
     *
     * @param bill bill
     * @return total
     */
    public static double calculateTotalFee(MonthlyBill bill) {
        return bill.getBaseRent() + bill.getWaterFee() + bill.getElectricFee() + bill.getOtherFees();
    }
}
