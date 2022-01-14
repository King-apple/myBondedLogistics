package com.newweb.mrli.ykport.baoshuisystem.utils;

import java.math.BigDecimal;


/**
 * 离职前最后一段代码
 * */
public class UnitConversion {

    /**
     * 默认保留两位小数,四舍五入
     *
     * @param value    原始数值
     * @param original 原始单位
     * @param need     转换的单位
     * @return
     */
    public static BigDecimal conversion(BigDecimal value, UnitsEnum original, UnitsEnum need) {
        return conversion(value, original, need, 2);
    }

    /**
     * @param value    原始数值
     * @param original 原始单位
     * @param need     转换的单位
     * @param scale    小数点位数
     * @return
     */
    public static BigDecimal conversion(BigDecimal value, UnitsEnum original, UnitsEnum need, int scale) {
        if (original == UnitsEnum.UN_KNOWN || need == UnitsEnum.UN_KNOWN) {
            throw new IllegalArgumentException("存在不支持的单位参数");
        }
        if (original.category != need.category) {
            throw new IllegalArgumentException("转换单位不统一!" + original.category.name + "不能转换为" + need.category.name);
        }
        return value == null ? new BigDecimal(0) : value.multiply(need.rate).divide(original.rate, scale, BigDecimal.ROUND_HALF_UP);
    }


    /**
     * 默认保留两位小数,四舍五入
     *
     * @param value    原始数值
     * @param original 原始单位
     * @param need     转换的单位
     * @return
     */
    public static BigDecimal conversion(BigDecimal value, String original, String need) {
        return conversion(value, getUnitEnum(original), getUnitEnum(need));
    }

    public static UnitsEnum getUnitEnum(String unit) {
        if (unit!= null) {
            for (UnitsEnum unitEnum : UnitsEnum.values()) {
                for (String possibleName : unitEnum.possibleNames) {
                    if (possibleName.equals(unit.toLowerCase())) {
                        return unitEnum;
                    }
                }
            }
        }
        return UnitsEnum.UN_KNOWN;
    }


    public enum UnitsEnum {

        LG_KM(CategoryEnum.LENGTH, "km", new String[]{"km", "千米"}, new BigDecimal("0.001"), "千米"),
        LG_M(CategoryEnum.LENGTH, "m", new String[]{"m", "米"}, new BigDecimal("1"), "米"),
        LG_Dm(CategoryEnum.LENGTH, "dm", new String[]{"dm", "分米"}, new BigDecimal("10"), "分米"),
        LG_CM(CategoryEnum.LENGTH, "cm", new String[]{"cm", "厘米"}, new BigDecimal("100"), "厘米"),
        LG_MM(CategoryEnum.LENGTH, "mm", new String[]{"mm", "毫米"}, new BigDecimal("1000"), "毫米"),
        LG_UM(CategoryEnum.LENGTH, "um", new String[]{"um", "微米"}, new BigDecimal("1000000"), "微米"),
        LG_NM(CategoryEnum.LENGTH, "nm", new String[]{"nm", "纳米"}, new BigDecimal("1000000000"), "纳米"),
        LG_INCH(CategoryEnum.LENGTH, "inch", new String[]{"in", "inch", "英寸"}, new BigDecimal("39.3700787"), "英寸"),

        EG_T(CategoryEnum.WEIGHT, "t", new String[]{"t", "吨"}, new BigDecimal("0.001"), "吨"),
        EG_KG(CategoryEnum.WEIGHT, "kg", new String[]{"kg", "千克"}, new BigDecimal("1"), "千克"),
        EG_G(CategoryEnum.WEIGHT, "g", new String[]{"g", "克"}, new BigDecimal("1000"), "克"),
        EG_MG(CategoryEnum.WEIGHT, "mg", new String[]{"mg", "毫克"}, new BigDecimal("1000000"), "毫克"),
        EG_UG(CategoryEnum.WEIGHT, "μg", new String[]{"μg", "ug", "微克"}, new BigDecimal("1000000000"), "微克"),
        EG_LB(CategoryEnum.WEIGHT, "lb", new String[]{"lb", "lbs", "磅"}, new BigDecimal("2.2046226"), "磅"),
        EG_OZ(CategoryEnum.WEIGHT, "oz", new String[]{"oz", "盎司"}, new BigDecimal("35.2739619"), "盎司"),
        EG_CT(CategoryEnum.WEIGHT, "ct", new String[]{"ct", "克拉"}, new BigDecimal("5000"), "克拉"),
        

        UN_KNOWN(null, "未知", null, new BigDecimal("0"), "未知");

        private CategoryEnum category;
        private String units;
        private String[] possibleNames;
        private BigDecimal rate;
        private String description;

        UnitsEnum(CategoryEnum category, String units, String[] possibleNames, BigDecimal rate, String description) {
            this.category = category;
            this.units = units;
            this.possibleNames = possibleNames;
            this.rate = rate;
            this.description = description;
        }

        public CategoryEnum getCategory() {
            return category;
        }

        public String getUnits() {
            return units;
        }

        public String[] getPossibleNames() {
            return possibleNames;
        }

        public BigDecimal getRate() {
            return rate;
        }

        public String getDescription() {
            return description;
        }


        private enum CategoryEnum {

            LENGTH("length", UnitsEnum.LG_M, "长度"),
            WEIGHT("weight", UnitsEnum.EG_KG, "重量");

            private String name;
            private UnitsEnum base;
            private String description;

            CategoryEnum(String name, UnitsEnum base, String description) {
                this.name = name;
                this.base = base;
                this.description = description;
            }

            public String getName() {
                return name;
            }

            public UnitsEnum getBase() {
                return base;
            }

            public String getDescription() {
                return description;
            }
        }

    }

}