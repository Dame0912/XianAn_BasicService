package com.xinan.cn.p2p.litagation.enums;


/**
 * 涉诉金额等级
 * <p>
 * 由于部分案件属于依法不能公开的，或者部分数据属于涉及个人隐私而不能公开的，因此部分数据以模型化数据或脱敏后提供
 */
public enum LawAmountRankEnum {

    Rank_0(0, "未填写或0"),
    Rank_1(1, "N < 1"),
    Rank_2(2, "1 <= N < 5"),
    Rank_3(3, "5 <= N < 10"),
    Rank_4(4, "10 <= N < 30"),
    Rank_5(5, "30 <= N < 50"),
    Rank_6(6, "50 <= N < 80"),
    Rank_7(7, "80 <= N < 100"),
    Rank_8(8, "100 <= N < 300"),
    Rank_9(9, "300 <= N < 500"),
    Rank_10(10, "500 <= N < 800"),
    Rank_11(11, "800 <= N < 1000"),
    Rank_12(12, "1000 <= N < 3000"),
    Rank_13(13, "3000 <= N < 5000"),
    Rank_14(14, "5000 <= N < 8000"),
    Rank_15(15, "8000 <= N < 10000"),
    Rank_16(16, "10000 <= N < 30000"),
    Rank_17(17, "30000 <= N < 50000"),
    Rank_18(18, "50000 <= N < 80000"),
    Rank_19(19, "80000 <= N < 100000"),
    Rank_20(20, "100000 <= N");

    /**
     * 金额等级
     */
    public final int amountRank;

    /**
     * 金额区间（单位： 万元）
     */
    public final String amountRange;

    LawAmountRankEnum(int amountRank, String amountRange) {
        this.amountRank = amountRank;
        this.amountRange = amountRange;
    }

    public static String getAmountRange(int amountRank) {
        for (LawAmountRankEnum lawAmount : LawAmountRankEnum.values()) {
            if (lawAmount.amountRank == amountRank) {
                return lawAmount.amountRange;
            }
        }
        return "";
    }

}
