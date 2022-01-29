package com.kkb.es;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpHost;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: sublun
 * @Date: 2021/4/22 15:43
 */
public class DocumentManagerTest {
    public RestHighLevelClient client = null;
    @Before
    public void init() {
        client = new RestHighLevelClient(RestClient.builder(
                new HttpHost("192.168.68.129", 9200, "http"),
                new HttpHost("192.168.68.130", 9200, "http"),
                new HttpHost("192.168.68.131", 9200, "http")
        ));
    }

    @Test
    public void addDocument() throws IOException {
        String item = "{\"id\":1, \"title\":\"帮日本核废水“出招”？知名男艺人迷之操作让人看呆了..\", \"content\":\"帮日本核废水“出招”？知名男艺人迷之操作让人看呆了..\", \"comment\":\"娱乐\", \"mobile\":\"13900112233\"}";
        IndexResponse response = client.index(new IndexRequest()
                    .index("test")
                    .id("1")
                    .source(item, XContentType.JSON)
                , RequestOptions.DEFAULT);
        System.out.println(response);

    }
    @Test
    public void updateDocument() throws Exception {
        String item = "{\"id\":1, \"title\":\"湖南电影频道一行来桃花源考察\", \"content\":\"湖南电影频道一行来桃花源考察\", \"comment\":\"娱乐\", \"mobile\":\"13900112239\"}";
        UpdateResponse response = client.update((new UpdateRequest()
                        .index("test")
                        .id("1")
                        .doc(item, XContentType.JSON))
                , RequestOptions.DEFAULT);
        System.out.println(response);
    }
    @Test
    public void deleteDocument() throws Exception {
        DeleteRequest request = new DeleteRequest("test","1");
        System.out.println(request);
        DeleteResponse response = client.delete(request, RequestOptions.DEFAULT);
        System.out.println(response);
    }
    @Test
    public void bulkDocument() throws IOException {
        JSONArray jsonArray = JSONArray.parseArray("[{\"id\":1, \"title\":\"帮日本核废水“出招”？知名男艺人迷之操作让人看呆了..\", \"content\":\"帮日本核废水“出招”？知名男艺人迷之操作让人看呆了..\", \"comment\":\"娱乐\", \"mobile\":\"13900112233\"}\n" +
                "{\"id\":2, \"title\":\"《奔跑吧9》4月23日开播，沙溢、蔡徐坤等回归\", \"content\":\"《奔跑吧9》4月23日开播，沙溢、蔡徐坤等回归\", \"comment\":\"娱乐\", \"mobile\":\"13900112233\"}\n" +
                "{\"id\":3, \"title\":\"桐梓：九坝这群人田间指导忙\", \"content\":\"桐梓：九坝这群人田间指导忙\", \"comment\":\"娱乐\", \"mobile\":\"13900112233\"}\n" +
                "{\"id\":4, \"title\":\"柳子戏《江姐》进入联排阶段，月底将与济南观众见面\", \"content\":\"柳子戏《江姐》进入联排阶段，月底将与济南观众见面\", \"comment\":\"娱乐\", \"mobile\":\"13900112233\"}\n" +
                "{\"id\":5, \"title\":\"湖南电影频道一行来桃花源考察\", \"content\":\"湖南电影频道一行来桃花源考察\", \"comment\":\"娱乐\", \"mobile\":\"13900112239\"}\n" +
                "{\"id\":6, \"title\":\"酒吧戏剧混搭新潮流 京城开首家戏剧酒吧\", \"content\":\"酒吧戏剧混搭新潮流 京城开首家戏剧酒吧\", \"comment\":\"娱乐\", \"mobile\":\"13900112239\"}\n" +
                "{\"id\":7, \"title\":\"贵州省歌舞剧院 音乐“江湖”华山论剑\", \"content\":\"贵州省歌舞剧院 音乐“江湖”华山论剑\", \"comment\":\"娱乐\", \"mobile\":\"13900112239\"}\n" +
                "{\"id\":8, \"title\":\"《奔跑吧9》欢乐定档！新一季，新面貌，新感受，你值..\", \"content\":\"《奔跑吧9》欢乐定档！新一季，新面貌，新感受，你值..\", \"comment\":\"娱乐\", \"mobile\":\"13900112239\"}\n" +
                "{\"id\":9, \"title\":\"李宇春发新歌《软肋》，剖析自我成长的道路\", \"content\":\"李宇春发新歌《软肋》，剖析自我成长的道路\", \"comment\":\"娱乐\", \"mobile\":\"13900112239\"}\n" +
                "{\"id\":10, \"title\":\"电影《超越》定档6月12日，郑恺演“百米飞人”\", \"content\":\"电影《超越》定档6月12日，郑恺演“百米飞人”\", \"comment\":\"娱乐\", \"mobile\":\"13900112239\"}\n" +
                "{\"id\":11, \"title\":\"电影《笨贼向前冲》火热预售中，4月24日全国上映\", \"content\":\"电影《笨贼向前冲》火热预售中，4月24日全国上映\", \"comment\":\"娱乐\", \"mobile\":\"13900112239\"}\n" +
                "{\"id\":12, \"title\":\"《金牌喜剧班》学员走进北京人艺 班级大考迎来收官\", \"content\":\"《金牌喜剧班》学员走进北京人艺 班级大考迎来收官\", \"comment\":\"娱乐\", \"mobile\":\"13000000000\"}\n" +
                "{\"id\":13, \"title\":\"花万把块钱，姜昆刘能亲口送祝福？揭秘明星祝福视频产..\", \"content\":\"花万把块钱，姜昆刘能亲口送祝福？揭秘明星祝福视频产..\", \"comment\":\"娱乐\", \"mobile\":\"13000000000\"}\n" +
                "{\"id\":14, \"title\":\"明星开饭店频翻车，其实他们都是“甩手掌柜”\", \"content\":\"明星开饭店频翻车，其实他们都是“甩手掌柜”\", \"comment\":\"娱乐\", \"mobile\":\"13000000000\"}\n" +
                "{\"id\":15, \"title\":\"明星开饭店频翻车 其实他们都是“甩手掌柜”\", \"content\":\"明星开饭店频翻车 其实他们都是“甩手掌柜”\", \"comment\":\"娱乐\", \"mobile\":\"13000000000\"}\n" +
                "{\"id\":16, \"title\":\"明星“从商记”，谁更得意？\", \"content\":\"明星“从商记”，谁更得意？\", \"comment\":\"娱乐\", \"mobile\":\"13000000000\"}\n" +
                "{\"id\":17, \"title\":\"朱丹都为它打call，龙泉的“明星”们有点厉害\", \"content\":\"朱丹都为它打call，龙泉的“明星”们有点厉害\", \"comment\":\"娱乐\", \"mobile\":\"13000000000\"}\n" +
                "{\"id\":18, \"title\":\"银川这些大明星，你pick谁？\", \"content\":\"银川这些大明星，你pick谁？\", \"comment\":\"娱乐\", \"mobile\":\"13000000000\"}\n" +
                "{\"id\":19, \"title\":\"“追星人”要守规矩！\", \"content\":\"“追星人”要守规矩！\", \"comment\":\"娱乐\", \"mobile\":\"13000000000\"}\n" +
                "{\"id\":20, \"title\":\"央视网评| 这才是追星应有的“姿势”\", \"content\":\"央视网评| 这才是追星应有的“姿势”\", \"comment\":\"娱乐\", \"mobile\":\"13000000000\"}\n" +
                "{\"id\":21, \"title\":\"央视春晚与刘德华“同台” 沈阳高新区走出多位“明星..\", \"content\":\"央视春晚与刘德华“同台” 沈阳高新区走出多位“明星..\", \"comment\":\"娱乐\", \"mobile\":\"13000000000\"}\n" +
                "{\"id\":22, \"title\":\"P2P暴雷明星要负责？北京传出重磅信号！代言者春节..\", \"content\":\"P2P暴雷明星要负责？北京传出重磅信号！代言者春节..\", \"comment\":\"娱乐\", \"mobile\":\"13000000000\"}\n" +
                "{\"id\":23, \"title\":\"莫言、冯巩、刘德华，顶流“大V”正向短视频集体迁徙\", \"content\":\"莫言、冯巩、刘德华，顶流“大V”正向短视频集体迁徙\", \"comment\":\"娱乐\", \"mobile\":\"13000000000\"}\n" +
                "{\"id\":24, \"title\":\"直面争议，70后直男导演迈出的第一步丨专访《追光吧..\", \"content\":\"直面争议，70后直男导演迈出的第一步丨专访《追光吧..\", \"comment\":\"娱乐\", \"mobile\":\"13000000000\"}\n" +
                "{\"id\":25, \"title\":\"新华微评：让失德艺人彻底“凉凉”\", \"content\":\"新华微评：让失德艺人彻底“凉凉”\", \"comment\":\"娱乐\", \"mobile\":\"13000000000\"}\n" +
                "{\"id\":26, \"title\":\"郑爽陷“代孕弃养门”：不能以精致的自私踩踏人伦底线\", \"content\":\"郑爽陷“代孕弃养门”：不能以精致的自私踩踏人伦底线\", \"comment\":\"娱乐\", \"mobile\":\"13000000000\"}\n" +
                "{\"id\":27, \"title\":\"2020明星带货生存战，谁赢了？\", \"content\":\"2020明星带货生存战，谁赢了？\", \"comment\":\"娱乐\", \"mobile\":\"13890112230\"}\n" +
                "{\"id\":28, \"title\":\"理智追星，拒绝隐私消费\", \"content\":\"理智追星，拒绝隐私消费\", \"comment\":\"娱乐\", \"mobile\":\"13890112230\"}\n" +
                "{\"id\":29, \"title\":\"冒犯、跨界和出位：跨年晚会的流量密码\", \"content\":\"冒犯、跨界和出位：跨年晚会的流量密码\", \"comment\":\"娱乐\", \"mobile\":\"13890112230\"}\n" +
                "{\"id\":30, \"title\":\"艺人们，请爱惜自己的羽毛\", \"content\":\"艺人们，请爱惜自己的羽毛\", \"comment\":\"娱乐\", \"mobile\":\"13890112230\"}\n" +
                "{\"id\":31, \"title\":\"国际水准的演出即将纷至沓来 “后世博文化演艺圈\", \"content\":\"国际水准的演出即将纷至沓来 “后世博文化演艺圈\", \"comment\":\"娱乐\", \"mobile\":\"13890112230\"}\n" +
                "{\"id\":32, \"title\":\"演艺圈“围城”为哪般：网红进演艺圈扑腾，明星下\", \"content\":\"演艺圈“围城”为哪般：网红进演艺圈扑腾，明星下\", \"comment\":\"娱乐\", \"mobile\":\"13890112230\"}\n" +
                "{\"id\":33, \"title\":\"【我的法援故事第611辑】身边的“娱乐圈大瓜”\", \"content\":\"【我的法援故事第611辑】身边的“娱乐圈大瓜”\", \"comment\":\"娱乐\", \"mobile\":\"13890112230\"}\n" +
                "{\"id\":34, \"title\":\"走进95后职场新人的世界！密室逃脱真人NPC工\", \"content\":\"走进95后职场新人的世界！密室逃脱真人NPC工\", \"comment\":\"娱乐\", \"mobile\":\"13890112230\"}\n" +
                "{\"id\":35, \"title\":\"“砸锅”品牌遭娱乐圈集体抵制！数十家国货发声力\", \"content\":\"“砸锅”品牌遭娱乐圈集体抵制！数十家国货发声力\", \"comment\":\"娱乐\", \"mobile\":\"13890112230\"}\n" +
                "{\"id\":36, \"title\":\"公布娱乐圈的冻龄秘诀，都有这一项！\", \"content\":\"公布娱乐圈的冻龄秘诀，都有这一项！\", \"comment\":\"娱乐\", \"mobile\":\"13890112230\"}\n" +
                "{\"id\":37, \"title\":\"天要下雨，娃要出道——富家子弟进入演艺圈赛道\", \"content\":\"天要下雨，娃要出道——富家子弟进入演艺圈赛道\", \"comment\":\"娱乐\", \"mobile\":\"13890112230\"}\n" +
                "{\"id\":38, \"title\":\"歌手庞麦郎：喧嚣的孤独\", \"content\":\"歌手庞麦郎：喧嚣的孤独\", \"comment\":\"娱乐\", \"mobile\":\"13890112230\"}\n" +
                "{\"id\":39, \"title\":\"这个15岁小孩什么背景？生日能获杨幂，朱一龙，\", \"content\":\"这个15岁小孩什么背景？生日能获杨幂，朱一龙，\", \"comment\":\"娱乐\", \"mobile\":\"13890112230\"}\n" +
                "{\"id\":40, \"title\":\"《爱在星空下》定档3.17，贾乃亮、陈意涵首搭\", \"content\":\"《爱在星空下》定档3.17，贾乃亮、陈意涵首搭\", \"comment\":\"娱乐\", \"mobile\":\"13890112230\"}\n" +
                "{\"id\":41, \"title\":\"喜剧悬疑电影《狄仁杰之夺命奇香》开机 神探杨树林破..\", \"content\":\"喜剧悬疑电影《狄仁杰之夺命奇香》开机 神探杨树林破..\", \"comment\":\"娱乐\", \"mobile\":\"13890112230\"}\n" +
                "{\"id\":42, \"title\":\"喜剧大师罗伯托·贝尼尼获威尼斯电影节终身成就奖\", \"content\":\"喜剧大师罗伯托·贝尼尼获威尼斯电影节终身成就奖\", \"comment\":\"娱乐\", \"mobile\":\"13890112230\"}\n" +
                "{\"id\":43, \"title\":\"第二轮开售｜摩拳擦掌，笑迎五一！\", \"content\":\"第二轮开售｜摩拳擦掌，笑迎五一！\", \"comment\":\"娱乐\", \"mobile\":\"13890112230\"}\n" +
                "{\"id\":44, \"title\":\"「教育强国」张颐武：《你好，李焕英》——忧伤与宽慰\", \"content\":\"「教育强国」张颐武：《你好，李焕英》——忧伤与宽慰\", \"comment\":\"娱乐\", \"mobile\":\"13890112230\"}\n" +
                "{\"id\":45, \"title\":\"“复兴”的国产喜剧，好像和以前不一样了\", \"content\":\"“复兴”的国产喜剧，好像和以前不一样了\", \"comment\":\"娱乐\", \"mobile\":\"13890112230\"}\n" +
                "{\"id\":46, \"title\":\"丁真被曝参演喜剧电影？银幕首秀或搭档杨超越，路透画..\", \"content\":\"丁真被曝参演喜剧电影？银幕首秀或搭档杨超越，路透画..\", \"comment\":\"娱乐\", \"mobile\":\"13890112230\"}\n" +
                "{\"id\":47, \"title\":\"【杂谈】电影里的温暖治愈力量\", \"content\":\"【杂谈】电影里的温暖治愈力量\", \"comment\":\"娱乐\", \"mobile\":\"13890112230\"}\n" +
                "{\"id\":48, \"title\":\"《金牌喜剧班》学员走进北京人艺 班级大考迎来收官\", \"content\":\"《金牌喜剧班》学员走进北京人艺 班级大考迎来收官\", \"comment\":\"娱乐\", \"mobile\":\"13890112230\"}\n" +
                "{\"id\":49, \"title\":\"开心麻花深耕大湾区音乐剧，未来它比电影、喜剧更吸金\", \"content\":\"开心麻花深耕大湾区音乐剧，未来它比电影、喜剧更吸金\", \"comment\":\"娱乐\", \"mobile\":\"13890112230\"}\n" +
                "{\"id\":50, \"title\":\"喜剧电影《爸爸是外星人》正式开机\", \"content\":\"喜剧电影《爸爸是外星人》正式开机\", \"comment\":\"娱乐\", \"mobile\":\"13890112230\"}\n" +
                "{\"id\":51, \"title\":\"喜剧，法国人引以为傲的“国民挚爱”\", \"content\":\"喜剧，法国人引以为傲的“国民挚爱”\", \"comment\":\"娱乐\", \"mobile\":\"13890112230\"}\n" +
                "{\"id\":52, \"title\":\"陈佩斯向相声致敬 《金牌喜剧班》展现新时代喜剧的传..\", \"content\":\"陈佩斯向相声致敬 《金牌喜剧班》展现新时代喜剧的传..\", \"comment\":\"娱乐\", \"mobile\":\"13890112230\"}\n" +
                "{\"id\":53, \"title\":\"《流星花园》：电视时代的青春叙事\", \"content\":\"《流星花园》：电视时代的青春叙事\", \"comment\":\"娱乐\", \"mobile\":\"13890112230\"}\n" +
                "{\"id\":54, \"title\":\"传苹果正开发新Apple TV 或集成音响和摄像头\", \"content\":\"传苹果正开发新Apple TV 或集成音响和摄像头\", \"comment\":\"娱乐\", \"mobile\":\"13900112233\"}\n" +
                "{\"id\":55, \"title\":\"以《十年》之名，守望来者\", \"content\":\"以《十年》之名，守望来者\", \"comment\":\"娱乐\", \"mobile\":\"13900112233\"}\n" +
                "{\"id\":56, \"title\":\"薄如纸、能弯曲！创维推出可变形OLED电视和8K ..\", \"content\":\"薄如纸、能弯曲！创维推出可变形OLED电视和8K ..\", \"comment\":\"娱乐\", \"mobile\":\"13900112233\"}\n" +
                "{\"id\":57, \"title\":\"三星电视：视听之上 还想诠释什么叫“科技美学”\", \"content\":\"三星电视：视听之上 还想诠释什么叫“科技美学”\", \"comment\":\"娱乐\", \"mobile\":\"13900112233\"}\n" +
                "{\"id\":58, \"title\":\"4月18日起，沙坪坝电视新闻播出平台有变化！\", \"content\":\"4月18日起，沙坪坝电视新闻播出平台有变化！\", \"comment\":\"娱乐\", \"mobile\":\"13900112233\"}\n" +
                "{\"id\":59, \"title\":\"1天无手机生活直播挑战：物联网重构家庭新场景\", \"content\":\"1天无手机生活直播挑战：物联网重构家庭新场景\", \"comment\":\"娱乐\", \"mobile\":\"13900112233\"}\n" +
                "{\"id\":60, \"title\":\"韩国两大财阀将联手？LG将向三星供货OLED面板\", \"content\":\"韩国两大财阀将联手？LG将向三星供货OLED面板\", \"comment\":\"娱乐\", \"mobile\":\"13900112233\"}\n" +
                "{\"id\":61, \"title\":\"《“大家”说法》电视公开课：到货的土豆变小了？\", \"content\":\"《“大家”说法》电视公开课：到货的土豆变小了？\", \"comment\":\"娱乐\", \"mobile\":\"13900112233\"}\n" +
                "{\"id\":62, \"title\":\"苏宁家电联袂电影《你好，李焕英》，万元大奖助力换大..\", \"content\":\"苏宁家电联袂电影《你好，李焕英》，万元大奖助力换大..\", \"comment\":\"娱乐\", \"mobile\":\"13900112233\"}\n" +
                "{\"id\":63, \"title\":\"中国电子信息博览会大奖揭晓 海信电视U7G-PRO..\", \"content\":\"中国电子信息博览会大奖揭晓 海信电视U7G-PRO..\", \"comment\":\"娱乐\", \"mobile\":\"13900112233\"}\n" +
                "{\"id\":64, \"title\":\"涨价！小米电视官方发布价格调整公告，网友：理解、支..\", \"content\":\"涨价！小米电视官方发布价格调整公告，网友：理解、支..\", \"comment\":\"娱乐\", \"mobile\":\"13900112233\"}\n" +
                "]");
        BulkRequest bulkRequest = new BulkRequest();
        jsonArray.stream()
                .forEach(item -> {
                    IndexRequest indexRequest = new IndexRequest();
                    indexRequest.index("test")
                            .id(((JSONObject) item).getString("id"))
                            .source(((JSONObject) item).toJSONString(), XContentType.JSON);
                    bulkRequest.add(indexRequest);
                });
        client.bulk(bulkRequest, RequestOptions.DEFAULT);
    }
    @Test
    public void getDocumentById() throws Exception {
        GetResponse response = client.get(new GetRequest("test", "1"), RequestOptions.DEFAULT);
        System.out.println(response);
        Map<String, Object> source = response.getSource();
        System.out.println(source);
    }
}
