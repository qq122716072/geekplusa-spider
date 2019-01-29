package com.geekplusa.spider.toutiao;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * @program: geekplusa-spider
 * @description: ${description}
 * @author: weixd
 * @createTime: 2019-01-29 13:13
 **/
public class TouTiaoRepoPageProcessor implements PageProcessor {

    private Site site = Site.me().setRetryTimes(3).setSleepTime(100);

    private static final String TOUTIAO_HOME_KEYWORD = "广告";
    private static final String TOUTIAO_URL = "(https://www\\.toutiao\\.com)";
//    private static final String TOUTIA_URL_HOME_AD = "//li[@class='J_qihu_ad']";
    private static final String TOUTIA_URL_HOME_AD = "//li[@class='J_qihu_ad']/div/div[@class='single-mode-rbox']/div/div/a/*";
//    private static final String TOUTIA_URL_HOME_AD = "//u1[@infinite-scroll-disabled='loading']/li[@class='']/div/div[@class='bui-box footer-bar']/div/a/text()";

    @Override
    public void process(Page page) {
        //判断是否为头条
        if (page.getUrl().regex(TOUTIAO_URL).match()) {
            /*
             * @Description 获取 <ul infinite-scroll-disabled="loading" infinite-scroll-immediate-check="containerCheck" infinite-scroll-immediate-check-count="containerCheckCount" infinite-scroll-distance="80">
             *              标签下的 <li class="J_qihu_ad" ad_qihu_id="${num}" ad_show="0">的所有标签 num=0-14
             */
            page.addTargetRequests(page.getHtml().xpath(TOUTIA_URL_HOME_AD).all());
            System.out.println("抓取的内容:" + page.getHtml().xpath(TOUTIA_URL_HOME_AD).get());
            page.putField(TOUTIAO_HOME_KEYWORD, page.getHtml().xpath(TOUTIA_URL_HOME_AD).toString());
        }
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Spider.create(new TouTiaoRepoPageProcessor()).addUrl("https://www.toutiao.com").thread(5).run();
    }
}
