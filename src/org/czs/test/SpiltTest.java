package org.czs.test;

import java.util.regex.Pattern;

public class SpiltTest {
	
	private static final Pattern DELIMITER = Pattern.compile("[|]");

	public static void main(String[] args) {
		String fields = "WEIXIN_CNT|YIXIN_CNT|CYBER_BANK_CNT|THIRD_PART_PAY_CNT|ALIPAY_CNT|TAOBAO_CNT|INFO_CNT|VARIETY_CNT|AMUSE_CNT|EDU_CNT|CATOON_CNT|SPORTS_CNT|FOOTBALL_CNT|BASEKET_CNT|FINANCE_CNT|ESHOPPING_CNT|EC_SELLER_CNT|TAOBAO_SELLER_CNT|JINGDONG_SELLER_CNT|YHD_SELLER_CNT|OTHER_SELLER_CNT|RECORD_CNT|NEWS_CNT|RESOURCE_CNT|JOB_CNT|TRIP_CNT|RESIDE_CNT|FOTO_CNT|FASHION_CNT|FOOD_CNT|CAR_CNT|HEALTH_CNT|ILLEGAL_CNT|OPERATOR_CNT|DIGITAL_CNT|MUSIC_CNT|VEDIO_CNT|READING_CNT|PIC_CNT|GAME_CNT|SOCIAL_CNT|COMMUNICATE_CNT|COMMUNITY_CNT|SPACE_CNT|TOOLS_CNT|SYS_CNT|APP_CNT|OFFICE_CNT|ENTAPP_CNT|IDEAL_CNT|CLOTHES_CNT|ACCESSORY_CNT|OUTDOOR_CNT|JEWEL_CNT|DIGITAL_SHOPPING_CNT|ELETRICAL_CNT|COSMETICS_CNT|BABY_CNT|HOUSE_CNT|DAILYUSE_CNT|HEALTH_PRO_CNT|BOOK_CNT|AV_CNT|MOTOR_CNT|TOY_CNT|STATIONERY_CNT|COLLECT_CNT|LIFE_CNT";

//		StringTokenizer itr =  new StringTokenizer(fields, "|");
//
//		while(itr.hasMoreTokens()){
//			System.out.println(itr.nextToken());
//		}
		
		String[] lines = SpiltTest.DELIMITER.split(fields);
		for(String line : lines){
			System.out.println(line.intern());
		}
	}

}
