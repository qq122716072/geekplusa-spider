package com.geekplusa.spider.github;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * @program: geekplusa-spider
 * @description: ${description}
 * @author: weixd
 * @createTime: 2019-01-29 10:36
 **/
public class GithubRepoPageProcessor implements PageProcessor {

    private static final String GITHUB_URL = "(https://github\\.com/\\w+/\\w+)";
    private static final String GITHUB_URL_DETAIL = "https://github\\.com/(\\w+)/.*";
    private Site site = Site.me().setRetryTimes(3).setSleepTime(100);


    @Override
    public void process(Page page) {
        page.addTargetRequests(page.getHtml().links().regex(GITHUB_URL).all());
        page.putField("author", page.getUrl().regex(GITHUB_URL_DETAIL).toString());
        page.putField("name", page.getHtml().xpath("//h1[@class='entry-title public']/strong/a/text()").toString());
        if (page.getResultItems().get("name")==null){
            //skip this page
            page.setSkip(true);
        }
        page.putField("readme", page.getHtml().xpath("//div[@id='readme']/tidyText()"));
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Spider.create(new GithubRepoPageProcessor()).addUrl("https://github.com/code4craft").thread(5).run();
    }
}
